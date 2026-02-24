package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest extends BaseFunctionalTest {

   @Test
   void testCreateProduct_isCorrect(ChromeDriver driver) {
      driver.get(baseUrl + "/product/create");

      WebElement nameInput = driver.findElement(By.name("productName"));
      nameInput.clear();
      nameInput.sendKeys("Sampo Bala Bala");

      WebElement quantityInput = driver.findElement(By.name("productQuantity"));
      quantityInput.clear();
      quantityInput.sendKeys("1000");

      WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
      submitButton.click();

      String currentUrl = driver.getCurrentUrl();
      String expectedUrl = baseUrl + "/product/list";

      assertTrue(currentUrl.contains("/product/list"));
      assertEquals(expectedUrl, currentUrl);

      String pageSource = driver.getPageSource();
      assertTrue(pageSource.contains("Sampo Bala Bala"));
      assertTrue(pageSource.contains("1000"));
   }
}