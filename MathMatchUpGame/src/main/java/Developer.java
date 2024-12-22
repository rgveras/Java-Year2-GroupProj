import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.*;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents the Developer window for the game.
 * It extends JFrame and contains a JPanel for developer information.
 * @author Ty Bryson
 */
public class Developer extends JFrame {
    private JTable table;
    private JTextField levelField;
    private JButton updateButton;

    private DefaultTableModel tableModel;
    public Developer() {
        setTitle("Developer Mode");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color
        Color skyBlue = new Color(69, 141, 224);

        tableModel = new DefaultTableModel(new Object[]{"Username", "Level"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
        table = new JTable(tableModel);

        // Customize table header font
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 32));

        // Customize table cells font
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column == 0) { // Assuming the username column is the first column
                    setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Top, left, bottom, right padding
                }
                setFont(new Font("Arial", Font.BOLD, 20));
                return component;
            }
        });
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for level input and update button
        JPanel inputPanel = new JPanel(new BorderLayout());
        levelField = new JTextField(10);
        updateButton = new JButton("Update Level");
        updateButton.setFont(new Font ("Arial", Font.BOLD, 20));
        inputPanel.setFont(new Font ("Arial", Font.BOLD, 20));
        JPanel promptLine = new JPanel();
        promptLine.add(new JLabel("New Level:")).setFont(new Font ("Arial", Font.BOLD, 20));
        promptLine.add(levelField);
        promptLine.add(updateButton);
        inputPanel.add(promptLine, BorderLayout.SOUTH);
        JLabel description = new JLabel("<html><div style='text-align: center;'>" +
                "Welcome to Developer Mode. Select a user to change their access level<br>" +
                "Choose a new level (1-4) for the user.</div></html>", SwingConstants.CENTER);
        description.setFont(new Font("Arial", 0, 20));
        inputPanel.add(description, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.SOUTH);

        loadUsers();

        // Add action listener to update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSelectedUserLevel();
            }
        });
    }

    private void loadUsers() {
        List<User> users = readUsersFromJson(); // Implement this method to read users from JSON
        for (User user : users) {
            tableModel.addRow(new Object[]{user.getUsername(), user.getLevel()});
        }
    }

    private void updateSelectedUserLevel() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String username = tableModel.getValueAt(selectedRow, 0).toString();
                int newLevel = Integer.parseInt(levelField.getText());
                if (newLevel >= 1 && newLevel <= 4) {
                    // Update level in JSON
                    updateLevelInJson(username, newLevel);
                    // Refresh table to reflect the change
                    tableModel.setValueAt(newLevel, selectedRow, 1);
                } else {
                    JOptionPane.showMessageDialog(this, "Level must be between 1 and 4.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating level.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to update.");
        }
    }

// Utilize your existing methods for JSON handling

    private void updateLevelInJson(String username, int newLevel) throws Exception {
        JSONObject jsonObj = readJsonFile();
        JSONArray users = jsonObj.getJSONArray("users");
        for (int i = 0; i < users.length(); i++) {
            JSONObject user = users.getJSONObject(i);
            if (user.getString("username").equals(username)) {
                user.put("level", newLevel);
                saveJsonFile(jsonObj); // Save the updated JSON object back to the file
                break;
            }
        }
    }

    private JSONObject readJsonFile() throws Exception {
        String content = new String(Files.readAllBytes(Paths.get("data/accdata.json")));
        return new JSONObject(content);
    }

    private void saveJsonFile(JSONObject jsonObj) throws Exception {
        try (FileWriter file = new FileWriter("data/accdata.json")) {
            file.write(jsonObj.toString(4)); // Writing with indentation for better readability
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Developer().setVisible(true));
    }

    // Dummy User class - replace with your own implementation
    public class User {
        private String username;
        private int level;

        public User(String username, int level) {
            this.username = username;
            this.level = level;
        }

        public String getUsername() {
            return username; }
        public int getLevel() {
            return level; }
        // Setter methods as needed
    }

    // Implement these methods to handle JSON reading/writing
    private List<User> readUsersFromJson() {
        List<User> userList = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get("data/accdata.json")));
            JSONObject json = new JSONObject(content);
            JSONArray users = json.getJSONArray("users");
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                String username = user.getString("username"); // Assuming your JSON has a "username" field
                int level = user.getInt("level"); // Assuming your JSON has a "level" field
                userList.add(new User(username, level));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

}