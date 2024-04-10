package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RedBusHome {
WebDriver driver;
	
	public RedBusHome(WebDriver driver) {
		this.driver = driver;
	PageFactory.initElements(driver, this);
	}
	
	
	
	@FindBy(id="src")
	WebElement from;
	
	@FindBy(id="dest")
	WebElement to;
	
	
	
	@FindBy(xpath = "//div[@class='labelCalendarContainer']")
	WebElement date;
	
	@FindBy(id = "search_button")
	WebElement search;
	 
	
	public WebElement getFrom() {
		return from;
	}
	public WebElement getTo() {
		return to;
	}
	public WebElement getDate() {
		return date;
	}
	public WebElement getSearch() {
		return search;
	}
	
	 
	 
	
}


