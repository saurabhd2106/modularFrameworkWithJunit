package in.amazon.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.aventstack.extentreports.Status;

@ExtendWith(BaseTest.class)
public class HomepageTests extends BaseTest {

	@DisplayName("VerifySearchProduct")
	@ParameterizedTest
	@MethodSource("utils.TestDataSourceFromDatabase#getDataforSearchProduct")
	public void verifySearchProduct(String product, String category) throws Exception {

		extentReportUtils.createATestcase("TC001 - Verify Search Product");

		extentReportUtils.addLogStatus(Status.INFO, "Product searched - " + product);

		extentReportUtils.addLogStatus(Status.INFO, "Category selected - " + category);

		String result = homepage.searchProduct(product, category);

		extentReportUtils.addLogStatus(Status.INFO, "Results - " + result);

		String expectedResult = "1-24 of over 2,000 results for Electronics : \"Apple Watch\"";

		Assertions.assertEquals(result, expectedResult);

	}

}
