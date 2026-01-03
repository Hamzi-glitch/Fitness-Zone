/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guigpProgramming;

/**
 *
 * @author user pc
 */

import javax.swing.*;

public class MainApp {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> {
            // Choose the starting UI HERE
            new RegisterMemberUI().setVisible(true);
        });
    }
}

