package com.qeautos.selenium_examples;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SeleniumTestLib {
	protected WebDriver driver = null;
	// FireFox:ff; IE: ie
	String browser = "";
	String url = "";
	String screenShotFile = "";
	boolean driverClosed = false;

	public SeleniumTestLib() {

	}

	public WebDriver start() {
		this.browser = Environment.BROWSER;
		this.url = Environment.URL;
		return visit();
	}
	public WebDriver start(String browser) {
		this.browser = browser.toLowerCase().trim();
		this.url = Environment.URL;
		return visit();
	}
	public WebDriver start(String browser, String url) {
		this.browser = browser.toLowerCase().trim();
		this.url = url.trim();
		return visit();
	}
	
	public WebDriver visit(){
		logCenter("Running Script: "+this.getClass().toString());
		log("Start "+browser+" browser...");
		log("Environment:  "+url);
		if (browser.toLowerCase().equals("ff")) {
			driver = new FirefoxDriver();
		} else if (browser.toLowerCase().equals("ie")) {
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer(); 
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	        System.setProperty("webdriver.ie.driver",getDriverPath("IEDriverServer.exe"));
			driver = new InternetExplorerDriver();			
		}else if (browser.toLowerCase().equals("chrome")) {
	        System.setProperty("webdriver.chrome.driver",getDriverPath("chromedriver.exe"));
			driver = new ChromeDriver();
		}
		log("Check url are available");
		log("URL:"+url);
		if (driver != null && !url.equals("")) {
			InputStream is = null;
			try {
				URL u = new URL(url);
				is = u.openStream();
			} catch (Exception e1) {
				System.out.println("-----------Invalid URL---:" + url);
				if (is != null)
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				return null;
			}
			//driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			driver.get(url);
			log("Sleeping "+Environment.WAITTIMESMIN+"ms");
			try {
				Thread.sleep(Environment.WAITTIMESMIN);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return driver;
	}
	
	public String getDriverPath(String browser)
	{
		String path = SeleniumTestLib.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1, path.length()-4)+"BrowserDriver"+"/"+browser;
		path = path.replace("%20", " ");
		return path;
	}
	public String getScreenShotPath(String screenShotName)
	{
		String path = SeleniumTestLib.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = path.substring(1, path.length()-4)+"Screenshot"+"/"+screenShotName;
		path = path.replace("%20", " ");
		return path;
	}

	public void quit() {
		
		if (!driverClosed)
		{	
			log("Close Browser");
			logCenter("End");
			driverClosed = true;
			driver.close();	
			driver.quit();
		}
		
	}
	
    public void takeScreenShot(){
    	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    	try {
    		screenShotFile = getScreenShotPath((new Date()).toString().replace(" ", "").replace(":","")+".png");
			FileUtils.copyFile(scrFile, new File(screenShotFile));
			logWithColor("Script Failed....");
			logWithColor("Screen shot: "+screenShotFile);
			quit();
		} 
    	catch (IOException e) {
			e.printStackTrace();
		}
    }
    public void takeScreenShot(Exception ee){
    	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    	try {
    		if (ee!=null) logWithColor(ee.toString());
    		screenShotFile = getScreenShotPath((new Date()).toString().replace(" ", "").replace(":","")+".png");
			FileUtils.copyFile(scrFile, new File(screenShotFile));
			logWithColor("Script Failed....");
			logWithColor("Screen shot: "+screenShotFile);
			quit();
		} 
    	catch (IOException e) {
			e.printStackTrace();
		}
    }
      
    public void click(By by) {
    	try {
    		log("click "+by.toString());
        	highlight(by);
            driver.findElement(by).click();  
		} catch (Exception e) {
			takeScreenShot(e);
		}	
    }  
      
    public void doubleClick(By by){  
    	try{
    		log("double click "+by.toString());
    		highlight(by);
            new Actions(driver).doubleClick(driver.findElement(by)).perform();  
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    	
    }  
      
    public void contextMenu(By by) {
    	try{
    		log("click context "+by);
    		highlight(by);
            new Actions(driver).contextClick(driver.findElement(by)).perform();  
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  
       
    public void clickAt(By by,String coordString) {
    	try{   		
    		int index = coordString.trim().indexOf(',');  
            int xOffset = Integer.parseInt(coordString.trim().substring(0, index));  
            int yOffset = Integer.parseInt(coordString.trim().substring(index+1));  
            log("click at point: "+by.toString()+" X:"+xOffset+" Y:"+yOffset);
            new Actions(driver).moveToElement(driver.findElement(by), xOffset, yOffset).click().perform();  
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  
      
    public void doubleClickAt(By by,String coordString){  
    	try{
            int index = coordString.trim().indexOf(',');  
            int xOffset = Integer.parseInt(coordString.trim().substring(0, index));  
            int yOffset = Integer.parseInt(coordString.trim().substring(index+1));
            log("double click at point: "+by.toString()+" X:"+xOffset+" Y:"+yOffset);
            new Actions(driver).moveToElement(driver.findElement(by), xOffset, yOffset)  
                                .doubleClick(driver.findElement(by))  
                                .perform();  
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  
      
    public void contextMenuAt(By by,String coordString) {  
    	try{
            int index = coordString.trim().indexOf(',');  
            int xOffset = Integer.parseInt(coordString.trim().substring(0, index));  
            int yOffset = Integer.parseInt(coordString.trim().substring(index+1)); 
            log("click at content: "+by.toString()+" X:"+xOffset+" Y:"+yOffset);
            new Actions(driver).moveToElement(driver.findElement(by), xOffset, yOffset)  
                                .contextClick(driver.findElement(by))  
                                .perform();  
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  
      
    public void fireEvent(By by,String eventName) {  
    	try{
    		log("------no realize---------");
    	}catch(Exception e){
    		takeScreenShot(e);
    	}  
    }  
      
    public void focus(By by) {  
    	try{
    		log("------no realize---------");
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    } 
      
    public void keyPress(By by,Keys theKey) {  
    	try{
    		log("key"+theKey.toString()+" press on: "+by.toString());
    		new Actions(driver).keyDown(driver.findElement(by), theKey).release().perform();       
    	}catch(Exception e){
    		takeScreenShot(e);
    	}        
    }  
      
    public void shiftKeyDown() { 
    	try{
    		log("shift Key Down");
    		new Actions(driver).keyDown(Keys.SHIFT).perform();    
    	}catch(Exception e){
    		takeScreenShot(e);
    	}  
    }  
      
    public void shiftKeyUp() { 
    	try{
    		log("shift Key up");
    		new Actions(driver).keyDown(Keys.SHIFT).perform();    
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  
      
    public void metaKeyDown() {
    	try{
    		log("meta key down");
            new Actions(driver).keyDown(Keys.META).perform();     
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  

    public void metaKeyUp() {  
        try{
    		log("meta key up");
    		new Actions(driver).keyUp(Keys.META).perform();     
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  

    public void altKeyDown() {  
    	try{
    		log("alt key down");
    		new Actions(driver).keyDown(Keys.ALT).perform();
    	}catch(Exception e){
    		takeScreenShot(e);
    	}         
    }  

    public void altKeyUp() { 
    	try{
    		log("alt key up");
            new Actions(driver).keyUp(Keys.ALT).perform();
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  

    public void controlKeyDown() {
    	try{
    		log("control key down");
    		new Actions(driver).keyDown(Keys.CONTROL).perform();
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  

    public void controlKeyUp() {
    	try{
    		log("control key up");
    		new Actions(driver).keyUp(Keys.CONTROL).perform();
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  
      
    public void KeyDown(Keys theKey) {
    	try{
    		log("key down "+theKey.toString());
            new Actions(driver).keyDown(theKey).perform();  
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  
    public void KeyDown(By by,Keys theKey){ 
    	try{
    		log("key "+theKey.toString()+" down "+by.toString());
    		new Actions(driver).keyDown(driver.findElement(by), theKey).perform();   
    	}catch(Exception e){
    		takeScreenShot(e);
    	} 
    }  
      
    public void KeyUp(Keys theKey){
    	try{
    		log("key up "+theKey.toString());
    		new Actions(driver).keyUp(theKey).perform(); 
    	}catch(Exception e){
    		takeScreenShot(e);
    	}  
    }  
      
    public void KeyUp(By by,Keys theKey){
    	try{
    		log("key "+theKey.toString()+" up "+by.toString());
    		new Actions(driver).keyUp(driver.findElement(by), theKey).perform();
    	}catch(Exception e){
    		takeScreenShot(e);
    	}           
    }  
      
    public void mouseOver(By by) {
    	try{
    		log("mouse over"+ by.toString());
    		new Actions(driver).moveToElement(driver.findElement(by)).perform(); 
    	}catch(Exception e){
    		takeScreenShot(e);
    	}     
    }  
      
    public void mouseOut(By by) {  
    	try{
    		log("mouse out"+ by.toString());
    		new Actions(driver).moveToElement((driver.findElement(by)), -10, -10).perform();  
    	}catch(Exception e){
    		takeScreenShot(e);
    	}  
    }  
      
    public void mouseDown(By by) {
    	try{
    		log("mouse down"+ by.toString());
    		new Actions(driver).clickAndHold(driver.findElement(by)).perform();  
    	}catch(Exception e){
    		takeScreenShot(e);
    	}     
    }  
      
    public void mouseDownRight(By by) {  
    	try{
    		log("mouse down right"+ by.toString());
    		new Actions(driver).clickAndHold(driver.findElement(by)).perform();  
    	}catch(Exception e){
    		takeScreenShot(e);
    	} 
    }  
      
    public void mouseDownAt(By by,String coordString) { 
    	try{
    		log("------no realize---------�?");
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  

    public void mouseDownRightAt(By by,String coordString) {  
    	try{
    		log("------no realize---------�?");
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  

    public void mouseUp(By by) {  
    	try{
    		log("------no realize---------�?");
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  

    public void mouseUpRight(By by) {  
    	try{
    		log("------no realize---------�?");
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  

    public void mouseUpAt(By by,String coordString) {  
    	try{
    		log("------no realize---------�?");
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  

    public void mouseUpRightAt(By by,String coordString) {  
    	try{
    		log("------no realize---------�?");
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  

    public void mouseMove(By by) { 
    	try{
    		log("mouse move to: "+by.toString());
    		 new Actions(driver).moveToElement(driver.findElement(by)).perform();  
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  

    public void mouseMoveAt(By by,String coordString) {  
    	try{
            int index = coordString.trim().indexOf(',');  
            int xOffset = Integer.parseInt(coordString.trim().substring(0, index));  
            int yOffset = Integer.parseInt(coordString.trim().substring(index+1));  
            log("mouse move at: "+by.toString()+"xOffset:"+xOffset+"yOffset:"+yOffset);
            new Actions(driver).moveToElement(driver.findElement(by),xOffset,yOffset).perform();  
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  
    public void type(By by, String testdata) {      	
    	try {
    		log("Type: "+testdata+" to field: "+by.toString());
    		highlight(by);
            driver.findElement(by).clear();   
            driver.findElement(by).sendKeys(testdata);  
		} catch (Exception e) {
			takeScreenShot(e);
		}
    	
    }  

    public void setSpeed(String value) {  
         System.out.println("The methods to set the execution speed in WebDriver were deprecated");  
    }  

    public String getSpeed() {  
        System.out.println("The methods to set the execution speed in WebDriver were deprecated");  
        return null;  
          
    }  
    public void check(By by) {  
    	try{
    		log("check "+by.toString());
            if(!isChecked(by))  
                click(by); 
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  

    public void uncheck(By by) {    
    	try{
    		log("uncheck "+by.toString());
            if(isChecked(by))  
                click(by); 
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  
      
    public void select(By by,String optionValue) {
    	try{
    		log("select "+by.toString()+" value:"+optionValue);
    		new Select(driver.findElement(by)).selectByValue(optionValue);  
    	}catch(Exception e){
    		takeScreenShot(e);
    	} 
    }  
      
    public void select(By by,int index) {  
    	try{
    		log("select "+by.toString()+" index:"+index);
    		new Select(driver.findElement(by)).selectByIndex(index);  
    	}catch(Exception e){
    		takeScreenShot(e);
    	} 
    }  

    public void addSelection(By by,String optionValue) { 
        select(by,optionValue);  
    }  
    public void addSelection(By by,int index) {  
        select(by,index);  
    }  
      
    public void removeSelection(By by,String value) {
    	try{
    		log("remove from "+by.toString()+" value:"+value);
    		new Select(driver.findElement(by)).deselectByValue(value); 
    	}catch(Exception e){
    		takeScreenShot(e);
    	}         
    }  
      
    public void removeSelection(By by,int index) {  
    	try{
    		log("remove from "+by.toString()+" index:"+index);
    		new Select(driver.findElement(by)).deselectByIndex(index); 
    	}catch(Exception e){
    		takeScreenShot(e);
    	}  
    }  

    public void removeAllSelections(By by) {  
    	try{
    		log("remove all from "+by.toString());
    		new Select(driver.findElement(by)).deselectAll(); 
    	}catch(Exception e){
    		takeScreenShot(e);
    	}  
    }  
      
    public void submit(By by) { 
    	try{
    		log("submit "+by.toString());
    		driver.findElement(by).submit();  
    	}catch(Exception e){
    		takeScreenShot(e);
    	} 
    }  
      
    public void open(String url) {  
    	try{
    		log("open "+url);
    		 driver.get(url);  
    	}catch(Exception e){
    		takeScreenShot(e);
    	} 
    }  
      
    public void openWindow(String url,String handler) {  
        System.out.println("------no realize---------�?");  
    }  

    public void selectWindow(String handler) {  
    	try{
    		log("select window "+handler);
    		driver.switchTo().window(handler);  
    	}catch(Exception e){
    		takeScreenShot(e);
    	} 
    }  
      
    public String getCurrentHandler(){  
    	try{
    		log("get current handler ");
            String currentHandler = driver.getWindowHandle();  
            return currentHandler;
    	}catch(Exception e){
    		takeScreenShot(e);
    		return "";
    	} 
    }  
      
    public String getSecondWindowHandler(){  
    	try{
    		log("get second window handler");
            Set<String> handlers = driver.getWindowHandles();  
            String reHandler = getCurrentHandler();  
            for(String handler : handlers){  
                if(reHandler.equals(handler))  continue;  
                reHandler = handler;  
            }  
            return reHandler; 
    	}catch(Exception e){
    		takeScreenShot(e);
    		return "";
    	} 
 
    }  
      
    public void selectPopUp(String handler) {  
        driver.switchTo().window(handler);  
    }  
      
    public void selectPopUp() {  
        driver.switchTo().window(getSecondWindowHandler());  
    }  
      
    public void deselectPopUp() {  
        driver.switchTo().window(getCurrentHandler());  
    }  
      
    public void selectFrame(int index) {  
        driver.switchTo().frame(index);  
    }  
      
    public void selectFrame(String str) {  
        driver.switchTo().frame(str);  
    }  
      
    public void selectFrame(By by) {  
        driver.switchTo().frame(driver.findElement(by));  
    }  
    public void waitForPopUp(String windowID,String timeout) {  
        System.out.println("------no realize---------");  
    }  
    public void accept(){  
        driver.switchTo().alert().accept();  
    }  
    public void dismiss(){  
        driver.switchTo().alert().dismiss();  
    }  
    public void chooseCancelOnNextConfirmation() {  
        driver.switchTo().alert().dismiss();  
    }  
      
    public void chooseOkOnNextConfirmation() {  
        driver.switchTo().alert().accept();  
    }  

    public void answerOnNextPrompt(String answer) {  
        driver.switchTo().alert().sendKeys(answer);  
    }  
      
    public void goBack() {  
        driver.navigate().back();  
    }  

    public void refresh() {  
        driver.navigate().refresh();  
    }  
      
    public void forward() {  
        driver.navigate().forward();  
    }  
      
    public void to(String urlStr){  
        driver.navigate().to(urlStr);  
    }  
      
    public void close() {  
        driver.close();  
    }  
      
    public boolean isAlertPresent() {  
        Boolean b = true;  
        try{  
            driver.switchTo().alert();  
        }catch(Exception e){  
            b = false;  
        }  
        return b;  
    }  

    public boolean isPromptPresent() {  
        return isAlertPresent();  
    }  

    public boolean isConfirmationPresent() {  
        return isAlertPresent();  
    }  

    public String getAlert() {  
        return driver.switchTo().alert().getText();  
    }  

    public String getConfirmation() {  
        return getAlert();  
    }  

    public String getPrompt() {  
        return getAlert();  
    }  

    public String getLocation() {  
        return driver.getCurrentUrl();  
    }  
      
    public String getTitle(){
    	try{
    		log("get title:"+driver.getTitle());
    		return driver.getTitle(); 
    	}catch(Exception e){
    		takeScreenShot(e);
    		return null;
    	}
    }  
      
    public String getBodyText() {  
        String str = "";  
        List<WebElement> elements = driver.findElements(By.xpath("//body//*[contains(text(),*)]"));  
        for(WebElement e : elements){  
            str += e.getText()+" ";  
        }  
         return str;  
    }  

    public WebElement getElement(By by) { 
    	try{
    		log("get element:"+ by.toString());
    		highlight(by);
            return driver.findElement(by);  
    	}catch(Exception e){
    		takeScreenShot(e);
    		return null;
    	}
    }  
    public List<WebElement> getElements(By by) { 
    	highlight(by);
        return driver.findElements(by);  
    } 
    public String getValue(By by) {  
    	highlight(by);
        return driver.findElement(by).getAttribute("value");  
    }  

    public String getText(By by) {   
        try{
    		log("get text:"+ by.toString());
    		log("Text:"+driver.findElement(by).getText());
    		highlight(by);
            return driver.findElement(by).getText(); 
    	}catch(Exception e){
    		takeScreenShot(e);
    		return null;
    	}
    }  

    public void highlight(By by) {
//        WebElement element = driver.findElement(by);
//        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border=\"5px solid yellow\"",element); //arguments[0].style.border='5px solid yellow'
//		String originalStyle = element.getAttribute("style");
//    	JavascriptExecutor j = (JavascriptExecutor) driver;
//    	j.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
//    	try {
//    	Thread.sleep(3000);
//    	}
//    	catch (InterruptedException e) {}
//    	js.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", element); 
    }  

    public Object getEval(String script,Object... args) {  
        return ((JavascriptExecutor)driver).executeScript(script,args);  
    }  
    public Object getAsyncEval(String script,Object... args){  
        return  ((JavascriptExecutor)driver).executeAsyncScript(script, args);  
    }  
    public boolean isChecked(By by) {  
        return driver.findElement(by).isSelected();  
    }  
    public String getTable(By by,String tableCellAddress) {  
        WebElement table = driver.findElement(by);  
        int index = tableCellAddress.trim().indexOf('.');  
        int row =  Integer.parseInt(tableCellAddress.substring(0, index));  
        int cell = Integer.parseInt(tableCellAddress.substring(index+1));  
         List<WebElement> rows = table.findElements(By.tagName("tr"));  
         WebElement theRow = rows.get(row);  
         String text = getCell(theRow, cell);  
         return text;  
    }  
    private String getCell(WebElement Row,int cell){  
         List<WebElement> cells;  
         String text = null;  
         if(Row.findElements(By.tagName("th")).size()>0){  
            cells = Row.findElements(By.tagName("th"));  
            text = cells.get(cell).getText();  
         }  
         if(Row.findElements(By.tagName("td")).size()>0){  
            cells = Row.findElements(By.tagName("td"));  
            text = cells.get(cell).getText();  
         }  
        return text;  
           
    }  

    public String[] getSelectedLabels(By by) {  
            Set<String> set = new HashSet<String>();  
            List<WebElement> selectedOptions = new Select(driver.findElement(by))  
                                                                            .getAllSelectedOptions();  
            for(WebElement e : selectedOptions){  
                set.add(e.getText());  
            }  
        return set.toArray(new String[set.size()]);  
    }  

    public String getSelectedLabel(By by) {  
        return getSelectedOption(by).getText();  
    }  

    public String[] getSelectedValues(By by) {  
        Set<String> set = new HashSet<String>();  
        List<WebElement> selectedOptions = new Select(driver.findElement(by))  
                                                                        .getAllSelectedOptions();  
        for(WebElement e : selectedOptions){  
            set.add(e.getAttribute("value"));  
        }  
    return set.toArray(new String[set.size()]);  
    }  

    public String getSelectedValue(By by) {  
        return getSelectedOption(by).getAttribute("value");  
    }  

    public String[] getSelectedIndexes(By by) {  
        Set<String> set = new HashSet<String>();  
        List<WebElement> selectedOptions = new Select(driver.findElement(by))  
                                                                        .getAllSelectedOptions();  
       List<WebElement> options = new Select(driver.findElement(by)).getOptions();  
        for(WebElement e : selectedOptions){  
            set.add(String.valueOf(options.indexOf(e)));  
        }  
        return set.toArray(new String[set.size()]);  
    }  

    public String getSelectedIndex(By by) {  
        List<WebElement> options = new Select(driver.findElement(by)).getOptions();  
        return String.valueOf(options.indexOf(getSelectedOption(by)));  
    }  

    public String[] getSelectedIds(By by) {  
        Set<String> ids = new HashSet<String>();  
        List<WebElement> options = new Select(driver.findElement(by)).getOptions();  
        for(WebElement option : options){  
            if(option.isSelected()) {  
                ids.add(option.getAttribute("id")) ;  
            }  
        }  
        return ids.toArray(new String[ids.size()]);  
    }  

    public String getSelectedId(By by) {  
        return getSelectedOption(by).getAttribute("id");  
    }  
    private WebElement getSelectedOption(By by){  
        WebElement selectedOption = null;  
        List<WebElement> options = new Select(driver.findElement(by)).getOptions();  
        for(WebElement option : options){  
            if(option.isSelected()) {  
                selectedOption = option;  
            }  
        }  
        return selectedOption;  
    }  
      
    public boolean isSomethingSelected(By by) {  
        boolean b = false;  
        List<WebElement> options = new Select(driver.findElement(by)).getOptions();  
        for(WebElement option : options){  
            if(option.isSelected()) {  
                b = true ;  
                break;  
            }  
        }  
        return b;  
    }  

    public String[] getSelectOptions(By by) {  
    	try{
    		log("get select options:"+by.toString());
            Set<String> set = new HashSet<String>();  
            List<WebElement> options = new Select(driver.findElement(by)).getOptions();  
            for(WebElement e : options){  
                set.add(e.getText());  
            }  
            return set.toArray(new String[set.size()]);  
    	}catch(Exception e){
    		takeScreenShot(e);
    		return null;
    	}

    }  
    public String getAttribute(By by,String attributeLocator) {  
    	try{
    		log("get attribute :"+by.toString()+"attributelocator:"+attributeLocator);
    		return driver.findElement(by).getAttribute(attributeLocator);  
    	}catch(Exception e){
    		takeScreenShot(e);
    		return "";
    	}
    }  

    public boolean isTextPresent(String pattern) {  
    	log("is Text Present? "+pattern);
        String Xpath= "//*[contains(text(),\'"+pattern+"\')]" ;  
        try {   
            driver.findElement(By.xpath(Xpath));  
            return true;   
        } catch (NoSuchElementException e) {   
            return false;   
        }     
    }  

    public boolean isElementPresent(By by) {  
    	try{
    		log("Element is ElementPresent? " +(driver.findElements(by).size() > 0));
    		 return driver.findElements(by).size() > 0;  
    	}catch(Exception e){
    		takeScreenShot(e);
    		return false;
    	}
    }  

    public boolean isVisible(By by) {  
    	try{
    		log("Element is isDisplayed? " +driver.findElement(by).isDisplayed());
    		return driver.findElement(by).isDisplayed();  
    	}catch(Exception e){
    		takeScreenShot(e);
    		return false;
    	}
    }  

    public boolean isEditable(By by) {  
    	try{
    		log("Element is editable? " +driver.findElement(by).isEnabled());
    		return driver.findElement(by).isEnabled();
    	}catch(Exception e){
    		takeScreenShot(e);
    		return false;
    	}
    }  
    public List<WebElement> getAllButtons() {  
        return driver.findElements(By.xpath("//input[@type='button']"));              
    }  

    public List<WebElement> getAllLinks() {  
        return driver.findElements(By.tagName("a"));  
    }     

    public List<WebElement> getAllFields() {  
        return driver.findElements(By.xpath("//input[@type='text']"));  
    }  

    public String[] getAttributeFromAllWindows(String attributeName) {  
        System.out.println("");  
        return null;  
    }  
    public void dragdrop(By by,String movementsString) { 
    	try{
    		log("drag element:"+by.toString()+" movements:"+movementsString);
    		dragAndDrop(by, movementsString);  
    	}catch(Exception e){
    		takeScreenShot(e);
    	}
    }  
    public void dragAndDrop(By by,String movementsString) { 
        int index = movementsString.trim().indexOf('.');  
        int xOffset = Integer.parseInt(movementsString.substring(0, index));  
        int yOffset = Integer.parseInt(movementsString.substring(index+1));
        WebElement webElement = this.getElement(by);
        new Actions(driver).dragAndDropBy(webElement, 0, 0).perform();
        new Actions(driver).dragAndDropBy(webElement, xOffset, yOffset).perform();
    }
    public void dragAndMoveAndDrop(By by,String movementsString) {  
        int index = movementsString.trim().indexOf('.');  
        int xOffset = Integer.parseInt(movementsString.substring(0, index));  
        int yOffset = Integer.parseInt(movementsString.substring(index+1));  
        new Actions(driver).dragAndDropBy(driver.findElement(by), 0, 0).perform();
        new Actions(driver).moveByOffset(xOffset, yOffset).perform();
    }
    public void setMouseSpeed(String pixels) {  
        System.out.println("not support");  
    }  

    public Number getMouseSpeed() {  
        System.out.println("not support");  
        return null;  
    }  
    public void dragAndDropToObject(By source,By target) {  
        new Actions(driver).dragAndDrop(driver.findElement(source), driver.findElement(target)).perform();  
    }  

    public void windowFocus() {  
        driver.switchTo().defaultContent();  
    }  

    public void windowMaximize() {  
        driver.manage().window().setPosition(new Point(0,0));  
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();  
        Dimension dim = new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());  
        driver.manage().window().setSize(dim);  
    }  

    public String[] getAllWindowIds() {  
        System.out.println("not realize!");  
        return null;  
    }  

    public String[] getAllWindowNames() {  
        System.out.println("not realize!");  
        return null;  
    }  

    public String[] getAllWindowTitles() {  
        Set<String> handles = driver.getWindowHandles();  
        Set<String> titles = new HashSet<String>();  
        for(String handle : handles){  
            titles.add(driver.switchTo().window(handle).getTitle());  
        }  
        return titles.toArray(new String[titles.size()]);  
    }  
    public String getHtmlSource() {  
        return driver.getPageSource();  
    }  

    public void setCursorPosition(String locator,String position) {  
        System.out.println("not realize�?");  
    }  

    public Number getElementIndex(String locator) {  
        System.out.println("not realize�?");  
        return null;  
    }  

    public Object isOrdered(By by1,By by2) {  
        System.out.println("not realize�?");  
        return null;  
    }  

    public Number getElementPositionLeft(By by) {  
        return driver.findElement(by).getLocation().getX();  
    }  

    public Number getElementPositionTop(By by) {  
        return driver.findElement(by).getLocation().getY();  
    }  

    public Number getElementWidth(By by) {  
        return driver.findElement(by).getSize().getWidth();  
    }  

    public Number getElementHeight(By by) {  
        return driver.findElement(by).getSize().getHeight();  
    }  

    public Number getCursorPosition(String locator) {  
        System.out.println("not realize�?");  
        return null;  
    }  

    public String getExpression(String expression) {  
        System.out.println("not realize�?");  
        return null;  
    }  

    public Number getXpathCount(By xpath) {  
        return driver.findElements(xpath).size();  
    }  

    public void assignId(By by,String identifier) {  
        System.out.println("�?想实现�?");  
    }  

    /*public void allowNativeXpath(String allow) {  
        commandProcessor.doCommand("allowNativeXpath", new String[] {allow,});  
    }*/  

    /*public void ignoreAttributesWithoutValue(String ignore) { 
        commandProcessor.doCommand("ignoreAttributesWithoutValue", new String[] {ignore,}); 
         
    }*/  

    public void waitForCondition(String script,String timeout,Object... args) {  
        Boolean b = false;  
        int time = 0;  
        while(time <= Integer.parseInt(timeout)){  
            b = (Boolean) ((JavascriptExecutor)driver).executeScript(script,args);  
            if(b==true) break;  
            try {  
                Thread.sleep(1000);  
            } catch (InterruptedException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
            time += 1000;  
        }     
    }  

    public void setTimeout(String timeout) {  
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(timeout), TimeUnit.SECONDS);  
    }  

    public void waitForPageToLoad(String timeout) {  
        driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(timeout), TimeUnit.SECONDS);  
    }  

    public void waitForFrameToLoad(String frameAddress,String timeout) {  
        /*driver.switchTo().frame(frameAddress) 
                            .manage() 
                            .timeouts() 
                            .pageLoadTimeout(Integer.parseInt(timeout), TimeUnit.SECONDS);*/  
    }  

    public String getCookie() {  
        String cookies = "";  
        Set<Cookie> cookiesSet = driver.manage().getCookies();  
        for(Cookie c : cookiesSet){  
            cookies += c.getName()+"="+c.getValue()+";";  
            }  
        return cookies;  
    }  

    public String getCookieByName(String name) {  
        return driver.manage().getCookieNamed(name).getValue();  
    }  

    public boolean isCookiePresent(String name) {  
        boolean b = false ;  
        if(driver.manage().getCookieNamed(name) != null || driver.manage().getCookieNamed(name).equals(null))  
            b = true;  
        return b;  
    }  

    public void createCookie(Cookie c) {  
          
        driver.manage().addCookie(c);  
    }  

    public void deleteCookie(Cookie c) {  
        driver.manage().deleteCookie(c);  
    }  

    public void deleteAllVisibleCookies() {  
        driver.manage().getCookieNamed("fs").isSecure();  
    }  

    /*public void setBrowserLogLevel(String logLevel) { 
         
    }*/  

    /*public void runScript(String script) { 
        commandProcessor.doCommand("runScript", new String[] {script,}); 
    }*/  

    /*public void addLocationStrategy(String strategyName,String functionDefinition) { 
        commandProcessor.doCommand("addLocationStrategy", new String[] {strategyName,functionDefinition,}); 
    }*/  

    /*public void rollup(String rollupName,String kwargs) { 
        commandProcessor.doCommand("rollup", new String[] {rollupName,kwargs,}); 
    } 

    public void addScript(String scriptContent,String scriptTagId) { 
        commandProcessor.doCommand("addScript", new String[] {scriptContent,scriptTagId,}); 
    } 

    public void removeScript(String scriptTagId) { 
        commandProcessor.doCommand("removeScript", new String[] {scriptTagId,}); 
    } 

    public void useXpathLibrary(String libraryName) { 
        commandProcessor.doCommand("useXpathLibrary", new String[] {libraryName,}); 
    } 

    public void setContext(String context) { 
        commandProcessor.doCommand("setContext", new String[] {context,}); 
    }*/  

    /*public void attachFile(String fieldLocator,String fileLocator) { 
        commandProcessor.doCommand("attachFile", new String[] {fieldLocator,fileLocator,}); 
    }*/  

    /*public void captureScreenshot(String filename) { 
        commandProcessor.doCommand("captureScreenshot", new String[] {filename,}); 
    }*/  

    public String captureScreenshotToString() {  
         String screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);  
         return screen;  
    }  

   /* public String captureNetworkTraffic(String type) { 
        return commandProcessor.getString("captureNetworkTraffic", new String[] {type}); 
    } 
*/  
    /*public void addCustomRequestHeader(String key, String value) { 
        commandProcessor.getString("addCustomRequestHeader", new String[] {key, value}); 
    }*/  

    /*public String captureEntirePageScreenshotToString(String kwargs) { 
        return commandProcessor.getString("captureEntirePageScreenshotToString", new String[] {kwargs,}); 
    }*/  

    public void shutDown() {  
        driver.quit();  
    }  

    /*public String retrieveLastRemoteControlLogs() { 
        return commandProcessor.getString("retrieveLastRemoteControlLogs", new String[] {}); 
    }*/  

    public void keyDownNative(Keys keycode) {  
        new Actions(driver).keyDown(keycode).perform();  
    }  

    public void keyUpNative(Keys keycode) {  
        new Actions(driver).keyUp(keycode).perform();  
    }  

    public void keyPressNative(String keycode) {  
        new Actions(driver).click().perform();  
    }  
          

    public void waitForElementPresent(By by) {  
        for(int i=0; i<60; i++) {  
        if (isElementPresent(by)) {  
        break;  
        } else {  
			try {
					Thread.sleep(Environment.WAITTIMESMIN);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
            }  
        }  
    }  
      

    public void clickAndWaitForElementPresent(By by, By waitElement) {  
    	try{
    		log("click "+by.toString()+" wait for element present:"+ waitElement.toString());
        	highlight(by);
            click(by);  
            waitForElementPresent(waitElement);  
    	}catch(Exception e){
    		takeScreenShot(e);
    	}

    }  
    public void clickAndWaitPageLoad(By by) {
    	try {
    		log("click and wait page load: "+by.toString());
    		highlight(by);
            click(by);  
	            try {
	    			Thread.sleep(Environment.WAITTIMESMIN);
	    		} catch (InterruptedException e) {
	    			e.printStackTrace();
	    		}
		} catch (Exception e) {
			takeScreenShot(e);
		}    	       
    }  
      
      
    public Boolean VeryTitle(String expect,String actual){  
        if(expect.equals(actual)) return true;  
        else return false;  
    }

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
    public void log(String log)
    {
    	//System.out.println((new Date()).toString()+"-- log: "+log);
    	Reporter.log("<font color='blue'>"+(new Date()).toString()+"-- log: "+"</font>",true);
    	Reporter.log("<font color='green'>"+log+"</font>",true);
    	Reporter.log("<br>",true);
    }  
	
    public void logCenter(String log)
    {
    	//System.out.print("");
    	//System.err.println("-------------------"+log+"-------------------");
    	Reporter.log("<font color='red'><-------------------"+log+"-------------------></font>",true);
    	Reporter.log("<br>",true);
    }
    public void logWithColor(String log)
    {
    	Reporter.log("<font color='blue'>"+(new Date()).toString()+"-- log: "+"</font>",true);
    	Reporter.log("<font color='red'>"+log+"</font>",true);
    	Reporter.log("<br>",true);
    }
    
	public void assertEquals(String expected,String actual)
	{
		logWithColor("Expected: "+expected+" Actually: "+actual);
		if(!expected.equals(actual))
		{
			takeScreenShot(null);
			Assert.assertEquals(expected, actual);
		}
	}
	public void assertEquals(Boolean expected,Boolean actual)
	{
		logWithColor("Expected: "+expected+" Actually: "+actual);
		if(!expected.equals(actual))
		{
			takeScreenShot(null);
			Assert.assertEquals(expected, actual);
		}
	}
	public void assertEquals(int expected,int actual)
	{
		logWithColor("Expected: "+expected+" Actually: "+actual);
		if(!(expected == actual))
		{
			takeScreenShot(null);
			Assert.assertEquals(expected, actual);
		}
	}
	public void assertEquals(float expected,float actual)
	{
		logWithColor("Expected: "+expected+" Actually: "+actual);
		if(!(expected == actual))
		{
			takeScreenShot(null);
			Assert.assertEquals(expected, actual);
		}
	}
	public void assertEquals(Double expected,Double actual)
	{
		logWithColor("Expected: "+expected+" Actually: "+actual);
		if(!(expected == actual))
		{
			takeScreenShot(null);
			Assert.assertEquals(expected, actual);
		}
	}
	public void assertEquals(Object expected,Object actual)
	{
		logWithColor("Expected: "+expected+" Actually: "+actual);
		if(!expected.equals(actual))
		{
			takeScreenShot(null);
			Assert.assertEquals(expected, actual);
		}
	}
	public void assertEquals(String message,Object expected,Object actual)
	{
		logWithColor(message);
		logWithColor("Expected: "+expected+" Actually: "+actual);
		if(!expected.equals(actual))
		{
			takeScreenShot(null);
			Assert.assertEquals(expected, actual);
		}
	}
	public void fail(String message)
	{
		logWithColor(message);
		Assert.fail(message);
		takeScreenShot(null);
	}
	 //Assert not equals
	public void assertNotEquals(String expected,String actual)
	{
		logWithColor("Not equals, Expected: "+expected+" Actually: "+actual);
		if(expected.equals(actual))
		{
			takeScreenShot(null);
			Assert.assertNotEquals(expected, actual);
		}
	}
}
