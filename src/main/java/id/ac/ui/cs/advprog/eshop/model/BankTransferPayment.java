package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

public class BankTransferPayment extends Payment {

   public BankTransferPayment(String id, Map<String, String> paymentData, Order order) {
      super(id, "BANK", paymentData, order);
      validate();
   }

   private void validate() {
      String bankName = paymentData.get("bankName");
      String refCode = paymentData.get("referenceCode");

      if (bankName == null || bankName.trim().isEmpty() ||
              refCode == null || refCode.trim().isEmpty()) {
         this.status = "REJECTED";
      } else {
         this.status = "SUCCESS";
      }
   }
}