package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

   private final ProductRepository productRepository;

   @Override
   public Product create(Product product) {
      productRepository.create(product);
      return product;
   }

   @Override
   public List<Product> findAll() {
      Iterator<Product> productIterator = productRepository.findAll();
      List<Product> allProduct = new ArrayList<>();
      productIterator.forEachRemaining(allProduct::add);
      return allProduct;
   }

   @Override
   public Product findProductById(String productId) {
      return productRepository.findById(productId);
   }

   @Override
   public void editProduct(String productId, Product product) {
      product.setProductId(productId);
      productRepository.edit(product);
   }

   @Override
   public void deleteProductById(String productId) {
      productRepository.delete(productId);
   }
}
