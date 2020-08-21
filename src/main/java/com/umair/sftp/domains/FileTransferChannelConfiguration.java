/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umair.sftp.domains;

import com.umair.sftp.constants.AppConstants;
import com.umair.sftp.wrappers.Configrurable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author m.umair
 */


@XmlRootElement(name = AppConstants.XMLConfigurationsConstants.CHANNEL_CONFIG_ELEMENT_TAG_NAME)
@XmlAccessorType (XmlAccessType.FIELD)
public class FileTransferChannelConfiguration implements Configrurable {

    private Integer id;
    private String configurationName;
    private Integer inboundSftpServer;
    private Integer outboundSftpServer;
    private String inboundDirectory;
    private String inboundArchiveDirectory;
    private String outboundDirectory;
    private String cron;
    private String bulkZipCron;
    private String regexFilter;
    private String sourceEncoding;
    private String targetEncoding;
    private String zipFile;
    private String zipFileName;
    private String zipFileDirectory;

    public Integer getId() {
        return id;
    }

    public String getConfigurationName() {
        return configurationName;
    }

    public Integer getInboundSftpServer() {
        return inboundSftpServer;
    }

    public Integer getOutboundSftpServer() {
        return outboundSftpServer;
    }

    public String getInboundDirectory() {
        return inboundDirectory;
    }

    public String getInboundArchiveDirectory() {
        return inboundArchiveDirectory;
    }

    public String getOutboundDirectory() {
        return outboundDirectory;
    }

    public String getCron() {
        return cron;
    }

    public String getRegexFilter() {
        return regexFilter;
    }

    public String getSourceEncoding() {
        return sourceEncoding;
    }

    public String getTargetEncoding() {
        return targetEncoding;
    }

    public String getZipFile() {
        return zipFile;
    }

    public String getBulkZipCron() {
        return bulkZipCron;
    }

    public String getZipFileDirectory() {
        return zipFileDirectory;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }

    public void setInboundSftpServer(Integer inboundSftpServer) {
        this.inboundSftpServer = inboundSftpServer;
    }

    public void setOutboundSftpServer(Integer outboundSftpServer) {
        this.outboundSftpServer = outboundSftpServer;
    }

    public void setInboundDirectory(String inboundDirectory) {
        this.inboundDirectory = inboundDirectory;
    }

    public void setInboundArchiveDirectory(String inboundArchiveDirectory) {
        this.inboundArchiveDirectory = inboundArchiveDirectory;
    }

    public void setOutboundDirectory(String outboundDirectory) {
        this.outboundDirectory = outboundDirectory;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public void setBulkZipCron(String bulkZipCron) {
        this.bulkZipCron = bulkZipCron;
    }

    public void setRegexFilter(String regexFilter) {
        this.regexFilter = regexFilter;
    }

    public void setSourceEncoding(String sourceEncoding) {
        this.sourceEncoding = sourceEncoding;
    }

    public void setTargetEncoding(String targetEncoding) {
        this.targetEncoding = targetEncoding;
    }

    public void setZipFile(String zipFile) {
        this.zipFile = zipFile;
    }

    public void setZipFileName(String zipFileName) {
        this.zipFileName = zipFileName;
    }

    public void setZipFileDirectory(String zipFileDirectory) {
        this.zipFileDirectory = zipFileDirectory;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        FileTransferChannelConfiguration other = (FileTransferChannelConfiguration) obj;
        if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean isIdentical(Object obj) {
        FileTransferChannelConfiguration other = (FileTransferChannelConfiguration) obj;
        return !(!this.inboundDirectory.equalsIgnoreCase(other.inboundDirectory) || !this.outboundDirectory.equalsIgnoreCase(other.outboundDirectory) || !this.inboundSftpServer.equals(other.inboundSftpServer) || !this.outboundSftpServer.equals(other.outboundSftpServer));
    }

    public String getZipFileName() {
        return zipFileName;
    }
}
