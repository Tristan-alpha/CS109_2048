package Controller;

import Manager.TimeManager;
import Model.ClassicModel;
import Model.Customized;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.example.cs109project.MouseClick;
import org.example.cs109project.Ranking;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static javafx.geometry.Pos.CENTER;

public class AbstractController {

    Random random = new Random();
    @FXML
    protected Label Steps;

    @FXML
    protected Label Points;

    @FXML
    protected Button Down;

    @FXML
    protected Button Left;

    @FXML
    protected Button Right;

    @FXML
    protected Button Up;

    @FXML
    protected GridPane Board;

    protected int xCount;

    protected int yCount;

    protected int stepsCounter;


    protected Customized gridNumber; // Assuming a 4x4 grid for 2048 game
    //    protected Classic gridNumberNew = new Classic(10, 10); // Assuming a 4x4 grid for 2048 game
    @FXML
    Button[][] buttons;

    Label[][] labels;

    public AbstractController() {
//        this.xCount = xCount;
//        this.yCount = yCount;
//        gridNumber = new ClassicModel(xCount, yCount);
//         buttons = new Button[xCount][yCount];
//         labels = new Label[xCount][yCount];
    }


    @FXML
    public void initialize() {
        initializeLabels();
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
            }
        }

        Steps.setText(String.valueOf(stepsCounter));
        Points.setText(String.valueOf(gridNumber.getTotalPoints()));
    }

    @FXML
    void down(ActionEvent event) {
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
            if (gridNumber.isGameVictory()) {
                System.out.println("You Win!");
            }
            if (gridNumber.isGameOver()) {
                System.out.println("Game Over!");
            }

        }

    }

    @FXML
    void left(ActionEvent event) {
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
        }
        if (gridNumber.isGameOver()) {
            System.out.println("Game Over!");
        }
    }

    @FXML
    void right(ActionEvent event) {
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
        }
        if (gridNumber.isGameOver()) {
            System.out.println("Game Over!");
        }
    }

    @FXML
    void up(ActionEvent event) {
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
        }
        if (gridNumber.isGameOver()) {
            System.out.println("Game Over!");
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
        stepsCounter = 0;
        gridNumber.setTotalPoints(0);
        gridNumber.initialNumbers();
        refreshScene();
    }

    //***Leo wrote this!!!
    MouseClick ms = new MouseClick();
    Media victorySound = new Media(Objects.requireNonNull(getClass().getResource("/Victory.mp3")).toExternalForm());
    Media gameOverSound = new Media(Objects.requireNonNull(getClass().getResource("/Gameover.mp3")).toExternalForm());
    MediaPlayer victoryPlayer = new MediaPlayer(victorySound);
    MediaPlayer gameOverPlayer = new MediaPlayer(gameOverSound);

    String getColor(int number) {
        switch (number) {
            case 2:
                return "#776e65";
            case 4:
                return "#ADD8E6";
            case 8:
                return "#f2b179";
            case 16:
                return "#f59563";
            case 32:
                return "#f67c5f";
            case 64:
                return "#f65e3b";
            case 128:
                return "#edcf72";
            case 256:
                return "#edcc61";
            case 512:
                return "#edc850";
            case 1024:
                return "#edc53f";
            case 2048:
                return "#edc22e";
            default:
                return "#cdc1b4"; // Default color for other numbers
        }
    }

    MouseClick mouseClick = new MouseClick();

    protected void ifGameOver(ActionEvent event, ClassicModel gridNumber) throws IOException {
        if (gridNumber.isGameOver()) {
            gameOverPlayer.play();
            mouseClick.mouseClick("fail");
        } else if (gridNumber.isGameVictory()) {
            victoryPlayer.play();
            mouseClick.mouseClick("victory");
        }
    }

    protected void ifGameOver(ClassicModel gridNumber, MediaPlayer mediaPlayer) throws IOException {
        if (gridNumber.isGameOver()) {
            gameOverPlayer.play();
            mouseClick.mouseClick("fail");
            mediaPlayer.stop();
        } else if (gridNumber.isGameVictory()) {
            victoryPlayer.play();
            mouseClick.mouseClick("victory");
            mediaPlayer.stop();
        }
    }

    protected void ifGameOver(Customized gridNumber, MediaPlayer mediaPlayer) throws IOException {
        if (gridNumber.isGameOver()) {
            gameOverPlayer.play();
            mouseClick.mouseClick("fail");
            mediaPlayer.stop();
        } else if (gridNumber.isGameVictory()) {
            victoryPlayer.play();
            mouseClick.mouseClick("victory");
            mediaPlayer.stop();
        }
    }

    public int getStepsCounter() {
        return stepsCounter;
    }
    Media click = new Media(getClass().getResource("/MouseclickEffect.mp3").toExternalForm());

    MediaPlayer clickPlayer = new MediaPlayer(click);

    void click() {
        clickPlayer.seek(Duration.ZERO);
        clickPlayer.play();
    }

}
