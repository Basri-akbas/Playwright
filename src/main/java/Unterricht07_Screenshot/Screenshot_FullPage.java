package Unterricht07_Screenshot;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.awt.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screenshot_FullPage {

    public static void main(String[] args) {

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        Page page = browser.newPage();
        page.setViewportSize(width, height);

        page.navigate("https://www.ebay.com/");

        // Sayfanin resmini alalim
        String tarih = new SimpleDateFormat("_hh_mm_ss_ddMMyyy").format(new Date());
        String dosyaYolu = "src/main/java/utilities/screenShot/ " + tarih + ".jpg";

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(dosyaYolu)).setFullPage(true));

        page.close();
        browser.close();
        playwright.close();
    }
}
