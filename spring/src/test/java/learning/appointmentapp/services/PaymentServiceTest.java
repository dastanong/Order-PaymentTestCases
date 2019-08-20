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
import learning.appointmentapp.entities.Payment;
import learning.appointmentapp.entities.Product;
import learning.appointmentapp.repositories.LineitemRepository;
import learning.appointmentapp.repositories.PaymentRepository;
import learning.appointmentapp.repositories.ProductRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PaymentServiceTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    LineitemRepository lineitemRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    public void testCreatePaymentSuccess() {
        //Given
        List<Lineitem> lineitemList = seedLineitem();

        //When
        Payment payment = paymentService.createPayment(lineitemList);

        //Then
        assertNotEquals(null, payment);
        assertNotEquals(null, payment.getOrder());
        assertEquals(7200, payment.getAmount());
        assertEquals(true, payment.getPaid());
        assertEquals(false, payment.getRefunded());
    }

    @Test
    public void testCreatePaymentFail() {
        //Given
        List<Lineitem> lineitemList = new ArrayList<>();

        //When
        Payment payment = paymentService.createPayment(lineitemList);

        //Then
        assertEquals(null, payment);
    }    

    @Test
    public void testRefundSuccess() {
        //Given
        Payment payment = seedPayment();

        //When
        Payment refundedPayment = paymentService.refundPayment(payment);

        //Then
        assertNotEquals(null, refundedPayment);
    }

    @Test
    public void testRefundFail() {
        //Given
        Payment payment = seedRefundedPaymentOrUnpaidOrder();

        //When
        Payment invalidRefundPayment = paymentService.refundPayment(payment);

        //Then
        assertEquals(null, invalidRefundPayment);
    }

    Payment seedPayment() {
        Payment payment = new Payment();
        Lineitem lineitem = seedLineitem().get(0);
        payment.setOrder(lineitem.getOrder());
        payment.setAmount(lineitem.getPrice());
        payment.setPaid(true);
        payment.setRefunded(false);
        paymentRepository.save(payment);

        return payment;
    }

    Payment seedRefundedPaymentOrUnpaidOrder() {
        Payment payment = new Payment();
        Lineitem lineitem = seedLineitem().get(1);
        payment.setOrder(lineitem.getOrder());
        payment.setAmount(lineitem.getPrice());
        payment.setPaid(false);
        payment.setRefunded(true);
        paymentRepository.save(payment);

        return payment;
    }

    List<Lineitem> seedLineitem() {
        List<Lineitem> allLineitems = new ArrayList<>();
        Order order = new Order();
        Lineitem item1 = new Lineitem();
        Lineitem item2 = new Lineitem();
        int quantity1 = 3;
        int quantity2 = 6;

        item1.setOrder(order);
        item1.setProduct(seedProduct().get(0));
        item1.setQuantity(quantity1);
        item1.setPrice(quantity1 * seedProduct().get(0).getPrice());

        item2.setOrder(order);
        item2.setProduct(seedProduct().get(1));
        item2.setQuantity(quantity2);
        item2.setPrice(quantity2 * seedProduct().get(1).getPrice());

        lineitemRepository.save(item1);
        lineitemRepository.save(item2);
        allLineitems.add(item1);
        allLineitems.add(item2);

        return allLineitems;
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