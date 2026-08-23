package main;

import database.DBDriver;
import gui.LoginFrame;
import javax.swing.*;

/** Application entry point. */
public final class Main {
    private Main() { }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                DBDriver.initialize();
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new LoginFrame().setVisible(true);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "Application could not start: " + exception.getMessage(),
                        "Startup error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
