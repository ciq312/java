import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public abstract class Employee {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @PositiveOrZero
    private Integer salary;

    public Employee(String firstName, String lastName, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public String getFirstName() {
    return firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public Integer getSalary() {
        return salary;
    }

    public abstract String getType();
}