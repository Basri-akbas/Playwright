package Unterricht05_Actions;

import com.microsoft.playwright.*;

import java.awt.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CheckBoxesRadioButtons {

    public static void main(String[] args) throws InterruptedException {

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) dimension.getWidth();
        int height = (int) dimension.getHeight();

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));

        Page page = browser.newPage();
        page.setViewportSize(width, height);

        page.navigate("https://demoqa.com/checkbox");

        Locator checkbox = page.getByText("Home");
        checkbox.click();

        // Checkboxes and radio buttons
        page.navigate("https://demoqa.com/radio-button");
        Locator impressiveButton = page.locator("(//label[@for='impressiveRadio'])");

        impressiveButton.check();
        assertThat(impressiveButton).isChecked();
        Thread.sleep(3000);

        /*
        impressiveButton.setChecked(true);
        assertThat(impressiveButton).isChecked();
        Thread.sleep(3000);

         */

        page.close();
        browser.close();
        playwright.close();


    }
}
