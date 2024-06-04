package org.example.cs109project;

import Manager.AlertManger;
import Manager.TimeManager;
import Manager.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimeLimitedGaming {

    @FXML
    private Button buttonDown;

    @FXML
    private Button buttonExit;

    @FXML
    private Button buttonLeft;

    @FXML
    private Button buttonRight;

    @FXML
    private Button buttonUp;

    @FXML
    private Label label00;

    @FXML
    private Label label01;

    @FXML
    private Label label02;

    @FXML
    private Label label03;

    @FXML
    private Label label10;

    @FXML
    private Label label11;

    @FXML
    private Label label12;

    @FXML
    private Label label13;

    @FXML
    private Label label20;

    @FXML
    private Label label21;

    @FXML
    private Label label22;

    @FXML
    private Label label23;

    @FXML
    private Label label30;

    @FXML
    private Label label31;

    @FXML
    private Label label32;

    @FXML
    private Label label33;

    @FXML
    private Label labelStep;

    @FXML
    private Label labelTime;

    private final TimeManager timeManager = new TimeManager();
    private final UserManager userManager = new UserManager();
    private final AlertManger alertManger = new AlertManger();

    private final MouseClick ms = new MouseClick();

    public static double timeLimit;
    private static String gameName;
    private static String username;
    private int stepCounter;

    @FXML
    void buttonDown(MouseEvent event) {
        stepCounter++;
        labelStep.setText(String.valueOf(stepCounter));
//        timeManager.countdownWithTimeline(labelTime, timeLimit,);
    }

    @FXML
    void buttonLeft(MouseEvent event) {
        stepCounter++;
        labelStep.setText(String.valueOf(stepCounter));
//        timeManager.countdownWithTimeline(labelTime, timeLimit);
    }

    @FXML
    void buttonRight(MouseEvent event) {
        stepCounter++;
        labelStep.setText(String.valueOf(stepCounter));
//        timeManager.countdownWithTimeline(labelTime, timeLimit);
    }

    @FXML
    void buttonUp(MouseEvent event) {
        stepCounter++;
        labelStep.setText(String.valueOf(stepCounter));
//        timeManager.countdownWithTimeline(labelTime, timeLimit);
    }

    @FXML
    void saveAndExit(MouseEvent event) throws IOException {
//        pause(event);
        List<String> gameData = getGameData();
        int seconds = timeManager.getSeconds();
//        userManager.writeInUserData(username, gameData, seconds, stepCounter, gameName);
        alertManger.showSuccessAlert("You can exit safely now.", "Data saved.");
        ms.mouseClick(event, "readFileOrCreateNewFileForUser");
    }

    private List<String> getGameData() {
        List<String> gameData = new ArrayList<>();
        gameData.add(label00.getText());
        gameData.add(label01.getText());
        gameData.add(label02.getText());
        gameData.add(label03.getText());
        gameData.add(label10.getText());
        gameData.add(label11.getText());
        gameData.add(label12.getText());
        gameData.add(label13.getText());
        gameData.add(label20.getText());
        gameData.add(label21.getText());
        gameData.add(label22.getText());
        gameData.add(label23.getText());
        gameData.add(label30.getText());
        gameData.add(label31.getText());
        gameData.add(label32.getText());
        gameData.add(label33.getText());
        return gameData;
    }

    private void setGameData(List<String> gameData) {
        List<Label> labelList = new ArrayList<>();
        labelList.add(label00);
        labelList.add(label01);
        labelList.add(label02);
        labelList.add(label03);
        labelList.add(label10);
        labelList.add(label11);
        labelList.add(label12);
        labelList.add(label13);
        labelList.add(label20);
        labelList.add(label21);
        labelList.add(label22);
        labelList.add(label23);
        labelList.add(label30);
        labelList.add(label31);
        labelList.add(label32);
        labelList.add(label33);
        if (!gameData.isEmpty()) {
            for (int i = 0; i < 16; i++) {
                labelList.get(i).setText(gameData.get(i));
            }
        }
    }

}
