import java.util.HashMap;
import java.util.Map;

public class MapTest {
    public static void main(String[] args) {
        HashMap<Long,String> map_1=new HashMap<>();
        HashMap<Long,String> map_2=new HashMap<>();

        map_1.put(1L,"str_01");         
        map_1.put(3L,"str_03");
        map_1.put(2L,"str_02");

        for(Long key : map_1.keySet()){
            String value=map_1.get(key);
            System.out.println("key:"+key+"   value:"+value);
        }

        map_2.put(5L,"str_03");
        map_2.put(4L,"str_04");
        map_2.put(6L,"str_06");
        map_2.putAll(map_1);
        
        for(Long key : map_2.keySet()){
            String value=map_2.get(key);
            System.out.println("key:"+key+"   value:"+value);
        }

    }
}

