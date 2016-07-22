package view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class PipeChartController {
    @FXML
    private PieChart pieChart;
    @FXML
    private AnchorPane pane;

    public void showChart(String s) {
        System.out.println(s);
        String[] lines = s.split("\n");
        HashMap<PieChart.Data, String> hs = new HashMap<>();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (int i = 0; i < lines.length; i++) {
            String[] temp = lines[i].split("\t");
            Double d = Double.parseDouble(temp[1]);
            d = d * 100;
            int floor = (int) Math.floor(d);

            PieChart.Data data = new PieChart.Data(temp[0], floor);
            hs.put(data, temp[2]);
            pieChartData.add(data);
        }

        final Label caption = new Label("");
        caption.setTextFill(Color.DARKBLUE);
        caption.setStyle("-fx-font: 10 arial;");


        pieChart = new PieChart(pieChartData);

        pieChart.setLabelLineLength(10);
        pieChart.setLegendSide(Side.LEFT);
        pane.getChildren().add(pieChart);
        pane.getChildren().add(caption);
        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            caption.setTranslateX(e.getSceneX());
                            caption.setTranslateY(e.getSceneY());
                            caption.setText(String.valueOf(data.getPieValue()) + "%\n" + hs.get(data));
                        }
                    });
        }

    }

}
