package ssb.domain_layer;

public abstract class Person {

    private final String firstName;
    private final String lastName;
    private final String phoneNr;
    private final String cprNr;
    private final String ID;

    public Person(String firstName, String lastName, String phoneNr, String cprNr) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNr = phoneNr;
        this.cprNr = cprNr;
        this.ID = generateID();
    }

    /*
    ID: First four letters of the Person's name + birthdate (without the year)
     */
    private String generateID() {
        return firstName.substring(0, 3) + lastName.substring(0, 3) + cprNr.substring(0, 4);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public String getCprNr() {
        return cprNr;
    }

    public String getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Person{" + "firstName=" + firstName + ", lastName=" + lastName + ", phoneNr=" + phoneNr + ", cprNr=" + cprNr + ", ID=" + ID + '}';
    }

}
