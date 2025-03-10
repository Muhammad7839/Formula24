package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The main entry point for the Card Game application.
 * Initializes the game UI and launches the JavaFX application.
 */
public class Main extends Application {
    // Array to hold ImageViews for displaying card images
    private ImageView[] cardViews = new ImageView[4];

    /**
     * Starts the JavaFX application, initializes the game UI, and sets up the stage.
     *
     * @param primaryStage The main window (stage) for the application.
     */
    @Override
    public void start(Stage primaryStage) {
        // Create the main game UI layout
        BorderPane root = CardGenerator.createGameUI(cardViews);

        // Set up the scene with defined dimensions
        Scene scene = new Scene(root, 800, 600);

        // Apply external CSS for styling
        String css = getClass().getResource("/application/application.css").toExternalForm();
        scene.getStylesheets().add(css);

        // Configure and display the primary stage
        primaryStage.setTitle("Card Game - Arithmetic Challenge");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Generate the initial set of random cards
        CardGenerator.generateRandomCards(cardViews);
    }

    /**
     * Main method to launch the JavaFX application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        launch(args);
    }
}
