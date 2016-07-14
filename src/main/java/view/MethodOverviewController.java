package view;

import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import model.MethodModel;

public class MethodOverviewController {

    @FXML
    private TableView<MethodModel> methodTable;

    @FXML
    private TableColumn<MethodModel, String> methodNameColumn;

    @FXML
    private TextArea summaryMethod;


    private MainApp mainApp;


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        showMethodDetails(null);
        methodTable.setItems(mainApp.getMethodData());
        setMethodsName();

    }

    public void setMethodsName() {
        methodNameColumn.setCellValueFactory(cellData -> cellData.getValue().getMethodNameProperty());
        methodTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showMethodDetails(newValue));
    }

    private void showMethodDetails(MethodModel methodModel) {
        if (methodModel != null) {
            summaryMethod.setText(methodModel.getTemplate());

        } else {
            summaryMethod.setText("Please select method from left table");

        }
    }
}
