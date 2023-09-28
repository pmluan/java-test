package hcmus.edu.vn.google.handle;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriveQuickstart {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DriveQuickstart.class);
	
	private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";

	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	// Directory to store user credentials for this application.
	private static final java.io.File CREDENTIALS_FOLDER = new java.io.File(System.getProperty("user.home"), "credentials");

	private static final String CLIENT_SECRET_FILE_NAME = "client_secret.json";

	private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

	private static Credential getCredentials(final NetHttpTransport httpTransport) throws IOException {

		java.io.File clientSecretFilePath = new java.io.File(CREDENTIALS_FOLDER, CLIENT_SECRET_FILE_NAME);

		if (!clientSecretFilePath.exists()) {
			throw new FileNotFoundException("Please copy " + CLIENT_SECRET_FILE_NAME + 
					" to folder: " + CREDENTIALS_FOLDER.getAbsolutePath());
		}

		// Load client secrets.
		InputStream in = new FileInputStream(clientSecretFilePath);

		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY,clientSecrets, SCOPES)
												.setDataStoreFactory(new FileDataStoreFactory(CREDENTIALS_FOLDER))
												.setAccessType("offline").build();

		return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
	}

	public static void main(String... args) throws IOException, GeneralSecurityException {

		LOGGER.info("CREDENTIALS_FOLDER: {}", CREDENTIALS_FOLDER.getAbsolutePath());

		// 1: Create CREDENTIALS_FOLDER
		if (!CREDENTIALS_FOLDER.exists()) {
			
			CREDENTIALS_FOLDER.mkdirs();
			
			LOGGER.info("Created Folder: {}", CREDENTIALS_FOLDER.getAbsolutePath());
			LOGGER.info("Copy file {} into folder above.. and rerun this class!!", CLIENT_SECRET_FILE_NAME);
			return;
		}

		// 2: Build a new authorized API client service.
		final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

		// 3: Read client_secret.json file & create Credential object.
		Credential credential = getCredentials(httpTransport);

		// 5: Create Google Drive Service.
		Drive service = new Drive.Builder(httpTransport, JSON_FACTORY, credential)
								 .setApplicationName(APPLICATION_NAME).build();

		// Print the names and IDs for up to 10 files.
		FileList result = service.files().list().setFields("nextPageToken, files(id, name)").execute();
		List<File> files = result.getFiles();
		
		if (files == null || files.isEmpty()) {
			LOGGER.info("No files found.");
			
		} else {
			
			for (File file : files) {
				LOGGER.info("File Name : {}, File id: {}", file.getName(), file.getId());
			}
			
		}
	}
}
