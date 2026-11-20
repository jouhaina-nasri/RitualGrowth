package com.yoterra.hippo;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import com.yoterra.hippo.helpers.AppPasswordsManager;
import com.yoterra.hippo.helpers.HippoCommandLine;
import com.yoterra.hippo.helpers.AppPasswordsManager.PassDef;
import com.yoterra.utils.CollectionUtils;

@SpringBootApplication(scanBasePackages = "com.yoterra.hippo.*",  exclude = {SecurityAutoConfiguration.class})
public class RgRestApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(RgRestApplication.class);
		app.run(args);

	}
}
