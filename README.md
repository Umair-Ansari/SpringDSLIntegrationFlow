# SpringDSLIntegrationFlow 
**This is demo app only**

Issue only occurs when inbound server is valid (app can connect) and outbound server is not valid (app cant connect)

To test issue inbound server shoud be configured correctly and IP for outbound server must be invalid. 

***Following configurations needs to be filled before testing***

open https://github.com/Umair-Ansari/SpringDSLIntegrationFlow/blob/master/src/main/java/com/umair/sftp/schedules/ScheduledTask.java

1. configrue inbound server. fill out only missing configurations. configurations must be valid.
inboundServer.setServerName("inboundServer"); // valid server
inboundServer.setHost(""); //ip
inboundServer.setPort(22);
inboundServer.setUser("");
inboundServer.setPassword("");


2. configure outbound server. **IP of this server must be invalid** 
outboundServer.setId(2);
outboundServer.setServerName("outboundServer"); // must be an invalid server
outboundServer.setHost(""); /ip
outboundServer.setPort(22);
outboundServer.setUser("");
outboundServer.setPassword("");

3. configure channel.
channel.setInboundSftpServer(1); // inboundServer  
channel.setOutboundSftpServer(2); // outboundServer 
channel.setInboundDirectory("/home/...."); //  inboundServer pick file
channel.setOutboundDirectory("/home/...."); // outboundServer  place file
channel.setCron("0 */1 * ? * *");
channel.setRegexFilter(".*");

once its configured just start app. scheduler should connect to inbound server and try to download file using **channel.setInboundDirectory("/home/....");** directory you provided. 

outbound server will try to connect (to invalid IP) and after 4-6 seconds it will move to next subscriber.

Integration Flow are here https://github.com/Umair-Ansari/SpringDSLIntegrationFlow/blob/master/src/main/java/com/umair/sftp/services/ChannelService.java
