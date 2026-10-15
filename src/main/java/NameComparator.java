import java.util.Comparator;

public class NameComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee employee, Employee otherEmployee) {
        return employee.getFirstName().compareTo(otherEmployee.getFirstName());
    }
}
