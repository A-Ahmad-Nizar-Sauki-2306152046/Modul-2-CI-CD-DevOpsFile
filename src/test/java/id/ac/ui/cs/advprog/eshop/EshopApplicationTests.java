package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EshopApplicationTests {

   @Test
   void contextLoads() {
      // This method is intentionally empty to check if the Spring application context loads correctly
   }

   @Test
   void testMain() {
      EshopApplication.main(new String[] {});
   }
}
