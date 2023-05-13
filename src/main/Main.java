package main;

import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main extends Application {

    public static void main(String[] args) throws IOException {

        JDBC.openConnection();

        launch(args);

        JDBC.closeConnection();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle rb = ResourceBundle.getBundle("language/Nat", Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("/view/login-view.fxml"));
        stage.setTitle(rb.getString("title"));
        stage.setScene(new Scene(root));
        stage.show();
    }


}
