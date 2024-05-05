package Unterricht12_Api;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import org.testng.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PutMethod {

    private Playwright playwright;
    private APIRequestContext apiRequestContext;

    void createPlaywright() {
        playwright = Playwright.create();
    }

    void createAPIRequestContext() {

        apiRequestContext=playwright.request().newContext(new APIRequest.NewContextOptions());
    }
    @BeforeAll
    void beforeAll() {
        createPlaywright();
        createAPIRequestContext();
    }
    void disposeAPIRequestContext() {
        if (apiRequestContext != null) {
            apiRequestContext.dispose();
            apiRequestContext = null;
        }
    }
    void closePlaywright() {
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }
    @AfterAll
    void afterAll() {
        disposeAPIRequestContext();
        closePlaywright();
    }

    @Test
    public void updateUserTest() throws IOException, IOException {

        //create users object: using builder pattern:
        Map<String,String > user=new HashMap<>();
        user.put("name","hasanbasri");
        user.put("email","hasanbasri20@gmail.com");
        user.put("gender","male");
        user.put("status","active");


        //1. POST Call: create a user
        APIResponse apiPostResponse = apiRequestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
                        .setData(user));

        System.out.println(apiPostResponse.url());
        System.out.println(apiPostResponse.status());
        Assert.assertEquals(apiPostResponse.status(), 201);
        Assert.assertEquals(apiPostResponse.statusText(), "Created");

        String responseText = apiPostResponse.text();
        System.out.println(responseText);


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiPostResponse.body());
        System.out.println("actual user from the response---->");
        System.out.println(jsonResponse.toPrettyString());

        Assert.assertEquals(jsonResponse.get("name").asText(),"hasanbasri");
        Assert.assertEquals(jsonResponse.get("email").asText(), user.get("email"));
        Assert.assertEquals(jsonResponse.get("gender").asText(), user.get("gender"));
        Assert.assertNotNull(jsonResponse.get("id").asText());

        String userId = jsonResponse.get("id").toString();
        System.out.println("new user id is : " + userId);



        System.out.println("---------------PUT CALL----------------");

        //2. PUT Call - update user:
        user.put("status","inactive");
        APIResponse apiPUTResponse = apiRequestContext.put("https://gorest.co.in/public/v2/users/" + userId,
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setHeader("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6")
                        .setData(user));

        System.out.println(apiPUTResponse.status() + " : " + apiPUTResponse.statusText());
        Assert.assertEquals(apiPUTResponse.status(), 200);

        String putResponseText = apiPUTResponse.text();
        System.out.println("update user : " + putResponseText);

        JsonNode jsonResponse1 = objectMapper.readTree(apiPUTResponse.body());
        System.out.println("Status updated---------------> "+jsonResponse1.get("status").asText());
        Assert.assertEquals(jsonResponse1.get("status").asText(), user.get("status"));
        Assert.assertEquals(jsonResponse1.get("name").asText(), user.get("name"));

        System.out.println("---------------GET CALL----------------");

        //3. Get the updates user with GET CALL:
        APIResponse apiGETResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users/"+userId,
                RequestOptions.create()
                        .setHeader("Authorization", "Bearer e4b8e1f593dc4a731a153c5ec8cc9b8bbb583ae964ce650a741113091b4e2ac6"));

        System.out.println(apiGETResponse.url());

        int statusCode = apiGETResponse.status();
        System.out.println("response status code: " + statusCode);
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(apiGETResponse.ok(), true);

        String statusGETStatusText = apiGETResponse.statusText();
        System.out.println(statusGETStatusText);

        String getResponseText = apiGETResponse.text();

        JsonNode jsonResponse2 = objectMapper.readTree(apiGETResponse.body());
        Assert.assertEquals(jsonResponse2.get("id").asText(), userId);
        Assert.assertEquals(jsonResponse2.get("status").asText(), user.get("status"));
        Assert.assertEquals(jsonResponse2.get("name").asText(), user.get("name"));

        //Delete user
        APIResponse apiDeleteResponse=apiRequestContext.delete("https://gorest.co.in/public/v2/users/"+userId);

        System.out.println("---------------Delete User-------------------------");
        System.out.println(apiDeleteResponse.status());
        System.out.println(apiDeleteResponse.statusText());

        Assert.assertEquals(apiDeleteResponse.status(), 404);

        System.out.println("delete user response body ====" + apiDeleteResponse.text());


    }

}
