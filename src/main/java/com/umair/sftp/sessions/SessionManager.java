/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umair.sftp.sessions;

import com.umair.sftp.constants.AppConstants;
import com.umair.sftp.domains.FileTransferEndpointConfiguration;
import com.jcraft.jsch.ChannelSftp;
import com.umair.sftp.SpringDSLIntegrationFlow;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author m.umair
 */
@Component
public class SessionManager {

    private HashMap<String, Resource> hmap = new HashMap();

    public SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory(FileTransferEndpointConfiguration sftpServer) {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);

        if (sftpServer.getPassword().length() > 0) {
            factory.setPassword(sftpServer.getPassword());
        } else {
            File jarPath = new File(SpringDSLIntegrationFlow.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            jarPath = jarPath.getParentFile().getParentFile().getParentFile();

            StringBuilder sb = new StringBuilder(jarPath.toString());

            sb.append("/");
            sb.append(AppConstants.LOCAL_DIRECTORY_NAME);
            sb.append("/");
            sb.append(sftpServer.getPrivateKey());

            File privateResourceFile = new File(sb.toString().substring(6));

            Resource resource;
            try {
                resource = new ByteArrayResource(Files.readAllBytes(privateResourceFile.toPath()));

                factory.setPrivateKey(resource);
                factory.setPrivateKeyPassphrase(sftpServer.getPrivateKeyPassphrase());
            }
            catch(Exception e){}

        }
        factory.setHost(sftpServer.getHost());
        factory.setPort(sftpServer.getPort());
        factory.setUser(sftpServer.getUser());

        factory.setAllowUnknownKeys(true);


        return new CachingSessionFactory<>(factory);
    }

    public void putHmap(String fileName, Resource resource) {
        this.hmap.put(fileName, resource);
    }
}
