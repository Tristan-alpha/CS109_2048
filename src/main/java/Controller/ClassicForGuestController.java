package Controller;

import Manager.AlertManger;
import Manager.TimeManager;
import Manager.UserManager;
import Model.ClassicModel;
import javafx.animation.ScaleTransition;
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
import javafx.util.Duration;
import org.example.cs109project.MouseClick;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static javafx.geometry.Pos.CENTER;

public class ClassicForGuestController extends AbstractController {
    protected ClassicModel gridNumber; // Assuming a 4x4 grid for 2048 game
    //    protected Classic gridNumberNew = new Classic(10, 10); // Assuming a 4x4 grid for 2048 game
    @FXML
//    Button[][] buttons;

            Label[][] labels;

    public ClassicForGuestController(int xCount, int yCount) {
//       super(xCount, yCount);
        this.xCount = xCount;
        this.yCount = yCount;
        gridNumber = new ClassicModel(xCount, yCount);
//        buttons = new Button[xCount][yCount];
        labels = new Label[xCount][yCount];
    }

    public ClassicForGuestController() {
        this(4, 4);
    }

    @FXML
    public void initialize() {
        initializeLabels();

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
        refreshScene();
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
        int points = gridNumber.getTotalPoints();
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
//                            timeInitialize(timeManager);
                            right(new ActionEvent());
                        }
                        case W, UP -> {
//                            timeInitialize(timeManager);
                            up(new ActionEvent());
                        }
                        case A, LEFT -> {
//                            timeInitialize(timeManager);
                            left(new ActionEvent());
                        }
                        case S, DOWN -> {
//                            timeInitialize(timeManager);
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
        if (timeManager.getTimeline() != null) {
            timeManager.getTimeline().stop();
        }
    }

    //The code bellow are written by Leo.
    @FXML
    private Button buttonExist;

    @FXML
    private Button buttonPause;

    @FXML
    private Button buttonResume;

    @FXML
    private Label labelTime;

    TimeManager timeManager = new TimeManager();
    UserManager userManager = new UserManager();
    AlertManger alertManger = new AlertManger();
    MouseClick ms = new MouseClick();
    private boolean isFirstClick = true;
    public static String username;
    public static String gameName;

    @FXML
    void pause(MouseEvent event) {
        timeManager.pauseTime();
    }

    @FXML
    void resume(MouseEvent event) {
        timeManager.resumeTime();
    }

    //this method is used to start auto-saving and time-counting when a button is clicked or a keyboard is pressed
    private void timeInitialize(TimeManager timeManager) {
        timeManager.startTime(labelTime);
    }


    public List<String> getGameData() {
        List<String> gameData = new ArrayList<>();
        for (Label[] label : labels) {
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

    //Try some merging animation. Where to invoke them? Ask Tristan.
    public void mergeCells(int row, int col) {
        // Get the label that represents the cell
        Label label = labels[row][col];

        // Create a scale transition that enlarges the label
        ScaleTransition st = new ScaleTransition(Duration.millis(200), label);
        st.setByX(0.2); // 20% increase in width
        st.setByY(0.2); // 20% increase in height
        st.setAutoReverse(true); // Automatically reverse the transition
        st.setCycleCount(2); // Play the transition twice (enlarge then shrink)

        // Start the transition
        st.play();
    }

}

