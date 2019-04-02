package ssb.domain_layer;

public class Address {
    
    private final int zipCode;
    private final int houseNr;
    private final String street;
    private final String city;
    
    public Address () {
        this(0, 0, "Null", "Null");
    }
    
    public Address(int zipCode, int houseNr, String street, String city) {
        
        this.houseNr = houseNr;
        this.zipCode = zipCode;
        this.street = street;
        this.city = city;
    }
    
    public String getAddress(){
        return (street + houseNr) + city + zipCode;
    }
    
}
