package learning.appointmentapp.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import learning.appointmentapp.entities.Appointment;
import learning.appointmentapp.entities.Employee;
import learning.appointmentapp.repositories.AppointmentRepository;
import learning.appointmentapp.repositories.EmployeeRepository;

@Service
public class BookingService {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public List<LocalDateTime> checkAppointment(Employee employee) {
        Employee specifiedEmployee = employeeRepository.findById(employee.getId()).orElse(new Employee());
        List<Appointment> appointmentsList = appointmentRepository.findAllByEmployee(specifiedEmployee);
        List<LocalDateTime> timeslotsList = new ArrayList<>();

        for(int i = 0; i < appointmentsList.size(); i++) {
            timeslotsList.add(appointmentsList.get(i).getTimeslot());
        }
        
        return timeslotsList;
    }
    
    public Appointment bookAppointment(LocalDateTime timeslot, Employee employee) {
        Employee specifiedEmployee = employeeRepository.findById(employee.getId()).orElse(new Employee());
        
        if(specifiedEmployee != null) {
            Appointment existingAppointment = appointmentRepository.findByTimeslot(timeslot);
            
            if(existingAppointment == null) {
                Appointment newAppointment = new Appointment();
                newAppointment.setEmployee(specifiedEmployee);
                newAppointment.setTimeslot(timeslot);
                appointmentRepository.save(newAppointment);
                return newAppointment;
            }        
        }
        return null;
    }
    public Appointment bookAppointment2(LocalDateTime timeslot, Employee employee) {
        
        LocalDateTime startTime = timeslot.minusHours(2);
        LocalDateTime endTime = timeslot.plusHours(2);

        List<Appointment> results = appointmentRepository.findByTimeslotBetween(startTime, endTime);

        if (results.size() == 0) {
            Appointment appointment = new Appointment();
            appointment.setEmployee(employee);
            appointment.setTimeslot(timeslot);
            appointmentRepository.save(appointment);
            return appointment;
        } else {
            return null;
        }
    }
}