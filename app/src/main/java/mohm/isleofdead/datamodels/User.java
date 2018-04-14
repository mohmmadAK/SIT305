package mohm.isleofdead.datamodels;

import java.util.HashMap;
import java.util.Map;

public class User {

    public String id;
    public String name;

    public User() {
        this.id = null;
        this.name = null;
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        return map;
    }
}
