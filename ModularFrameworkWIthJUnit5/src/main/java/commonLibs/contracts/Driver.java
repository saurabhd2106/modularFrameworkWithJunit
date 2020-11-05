package commonLibs.contracts;

public interface Driver {
	
	public void navigateToUrl(String url) throws Exception;
	
	public void refresh() throws Exception;
	
	public void navigateBack() throws Exception;
	
	public void navigateForward() throws Exception;
	
	public String getTitle() throws Exception;

	public String getCurrentUrl() throws Exception;
	
	public void closeBrowser() throws Exception;
	
	public void closeAllBrowsers() throws Exception;
	
}
