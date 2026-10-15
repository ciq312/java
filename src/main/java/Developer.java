public class Developer extends Employee {
    public Developer(String firstName, String lastName, Integer salary) {
    super(firstName, lastName, salary);
    }

    @Override
    public String getType() {return "Developer";}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Developer other)) {
            return false;
        }
        return getFirstName().equals(other.getFirstName()) && getLastName().equals(other.getLastName());
    }
}