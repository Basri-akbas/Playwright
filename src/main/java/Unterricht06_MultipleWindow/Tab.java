package Unterricht06_MultipleWindow;

import com.microsoft.playwright.*;

public class Tab {

    public static void main(String[] args) throws InterruptedException {

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        Page page = browser.newPage();

        page.navigate("https://the-internet.herokuapp.com/windows");

        // Get page after a specific action (e.g. clicking a link)
        Page newPage = page.context().waitForPage(() -> {
            page.getByText("Click Here").click(); // Opens a new tab
        });
        newPage.waitForLoadState();

        System.out.println("yeni sayfa basligi " + newPage.title());

        Thread.sleep(5000);

        // Eski sekmeye geri dön
        page.bringToFront();

        Thread.sleep(5000);
        page.close();
        browser.close();
        playwright.close();

    }
}
