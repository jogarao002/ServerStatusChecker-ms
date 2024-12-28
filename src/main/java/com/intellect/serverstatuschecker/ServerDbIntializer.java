package com.intellect.serverstatuschecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.intellect.serverstatuschecker.domain.ServerDetails;
import com.intellect.serverstatuschecker.repository.ServerDetailsRepository;
import com.intellect.serverstatuschecker.service.dto.DataSeedingEnum;

@Component
public class ServerDbIntializer implements ApplicationRunner {

	@Autowired
	private ServerDetailsRepository serverDetailsRepository;
	
    private static final Logger logger = Logger.getLogger(ServerDbIntializer.class.getName());
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		serverDetails();
	}

	public void serverDetails() throws IOException {
		List<String> recordsInFile = new ArrayList<>();
		String fileName = "dataseeding/serverDetails.txt";
		try {
			logger.info("Reading server details from file: " + fileName);
			ClassPathResource resource = new ClassPathResource(fileName);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			recordsInFile = bufferedReader.lines().collect(Collectors.toList());
			bufferedReader.close();
			logger.info("Successfully read " + recordsInFile.size() + " records from the file.");
			List<ServerDetails> serverDetailsList = serverDetailsRepository.findAll();
            logger.info("Found " + serverDetailsList.size() + " existing server records in the database.");
			for (String records : recordsInFile) {
				String[] recordFields = records.split("#");
				ServerDetails serverDetails = new ServerDetails();
				for (int i = 0; i < recordFields.length; i++) {
					// split field with : separated
					String[] withFieldName = recordFields[i].split(":");
					if (withFieldName != null) {
						if (null != withFieldName[0].trim() && (withFieldName[0].trim())
								.equalsIgnoreCase(DataSeedingEnum.SERVER_NAME.getColumnName())) {
							serverDetails.setHostName(withFieldName[1].trim());
						}else if (null != withFieldName[0].trim() && (withFieldName[0].trim())
								.equalsIgnoreCase(DataSeedingEnum.SERVICE_NAME.getColumnName())) {
							serverDetails.setServiceName(withFieldName[1].trim());
						}else if (null != withFieldName[0].trim() && (withFieldName[0].trim())
								.equalsIgnoreCase(DataSeedingEnum.SERVER_PROTOCOL_TYPE.getColumnName())) {
							serverDetails.setServerProtocolType(withFieldName[1].trim());
						} else if (null != withFieldName[0].trim() && (withFieldName[0].trim())
								.equalsIgnoreCase(DataSeedingEnum.SERVER_IP_ADDRESS.getColumnName())) {
							serverDetails.setServerIpAddress(withFieldName[1].trim());
						} else if (null != withFieldName[0].trim() && (withFieldName[0].trim())
								.equalsIgnoreCase(DataSeedingEnum.SERVER_PORT.getColumnName())) {
							serverDetails.setServerPort(withFieldName[1].trim());
						} else if (null != withFieldName[0].trim() && (withFieldName[0].trim())
								.equalsIgnoreCase(DataSeedingEnum.SERVER_STATUS.getColumnName())) {
							serverDetails.setServerStatus(Boolean.parseBoolean(withFieldName[1].trim()));
						}

					}
				}

				if (null != serverDetails && null != serverDetails.getServerIpAddress()) {
                    logger.info("Processing server with IP: " + serverDetails.getServerIpAddress());
					boolean isSameIpAddress = serverDetailsList.stream().anyMatch(existingServer -> existingServer
							.getServerIpAddress().equalsIgnoreCase(serverDetails.getServerIpAddress()));

					// If no server with the same IP address exists, save the new server details
					if (!isSameIpAddress && serverDetails.getServerIpAddress() != null) {
                        logger.info("No existing server with the same IP address. Saving the new server details.");
						serverDetailsRepository.save(serverDetails);
					}else {
                        logger.info("Server with IP: " + serverDetails.getServerIpAddress() + " already exists.");
                    }
				}
			}

		} catch (IOException e) {
            logger.severe("Error reading the file " + fileName + ": " + e.getMessage());
			e.printStackTrace();
		}
	}
	

}
