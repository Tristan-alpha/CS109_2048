package org.example.cs109project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class WarningForGuest {

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonNext;

    private MouseClick ms = new MouseClick();

    @FXML
    void goBack(MouseEvent event) throws IOException {
        click();
        ms.mouseClick(event, "welcome", "WelcomeStyle");
    }

    @FXML
    void goGaming(MouseEvent event) throws IOException {
        click();
        ms.mouseClick(event, "ClassicForGuest","Gaming");
    }
    Media click = new Media(Objects.requireNonNull(getClass().getResource("/MouseclickEffect.mp3")).toExternalForm());
    MediaPlayer clickPlayer = new MediaPlayer(click);
    void click() {
        clickPlayer.play();
    }
}
