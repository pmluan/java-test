package hcmus.edu.vn.google.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import hcmus.edu.vn.google.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GoogleApiService {
	private static final Logger LOGGER = LoggerFactory.getLogger(GoogleApiService.class);
	
    // Directory to store user credentials for this application.
    private static final File CREDENTIALS_FOLDER = new File(System.getProperty("user.home"), "credentials");
 
    private static final String CLIENT_SECRET_FILE_NAME = "client_secret.json";
    
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    
    // Global instance of the HTTP transport.
    private static HttpTransport httpTransport;
    
    // Global instance of the {@link FileDataStoreFactory}.
    private static  FileDataStoreFactory dataStoreFactory;
    
    static {
        try {
        	httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(CREDENTIALS_FOLDER);
        } catch (Exception t) {
            LOGGER.error("Log error:{}",t.getMessage());
            System.exit(1);
        }
    }
	
	public static Credential getCredentials(List<String> scopes) throws IOException {
		
		File clientSecretFilePath = new File(CREDENTIALS_FOLDER, CLIENT_SECRET_FILE_NAME);
		 
        if (!clientSecretFilePath.exists()) {
            throw new FileNotFoundException("Please copy " + CLIENT_SECRET_FILE_NAME
                    + " to folder: " + CREDENTIALS_FOLDER.getAbsolutePath());
        }
        
        InputStream in = new FileInputStream(clientSecretFilePath);
        
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
		
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY,
                clientSecrets, scopes).setDataStoreFactory(dataStoreFactory).setAccessType("offline").build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
	}
	
	
    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
    	List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);
    	Credential credential = GoogleApiService.getCredentials(scopes);
    	HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    	
    	return new Sheets.Builder(httpTransport, JSON_FACTORY, credential)
    					 .setApplicationName(Constants.APPLICATION_NAME_SHEET)
    					 .build();
    }
    
    
	public static Drive getDriveService() throws IOException, GeneralSecurityException {
		List<String> scopes = Collections.singletonList(DriveScopes.DRIVE);
		Credential credential = GoogleApiService.getCredentials(scopes);
		HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		
		return new Drive.Builder(httpTransport, JSON_FACTORY, credential)
				 		.setApplicationName(Constants.APPLICATION_NAME_DRIVE)
				 		.build();
	}
	
	private GoogleApiService () {
		// Prevent instantiation
	}
}
