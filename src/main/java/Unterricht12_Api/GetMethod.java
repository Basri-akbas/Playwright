package Unterricht12_Api;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.*;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetMethod {

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
    void getSpecificUserApiTest() throws IOException {
        APIResponse apiResponse=apiRequestContext.get("https://gorest.co.in/public/v2/users", RequestOptions.create()
                .setQueryParam("gender","male")
                .setQueryParam("status","active"));

        int statusCode=apiResponse.status();
        System.out.println("response status ccode>>>>>>>>>>>>>>>>>> "+statusCode);
        Assertions.assertEquals(200,statusCode);
        Assert.assertEquals(statusCode,200);

        String statusText= apiResponse.statusText();
        System.out.println("Api Status Text----------------> "+statusText);

        System.out.println("Api Response with Plain Text-------------> "+apiResponse.text());

        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode jsonResponse=objectMapper.readTree(apiResponse.body());
        System.out.println(jsonResponse.toPrettyString());

    }

    @AfterTest
    public void tearDown(){
        playwright.close();
    }

}
