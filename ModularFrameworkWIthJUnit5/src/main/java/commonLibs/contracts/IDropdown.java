package commonLibs.contracts;

import org.openqa.selenium.WebElement;

public interface IDropdown {
	
	public void selectViaVisibleText(WebElement element, String visibleText) throws Exception;

	public void selectViaValue(WebElement element, String value) throws Exception;

	public void selectViaIndex(WebElement element, int index) throws Exception;

}
