package Unterricht09_FileDownload;

import com.microsoft.playwright.*;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileDownload01 {

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
        System.out.println("page" + download.page().title());
        System.out.println("url" + download.url());
        System.out.println("path" + download.path().toString());

        String  filePath= "src/main/java/utilities/downloads/";

        // Wait for the download process to complete and save the downloaded file somewhere
        download.saveAs (Paths.get( filePath, download.suggestedFilename()));


        boolean isFileDoownloaded= Files.exists(Paths.get(filePath, download.suggestedFilename()));
        assert isFileDoownloaded;
        System.out.println("dosya durumu :"+isFileDoownloaded);

        //download.delete();

        Files.deleteIfExists(Paths.get(filePath, download.suggestedFilename()));

        page.close();
        browser.close();
        playwright.close();

    }
}
