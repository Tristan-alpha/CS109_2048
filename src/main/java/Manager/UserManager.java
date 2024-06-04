package Manager;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserManager {
    AlertManger alertManger = new AlertManger();

    //this method is used to write the correct user data into file, after a successful register
    //can use in both write in username&passwd and prompt text
    public void writeInUserData(String username, String info, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(username + " " + info);
            fileWriter.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeInUserData(String username, String gameMode, List<String> gameData, int seconds, int steps, String gameName, String points) {
        //get local time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String time = formatter.format(calendar.getTime());//"time" means when user saves the game
        //done
        String fileName = String.format("%s_Data.txt", username);
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String newLine = String.format("%s %s %s %s %d %d %s", username, gameMode, time, gameName, seconds, steps, points) + " ";
        for (String string : gameData) {
            newLine += string + " ";
        }

        int index = -1;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).split(" ")[5].equals(gameName)) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            lines.set(index, newLine);
        } else {
            lines.add(newLine);
        }

        try (FileWriter fileWriter = new FileWriter(fileName, false)) {
            for (String line : lines) {
                fileWriter.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //this method is used when login, check if the user data match
    public boolean authenciate(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //this method is used at registering, to check if the input username has existed
    public boolean isUsernameExists(String username) throws IOException {
        Path filepath = Paths.get("users.txt");
        List<String> lines = Files.readAllLines(filepath);
        for (String line : lines) {
            String[] parts = line.split(" ");
            if (username.equals(parts[0])) {
                return true;
            }
        }
        return false;
    }

    public void updateFile(List<Button> buttonList, String username) throws IOException {
        List<String> lines = reader(username);
        if (!lines.isEmpty()) {
            int index = lines.size();
            if (index > 8) {
                index = 8;
            }
            for (int i = 0; i < index; i++) {
                String gameMode = lines.get(i).split(" ")[1] + " " + lines.get(i).split(" ")[2];
                String time = lines.get(i).split(" ")[3] + " " + lines.get(i).split(" ")[4];
                String gameName = lines.get(i).split(" ")[5];
                buttonList.get(i).setWrapText(true);
//                buttonList.get(i).setText(String.format("%s %s at %s", gameMode, gameName, time));
                buttonList.get(i).setText(String.format("%s", gameName));

                // Create a tooltip
                Tooltip tooltip = new Tooltip(String.format("%s %s at %s", gameMode, gameName, time));
                // Add the tooltip to the button
                buttonList.get(i).setTooltip(tooltip);
            }
        }
    }

    //This method is used when given a username, and return a list of data
    public List<String> reader(String username) throws IOException {
        File file = new File(String.format("%s_Data.txt", username));
        if (file.exists()) {
            Path filepath = Paths.get(String.format("%s_Data.txt", username));
            return Files.readAllLines(filepath);
        } else {
            alertManger.showFailedAlert("file destroyed");
            return null;
        }
    }

    //This method is used when given a username and an index, and return a String[] data of specific index
    public String[] reader(String username, int index) throws IOException {
        File file = new File(String.format("%s_Data.txt", username));
        if (!file.exists()) {
            alertManger.showFailedAlert("file dose not exist");
        }
        Path filepath = Paths.get(String.format("%s_Data.txt", username));
        List<String> lines = Files.readAllLines(filepath);
        if (lines.isEmpty()){
            return null;
        }
        return lines.get(index).split(" ");
    }
}
