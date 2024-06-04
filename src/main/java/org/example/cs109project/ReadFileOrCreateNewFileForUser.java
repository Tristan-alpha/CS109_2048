package org.example.cs109project;

import Controller.ClassicController;
import Controller.ClassicFromFileController;
import Controller.LimitedTimeController;
import Controller.LimitedTimeFromFileController;
import Manager.AlertManger;
import Manager.UserManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadFileOrCreateNewFileForUser {

    @FXML
    private Button buttonFile00;

    @FXML
    private Button buttonFile01;

    @FXML
    private Button buttonFile02;

    @FXML
    private Button buttonFile03;

    @FXML
    private Button buttonFile10;

    @FXML
    private Button buttonFile11;

    @FXML
    private Button buttonFile12;

    @FXML
    private Button buttonFile13;

    @FXML
    private Button buttonNewFile;

    @FXML
    private Label textfieldWelcome;

    @FXML
    private TextField textfieldNewFileName;

    @FXML
    private ComboBox<String> comboBoxMode;

    //variables of manager classes
    private final MouseClick ms = new MouseClick();
    private final AlertManger alertManger = new AlertManger();
    private final UserManager userManager = new UserManager();
    //end

    //other variables
    private static String username;
    private boolean dataFileInitialized = true;
    //end

    @FXML
    void newFileGaming(MouseEvent event) throws IOException {
        click();
        if (!textfieldNewFileName.getText().isEmpty()) {
            String selectedMode = comboBoxMode.getValue();
            if (selectedMode == null) {
                alertManger.showFailedAlert("no mode selected");
            } else {
                if (userManager.reader(username).size() >= 8) {
                    alertManger.showFailedAlert("no more space");
                    return;
                }
                switch (selectedMode) {
                    case "Classic Mode":
                        ClassicController.gameName = textfieldNewFileName.getText();
                        ClassicController.username = username;
                        ms.mouseClick(event, "Classic", "Gaming");
                        break;
                    case "Time-limited Mode":
//                        LimitedTimeController.gameMode = selectedMode;
                        LimitedTimeController.gameName = textfieldNewFileName.getText();
                        LimitedTimeController.username = username;
                        ms.mouseClick(event, "selectTime", "Gaming");
                        break;
                    case "Customize Mode":
                        ms.mouseClick(event, "Customized");
                        break;
                }
            }
        } else {
            alertManger.showFailedAlert("empty file name");
        }
    }

    @FXML
    void loadGameFile1(MouseEvent event) throws IOException {
        loadFileGameFromIndex(event, 0);
    }

    @FXML
    void loadGameFile2(MouseEvent event) throws IOException {
        loadFileGameFromIndex(event, 1);
    }

    @FXML
    void loadGameFile3(MouseEvent event) throws IOException {
        loadFileGameFromIndex(event, 2);
    }

    @FXML
    void loadGameFile4(MouseEvent event) throws IOException {
        loadFileGameFromIndex(event, 3);
    }

    @FXML
    void loadGameFile5(MouseEvent event) throws IOException {
        loadFileGameFromIndex(event, 4);
    }

    @FXML
    void loadGameFile6(MouseEvent event) throws IOException {
        loadFileGameFromIndex(event, 5);
    }

    @FXML
    void loadGameFile7(MouseEvent event) throws IOException {
        loadFileGameFromIndex(event, 6);
    }

    @FXML
    void loadGameFile8(MouseEvent event) throws IOException {
        loadFileGameFromIndex(event, 7);
    }

    static void setUsername(String s) {
        ReadFileOrCreateNewFileForUser.username = s;
    }

    public void initialize() throws IOException {
        textfieldWelcome.setText(String.format("Welcome, %s! Your previous files are as follows.", username));
        List<Button> buttonList = new ArrayList<>();
        buttonList.add(buttonFile00);
        buttonList.add(buttonFile01);
        buttonList.add(buttonFile02);
        buttonList.add(buttonFile03);
        buttonList.add(buttonFile10);
        buttonList.add(buttonFile11);
        buttonList.add(buttonFile12);
        buttonList.add(buttonFile13);
        comboBoxMode.setItems(FXCollections.observableArrayList(
                "Classic Mode", "Time-limited Mode", "Customize Mode"
        ));
        File file = new File(String.format("%s_Data.txt", username));
        Path path = Paths.get(String.format("%s_Data.txt", username));
        try {
            // Create the file if it doesn't exist
            if (!Files.exists(path)) {
                Files.createFile(path);
                dataFileInitialized = true;
                System.out.println("File created at: " + path.toAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!file.exists() && dataFileInitialized) {
            alertManger.showFailedAlert("file dose not exist");
        }
        userManager.updateFile(buttonList, username);
    }

    private void loadFileGameFromIndex(MouseEvent event, int index) throws IOException {
        click();
        String[] data = userManager.reader(username, index);
        if (data==null){
            alertManger.showFailedAlert("no file found");
            return;
        }
        boolean b1 = false;
        boolean b2 = false;
        if (data.length != 25) {//check if the length of data increased or decreased
            alertManger.showFailedAlert("chess board error");
            b1 = true;
        }
        for (int i = 9; i < 25; i++) {//check if -1, 3, 9 appears in the data
            if (!isPowerOfTwo(data[i]) || !Character.isDigit(data[i].charAt(0))) {
                alertManger.showFailedAlert("number error");
                b2 = true;
                break;
            }
        }
        if (!b1 && !b2) {
            switch (data[1]) {
                case "Classic":
                    ClassicFromFileController.username = data[0];
                    ClassicFromFileController.gameName = data[5];
                    ClassicFromFileController.seconds = Integer.parseInt(data[6]);
                    ClassicFromFileController.steps = Integer.parseInt(data[7]);
                    ClassicFromFileController.points = Integer.parseInt(data[8]);
                    ClassicFromFileController.gameData.addAll(Arrays.asList(data).subList(9, 25));
                    ms.mouseClick(event, "ClassicFromFile", "Gaming");
                    break;
                case "Time-limited":
                    LimitedTimeFromFileController.username = data[0];
                    LimitedTimeFromFileController.gameName = data[5];
                    LimitedTimeFromFileController.timeLimit = Integer.parseInt(data[6]);
                    LimitedTimeFromFileController.steps = Integer.parseInt(data[7]);
                    LimitedTimeFromFileController.points = Integer.parseInt(data[8]);
                    LimitedTimeFromFileController.gameData.addAll(Arrays.asList(data).subList(9, 25));
                    ms.mouseClick(event, "LimitedTimeFromFile", "Gaming");
                    break;
            }

        }
    }

    private boolean isPowerOfTwo(String s) {
        int n = Integer.parseInt(s);
        return ((n > 0) && ((n & (n - 1)) == 0)) || (n == 0);
    }

    @FXML
    private GridPane gridPane;

    Media clickSound = new Media(getClass().getResource("/MouseclickEffect.mp3").toExternalForm());
    MediaPlayer clickPlayer = new MediaPlayer(clickSound);

    void click() {
        clickPlayer.seek(javafx.util.Duration.ZERO);
        clickPlayer.play();
    }
}