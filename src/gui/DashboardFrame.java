package gui;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    public DashboardFrame() {
        super("College Student Management System"); setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(560,300); setLocationRelativeTo(null);
        JPanel panel=new JPanel(new GridLayout(2,2,15,15)); panel.setBorder(BorderFactory.createEmptyBorder(35,35,35,35)); add(panel);
        addButton(panel,"Register student",()->new RegistrationFrame(null).setVisible(true));
        addButton(panel,"Manage students",()->new ManageStudentsFrame().setVisible(true));
        addButton(panel,"Marks & attendance",()->new MarksAttendanceFrame().setVisible(true));
        addButton(panel,"Sign out",()->{new LoginFrame().setVisible(true);dispose();});
    }
    private void addButton(JPanel p,String label,Runnable action){JButton b=new JButton(label);b.addActionListener(e->action.run());p.add(b);}
}
