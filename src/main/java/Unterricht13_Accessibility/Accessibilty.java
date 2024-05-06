package Unterricht13_Accessibility;

import com.deque.html.axecore.playwright.*; // 1
import com.deque.html.axecore.results.AxeResults;

import org.junit.jupiter.api.*;
import com.microsoft.playwright.*;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
public class Accessibilty {

    @Test // 2
    void shouldNotHaveAutomaticallyDetectableAccessibilityIssues() throws Exception {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext context = browser.newContext();
        Page  page = context.newPage();

        page.navigate("https://your-site.com/"); // 3

        AxeResults accessibilityScanResults = new AxeBuilder(page).analyze(); // 4

        assertEquals(Collections.emptyList(), accessibilityScanResults.getViolations()); // 5

        page.close();
        browser.close();
        playwright.close();
    }

    @Test
    void navigationMenuFlyoutShouldNotHaveAutomaticallyDetectableAccessibilityViolations() throws Exception {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext context = browser.newContext();
        Page  page = context.newPage();

        page.navigate("https://your-site.com/");

        page.locator("//button[@aria-label='Navigation Menu']").click();

        // It is important to waitFor() the page to be in the desired
        // state *before* running analyze(). Otherwise, axe might not
        // find all the elements your test expects it to scan.
        page.locator("#navigation-menu-flyout").waitFor();

        AxeResults accessibilityScanResults = new AxeBuilder(page)
                .include(Arrays.asList("#navigation-menu-flyout"))
                .analyze();

        assertEquals(Collections.emptyList(), accessibilityScanResults.getViolations());

        AxeResults accessibilityScanResults1 = new AxeBuilder(page)
                .withTags(Arrays.asList("wcag2a", "wcag2aa", "wcag21a", "wcag21aa"))
                .analyze();

        assertEquals(Collections.emptyList(), accessibilityScanResults.getViolations());

        page.close();
        browser.close();
        playwright.close();
    }
}
