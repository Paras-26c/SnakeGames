import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

public class GameBoard extends JPanel implements ActionListener {

    private final int TILE_SIZE = 25; // Size of one tile (square)
    private final int BOARD_WIDTH = 800 / TILE_SIZE; // Number of tiles horizontally
    private final int BOARD_HEIGHT = 600 / TILE_SIZE; // Number of tiles vertically

    private LinkedList<Point> snake; // Snake body
    private Point food; // Current food position
    private boolean gameOver;
    private boolean movingUp, movingDown, movingLeft, movingRight;
    private Timer timer;

    public GameBoard() {
        this.snake = new LinkedList<>();
        this.snake.add(new Point(5, 5)); // Starting position of snake
        this.gameOver = false;
        this.movingUp = false;
        this.movingDown = true;
        this.movingLeft = false;
        this.movingRight = false;

        // Set up the timer to trigger action every 100ms (speed of snake movement)
        this.timer = new Timer(100, this);
        this.timer.start();

        // Listen for key events to change the snake's direction
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP && !movingDown) {
                    movingUp = true;
                    movingDown = false;
                    movingLeft = false;
                    movingRight = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN && !movingUp) {
                    movingDown = true;
                    movingUp = false;
                    movingLeft = false;
                    movingRight = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT && !movingRight) {
                    movingLeft = true;
                    movingUp = false;
                    movingDown = false;
                    movingRight = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT && !movingLeft) {
                    movingRight = true;
                    movingUp = false;
                    movingDown = false;
                    movingLeft = false;
                }
            }
        });

        this.setFocusable(true);
        spawnFood();
    }

    // Game logic
    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            return;
        }

        // Move the snake
        Point head = snake.getFirst();
        Point newHead = new Point(head);

        if (movingUp) newHead.y--;
        if (movingDown) newHead.y++;
        if (movingLeft) newHead.x--;
        if (movingRight) newHead.x++;

        // Check for game over conditions
        if (newHead.x < 0 || newHead.x >= BOARD_WIDTH || newHead.y < 0 || newHead.y >= BOARD_HEIGHT || snake.contains(newHead)) {
            gameOver = true;
            repaint();
            return;
        }

        // Move the snake
        snake.addFirst(newHead);

        // Check if snake eats food
        if (newHead.equals(food)) {
            spawnFood(); // Respawn food
        } else {
            snake.removeLast(); // Remove the tail
        }

        repaint(); // Redraw the game
    }

    // Spawn food at a random position
    private void spawnFood() {
        Random rand = new Random();
        int x = rand.nextInt(BOARD_WIDTH);
        int y = rand.nextInt(BOARD_HEIGHT);
        food = new Point(x, y);
    }

    // Draw the snake and the food
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameOver) {
            g.setColor(Color.RED);
            g.drawString("Game Over! Press Enter to Restart", 300, 300);
            return;
        }

        // Draw the snake
        g.setColor(Color.GREEN);
        for (Point p : snake) {
            g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        // Draw the food
        g.setColor(Color.RED);
        g.fillRect(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
}

