package id.ac.ui.cs.advprog.eshop.controller;

import java.util.Arrays;
import java.util.List;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @MockitoBean
   private ProductService productService;

   @Test
   void testCreateProductPage() throws Exception {
      mockMvc.perform(get("/product/create"))
              .andExpect(status().isOk())
              .andExpect(view().name("CreateProduct"))
              .andExpect(model().attributeExists("product"));
   }

   @Test
   void testCreateProductPost() throws Exception {
      mockMvc.perform(post("/product/create")
                      .param("productId", "1")
                      .param("productName", "Sampo Cap Bambang")
                      .param("productQuantity", "100"))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrl("list"));

      Mockito.verify(productService).create(any(Product.class));
   }

   @Test
   void testProductListPage() throws Exception {
      Product product = new Product();
      product.setProductId("1");
      product.setProductName("Sampo Cap Bambang");
      product.setProductQuantity(100);

      List<Product> productList = Arrays.asList(product);
      Mockito.when(productService.findAll()).thenReturn(productList);

      mockMvc.perform(get("/product/list"))
              .andExpect(status().isOk())
              .andExpect(view().name("ProductList"))
              .andExpect(model().attributeExists("products"))
              .andExpect(model().attribute("products", productList));
   }

   @Test
   void testEditProductPage() throws Exception {
      Product product = new Product();
      product.setProductId("1");
      Mockito.when(productService.findProductById("1")).thenReturn(product);

      mockMvc.perform(get("/product/edit/1"))
              .andExpect(status().isOk())
              .andExpect(view().name("EditProduct"))
              .andExpect(model().attributeExists("product"));
   }

   @Test
   void testEditProductPost() throws Exception {
      mockMvc.perform(post("/product/edit")
                      .param("productId", "1")
                      .param("productName", "Sampo Cap Budi")
                      .param("productQuantity", "50"))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrl("list"));

      Mockito.verify(productService).editProduct(eq("1"), any(Product.class));
   }

   @Test
   void testDeleteProduct() throws Exception {
      mockMvc.perform(get("/product/delete/1"))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrl("../list"));

      Mockito.verify(productService).deleteProductById("1");
   }

}
