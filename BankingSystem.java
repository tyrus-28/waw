import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class BankingSystem extends JFrame {

    private double balance = 0.0;
    private String accountNumber = "123456789";
    private String accountType = "Savings";
    private ArrayList<String[]> transactionHistory = new ArrayList<>();
    private JTable transactionTable;
    private DefaultTableModel transactionTableModel;
    private JTextField amountField;
    private JTextField recipientField;
    private JLabel balanceLabel;
    private JPanel mainContentPanel;

    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton createAccountButton;

    private JPanel createAccountPanel;
    private JTextField newAccountNumberField;
    private JTextField newAccountTypeField;
    private JTextField newInitialBalanceField;
    private JButton createNewAccountButton = new JButton("Create Account");
    private JButton backToLoginButton;

    private boolean loggedIn = false;

    public BankingSystem() {
        setTitle("LiLa Bank");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(new Color(245, 245, 245));
        setContentPane(contentPane);

        
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new GridLayout(5, 1, 10, 10));
        sidebarPanel.setBackground(new Color(30, 144, 255)); 
        sidebarPanel.setPreferredSize(new Dimension(220, 0));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        String[] menuItems = {"Dashboard", "Accounts", "Transfers", "Settings", "Logout"};
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setForeground(Color.WHITE);
            button.setBackground(new Color(25, 25, 112)); 
            button.setFocusPainted(false);
            button.setFont(new Font("Segoe UI", Font.BOLD, 16));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            if (item.equals("Logout")) {
                button.addActionListener(e -> logout());
            } else {
                button.addActionListener(e -> showPanel(item));
            }
            sidebarPanel.add(button);
        }

        contentPane.add(sidebarPanel, BorderLayout.WEST);

        
        mainContentPanel = new JPanel(new CardLayout());
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.add(mainContentPanel, BorderLayout.CENTER);

        
        mainContentPanel.add(createDashboardPanel(), "Dashboard");
        mainContentPanel.add(createAccountsPanel(), "Accounts");
        mainContentPanel.add(createTransfersPanel(), "Transfers");
        mainContentPanel.add(createSettingsPanel(), "Settings");

        
        showPanel("Dashboard");
    }

    public BankingSystem(String accountNumber, String accountType, double initialBalance) {
        this();
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = initialBalance;
    }

    private JPanel createAccountsPanel() {
        JPanel accountsPanel = new JPanel();
        accountsPanel.setLayout(new BorderLayout());
        accountsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        accountsPanel.setBackground(new Color(250, 250, 250)); 

        JLabel accountsLabel = new JLabel("Account Details");
        accountsLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        accountsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        accountsLabel.setForeground(new Color(25, 25, 112)); 
        accountsPanel.add(accountsLabel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(3, 1, 15, 15));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(30, 144, 255), 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel accountNumberLabel = new JLabel("Account Number: " + accountNumber);
        accountNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JLabel accountTypeLabel = new JLabel("Account Type: " + accountType);
        accountTypeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        JLabel balanceLabelPanel = new JLabel("Balance: $" + new DecimalFormat("#0.00").format(balance));
        balanceLabelPanel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        balanceLabelPanel.setForeground(new Color(30, 144, 255));

        detailsPanel.add(accountNumberLabel);
        detailsPanel.add(accountTypeLabel);
        detailsPanel.add(balanceLabelPanel);

        accountsPanel.add(detailsPanel, BorderLayout.CENTER);

        return accountsPanel;
    }

    private JPanel createTransfersPanel() {
        JPanel transfersPanel = new JPanel();
        transfersPanel.setLayout(new GridBagLayout());
        transfersPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        transfersPanel.setBackground(new Color(245, 255, 250)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Transfer Funds");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        transfersPanel.add(titleLabel, gbc);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        transfersPanel.add(amountLabel, gbc);

        amountField = new JTextField(20);
        amountField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 1;
        transfersPanel.add(amountField, gbc);

        JLabel recipientLabel = new JLabel("Recipient Account:");
        recipientLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        transfersPanel.add(recipientLabel, gbc);

        recipientField = new JTextField(20);
        recipientField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 1;
        gbc.gridy = 2;
        transfersPanel.add(recipientField, gbc);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBackground(new Color(30, 144, 255));
        transferButton.setForeground(Color.WHITE);
        transferButton.setFocusPainted(false);
        transferButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        transferButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        transferButton.addActionListener(e -> transfer());
        transfersPanel.add(transferButton, gbc);

        return transfersPanel;
    }

    private JPanel createDashboardPanel() {
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new BorderLayout());
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        dashboardPanel.setBackground(new Color(255, 250, 250)); 

        
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new GridLayout(3, 1, 10, 10));
        summaryPanel.setBackground(new Color(245, 245, 255));

        balanceLabel = new JLabel("Balance: $" + new DecimalFormat("#0.00").format(balance));
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        balanceLabel.setForeground(new Color(30, 144, 255));
        JLabel accountLabel = new JLabel("Account Number: " + accountNumber);
        accountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JLabel typeLabel = new JLabel("Account Type: " + accountType);
        typeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        summaryPanel.add(balanceLabel);
        summaryPanel.add(accountLabel);
        summaryPanel.add(typeLabel);

        dashboardPanel.add(summaryPanel, BorderLayout.NORTH);

        
        String[] columnNames = {"Date", "Description", "Amount"};
        transactionTableModel = new DefaultTableModel(columnNames, 0);
        transactionTable = new JTable(transactionTableModel);
        transactionTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        transactionTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        dashboardPanel.add(scrollPane, BorderLayout.CENTER);

        
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        actionPanel.setBackground(new Color(255, 250, 250));

        JButton depositButton = new JButton("Deposit");
        depositButton.setBackground(new Color(30, 144, 255));
        depositButton.setForeground(Color.WHITE);
        depositButton.setFocusPainted(false);
        depositButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        depositButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        depositButton.addActionListener(e -> deposit());
        actionPanel.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBackground(new Color(220, 20, 60));
        withdrawButton.setForeground(Color.WHITE);
        withdrawButton.setFocusPainted(false);
        withdrawButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        withdrawButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        withdrawButton.addActionListener(e -> withdraw());
        actionPanel.add(withdrawButton);

        dashboardPanel.add(actionPanel, BorderLayout.SOUTH);

        return dashboardPanel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
        panel.setBackground(new Color(245, 245, 245)); 

        JLabel titleLabel = new JLabel("Welcome to LiLa Bank");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(25, 25, 112));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        usernameField = new JTextField(20);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(30, 144, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> handleLogin());

        createAccountButton = new JButton("Create New Account");
        createAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccountButton.setBackground(new Color(100, 149, 237));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setFocusPainted(false);
        createAccountButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        createAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAccountButton.addActionListener(e -> showPanel("CreateAccount"));

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(createAccountButton);

        return panel;
    }

    private JPanel createCreateAccountPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));
        panel.setBackground(new Color(255, 250, 240)); 

        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel accountNumberLabel = new JLabel("Account Number:");
        accountNumberLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        accountNumberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        newAccountNumberField = new JTextField(20);
        newAccountNumberField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        newAccountNumberField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        accountTypeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        newAccountTypeField = new JTextField(20);
        newAccountTypeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        newAccountTypeField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel initialBalanceLabel = new JLabel("Initial Balance:");
        initialBalanceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        initialBalanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        newInitialBalanceField = new JTextField(20);
        newInitialBalanceField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        newInitialBalanceField.setAlignmentX(Component.CENTER_ALIGNMENT);

        createNewAccountButton = new JButton("Create Account");
        createNewAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createNewAccountButton.setBackground(new Color(70, 130, 180));
        createNewAccountButton.setForeground(Color.WHITE);
        createNewAccountButton.setFocusPainted(false);
        createNewAccountButton.setFont(new Font("Arial", Font.BOLD, 16));
        createNewAccountButton.addActionListener(e -> handleCreateAccount());

        backToLoginButton = new JButton("Back to Login");
        backToLoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backToLoginButton.setBackground(new Color(100, 149, 237));
        backToLoginButton.setForeground(Color.WHITE);
        backToLoginButton.setFocusPainted(false);
        backToLoginButton.setFont(new Font("Arial", Font.BOLD, 14));
        backToLoginButton.addActionListener(e -> showPanel("Login"));

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(accountNumberLabel);
        panel.add(newAccountNumberField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(accountTypeLabel);
        panel.add(newAccountTypeField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(initialBalanceLabel);
        panel.add(newInitialBalanceField);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(createNewAccountButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(backToLoginButton);

        return panel;
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password.", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        loggedIn = true;
        JOptionPane.showMessageDialog(this, "Login successful. Welcome, " + username + "!", "Login", JOptionPane.INFORMATION_MESSAGE);
        showPanel("Dashboard");
    }

    private void handleCreateAccount() {
        String accNumber = newAccountNumberField.getText();
        String accType = newAccountTypeField.getText();
        String initialBalanceStr = newInitialBalanceField.getText();

        if (accNumber.isEmpty() || accType.isEmpty() || initialBalanceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double initialBalance;
        try {
            initialBalance = Double.parseDouble(initialBalanceStr);
            if (initialBalance < 0) {
                JOptionPane.showMessageDialog(this, "Initial balance cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid initial balance.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        accountNumber = accNumber;
        accountType = accType;
        balance = initialBalance;

        
        transactionHistory.clear();
        if (transactionTableModel != null) {
            transactionTableModel.setRowCount(0);
        }

        loggedIn = true;
        JOptionPane.showMessageDialog(this, "Account created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        showPanel("Dashboard");
        updateBalanceLabel();
        updateAccountDetails();
    }

    private JPanel createSettingsPanel() {
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout());
        settingsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        settingsPanel.setBackground(new Color(255, 250, 240)); 

        JLabel settingsLabel = new JLabel("Settings");
        settingsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        settingsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        settingsPanel.add(settingsLabel, BorderLayout.NORTH);

        JPanel settingsContentPanel = new JPanel();
        settingsContentPanel.setLayout(new BoxLayout(settingsContentPanel, BoxLayout.Y_AXIS));
        settingsContentPanel.setBackground(Color.WHITE);
        settingsContentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JCheckBox notificationsCheckBox = new JCheckBox("Enable Notifications");
        notificationsCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));
        notificationsCheckBox.setBackground(Color.WHITE);
        notificationsCheckBox.setSelected(true);

        JCheckBox darkModeCheckBox = new JCheckBox("Enable Dark Mode");
        darkModeCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));
        darkModeCheckBox.setBackground(Color.WHITE);

        JButton saveButton = new JButton("Save Settings");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setBackground(new Color(70, 130, 180));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.addActionListener(e -> {
            String message = "Settings saved:\n" +
                             "Notifications: " + (notificationsCheckBox.isSelected() ? "Enabled" : "Disabled") + "\n" +
                             "Dark Mode: " + (darkModeCheckBox.isSelected() ? "Enabled" : "Disabled");
            JOptionPane.showMessageDialog(this, message, "Settings", JOptionPane.INFORMATION_MESSAGE);
        });

        settingsContentPanel.add(notificationsCheckBox);
        settingsContentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        settingsContentPanel.add(darkModeCheckBox);
        settingsContentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        settingsContentPanel.add(saveButton);

        settingsPanel.add(settingsContentPanel, BorderLayout.CENTER);

        return settingsPanel;
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "You have been logged out.", "Logout", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            BankAuthentication authFrame = new BankAuthentication();
            authFrame.setVisible(true);
        }
    }

    private void showPanel(String name) {
        CardLayout cl = (CardLayout)(mainContentPanel.getLayout());
        cl.show(mainContentPanel, name);
    }

    private void deposit() {
        String amountText = JOptionPane.showInputDialog(this, "Enter amount to deposit:");
        if (amountText != null) {
            try {
                double amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Amount must be positive.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                balance += amount;
                addTransaction("Deposit", amount);
                updateBalanceLabel();
                updateAccountDetails();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void withdraw() {
        String amountText = JOptionPane.showInputDialog(this, "Enter amount to withdraw:");
        if (amountText != null) {
            try {
                double amount = Double.parseDouble(amountText);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Amount must be positive.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (amount > balance) {
                    JOptionPane.showMessageDialog(this, "Insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                balance -= amount;
                addTransaction("Withdraw", -amount);
                updateBalanceLabel();
                updateAccountDetails();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void transfer() {
        String amountText = amountField.getText();
        String recipient = recipientField.getText();

        if (amountText.isEmpty() || recipient.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter amount and recipient.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be positive.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (amount > balance) {
                JOptionPane.showMessageDialog(this, "Insufficient balance.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            balance -= amount;
            addTransaction("Transfer to " + recipient, -amount);
            updateBalanceLabel();
            updateAccountDetails();
            amountField.setText("");
            recipientField.setText("");
            JOptionPane.showMessageDialog(this, "Transfer successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addTransaction(String description, double amount) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        transactionHistory.add(new String[]{date, description, new DecimalFormat("#0.00").format(amount)});
        transactionTableModel.addRow(new Object[]{date, description, "$" + new DecimalFormat("#0.00").format(amount)});
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Balance: $" + new DecimalFormat("#0.00").format(balance));
        updateAccountDetails(); 
    }

    private void updateAccountDetails() {
        
        Component comp = mainContentPanel.getComponent(1); 
        if (comp instanceof JPanel) {
            JPanel accountsPanel = (JPanel) comp;
            for (Component c : accountsPanel.getComponents()) {
                if (c instanceof JPanel) {
                    JPanel detailsPanel = (JPanel) c;
                    for (Component labelComp : detailsPanel.getComponents()) {
                        if (labelComp instanceof JLabel) {
                            JLabel label = (JLabel) labelComp;
                            if (label.getText().startsWith("Account Number:")) {
                                label.setText("Account Number: " + accountNumber);
                            } else if (label.getText().startsWith("Account Type:")) {
                                label.setText("Account Type: " + accountType);
                            } else if (label.getText().startsWith("Balance:")) {
                                label.setText("Balance: $" + new DecimalFormat("#0.00").format(balance));
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankAuthentication authFrame = new BankAuthentication();
            authFrame.setVisible(true);
        });
    }
}
