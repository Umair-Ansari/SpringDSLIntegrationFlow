/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umair.sftp.services;

import com.jcraft.jsch.ChannelSftp;
import com.umair.sftp.SpringDSLIntegrationFlow;
import com.umair.sftp.config.Startup;
import com.umair.sftp.constants.AppConstants;
import com.umair.sftp.domains.FileTransferChannelConfiguration;
import com.umair.sftp.domains.FileTransferEndpointConfiguration;
import com.umair.sftp.util.Util;
import com.umair.sftp.sessions.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.sftp.dsl.Sftp;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Set;

/**
 *
 * @author m.umair
 */
@Component
public class ChannelService {


    private ConfigurationService configurationService;
    private IntegrationFlowContext context;
    private SessionManager sessionManager;
    private Util util;
    private Startup startup;

    public ChannelService(){}

    @Autowired
    public ChannelService(IntegrationFlowContext context, SessionManager sessionManager, Util util, Startup startup, ConfigurationService configurationService) {
        this.context = context;
        this.sessionManager = sessionManager;
        this.util = util;
        this.startup = startup;
        this.configurationService = configurationService;
    }

    public void register(Set<FileTransferChannelConfiguration> toRegister) throws Throwable {
        toRegister.forEach(this::registerChannel);
    }

    public void unregister(Set<FileTransferChannelConfiguration> toUnregister) throws Throwable {
        toUnregister.forEach(config -> {
            this.destroy(String.valueOf(config.getId()));
        });
    }

    public void update(Set<FileTransferChannelConfiguration> toUpdate) throws Throwable {

        toUpdate.forEach(config -> {

            this.destroy(String.valueOf(config.getId()));
            this.registerChannel(config);
        });
    }

    private void stop(String id) {
        this.context.getRegistrationById(id).stop();
    }

    private void start(String id) throws Throwable {
        this.context.getRegistrationById(id).start();
    }

    private void destroy(String id) {
        this.stop(id);
        this.context.getRegistrationById(id).destroy();
    }

    public void registerChannel(FileTransferChannelConfiguration config) {

        FileTransferEndpointConfiguration inboundSftpServer = configurationService.getFileTrasnferEndpointConfiguration(config.getInboundSftpServer());
        FileTransferEndpointConfiguration outboundSftpServer = configurationService.getFileTrasnferEndpointConfiguration(config.getOutboundSftpServer());

        CachingSessionFactory<ChannelSftp.LsEntry> inboundSftp = (CachingSessionFactory<ChannelSftp.LsEntry>) sessionManager.sftpSessionFactory(inboundSftpServer);
        CachingSessionFactory<ChannelSftp.LsEntry> outboundSftp = (CachingSessionFactory<ChannelSftp.LsEntry>) sessionManager.sftpSessionFactory(outboundSftpServer);

        if (this.context.getRegistrationById(String.valueOf(config.getId())) == null) {
            IntegrationFlow flow = configureIntegrationFlow(config, inboundSftpServer, outboundSftpServer, inboundSftp, outboundSftp);
            System.out.println("Registering an Integration Flow with id : " + String.valueOf(config.getId()));
            this.context.registration(flow)
                    .id(String.valueOf(config.getId()))
                    .addBean(inboundSftp)
                    .register();
        }
        configurationService.addConfigurationChannelById(config);
    }

    private IntegrationFlow configureIntegrationFlow(FileTransferChannelConfiguration config, FileTransferEndpointConfiguration inboundSftpServer, FileTransferEndpointConfiguration outboundSftpServer, CachingSessionFactory<ChannelSftp.LsEntry> inboundSftp, CachingSessionFactory<ChannelSftp.LsEntry> outboundSftp) {
        boolean doArchive = config.getInboundArchiveDirectory() != null;

        return IntegrationFlows.from(
                Sftp.inboundAdapter(inboundSftp)
                .localDirectory(this.getlocalDirectory(config.getId()))
                .deleteRemoteFiles(true)
                .autoCreateLocalDirectory(true)
                .filter(new CompositeFileListFilter().addFilter(new LastModifiedLsEntryFileListFilter(config.getRegexFilter())))
                .remoteDirectory(config.getInboundDirectory())
                , e -> e.poller(
                        Pollers.cron(config.getCron())
                        .errorChannel(MessageHeaders.ERROR_CHANNEL).errorHandler((ex) -> {
                            try {

                                this.destroy(String.valueOf(config.getId()));
                                configurationService.removeConfigurationChannelById(config.getId());

                                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> exception >>>>>>>>>>>>>");
                                ex.printStackTrace();

                            } catch (Exception ex1) {
                                ex1.printStackTrace();
                            }
                        })
                        .advice(startup.scanRemoteDirectory())
                ))
//                .transform(
//                    file -> util.transform((File) file, config.getSourceEncoding(), config.getTargetEncoding(), doEncoding, doZip))
                .publishSubscribeChannel(s -> s
                    .subscribe(f -> {

                        f.handle(Sftp.outboundAdapter(outboundSftp)
                            .useTemporaryFileName(false)
                            .autoCreateDirectory(true)
                            .remoteDirectory(config.getOutboundDirectory()), c -> c.advice(startup.deleteFileAdvice()));

                    })
                    .subscribe(f -> {

                                    if(doArchive) {
                                        f.handle(Sftp.outboundAdapter(inboundSftp)
                                                .useTemporaryFileName(false)
                                                .autoCreateDirectory(true)
                                                .remoteDirectory(config.getInboundArchiveDirectory()));
                                    } else {
                                        f.handle(m -> {});
                                    }

                                })
                    .subscribe(f -> f
                        .handle(m -> {

                            System.out.println(">>>>>>>>>>>>>> file transferred log here");
                        })
                    )
                )
                .get();
    }

    private File getlocalDirectory(int configId) {
        File jarPath = new File(SpringDSLIntegrationFlow.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        jarPath = jarPath.getParentFile().getParentFile().getParentFile();

        StringBuilder sb = new StringBuilder(jarPath.toString());

        sb.append("/");
        sb.append(AppConstants.TEMP_DIRECTORY_NAME);
        sb.append("/");
        sb.append(configId);

        File directory = new File(sb.toString().substring(6));

        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }
}
