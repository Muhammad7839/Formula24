# Card Game - Arithmetic Challenge

## Overview
This is a JavaFX-based game where players must form a mathematical expression using four randomly drawn cards to reach 24. The game includes AI-generated hints, shuffle animations, and a simple UI with ads.

## Features
- JavaFX UI with a simple interface
- Randomly generated playing cards
- AI-powered hints using Gemini API
- Smooth shuffle animation with fade effects
- Expression evaluation to check if the result is 24
- Ads displayed at the top and bottom of the game

## Installation and Setup

### Prerequisites
- Java 17 or later
- Maven installed
- IntelliJ IDEA (recommended)

### Steps to Run
1. Clone the repository  
   `git clone https://github.com/Muhammad7839/CardGameIntelliJ.git`  
   `cd CardGameIntelliJ`
2. youtube link https://youtu.be/KOG_xwrZXik

2. Open in IntelliJ IDEA
    - Go to **File** → **Open** → Select `CardGameIntelliJ` folder
    - Wait for Maven to import dependencies

3. Run the game
    - Open `Main.java`
    - Click **Run**

## Project Structure
CardGameIntelliJ/
│── src/main/java/application/
│   ├── Main.java              # Entry point, initializes UI
│   ├── CardGenerator.java     # Handles card logic & UI layout
│   ├── EvaluateString.java    # Evaluates user mathematical expressions
│   ├── GeminiAPI.java         # Connects to AI for hints
│── src/main/resources/
│   ├── application.css        # UI styling
│   ├── cards/                 # Images of playing cards
│── pom.xml                    # Maven dependencies
│── README.md                  # Project documentation




## Explanation of Files

### Main.java
- Initializes the JavaFX UI and sets up the game window
- Loads CSS for styling
- Calls `CardGenerator.generateRandomCards()` to start the game

### CardGenerator.java
- Creates the game UI, including buttons, cards, and ads
- Handles shuffling animation with fade effects
- Sends requests to Gemini API for AI-generated hints

### EvaluateString.java
- Evaluates the user’s input to check if the expression results in 24
- Supports basic math operations and operator precedence

### GeminiAPI.java
- Connects to the Gemini AI API to get hints
- Processes API responses and extracts relevant hints for the user

## How the Game Works
1. The game draws four random playing cards.
2. The player enters an arithmetic expression using the card values.
3. If the expression evaluates to 24, the player moves to the next round.
4. If incorrect, the player can try again or request a hint.
5. The hint button provides AI-generated hints to guide the player.
6. The shuffle button replaces the cards with new ones.

## Troubleshooting

### API Issues
- If the hint button shows "API error 400", check the API key in `GeminiAPI.java`.
- Make sure the API request is formatted correctly.

### Images Not Loading
- Ensure card images are in `src/main/resources/application/cards/`.
- Check that image paths in `CardGenerator.java` are correct.

## Future Improvements
- Adding multiplayer mode
- Introducing a leaderboard system
- Improving AI hints with more detailed explanations

## License
This project is under the MIT License.