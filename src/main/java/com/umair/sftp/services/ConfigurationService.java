/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umair.sftp.services;

import com.umair.sftp.domains.FileTransferChannelConfiguration;
import com.umair.sftp.domains.FileTransferEndpointConfiguration;
import java.util.HashSet;
import java.util.Set;
import com.umair.sftp.wrappers.HashSetWrapper;
import org.springframework.stereotype.Service;

/**
 *
 * @author m.umair
 */

@Service
public class ConfigurationService {
    private Set<FileTransferEndpointConfiguration> fileTransferEndpointConfiguration =  new HashSet<>();
    private Set<String> keySet = new HashSet<>();
    private Set<FileTransferChannelConfiguration> currentConfigurationChannel = new HashSet<>();
    private HashSetWrapper<FileTransferChannelConfiguration> configurationChannelSet = new HashSetWrapper(currentConfigurationChannel);

    public void setConfigurationChannelListDB(Set<FileTransferChannelConfiguration> configurationChannelListDB){
        configurationChannelSet = new HashSetWrapper(currentConfigurationChannel);
        configurationChannelSet.addAll(configurationChannelListDB);
        
    }

    public HashSetWrapper<FileTransferChannelConfiguration> getConfigurations() throws Throwable{
        return configurationChannelSet;
    }

    public void setCurrentConfigurationChannel(Set<FileTransferChannelConfiguration> currentConfigurationChannel)  throws Throwable{
        this.currentConfigurationChannel = currentConfigurationChannel;
    }
    
    public void removeConfigurationChannelById(Integer configurationChannelId){
        this.currentConfigurationChannel.remove(configurationChannelId);
    }

    public void setKey(String key) {
        this.keySet.add(key);
    }
    
    public boolean checkKey(String key){
        if (this.keySet.stream().anyMatch((k) -> (k.equalsIgnoreCase(key)))) {
            System.out.println("private key match found");
            return true;
        }        
        return false;
    }
    
    public void addConfigurationChannelById(FileTransferChannelConfiguration currentConfigurationChannel){
        this.currentConfigurationChannel.add(currentConfigurationChannel);
        this.configurationChannelSet.add(currentConfigurationChannel);
    }

    public void setFileTrasnferEndpointConfiguration(Set<FileTransferEndpointConfiguration> fileTransferEndpointConfiguration) {
        this.fileTransferEndpointConfiguration = fileTransferEndpointConfiguration;
    }
    
    public FileTransferEndpointConfiguration getFileTrasnferEndpointConfiguration(Integer id){
        FileTransferEndpointConfiguration configuration = new FileTransferEndpointConfiguration();
        fileTransferEndpointConfiguration.forEach((FileTransferEndpointConfiguration cnsmr) -> {
            if(cnsmr.getId() == id){
                configuration.setId(cnsmr.getId());
                configuration.setPassword(cnsmr.getPassword());
                configuration.setHost(cnsmr.getHost());
                configuration.setPort(cnsmr.getPort());
                configuration.setPrivateKey(cnsmr.getPrivateKey());
                configuration.setUser(cnsmr.getUser());
                configuration.setPrivateKeyPassphrase(cnsmr.getPrivateKeyPassphrase());
            }
        });
        return configuration;
    }
    
}
