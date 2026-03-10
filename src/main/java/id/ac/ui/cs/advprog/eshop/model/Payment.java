package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class Payment {
   String id;
   String method;
   @Setter
   String status;
   Map<String, String> paymentData;
   Order order;

   public Payment(String id, String method, Map<String, String> paymentData, Order order) {
      this.id = id;
      this.method = method;
      this.paymentData = paymentData;
      this.order = order;

      if ("VOUCHER".equals(method)) {
         String voucherCode = paymentData.get("voucherCode");
         if (voucherCode != null &&
                 voucherCode.length() == 16 &&
                 voucherCode.startsWith("ESHOP") &&
                 voucherCode.chars().filter(Character::isDigit).count() == 8) {
            this.status = "SUCCESS";
         } else {
            this.status = "REJECTED";
         }
      } else if ("BANK".equals(method)) {
         String bankName = paymentData.get("bankName");
         String refCode = paymentData.get("referenceCode");
         if (bankName == null || bankName.trim().isEmpty() ||
                 refCode == null || refCode.trim().isEmpty()) {
            this.status = "REJECTED";
         } else {
            this.status = "SUCCESS";
         }
      } else {
         this.status = "REJECTED";
      }
   }
}