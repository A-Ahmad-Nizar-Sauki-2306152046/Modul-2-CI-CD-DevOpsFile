package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.VoucherPayment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PaymentControllerTest {

   private MockMvc mockMvc;

   @Mock
   private PaymentService paymentService;

   @InjectMocks
   private PaymentController paymentController;

   private Payment payment;

   @BeforeEach
   void setUp() {
      MockitoAnnotations.openMocks(this);
      mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();

      List<Product> products = new ArrayList<>();
      Product product = new Product();
      product.setProductId("prod-1");
      product.setProductName("Kecap");
      product.setProductQuantity(1);
      products.add(product);

      Order order = new Order("order-1", products, 123L, "Safira");
      payment = new VoucherPayment("pay-1", new HashMap<>(), order);
   }

   @Test
   void testPaymentDetailForm() throws Exception {
      mockMvc.perform(get("/payment/detail"))
              .andExpect(status().isOk())
              .andExpect(view().name("payment/paymentDetailForm"));
   }

   @Test
   void testPaymentDetail() throws Exception {
      when(paymentService.getPayment("pay-1")).thenReturn(payment);

      mockMvc.perform(get("/payment/detail/pay-1"))
              .andExpect(status().isOk())
              .andExpect(model().attributeExists("payment"))
              .andExpect(view().name("payment/paymentDetail"));
   }

   @Test
   void testPaymentAdminList() throws Exception {
      List<Payment> payments = new ArrayList<>();
      payments.add(payment);
      when(paymentService.getAllPayments()).thenReturn(payments);

      mockMvc.perform(get("/payment/admin/list"))
              .andExpect(status().isOk())
              .andExpect(model().attributeExists("payments"))
              .andExpect(view().name("payment/paymentList"));
   }

   @Test
   void testPaymentAdminDetail() throws Exception {
      when(paymentService.getPayment("pay-1")).thenReturn(payment);

      mockMvc.perform(get("/payment/admin/detail/pay-1"))
              .andExpect(status().isOk())
              .andExpect(model().attributeExists("payment"))
              .andExpect(view().name("payment/paymentAdminDetail"));
   }

   @Test
   void testSetPaymentStatus() throws Exception {
      when(paymentService.getPayment("pay-1")).thenReturn(payment);
      when(paymentService.setStatus(payment, "SUCCESS")).thenReturn(payment);

      mockMvc.perform(post("/payment/admin/set-status/pay-1")
                      .param("status", "SUCCESS"))
              .andExpect(status().is3xxRedirection())
              .andExpect(redirectedUrl("/payment/admin/list"));

      verify(paymentService, times(1)).setStatus(payment, "SUCCESS");
   }
}