package org.assignments;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class InterviewClass {
	
	WebDriver driver;
	WebDriverWait wait;
	
	public static void main(String[] args) throws InterruptedException {
		InterviewClass objIC = new InterviewClass();
		objIC.launchBrowser();	//
		objIC.setPreferences("North America", "United States");
		objIC.clickTab();
		objIC.getProductDetails(4);
	}
	

	public void launchBrowser() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\haile\\Documents\\NvidiaAssignment\\chromedriver.exe");
		driver = new ChromeDriver();
		wait=new WebDriverWait(driver, 20);
		driver.get("https://www.newegg.com/NVIDIA/BrandStore/ID-1441");
		driver.manage().window().maximize();
	}
	
	
	public void setPreferences(String strContinent, String strCountry) throws InterruptedException {

		WebElement objPopUp = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("centerPopup-body")));
		Boolean boolPopUp = objPopUp.isDisplayed();
		
		if (boolPopUp) { 
			System.out.println("Pop up appeared");
			WebElement continent = driver.findElement(By.className("react-slidedown"));
			Boolean continentUL = continent.isDisplayed();
			List <WebElement> continentNames = continent.findElements(By.tagName("li"));
			for (WebElement elementContinent : continentNames)
			{
				if (elementContinent.getText().equals(strContinent)) {
					elementContinent.click();
					Thread.sleep(2000);
					break;
				}
			}
			System.out.println("selected continent : "+strContinent);
			
			WebElement country = driver.findElement(By.cssSelector("div.ReactModal__Overlay.ReactModal__Overlay--after-open div.ReactModal__Content.ReactModal__Content--after-open.centerPopup div.centerPopup-body div.top-country-wrap div.top-country-wrap-inner div.list-box:nth-child(2) div.list-box-inner div.react-slidedown div:nth-child(1) > ul.pure-menu-list.dropdown-list"));
			List <WebElement> countryNames = country.findElements(By.tagName("li"));
			for (WebElement elementCountry : countryNames)
			{
				System.out.println(elementCountry.getText());
				if (elementCountry.getText().equals(strCountry)) {
					elementCountry.click();
					break;
				}
			}
			System.out.println("selected country : "+strCountry);
			objPopUp.findElement(By.xpath("//button[contains(text(),'Stay at United States')]")).click();;
		} else {
			System.out.println("Pop Up did not appear : " +boolPopUp);
		}
	}
	
	
	public void clickTab() throws InterruptedException {
		Thread.sleep(2000);
		WebElement objTab = driver.findElement(By.id("tb_id109"));
		objTab.click();
		//Thread.sleep(3000);
		WebElement searchResultLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("searchCount")));
		if (searchResultLabel.getText().contains("Results")) {
			System.out.println("Search results populated");
		} else {
			System.out.println("Searcg results not populated");
		}
		System.out.println(objTab.getClass().toString());
	}
	
	
	public void getProductDetails(int intVar) throws InterruptedException {
		int counter;
		WebElement productList, productPrice;
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,350)");
		Thread.sleep(3000);
		
		if(intVar!=0) {
			for (counter=1;counter<intVar+1;counter++) {
				productList = driver.findElement(By.xpath("//div[@id='tbc_id109']/child::div[@class='pageItemContainer ']/child::div[@class='pageItem']["+counter+"]/descendant::div[@class='itemContent']/child::div[@class='fullRow'][1]"));
				productPrice = driver.findElement(By.xpath("//div[@id='tbc_id109']/child::div[@class='pageItemContainer ']/child::div[@class='pageItem']["+counter+"]/descendant::div[@class='itemContent']/child::div[@class='fullRow'][2]/descendant::span[@class='itemPriceCharacteristic']"));

				System.out.println(counter+" Product Name fetched : " +productList.getText());
				System.out.println(counter+" Product price fetched : " +productPrice.getText());
			}	
		} else {
			System.out.println("Integer variable passed is 0");
		}
	}
}
