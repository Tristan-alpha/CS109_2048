package org.example.cs109project;

import Controller.ClassicController;
import Controller.ClassicFromFileController;
import Manager.AlertManger;
import Manager.User;
import Manager.UserManager;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class LoginForUser {

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonLogin;

    @FXML
    private PasswordField passwordfieldPasswprd;

    @FXML
    private TextField textfieldUsername;

    @FXML
    private Button buttonForgetPassword;

    //variables of manager classes
    private final MouseClick ms = new MouseClick();
    private final AlertManger alertManger = new AlertManger();
    private final UserManager userManager = new UserManager();
    //end

    @FXML
    void goBack(MouseEvent event) throws IOException {
        click();
        ms.mouseClick(event, "welcome", "WelcomeStyle");
    }

    @FXML
    void login(MouseEvent event) throws IOException {
        click();
        String username = textfieldUsername.getText();
        ReadFileOrCreateNewFileForUser.setUsername(username);
        ClassicController.username = username;
        ClassicFromFileController.username = username;
        boolean isCorrectUser = userManager.authenciate(textfieldUsername.getText(), passwordfieldPasswprd.getText());
        if (isCorrectUser) {
            alertManger.showSuccessAlert(String.format("Welcome, %s!", username), "Login Successfully");
            ms.mouseClick(event, "readFileOrCreateNewFileForUser", "ReadFileStyle");
        } else {
            alertManger.showFailedAlert("wrong password");
        }

    }

    @FXML
    void forgetPassword(MouseEvent event) throws IOException {
        click();
        ms.mouseClick(event, "userPrompt");
    }

    public void initialize() {
        Scene scene = buttonLogin.getScene(); // Get the scene using any node in the scene
        if (scene != null) {
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/cssfiles/LoginStyle.css")).toExternalForm());
        }
    }

    Media click = new Media(Objects.requireNonNull(getClass().getResource("/MouseclickEffect.mp3")).toExternalForm());
    MediaPlayer clickPlayer = new MediaPlayer(click);

    void click() {
        clickPlayer.play();
    }
}
