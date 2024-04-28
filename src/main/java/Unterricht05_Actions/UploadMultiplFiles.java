package Unterricht05_Actions;

import com.microsoft.playwright.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class UploadMultiplFiles {

    public static void main(String[] args) throws InterruptedException {


        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        Page page = browser.newPage();

        page.navigate("https://demo.automationtesting.in/FileUpload.html");

        // Select multiple files
        Locator dosyaSecButton = page.locator("input[id='input-4']");

        String dosyaYolu = "C:/Users/basri/IdeaProjects/playwright/src/main/java/utilities/files/file1.txt";
        String dosyaYolu2 = "C:/Users/basri/IdeaProjects/playwright/src/main/java/utilities/files/file2.txt";

        dosyaSecButton.setInputFiles(new Path[] {Paths.get(dosyaYolu), Paths.get(dosyaYolu2)});

        Locator uploadButton = page.getByTitle("Upload selected files");
        uploadButton.click();


        Thread.sleep(5000);
        page.close();
        browser.close();
        playwright.close();


    }
}
