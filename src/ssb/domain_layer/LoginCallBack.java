package ssb.domain_layer;

import java.util.List;

public interface LoginCallBack {
    void handleMultipleHomes(List<String> homeNames);
    void adminastratorLogin();
}
