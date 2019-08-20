package learning.appointmentapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import learning.appointmentapp.entities.Lineitem;
import learning.appointmentapp.entities.Order;;

public interface LineitemRepository extends JpaRepository<Lineitem, Long> {
    Lineitem findByOrder(Order order);
}