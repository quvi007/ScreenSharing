package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Main extends Application {
    BorderPane root;
    HBox hBox;
    RadioButton rbServer, rbClient;
    ToggleGroup group;
    Button bConnect;
    ImageView imageView;
    Thread t;

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new BorderPane();
        hBox = new HBox(15);
        rbServer = new RadioButton("Act as Server");
        rbClient = new RadioButton("Act as Client");

        group = new ToggleGroup();
        rbServer.setToggleGroup(group);
        rbClient.setToggleGroup(group);

        bConnect = new Button("Connect");


        hBox.getChildren().addAll(rbServer, rbClient, bConnect);

        HBox.setMargin(bConnect, new Insets(15, 0, 20, 20));
        HBox.setMargin(rbServer, new Insets(20, 0, 20, 20));
        HBox.setMargin(rbClient, new Insets(20, 0, 20, 20));
        root.setTop(hBox);

        imageView = new ImageView();
        imageView.setImage(new Image("pic.jpg"));
        root.setCenter(imageView);

        imageView.setFitHeight(562);
        imageView.setFitWidth(1000);

        primaryStage.setTitle("CitadelX");
        primaryStage.setScene(new Scene(root, 1000, 620));
        //
        // primaryStage.setMaximized(true);
        primaryStage.show();

        Thread taskThread = new Thread(new Runnable() {
            Robot robot = new Robot();
            Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    BufferedImage bufferedImage = robot.createScreenCapture(rectangle);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImage(image);
                        }
                    });
                }
            }
        });

        bConnect.setOnAction(e -> taskThread.start());

    }

    public static void main(String[] args) {
        launch(args);
    }
}
