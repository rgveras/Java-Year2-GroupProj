import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
/**
 * InsDash class represents the Instructor Dashboard GUI.
 * @author Zih Yu Liao
 */
public class Instructor extends JFrame{
    private JPanel panel1;
    public JPanel panelInstructor;
    private JButton backButton;

    /**
     * Constructs a new Instructor Dashboard frame.
     */
    public Instructor() {
        // Setting up the JFrame
        setTitle("Instructor Dashboard");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Initialize your panels or add components here
        panel1 = new JPanel();
        panelInstructor = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.add(panelInstructor, BorderLayout.CENTER);

        // Adding a button to trigger the JSON processing
        JButton processJsonBtn = new JButton("Process JSON");
        processJsonBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processJson();
            }
        });
        panel1.add(processJsonBtn, BorderLayout.SOUTH);
        // Initialize the back button
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the Back button is clicked
                // For example, close the current window:
                dispose();
                // If you have another frame to go back to, you might want to show it here.
                // Or, if you are switching panels within the same frame, you would change the displayed panel here.
            }
        });

        // Add the Back button to the top or another suitable place in your layout
        panel1.add(backButton, BorderLayout.NORTH);

        add(panel1);
    }
    /**
     * Processes a JSON string containing user information and displays it in a dialog box.
     *
     * This method parses a JSON string containing user information and constructs a formatted HTML
     * string with usernames and their corresponding highest scores (if available). It then displays
     * this information in a dialog box using JOptionPane.
     *
     * @throws JSONException if there is an error in parsing the JSON string
     */

    private void processJson() {
        // Sample JSON string
        String jsonString = "{\"isAdmin\":true,\"users\":[{\"password\":\"admin123\",\"level\":1,\"isAdmin\":true,\"username\":\"admin\"},{\"password\":\"password1\",\"level\":3,\"isAdmin\":false,\"highest_score\":220,\"username\":\"user1\"},{\"password\":\"password2\",\"level\":1,\"isAdmin\":false,\"highest_score\":150,\"username\":\"user2\"},{\"password\":\"password3\",\"level\":1,\"isAdmin\":false,\"highest_score\":200,\"username\":\"user3\"},{\"password\":\"uu\",\"level\":1,\"isAdmin\":false,\"highest_score\":140,\"username\":\"uu\"},{\"password\":\"a\",\"level\":1,\"isAdmin\":false,\"highest_score\":300,\"username\":\"a\"},{\"password\":\"123456\",\"level\":1,\"isAdmin\":false,\"highest_score\":100,\"username\":\"rTest\"},{\"password\":\"ab\",\"level\":1,\"isAdmin\":false,\"username\":\"ab\"}]}";

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray usersArray = jsonObject.getJSONArray("users");
            StringBuilder userDetails = new StringBuilder("<html>");

            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject user = usersArray.getJSONObject(i);
                String username = user.getString("username");
                int highestScore = user.optInt("highest_score", -1); // Use -1 as default if highest_score doesn't exist

                userDetails.append("Username: ").append(username).append(", Highest Score: ").append(highestScore).append("<br>");
            }

            userDetails.append("</html>");
            JOptionPane.showMessageDialog(this, userDetails.toString(), "User Details", JOptionPane.INFORMATION_MESSAGE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Instructor().setVisible(true);
            }
        });
    }


}