package Manager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import org.example.cs109project.MouseClick;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class AlertManger {
    public void showSuccessAlert(String contentText, String title) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(contentText);
            alert.showAndWait();
        });
    }

    public void showTimeLimitedAlert(String title, String contentText, ActionEvent event, MouseClick ms) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(contentText);
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    try {
                        ms.mouseClick(event, "readFileOrCreateNewFileForUser");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    public boolean showFailedAlert(String messageType) {
        AtomicBoolean result = new AtomicBoolean(false);
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING); // 根据需要可以改为其他类型
            alert.setTitle("Failed");
            switch (messageType) {
                case "username existed":
                    alert.setHeaderText("Registration Failed");
                    alert.setContentText("Username already exists!");
                    break;
                case "password inconsistent":
                    alert.setHeaderText("Registration Failed");
                    alert.setContentText("Passwords do not match!");
                    break;
                case "wrong password":
                    alert.setHeaderText("Login Failed");
                    alert.setContentText("Wrong password. Pleas try again.");
                    break;
                case "no previous prompt":
                    alert.setHeaderText("No prompts found");
                    alert.setContentText("You have no previous prompts.");
                    break;
                case "empty file name":
                    alert.setHeaderText("empty file name");
                    alert.setContentText("Please enter a proper file name.");
                    break;
                case "empty fields":
                    alert.setHeaderText("empty fields");
                    alert.setContentText("Please make sure you have input nonempty fields.");
                    break;
                case "file destroyed":
                    alert.setHeaderText("file destroyed");
                    alert.setContentText("The file has been destroyed.");
                    break;
                case "file dose not exist":
                    alert.setTitle("Error Code: 101");
                    alert.setHeaderText("file does not exist");
                    alert.setContentText("There's an error about the file type.");
                    break;
                case "chess board error":
                    alert.setTitle("Error Code: 102");
                    alert.setHeaderText("Chess board error");
                    alert.setContentText("There's an error about the chess board.");
                    break;
                case "number error":
                    alert.setTitle("Error Code: 103");
                    alert.setHeaderText("Number error");
                    alert.setContentText("There's an error about the number.");
                    break;
                case "no mode selected":
                    alert.setHeaderText("No mode selected");
                    alert.setContentText("Please select a mode.");
                    break;
                case "no more space":
                    alert.setHeaderText("Space is used up.");
                    alert.setContentText("You have no more space for new games.");
                    break;
                case "no file found":
                    alert.setHeaderText("No file found");
                    alert.setContentText("You have not created this file.");
                    break;
                default:
                    alert.setHeaderText("Unknown Message");
                    alert.setContentText("An unknown error occurred!");
                    break;
            }
            result.set(alert.showAndWait().isPresent());
            alert.showAndWait();
        });
        return result.get();
    }
}

