package commonLibs.contracts;

import java.util.Set;

public interface IWindows {
	
	public void switchToChildWindow(String windowhandle) throws Exception;

	public void switchToChildWindow(int childWindowIndex) throws Exception;

	public String getWindowHandle() throws Exception;

	public Set<String> getWindowHandles() throws Exception;


}
