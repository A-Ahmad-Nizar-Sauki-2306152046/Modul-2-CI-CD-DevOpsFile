package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.BankTransferPayment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.VoucherPayment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

   private static final String VOUCHER_METHOD = "VOUCHER";
   private static final String BANK_METHOD = "BANK";
   private static final String SUCCESS_STATUS = "SUCCESS";
   private static final String REJECTED_STATUS = "REJECTED";
   private static final String FAILED_STATUS = "FAILED";

   private final PaymentRepository paymentRepository;

   @Override
   public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
      String paymentId = UUID.randomUUID().toString();
      Payment payment = createPayment(order, method, paymentData, paymentId);
      return paymentRepository.save(payment);
   }

   @Override
   public Payment setStatus(Payment payment, String status) {
      Payment savedPayment = paymentRepository.findById(payment.getId());

      if (savedPayment == null) {
         throw new NoSuchElementException("Payment tidak ditemukan");
      }

      savedPayment.setStatus(status);

      updateOrderStatus(savedPayment, status);

      return paymentRepository.save(savedPayment);
   }

   @Override
   public Payment getPayment(String paymentId) {
      return paymentRepository.findById(paymentId);
   }

   @Override
   public List<Payment> getAllPayments() {
      return paymentRepository.findAll();
   }

   private Payment createPayment(Order order, String method, Map<String, String> paymentData, String paymentId) {
      if (VOUCHER_METHOD.equals(method)) {
         return new VoucherPayment(paymentId, paymentData, order);
      }

      if (BANK_METHOD.equals(method)) {
         return new BankTransferPayment(paymentId, paymentData, order);
      }

      throw new IllegalArgumentException("Metode pembayaran tidak valid");
   }

   private void updateOrderStatus(Payment payment, String status) {
      if (SUCCESS_STATUS.equals(status)) {
         payment.getOrder().setStatus(SUCCESS_STATUS);
      } else if (REJECTED_STATUS.equals(status)) {
         payment.getOrder().setStatus(FAILED_STATUS);
      }
   }
}