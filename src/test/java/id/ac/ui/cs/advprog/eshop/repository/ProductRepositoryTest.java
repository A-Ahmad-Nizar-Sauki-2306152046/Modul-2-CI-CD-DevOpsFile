package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

   @InjectMocks
   ProductRepository productRepository;

   @Test
   void testCreateProductWithoutId() {
      Product product = new Product();
      product.setProductName("Sampo Cap Kuda");
      product.setProductQuantity(200);

      Product savedProduct = productRepository.create(product);

      assertNotNull(savedProduct.getProductId());
      assertEquals("Sampo Cap Kuda", savedProduct.getProductName());
      assertEquals(200, savedProduct.getProductQuantity());
   }

   @Test
   void testCreateAndFind() {
      Product product = new Product();
      product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
      product.setProductName("Sampo Cap Bambang");
      product.setProductQuantity(100);
      productRepository.create(product);

      Iterator<Product> productIterator = productRepository.findAll();
      assertTrue(productIterator.hasNext());
      Product savedProduct = productIterator.next();
      assertEquals(product.getProductId(), savedProduct.getProductId());
      assertEquals(product.getProductName(), savedProduct.getProductName());
      assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
   }

   @Test
   void testFindAllIfEmpty() {
      Iterator<Product> productIterator = productRepository.findAll();
      assertFalse(productIterator.hasNext());
   }

   @Test
   void testFindAllIfMoreThanOneProduct() {
      Product product1 = new Product();
      product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
      product1.setProductName("Sampo Cap Bambang");
      product1.setProductQuantity(100);
      productRepository.create(product1);

      Product product2 = new Product();
      product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
      product2.setProductName("Sampo Cap Usep");
      product2.setProductQuantity(50);
      productRepository.create(product2);

      Iterator<Product> productIterator = productRepository.findAll();
      assertTrue(productIterator.hasNext());
      Product savedProduct = productIterator.next();
      assertEquals(product1.getProductId(), savedProduct.getProductId());
      savedProduct = productIterator.next();
      assertEquals(product2.getProductId(), savedProduct.getProductId());
      assertFalse(productIterator.hasNext());
   }

   @Test
   void testEditProduct() {
      Product product = new Product();
      product.setProductId("eb668e9f-1c39-460e-8860-71af6af63bd6");
      product.setProductName("Sampo Cap Bambang");
      product.setProductQuantity(100);
      productRepository.create(product);

      Product updatedProduct = new Product();
      updatedProduct.setProductId("eb668e9f-1c39-460e-8860-71af6af63bd6");
      updatedProduct.setProductName("Sampo Cap Bebek");
      updatedProduct.setProductQuantity(400);

      Product result = productRepository.edit(updatedProduct);

      assertNotNull(result);
      assertEquals("Sampo Cap Bebek", result.getProductName());
      assertEquals(400, result.getProductQuantity());

      Iterator<Product> iterator = productRepository.findAll();
      assertTrue(iterator.hasNext());
      Product savedProduct = iterator.next();
      assertEquals("Sampo Cap Bebek", savedProduct.getProductName());
   }

   @Test
   void testEditProduct_NotFound() {
      Product productAsli = new Product();
      productAsli.setProductId("id-asli");
      productAsli.setProductName("Sampo Asli");
      productAsli.setProductQuantity(100);
      productRepository.create(productAsli);

      Product productGhoib = new Product();
      productGhoib.setProductId("id-ghoib");
      productGhoib.setProductName("Barang Invisible");
      productGhoib.setProductQuantity(500);

      Product result = productRepository.edit(productGhoib);

      assertNull(result);
   }

   @Test
   void testEditProductQuantityOnly() {
      Product product = new Product();
      product.setProductId("ID-Tetap");
      product.setProductName("Kecap Manis Banget");
      product.setProductQuantity(5);
      productRepository.create(product);

      Product updateRequest = new Product();
      updateRequest.setProductId("ID-Tetap");
      updateRequest.setProductName("Kecap Manis Banget");
      updateRequest.setProductQuantity(100);

      productRepository.edit(updateRequest);

      Product result = productRepository.findById("ID-Tetap");
      assertEquals(100, result.getProductQuantity());
      assertEquals("Kecap Manis Banget", result.getProductName());
      assertEquals("ID-Tetap", result.getProductId());
   }

   @Test
   void testDeleteProduct_VerifyListIntegrity() {
      Product product1 = new Product();
      product1.setProductId("id-1");
      productRepository.create(product1);
      Product product2 = new Product();
      product2.setProductId("id-2");
      productRepository.create(product2);
      Product product3 = new Product();
      product3.setProductId("id-3");
      productRepository.create(product3);
      Product product4 = new Product();
      product4.setProductId("id-4");
      productRepository.create(product4);
      Product product5 = new Product();
      product5.setProductId("id-5");
      productRepository.create(product5);

      productRepository.delete("id-3");
      productRepository.delete("id-5");

      assertNull(productRepository.findById("id-3"));
      assertNull(productRepository.findById("id-5"));

      assertNotNull(productRepository.findById("id-1"));
      assertNotNull(productRepository.findById("id-2"));
      assertNotNull(productRepository.findById("id-4"));

      int remainingProduct = 0;
      Iterator<Product> iterator = productRepository.findAll();
      while (iterator.hasNext()) {
         iterator.next(); remainingProduct++;
      }
      assertEquals(3, remainingProduct);
   }

   @Test
   void testDeleteProductPositive() {
      Product product = new Product();
      product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
      product.setProductName("Sampo Cap Bambang");
      product.setProductQuantity(100);
      productRepository.create(product);

      productRepository.delete(product.getProductId());

      assertNull(productRepository.findById(product.getProductId()));
   }

   @Test
   void testDeleteProductNegative_NotFound() {

      Product product = new Product();
      product.setProductId("id-asli");
      product.setProductName("Sampo Asli");
      productRepository.create(product);

      productRepository.delete("random-id");

      Iterator<Product> iterator = productRepository.findAll();
      assertTrue(iterator.hasNext());
   }
}