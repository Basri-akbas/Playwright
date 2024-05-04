package Unterricht12_Api;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.HttpHeader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GetResponseHeaders {

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
    public void getHeadersTest(){
        APIResponse apiResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users");
        int statusCode = apiResponse.status();
        System.out.println("response status code: " + statusCode);
        Assert.assertEquals(statusCode, 200);

        //using map:
        Map<String, String> headersMap =  apiResponse.headers();
        headersMap.forEach((k,v) -> System.out.println(k + ":" + v));
        System.out.println("total response headers: " + headersMap.size());
        Assert.assertEquals(headersMap.get("server"), "cloudflare");
        Assert.assertEquals(headersMap.get("content-type"), "application/json; charset=utf-8");

        System.out.println("===============================");

        //using list:
        List<HttpHeader> headersList = apiResponse.headersArray();
        for(HttpHeader e : headersList){
            System.out.println(e.name + " : " + e.value);
        }
    }
    @AfterAll
    void afterAll() {
        disposeAPIRequestContext();
        closePlaywright();
    }
}
