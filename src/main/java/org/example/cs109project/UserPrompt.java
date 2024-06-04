package org.example.cs109project;

import Manager.AlertManger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class UserPrompt {

    @FXML
    private Button buttonSearch;

    @FXML
    private TextField textfieldUsername;

    @FXML
    private Button buttonBack;

    private final AlertManger alertManger = new AlertManger();
    private final MouseClick ms = new MouseClick();

    @FXML
    void search(MouseEvent event) throws IOException {
        String username = textfieldUsername.getText();
        String promptText = null;
        boolean hasPrompt = false;
        Path filepath = Paths.get("usersPromptText.txt");
        List<String> lines = Files.readAllLines(filepath);
        for (String line : lines) {
            String[] parts = line.split(" ");
            if (parts[0].equals(username)) {
                hasPrompt = true;
                promptText = parts[1];
                break;
            }
        }
        if (hasPrompt) {
            alertManger.showSuccessAlert(String.format("Your previous prompt text is: %s", promptText),
                    "Prompt found!");
        } else {
            alertManger.showFailedAlert("no previous prompt");
        }
    }

    @FXML
    void backToLogin(MouseEvent event) throws IOException {
        ms.mouseClick(event, "loginForUser");
    }

}
