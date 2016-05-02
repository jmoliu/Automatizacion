package AbstractPageObject;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPageObject {
	protected WebDriver driver;
	protected WebDriverWait wait;

	public AbstractPageObject(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 30);
	}
	
	protected abstract String searchProduct();
	
	protected abstract String[] searchFileProducts();
	
	protected abstract File stealDataProducts();
	
	protected abstract String verifyProductsFounded(String productToSearch);

}