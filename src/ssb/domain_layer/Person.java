package ssb.domain_layer;

public class Person {

    private String firstName;
    private String lastName;
    private String phoneNr;
    private String cprNr;
    private String ID;

    public Person() {
        this("N/A", "N/A", "N/A", "N/A");
    }

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
        return (firstName + lastName).substring(0, 4) + cprNr.substring(0, 4);
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
}
