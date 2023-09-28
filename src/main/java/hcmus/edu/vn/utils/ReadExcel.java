package hcmus.edu.vn.utils;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadExcel {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReadExcel.class);

//	public static void main(String[] args) throws IOException {
//
//		String[] data = { "193", "098", "226", "253", "012", "176", "067", "329", "335", "325", "340" };
//
//		List<ExampleModel> lives = readExcel(null, "C:\\Users\\phamm\\Downloads\\Data_HDB_MOBILE_BRANCH_LIVE.xls");
//		List<ExampleModel> lives1 = lives.stream()
//				.filter(item -> StringUtils.equalsAnyIgnoreCase(item.getBranch(), data)).toList();
//
//		List<ExampleModel> newDatas = readExcel(null, "C:\\Users\\phamm\\Downloads\\branch_09_12_2021_final.xls");
//		List<ExampleModel> newDatas1 = newDatas.stream()
//				.filter(item -> StringUtils.equalsAnyIgnoreCase(item.getBranch(), data)).toList();
//
//		System.out.println(Jackson.toJsonString(lives1));
//		System.out.println(Jackson.toJsonString(newDatas1));
//		
//	}
//
//	public static List<ExampleModel> readExcel(String version, String filePath) throws IOException {
//		String createdDate = getCreatedDate();
//		List<ExampleModel> examples = new ArrayList<>();
//		File file = new File(filePath);
//
//		try (Workbook workbook = WorkbookFactory.create(file)) {
//			Sheet sheet = workbook.getSheetAt(0);
//
//			for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
//				if (rowIndex == 0 || rowIndex == 1) {
//					// Ignore header
//					LOGGER.info("Ignore header");
//				} else {
//					ExampleModel exampleModel = getFromRow(createdDate, sheet, rowIndex, version);
//					if (exampleModel != null) {
//						examples.add(exampleModel);
//					}
//				}
//			}
//		} catch (Exception e) {
//			LOGGER.error("Error in read Excel file: ", e);
//		}
//		return examples;
//	}
//
//	private static ExampleModel getFromRow(String createdDate, Sheet sheet, int rowIndex, String version) {
//		Row row = sheet.getRow(rowIndex);
//		if (row == null) {
//			return null;
//		}
//		String branch = getCellValue(row.getCell(0));
//		String branchName = getCellValue(row.getCell(1));
//		String branchShort = getCellValue(row.getCell(2));
//		String branchLocation = getCellValue(row.getCell(3));
//		String state = getCellValue(row.getCell(4));
//		String stateDesc = getCellValue(row.getCell(5));
//		String stateShortDesc = getCellValue(row.getCell(6));
//		String ditrict = getCellValue(row.getCell(7));
//		String ditrictDesc = getCellValue(row.getCell(8));
//		String ditrictShortDesc = getCellValue(row.getCell(9));
//		String branchPhone = getCellValue(row.getCell(10));
//		String branchWard = getCellValue(row.getCell(11));
//		String branchParent = getCellValue(row.getCell(12));
//
//		ExampleModel exampleModel = new ExampleModel();
//		exampleModel.setBranch(branch);
//		exampleModel.setBranchName(branchName);
//		exampleModel.setBranchShort(branchShort);
//		exampleModel.setBranchLocation(branchLocation);
//		exampleModel.setState(state);
//		exampleModel.setStateDesc(stateDesc);
//		exampleModel.setStateShortDesc(stateShortDesc);
//		exampleModel.setDistrict(ditrict);
//		exampleModel.setDistrictDesc(ditrictDesc);
//		exampleModel.setDistrictShortDesc(ditrictShortDesc);
//		exampleModel.setBranchPhone(branchPhone);
//		exampleModel.setBranchWard(branchWard);
//		exampleModel.setBranchParent(branchParent);
//
//		return exampleModel;
//	}

	private static String getTemplateId(String partnerId, String validation, String platform) {
		StringBuilder templateId = new StringBuilder();
		if (StringUtils.isNotBlank(partnerId)) {
			templateId.append(StringUtils.replace(partnerId, StringUtils.SPACE, "-"));
			templateId.append('-');
		}
		if (StringUtils.isNotBlank(validation)) {
			templateId.append(StringUtils.replace(validation, StringUtils.SPACE, "-"));
			templateId.append('-');
		}
		if (StringUtils.isNotBlank(platform)) {
			templateId.append(StringUtils.replace(platform, StringUtils.SPACE, "-"));
		}
		String finalTemplateId = templateId.toString();
		if (StringUtils.isNotBlank(finalTemplateId)) {
			int lastIndex = finalTemplateId.lastIndexOf('-');
			if (lastIndex == finalTemplateId.length() - 1) {
				finalTemplateId = finalTemplateId.substring(0, lastIndex);
			}
		}
		return finalTemplateId;
	}

	// Get cell value
	private static String getCellValue(Cell cell) {
		Object cellValue = StringUtils.EMPTY;
		if (cell != null) {
			CellType cellType = cell.getCellTypeEnum();
			switch (cellType) {
			case BOOLEAN:
				cellValue = cell.getBooleanCellValue();
				break;
			case FORMULA:
				Workbook workbook = cell.getSheet().getWorkbook();
				FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
				cellValue = evaluator.evaluate(cell).getNumberValue();
				break;
			case NUMERIC:
				cellValue = (int) cell.getNumericCellValue();
				break;
			case STRING:
				cellValue = cell.getStringCellValue();
				break;
			case _NONE:
			case BLANK:
			case ERROR:
				break;
			default:
				break;
			}
		}
		return formatValue(cellValue.toString());
	}

	private static String formatValue(String input) {
		if (StringUtils.equalsAnyIgnoreCase(input, "N/A", "#N/A")) {
			return StringUtils.EMPTY;
		}
		return StringUtils.replace(input, "\\n", StringUtils.EMPTY).trim();
	}

	private static String getRandomCode() {
		UUID uuid = UUID.randomUUID();
		String[] ids = uuid.toString().split("-");
		return ids[0];
	}

	private static String getCreatedDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+08:00'");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
		return sdf.format(System.currentTimeMillis());
	}

	private ReadExcel() {
		/* Prevent instantiation */
	}
}