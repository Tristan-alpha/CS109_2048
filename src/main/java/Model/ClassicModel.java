package Model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Arrays;
import java.util.Random;

public class ClassicModel {

    protected final int X_COUNT;
    protected final int Y_COUNT;

    protected int[][] numbers;

    protected int[][] oldNumbers;

    protected int totalPoints = 0;


    static Random random = new Random();

    public ClassicModel(int xCount, int yCount) {
        this.X_COUNT = xCount;
        this.Y_COUNT = yCount;
        this.numbers = new int[this.X_COUNT][this.Y_COUNT];
        this.initialNumbers();
    }


    public void initialNumbers() {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                //todo: update generate numbers method
                numbers[i][j] = 0;
            }
        }
        generateANumber(2);
        generateANumber(4);
    }


    public void generateANumber(int number) {
        int randomX = random.nextInt(numbers.length);
        int randomY = random.nextInt(numbers[0].length);
        if (numbers[randomX][randomY] == 0) {
            numbers[randomX][randomY] = number;
            return;
        } else {
            generateANumber(number);
        }
    }

//    public void generateANumber(int trial) {
//        int x, y;
//        do {
//            x = random.nextInt(numbers.length);
//            y = random.nextInt(numbers[0].length);
//        } while (numbers[x][y] != 0);
//
//        // Generate a number (2 or 4)
//        numbers[x][y] = random.nextInt(2) * 2 + 2;
//    }

    public void moveRight() {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = numbers.length - 2; j >= 0; j--) {

                for (int k = 0; j + k < numbers.length - 1; k++) {
                    if (numbers[i][j + k] == numbers[i][j + k + 1] && numbers[i][j + k] != 0) {
                        collision();
                    }

                    if (numbers[i][j + k + 1] == 0) {
                        numbers[i][j + k + 1] += numbers[i][j + k];
                        numbers[i][j + k] = 0;

                    }

                    if (numbers[i][j + k] == numbers[i][j + k + 1]) {
                        totalPoints += numbers[i][j + k + 1] * 2;
                        numbers[i][j + k + 1] += numbers[i][j + k];
                        numbers[i][j + k] = 0;
                    }
                }
            }
        }
    }

    public void moveLeft() {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 1; j < numbers.length; j++) {

                for (int k = 0; j - k > 0; k++) {
                    if (numbers[i][j - k] == numbers[i][j - k - 1] && numbers[i][j - k] != 0) {
                        collision();
                    }
                    if (numbers[i][j - k - 1] == 0) {
                        numbers[i][j - k - 1] += numbers[i][j - k];
                        numbers[i][j - k] = 0;

                    }

                    if (numbers[i][j - k] == numbers[i][j - k - 1]) {
                        totalPoints += numbers[i][j - k - 1] * 2;
                        numbers[i][j - k - 1] += numbers[i][j - k];
                        numbers[i][j - k] = 0;
                    }
                }
            }
        }
    }

    public void moveUp() {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 1; j < numbers.length; j++) {

                for (int k = 0; j - k > 0; k++) {

                    if (numbers[j - k][i] == numbers[j - k - 1][i] && numbers[j - k][i] != 0) {
                        collision();
                    }
                    if (numbers[j - k - 1][i] == 0) {
                        numbers[j - k - 1][i] += numbers[j - k][i];
                        numbers[j - k][i] = 0;
                    }

                    if (numbers[j - k][i] == numbers[j - k - 1][i]) {
                        totalPoints += numbers[j - k - 1][i] * 2;
                        numbers[j - k - 1][i] += numbers[j - k][i];
                        numbers[j - k][i] = 0;
                    }
                }
            }
        }
    }

    public void moveDown() {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = numbers.length - 2; j >= 0; j--) {

                for (int k = 0; j + k < numbers.length - 1; k++) {
                    if (numbers[j + k][i] == numbers[j + k + 1][i] && numbers[j + k][i] != 0) {
                        collision();
                    }

                    if (numbers[j + k][i] == numbers[j + k + 1][i]) {
                        totalPoints += numbers[j + k + 1][i] * 2;
                        numbers[j + k + 1][i] += numbers[j + k][i];
                        numbers[j + k][i] = 0;
                        //
                    }

                    if (numbers[j + k + 1][i] == 0) {
                        numbers[j + k + 1][i] += numbers[j + k][i];
                        numbers[j + k][i] = 0;

                    }
                }
            }
        }
    }

    public boolean isGameOver() {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[0].length; j++) {
                if (numbers[i][j] == 0) {
                    return false;
                }
            }
        }
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[0].length; j++) {
                if (i < numbers.length - 1 && numbers[i][j] == numbers[i + 1][j]) {
                    return false;
                }
                if (j < numbers[0].length - 1 && numbers[i][j] == numbers[i][j + 1]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isGameVictory() {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[0].length; j++) {
                if (numbers[i][j] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isActionPerformed(int[][] numbers, int[][] newNumbers) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[0].length; j++) {
                if (numbers[i][j] != newNumbers[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    //    public void settleLabels(){
//
//    }
    public int getNumber(int i, int j) {
        return numbers[i][j];
    }

    public int[][] getNumbers() {
        return numbers;
    }

    public void printNumber() {
        for (int[] line : numbers) {
            System.out.println(Arrays.toString(line));
        }
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void setNumbers(int i, int j) {
        this.numbers[i][j] = 0;
    }



    //***Leo wrote this.
    Media collision = new Media(getClass().getResource("/CollisonEffect.mp3").toExternalForm());
    MediaPlayer collisionPlayer = new MediaPlayer(collision);

    void collision() {
        collisionPlayer.seek(javafx.util.Duration.ZERO);
        collisionPlayer.play();
    }
}
