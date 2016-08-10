package view;


import application.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class RootLayoutController {

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleHome() {

        mainApp.initRootLayout();
        mainApp.showMethodOverview();
    }

    @FXML
    private void submitCode() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label).forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));
        alert.setTitle("Submit code");
        alert.setHeaderText("Submit code");
        alert.setContentText("your code has been submitted to crowdSummarizer");
        alert.showAndWait();
    }
}
