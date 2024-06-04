package Controller;

import Model.Customized;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.util.Objects;

import static javafx.geometry.Pos.CENTER;

public class CustomizedController extends AbstractController {

    @FXML
    private Button OK = new Button();
    private Button[][] buttons;
    private int[][] buttonStates;

    private Customized gridNumber;

    public CustomizedController() {

        this.xCount = 10;
        this.yCount = 10;
        gridNumber = new Customized(xCount, yCount);
//         buttons = new Button[xCount][yCount];
        labels = new Label[xCount][yCount];
        buttons = new Button[xCount][yCount];
        buttonStates = new int[xCount][yCount];
    }

    @FXML
    public void initialize() {
        //***Leo added this.
        // Set the width and height of buttonBGM
        buttonBGM.setPrefWidth(60);
        buttonBGM.setPrefHeight(50);
        // Initialize the background music
        Media bgm = new Media(Objects.requireNonNull(getClass().getResource("/Jay Chou Secret.mp3")).toString());
        mediaPlayer = new MediaPlayer(bgm);
        playIcon = new Image(String.valueOf(Objects.requireNonNull(getClass().getResource("/play.png")).toExternalForm()));
        pauseIcon = new Image(String.valueOf(Objects.requireNonNull(getClass().getResource("/pause.png")).toExternalForm()));
        // Set the initial icon to play
        iconImage.setImage(playIcon);
        //***end
        popImage.setImage(popIcon);

        Board.setStyle("-fx-grid-lines-visible: false;");
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                buttons[i][j] = new Button();
                final int x = i;
                final int y = j;
                buttons[i][j].setOnAction((ActionEvent e) -> {
                    buttonStates[x][y] = 1;
                    //set the button color to orange
                    buttons[x][y].setStyle("-fx-background-color: #FFA500");
                });
//                buttons[i][j].setStyle("-fx-background-color: #FFFFFF");
                buttons[i][j].setMaxWidth(Double.MAX_VALUE);
                buttons[i][j].setMaxHeight(Double.MAX_VALUE);

                buttons[i][j].setFocusTraversable(true);

                Board.add(buttons[i][j], j, i);
            }
        }


        gridNumber.setButtonStatus(buttonStates);
//        OK.setOnAction((ActionEvent e) -> {
//            hideButtons();
//        });
//        initializeLabels();

        Platform.runLater(() -> {
            OK.requestFocus();
        });
        bindKey();
        //Bugs need to be fixed: up arrow can not be used
    }


    public void hideButtons() {

        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                buttons[i][j].setVisible(false);
            }
        }

        for (int i = 0; i < xCount; i++) {//put the labels in the center
            for (int j = 0; j < yCount; j++) {
//                if(buttonStates[row][col] == 1) {
                labels[i][j] = new Label();
                labels[i][j].setAlignment(CENTER);
                labels[i][j].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                GridPane.setHgrow(labels[i][j], Priority.ALWAYS);
                GridPane.setVgrow(labels[i][j], Priority.ALWAYS);
                GridPane.setConstraints(labels[i][j], j, i);
                Board.getChildren().add(labels[i][j]);

                labels[i][j].setMouseTransparent(true);
            }
        }


        gridNumber.initialNumbers();
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                labels[i][j].setText(String.valueOf(gridNumber.getNumber(i, j)));
                labels[i][j].setStyle("-fx-background-color: " + getColor(gridNumber.getNumber(i, j)) + ";");
            }
        }

        //permanently hide the grids with 0
        for (int i = 0; i < xCount; i++) {
            for (int j = 0; j < yCount; j++) {
                if (buttonStates[i][j] == 0) {
                    labels[i][j].setVisible(false);
                }
            }
        }

        gridNumber.printNumber();
    }


    public void refreshScene() {
        for (int i = 0; i < gridNumber.getNumbers().length; i++) {
            for (int j = 0; j < gridNumber.getNumbers()[0].length; j++) {
                labels[i][j].setText(String.valueOf(gridNumber.getNumber(i, j)));
                labels[i][j].setStyle("-fx-background-color: " + getColor(gridNumber.getNumber(i, j)) + ";");
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
            try {
                ifGameOver(gridNumber, mediaPlayer);
            } catch (IOException e) {
                e.printStackTrace();
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
            try {
                ifGameOver(gridNumber, mediaPlayer);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            try {
                ifGameOver(gridNumber, mediaPlayer);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public int[][] getButtonStates() {
        return buttonStates;
    }

    //***Leo added this.
    @FXML
    private Button buttonBGM;

    @FXML
    private ImageView iconImage;

    private MediaPlayer mediaPlayer;
    private Image playIcon;
    private Image pauseIcon;
    private Image popIcon = new Image(String.valueOf(Objects.requireNonNull(getClass().getResource("/magic.png")).toExternalForm()));

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

    int whetherClicked = 0;

    @FXML
    private ImageView popImage;

    public void eliminateTiles() {
        if (whetherClicked == 0) {
            whetherClicked = 1;
            for (int i = 0; i < xCount; i++) {
                for (int j = 0; j < yCount; j++) {


                    if (buttonStates[i][j] == 1) {

                        buttons[i][j].setVisible(true);
                        buttons[i][j].setOpacity(0);
                        final int x = i;
                        final int y = j;
                        buttons[i][j].setOnAction(/*ActionEvent */(ActionEvent e) -> {
//                    System.out.println("Button clicked!");
                            gridNumber.setNumbers(x, y);

                            gridNumber.setTotalPoints(gridNumber.getTotalPoints() + gridNumber.getNumber(x, y));
                            refreshScene();
                        });

                    }
                }
            }
//        labels[i][j].setMouseTransparent(true);
        } else {
            whetherClicked = 0;
            for (int i = 0; i < xCount; i++) {
                for (int j = 0; j < yCount; j++) {
                    buttons[i][j].setVisible(false);
                }
            }
        }
    }
}