package org.example.cs109project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class Welcome {

    @FXML
    private Button buttonGuest;

    @FXML
    private Button buttonRegister;

    @FXML
    private Button buttonUser;

    @FXML
    private Label labelWelcome;
    private final MouseClick ms = new MouseClick();

    @FXML
    void goAsGuest(MouseEvent event) throws IOException {
        ms.mouseClick(event, "warningForGuest", "Warning");
        click();
    }

    @FXML
    void goAsUSer(MouseEvent event) throws IOException {
        ms.mouseClick(event, "loginForUser", "LoginStyle");
        click();
    }

    @FXML
    void goRegister(MouseEvent event) throws IOException {
        ms.mouseClick(event, "creatingAccount", "RegisterStyle");
        click();
    }

    @FXML
    private Button buttonRanking;
    private Stage rankingStage;
    private boolean isRankingShown = false;

    private MouseClick mouseClick = new MouseClick();
    @FXML
    private ImageView rankingImage;

    @FXML
    void showRanking(MouseEvent event) throws IOException {
        click();
        if (rankingStage == null) {
            // Create the ranking stage
            rankingStage = new Stage();
            rankingStage.initStyle(StageStyle.UTILITY);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ranking.fxml")));
            Scene scene = new Scene(root);
            rankingStage.setScene(scene);
        }

        if (isRankingShown) {
            // Hide the ranking stage
            rankingStage.hide();
        } else {
            // Show the ranking stage
            rankingStage.show();
        }
        isRankingShown = !isRankingShown;
    }

    public void initialize() {
        Image rankImage = new Image(Objects.requireNonNull(getClass().getResource("/ranking.png")).toExternalForm());
        rankingImage.setImage(rankImage);
    }

    Media click = new Media(Objects.requireNonNull(getClass().getResource("/MouseclickEffect.mp3")).toExternalForm());
    MediaPlayer clickPlayer = new MediaPlayer(click);

    @FXML
    void click(MouseEvent event) {
        clickPlayer.seek(Duration.ZERO);
        clickPlayer.play();
    }

    void click() {
        clickPlayer.seek(Duration.ZERO);
        clickPlayer.play();
    }


}
