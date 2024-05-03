package Unterricht12_Api;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PutApiMethod {

    private Playwright playwright;
    private APIRequestContext request;
    private static final String API_TOKEN = System.getenv("GITHUB_API_TOKEN");

    void createPlaywright() {
        playwright = Playwright.create();
    }

    void createAPIRequestContext() {
        Map<String, String> headers = new HashMap<>();
        // We set this header per GitHub guidelines.
        headers.put("Accept", "application/vnd.github.v3+json");
        // Add authorization token to all requests.
        // Assuming personal access token available in the environment.
        headers.put("Authorization", "token " + API_TOKEN);

        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                // All requests we send go to this API endpoint.
                .setBaseURL("https://api.github.com")
                .setExtraHTTPHeaders(headers));
    }


    @BeforeAll
    void beforeAll() {
        createPlaywright();
        createAPIRequestContext();
    }

    void disposeAPIRequestContext() {
        if (request != null) {
            request.dispose();
            request = null;
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


}
