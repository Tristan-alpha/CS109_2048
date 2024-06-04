package org.example.cs109project;

import Controller.LimitedTimeController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SelectTime {

    @FXML
    private Label labelTime;

    @FXML
    private Slider sliderTimeChooser;

    @FXML
    private Button buttonConfirm;

    private final MouseClick ms = new MouseClick();

    @FXML
    void confirm(MouseEvent event) throws IOException {
        LimitedTimeController.timeLimit = sliderTimeChooser.getValue() * 60;
        ms.mouseClick(event, "LimitedTime");
    }

    public void initialize() {
        // 创建一个Slider控件，设置最小值、最大值和初始值
        sliderTimeChooser.setShowTickMarks(true);
        sliderTimeChooser.setShowTickLabels(true);
        sliderTimeChooser.setMajorTickUnit(0.5);
        sliderTimeChooser.setMinorTickCount(0); // 去除小刻度，使得只能选择大刻度
        sliderTimeChooser.setSnapToTicks(true);
        sliderTimeChooser.setBlockIncrement(0.5);
        // 添加滑动事件监听器，更新标签显示的时长值
        sliderTimeChooser.valueProperty().addListener((observable, oldValue, newValue) -> {
            // 当滑块停止移动时，调整其值为最近的0.5的倍数
            double currentValue = sliderTimeChooser.getValue();
            double roundedValue = Math.round(currentValue * 2) / 2.0; // 将当前值四舍五入到最近的0.5倍数
            sliderTimeChooser.setValue(roundedValue); // 设置滑块的新值
            labelTime.setText(String.format("Time-limited: %.2f minutes", roundedValue));
        });
    }

}
