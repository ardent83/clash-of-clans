package view;

import controller.LogInController;
import data.SavePlayerData;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Map.*;
import model.Player;

import java.util.ArrayList;

public class LogIn extends Application {
    private final ArrayList<Player> players;
    private final LogInController controller;

    public LogIn(ArrayList<Player> players) {
        this.players = players;
        this.controller = new LogInController(players);
    }

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundImage(new Image("poster1.jpg"),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
        root.setCenter(vBoxFields(stage));

        Scene scene = new Scene(root,400,300);
        stage.setScene(scene);
        scene.getStylesheets().add("style.css");
        stage.show();
        stage.setResizable(false);
    }
    private final static DropShadow shadow = new DropShadow(20, Color.web("#000000"));
    private VBox vBoxFields(Stage stage){
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20));
        vBox.setSpacing(15);
        vBox.setAlignment(Pos.CENTER);
        TextField textFieldID = new TextField();
        textFieldID.setPromptText("username");
        TextField textFieldPassword = new TextField();
        textFieldPassword.setPromptText("password");
        textFieldID.setEffect(shadow);
        textFieldPassword.setEffect(shadow);

        Button buttonLogin = new Button("Log In");
        buttonLogin.setEffect(shadow);
        buttonLogin.setOnMouseClicked(mouseEvent -> {
            try {
                Player player = controller.getPlayer(textFieldID.getText(), textFieldPassword.getText());
                // new playerPanel();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });

        Button buttonSignUp = new Button("Sign Up");
        buttonSignUp.setEffect(shadow);
        buttonSignUp.setOnMouseClicked(mouseEvent -> {
            new SignUp(players).start(new Stage());
            stage.close();
        });

        HBox hBoxButton = new HBox(buttonSignUp, buttonLogin);
        hBoxButton.setSpacing(10);
        hBoxButton.setAlignment(Pos.CENTER);

        Text text = new Text("Log In");
        text.setId("text");
        vBox.getChildren().addAll(text, textFieldID, textFieldPassword, hBoxButton);

        return vBox;
    }
}
