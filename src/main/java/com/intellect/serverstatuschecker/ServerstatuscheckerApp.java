package com.intellect.serverstatuschecker;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.scheduling.annotation.EnableScheduling;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableConfigurationProperties({ LiquibaseProperties.class})
@EnableScheduling 
public class ServerstatuscheckerApp {

    private static final Logger LOG = LoggerFactory.getLogger(ServerstatuscheckerApp.class);

    private final Environment env;

    public ServerstatuscheckerApp(Environment env) {
        this.env = env;
    }

	/**
	 * Inits the application.
	 */
	@PostConstruct
	public void initApplication() {
		Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
		if (activeProfiles.contains("dev") && activeProfiles.contains("prod")) {
			LOG.error("You have misconfigured your application! It should not run "
					+ "with both the 'dev' and 'prod' profiles at the same time.");
		}
		if (activeProfiles.contains("dev") && activeProfiles.contains("cloud")) {
			LOG.error("You have misconfigured your application! It should not "
					+ "run with both the 'dev' and 'cloud' profiles at the same time.");
		}
	}

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments.
     */
	public static void main(String[] args) throws UnknownHostException {
		/**
		 * SpringApplication app = new
		 * SpringApplication(HrmRecruitmentApplication.class);
		 * DefaultProfileUtil.addDefaultProfile(app); Environment env =
		 * app.run(args).getEnvironment();
		 */
		SpringApplication application = new SpringApplication(ServerstatuscheckerApp.class);
		ConfigurableEnvironment env = new StandardEnvironment();
		if (args.length > 0) {
			System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, args[0]);
			env.setActiveProfiles(args[0]);
		} else {
			System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "dev");
			env.setActiveProfiles("dev");
		}

		application.setEnvironment(env);
		application.run(args);

		String protocol = "http";
		if (env.getProperty("server.ssl.key-store") != null) {
			protocol = "https";
		}
		LOG.info("\n-------------------------------------------------------------------------------------------\n\t"
				+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}\n\t"
				+ "External: \t{}://{}:{}\n\t"
				+ "Profile(s): \t{}\n-------------------------------------------------------------------------------------------",
				env.getProperty("spring.application.name"), protocol, env.getProperty("server.port"), protocol,
				InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"), env.getActiveProfiles());

		String configServerStatus = env.getProperty("configserver.status");
		LOG.info("\n-------------------------------------------------------------------------------------------\n\t"
				+ "Config Server: \t{}\n-------------------------------------------------------------------------------------------",
				configServerStatus == null ? "Not found or not setup for this application" : configServerStatus);
	}

}
