/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guigpProgramming;


import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class DisplayMembersUI extends JFrame {

    // ===== DATABASE =====
    private DatabaseManager db = new DatabaseManager();

    // ===== UI COMPONENTS =====
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> sortCB;
    private JButton loadBtn, sortBtn;

    // NAVIGATION BUTTONS
    private JButton navRegBtn, navSearchBtn, navEditBtn;

    // DATA STORAGE
    private ArrayList<Member> members = new ArrayList<>();

    // ===== THEME =====
    private final Color bgColor = new Color(240, 242, 245);
    private final Color cardColor = Color.WHITE;
    private final Color primaryBlue = new Color(37, 99, 235);
    private final Color navGray = new Color(51, 65, 85);
    private final Color textColor = new Color(30, 41, 59);

    public DisplayMembersUI() {
        setTitle("Fitzone Management System - Member List");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainLayout = new JPanel(new BorderLayout());
        mainLayout.setBackground(bgColor);
        setContentPane(mainLayout);

        // ===== NAVIGATION =====
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        navPanel.setBackground(navGray);

        JLabel brandLabel = new JLabel("FITZONE");
        brandLabel.setForeground(Color.WHITE);
        brandLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        navPanel.add(brandLabel);

        navRegBtn = createNavButton("📝 Register");
        navSearchBtn = createNavButton("🔍 Search");
        navEditBtn = createNavButton("✏️ Edit/Delete");

        navPanel.add(navRegBtn);
        navPanel.add(navSearchBtn);
        navPanel.add(navEditBtn);

        mainLayout.add(navPanel, BorderLayout.NORTH);

        // ===== CONTENT =====
        JPanel contentArea = new JPanel(new BorderLayout(0, 20));
        contentArea.setOpaque(false);
        contentArea.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainLayout.add(contentArea, BorderLayout.CENTER);

        // TABLE CARD
        JPanel tableCard = createStyledCard("Member Directory");
        tableCard.setLayout(new BorderLayout());

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Member ID", "Name", "Contact", "Membership",
                "Rate Type", "Duration", "Total (RM)"
        });

        table = new JTable(model);
        styleTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tableCard.add(scrollPane, BorderLayout.CENTER);

        // CONTROL PANEL
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        controlPanel.setBackground(cardColor);

        sortCB = new JComboBox<>(new String[]{"Sort by Name", "Sort by Membership"});
        loadBtn = createCustomButton("🔄 Refresh Data", primaryBlue);
        sortBtn = createCustomButton("📊 Sort", new Color(100, 116, 139));

        controlPanel.add(new JLabel("Options: "));
        controlPanel.add(sortCB);
        controlPanel.add(sortBtn);
        controlPanel.add(loadBtn);

        contentArea.add(tableCard, BorderLayout.CENTER);
        contentArea.add(controlPanel, BorderLayout.SOUTH);

        // ===== NAVIGATION EVENTS =====
        navRegBtn.addActionListener(e -> { new RegisterMemberUI().setVisible(true); this.dispose(); });
        navSearchBtn.addActionListener(e -> { new SearchMemberUI().setVisible(true); this.dispose(); });
        navEditBtn.addActionListener(e -> { new EditDeleteMemberUI().setVisible(true); this.dispose(); });

        // ===== LOGIC EVENTS =====
        loadBtn.addActionListener(e -> loadMembers());
        sortBtn.addActionListener(e -> sortMembers());

        // Initial load
        loadMembers();
    }

    // ===== DATABASE LOGIC =====
    private void loadMembers() {
        members.clear();
        model.setRowCount(0);

        members = db.getAllMembers();
        displayTable();
    }

    private void displayTable() {
        model.setRowCount(0);

        for (Member m : members) {
            model.addRow(new Object[]{
                    m.getMemberid(),
                    m.getMembername(),
                    m.getContactNum(),
                    m.getMembership(),
                    m.getRate(),
                    m.getDuration(),
                    String.format("%.2f", m.getTotal())
            });
        }
    }

    private void sortMembers() {
        if (members.isEmpty()) return;

        if (sortCB.getSelectedIndex() == 0) {
            members.sort((a, b) ->
                    a.getMembername().compareToIgnoreCase(b.getMembername()));
        } else {
            members.sort(Comparator.comparing(Member::getMembership));
        }

        displayTable();
    }

    // ===== UI HELPERS =====
    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(Color.BLACK);
        table.setGridColor(new Color(241, 245, 249));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(248, 250, 252));
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setForeground(textColor);
    }

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
                        BorderFactory.createEmptyBorder(10, 10, 10, 10),
                        title,
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("Segoe UI", Font.BOLD, 15),
                        primaryBlue
                )
        ));
        return panel;
    }

    private JButton createCustomButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return btn;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> new DisplayMembersUI().setVisible(true));
    }
}
