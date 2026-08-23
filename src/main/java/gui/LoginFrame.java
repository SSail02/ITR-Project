package gui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        super("College Student Management - Login"); setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(380, 220); setLocationRelativeTo(null);
        JPanel panel = new JPanel(new GridBagLayout()); GridBagConstraints g = new GridBagConstraints(); g.insets=new Insets(6,6,6,6); g.fill=GridBagConstraints.HORIZONTAL;
        JTextField username=new JTextField(18); JPasswordField password=new JPasswordField(18); JButton login=new JButton("Sign in");
        add(panel); addRow(panel,g,0,"Username",username); addRow(panel,g,1,"Password",password); g.gridx=1;g.gridy=2;panel.add(login,g);
        login.addActionListener(e -> { if (username.getText().trim().equals("admin") && String.valueOf(password.getPassword()).equals("admin123")) { new DashboardFrame().setVisible(true); dispose(); } else JOptionPane.showMessageDialog(this,"Invalid username or password.","Login failed",JOptionPane.ERROR_MESSAGE); });
        getRootPane().setDefaultButton(login);
    }
    private void addRow(JPanel p, GridBagConstraints g, int row, String label, JComponent component) { g.gridx=0;g.gridy=row;p.add(new JLabel(label + ":"),g);g.gridx=1;p.add(component,g); }
}
