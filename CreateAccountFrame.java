import java.awt.*;
import javax.swing.*;

public class CreateAccountFrame extends JFrame {

    public CreateAccountFrame() {
        
        setTitle("LiLa Bank");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        
        JLabel headerLabel = new JLabel("LiLa Bank", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        headerLabel.setForeground(Color.GRAY);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(headerLabel, BorderLayout.NORTH);


        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(14, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(30, 144, 255), 3), "New Account"));
        formPanel.setBackground(Color.WHITE);

        
        formPanel.add(new JLabel("Account no."));
        JTextField accountNoField = new JTextField();
        accountNoField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(accountNoField);

        formPanel.add(new JLabel("MICR no."));
        JTextField micrNoField = new JTextField();
        micrNoField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(micrNoField);

        formPanel.add(new JLabel("Pin"));
        JPasswordField pinField = new JPasswordField();
        pinField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(pinField);

        formPanel.add(new JLabel("Account Type"));
        JComboBox<String> accountTypeCombo = new JComboBox<>(new String[]{"Select", "Saving", "Current"});
        accountTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(accountTypeCombo);

        formPanel.add(new JLabel("Name"));
        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Date of Birth"));
        JTextField dobField = new JTextField();
        dobField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(dobField);

        formPanel.add(new JLabel("Nationality"));
        JComboBox<String> nationalityCombo = new JComboBox<>(new String[]{"Select", "Item 1", "Item 2", "Item 3"});
        nationalityCombo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(nationalityCombo);

        formPanel.add(new JLabel("Mobile no."));
        JTextField mobileField = new JTextField();
        mobileField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(mobileField);

        formPanel.add(new JLabel("Address"));
        JTextField addressField = new JTextField();
        addressField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(addressField);

        formPanel.add(new JLabel("Gender"));
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        genderPanel.setBackground(Color.WHITE);
        JRadioButton maleRadio = new JRadioButton("Male");
        maleRadio.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JRadioButton femaleRadio = new JRadioButton("Female");
        femaleRadio.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        formPanel.add(genderPanel);

        formPanel.add(new JLabel("Email"));
        JTextField emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        formPanel.add(emailField);

    
        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(30, 144, 255));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton clearButton = new JButton("Clear");
        clearButton.setBackground(new Color(100, 149, 237));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(220, 20, 60));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);

        
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        
        
//        JLabel thankYouLabel = new JLabel("Thank you and Welcome to LiLa Bank.", JLabel.CENTER);
//        thankYouLabel.setForeground(Color.RED);
//        thankYouLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
//        add(thankYouLabel, BorderLayout.PAGE_END);

        
        backButton.addActionListener(e -> {
            
            BankAuthentication authFrame = new BankAuthentication();
            authFrame.setVisible(true);
            this.dispose();
        });
        clearButton.addActionListener(e -> clearForm(formPanel));
        saveButton.addActionListener(e -> {
            
            Component[] components = formPanel.getComponents();
            boolean valid = true;
            for (int i = 1; i < components.length; i += 2) { 
                Component comp = components[i];
                if (comp instanceof JTextField) {
                    if (((JTextField) comp).getText().trim().isEmpty()) {
                        valid = false;
                        break;
                    }
                } else if (comp instanceof JPasswordField) {
                    if (((JPasswordField) comp).getPassword().length == 0) {
                        valid = false;
                        break;
                    }
                } else if (comp instanceof JComboBox) {
                    if (((JComboBox<?>) comp).getSelectedIndex() == 0) {
                        valid = false;
                        break;
                    }
                }
            }
            if (!valid) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields correctly.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Account created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            BankAuthentication authFrame = new BankAuthentication();
            authFrame.setVisible(true);
            this.dispose();
        });
    }

    private void clearForm(JPanel formPanel) {
        for (Component component : formPanel.getComponents()) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText("");
            } else if (component instanceof JPasswordField) {
                ((JPasswordField) component).setText("");
            } else if (component instanceof JComboBox) {
                ((JComboBox<?>) component).setSelectedIndex(0);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CreateAccountFrame form = new CreateAccountFrame();
            form.setVisible(true);
        });
    }
}
