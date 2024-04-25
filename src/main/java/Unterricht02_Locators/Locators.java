package Unterricht02_Locators;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

public class Locators {

    public static void main(String[] args) {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page = browser.newPage();
        page.navigate("https://demoqa.com/text-box");
        System.out.println(page.title());

        // getByText
        Locator textbox=page.getByText("Book Store Application");
        System.out.println("1. text " + textbox.innerText());

        // getByRole
        Locator fullName = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Full Name"));
        fullName.click();
        fullName.fill("Hasan");
        Locator fullName1= page.getByRole(AriaRole.TEXTBOX,new Page.GetByRoleOptions().setName("Full Name"));
        System.out.println("2. fullName " + fullName1.innerText());


        // getByPlaceholder
        Locator email  = page.getByPlaceholder("name@example.com");
        System.out.println("3. email " + email.innerText());
        email.click();

        // click submit button
        // getByRole
        Locator submitButton = page.getByRole(AriaRole.BUTTON,new Page.GetByRoleOptions().setName("Submit"));
        submitButton.click();

        page.close();
        browser.close();
        playwright.close();
    }
}
