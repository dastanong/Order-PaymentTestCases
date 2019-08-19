package learning.appointmentapp;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import learning.appointmentapp.entities.Employee;
import learning.appointmentapp.repositories.EmployeeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EmployeeRepositoryTest {
 
    @Autowired
    EmployeeRepository employeeRepository;
 
    @Test
    public void testFindAllEmpty() {
        // Given
        employeeRepository.deleteAll();
 
        // When
        List<Employee> result = employeeRepository.findAll();
 
        // Then
        assertEquals(0, result.size());
    }

    public List<Employee> seedEmployeeList() {
        List<Employee> employeeList = new ArrayList<Employee>();

        Employee employee1 = new Employee();
        employee1.setName("Abc");
        employee1.setEmail("employee1@hotmail.com");
        employeeRepository.save(employee1);
        Employee employee2 = new Employee();
        employee2.setName("Bac");
        employee2.setEmail("employee2@yahoo.com");
        employeeRepository.save(employee2);

        employeeList.add(employee1);
        employeeList.add(employee2);

        return employeeList;
    }

    public Employee seedEmployee() {
        Employee employee = new Employee();
        employee.setName("Employee 1");
        employee.setEmail("employee1@hotmail.com");
        employeeRepository.save(employee);
        return employee;
    }

    @Test
    public void testFindAllNotEmpty() {
        //Given
        seedEmployee();

        //When
        List<Employee> result = employeeRepository.findAll();

        //Then
        assertEquals(1, result.size());

        Employee searchedEmployee = result.get(0);
        assertEquals("Employee 1", searchedEmployee.getName());
        assertEquals("employee1@hotmail.com", searchedEmployee.getEmail());
    }

    @Test
    public void testFindById() {
        //Given
        seedEmployee();
        long employeeId = seedEmployee().getId();

        //When
        Employee searchedEmployee = employeeRepository.findById(employeeId).orElse(new Employee());

        //Then
        assertEquals("Employee 1", searchedEmployee.getName());
        assertEquals("employee1@hotmail.com", searchedEmployee.getEmail());
    }

    @Test
    public void testFindByWrongId() {
        //Given
        seedEmployee();
        long employeeId = 1;

        //When
        Employee searchedEmployee = employeeRepository.findById(employeeId).orElse(null);

        //Then
        assertEquals(null, searchedEmployee);
    }

    @Test
    public void testFindByEmail() {
        //Given
        String email = seedEmployee().getEmail();
        
        //When
        Employee searchedEmployee = employeeRepository.findByEmail(email);

        //Then
        assertEquals("employee1@hotmail.com", searchedEmployee.getEmail());
    }

    @Test
    public void testFindByName() {
        //Given
        String name = seedEmployee().getName();
        
        //When
        Employee searchedEmployee = employeeRepository.findByName(name);

        //Then
        assertEquals("Employee 1", searchedEmployee.getName());
    }

    @Test
    public void testFindAllSorting() {
        //Given
        seedEmployeeList();

        //When
        Sort sorting = new Sort(Sort.Direction.DESC, "name");
        List<Employee> employeeList = employeeRepository.findAll(sorting);

        //Then
        assertEquals("Bac", employeeList.get(0).getName());
    }
}