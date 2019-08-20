package learning.appointmentapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import learning.appointmentapp.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllById(Long id);
}