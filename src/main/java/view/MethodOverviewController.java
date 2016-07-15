package view;

import application.Main;
import application.MainApp;
import core.lda.TopicModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import model.MethodModel;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class MethodOverviewController {

    @FXML
    private TableView<MethodModel> methodTable;

    @FXML
    private TableColumn<MethodModel, String> methodNameColumn;

    @FXML
    private TextArea summaryMethod;


    @FXML
    private CheckBox checkBox;

    @FXML
    private Pane drag;


    private MainApp mainApp;
    private boolean selected;


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        drag.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });
        // Dropping over surface
        drag.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    mainApp.setInput(db.getFiles());

                    try {
                        Main.start(mainApp);
                        if (selected) {
                            try {
                                TopicModel.lda();
                                methodTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                                    try {

                                        summaryMethod.appendText("----------------------------------------" + "\n");
                                        summaryMethod.appendText("Topics: \n");
                                        summaryMethod.appendText(TopicModel.showTopic(newValue));

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

        showMethodDetails(null);
        methodTable.setItems(mainApp.getMethodData());
        setMethodsName();
        checkBox.setOnAction((ev -> {
            selected = checkBox.isSelected();
        }));


    }

    public void setMethodsName() {
        methodNameColumn.setCellValueFactory(cellData -> cellData.getValue().getMethodNameProperty());
        methodTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showMethodDetails(newValue));


    }

    private void showMethodDetails(MethodModel methodModel) {
        if (methodModel != null) {
            summaryMethod.setText(methodModel.getTemplate());
        } else {
            summaryMethod.setText("Please select method from left side");

        }
    }

}
