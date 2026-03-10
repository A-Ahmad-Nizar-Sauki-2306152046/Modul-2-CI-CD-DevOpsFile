package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.BankTransferPayment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.VoucherPayment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

   @Autowired
   private PaymentRepository paymentRepository;

   @Override
   public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
      Payment payment;
      String paymentId = UUID.randomUUID().toString();

      if ("VOUCHER".equals(method)) {
         payment = new VoucherPayment(paymentId, paymentData, order);
      } else if ("BANK".equals(method)) {
         payment = new BankTransferPayment(paymentId, paymentData, order);
      } else {
         throw new IllegalArgumentException("Metode pembayaran tidak valid");
      }

      return paymentRepository.save(payment);
   }

   @Override
   public Payment setStatus(Payment payment, String status) {
      Payment savedPayment = paymentRepository.findById(payment.getId());

      if (savedPayment == null) {
         throw new NoSuchElementException("Payment tidak ditemukan");
      }

      savedPayment.setStatus(status);

      if ("SUCCESS".equals(status)) {
         savedPayment.getOrder().setStatus("SUCCESS");
      } else if ("REJECTED".equals(status)) {
         savedPayment.getOrder().setStatus("FAILED");
      }

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
}