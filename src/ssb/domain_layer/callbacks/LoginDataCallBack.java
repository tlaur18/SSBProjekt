package ssb.domain_layer.callbacks;

import java.util.ArrayList;
import java.util.HashMap;

public interface LoginDataCallBack {
    void employeeDetailsResult(HashMap<String, String> employeeDetails);
    void homeDataResult(ArrayList<HashMap<String, String>> homes);
}
