import javax.swing.*;
import java.awt.*;

public class ContactPersonScreen extends JFrame {

    public ContactPersonScreen() {
        // Set up frame properties
        setTitle("Contact Person");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        // Create main panel with a vertical layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        // City dropdown
        JLabel cityLabel = new JLabel("Select Location:");
        String[] cities = {"Select City", "Chennai", "Tambaram", "Potheri"};
        JComboBox<String> cityDropdown = new JComboBox<>(cities);
        cityDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Issue type dropdown
        JLabel issueLabel = new JLabel("Select Issue:");
        String[] issues = {"Select Issue", "Water Issue", "Electricity Issue"};
        JComboBox<String> issueDropdown = new JComboBox<>(issues);
        issueDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        // Display contact button
        JButton displayContactButton = new JButton("Show Contact");
        displayContactButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        displayContactButton.setBackground(new Color(70, 130, 180));
        displayContactButton.setForeground(Color.WHITE);
        displayContactButton.setFocusPainted(false);

        // Action for displayContactButton
        displayContactButton.addActionListener(e -> {
            String city = cityDropdown.getSelectedItem().toString();
            String issue = issueDropdown.getSelectedItem().toString();
            String contactInfo = getContactInfo(city, issue);
            JOptionPane.showMessageDialog(this, contactInfo);
        });

        // Add components to panel
        panel.add(cityLabel);
        panel.add(cityDropdown);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        panel.add(issueLabel);
        panel.add(issueDropdown);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacer
        panel.add(displayContactButton);

        // Add panel to frame
        add(panel);
        setVisible(true);
    }

    // Method to get contact information based on city and issue type
    private String getContactInfo(String city, String issueType) {
        String contactInfo = "";

        switch (city) {
            case "Chennai":
                contactInfo = issueType.equals("Water Issue") ? "Plumber: 123-456-7890" :
                              issueType.equals("Electricity Issue") ? "Electrician: 987-654-3210" :
                              "Please select a valid issue type.";
                break;
            case "Tambaram":
                contactInfo = issueType.equals("Water Issue") ? "Plumber: 111-222-3333" :
                              issueType.equals("Electricity Issue") ? "Electrician: 444-555-6666" :
                              "Please select a valid issue type.";
                break;
            case "Potheri":
                contactInfo = issueType.equals("Water Issue") ? "Plumber: 777-888-9999" :
                              issueType.equals("Electricity Issue") ? "Electrician: 000-111-2222" :
                              "Please select a valid issue type.";
                break;
            default:
                contactInfo = "Please select a valid city.";
        }

        return contactInfo;
    }
}
