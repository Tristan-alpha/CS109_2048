package org.example.cs109project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class GameMode {

    @FXML
    private ToggleGroup Mode;

    @FXML
    private RadioButton buttonClassic;

    @FXML
    private RadioButton buttonCustomize;

    @FXML
    private Button buttonGo;

    @FXML
    private RadioButton buttonTimeLimited;
    private MouseClick ms = new MouseClick();

    @FXML
    void goGaming(MouseEvent event) throws IOException {
        ms.mouseClick(event, "Gaming");
    }

}
