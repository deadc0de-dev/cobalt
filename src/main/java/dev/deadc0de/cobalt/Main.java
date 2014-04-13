package dev.deadc0de.cobalt;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int WIDTH = 300;
    private static final int HEIGHT = 200;

    public static void main(String... args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final Scene scene = new Scene(root(), WIDTH, HEIGHT, background());
        stage.setScene(scene);
        stage.setTitle("Cobalt");
        stage.show();
    }

    private Parent root() {
        final Text group = label("deadc0de.dev");
        final AnchorPane groupPane = new AnchorPane(group);
        AnchorPane.setBottomAnchor(group, 0D);
        AnchorPane.setRightAnchor(group, 0D);
        final Text message = label("Not implemented yet...");
        final BorderPane messagePane = new BorderPane(message);
        return new StackPane(groupPane, messagePane);
    }

    private Text label(String text) {
        final Text formattedText = new Text(text);
        formattedText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        formattedText.setFill(Color.WHITE);
        formattedText.setStroke(Color.web("#0047AB"));
        return formattedText;
    }

    private Paint background() {
        return new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
            new Stop(0, Color.web("#284493")),
            new Stop(0.5, Color.web("#B0C6DA")),
            new Stop(1, Color.web("#49559A"))});
    }
}
