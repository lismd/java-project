package com.honeywell.avionics.browser.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(UDPServerConfig.UDPServerProperties.class)
public class UDPServerConfig {

    @Resource
    private UDPServerProperties udpServerProperties;

    @Bean
    public IntegrationFlow processUniCastUdpMessage() {
        return IntegrationFlows
                .from(new UnicastReceivingChannelAdapter(udpServerProperties.getPort()))
                .handle("UDPServer", "handleMessage")
                .get();
    }

    @ConfigurationProperties(prefix = "udp.server")
    public static class UDPServerProperties {

        private int port;

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }
}
