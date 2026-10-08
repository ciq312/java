import java.util.Comparator;

public class SalaryComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee employee, Employee otherEmployee) {
        if (employee.getSalary().equals(otherEmployee.getSalary())) return 0;
        return employee.getSalary() > otherEmployee.getSalary() ? 1 : -1;
    }
}
