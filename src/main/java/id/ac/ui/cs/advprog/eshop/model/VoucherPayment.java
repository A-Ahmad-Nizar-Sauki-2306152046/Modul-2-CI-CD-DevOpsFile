package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

public class VoucherPayment extends Payment {

   public VoucherPayment(String id, Map<String, String> paymentData, Order order) {
      super(id, "VOUCHER", paymentData, order);
      validate();
   }

   private void validate() {
      String voucherCode = paymentData.get("voucherCode");
      if (voucherCode != null &&
              voucherCode.length() == 16 &&
              voucherCode.startsWith("ESHOP") &&
              voucherCode.chars().filter(Character::isDigit).count() == 8) {
         this.status = "SUCCESS";
      } else {
         this.status = "REJECTED";
      }
   }
}