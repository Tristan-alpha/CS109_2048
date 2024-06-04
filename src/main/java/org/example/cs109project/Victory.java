package org.example.cs109project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Victory {

    MouseClick ms = new MouseClick();

    @FXML
    private Button buttonBack;

    @FXML
    void back(MouseEvent event) throws IOException {
        ms.mouseClick(event, "readFileOrCreateNewFileForUser","ReadFileStyle");
    }

}
