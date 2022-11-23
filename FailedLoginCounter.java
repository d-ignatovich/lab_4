import java.util.HashMap;

public class FailedLoginCounter {
    private static FailedLoginCounter instance;
    private static HashMap<String,Integer> map = new HashMap<>();
    public static synchronized FailedLoginCounter getInstance(String email){
        if (instance == null) {
            instance = new FailedLoginCounter();
            map.put(email,1);
        } else {
            if (map.containsKey(email)){
                map.put(email, map.get(email) + 1);
            } else {
                map.put(email,1);
            }
        }
        return instance;
    }
    public static int getCounter(String email){
        if (map!=null){
            if (map.containsKey(email)){
                return map.get(email);
            } else {
                return 0;
            }
        }
        return 0;
    }
}