package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.MethodModel;
import view.MethodOverviewController;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<MethodModel> method = FXCollections.observableArrayList();
    private List<File> files;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Code Summary");

        initRootLayout();
        showMethodOverview();



    }

    public void setMethod(MethodModel methodModel) {
        method.add(methodModel);
    }

    public ObservableList<MethodModel> getMethodData() {
        return method;
    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../fxml/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showMethodOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../fxml/MethodOverview.fxml"));
            AnchorPane methodOverview = loader.load();

            rootLayout.setCenter(methodOverview);

            MethodOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInput(List<File> files) {
        this.files = files;
    }

    public List<File> getFiles() {
        return files;
    }
}
