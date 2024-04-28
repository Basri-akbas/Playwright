package Unterricht09_FileDownload;

import com.microsoft.playwright.*;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileDownload02 {

    public static void main(String[] args) throws InterruptedException, IOException {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless (false));

        Page page =browser.newPage();
        page.setViewportSize (width, height);

        page.navigate("https://demoqa.com/upload-download");

        // Wait for the download to start
        Download download = page.waitForDownload (() -> {

            // Perform the action that initiates download
            page.getByText("Download").last().click();
        });


        String tarih = new SimpleDateFormat( "_hh_mm_ss_ddMMyyy").format(new Date());
        String filePath1 ="src/main/java/utilities/downloads/" + tarih + "file.jpg";

        // Wait for the download process to complete and save the downloaded file somewhere

        download.saveAs (Paths.get( filePath1));

        boolean isFileDoownloaded= Files.exists(Paths.get(filePath1));
        assert isFileDoownloaded;


        page.close();
        browser.close();
        playwright.close();
    }
}
