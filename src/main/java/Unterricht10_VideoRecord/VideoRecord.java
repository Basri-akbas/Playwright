package Unterricht10_VideoRecord;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.*;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.microsoft.playwright.options.AriaRole.TEXTBOX;

public class VideoRecord {

    public static void main(String[] args) throws InterruptedException, IOException {

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        String tarih = new SimpleDateFormat(  "_hh_mm_ss_ddMMyyy").format(new Date());
        String filePath = "src/main/java/utilities/videos/" + tarih;

        BrowserContext context = browser.newContext(new Browser. NewContextOptions().
                setRecordVideoDir(Paths.get(filePath)));

        Page page = context.newPage();
        page.setViewportSize (width, height);
        page.navigate( "https://www.ebay.de/");

        // getByRole
        Locator searchEbay = page.getByPlaceholder("Bei eBay finden");
        searchEbay.click();
        searchEbay.fill(  "Handy");

        Thread.sleep( 2000);

        Path path = page.video().path();
        System.out.println(path);

        context.close();
        page.close();
        playwright.close();


    }
}