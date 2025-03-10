package application;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import java.net.URL;
import java.util.*;

/**
 * Handles the game UI, card generation, animations, and hint retrieval.
 */
public class CardGenerator {
    // Mapping card image names to their numerical values
    private static final Map<String, Integer> CARD_VALUES = new HashMap<>() {{
        for (int i = 2; i <= 10; i++) put(i + "_of_hearts.png", i);
        put("Jack_of_hearts.png", 11); put("Queen_of_hearts.png", 12);
        put("King_of_hearts.png", 13); put("Ace_of_hearts.png", 1);

        for (int i = 2; i <= 10; i++) put(i + "_of_diamonds.png", i);
        put("Jack_of_diamonds.png", 11); put("Queen_of_diamonds.png", 12);
        put("King_of_diamonds.png", 13); put("Ace_of_diamonds.png", 1);

        for (int i = 2; i <= 10; i++) put(i + "_of_clubs.png", i);
        put("Jack_of_clubs.png", 11); put("Queen_of_clubs.png", 12);
        put("King_of_clubs.png", 13); put("Ace_of_clubs.png", 1);

        for (int i = 2; i <= 10; i++) put(i + "_of_spades.png", i);
        put("Jack_of_spades.png", 11); put("Queen_of_spades.png", 12);
        put("King_of_spades.png", 13); put("Ace_of_spades.png", 1);
    }};

    private static String[] selectedCards = new String[4];

    /**
     * Creates the game UI with card display, ads, and controls.
     */
    public static BorderPane createGameUI(ImageView[] cardViews) {
        BorderPane mainContainer = new BorderPane();
        mainContainer.setStyle("-fx-background-color: #1e1e1e;");

        // Add banners for ads
        mainContainer.setTop(createAdBanner("Play now! Premium card games available", "#4285F4"));
        mainContainer.setBottom(createAdBanner("Upgrade to Pro: No ads & unlimited hints!", "#EA4335"));

        // Center game content
        mainContainer.setCenter(createGameContent(cardViews));
        return mainContainer;
    }

    /**
     * Creates an advertisement banner.
     */
    private static HBox createAdBanner(String adText, String bgColor) {
        HBox adBanner = new HBox();
        adBanner.setAlignment(Pos.CENTER);
        adBanner.setPadding(new Insets(10));
        adBanner.setStyle("-fx-background-color: " + bgColor + ";");

        Label adLabel = new Label(adText);
        adLabel.setTextFill(Color.WHITE);
        adLabel.setFont(Font.font("System", FontWeight.BOLD, 14));

        Button adButton = new Button("Get it");
        adButton.setStyle("-fx-background-color: white; -fx-text-fill: " + bgColor + "; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        adBanner.getChildren().addAll(adLabel, spacer, adButton);
        return adBanner;
    }

    /**
     * Creates the main game content including cards, input, and buttons.
     */
    private static VBox createGameContent(ImageView[] cardViews) {
        VBox root = new VBox(20);
        root.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        // Card container
        HBox cardContainer = new HBox(15);
        cardContainer.setStyle("-fx-alignment: center;");

        for (int i = 0; i < 4; i++) {
            cardViews[i] = new ImageView();
            cardViews[i].setFitWidth(100);
            cardViews[i].setFitHeight(150);
            cardViews[i].setPreserveRatio(true);

            StackPane cardPane = new StackPane();
            cardPane.setStyle("-fx-background-color: white; -fx-padding: 5px; -fx-border-radius: 5px;");
            cardPane.getChildren().add(cardViews[i]);
            cardContainer.getChildren().add(cardPane);
        }

        // Input field for arithmetic expression
        TextField expressionField = new TextField();
        expressionField.setPromptText("Enter your expression");
        expressionField.setStyle("-fx-font-size: 16px; -fx-pref-width: 300px; -fx-border-radius: 5px;");

        Label resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        // Buttons
        Button evaluateButton = new Button("Evaluate");
        evaluateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        evaluateButton.setOnAction(e -> {
            String expression = expressionField.getText().trim();
            if (expression.isEmpty()) {
                resultLabel.setText("⚠️ Enter an expression first!");
                resultLabel.setStyle("-fx-text-fill: orange;");
                return;
            }
            System.out.println("Evaluating Expression: " + expression + " = " + EvaluateString.evaluate(expression)); // Debugging Output
            if (isValidExpression(expression)) {
                resultLabel.setText("✅ Correct! Moving to next level...");
                resultLabel.setStyle("-fx-text-fill: #4CAF50;");
                expressionField.clear();
                generateRandomCards(cardViews);
            } else {
                resultLabel.setText("❌ Incorrect! Try again.");
                resultLabel.setStyle("-fx-text-fill: red;");
            }
        });

        Button shuffleButton = new Button("Shuffle Cards");
        shuffleButton.setStyle("-fx-background-color: #FF9800; -fx-text-fill: white;");
        shuffleButton.setOnAction(e -> generateRandomCards(cardViews));

        Button hintButton = new Button("Hint");
        hintButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        hintButton.setOnAction(e -> {
            resultLabel.setText(getSolutionFromGemini());
            resultLabel.setStyle("-fx-text-fill: yellow;");
        });

        HBox buttonContainer = new HBox(20, evaluateButton, shuffleButton, hintButton);
        buttonContainer.setStyle("-fx-alignment: center;");

        root.getChildren().addAll(cardContainer, expressionField, resultLabel, buttonContainer);
        return root;
    }

    /**
     * Generates random cards with fade-in and fade-out animations.
     */
    public static void generateRandomCards(ImageView[] cardViews) {
        List<String> cardKeys = new ArrayList<>(CARD_VALUES.keySet());
        Collections.shuffle(cardKeys);

        for (ImageView cardView : cardViews) {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), cardView);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> updateCardImages(cardViews, cardKeys));
            fadeOut.play();
        }
    }

    private static void updateCardImages(ImageView[] cardViews, List<String> cardKeys) {
        for (int i = 0; i < 4; i++) {
            selectedCards[i] = cardKeys.get(i);
            String imagePath = "/application/cards/" + selectedCards[i];

            URL imageUrl = CardGenerator.class.getResource(imagePath);
            if (imageUrl != null) {
                cardViews[i].setImage(new Image(imageUrl.toExternalForm(), 100, 150, true, true));
            }
        }

        for (ImageView cardView : cardViews) {
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), cardView);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        }
    }

    private static boolean isValidExpression(String expression) {
        return EvaluateString.evaluate(expression) == 24;
    }

    /**
     * Calls the Gemini AI API to get a hint.
     */
    private static String getSolutionFromGemini() {
        String requestBody = "{ \"contents\": [{ \"parts\": [{ \"text\": " +
                "\"Provide a hint for solving 24 using numbers " + Arrays.toString(selectedCards) +
                ". The hint should be subtle and should not reveal the complete solution.\"}]}]}";

        return GeminiAPI.callGemini(requestBody);
    }
}
