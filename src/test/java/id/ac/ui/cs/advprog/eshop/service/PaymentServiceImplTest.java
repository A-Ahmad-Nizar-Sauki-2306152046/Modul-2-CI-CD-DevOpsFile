package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.*;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

   @InjectMocks
   PaymentServiceImpl paymentService;

   @Mock
   PaymentRepository paymentRepository;

   List<Payment> payments;
   Order order;

   @BeforeEach
   void setUp() {
      List<Product> products = new ArrayList<>();
      Product product = new Product();
      product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
      product.setProductName("Sampo Cap Bambang");
      product.setProductQuantity(2);
      products.add(product);

      order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
              products, 1708560000L, "Safira Sudrajat");

      payments = new ArrayList<>();
      Map<String, String> voucherData = new HashMap<>();
      voucherData.put("voucherCode", "ESHOP1234ABC5678");
      Payment payment = new VoucherPayment("payment-1", voucherData, order);
      payments.add(payment);
   }

   @Test
   void testAddPayment() {
      Payment payment = payments.get(0);
      doReturn(payment).when(paymentRepository).save(any(Payment.class));

      Payment result = paymentService.addPayment(order, "VOUCHER", payment.getPaymentData());

      verify(paymentRepository, times(1)).save(any(Payment.class));
      assertEquals(payment.getId(), result.getId());
      assertEquals("VOUCHER", result.getMethod());
   }

   @Test
   void testSetStatusSuccess() {
      Payment payment = payments.get(0);
      doReturn(payment).when(paymentRepository).findById(payment.getId());
      doReturn(payment).when(paymentRepository).save(any(Payment.class));

      Payment result = paymentService.setStatus(payment, "SUCCESS");

      assertEquals("SUCCESS", result.getStatus());
      assertEquals("SUCCESS", result.getOrder().getStatus()); // Order ikut SUCCESS
      verify(paymentRepository, times(1)).save(any(Payment.class));
   }

   @Test
   void testSetStatusRejected() {
      Payment payment = payments.get(0);
      doReturn(payment).when(paymentRepository).findById(payment.getId());
      doReturn(payment).when(paymentRepository).save(any(Payment.class));

      Payment result = paymentService.setStatus(payment, "REJECTED");

      assertEquals("REJECTED", result.getStatus());
      assertEquals("FAILED", result.getOrder().getStatus());
      verify(paymentRepository, times(1)).save(any(Payment.class));
   }

   @Test
   void testSetStatusInvalidPaymentId() {
      doReturn(null).when(paymentRepository).findById("invalid-id");

      Payment invalidPayment = new VoucherPayment("invalid-id", new HashMap<>(), order);

      assertThrows(NoSuchElementException.class, () -> {
         paymentService.setStatus(invalidPayment, "SUCCESS");
      });
   }

   @Test
   void testGetPayment() {
      Payment payment = payments.get(0);
      doReturn(payment).when(paymentRepository).findById(payment.getId());

      Payment result = paymentService.getPayment(payment.getId());
      assertEquals(payment.getId(), result.getId());
   }

   @Test
   void testGetAllPayments() {
      doReturn(payments).when(paymentRepository).findAll();

      List<Payment> result = paymentService.getAllPayments();
      assertEquals(1, result.size());
   }

   @Test
   void testAddPaymentBank() {
      Map<String, String> bankData = new HashMap<>();
      bankData.put("bankName", "Bank BCA");
      bankData.put("referenceCode", "REF123");

      Payment bankPayment = new BankTransferPayment("payment-2", bankData, order);
      doReturn(bankPayment).when(paymentRepository).save(any(Payment.class));

      Payment result = paymentService.addPayment(order, "BANK", bankData);

      verify(paymentRepository, times(1)).save(any(Payment.class));
      assertEquals("BANK", result.getMethod());
   }

   @Test
   void testAddPaymentInvalidMethod() {
      Map<String, String> paymentData = new HashMap<>();

      assertThrows(IllegalArgumentException.class, () -> {
         paymentService.addPayment(order, "PAYLATER", paymentData);
      });
   }

   @Test
   void testSetStatusOther() {
      Payment payment = payments.get(0);
      doReturn(payment).when(paymentRepository).findById(payment.getId());
      doReturn(payment).when(paymentRepository).save(any(Payment.class));

      Payment result = paymentService.setStatus(payment, "PENDING");

      assertEquals("PENDING", result.getStatus());

      assertEquals("WAITING_PAYMENT", result.getOrder().getStatus());
      verify(paymentRepository, times(1)).save(any(Payment.class));
   }
}