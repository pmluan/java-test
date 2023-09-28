package hcmus.edu.vn.google.handle;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;
import hcmus.edu.vn.google.service.GoogleApiService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

public class GoolgeSheetHandle {
	private static final Logger LOGGER = LoggerFactory.getLogger(GoolgeSheetHandle.class);

	private static Sheets sheetsService;
	private static Drive driveService;
	private static final String SPREAD_SHEET_ID = "158vXazX-aVfCcjAGe42SzsSRoqzMO2ZJNu-ZwSTHCIQ";

	public static void main(String[] args) {

		try {

			sheetsService = GoogleApiService.getSheetsService();
			driveService = GoogleApiService.getDriveService();

			Permission userPermission = new Permission().setType("group").setRole("writer")
					.setEmailAddress("<hoan.nn@sutrixsolutions.com>");
			driveService.permissions().create(SPREAD_SHEET_ID, userPermission).setFileId(SPREAD_SHEET_ID).execute();

			LOGGER.info("DONE!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		} catch (IOException | GeneralSecurityException e) {
			LOGGER.error("Error: ", e);
		}

	}

	private static UpdateValuesResponse writeIntoSheet() throws IOException {
		ValueRange range = new ValueRange().setValues(Arrays.asList(Arrays.asList("books", "30")));
		return sheetsService.spreadsheets().values().update(SPREAD_SHEET_ID, "A1", range).setValueInputOption("RAW")
				.execute();
	}

	private static AppendValuesResponse appendingData() throws IOException {
		ValueRange appendBody = new ValueRange().setValues(Arrays.asList(Arrays.asList("aafafaf", "aaaaa")));
		return sheetsService.spreadsheets().values().append(SPREAD_SHEET_ID, "A1", appendBody)
				.setValueInputOption("USER_ENTERED").setInsertDataOption("INSERT_ROWS").setIncludeValuesInResponse(true)
				.execute();
	}

	private static ValueRange readValueFromSheet() throws IOException {
		List<String> ranges = Arrays.asList("A1");
		BatchGetValuesResponse readResult = sheetsService.spreadsheets().values().batchGet(SPREAD_SHEET_ID)
				.setRanges(ranges).execute();

		return readResult.getValueRanges().get(0);
	}

	private static void createNewSpreadsheets() throws IOException {
		Spreadsheet spreadSheet = new Spreadsheet()
				.setProperties(new SpreadsheetProperties().setTitle("My Spreadsheet"));
		sheetsService.spreadsheets().create(spreadSheet).execute();
	}

	private static String createGoogleSheet() {
		try {
			String sheetName = "CreateSheets";

			File sheetFile = new File();
			sheetFile.setName(sheetName);
			sheetFile.setMimeType("application/vnd.google-apps.spreadsheet");

			driveService.files().create(sheetFile).setFields("id, name").execute();

			return sheetFile.getId();

		} catch (Exception e) {

			LOGGER.error("createNewSpreadsheets - Error: ", e);
			return StringUtils.EMPTY;
		}

	}
}
