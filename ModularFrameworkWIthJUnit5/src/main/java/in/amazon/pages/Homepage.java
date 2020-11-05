package in.amazon.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Homepage extends BasePage {

	@CacheLookup
	@FindBy(id = "twotabsearchtextbox")
	private WebElement searchBox;

	@CacheLookup
	@FindBy(xpath = "//input[@value='Go']")
	private WebElement searchButton;

	@CacheLookup
	@FindBy(id = "searchDropdownBox")
	private WebElement searchDropdown;

	@FindBy(xpath = "//span[@data-component-type='s-result-info-bar']")
	private WebElement results;

	public Homepage(WebDriver driver) {

		super(driver);

		PageFactory.initElements(driver, this);

	}

	public String searchProduct(String product, String category) throws Exception {

		elementControl.clearText(searchBox);

		elementControl.setText(searchBox, product);

		dropdownControl.selectViaVisibleText(searchDropdown, category);

		elementControl.clickElement(searchButton);

		return elementControl.getText(results);

	}

}
