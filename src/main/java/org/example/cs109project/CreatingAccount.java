package org.example.cs109project;

import Manager.AlertManger;
import Manager.UserManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreatingAccount {

    @FXML
    private Button buttonCreateAccount;

    @FXML
    private PasswordField passwordfieldPassword;

    @FXML
    private PasswordField passwordfieldVerification;

    @FXML
    private TextField textfieldPrompt;

    @FXML
    private TextField textfieldUsername;
    MouseClick ms = new MouseClick();
    private final UserManager userManager = new UserManager();
    private final AlertManger alertManger = new AlertManger();

    @FXML
    void registered(MouseEvent event) throws IOException {
        click();
        String username = textfieldUsername.getText();
        String password = passwordfieldPassword.getText();
        String passwordVerification = passwordfieldVerification.getText();
        String promptText = textfieldPrompt.getText();
        if (username.isEmpty() || password.isEmpty() || passwordVerification.isEmpty()) {
            alertManger.showFailedAlert("empty fields");
        } else {
            if (userManager.isUsernameExists(username)) {
                alertManger.showFailedAlert("username existed");
            } else if (!password.equals(passwordVerification)) {
                alertManger.showFailedAlert("password inconsistent");
            } else {//register successfully
                userManager.writeInUserData(username, password, "users.txt");
                userManager.writeInUserData(username, promptText, "usersPromptText.txt");
                alertManger.showSuccessAlert("Success! You have your own account now.", "Register Successfully");
                ReadFileOrCreateNewFileForUser.setUsername(username);
                ms.mouseClick(event, "readFileOrCreateNewFileForUser","ReadFileStyle");
            }
        }
    }

    @FXML
    void back(MouseEvent event) throws IOException {
        click();
        ms.mouseClick(event, "welcome", "WelcomeStyle");
    }

    @FXML
    private Button back;
    @FXML
    private ImageView imageBack;

    Image image = new Image("/undo.png");

    public void initialize() {
        imageBack.setImage(image);
    }

    Media click = new Media(Objects.requireNonNull(getClass().getResource("/MouseclickEffect.mp3")).toExternalForm());
    MediaPlayer clickPlayer = new MediaPlayer(click);
    void click() {
        clickPlayer.play();
    }
}
