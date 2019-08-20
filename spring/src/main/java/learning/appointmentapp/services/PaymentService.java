package learning.appointmentapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learning.appointmentapp.entities.Lineitem;
import learning.appointmentapp.entities.Order;
import learning.appointmentapp.entities.Payment;
import learning.appointmentapp.repositories.LineitemRepository;

@Service
public class PaymentService {
    @Autowired
    LineitemRepository lineitemRepository;
    
    public Payment createPayment(List<Lineitem> itemList) {
        int totalAmount = 0;
        Order order = new Order();

        if(itemList.size() > 0) {
            for(int i = 0; i < itemList.size(); i++) {
                order = itemList.get(i).getOrder();
                totalAmount += itemList.get(i).getPrice();
            }
            
            Payment newPayment = new Payment();
            newPayment.setOrder(order);
            newPayment.setPaid(true);
            newPayment.setRefunded(false);
            newPayment.setAmount(totalAmount);

            return newPayment;
        } else {
            return null;
        }
    }

    public Payment refundPayment(Payment payment) {
        if(payment.getPaid() == true && payment.getOrder() != null && payment.getRefunded() == false) {
            payment.setRefunded(false);
            return payment;
        }
        if(payment.getPaid() == false) {
            return null;
        } else if(payment.getRefunded() == true) {
            return null;
        }
        return null;
    }
}