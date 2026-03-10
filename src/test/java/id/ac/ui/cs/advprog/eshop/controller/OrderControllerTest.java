package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OrderControllerTest {

   private MockMvc mockMvc;

   @Mock
   private OrderService orderService;

   @InjectMocks
   private OrderController orderController;

   @BeforeEach
   void setUp() {
      MockitoAnnotations.openMocks(this);
      mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
   }

   @Test
   void testCreateOrderPage() throws Exception {
      mockMvc.perform(get("/order/create"))
              .andExpect(status().isOk())
              .andExpect(view().name("order/createOrder"));
   }

   @Test
   void testHistoryOrderPage() throws Exception {
      mockMvc.perform(get("/order/history"))
              .andExpect(status().isOk())
              .andExpect(view().name("order/orderHistoryForm"));
   }

   @Test
   void testHistoryOrderPost() throws Exception {
      List<Order> orders = new ArrayList<>();
      when(orderService.findAllByAuthor("Safira")).thenReturn(orders);

      mockMvc.perform(post("/order/history").param("author", "Safira"))
              .andExpect(status().isOk())
              .andExpect(model().attributeExists("orders"))
              .andExpect(model().attribute("author", "Safira"))
              .andExpect(view().name("order/orderHistoryList"));
   }

   @Test
   void testPayOrderPage() throws Exception {

      List<Product> products = new ArrayList<>();
      Product product = new Product();
      product.setProductId("prod-1");
      product.setProductName("Kecap");
      product.setProductQuantity(1);
      products.add(product);
      Order order = new Order("order-1", products, 123L, "Safira");

      when(orderService.findById("order-1")).thenReturn(order);

      mockMvc.perform(get("/order/pay/order-1"))
              .andExpect(status().isOk())
              .andExpect(model().attributeExists("order"))
              .andExpect(view().name("order/payOrder"));
   }

   @Test
   void testPayOrderPost() throws Exception {
      mockMvc.perform(post("/order/pay/order-1"))
              .andExpect(status().isOk())
              .andExpect(model().attributeExists("paymentId"))
              .andExpect(view().name("order/paymentSuccess"));
   }
}