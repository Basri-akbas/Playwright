package Unterricht04_Assertions;

import com.microsoft.playwright.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PageAssertions {

    public static void main(String[] args) {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        Page page = browser.newPage();

        page.navigate("https://www.ebay.com/");

        // page assertions

        // hasUrl
        assertThat(page).hasURL("https://www.ebay.com/");

        // hasTitle
        assertThat(page).hasTitle("Electronics, Cars, Fashion, Collectibles & More | eBay");

        // not ()
        assertThat(page).not().hasTitle("test");

        page.close();
        browser.close();
        playwright.close();



    }
}
