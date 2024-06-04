package Controller;

import Manager.AlertManger;
import Manager.TimeManager;
import Manager.UserManager;
import Model.ClassicModel;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.example.cs109project.MouseClick;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javafx.geometry.Pos.CENTER;

public class ClassicFromFileController extends AbstractController {
    protected ClassicModel gridNumber; // Assuming a 4x4 grid for 2048 game
    //    protected Classic gridNumberNew = new Classic(10, 10); // Assuming a 4x4 grid for 2048 game
    @FXML
//    Button[][] buttons;

            Label[][] labels;

    public ClassicFromFileController(int xCount, int yCount) {
//       super(xCount, yCount);
        this.xCount = xCount;
        this.yCount = yCount;
        gridNumber = new ClassicModel(xCount, yCount);
//        buttons = new Button[xCount][yCount];
        labels = new Label[xCount][yCount];
    }

    public ClassicFromFileController() {
        this(4, 4);
    }

    @FXML
    public void initialize() {
        initializeLabels();
        //***Leo wrote this!!!
        stepsCounter = steps;
        gridNumber.setTotalPoints(points);
        Steps.setText(String.valueOf(steps));
        Points.setText(String.valueOf(points));
        int hour = seconds / 3600;
        int minute = (seconds % 3600) / 60;
        int second = seconds % 60;
        labelTime.setText(String.format("Time:%02d:%02d:%02d", hour, minute, second));
        for (int i = 0; i < labels.length; i++) {
            for (int j = 0; j < labels[i].length; j++) {
                labels[i][j].setText(gameData.get(i * labels.length + j));
                labels[i][j].setStyle("-fx-background-color: " + getColor(gridNumber.getNumber(i, j)) + ";");
                gridNumber.getNumbers()[i][j] = Integer.parseInt(gameData.get(i * labels.length + j));
            }
        }
        refreshScene();//

        // Set the width and height of buttonBGM
        buttonBGM.setPrefWidth(60);
        buttonBGM.setPrefHeight(50);
        // Initialize the background music
        Media bgm = new Media(Objects.requireNonNull(getClass().getResource("/Jay Chou 路小雨.mp3")).toString());
        mediaPlayer = new MediaPlayer(bgm);
        playIcon = new Image(String.valueOf(Objects.requireNonNull(getClass().getResource("/play.png")).toExternalForm()));
        pauseIcon = new Image(String.valueOf(Objects.requireNonNull(getClass().getResource("/pause.png")).toExternalForm()));
        // Set the initial icon to play
        iconImage.setImage(playIcon);
        //***end
        iconImage.setImage(playIcon);
//        popImage.setImage(pop);
    }

    public void initializeLabels() {
        for (int row = 0; row < gridNumber.getNumbers().length; row++) {
            for (int col = 0; col < gridNumber.getNumbers()[0].length; col++) {
                labels[row][col] = new Label();
                labels[row][col].setAlignment(CENTER);
                labels[row][col].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                GridPane.setHgrow(labels[row][col], Priority.ALWAYS);
                GridPane.setVgrow(labels[row][col], Priority.ALWAYS);
                GridPane.setConstraints(labels[row][col], col, row);
                Board.getChildren().add(labels[row][col]);
            }
        }

        gridNumber.initialNumbers();
        for (int i = 0; i < gridNumber.getNumbers().length; i++) {
            for (int j = 0; j < gridNumber.getNumbers()[0].length; j++) {
                labels[i][j].setText(String.valueOf(gridNumber.getNumber(i, j)));
                labels[i][j].setStyle("-fx-background-color: " + getColor(gridNumber.getNumber(i, j)) + ";");
            }
        }
        bindKey();
    }

    //match the numbers[][] with the labels

    public void refreshScene() {
        for (int i = 0; i < gridNumber.getNumbers().length; i++) {
            for (int j = 0; j < gridNumber.getNumbers()[0].length; j++) {
                labels[i][j].setText(String.valueOf(gridNumber.getNumber(i, j)));
                labels[i][j].setStyle("-fx-background-color: " + getColor(gridNumber.getNumber(i, j)) + ";");
            }
        }

        Steps.setText(String.valueOf(stepsCounter));
        points = gridNumber.getTotalPoints();
        Points.setText(String.valueOf(points));
    }

    @FXML
    void down(ActionEvent event) {
        //***Leo wrote the determination of the timeline status.
        if (timeManager.isPaused()) {
            return;
        }//***end
        //***Leo wrote this!!!
        click();
        timeInitialize(timeManager);
        //***end
        int[][] oldNumbers = new int[gridNumber.getNumbers().length][];
        for (int i = 0; i < gridNumber.getNumbers().length; i++) {
            oldNumbers[i] = gridNumber.getNumbers()[i].clone();
        }
        gridNumber.moveDown();

        if (gridNumber.isActionPerformed(gridNumber.getNumbers(), oldNumbers)) {

            gridNumber.generateANumber(random.nextInt(2) == 0 ? 2 : 4); // Generate a new number after each move
            gridNumber.printNumber(); // Print the grid to console for debugging
            System.out.println();
            stepsCounter++;
            refreshScene();
            try {
                ifGameOver(gridNumber, mediaPlayer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void left(ActionEvent event) {
        //***Leo wrote the determination of the timeline status.
        if (timeManager.isPaused()) {
            return;
        }//***end
        //***Leo wrote this!!!
        click();
        timeInitialize(timeManager);
        //***end
        int[][] oldNumbers = new int[gridNumber.getNumbers().length][];
        for (int i = 0; i < gridNumber.getNumbers().length; i++) {
            oldNumbers[i] = gridNumber.getNumbers()[i].clone();
        }
        gridNumber.moveLeft();

        if (gridNumber.isActionPerformed(oldNumbers, gridNumber.getNumbers())) {


            gridNumber.generateANumber(random.nextInt(2) == 0 ? 2 : 4); // Generate a new number after each move
            gridNumber.printNumber(); // Print the grid to console for debugging
            System.out.println();
            stepsCounter++;
            refreshScene();
            try {
                ifGameOver(gridNumber, mediaPlayer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        if (gridNumber.isGameOver()) {
//            System.out.println("Game Over!");
//        }
    }

    @FXML
    void right(ActionEvent event) {
        //***Leo wrote the determination of the timeline status.
        if (timeManager.isPaused()) {
            return;
        }//***end
        //***Leo wrote this!!!
        click();
        timeInitialize(timeManager);
        //***end
        int[][] oldNumbers = new int[gridNumber.getNumbers().length][];
        for (int i = 0; i < gridNumber.getNumbers().length; i++) {
            oldNumbers[i] = gridNumber.getNumbers()[i].clone();
        }
        gridNumber.moveRight();

        if (gridNumber.isActionPerformed(gridNumber.getNumbers(), oldNumbers)) {


            gridNumber.generateANumber(random.nextInt(2) == 0 ? 2 : 4); // Generate a new number after each move
            gridNumber.printNumber(); // Print the grid to console for debugging
            System.out.println();
            stepsCounter++;
            refreshScene();
            try {
                ifGameOver(gridNumber, mediaPlayer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        if (gridNumber.isGameOver()) {
//            System.out.println("Game Over!");
//        }
    }

    @FXML
    void up(ActionEvent event) {
        //***Leo wrote the determination of the timeline status.
        if (timeManager.isPaused()) {
            return;
        }//***end
        //***Leo wrote this!!!
        click();
        timeInitialize(timeManager);
        //***end
        int[][] oldNumbers = new int[gridNumber.getNumbers().length][];
        for (int i = 0; i < gridNumber.getNumbers().length; i++) {
            oldNumbers[i] = gridNumber.getNumbers()[i].clone();
        }
        gridNumber.moveUp();

        if (gridNumber.isActionPerformed(gridNumber.getNumbers(), oldNumbers)) {


            gridNumber.generateANumber(random.nextInt(2) == 0 ? 2 : 4); // Generate a new number after each move
            gridNumber.printNumber(); // Print the grid to console for debugging
            System.out.println();
            stepsCounter++;
            refreshScene();
            try {
                ifGameOver(gridNumber, mediaPlayer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        if (gridNumber.isGameOver()) {
//            System.out.println("Game Over!");
//        }
    }


    public void bindKey() {
        // Make sure the scene can receive key press events
//        Right.setFocusTraversable(true);

        // Define what happens when a key is pressed
        Platform.runLater(() -> {
            Right.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    switch (event.getCode()) {
                        case D, RIGHT -> {
                            right(new ActionEvent());
                        }
                        case W, UP -> {
                            up(new ActionEvent());
                        }
                        case A, LEFT -> {
                            left(new ActionEvent());
                        }
                        case S, DOWN -> {
                            down(new ActionEvent());
                        }
                    }
                }
            });
        });
    }

    public void restart() {
        click();
        stepsCounter = 0;
        gridNumber.setTotalPoints(0);
        gridNumber.initialNumbers();
        refreshScene();
        timeManager.setSeconds(0);
        labelTime.setText("Time: 00:00:00");
        Steps.setText("0");
        timeManager.setSeconds(0);
        if (timeManager.getTimeline() != null) {
            timeManager.getTimeline().stop();
        }
    }

    //***The code bellow are written by Leo.
    @FXML
    private Button buttonExist;

    @FXML
    private Button buttonPause;

    @FXML
    private Button buttonResume;

    @FXML
    private Label labelTime;

    TimeManager timeManager = new TimeManager();
    TimeManager timeManagerForAutoSave = new TimeManager();
    UserManager userManager = new UserManager();
    AlertManger alertManger = new AlertManger();
    MouseClick ms = new MouseClick();
    private boolean isFirstClick = true;
    public static String username;
    public static String gameName;
    public static int seconds;
    public static int steps;
    public static List<String> gameData = new ArrayList<>();
    public static int points;
    private final static String gameMode = "Classic Mode";

    @FXML
    void pause(MouseEvent event) {
        click();
        timeManager.pauseTime();
    }

    @FXML
    void resume(MouseEvent event) {
        click();
        timeManager.resumeTime();
    }

    @FXML
    void saveAndExist(MouseEvent event) throws IOException {
        click();
        mediaPlayer.stop();
        timeManagerForAutoSave.stopAutoSave();
        List<String> gameData = this.getGameData();
        int seconds = timeManager.getSeconds();
        userManager.writeInUserData(username, gameMode, gameData, seconds, stepsCounter, gameName, Points.getText());
        alertManger.showSuccessAlert("You can exit safely now.", "Data saved.");
        timeManager.stopAutoSave();
        ms.mouseClick(event, "readFileOrCreateNewFileForUser");
    }

    //this method is used to start auto-saving and time-counting when a button is clicked or a keyboard is pressed
    private void timeInitialize(TimeManager timeManager) {
        timeManager.startTime(labelTime, seconds);
        if (isFirstClick || timeManagerForAutoSave.getTimeline() == null || timeManagerForAutoSave.getTimeline().getStatus() != Timeline.Status.RUNNING) {
            // 第一次点击时开启自动定时记录数据的功能
            timeManagerForAutoSave.autoSaveGame(username, gameMode, this, stepsCounter, gameName, Points);
            isFirstClick = false;
        }
    }


    public List<String> getGameData() {
        List<String> gameData = new ArrayList<>();
        for (Label[] label : this.labels) {
            for (Label value : label) {
                gameData.add(value.getText());
            }
        }
        return gameData;
    }

    private MediaPlayer mediaPlayer;
    private Image playIcon;
    private Image pauseIcon;

    @FXML
    private ImageView iconImage;
    @FXML
    private Button buttonBGM;

    @FXML
    void playBGM(MouseEvent event) {
        if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayer.stop();
            iconImage.setImage(playIcon); // Set the icon to play when the music is stopped
        } else {
            mediaPlayer.play();
            iconImage.setImage(pauseIcon); // Set the icon to pause when the music is playing
        }
    }
}
