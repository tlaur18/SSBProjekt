package ssb.domain_layer;

import java.util.HashMap;
import ssb.data_layer.DatabaseManager;

public class CPRRegisterManager {

    private static CPRRegisterManager instance = null;
    
    private CPRRegisterManager() {
    }
    
    public static CPRRegisterManager getInstance() {
        if (instance == null) {
            instance = new CPRRegisterManager();
        }
        return instance;
    }
    
    public HashMap<String, String> searchCPRRegister(String cprToSearch) {
        return DatabaseManager.getInstance().searchCPRRegister(cprToSearch);
    }
}
