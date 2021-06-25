package com.vmv.services.product.repository;

import com.vmv.services.product.core.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository("ProductRepository")
public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findById(String id);
}
