package fr.imt.cepi;


import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertEquals;

public class TestSelenium {
    private WebDriver driver;

    @BeforeClass
    public static void setupClass() {
        ChromeDriverManager.getInstance().setup();
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
    }

    @After
    public void tearDown() {
        if (driver != null)
            driver.quit();
    }

    @Test
    public void connectOK() throws Exception {
        driver.get("http://localhost:8080/MyServlet/");

        driver.findElement(By.name("login")).sendKeys("bertin");
        driver.findElement(By.name("password")).sendKeys("dominique");
        driver.findElement(By.name("connect")).click();

        assertEquals("Accueil", driver.getTitle());
        assertEquals("Bonjour BERTIN Dominique", driver.findElement(By.tagName("h1")).getText());
    }
}

