package com.yoterra.hippo.config;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yoterra.hippo.annotations.profiles.Local;
//
@Local
@Configuration
public class DbConfig {
//	
@Value("${com.hippo.db.port}")
private String dbPort;
//	
 @Bean(initMethod = "start", destroyMethod = "stop")
public Server h2Server() throws SQLException {
return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", dbPort);
  }
}
