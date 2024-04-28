package Unterricht05_Actions;

import com.microsoft.playwright.*;

import java.awt.*;
import java.nio.file.Paths;

public class UploadFile1 {

    public static void main(String[] args) throws InterruptedException {


        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        Page page = browser.newPage();

        page.navigate("https://the-internet.herokuapp.com/upload");

        // Select one file
        Locator dosyaSecButton = page.locator("(//input[@id='file-upload'])");
       //String dosyaYolu = System.getProperty("user.home")+ "utilities/files/file1.txt";

        dosyaSecButton.setInputFiles(Paths.get("C:/Users/basri/IdeaProjects/playwright/src/main/java/utilities/files/file1.txt"));

        page.locator("(//input[@id='file-submit'])").click();


        Thread.sleep(5000);
        page.close();
        browser.close();
        playwright.close();


    }
}
