package more.core.Interface;

import java.util.Map;

public interface HttpRequest {
    String doGet(String uri);
    String doPost(String uri, Map<String, Object> mapParam);
    String doPost(String uri, String jsonParam);
}
