package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentTest {
   List<Product> products;
   Order order;

   @BeforeEach
   void setUp() {
      products = new ArrayList<>();
      Product product = new Product();
      product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
      product.setProductName("Sampo Cap Bambang");
      product.setProductQuantity(2);
      products.add(product);

      order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
              products, 1708560000L, "Safira Sudrajat");
   }

   @Test
   void testCreatePaymentVoucherSuccess() {
      Map<String, String> paymentData = new HashMap<>();
      paymentData.put("voucherCode", "ESHOP1234ABC5678");

      Payment payment = new VoucherPayment("payment-1", paymentData, order);
      assertEquals("SUCCESS", payment.getStatus());
   }

   @Test
   void testCreatePaymentVoucherRejected_Not16Chars() {
      Map<String, String> paymentData = new HashMap<>();
      paymentData.put("voucherCode", "ESHOP123"); // Kurang dari 16

      Payment payment = new VoucherPayment("payment-1", paymentData, order);
      assertEquals("REJECTED", payment.getStatus());
   }

   @Test
   void testCreatePaymentVoucherRejected_NotStartWithEshop() {
      Map<String, String> paymentData = new HashMap<>();
      paymentData.put("voucherCode", "SHOPP1234ABC5678"); // Tidak mulai dengan ESHOP

      Payment payment = new VoucherPayment("payment-1", paymentData, order);
      assertEquals("REJECTED", payment.getStatus());
   }

   @Test
   void testCreatePaymentBankTransferSuccess() {
      Map<String, String> paymentData = new HashMap<>();
      paymentData.put("bankName", "Bank BCA");
      paymentData.put("referenceCode", "REF123456");

      Payment payment = new BankTransferPayment("payment-4", paymentData, order);
      assertEquals("SUCCESS", payment.getStatus());
   }

   @Test
   void testCreatePaymentBankTransferRejected_EmptyBankName() {
      Map<String, String> paymentData = new HashMap<>();
      paymentData.put("bankName", "");
      paymentData.put("referenceCode", "REF123456");

      Payment payment = new BankTransferPayment("payment-4", paymentData, order);
      assertEquals("REJECTED", payment.getStatus());
   }

   @Test
   void testCreatePaymentBankTransferRejected_NullBankName() {
      Map<String, String> paymentData = new HashMap<>();
      paymentData.put("referenceCode", "REF123456");

      Payment payment = new BankTransferPayment("payment-6", paymentData, order);
      assertEquals("REJECTED", payment.getStatus());
   }

   @Test
   void testCreatePaymentBankTransferRejected_NullReferenceCode() {
      Map<String, String> paymentData = new HashMap<>();
      paymentData.put("bankName", "Bank BCA");

      Payment payment = new BankTransferPayment("payment-7", paymentData, order);
      assertEquals("REJECTED", payment.getStatus());
   }

   @Test
   void testCreatePaymentBankTransferRejected_EmptyReferenceCode() {
      Map<String, String> paymentData = new HashMap<>();
      paymentData.put("bankName", "Bank BCA");
      paymentData.put("referenceCode", "   ");

      Payment payment = new BankTransferPayment("payment-8", paymentData, order);
      assertEquals("REJECTED", payment.getStatus());
   }

   @Test
   void testCreatePaymentVoucherRejected_NullVoucherCode() {
      Map<String, String> paymentData = new HashMap<>();

      Payment payment = new VoucherPayment("payment-9", paymentData, order);
      assertEquals("REJECTED", payment.getStatus());
   }

   @Test
   void testCreatePaymentVoucherRejected_Not8Digits() {
      Map<String, String> paymentData = new HashMap<>();
      paymentData.put("voucherCode", "ESHOP1234567890A");

      Payment payment = new VoucherPayment("payment-10", paymentData, order);
      assertEquals("REJECTED", payment.getStatus());
   }
}