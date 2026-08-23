package gui;

import database.StudentDBOperations;
import database.StudentDBOperations.Student;
import utils.Validator;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class RegistrationFrame extends JFrame {
    private final JTextField roll=new JTextField(), name=new JTextField(), branch=new JTextField(), semester=new JTextField(), contact=new JTextField(), email=new JTextField(), dob=new JTextField();
    private final Student original;
    public RegistrationFrame(Student existing) {
        super(existing == null ? "Student registration" : "Edit student"); original=existing; setSize(460,360);setLocationRelativeTo(null); JPanel p=new JPanel(new GridLayout(8,2,8,8));p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));add(p);
        add(p,"Roll number",roll);add(p,"Student name",name);add(p,"Branch / department",branch);add(p,"Semester",semester);add(p,"Contact number",contact);add(p,"Email",email);add(p,"Date of birth (yyyy-MM-dd)",dob); JButton save=new JButton("Save student");p.add(new JLabel());p.add(save);
        if(existing!=null){roll.setText(existing.rollNumber());roll.setEditable(false);name.setText(existing.name());branch.setText(existing.branch());semester.setText(String.valueOf(existing.semester()));contact.setText(existing.contact());email.setText(existing.email());dob.setText(existing.dateOfBirth());}
        save.addActionListener(e->save());
    }
    private void add(JPanel p,String label,JTextField field){p.add(new JLabel(label+":"));p.add(field);}
    private void save(){try { Student s=new Student(roll.getText().trim(),name.getText().trim(),branch.getText().trim(),Integer.parseInt(semester.getText().trim()),contact.getText().trim(),email.getText().trim(),dob.getText().trim());String issue=Validator.validateStudent(s);if(issue!=null){JOptionPane.showMessageDialog(this,issue);return;}new StudentDBOperations().saveStudent(s);JOptionPane.showMessageDialog(this,"Student saved.");dispose();}catch(NumberFormatException ex){JOptionPane.showMessageDialog(this,"Semester must be a number.");}catch(SQLException ex){JOptionPane.showMessageDialog(this,"Could not save student: "+ex.getMessage(),"Database error",JOptionPane.ERROR_MESSAGE);}}
}
