package com.macpaw.page;


import com.macpaw.util.HandleResult;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GamePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(GamePage.class);

    private WebDriver driver;
    volatile List<String> rows;
    int[][] containerValues;
    String message;


    public GamePage(WebDriver driver) {
        this.driver = driver;
    }

    @FindBy(how = How.CLASS_NAME, using = "grid-container")
    private WebElement container;

    @FindBy(how = How.CLASS_NAME, using = "score-container")
    private WebElement score;

    /**
     * Starts to play and print result of each action until game over or won
     */
    public void startGame() {
        LOGGER.info("Game is started at " + LocalDateTime.now().getMinute() + " min : " + LocalDateTime.now().getSecond() + " sec");

        do {
            doAction();
            printCurrentGrid();
        } while(isContinue());

        LOGGER.info("Game over at " + LocalDateTime.now().getMinute() + " min : " + LocalDateTime.now().getSecond() + " sec");
        LOGGER.info("GAME RESULT = " + message );
        LOGGER.info("SCORE = " + score.getText() );

    }

    private void doAction() {
        containerValues = new int[][] {{0,0,0,0}, {0,0,0,0}, {0,0,0,0} ,{0,0,0,0}};

        // get div children list and get their attribute css class
        rows = driver.findElements(By.cssSelector(".tile-container  >  *"))
                .stream().map(e -> e.getAttribute("class"))
                .collect(Collectors.toList());

        // write current grid state
        rows.forEach(name -> {
            int[] actionResult = HandleResult.parseClassName(name);
            containerValues[actionResult[2] - 1][actionResult[1] - 1] = actionResult[0];
        });

        // do game action
        String action = KeysEnum.values()[new Random().nextInt(4)].toString();
        container.sendKeys(Keys.valueOf(KeysEnum.values()[new Random().nextInt(4)].toString()));
        LOGGER.info("Move " + action.substring(6));
    }

    /**
     *
     * @return if css class contains "game" it means game is finished. Stop to continue game
     */
    private boolean isContinue() {

         message = driver.findElement(By.className("game-message")).getAttribute("class").substring(12);

        if(message.contains("game"))
            return false;

        return true;
    }

    private void printCurrentGrid() {

        Stream.of(containerValues).map(Arrays::toString).forEach(LOGGER::info);
    }

    private enum KeysEnum {
        ARROW_DOWN, ARROW_UP, ARROW_LEFT, ARROW_RIGHT
    }

}
