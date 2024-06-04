package org.example.cs109project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class FailToRegister {

    @FXML
    private Button buttonGoBack;

    @FXML
    private Label info;

    public Label getInfo() {
        return info;
    }

    MouseClick ms = new MouseClick();

    @FXML
    void goBackToRegister(MouseEvent event) throws IOException {
        ms.mouseClick(event, "welcome");
    }

}
