package Unterricht12_Api;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteMethod {

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
    public void deleteBook() {

        APIResponse apiDeleteResponse = apiRequestContext.delete("https://restful-booker.herokuapp.com/booking/1", RequestOptions.create()
                .setHeader("Cookie","token=abc123"));

        System.out.println(apiDeleteResponse.status());
        System.out.println(apiDeleteResponse.statusText());


    }

    @AfterAll
    void afterAll() {
        disposeAPIRequestContext();
        closePlaywright();
    }
}
