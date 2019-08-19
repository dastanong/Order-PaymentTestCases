package learning.appointmentapp;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import learning.appointmentapp.entities.Appointment;
import learning.appointmentapp.entities.Employee;
import learning.appointmentapp.repositories.AppointmentRepository;
import learning.appointmentapp.repositories.EmployeeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AppointmentRepositoryTest {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public Appointment seedAppointment(LocalDateTime timeslot) {
        Appointment appointment = new Appointment();
        appointment.setTimeslot(timeslot);
        appointmentRepository.save(appointment);
        return appointment;
    }

    @Test
    public void testFindAllEmpty() {
        appointmentRepository.deleteAll();

        List<Appointment> result = appointmentRepository.findAll();

        assertEquals(0, result.size());
    }

    @Test
    public void testFindAllNotEmpty() {
        LocalDateTime timeslot = LocalDateTime.now();
        seedAppointment(timeslot);

        List<Appointment> result = appointmentRepository.findAll();

        // Then: get an array of the appointments
        assertEquals(1, result.size());

        Appointment retrieved = result.get(0);
        assertEquals(timeslot, retrieved.getTimeslot());
    }

    @Test
    public void testFindByTimeslotBetweenEmpty() {
        // Given a start time and a end time
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(3);

        // Find all appointments within that time range
        List<Appointment> results = appointmentRepository.findByTimeslotBetween(startTime, endTime);

        assertEquals(0, results.size());
    }

    @Test
    public void testFindByTimeslotBetweenPresent() {
        // Given a start time and a end time
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(3);

        seedAppointment(LocalDateTime.now().plusMinutes(30));
        seedAppointment(LocalDateTime.now().plusMinutes(45));
        seedAppointment(LocalDateTime.now().plusHours(5));

        // Find all appointments within that time range
        List<Appointment> results = appointmentRepository.findByTimeslotBetween(startTime, endTime);

        assertEquals(2, results.size());
    }

    @Test
    public void testFindByEmployeeEmailContainsPresent() {
        // Given
        Employee employee = new Employee();
        employee.setName("John");
        employee.setEmail("john@gmail.com");
        employeeRepository.save(employee);
        Appointment seeded = seedAppointment(LocalDateTime.now());
        seeded.setEmployee(employee);
        appointmentRepository.save(seeded);

        // When
        List<Appointment> results = appointmentRepository.findByEmployeeEmailContains(employee.getEmail());

        assertEquals(1, results.size());
    }

    @Test
    public void testFindByEmployeeEmailContainsEmpty() {

    }
}