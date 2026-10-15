public class Manager extends Employee {
    public Manager(String firstName, String lastName, Integer salary) {
        super(firstName, lastName, salary);
    }

    @Override
    public String getType() {return "Manager";}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Manager other)) {
            return false;
        }
        return getFirstName().equals(other.getFirstName()) && getLastName().equals(other.getLastName());
    }

}