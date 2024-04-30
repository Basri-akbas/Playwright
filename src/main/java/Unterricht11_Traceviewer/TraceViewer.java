package Unterricht11_Traceviewer;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.awt.*;
import java.nio.file.Paths;

public class TraceViewer {

    public static void main(String[] args) throws InterruptedException {

        Dimension dimension = Toolkit.getDefaultToolkit ().getScreenSize();
        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();
        Playwright playwright = Playwright.create();

        Browser browser = playwright.chromium ().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext context = browser.newContext();

        // Start tracing before creating / navigating a page.
        context.tracing().start(new Tracing. StartOptions()
                .setScreenshots (true)
                .setSnapshots (true)
                .setSources (true));

        Page page =context.newPage();
        page.setViewportSize (width, height);

        page.navigate( "https://www.ebay.de/");

        // getByRole
        Locator searchEbay = page.getByPlaceholder("Bei eBay finden");
        searchEbay.click();
        searchEbay.fill(  "Handy");

        Thread.sleep( 2000);

        //stop trace viewer
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));

        context.close();
        page.close();
        playwright.close();

    }

}
