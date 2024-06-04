package org.example.cs109project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ranking {
    public static int score1;
    public static int score2;
    public static int score3;

    public static String username1;
    public static String username2;
    public static String username3;

    @FXML
    private Label score1st;

    @FXML
    private Label score2nd;

    @FXML
    private Label score3rd;

    public void initialize() {
        List<String> data = readAllData();
        List<String> sortedData = sortNumberDescending(data);
        switch (sortedData.size()) {
            case 1:
                score1st.setText(String.format("1st-%s: %s", sortedData.get(0).split(" ")[0], sortedData.get(0).split(" ")[8]));
                score2nd.setText(String.format("2nd-%s", "No data"));
                score3rd.setText(String.format("3rd-%s", "No data"));
                break;
            case 2:
                score1st.setText(String.format("1st-%s: %s", sortedData.get(0).split(" ")[0], sortedData.get(0).split(" ")[8]));
                score2nd.setText(String.format("2nd-%s: %s", sortedData.get(1).split(" ")[0], sortedData.get(1).split(" ")[8]));
                score3rd.setText(String.format("3rd-%s", "No data"));
                break;
            default:
                score1st.setText(String.format("1st-%s: %s", sortedData.get(0).split(" ")[0], sortedData.get(0).split(" ")[8]));
                score2nd.setText(String.format("2nd-%s: %s", sortedData.get(1).split(" ")[0], sortedData.get(1).split(" ")[8]));
                score3rd.setText(String.format("3rd-%s: %s", sortedData.get(2).split(" ")[0], sortedData.get(2).split(" ")[8]));
                break;
            case 0:
                score1st.setText(String.format("1st-%s", "No data"));
                score2nd.setText(String.format("2nd-%s", "No data"));
                score3rd.setText(String.format("3rd-%s", "No data"));
                break;
        }
    }

    private List<String> readAllData() {
        try (Stream<Path> paths = Files.walk(Paths.get(""))) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith("_Data.txt"))
                    .flatMap(path -> {
                        try {
                            return Files.lines(path);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> sortNumberDescending(List<String> data) {
        // 假设每个字符串至少有8个由空格分隔的部分
        // 使用流和自定义比较器进行排序
        return data.stream()
                .sorted(Comparator.comparingInt(s -> extractNumber((String) s)).reversed())
                .collect(Collectors.toList());
    }

    private static int extractNumber(String s) {
        String[] parts = s.split("\\s+");
        if (parts.length > 7) {
            // 尝试将第8个部分转换为整数
            try {
                return Integer.parseInt(parts[8]);
            } catch (NumberFormatException e) {
                // 如果第8个部分不是数字，可以抛出异常、记录错误或返回一个默认值
                throw new IllegalArgumentException("The 8th part of the string is not a number: " + parts[7], e);
            }
        }
        // 如果没有足够的部分，可以抛出异常、记录错误或返回一个默认值
        throw new IllegalArgumentException("The string does not have enough parts: " + s);
    }
}
