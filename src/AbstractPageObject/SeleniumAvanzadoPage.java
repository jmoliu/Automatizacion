package AbstractPageObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

import Testing_Pack.RWfile;

public class SeleniumAvanzadoPage extends AbstractPageObject {
	public SeleniumAvanzadoPage(WebDriver driver) {
		super(driver);
	}

	private static String sourceProducts = "Archives/products.txt";
    private static String destProductsFound = "Archives/productsFound.txt";
    private static String sourceAllProducts = "Archives/allProducts.txt";
	
	@Override
	protected String searchProduct() {
        //Busco el producto por su xpath
    	WebElement iphone = driver.findElement(By.xpath("//div[@id='content']/div[2]/div[2]/div/div[1]/a/img"));
    	//Lo clickeo
    	iphone.click();
    	String iPhoneTitle = driver.findElement(By.xpath("//div[2]/div/div/div[1]/div[2]/h1")).getText();
    	return iPhoneTitle;
	}

	@Override
	protected String[] searchFileProducts() {
		//Leo la lista de productos
        String[] products = RWfile.readFile(sourceProducts);
        String[] productsFound = new String[products.length];
 		for (int i=0; i<products.length; i++){
 			//Ingreso el producto a buscar en la barra de busqueda
 			WebElement actualProduct = driver.findElement(By.xpath("//div[@id='search']/input"));
 			actualProduct.sendKeys(products[i]);
 			//Accedo al producto
 			driver.findElement(By.xpath("//header/div/div/div[2]/div/span/button")).click();
 			String pageText = driver.findElement(By.xpath("//div[2]/div/div/div[4]/div/div/div[2]/h4/a")).getText();
 			productsFound[i] = pageText;
 			driver.navigate().back();
 		}
 		return productsFound;
	}

	@Override
	protected File stealDataProducts() {
    	//Hago una busqueda de todos los productos
    	WebElement search = driver.findElement(By.xpath("//div[@id='search']/input"));
        search.sendKeys(" ");
        driver.findElement(By.xpath("//header/div/div/div[2]/div/span/button")).click();
        //Inicializo una lista para almacenar todos los productos encontrados
        List<String> allProducts = new LinkedList<String>();
        //Verifico que exista una siguiente pagina
        while(driver.findElements(By.xpath("//ul[@class='pagination']//a[.='>']")).size() != 0){
        	//Hago una busqueda por la clase de los productos
	        List<WebElement> bar = driver.findElements(By.className("product-thumb"));
	 		for(WebElement current : bar){
	 			//Accedo al nombre
	 			String actualProduct = current.findElement(By.tagName("img")).getAttribute("title");
	 			allProducts.add(actualProduct);
	 		}
	 		//En caso de que exista siguiente pagina, accedo
	 		WebDriverWait waiter = new WebDriverWait(driver, 5000);
	 		waiter.until( ExpectedConditions.presenceOfElementLocated(By.xpath("//ul[@class='pagination']//a[.='>']")) );
	 		driver.findElement(By.xpath("//ul[@class='pagination']//a[.='>']")).click();
	 		
        }
        //Genero un nuevo archivo con los productos encontrados
	 	RWfile.writeFile(destProductsFound, allProducts);
	 	File fAllProducts = new File(sourceAllProducts);
	 	return fAllProducts;
	}

	@Override
	protected String verifyProductsFounded(String productToSearch) {
    	//Busco el producto
    	WebElement search = driver.findElement(By.xpath("//div[@id='search']/input"));
        search.sendKeys(productToSearch);
        driver.findElement(By.xpath("//header/div/div/div[2]/div/span/button")).click();
        //Accedo al producto encontrado
        List<WebElement> bar = driver.findElements(By.className("product-thumb"));
        WebElement pageElement = bar.get(0);
        String elementName = pageElement.findElement(By.tagName("img")).getAttribute("title");
        return elementName;
	}


}