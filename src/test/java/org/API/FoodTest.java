package org.API;

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
     list.add(new ApiResuable("/get-recipes?category=Non-Veg","GET",null));
     list.add(new ApiResuable("/get-recipe-detail?id=1","GET",null));
        list.add(new ApiResuable("/get-recipe-detail?id=2","GET",null));
        list.add(new ApiResuable("/get-recipe-detail?id=3","GET",null));
        list.add(new ApiResuable("/get-recipe-detail?id=4","GET",null));
        list.add(new ApiResuable("/get-recipe-detail?id=5","GET",null));
        list.add(new ApiResuable("/get-recipe-detail?id=6","GET",null));
        list.add(new ApiResuable("/get-recipe-detail?id=7","GET",null));
        list.add(new ApiResuable("/get-recipe-detail?id=8","GET",null));
        list.add(new ApiResuable("/get-recipe-detail?id=9","GET",null));
        list.add(new ApiResuable("/get-recipe-detail?id=10","GET",null));
        list.add(new ApiResuable("/get-recipe-detail?id=11","GET",null));
        list.add(new ApiResuable("/get-recipe-detail?id=12","GET",null));
      list.add(new ApiResuable("/get-suggestions?query=chi", "GET", null));
        list.add(new ApiResuable("/get-suggestions?query=ch", "GET", null));
        list.add(new ApiResuable("/get-suggestions?query=fish", "GET", null));
     list.add(new ApiResuable("/chatbot-api","POST","{\"prompt\":\"hi\"}"));

     for(ApiResuable r : list){
         res = ApiResuable.execute(r,header);
         System.out.println("Method "+ r.getMethod());
         System.out.println("Endpoint : "+ r.getEndpoint());
         System.out.println("Status code :"+res.getStatusCode());
         res.prettyPrint();

     }
}
}
