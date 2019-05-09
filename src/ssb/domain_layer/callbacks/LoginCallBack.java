package ssb.domain_layer.callbacks;

import java.util.List;

public interface LoginCallBack {
    void handleMultipleHomes(List<String> homeNames);
    void adminastratorLogin();
}
