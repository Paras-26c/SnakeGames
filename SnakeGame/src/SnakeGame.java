import javax.swing.*;
import java.awt.*;

public class SnakeGame extends JFrame {

    // Constructor to set up the game window
    public SnakeGame() {
        // Set up the JFrame properties
        this.setTitle("Snake Game");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center the window
        this.setResizable(false);

        // Add the GameBoard panel where the game will be drawn
        this.add(new GameBoard());
    }

    public static void main(String[] args) {
        // Create an instance of SnakeGame and make it visible
        SwingUtilities.invokeLater(() -> {
            SnakeGame game = new SnakeGame();
            game.setVisible(true);
        });
    }
}
