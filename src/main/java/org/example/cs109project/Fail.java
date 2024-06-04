package org.example.cs109project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Fail {

    @FXML
    private Button buttonBack;
    MouseClick mouseClick = new MouseClick();

    @FXML
    void back(MouseEvent event) throws IOException {
        mouseClick.mouseClick(event, "readFileOrCreateNewFileForUser","ReadFileStyle");
    }

}
