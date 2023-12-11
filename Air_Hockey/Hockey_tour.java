package Air_Hockey;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Hockey_tour extends JFrame {
    private StartMenu startMenu;
    private JTextArea helpArea;
    private static Hockey_tour instance;

    public Hockey_tour(StartMenu startMenu) {
        this.startMenu = startMenu;
        setTitle("Hockey Tour");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        helpArea = new JTextArea("Air Hockey Game Instructions\n" +
                "Objective: The goal of Air Hockey is to score points by hitting the puck into the opponent's goal.\n" +
                "\n" +
                "Controls:\n" +
                "\n" +
                "Player 1 (Bottom Paddle):\n" +
                "Use the A and D keys to move left and right.\n" +
                "Use the W and S keys to move up and down.\n" +
                "Player 2 (Top Paddle):\n" +
                "Mouse movement controls the position.\n" +
                "Gameplay:\n" +
                "\n" +
                "The game starts when you press Enter.\n" +
                "The puck is placed at the center of the table.\n" +
                "Players control their paddles to hit the puck.\n" +
                "The puck moves across the table, and players attempt to hit it into the opponent's goal.\n" +
                "Each time the puck crosses the goal line, the opposing player gains a point.\n" +
                "Scoring:\n" +
                "\n" +
                "A player scores a point when the puck passes through the opponent's goal.\n" +
                "The first player to reach a set number of points wins the game.\n" +
                "Winning the Game:\n" +
                "\n" +
                "The game usually consists of several rounds.\n" +
                "The player who scores the predetermined number of goals first wins the game.\n" +
                "Game Over:\n" +
                "\n" +
                "If a player reaches the winning goal count, the game ends.\n" +
                "Options are provided to either play again or return to the start menu.\n" +
                "Pause and Restart:\n" +
                "\n" +
                "The game can be paused by pressing OK in the pause menu.\n" +
                "To restart the game, press OK in the winning/losing dialogue.\n" +
                "Hints:\n" +
                "\n" +
                "Strategically position your paddle to intercept the puck and score goals.\n" +
                "Try to predict the puck's movement to gain an advantage over your opponent.\n" +
                "Quick reactions and precise control are key to winning.\n" +
                "Have Fun! Enjoy the game and compete with your friends to become the Air Hockey champion!\n" +
                "\n" +
                "These instructions provide a basic understanding of how to play the Air Hockey game, including controls, gameplay, scoring, winning conditions, and options to restart or return to the start menu. Adjust the instructions as needed to match the specific features and controls of your game");

        // Customize the JTextArea
        helpArea.setEditable(false);
        helpArea.setLineWrap(true);
        helpArea.setWrapStyleWord(true);

        add(helpArea, BorderLayout.CENTER);

        // Optional: Add a button to close the help window
        JButton closeButton = createStyledButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMenu.setVisible(true);
                dispose(); // Close the current frame
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Make the JFrame visible
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setContentAreaFilled(false); // Make the button transparent
        button.setForeground(Color.green); // Set text color to green
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Set font size
        button.setBorder(null);
        return button;
    }

    public static Hockey_tour getInstance() {
        if (instance == null) {
            instance = new Hockey_tour(StartMenu.getInstance());
        }
        return instance;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> getInstance().setVisible(true));
    }
}
