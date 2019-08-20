package learning.appointmentapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learning.appointmentapp.entities.Lineitem;
import learning.appointmentapp.entities.Order;
import learning.appointmentapp.entities.Product;
import learning.appointmentapp.repositories.LineitemRepository;
import learning.appointmentapp.repositories.OrderRepository;
import learning.appointmentapp.repositories.ProductRepository;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    LineitemRepository lineitemRepository;

    @Autowired
    ProductRepository productRepository;
    
    public Lineitem createOrder(Product product, int quantity) {
        List<Product> existingProduct = productRepository.findAllById(product.getId());
        
        if(quantity > 0 && existingProduct.size()> 0) {
            Order newOrder = new Order();
            Lineitem lineitem = new Lineitem();
            long price = 0;

            lineitem.setOrder(newOrder);
            lineitem.setProduct(product);
            lineitem.setQuantity(quantity);

            price = quantity * product.getPrice();
            lineitem.setPrice(price);

            lineitemRepository.save(lineitem);
            return lineitem;
        } else {
            return null;
        }
    }

}