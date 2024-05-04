package Unterricht12_Api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testng.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostMethod {

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

    @Test
    public void createUserPOstMethod() throws IOException {

        Map<String, String> data = new HashMap<String, String>();
        data.put("username", "admin");
        data.put("password", "password123");

        //POST Call: create a user
        APIResponse apiPostResponse =apiRequestContext.post("https://restful-booker.herokuapp.com/auth",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setData(data));
        System.out.println(apiPostResponse.status());
        Assert.assertEquals(apiPostResponse.status(), 200);
        Assert.assertEquals(apiPostResponse.statusText(), "OK");

        System.out.println(apiPostResponse.text());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode postJsonResponse = objectMapper.readTree(apiPostResponse.body());
        System.out.println(postJsonResponse.toPrettyString());

        //capture id from the post json response:
        String token=postJsonResponse.get("token").asText();
        System.out.println("token : "+token);
        //String userId = postJsonResponse.get("id").asText();
        //System.out.println("user id : " + userId);

       /* APIResponse apiGetResponse =
                apiRequestContext.get("https://demoqa.com/Account/v1/Authorized"+ userId);

        Assert.assertEquals(apiGetResponse.status(), 200);
        Assert.assertEquals(apiGetResponse.statusText(), "OK");
        System.out.println(apiGetResponse.text());
//        Assert.assertTrue(apiGetResponse.text().contains("hasanbasri"));
*/

    }

    @AfterAll
    void afterAll() {
        disposeAPIRequestContext();
        closePlaywright();
    }
}
