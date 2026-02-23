package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

   @Mock
   private ProductRepository productRepository;

   @InjectMocks
   private ProductServiceImpl productService;

   @Test
   void testEditProduct() {
      String productId = "ac338e9f-1c39-460e-9960-71af6af63bd6";
      Product product = new Product();
      product.setProductName("Sampo Cap Bebek");
      product.setProductQuantity(1000);

      when(productRepository.edit(product)).thenReturn(product);
      productService.editProduct(productId, product);
      assertEquals(productId, product.getProductId());

      verify(productRepository, times(1)).edit(product);
   }

   @Test
   void testDeleteProductByIdPositive() {
      String productId = "product-id-yang-valid";

      productService.deleteProductById(productId);
      verify(productRepository, times(1)).delete(productId);
   }

   @Test
   void testCreateProduct() {
      Product product = new Product();
      product.setProductId("1");
      product.setProductName("Sampo Cap Bango");
      product.setProductQuantity(100);

      when(productRepository.create(product)).thenReturn(product);

      Product savedProduct = productService.create(product);

      assertEquals(product.getProductId(), savedProduct.getProductId());
      verify(productRepository, times(1)).create(product);
   }

   @Test
   void testFindAll() {
      Product product1 = new Product();
      product1.setProductId("1");
      Product product2 = new Product();
      product2.setProductId("2");

      List<Product> productList = new ArrayList<>();
      productList.add(product1);
      productList.add(product2);
      Iterator<Product> iterator = productList.iterator();

      when(productRepository.findAll()).thenReturn(iterator);

      List<Product> result = productService.findAll();

      assertEquals(2, result.size());
      assertEquals("1", result.get(0).getProductId());
      verify(productRepository, times(1)).findAll();
   }

   @Test
   void testFindProductById() {
      Product product = new Product();
      product.setProductId("1");
      product.setProductName("Sampo Cap Bango");

      when(productRepository.findById("1")).thenReturn(product);

      Product result = productService.findProductById("1");

      assertNotNull(result);
      assertEquals("1", result.getProductId());
      assertEquals("Sampo Cap Bango", result.getProductName());
      verify(productRepository, times(1)).findById("1");
   }
}