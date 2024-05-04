package Unterricht12_Api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.io.IOException;
import java.util.Map;


public class GetMethod_Einfach {

    @Test
    public void getUsersApiTest() throws IOException {

        Playwright playwright=Playwright.create();

        APIRequestContext apiRequestContext=playwright.request().newContext(new APIRequest.NewContextOptions());
        //APIResponse apiResponse=apiRequestContext.get("https://gorest.co.in/public/v2/users");
        APIResponse apiResponse=apiRequestContext.get("https://automationexercise.com/api/productsList");

        int statusCode=apiResponse.status();
        System.out.println("response status ccode>>>>>>>>>>>>>>>>>> "+statusCode);
        Assertions.assertEquals(200,statusCode);
        Assert.assertEquals(statusCode,200);

        String statusText= apiResponse.statusText();
        System.out.println("Api Status Text----------------> "+statusText);


        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode jsonResponse=objectMapper.readTree(apiResponse.body());
        System.out.println(jsonResponse.toPrettyString());

        System.out.println("Api Url----------------->"+apiResponse.url());
        System.out.println("Api Response Plain Text--------->"+apiResponse.text());
        System.out.println("----------------Response Headers----------------------");
        Map<String,String>  headersMap= apiResponse.headers();
        System.out.println(headersMap);

    }

    @Test
    public void getSpecificUserApiTest() throws IOException {
        Playwright playwright=Playwright.create();

        APIRequestContext apiRequestContext=playwright.request().newContext(new APIRequest.NewContextOptions());
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





}
