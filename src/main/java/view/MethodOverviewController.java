package view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import application.MainApp;
import javafx.scene.control.cell.PropertyValueFactory;
import model.MethodModel;

public class MethodOverviewController {

    @FXML
    private TableView<MethodModel> methodTable;

    @FXML
    private TableColumn<MethodModel, String> methodNameColumn;


    private MainApp mainApp;


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        methodTable.setItems(mainApp.getMethodData());
        setMethodsName();

    }

    public void setMethodsName() {
        methodNameColumn.setCellValueFactory(cellData -> cellData.getValue().getMethodNameProperty());
    }
}
