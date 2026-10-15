import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.OptionalDouble;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class EmployeeServiceTest {
    @Test
    public void averageSalary_shouldReturnAverage() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Manager("w", "kek", 1000));
        employees.add(new Developer("w", "kek", 2000));
        employees.add(new Manager("w", "kek", 3000));

        var service = new EmployeeService();

        var avg = service.averageSalary(employees);

        assertEquals(OptionalDouble.of(2000), avg);
    }

    @Test
    public void filterByRole_ShouldFilterDevelopers() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Manager("w", "kek", 1000));
        employees.add(new Developer("w", "kek", 2000));
        employees.add(new Manager("w", "kek", 3000));

        var service = new EmployeeService();

        var filtered = service.filterByRole(employees, "Manager");

        assertEquals(2, filtered.size());
    }

    @Test
    public void sortByName_ShouldSort() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Manager("efg", "kek", 3000));
        employees.add(new Manager("abc", "kek", 1000));
        employees.add(new Developer("bcd", "kek", 2000));

        var service = new EmployeeService();

        service.namesSorted(employees);

        assertEquals("abc", employees.getFirst().getFirstName());
        assertEquals("bcd", employees.get(1).getFirstName());
        assertEquals("efg", employees.get(2).getFirstName());
    }

}
