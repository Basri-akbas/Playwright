package Unterricht01_Browseröffnung;

import com.microsoft.playwright.*;

public class Tarayiciacma {

    public static void main(String[] args) {

         Playwright playwright = Playwright.create();
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();
            page.navigate("https://demoqa.com/");
            System.out.println(page.title());
            System.out.println(page.content());
            System.out.println(page.context());
            System.out.println(page.url());

            page.close();
            browser.close();
            playwright.close();

    }
}
