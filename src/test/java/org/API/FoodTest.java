
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.*;

public class FoodTest {
    Response res;
    @BeforeClass
    public void setup() {
        ApiResuable.uri("http://127.0.0.1:5000");
    }

    @Test
    public void runGetApiTest() {
        Map<String,String > header = new HashMap<>();
        header.put("Content-Type", "application/json");
        ArrayList<ApiResuable> list = new ArrayList<>();
        for(int i=1;i<=10 ;i++){
            list.add(new ApiResuable("/get-recipe-detail?id="+i,"GET",null));
        }
        list.add(new ApiResuable("/get-recipes?category=Non-Veg","GET",null));
        list.add(new ApiResuable("/get-suggestions?query=chi", "GET", null));
        list.add(new ApiResuable("/get-suggestions?query=ch", "GET", null));
        list.add(new ApiResuable("/get-suggestions?query=fish", "GET", null));
        list.add(new ApiResuable("/chatbot-api","POST","{\"prompt\":\"hi\"}"));

        for(ApiResuable r:list){
            res = ApiResuable.execute(r,header);
            System.out.println("Method "+ r.getMethod());
            System.out.println("Endpoint : "+ r.getEndpoint());
            System.out.println("Status code :"+res.getStatusCode());
            System.out.println("Body : "+res.getBody().asString());

        }
    }
}
