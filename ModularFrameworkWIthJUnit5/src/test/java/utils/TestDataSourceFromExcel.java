package utils;

import commonLibs.utils.ExcelDriver;

public class TestDataSourceFromExcel {
	
	private static String currentWorkingDirectory;
	
	public static Object[][] getDataforSearchProduct() throws Exception{

		currentWorkingDirectory = System.getProperty("user.dir");

		String excelWorkbook = String.format("%s/testDataSource/TestData.xlsx", currentWorkingDirectory);

		String excelSheetname = "SearchProduct";
		
		return readDataFromExcel(excelWorkbook, excelSheetname);

	}

	private static Object[][] readDataFromExcel(String workbookFilename, String sheetname) throws Exception {

		ExcelDriver excelDriver = new ExcelDriver();

		excelDriver.openWorkbook(workbookFilename);

		Object[][] data;

		int rowCount, cellCount;

		rowCount = excelDriver.getRowCount(sheetname);

		cellCount = excelDriver.getCellCountFromRow(sheetname, 0);

		data = new Object[rowCount + 1][cellCount];

		for (int row = 0; row <= rowCount; row++) {
			for (int cell = 0; cell < cellCount; cell++) {
				
				data[row][cell] = excelDriver.getCellData(sheetname, row, cell);
				
				
			}
		}
		
		return data;

	}

}
