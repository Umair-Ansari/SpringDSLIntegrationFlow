package com.umair.sftp.schedules;

import com.umair.sftp.constants.AppConstants;
import com.umair.sftp.domains.FileTransferChannelConfiguration;
import com.umair.sftp.domains.FileTransferEndpointConfiguration;
import com.umair.sftp.services.ChannelService;
import com.umair.sftp.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;


import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author m.umair
 */
@Component
public class ScheduledTask {

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private ChannelService channelService;


    @Scheduled(fixedDelay = AppConstants.SCHEDULER_FIXED_DELAY)
    public void scheduler() {
        try {
            //doing constant config for demo purpose
            FileTransferEndpointConfiguration inboundServer =  new FileTransferEndpointConfiguration();
            inboundServer.setId(1);
            inboundServer.setServerName("inboundServer"); // valid server
            inboundServer.setHost("");
            inboundServer.setPort(22);
            inboundServer.setUser("");
            inboundServer.setPassword("");

            FileTransferEndpointConfiguration outboundServer =  new FileTransferEndpointConfiguration();
            outboundServer.setId(2);
            outboundServer.setServerName("outboundServer"); // much be an invalid server
            outboundServer.setHost("");
            outboundServer.setPort(22);
            outboundServer.setUser("");
            outboundServer.setPassword("");

            Set<FileTransferEndpointConfiguration> fileTransferEndpointConfiguration = new HashSet<>();
            fileTransferEndpointConfiguration.add(inboundServer);
            fileTransferEndpointConfiguration.add(outboundServer);


            FileTransferChannelConfiguration channel = new FileTransferChannelConfiguration();
            channel.setId(1);
            channel.setConfigurationName("demo config");
            channel.setInboundSftpServer(1); // inboundServer  
            channel.setOutboundSftpServer(2); // outboundServer 
            channel.setInboundDirectory("/home/...."); //  inboundServer pick file
            channel.setOutboundDirectory("/home/...."); // outboundServer  place file
            channel.setCron("0 */1 * ? * *");
            channel.setRegexFilter(".*");

            Set<FileTransferChannelConfiguration> toRegister = new HashSet<>();
            toRegister.add(channel);


            configurationService.setFileTrasnferEndpointConfiguration(fileTransferEndpointConfiguration);
            channelService.register(toRegister);

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

}
