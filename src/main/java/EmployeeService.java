import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.util.OptionalDouble;

public class EmployeeService {
    public OptionalDouble averageSalary(ArrayList<Employee> employees) {
        return employees.stream().mapToInt(Employee::getSalary).average();
    }

    public List<Employee> filterByRole(ArrayList<Employee> employees, String role) {
        return employees.stream().filter(employee -> {
            return Objects.equals(employee.getType(), role);
        }).toList();
    }

    public void namesSorted(List<Employee> employees) {
        employees.sort(new NameComparator());
    }
}
