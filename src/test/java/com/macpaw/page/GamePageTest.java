package com.macpaw.page;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;


public class GamePageTest {

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("http://gabrielecirulli.github.io/2048/");

    }


    @Test
    public void play() {
        GamePage gamePage = PageFactory.initElements(driver, GamePage.class);
        gamePage.startGame();

    }


    @After
    public void tearDown() throws Exception {
        driver.quit();

    }
}