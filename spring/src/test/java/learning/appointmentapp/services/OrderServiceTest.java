package learning.appointmentapp.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import learning.appointmentapp.entities.Lineitem;
import learning.appointmentapp.entities.Order;
import learning.appointmentapp.entities.Product;
import learning.appointmentapp.repositories.LineitemRepository;
import learning.appointmentapp.repositories.ProductRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    LineitemRepository lineitemRepository;

    @Test
    public void testCreateOrderSuccess() {
        //Given
        Product product = seedProduct().get(0);
        int quantity = 2;

        //When
        Lineitem lineitem = orderService.createOrder(product, quantity);

        //Then
        assertNotEquals(null, lineitem.getOrder());
        assertEquals(2, lineitem.getQuantity());
        assertEquals(4000, lineitem.getPrice());
    }

    @Test
    public void testCreateOrderFail() {
        //Given
        Product product = new Product();
        int quantity = 0;

        //When
        Lineitem lineitem = orderService.createOrder(product, quantity);

        //Then
        assertEquals(null, lineitem);
    }

    List<Product> seedProduct() {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        Product product2 = new Product();

        product1.setName("Monitor");
        product1.setPrice(2000);
        product2.setName("Mouse");
        product2.setPrice(200);

        productRepository.save(product1);
        productRepository.save(product2);

        productList.add(product1);
        productList.add(product2);

        return productList;
    }
}