import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProblemHistoryScreen extends JFrame {
    private int userId;

    public ProblemHistoryScreen(int userId) {
        this.userId = userId;

        // Set up frame properties
        setTitle("Problem History");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setResizable(false);

        // Create a main panel with vertical BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // Header label
        JLabel headerLabel = new JLabel("Your Reported Problems");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create a text area for displaying the problem history
        JTextArea historyArea = new JTextArea(15, 40);
        historyArea.setEditable(false);
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);
        historyArea.setFont(new Font("Arial", Font.PLAIN, 14));

        // Scroll pane for text area
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Fetch and display problem history from the database
        loadProblemHistory(historyArea);

        // Back button to close the window
        JButton backButton = new JButton("Back");
        customizeButton(backButton);
        backButton.addActionListener(e -> dispose());

        // Add components to main panel
        mainPanel.add(headerLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacer
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacer
        mainPanel.add(backButton);

        // Add main panel to frame
        add(mainPanel);
        setVisible(true);
    }

    // Method to fetch and load problem history from the database
    private void loadProblemHistory(JTextArea historyArea) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT problem_type, problem_description, created_at FROM problems WHERE user_id = ? ORDER BY created_at DESC")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            StringBuilder history = new StringBuilder();
            while (rs.next()) {
                history.append("📌 Type: ").append(rs.getString("problem_type")).append("\n")
                       .append("📝 Description: ").append(rs.getString("problem_description")).append("\n")
                       .append("📅 Date: ").append(rs.getTimestamp("created_at")).append("\n")
                       .append("────────────────────────────\n");
            }

            if (history.length() == 0) {
                historyArea.setText("No problems reported yet.");
            } else {
                historyArea.setText(history.toString());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error fetching problem history.", "Database Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
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
}
