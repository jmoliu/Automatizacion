package AbstractPageObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.google.common.io.Files;

import Testing_Pack.RWfile;

public class SeleniumAvanzadoTest extends BaseTest {
	
	private static String sourceProducts = "Archives/products.txt";
    private static String destProductsFound = "Archives/productsFound.txt";
    private static String sourceAllProducts = "Archives/allProducts.txt";
	
	@Test
    public void parte1() {
    	AbstractPageObject page = new SeleniumAvanzadoPage(driver);
    	String iPhoneTitle = page.searchProduct();
        assertEquals(iPhoneTitle,"iPhone");
    }
	
	@Test
    public void parte2(){
		AbstractPageObject page = new SeleniumAvanzadoPage(driver);
    	String[] productsSearched = page.searchFileProducts();
    	String[] products = RWfile.readFile(sourceProducts);
 		for (int i=0; i<products.length; i++){
 			assertEquals(products[i],productsSearched[i]);
 		}
    }
	
	@Test
    public void parte3(){
		AbstractPageObject page = new SeleniumAvanzadoPage(driver);
		File stolenDataProducts = page.stealDataProducts();
		File fProductsFound = new File(destProductsFound);
	 	try {
	 		//Verifico que el archivo contenga todos los productos del sitio
			assertTrue(Files.equal(stolenDataProducts, fProductsFound));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
    public void parte4(){
		AbstractPageObject page = new SeleniumAvanzadoPage(driver);
		String productVerified = page.verifyProductsFounded("IPhone");
		String[] allProducts = RWfile.readFile(destProductsFound);
		assertTrue(Arrays.asList(allProducts).contains(productVerified));
	}
}
