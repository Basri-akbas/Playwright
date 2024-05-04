package Unterricht12_Api;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.*;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DisposeMethod {

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
    public void disposeResponseTest(){

        //Request-1
        APIResponse apiResponse=apiRequestContext.get("https://gorest.co.in/public/v2/users");

        int statusCode=apiResponse.status();
        System.out.println("response status ccode>>>>>>>>>>>>>>>>>> "+statusCode);
        Assertions.assertEquals(200,statusCode);
        Assert.assertEquals(statusCode,200);

        String statusText= apiResponse.statusText();
        System.out.println("Api Status Text----------------> "+statusText);

        System.out.println("Api Response with Plain Text-------------> "+apiResponse.text());

        apiResponse.dispose();   //will dispose only response body but status code, url, status text will remain same
        int statusCode1=apiResponse.status();
        System.out.println("response status code after disposed >>>>>>>>>>>>>>>>>> "+statusCode1);

        try {
            System.out.println("Api Response after dispose with Plain Text-------------> "+apiResponse.text());
        }
        catch (PlaywrightException e){
            System.out.println("--------Api Response body is disposed-------------");
        }
        String statusResText1 = apiResponse.statusText();
        System.out.println("response status text after disposed------------>"+statusResText1);
        System.out.println("response url after disposed----------> " + apiResponse.url());

        //Request -2:
        APIResponse apiResponse1 = apiRequestContext.get("https://reqres.in/api/users/2");
                System.out.println("get response body for 2nd request: ");
        System.out.println("status code:" + apiResponse1.status());
        System.out.println("repose body:" + apiResponse1.text());

        //request context dispose:
        apiRequestContext.dispose();
        //System.out.println("response1 body:" + apiResponse.text());
        System.out.println("response2 body:" + apiResponse1.text());
    }

    @AfterTest
    public void tearDown(){
        playwright.close();
    }

}
