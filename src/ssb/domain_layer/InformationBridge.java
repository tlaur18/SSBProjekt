package ssb.domain_layer;

import java.util.ArrayList;
import java.util.HashMap;
import ssb.domain_layer.Employee.Employee;

public class InformationBridge {

    private static InformationBridge INSTANCE = new InformationBridge();
    private final HashMap<String, Integer> integerInformation;
    private final HashMap<String, Double> doubleInformation;
    private final HashMap<String, String> stringInformation;
    private final HashMap<String, Boolean> booleanInformation;
    private final HashMap<String, ArrayList<?>> arrayListInformation;
    private Employee loggedInEmployee = null;

    private InformationBridge() {
        this.integerInformation = new HashMap<>();
        this.doubleInformation = new HashMap<>();
        this.stringInformation = new HashMap<>();
        this.booleanInformation = new HashMap<>();
        this.arrayListInformation = new HashMap<>();
    }

    public void putInteger(String key, Integer value) {
        integerInformation.put(key, value);
    }

    public Integer getIntegerValue(String key) {
        return integerInformation.get(key);
    }

    public void putDouble(String key, Double value) {
        doubleInformation.put(key, value);
    }

    public Double getDoubleValue(String key) {
        return doubleInformation.get(key);
    }

    public void putString(String key, String value) {
        stringInformation.put(key, value);
    }

    public String getStringValue(String key) {
        return stringInformation.get(key);
    }

    public void putBoolean(String key, Boolean value) {
        booleanInformation.put(key, value);
    }

    public Boolean getBooleanValue(String key) {
        return booleanInformation.get(key);
    }

    public void putArrayList(String key, ArrayList<?> arrayList) {
        arrayListInformation.put(key, arrayList);
    }

    public ArrayList<?> getArrayListValue(String key) {
        return arrayListInformation.get(key);
    }

    public Employee getLoggedInEmployee() {
        return loggedInEmployee;
    }

    public void setLoggedInEmployee(Employee loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
    }

    public static InformationBridge getINSTANCE() {
        return INSTANCE;
    }
    
    public void resetSystem() {
        INSTANCE = new InformationBridge();
    }

}
