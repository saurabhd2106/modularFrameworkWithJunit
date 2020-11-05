package commonLibs.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDriver {

	private InputStream fileReader;

	private OutputStream fileWriter;

	private Workbook excelWorkbook;

	private String excelFilename;

	public void createWorkbook(String workbookFilename) throws Exception {
		workbookFilename = workbookFilename.trim();

		File file = new File(workbookFilename);

		if (file.exists()) {
			throw new Exception("File already exist...");
		}

		if (workbookFilename.endsWith(".xlsx")) {
			excelWorkbook = new XSSFWorkbook();
		} else if (workbookFilename.endsWith(".xls")) {
			excelWorkbook = new HSSFWorkbook();

		} else {
			throw new Exception("Invalid file..." + workbookFilename);
		}

		fileWriter = new FileOutputStream(workbookFilename);

		excelWorkbook.write(fileWriter);

		fileWriter.close();

		excelWorkbook.close();

	}

	public void openWorkbook(String workbookFilename) throws Exception {

		workbookFilename = workbookFilename.trim();

		excelFilename = workbookFilename;

		File file = new File(workbookFilename);

		if (!file.exists()) {
			throw new Exception("File doesnot exists..");
		}

		fileReader = new FileInputStream(workbookFilename);

		excelWorkbook = WorkbookFactory.create(fileReader);

	}

	public void createSheet(String sheetname) throws Exception {

		sheetname = sheetname.trim();

		Sheet sheet = excelWorkbook.getSheet(sheetname);

		if (sheet != null) {
			throw new Exception("Sheet already exists..");
		}

		excelWorkbook.createSheet(sheetname);

	}

	public int getRowCount(String sheetname) throws Exception {

		sheetname = sheetname.trim();

		Sheet sheet = excelWorkbook.getSheet(sheetname);

		if (sheet == null) {
			throw new Exception("Sheet doesnot exist...");
		}

		return sheet.getLastRowNum();

	}

	public int getCellCountFromRow(String sheetname, int rowNumber) throws Exception {

		sheetname = sheetname.trim();

		Sheet sheet = excelWorkbook.getSheet(sheetname);

		if (sheet == null) {
			throw new Exception("Sheet doesnot exist...");
		}

		if (rowNumber < 0) {
			throw new Exception("Invalid Row number");
		}

		Row row;

		row = sheet.getRow(rowNumber);

		if (row == null) {
			return 0;
		} else {
			return row.getLastCellNum();
		}

	}

	public String getCellData(String sheetname, int rowNumber, int cellNumber) throws Exception {
		sheetname = sheetname.trim();

		Sheet sheet = excelWorkbook.getSheet(sheetname);

		if (sheet == null) {
			throw new Exception("Sheet doesnot exist...");
		}

		if (rowNumber < 0 || cellNumber < 0) {
			throw new Exception("Invalid Row or cell number");
		}

		Row row;

		row = sheet.getRow(rowNumber);

		if (row == null) {
			return "";
		}

		Cell cell = row.getCell(cellNumber);

		if (cell == null) {
			return "";
		} else {

			if (cell.getCellType() == CellType.NUMERIC) {
				return String.valueOf(cell.getNumericCellValue());
			} else if (cell.getCellType() == CellType.BOOLEAN) {
				return String.valueOf(cell.getBooleanCellValue());
			} else {
				return cell.getStringCellValue();
			}

		}
	}

	public void setCellData(String sheetname, int rowNumber, int cellNumber, String value) throws Exception {
		sheetname = sheetname.trim();

		Sheet sheet = excelWorkbook.getSheet(sheetname);

		if (sheet == null) {
			throw new Exception("Sheet doesnot exist...");
		}

		if (rowNumber < 0 || cellNumber < 0) {
			throw new Exception("Invalid Row or cell number");
		}

		Row row;

		row = sheet.getRow(rowNumber);

		if (row == null) {
			row = sheet.createRow(rowNumber);

			row = sheet.getRow(rowNumber);
		}

		Cell cell = row.getCell(cellNumber);

		if (cell == null) {
			cell = row.createCell(cellNumber);

			cell = row.getCell(cellNumber);
		}

		cell.setCellValue(value);
	}

	public void saveFile() throws Exception {

		fileWriter = new FileOutputStream(excelFilename);

		excelWorkbook.write(fileWriter);

		fileWriter.close();

	}

	public void saveAsFile(String filename) throws Exception {
		fileWriter = new FileOutputStream(filename);

		excelWorkbook.write(fileWriter);

		fileWriter.close();
	}

	public void closeFile() throws Exception {

		fileReader.close();

		fileWriter.close();

		excelWorkbook.close();

	}
}
