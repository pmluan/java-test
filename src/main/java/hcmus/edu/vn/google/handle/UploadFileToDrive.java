package hcmus.edu.vn.google.handle;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import hcmus.edu.vn.google.service.GoogleApiService;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class UploadFileToDrive {

	public static void main(String[] args) throws IOException, GeneralSecurityException {

		Drive driveService = GoogleApiService.getDriveService();

		File fileMetadata = new File();
		fileMetadata.setName("");
		java.io.File filePath = new java.io.File("");
		FileContent mediaContent = new FileContent("a", filePath);
		File file = driveService.files().create(fileMetadata, mediaContent)
		    .setFields("id")
		    .execute();
		System.out.println("File ID: " + file.getId());
	}

}
