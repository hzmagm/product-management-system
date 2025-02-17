package com.springdemo.products_management_system.ProductRepositoryTest;

import com.springdemo.products_management_system.Model.Product;
import com.springdemo.products_management_system.Repo.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Test
    public void testSaveProduct() {
        Product product = new Product();
        product.setName("test");
        product.setPrice(BigDecimal.valueOf(653));
        Product savedProduct = productRepository.save(product);
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct).isEqualTo(product);
    }
    @Test
    public void testUpdateProduct() {
        Product product = new Product();
        product.setName("test");
        product.setPrice(BigDecimal.valueOf(653));
        Product savedProduct = productRepository.save(product);
        savedProduct.setName("test2");
        productRepository.save(savedProduct);
        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("test2");
    }
    @Test
    public void testDeleteProduct() {
        Product product = new Product();
        product.setName("test");
        product.setPrice(BigDecimal.valueOf(653));
        productRepository.save(product);

        productRepository.delete(product);
        Optional<Product> foundProduct = productRepository.findById(product.getId());
        assertThat(foundProduct).isNotPresent();
    }
    @Test
    public void testFindById() {
        Product product = new Product();
        product.setName("test");
        product.setPrice(BigDecimal.valueOf(653));

        productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findById(product.getId());
        assertThat(foundProduct).isPresent();
    }
}
