public abstract class Employee {
    private final string firstName;
    private final string lastName;
    private Integer salary;

    public Employee(string firstName, string lastName, int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public string getFirstName() {
    return firstName;
    }


    public string getLastName() {
        return lastName;
    }


    public Integer getSalary() {
        return salary;
    }
}