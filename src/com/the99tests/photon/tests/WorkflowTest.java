/*
 * Copyright (c) 2017, Preenos Crowd Technologies Private Limited
 * 
 * Please read instructions below for writing your tests
 */

package com.the99tests.photon.tests;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Sleeper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.the99tests.photon.PhotonSession;
import com.the99tests.photon.platforms.UnsupportedConfigException;

public class WorkflowTest {
	/*
	 * Test setup
	 */
	
	public static RemoteWebDriver driver=null;
	//WebDriver driver =null;
	
	@BeforeMethod
	public void init() throws IOException, TimeoutException, UnsupportedConfigException, InterruptedException {
		if(PhotonSession.isLocal()) {
			/*
			 * TODO: Local/Test playground setup as per your needs(Please refer to the README for more info)
			 * 
			 * For local, it should be something like
			 * 
			 * driver=ChromeDriver()
			 * PhotonSession.setupLocalSession(driver);
			 */			
			
		/*	try {
				
				System.setProperty("webdriver.chrome.driver", "C:\\Users\\kishores\\Downloads\\chromedriver_win32\\chromedriver.exe");
				driver = new ChromeDriver();
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				// TODO: handle exception
			}
		*/
			DesiredCapabilities capability=DesiredCapabilities.chrome();
			 LoggingPreferences logPrefs = new LoggingPreferences();
			 logPrefs.enable(LogType.BROWSER, Level.ALL);
			 capability.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			 capability.setCapability(CapabilityType.PLATFORM, "WINDOWS");
		try{
			 driver = new RemoteWebDriver(new URL("http://52.66.6.164:4444/wd/hub"),capability);
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
			 PhotonSession.setupLocalSession(driver);
			 
		}
		
		else 
		
		{
			PhotonSession.setupPhotonSession();
			driver=PhotonSession.getNativeDriver();
		}	
	}
	
	@AfterMethod
	public void close() {
		PhotonSession.closeSession();
		driver.quit();
	} 
	
	/* 
	 * TODO: Write your tests below
	 */
	
	/*@Test
	public void Test1() throws InterruptedException
	{try{
		driver.get("http://www.amazon.in/");		
		Thread.sleep(2000);
		getscreenshot();
		
	}catch(Exception ex){
		System.out.println(ex.getMessage());
	}
	
	}
	*/
	
	@Test
	public void TC_001_signIn()
	{
		try {
			driver.get("https://www.amazon.com/");
			driver.manage().window().maximize();
			PhotonSession.checkpoint("72_114_6073_27594_amazon_homepage");
			driver.findElement(By.xpath("//span[text()='Hello. Sign in']")).click();
			PhotonSession.checkpoint("72_114_6073_27595_login_page");
			WebElement Email=driver.findElement(By.xpath("//input[@id='ap_email']"));
			WebElement Password=driver.findElement(By.xpath("//input[@id='ap_password']"));
			if(isElementPresent(Email)&(isElementPresent(Password)))
			{
				System.out.println("Email text field and password field is present");
			}
			
			Email.sendKeys("sundevqa@gmail.com");
			PhotonSession.checkpoint("72_114_6073_27596_login_page");
			System.out.println("Email Address entered");
			
			Password.sendKeys("Reset@123");
			PhotonSession.checkpoint("72_114_6073_27597_login_page");
			System.out.println("Password entered");
			WebElement sigInBtn=driver.findElement(By.xpath("//input[@id='signInSubmit']"));
			sigInBtn.click();
			Thread.sleep(3000);
			Actions action = new Actions(driver);
			WebElement Account= driver.findElement(By.xpath("//a[@id='nav-link-accountList']/span[2]"));
			Thread.sleep(3000);
			action.moveToElement(Account).build().perform();
			Thread.sleep(3000);
			WebElement AccountnList=driver.findElement(By.xpath("//div[@id='nav-al-container']"));
			if(isElementPresent(AccountnList))
			{
				PhotonSession.checkpoint("72_114_6073_27598_user_homepage");
				System.out.println("Account List is verified");
			}
			getscreenshot();
			//driver.close();
		} catch (Exception e) {
			System.out.println("Execption caught:"+e.getMessage());
		}
	}
	@Test
	public void TC_002_searchItem()
	{
		try {
			driver.get("https://www.amazon.com/");
			driver.manage().window().maximize();
			PhotonSession.checkpoint("72_115_6074_27599_amazon_homepage");
			WebElement txtSearchbox=driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']"));
			txtSearchbox.click();
			PhotonSession.checkpoint("72_115_6074_27600_search_field");
			txtSearchbox.sendKeys("Dove Soap");
			Thread.sleep(1000);
		     WebElement suggestion=driver.findElement(By.xpath("//div[@id='suggestions']"));
			PhotonSession.checkpoint("72_115_6074_27601_search_result");
			isElementPresent(suggestion);
			Thread.sleep(2000);
			System.out.println("Suggestion list is verified");
			System.out.println("product name entered as  Dove Soap");
			driver.findElement(By.xpath(".//div[@id='nav-search']//input[@class='nav-input' and @value='Go']")).click();
			Thread.sleep(2000);
			PhotonSession.checkpoint("72_115_6074_27602_search_result");	//WebElement imgratings=driver.findElement(By.xpath("//span[@class='a-icon-alt']"));
			WebElement lnkPreviouspage=driver.findElement(By.xpath("//span[@id='pagnPrevString']"));
			WebElement lnkNextpage=driver.findElement(By.xpath("//span[@id='pagnNextString']"));
			if((isElementPresent(lnkPreviouspage))&(isElementPresent(lnkNextpage)))
			{
				PhotonSession.checkpoint("72_115_6074_27603_search_result");
				System.out.println("Ratings and previous and nextpage links are verified");

			}
			PhotonSession.checkpoint("72_115_6075_27605_seach_product");
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@class='a-row a-spacing-top-mini']//a//h2[1]")).click();
			PhotonSession.checkpoint("72_115_6075_27606_product_page");
			WebElement proName=driver.findElement(By.xpath("//span[@id='productTitle']"));
			WebElement proPrice=driver.findElement(By.xpath("//div[@id='snsPrice']/div"));
			WebElement proImage=driver.findElement(By.xpath("//img[@id='landingImage']"));
			WebElement lnkAddtocart=driver.findElement(By.xpath("//span[@class='a-button-inner']//a[@id='a-autoid-3-announce']"));
			if(isElementPresent(proName)&(isElementPresent(proPrice))&(isElementPresent(proImage))&(isElementPresent(lnkAddtocart)))
			{
				System.out.println("Product price,image,name and add to cart button are verified");

			}
			WebElement lnkAddtocartin = driver.findElement(By.xpath("//span[@class='a-button-inner']//a[@id='a-autoid-3-announce']"));
			Actions build = new Actions(driver); 
			build.moveToElement(lnkAddtocartin).build().perform(); 
			PhotonSession.checkpoint("72_115_6075_27607_add_cart");
			lnkAddtocartin.click();
			WebElement txtAddedtocart=driver.findElement(By.xpath("//div[@id='huc-v2-order-row-confirm-text']/h1"));
			WebElement lnkProceed=driver.findElement(By.xpath("//a[@id='hlb-ptc-btn-native']"));
			if(isElementPresent(txtAddedtocart)&(isElementPresent(lnkProceed)))
			{
				PhotonSession.checkpoint("72_115_6075_27608_add_cart");
				System.out.println("Added to Cart confirmation message is verified");
			}
			WebElement txtCart = driver.findElement(By.xpath("//div[@id='hlb-subcart']//span[@class='a-size-medium a-align-center huc-subtotal']/span"));
			System.out.println("Cart item: "+txtCart.getText());
			PhotonSession.checkpoint("72_115_6075_27609_cart_view");
			WebElement txtCartPrice = driver.findElement(By.xpath("//div[@id='hlb-subcart']//span[@class='a-color-price hlb-price a-inline-block a-text-bold']"));
			System.out.println("Cart price:"+txtCartPrice.getText());
			getscreenshot();
			//driver.close();

		} catch (Exception e) {
			System.out.println("Execption caught:"+e.getMessage());
		}

	}
	@Test
	public void TC_003_proceedTocheckout(){
		try{
			driver.get("https://www.amazon.com/");
			driver.manage().window().maximize();
			WebElement signIn=driver.findElement(By.xpath("//span[text()='Hello. Sign in']"));
			signIn.click();
			WebElement Email=driver.findElement(By.xpath("//input[@id='ap_email']"));
			Email.sendKeys("sundevqa@gmail.com");
			System.out.println("Email Address entered");
			WebElement Password=driver.findElement(By.xpath("//input[@id='ap_password']"));
			Password.sendKeys("Reset@123");
			System.out.println("Password entered");
			WebElement sigInBtn=driver.findElement(By.xpath("//input[@id='signInSubmit']"));
			sigInBtn.click();
            PhotonSession.checkpoint("72_115_6075_27604_amazon_login");
			WebElement lnkCart = driver.findElement(By.xpath("//a[@id='nav-cart']"));
			PhotonSession.checkpoint("72_116_6077_27615_cart_view");
			lnkCart.click();
			WebElement lnkProceedtoCart = driver.findElement(By.xpath("//input[@name='proceedToCheckout']"));
			PhotonSession.checkpoint("72_116_6077_27616_proceed_checkout");
			WebElement txtPropricencount = driver.findElement(By.xpath("//p[@class='a-spacing-none a-spacing-top-none']"));
			if(isElementPresent(lnkProceedtoCart)&(isElementPresent(txtPropricencount)))
			{
				System.out.println("Procced to checkout button is present");
			}
			lnkProceedtoCart.click();
			PhotonSession.checkpoint("72_116_6077_27617_shipping_address");
			WebElement txtshippingaddress = driver.findElement(By.xpath("//h1[@class='a-spacing-base']"));
			System.out.println("navigated to:"+txtshippingaddress.getText());
			WebElement lnkShipdsaddress = driver.findElement(By.xpath("//div[@id='address-book-entry-0']/div[2]"));
			if(isElementPresent(lnkShipdsaddress))
			{
				lnkShipdsaddress.click();
			}
			WebElement lstAddress = driver.findElement(By.xpath("//select[@name='addressID.destinationType.destinationId.destinationOwnerId.1']"));
			if(isElementPresent(lstAddress))
			{
				WebElement btnContinue = driver.findElement(By.xpath("//input[@name='continueMultiAddress']"));
				btnContinue.click();
			}
			Thread.sleep(2000);
			getscreenshot();
			//driver.close();
		}catch(Exception e){
			System.out.println("Execption caught:"+e.getMessage());
		}
	}
	
	protected boolean isElementPresent(WebElement element){
		try{
			return element.isDisplayed();
		}catch(NoSuchElementException ex){
			return false;
		}
	}
	
	
	 public void getscreenshot() throws Exception 
     {
		 String path=System.getProperty("user.dir");
             File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
          //The below method will save the screen shot in d drive with name "screenshot.png"
             FileUtils.copyFile(scrFile, new File((path+"\\screenshots\\screenshot.png")));
     }
	
	}


/* 
 * TODO: Submit your tests
 */
