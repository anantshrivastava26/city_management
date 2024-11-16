import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu(int userId) {
        // Set up frame properties
        setTitle("Smart City Management - Main Menu");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setResizable(false);

        // Create a main panel with a BoxLayout (vertical)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Header label
        JLabel headerLabel = new JLabel("Smart City Management");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create buttons
        JButton enterProblemButton = new JButton("Enter a Problem");
        JButton viewHistoryButton = new JButton("View Problem History");
        JButton feedbackButton = new JButton("Send Feedback");
        JButton contactPersonButton = new JButton("Contact Person");
        JButton sosButton = new JButton("SOS");
        JButton logoutButton = new JButton("Logout");

        // Set button styles
        customizeButton(enterProblemButton);
        customizeButton(viewHistoryButton);
        customizeButton(feedbackButton);
        customizeButton(contactPersonButton);
        customizeButton(sosButton);
        customizeButton(logoutButton);

        // Button actions
        enterProblemButton.addActionListener(e -> new ProblemEntryScreen(userId));
        viewHistoryButton.addActionListener(e -> new ProblemHistoryScreen(userId));
        feedbackButton.addActionListener(e -> sendFeedback());
        contactPersonButton.addActionListener(e -> new ContactPersonScreen());
        sosButton.addActionListener(e -> showSOSDialog());
        logoutButton.addActionListener(e -> {
            dispose(); // Close the current window
            new LoginScreen(); // Redirect to the login screen
        });

        // Add components to the panel
        mainPanel.add(headerLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        mainPanel.add(enterProblemButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        mainPanel.add(viewHistoryButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        mainPanel.add(feedbackButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        mainPanel.add(contactPersonButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        mainPanel.add(sosButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        mainPanel.add(logoutButton);

        // Add main panel to the frame
        add(mainPanel);
        setVisible(true);
    }

    // Method to style buttons
    private void customizeButton(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(70, 130, 180)); // Steel blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    // Method to send feedback
    private void sendFeedback() {
        String feedback = JOptionPane.showInputDialog(this, "Enter your feedback:");
        if (feedback != null && !feedback.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Thank you for your feedback!");
        }
    }

    // Method to show SOS confirmation dialog
    private void showSOSDialog() {
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to send an SOS alert?", "SOS Confirmation",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Sending Help AS SOON AS POSSIBLE");
        }
    }
}
