package Manager;

import Controller.ClassicController;
import Controller.ClassicFromFileController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.example.cs109project.MouseClick;
//import java.time.Duration;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TimeManager {

    Timeline timeline;

    UserManager userManager = new UserManager();
    AlertManger alertManger = new AlertManger();
    MouseClick ms = new MouseClick();

    public Timeline getTimeline() {
        return timeline;
    }

    private static final int AUTO_SAVE_INTERVAL_SECONDS = 10; // 定时自动保存间隔（秒）

    public int getSeconds() {
        return seconds;
    }

    private Instant endTime;
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture<?> countdownTask = null;

    private int seconds = 0;

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void startTime(Label label) {
        if (timeline == null || timeline.getStatus() != Timeline.Status.RUNNING) {
            seconds = 0; // 重置秒数
            updateLabel(label);
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), event1 -> {
                seconds++;
                updateLabel(label);
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    public void startTime(Label label, int startingSeconds) {
        if (timeline == null || timeline.getStatus() != Timeline.Status.RUNNING) {
            seconds = startingSeconds; // 重置秒数
            updateLabel(label);
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), event1 -> {
                seconds++;
                updateLabel(label);
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    public void startWithSeconds(Label label, int startingSeconds) {
        if (timeline == null || timeline.getStatus() != Timeline.Status.RUNNING) {
            this.seconds = startingSeconds; // 设置秒数
            updateLabel(label);
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), event1 -> {
                seconds++;
                updateLabel(label);
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }

    public void pauseTime() {
        if (timeline != null && timeline.getStatus() == Timeline.Status.RUNNING) {
            timeline.pause();
        }
    }

    // 如果你想在之后恢复计时器，你可以添加这个方法
    public void resumeTime() {
        if (timeline != null && timeline.getStatus() == Timeline.Status.PAUSED) {
            timeline.play();
        }
    }

    public void updateLabel(Label label) {
        label.setText(String.format("Time: %02d:%02d:%02d", seconds / 3600, seconds / 60, seconds));
    }

    public void autoSaveGame(String username, String gameMode, ClassicFromFileController classicFromFileController, int stepCounter, String gameName, Label points) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(AUTO_SAVE_INTERVAL_SECONDS), event -> {
            try {
                List<String> gameData = classicFromFileController.getGameData();
                saveGameData(username, gameMode, gameData, this.seconds, String.valueOf(classicFromFileController.getStepsCounter()), gameName, points.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void autoSaveGame(String username, String gameMode, ClassicController classicController, int stepCounter, String gameName, Label points) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(AUTO_SAVE_INTERVAL_SECONDS), event -> {
            try {
                List<String> gameData = classicController.getGameData();
                saveGameData(username, gameMode, gameData, this.seconds, String.valueOf(classicController.getStepsCounter()), gameName, points.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    private void saveGameData(String username, String gameMode, List<String> gameData, int seconds, String steps, String gameName, String points) throws IOException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String time = formatter.format(calendar.getTime());//"time" means when user saves the game
        List<String> stringList = userManager.reader(username);
        int rowNumberIndex = 0;
        if (stringList != null) {
            boolean isFirstWriteIn = true;
            for (int i = 0; i < stringList.size(); i++) {
                if (stringList.get(i).split(" ")[5].equals(gameName)) {
                    rowNumberIndex = i;
                    isFirstWriteIn = false;
                    break;
                }
            }
            if (isFirstWriteIn) {
                rowNumberIndex = stringList.size();
            }
        }
        String filePath = String.format("%s_Data.txt", username);
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            stringBuilder.append(gameData.get(i));
            stringBuilder.append(" ");
        }
        String newContent = String.format("%s %s %s %s %d %s %s %s", username, gameMode, time, gameName, seconds, steps, points, stringBuilder);
        try {
            modifyLine(filePath, rowNumberIndex, newContent);
            System.out.println("Game data has been saved automatically.");
        } catch (IOException e) {
            System.err.println("保存游戏数据时出错：" + e.getMessage());
        }
    }

    public static void modifyLine(String filePath, int lineNumber, String newContent) throws IOException {
        File file = new File(filePath);
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            long position = 0;
            for (int i = 0; i < lineNumber; i++) {
                // 查找要修改的行的位置
                String line = raf.readLine();
                if (line == null) {
                    System.err.println("指定行不存在");
                    return;
                }
                position += line.length() + 1; // 1 表示换行符的长度
            }
            raf.seek(position); // 将文件指针移动到要修改的行的位置
            // 读取要修改的行
            String oldLine = raf.readLine();
            if (oldLine == null) {
                // 如果指定的行为空，则直接写入新内容
                raf.seek(position);
                raf.writeBytes(newContent + "\n");
                return;
            }
            // 计算要插入的新行的长度与旧行的长度差
            int lengthDiff = newContent.length() - oldLine.length();
            // 如果新行的长度与旧行的长度不同，需要移动后续内容的位置
            if (lengthDiff > 0) {
                moveFollowingLines(raf, position + oldLine.length() + 1, lengthDiff);
            } else if (lengthDiff < 0) {
                moveFollowingLines(raf, position + oldLine.length() + 1, lengthDiff);
            }
            // 将新内容写入文件
            raf.seek(position);
            raf.writeBytes(newContent);
        }
    }

    private static void moveFollowingLines(RandomAccessFile raf, long position, int lengthDiff) throws IOException {
        long currentPosition = raf.getFilePointer();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = raf.read(buffer)) != -1) {
            raf.seek(position);
//            raf.seek(position + lengthDiff);
            raf.write(buffer, 0, bytesRead);
            position += bytesRead + lengthDiff;
            raf.seek(currentPosition);
//            currentPosition = raf.getFilePointer();
        }
    }

    public long remainingSeconds;

    public void countdownWithTimeline(Label labelTime, double limitedTime, ActionEvent event, MouseClick ms) {
        long limitedTimeAsSeconds = (long) limitedTime;
        if (endTime == null || endTime.isBefore(Instant.now())) {
            endTime = Instant.now().plusSeconds(limitedTimeAsSeconds);
            if (countdownTask == null || countdownTask.isCancelled()) {
                countdownTask = executorService.scheduleAtFixedRate(() -> {
                    long remainingSeconds = java.time.Duration.between(Instant.now(), endTime).getSeconds();
                    if (remainingSeconds > 0) {
                        updateRemainingTime(remainingSeconds, labelTime);
                        this.remainingSeconds = remainingSeconds;
                    } else {
                        // 倒计时结束，取消任务并可能更新UI
                        gameOverPlayer.play();
                        alertManger.showTimeLimitedAlert("Time's up!", "You have failed to reach 2048.", event, ms);
                        executorService.shutdownNow(); // 这将取消所有任务
                        endTime = null;
                        countdownTask = null;
                        updateRemainingTime(remainingSeconds, labelTime);
                    }
                }, 0, 1, TimeUnit.SECONDS);
            }
        }
    }

    private void updateRemainingTime(long remainingSeconds, Label labelTime) {
        long minutes = remainingSeconds / 60;
        long seconds = remainingSeconds % 60;
        Platform.runLater(() -> labelTime.setText(String.format("Remaining: %d mins %d s", minutes, seconds)));
    }

    public void stopAutoSave() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    //method used to disable key and mouse control if the game is paused
    public boolean isPaused() {
        return timeline != null && timeline.getStatus() == Timeline.Status.PAUSED;
    }

    Media gameOverSound = new Media(Objects.requireNonNull(getClass().getResource("/Gameover.mp3")).toExternalForm());
    MediaPlayer gameOverPlayer = new MediaPlayer(gameOverSound);
}