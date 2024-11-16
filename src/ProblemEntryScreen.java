import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProblemEntryScreen extends JFrame {
    private JComboBox<String> problemTypeBox;
    private JTextArea problemDescriptionArea;
    private int userId;

    public ProblemEntryScreen(int userId) {
        this.userId = userId;

        // Frame setup
        setTitle("Report a Problem");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setResizable(false);

        // Main panel setup
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Header label
        JLabel headerLabel = new JLabel("Report an Issue");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Problem Type dropdown
        JLabel problemTypeLabel = new JLabel("Problem Type:");
        problemTypeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        problemTypeBox = new JComboBox<>(new String[]{"Electricity", "Water Supply", "Roads", "Waste Management"});
        problemTypeBox.setMaximumSize(new Dimension(200, 25));

        // Description area
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        problemDescriptionArea = new JTextArea(5, 20);
        problemDescriptionArea.setLineWrap(true);
        problemDescriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(problemDescriptionArea);
        descriptionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Submit button
        JButton submitButton = new JButton("Submit Problem");
        customizeButton(submitButton);
        submitButton.addActionListener(e -> submitProblem());

        // Back button
        JButton backButton = new JButton("Back");
        customizeButton(backButton);
        backButton.addActionListener(e -> dispose());

        // Adding components to the main panel
        mainPanel.add(headerLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(problemTypeLabel);
        mainPanel.add(problemTypeBox);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(descriptionLabel);
        mainPanel.add(descriptionScrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(submitButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(backButton);

        // Adding main panel to the frame
        add(mainPanel);
        setVisible(true);
    }

    // Method to customize button appearance
    private void customizeButton(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(70, 130, 180)); // Steel blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    // Method to submit the problem
    private void submitProblem() {
        String problemType = (String) problemTypeBox.getSelectedItem();
        String description = problemDescriptionArea.getText().trim();

        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a description for the problem.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO problems (user_id, problem_type, problem_description) VALUES (?, ?, ?)")) {
            stmt.setInt(1, userId);
            stmt.setString(2, problemType);
            stmt.setString(3, description);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Problem submitted successfully.");
            dispose();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error submitting problem. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
