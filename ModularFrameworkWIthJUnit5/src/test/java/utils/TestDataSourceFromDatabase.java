package utils;

import commonLibs.utils.DatabaseDetails;
import commonLibs.utils.DatabaseUtils;

public class TestDataSourceFromDatabase {
	
	public static Object[][] getDataforSearchProduct() throws Exception {

		DatabaseUtils databaseUtils = new DatabaseUtils();

		DatabaseDetails dbDetails = new DatabaseDetails();

		dbDetails.setServer("localhost");
		dbDetails.setDatabase("amazonTestData");

		dbDetails.setPassword("admin@1234");

		dbDetails.setUsername("root");
		dbDetails.setPortNumber(3306);

		databaseUtils.openConnect(dbDetails);

		String sqlQuery = "select * from searchProductTestData";

		Object[][] data = databaseUtils.executeSelectSqlQuery(sqlQuery);

		databaseUtils.closeConection();

		return data;

	}

}
