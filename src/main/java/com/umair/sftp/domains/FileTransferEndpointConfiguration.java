/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umair.sftp.domains;
import com.umair.sftp.constants.AppConstants;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author m.umair
 */

@XmlRootElement(name = AppConstants.XMLConfigurationsConstants.END_POINT_CONFIG_ELEMENT_TAG_NAME)
@XmlAccessorType (XmlAccessType.FIELD)
public class FileTransferEndpointConfiguration implements Serializable {

    private Integer id;
    private Integer port = 22;
    private String host;
    private String user;
    private String privateKey;
    private String privateKeyPassphrase;
    private String password;
    private String serverName;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPrivateKeyPassphrase() {
        return privateKeyPassphrase;
    }

    public void setPrivateKeyPassphrase(String privateKeyPassphrase) {
        this.privateKeyPassphrase = privateKeyPassphrase;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public String toString() {
        return "FileTransferEndpointConfiguration{" + "id=" + id + ", port=" + port + ", host=" + host + ", user=" + user + ", privateKey=" + privateKey + ", privateKeyPassphrase=" + privateKeyPassphrase + ", password=" + password + ", serverName=" + serverName + '}';
    }

}
