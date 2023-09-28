package hcmus.edu.vn.google.handle;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import hcmus.edu.vn.google.service.GoogleApiService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateFolder {
	private static final Logger LOGGER = LoggerFactory.getLogger(CreateFolder.class);
	
	public static void main(String[] args) {
		// Create a Root Folder.
		createGoogleSheet();
	}

	public static  File createGoogleFolder(String folderIdParent, String folderName)
			throws IOException, GeneralSecurityException {

		File fileMetadata = new File();

		fileMetadata.setName(folderName);
		fileMetadata.setMimeType("application/vnd.google-apps.folder");

		if (folderIdParent != null) {
			List<String> parents = Collections.singletonList(folderIdParent);

			fileMetadata.setParents(parents);
		}
		Drive driveService = GoogleApiService.getDriveService();

		// Create a Folder.
		return driveService.files().create(fileMetadata).setFields("id, name").execute();

	}

	private static String createGoogleSheet() {
		try {
			Drive driveService = GoogleApiService.getDriveService();

			String sheetName = "Catch_All_Concern_" + System.currentTimeMillis();
			String folderName = "DEMO";

			FileList result = driveService.files().list().setFields("nextPageToken, files(id, name)").execute();
			List<File> files = result.getFiles();
			List<String> parentNames = new ArrayList<>();

			if (CollectionUtils.isEmpty(files)) {
				LOGGER.info("No files found");
			}

			for (File file : files) {
				if (StringUtils.equalsIgnoreCase(folderName, file.getName())) {
					parentNames = Collections.singletonList(file.getId());
				}
			}

			File sheetFile = new File();
			sheetFile.setName(sheetName);
			sheetFile.setMimeType("application/vnd.google-apps.spreadsheet");

			if (CollectionUtils.isEmpty(parentNames)) {
				File parentFile = createGoogleFolder(null, folderName);
				parentNames = Collections.singletonList(parentFile.getId());
			}

			sheetFile.setParents(parentNames);

			driveService.files().create(sheetFile).setFields("id, name").execute();

			return sheetName;

		} catch (Exception e) {

			LOGGER.error("createGoogleSheet - Error: ", e);
			return StringUtils.EMPTY;
		}

	}
}
