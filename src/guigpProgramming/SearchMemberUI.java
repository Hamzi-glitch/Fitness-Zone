/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guigpProgramming;


import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class SearchMemberUI extends JFrame {

    // ===== DATABASE =====
    private DatabaseManager db = new DatabaseManager();

    // ===== UI COMPONENTS =====
    private JTextField searchTF, nameTF, contactTF, durationTF, totalTF;
    private JComboBox<String> membershipCB;
    private JRadioButton standardRB, studentRB;
    private JButton searchBtn, clearBtn;

    // NAVIGATION BUTTONS
    private JButton navRegBtn, navDisplayBtn, navEditBtn;

    // ===== THEME =====
    private final Color bgColor = new Color(240, 242, 245);
    private final Color cardColor = Color.WHITE;
    private final Color primaryBlue = new Color(37, 99, 235);
    private final Color navGray = new Color(51, 65, 85);
    private final Color secondaryGray = new Color(100, 116, 139);
    private final Color readOnlyBg = new Color(248, 250, 252);
    private final Color textColor = new Color(30, 41, 59);

    public SearchMemberUI() {
        setTitle("Fitzone Management System - Search");
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
        

        JLabel brandLabel = new JLabel("FITZONE ");
        brandLabel.setForeground(Color.WHITE);
        brandLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        navPanel.add(brandLabel);

        navRegBtn = createNavButton("📝 Register");
        navDisplayBtn = createNavButton("📋 Display All");
        navEditBtn = createNavButton("✏️ Edit Member");

        navPanel.add(navRegBtn);
        navPanel.add(navDisplayBtn);
        navPanel.add(navEditBtn);

        mainLayout.add(navPanel, BorderLayout.NORTH);

        // ===== CONTENT =====
        JPanel contentArea = new JPanel(new BorderLayout(0, 20));
        contentArea.setOpaque(false);
        contentArea.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainLayout.add(contentArea, BorderLayout.CENTER);

        // SEARCH CARD
        JPanel searchCard = createStyledCard("Member Lookup");
        searchCard.setLayout(new BorderLayout(15, 0));

        searchTF = createStyledField(true);
        searchBtn = createCustomButton("Find Member", primaryBlue);

        searchCard.add(new JLabel("Enter Member Name: "), BorderLayout.WEST);
        searchCard.add(searchTF, BorderLayout.CENTER);
        searchCard.add(searchBtn, BorderLayout.EAST);

        // RESULT CARD
        JPanel resultsCard = createStyledCard("Member Details");
        resultsCard.setLayout(new GridLayout(6, 2, 20, 20));

        nameTF = createStyledField(false);
        contactTF = createStyledField(false);
        durationTF = createStyledField(false);
        totalTF = createStyledField(false);

        membershipCB = new JComboBox<>(new String[]{"Basic", "Premium", "VIP"});
        membershipCB.setEnabled(false);

        standardRB = new JRadioButton("Standard");
        studentRB = new JRadioButton("Student");
        standardRB.setEnabled(false);
        studentRB.setEnabled(false);
        standardRB.setBackground(cardColor);
        studentRB.setBackground(cardColor);

        JPanel ratePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        ratePanel.setBackground(cardColor);
        ratePanel.add(standardRB);
        ratePanel.add(studentRB);

        addFormField(resultsCard, "Full Name:", nameTF);
        addFormField(resultsCard, "Contact Number:", contactTF);
        addFormField(resultsCard, "Membership Level:", membershipCB);
        addFormField(resultsCard, "Rate Category:", ratePanel);
        addFormField(resultsCard, "Duration (Months):", durationTF);
        addFormField(resultsCard, "Total Payment (RM):", totalTF);

        clearBtn = createCustomButton("Clear Search Results", secondaryGray);

        contentArea.add(searchCard, BorderLayout.NORTH);
        contentArea.add(resultsCard, BorderLayout.CENTER);
        contentArea.add(clearBtn, BorderLayout.SOUTH);

        // ===== NAVIGATION EVENTS =====
        navRegBtn.addActionListener(e -> { new RegisterMemberUI().setVisible(true); dispose(); });
        navDisplayBtn.addActionListener(e -> { new DisplayMembersUI().setVisible(true); dispose(); });
        navEditBtn.addActionListener(e -> { new EditDeleteMemberUI().setVisible(true); dispose(); });

        // ===== LOGIC EVENTS =====
        searchBtn.addActionListener(e -> searchMember());
        clearBtn.addActionListener(e -> clearForm());
    }

    // ===== SEARCH LOGIC (DatabaseManager) =====
    private void searchMember() {
        if (searchTF.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Search field is empty.");
            return;
        }

        ArrayList<Member> list = db.searchMember(searchTF.getText());

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No member found.");
            clearForm();
            return;
        }

        Member m = list.get(0); // first match

        nameTF.setText(m.getMembername());
        contactTF.setText(m.getContactNum());
        membershipCB.setSelectedItem(m.getMembership());
        durationTF.setText(String.valueOf(m.getDuration()));
        totalTF.setText(String.format("%.2f", m.getTotal()));

        if (m.getRate().equalsIgnoreCase("Standard"))
            standardRB.setSelected(true);
        else
            studentRB.setSelected(true);
    }

    private void clearForm() {
        searchTF.setText("");
        nameTF.setText("");
        contactTF.setText("");
        durationTF.setText("");
        totalTF.setText("");
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

    private JTextField createStyledField(boolean editable) {
        JTextField tf = new JTextField();
        tf.setEditable(editable);
        tf.setBackground(editable ? Color.WHITE : readOnlyBg);
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return tf;
    }

    private JButton createCustomButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
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
        SwingUtilities.invokeLater(() -> new SearchMemberUI().setVisible(true));
    }
}
