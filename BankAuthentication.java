import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BankAuthentication extends JFrame {

    public BankAuthentication() {
        
        setTitle("Authentication");
        setSize(450, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(245, 245, 245)); 

        
        JLabel accountLabel = new JLabel("Account number:");
        accountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JTextField accountField = new JTextField();
        accountField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JLabel pinLabel = new JLabel("Pin:");
        pinLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JPasswordField pinField = new JPasswordField();
        pinField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        panel.add(accountLabel);
        panel.add(accountField);
        panel.add(pinLabel);
        panel.add(pinField);

        
        JButton loginButton = new JButton("Log in");
        loginButton.setBackground(new Color(30, 144, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String accountNumber = accountField.getText();
                String pin = new String(pinField.getPassword());
                
                if (accountNumber.isEmpty() || pin.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter account number and pin.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                JOptionPane.showMessageDialog(null, "Logging in with Account: " + accountNumber);
                BankingSystem bankingSystem = new BankingSystem();
                bankingSystem.setVisible(true);
                dispose();
            }
        });

        
        JButton newAccountButton = new JButton("New Account");
        newAccountButton.setBackground(new Color(100, 149, 237));
        newAccountButton.setForeground(Color.WHITE);
        newAccountButton.setFocusPainted(false);
        newAccountButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        newAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                CreateAccountFrame createAccountFrame = new CreateAccountFrame();
                createAccountFrame.setVisible(true);
                dispose();
            }
        });

        
        JLabel titleLabel = new JLabel("LiLa Bank", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.GRAY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        
        add(titleLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        buttonPanel.add(loginButton);
        buttonPanel.add(newAccountButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankAuthentication authFrame = new BankAuthentication();
            authFrame.setVisible(true);
        });
    }
}
