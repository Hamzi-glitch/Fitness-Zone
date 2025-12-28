/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guigpProgramming;

import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;


public class EditDeleteMemberUI extends JFrame {

    // ===== DATABASE =====
    private DatabaseManager db = new DatabaseManager();
    private Member currentMember;

    // ===== UI COMPONENTS =====
    private JTextField searchTF, nameTF, contactTF, durationTF, totalTF;
    private JComboBox<String> membershipCB;
    private JRadioButton standardRB, studentRB;
    private JButton searchBtn, updateBtn, deleteBtn, clearBtn;

    // NAVIGATION
    private JButton navRegBtn, navSearchBtn, navDisplayBtn;

    // ===== ENHANCED THEME =====
    private final Color bgColor = new Color(240, 242, 245);
    private final Color cardColor = Color.WHITE;
    private final Color primaryBlue = new Color(37, 99, 235);
    private final Color navGray = new Color(51, 65, 85);
    private final Color successGreen = new Color(22, 163, 74);
    private final Color dangerRed = new Color(220, 38, 38);
    private final Color secondaryGray = new Color(100, 116, 139);
    private final Color readOnlyBg = new Color(248, 250, 252);
    private final Color textColor = new Color(30, 41, 59);

    public EditDeleteMemberUI() {
        setTitle("Fitzone Management System - Edit/Delete");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainLayout = new JPanel(new BorderLayout());
        mainLayout.setBackground(bgColor);
        setContentPane(mainLayout);

        // ===== NAVIGATION PANEL =====
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        navPanel.setBackground(navGray);

        JLabel brand = new JLabel("FITZONE");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("Segoe UI", Font.BOLD, 18));
        navPanel.add(brand);

        navRegBtn = createNavButton("📝 Register");
        navSearchBtn = createNavButton("🔍 Search");
        navDisplayBtn = createNavButton("📋 Display All");

        navPanel.add(navRegBtn);
        navPanel.add(navSearchBtn);
        navPanel.add(navDisplayBtn);

        mainLayout.add(navPanel, BorderLayout.NORTH);

        // ===== CONTENT AREA =====
        JPanel content = new JPanel(new BorderLayout(0, 20));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainLayout.add(content, BorderLayout.CENTER);

        // TOP: SEARCH CARD
        JPanel searchCard = createStyledCard("Find Member to Modify");
        searchCard.setLayout(new BorderLayout(15, 0));

        searchTF = createStyledField(true);
        searchBtn = createCustomButton("Find Member", primaryBlue);

        JLabel searchLabel = new JLabel("Full Name:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        searchCard.add(searchLabel, BorderLayout.WEST);
        searchCard.add(searchTF, BorderLayout.CENTER);
        searchCard.add(searchBtn, BorderLayout.EAST);

        // CENTER: FORM CARD
        JPanel formCard = createStyledCard("Edit Member Information");
        formCard.setLayout(new GridLayout(6, 2, 20, 20));

        nameTF = createStyledField(true);
        contactTF = createStyledField(true);
        durationTF = createStyledField(true);
        totalTF = createStyledField(false);

        membershipCB = new JComboBox<>(new String[]{"Basic", "Premium", "VIP"});
        membershipCB.setBackground(Color.WHITE);

        standardRB = new JRadioButton("Standard");
        studentRB = new JRadioButton("Student");
        standardRB.setBackground(cardColor);
        studentRB.setBackground(cardColor);

        ButtonGroup rateGroup = new ButtonGroup();
        rateGroup.add(standardRB);
        rateGroup.add(studentRB);

        JPanel ratePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        ratePanel.setBackground(cardColor);
        ratePanel.add(standardRB);
        ratePanel.add(studentRB);

        addFormField(formCard, "Full Name:", nameTF);
        addFormField(formCard, "Contact Number:", contactTF);
        addFormField(formCard, "Membership Type:", membershipCB);
        addFormField(formCard, "Rate Type:", ratePanel);
        addFormField(formCard, "Duration (Months):", durationTF);
        addFormField(formCard, "Total Calculation (RM):", totalTF);

        // BOTTOM: ACTION BUTTONS
        JPanel actionPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        actionPanel.setOpaque(false);

        updateBtn = createCustomButton("Update Member", successGreen);
        deleteBtn = createCustomButton("Delete Member", dangerRed);
        clearBtn = createCustomButton("Clear Form", secondaryGray);

        actionPanel.add(updateBtn);
        actionPanel.add(deleteBtn);
        actionPanel.add(clearBtn);

        content.add(searchCard, BorderLayout.NORTH);
        content.add(formCard, BorderLayout.CENTER);
        content.add(actionPanel, BorderLayout.SOUTH);

        // Initial State
        setFormEnabled(false);

        // NAVIGATION EVENTS
        navRegBtn.addActionListener(e -> { new RegisterMemberUI().setVisible(true); this.dispose(); });
        navSearchBtn.addActionListener(e -> { new SearchMemberUI().setVisible(true); this.dispose(); });
        navDisplayBtn.addActionListener(e -> { new DisplayMembersUI().setVisible(true); this.dispose(); });

        // LOGIC EVENTS
        searchBtn.addActionListener(e -> searchMember());
        updateBtn.addActionListener(e -> updateMember());
        deleteBtn.addActionListener(e -> deleteMember());
        clearBtn.addActionListener(e -> clearForm());
    }

    // ===== UI HELPERS (STYLED) =====
    private JButton createNavButton(String text) {
        JButton b = new JButton(text);
        b.setForeground(Color.WHITE);
        b.setBackground(navGray);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { b.setBackground(primaryBlue); }
            public void mouseExited(java.awt.event.MouseEvent evt) { b.setBackground(navGray); }
        });
        return b;
    }

    private JPanel createStyledCard(String title) {
        JPanel p = new JPanel();
        p.setBackground(cardColor);
        p.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(226, 232, 240), 1, true),
            BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15),
                title, TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Segoe UI", Font.BOLD, 14), primaryBlue
            )
        ));
        return p;
    }

    private void addFormField(JPanel panel, String labelStr, JComponent field) {
        JLabel label = new JLabel(labelStr);
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
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return tf;
    }

    private JButton createCustomButton(String text, Color color) {
        JButton b = new JButton(text);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return b;
    }

    // ===== DATABASE LOGIC (RETAINED) =====
    private void searchMember() {
        ArrayList<Member> list = db.searchMember(searchTF.getText());
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Member not found.");
            clearForm();
            return;
        }
        currentMember = list.get(0);
        nameTF.setText(currentMember.getMembername());
        contactTF.setText(currentMember.getContactNum());
        membershipCB.setSelectedItem(currentMember.getMembership());
        durationTF.setText(String.valueOf(currentMember.getDuration()));
        totalTF.setText(String.format("%.2f", currentMember.getTotal()));
        if (currentMember.getRate().equalsIgnoreCase("Standard"))
            standardRB.setSelected(true);
        else
            studentRB.setSelected(true);
        setFormEnabled(true);
    }

    private void updateMember() {
        try {
            int duration = Integer.parseInt(durationTF.getText());
            double total = getRate() * duration;
            currentMember.setMembername(nameTF.getText());
            currentMember.setContactNum(contactTF.getText());
            currentMember.setMembership(membershipCB.getSelectedItem().toString());
            currentMember.setRate(standardRB.isSelected() ? "Standard" : "Student");
            currentMember.setDuration(duration);
            currentMember.setTotal(total);
            totalTF.setText(String.format("%.2f", total));
            if (db.updateMember(currentMember))
                JOptionPane.showMessageDialog(this, "Member updated!");
            else
                JOptionPane.showMessageDialog(this, "Update failed.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void deleteMember() {
        if (currentMember == null) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Delete this member?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        if (db.deleteMember(currentMember.getMembername())) {
            JOptionPane.showMessageDialog(this, "Member deleted.");
            clearForm();
            currentMember = null;
        }
    }

    private double getRate() {
        boolean student = studentRB.isSelected();
        return switch (membershipCB.getSelectedItem().toString()) {
            case "Basic" -> student ? 80 : 120;
            case "Premium" -> student ? 150 : 200;
            case "VIP" -> student ? 280 : 350;
            default -> 0;
        };
    }

    private void clearForm() {
        searchTF.setText("");
        nameTF.setText("");
        contactTF.setText("");
        durationTF.setText("");
        totalTF.setText("");
        standardRB.setSelected(false);
        studentRB.setSelected(false);
        membershipCB.setSelectedIndex(0);
        setFormEnabled(false);
    }

    private void setFormEnabled(boolean enabled) {
        nameTF.setEnabled(enabled);
        contactTF.setEnabled(enabled);
        durationTF.setEnabled(enabled);
        membershipCB.setEnabled(enabled);
        standardRB.setEnabled(enabled);
        studentRB.setEnabled(enabled);
        updateBtn.setEnabled(enabled);
        deleteBtn.setEnabled(enabled);
        Color bg = enabled ? Color.WHITE : readOnlyBg;
        nameTF.setBackground(bg);
        contactTF.setBackground(bg);
        durationTF.setBackground(bg);
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new EditDeleteMemberUI().setVisible(true));
    }
}