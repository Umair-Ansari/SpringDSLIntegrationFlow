/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umair.sftp.constants;

/**
 *
 * @author umair
 */
public interface AppConstants {

    public static final String LOCAL_DIRECTORY_NAME = "keys";
    public static final int SCHEDULER_FIXED_DELAY = 40000;
    public static final String CONFIGURATIONS_FILE_NAME = "fileTrasnferEndpointConfiguration.xml";
    public static final String SUCCESS_EXPRESSION = "headers[file_originalFile].delete()";
    public static final  String CHANNEL_NAME = "fromSftpChannel";
    public static final String CRON_EXPRESSION = "0 */10 * ? * *";
    public static final String FILE_NAME = "file_name";
    public static final long REMOTE_DIR_FIXED_DELAY=60_000;
    public static final String TEMP_DIRECTORY_NAME="temp";
    public static final String API_SUCCESS_CODE="200";
    public static final String API_SUCCESS_STATUS="SUCCESS";
    public static final String API_ERROR_CODE="300";
    public static final String API_ERROR_STATUS="ERROR";
    public static final String API_EXCEPTION_CODE="500";
    public static final String API_EXCEPTION_STATUS="EXCEPTION";

    public static class XMLConfigurationsConstants{
        public static final String ROOT_ELEMENT_TAG_NAME="configurations";
        public static final String END_POINT_CONFIG_ELEMENT_TAG_NAME="fileTrasnferEndpointConfiguration";
        public static final String CHANNEL_CONFIG_ELEMENT_TAG_NAME="fileTrasferChannelConfiguration";
    }

    public static class FileTrasferChannelConstants {
        public static final String FILE_TRANSFER_CHANNEL_ID = "id";
        public static final String FILE_TRANSFER_CHANNEL_NAME = "name";
        public static final String FILE_TRANSFER_CHANNEL_INBOUND_DIRECTORY = "inbound_directory";
        public static final String FILE_TRANSFER_CHANNEL_OUTBOUND_DIRECTORY = "outbound_directory";
        public static final String FILE_TRANSFER_CHANNEL_INBOUND_SFTP_SERVER = "inbound_sftp_server";
        public static final String FILE_TRANSFER_CHANNEL_OUTBOUND_SFTP_SERVER = "outbound_sftp_server";
        public static final String FILE_TRANSFER_CHANNEL_INBOUND_ARCHIVE_DIRECTORY = "inbound_archive_directory";
    }

}
