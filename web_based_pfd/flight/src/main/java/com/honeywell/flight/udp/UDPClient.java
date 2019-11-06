package com.honeywell.flight.udp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.integration.ip.udp.UnicastSendingMessageHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@EnableConfigurationProperties(UDPClient.UDPServerProperties.class)
public class UDPClient implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(UDPClient.class);

    @Resource
    private UDPServerProperties serverProperties;

    private UnicastSendingMessageHandler handler;

    @Override
    public void afterPropertiesSet() throws Exception {
        handler = new UnicastSendingMessageHandler(serverProperties.getHost(), serverProperties.getPort());
    }

    public void sendMessage(String message) {
        handler.handleMessage(MessageBuilder.withPayload(message).build());
        LOGGER.info("sent data: {}", message);
    }

    @ConfigurationProperties(prefix = "udp.server")
    public static class UDPServerProperties {

        private String host;

        private int port;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
