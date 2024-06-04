package org.example.cs109project;

import DataHolder.resumeGameData;
import Manager.AlertManger;
import Manager.TimeManager;
import Manager.UserManager;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Gaming {

    @FXML
    private Button buttonDown;

    @FXML
    private Button buttonExit;

    @FXML
    private Button buttonPause;

    @FXML
    private Button buttonResume;

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

    private int stepCounter;

    //variables of manager classes
    private final TimeManager timeManager = new TimeManager();
    private final UserManager userManager = new UserManager();
    private final AlertManger alertManger = new AlertManger();
    MouseClick ms = new MouseClick();

    //end

    //other variables
    private static String gameName;
    private static String username;
    private boolean isFirstClick = true;
    //end

    @FXML
    void buttonUp(MouseEvent event) {
        //methods to let grids move on [goOn()]
        stepCounter++;
        labelStep.setText(String.valueOf(stepCounter));
        timeManager.startWithSeconds(labelTime, Integer.parseInt(resumeGameData.seconds));
        timeInitialize(timeManager);
    }

    @FXML
    void buttonDown(MouseEvent event) {
        //
        stepCounter++;
        labelStep.setText(String.valueOf(stepCounter));
        timeManager.startWithSeconds(labelTime, Integer.parseInt(resumeGameData.seconds));
        timeInitialize(timeManager);
    }

    @FXML
    void buttonLeft(MouseEvent event) {
        //
        stepCounter++;
        labelStep.setText(String.valueOf(stepCounter));
        timeManager.startWithSeconds(labelTime, Integer.parseInt(resumeGameData.seconds));
        timeInitialize(timeManager);
    }

    @FXML
    void buttonRight(MouseEvent event) {
        //
        stepCounter++;
        labelStep.setText(String.valueOf(stepCounter));
        timeManager.startWithSeconds(labelTime, Integer.parseInt(resumeGameData.seconds));
        timeInitialize(timeManager);
    }

    @FXML
    void pause(MouseEvent event) {
        timeManager.pauseTime();
    }

    @FXML
    void resume(MouseEvent event) {
        timeManager.resumeTime();
    }

    @FXML
    void saveAndExit(MouseEvent event) throws IOException {
        pause(event);
        List<String> gameData = getGameData();
        int seconds = timeManager.getSeconds();
//        userManager.writeInUserData(username, gameData, seconds, stepCounter, gameName);
        alertManger.showSuccessAlert("You can exit safely now.", "Data saved.");
        ms.mouseClick(event, "readFileOrCreateNewFileForUser");
    }

    private void timeInitialize(TimeManager timeManager) {
        timeManager.startTime(labelTime);
        if (isFirstClick || timeManager.getTimeline().getStatus() != Timeline.Status.RUNNING || timeManager.getTimeline() == null) {
            // 第一次点击时开启自动定时记录数据的功能
//            timeManager.autoSaveGame(username, getGameData(), labelStep, gameName);
            isFirstClick = false;
        }
    }

    public void initialize() {
        System.out.println("Resume Game Steps: " + resumeGameData.steps);
        System.out.println("Resume Game Seconds: " + resumeGameData.seconds);
        stepCounter = Integer.parseInt(resumeGameData.steps);
        labelStep.setText(String.valueOf(stepCounter));
        gameName = resumeGameData.gameName;
        setGameData(resumeGameData.lastData);
//        timeManager.startWithSeconds(labelTime, Integer.parseInt(resumeGameData.seconds));
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

    static void receiveUsername(String s) {
        username = s;
    }
}



