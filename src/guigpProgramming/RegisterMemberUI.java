/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guigpProgramming;


import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class RegisterMemberUI extends JFrame {

    // ===== DATABASE =====
    private DatabaseManager db = new DatabaseManager();

    // ===== UI COMPONENTS =====
    private JTextField nameTF, contactTF, durationTF, totalTF;
    private JComboBox<String> membershipCB;
    private JRadioButton standardRB, studentRB;
    private JButton calculateBtn, saveBtn, resetBtn;
    private JTextArea summaryTA;

    // NAVIGATION
    private JButton navSearchBtn, navDisplayBtn, navEditBtn;

    // ===== THEME =====
    private final Color bgColor = new Color(240, 242, 245);
    private final Color cardColor = Color.WHITE;
    private final Color primaryBlue = new Color(37, 99, 235);
    private final Color navGray = new Color(51, 65, 85);
    private final Color successGreen = new Color(22, 163, 74);
    private final Color dangerRed = new Color(220, 38, 38);
    private final Color textColor = new Color(30, 41, 59);

    public RegisterMemberUI() {
        setTitle("Fitzone Management System - Registration");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainLayout = new JPanel(new BorderLayout());
        mainLayout.setBackground(bgColor);
        setContentPane(mainLayout);

        // ===== NAV BAR =====
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        navPanel.setBackground(navGray);
        exitBtn = createNavButton("❌ Exit");
        navPanel.add(exitBtn);
        exitBtn.addActionListener(e -> exitApp());

        JLabel brand = new JLabel("FITZONE");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("Segoe UI", Font.BOLD, 18));
        navPanel.add(brand);

        navSearchBtn = createNavButton("🔍 Search");
        navDisplayBtn = createNavButton("📋 Display All");
        navEditBtn = createNavButton("✏️ Edit Member");

        navPanel.add(navSearchBtn);
        navPanel.add(navDisplayBtn);
        navPanel.add(navEditBtn);

        mainLayout.add(navPanel, BorderLayout.NORTH);

        // ===== CONTENT =====
        JPanel content = new JPanel(new BorderLayout(20, 0));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainLayout.add(content, BorderLayout.CENTER);

        // LEFT: FORM
        JPanel leftPanel = createStyledCard("Member Registration");
        leftPanel.setLayout(new GridLayout(6, 2, 15, 20));

        nameTF = createStyledField();
        contactTF = createStyledField();
        durationTF = createStyledField();

        totalTF = createStyledField();
        totalTF.setEditable(false);
        totalTF.setBackground(new Color(248, 250, 252));

        membershipCB = new JComboBox<>(new String[]{"Basic", "Premium", "VIP"});
        membershipCB.setBackground(Color.WHITE);

        standardRB = new JRadioButton("Standard");
        studentRB = new JRadioButton("Student");
        standardRB.setBackground(Color.WHITE);
        studentRB.setBackground(Color.WHITE);

        ButtonGroup rateGroup = new ButtonGroup();
        rateGroup.add(standardRB);
        rateGroup.add(studentRB);

        JPanel ratePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        ratePanel.setBackground(Color.WHITE);
        ratePanel.add(standardRB);
        ratePanel.add(studentRB);

        addFormField(leftPanel, "Full Name", nameTF);
        addFormField(leftPanel, "Contact Number", contactTF);
        addFormField(leftPanel, "Membership", membershipCB);
        addFormField(leftPanel, "Rate Type", ratePanel);
        addFormField(leftPanel, "Duration (Months)", durationTF);
        addFormField(leftPanel, "Total Due (RM)", totalTF);

        // RIGHT: SUMMARY
        JPanel rightPanel = new JPanel(new BorderLayout(0, 20));
        rightPanel.setOpaque(false);

        JPanel summaryCard = createStyledCard("Registration Summary");
        summaryCard.setLayout(new BorderLayout());

        summaryTA = new JTextArea();
        summaryTA.setEditable(false);
        summaryTA.setFont(new Font("Monospaced", Font.PLAIN, 13));
        summaryTA.setText("\n   Awaiting calculation...");
        summaryCard.add(new JScrollPane(summaryTA), BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        actionPanel.setOpaque(false);

        calculateBtn = createCustomButton("Calculate", primaryBlue);
        saveBtn = createCustomButton("Save Member", successGreen);
        resetBtn = createCustomButton("Reset", dangerRed);

        actionPanel.add(calculateBtn);
        actionPanel.add(saveBtn);
        actionPanel.add(resetBtn);

        rightPanel.add(summaryCard, BorderLayout.CENTER);
        rightPanel.add(actionPanel, BorderLayout.SOUTH);

        content.add(leftPanel, BorderLayout.WEST);
        content.add(rightPanel, BorderLayout.CENTER);

        // ===== NAV EVENTS =====
        navSearchBtn.addActionListener(e -> { new SearchMemberUI().setVisible(true); dispose(); });
        navDisplayBtn.addActionListener(e -> { new DisplayMembersUI().setVisible(true); dispose(); });
        navEditBtn.addActionListener(e -> { new EditDeleteMemberUI().setVisible(true); dispose(); });

        // ===== FORM EVENTS =====
        calculateBtn.addActionListener(e -> calculateTotal());
        saveBtn.addActionListener(e -> saveData());
        resetBtn.addActionListener(e -> resetForm());
    }

    // ===== LOGIC =====
    private void calculateTotal() {
        try {
            int duration = Integer.parseInt(durationTF.getText());

            Member temp = new Member(
                    nameTF.getText(),
                    contactTF.getText(),
                    membershipCB.getSelectedItem().toString(),
                    standardRB.isSelected() ? "Standard" : "Student",
                    duration
            );

            totalTF.setText(String.format("%.2f", temp.getTotal()));

            summaryTA.setText(
                    "\n  FITZONE MEMBERSHIP RECEIPT\n"
                  + "  Name      : " + temp.getMembername() + "\n"
                  + "  Membership: " + temp.getMembership() + "\n"
                  + "  Rate Type : " + temp.getRate() + "\n"
                  + "  Duration  : " + temp.getDuration() + " months\n"
                  + "  TOTAL     : RM " + String.format("%.2f", temp.getTotal())
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void saveData() {
        try {
            int duration = Integer.parseInt(durationTF.getText());

            Member member = new Member(
                    nameTF.getText(),
                    contactTF.getText(),
                    membershipCB.getSelectedItem().toString(),
                    standardRB.isSelected() ? "Standard" : "Student",
                    duration
            );
            
          

            if (db.addMember(member)) {
                JOptionPane.showMessageDialog(this, "Member registered successfully!");
                summaryTA.append("\n\n  Status: SAVED TO DATABASE");
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save member.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please complete all fields correctly.");
        }
    }

    private void resetForm() {
        nameTF.setText("");
        contactTF.setText("");
        durationTF.setText("");
        totalTF.setText("");
        summaryTA.setText("\n   Awaiting calculation...");
        membershipCB.setSelectedIndex(0);
        standardRB.setSelected(false);
        studentRB.setSelected(false);
    }

    // ===== UI HELPERS =====
    private JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setForeground(Color.WHITE);
        btn.setBackground(navGray);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(primaryBlue);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(navGray);
            }
        });
        return btn;
    }

    private JPanel createStyledCard(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(cardColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(226, 232, 240), 1, true),
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(15, 15, 15, 15),
                        title,
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 14),
                        primaryBlue
                )
        ));
        return panel;
    }

    private void addFormField(JPanel panel, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(textColor);
        panel.add(label);
        panel.add(field);
    }

    private JTextField createStyledField() {
        JTextField tf = new JTextField();
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return tf;
    }

    private JButton createCustomButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        return btn;
    }
    
    private void exitApp() {
    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Are you sure you want to exit?",
        "Exit Application",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        System.exit(0);
        }
    }
    
    private javax.swing.JButton exitBtn;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterMemberUI().setVisible(true));
    }
}
