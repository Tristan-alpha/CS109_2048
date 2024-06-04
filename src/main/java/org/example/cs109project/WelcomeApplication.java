package org.example.cs109project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class WelcomeApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("welcome.fxml")));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/cssfiles/WelcomeStyle.css")).toExternalForm());
//        Font merriweather = Font.loadFont(getClass().getResourceAsStream("/fonts/Merriweather-Light.ttf"), 20);
//        System.out.println(merriweather);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // The stage is maximized
                // Adjust the size and position of the scene
                scene.getRoot().setScaleX(1.5); // Increase the scale of the scene
                scene.getRoot().setScaleY(1.5);
                scene.getRoot().setLayoutX((stage.getWidth() - scene.getWidth()) / 2); // Center the scene
                scene.getRoot().setLayoutY((stage.getHeight() - scene.getHeight()) / 2);
            } else {
                // The stage is not maximized
                // Reset the size and position of the scene
                scene.getRoot().setScaleX(1.0); // Reset the scale of the scene
                scene.getRoot().setScaleY(1.0);
                scene.getRoot().setLayoutX(0); // Reset the position of the scene
                scene.getRoot().setLayoutY(0);
            }
        });
        if (root instanceof Region) {
            Image image = new Image("/GrayDotBackground.jpg");
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImage);
            ((Region) root).setBackground(background);
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
