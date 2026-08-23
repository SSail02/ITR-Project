package gui;

import database.StudentDBOperations;
import model.Student;
import utils.CsvHelper;
import utils.Validator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class ManageStudentsFrame extends JFrame {
    private final StudentDBOperations db=new StudentDBOperations(); private final JTextField search=new JTextField(16); private final JComboBox<String> branches=new JComboBox<>(new String[]{"All"}); private final JComboBox<String> semesters=new JComboBox<>(new String[]{"All","1","2","3","4","5","6","7","8","9","10","11","12"});
    private final DefaultTableModel model=new DefaultTableModel(new String[]{"Roll number","Name","Branch","Semester","Contact","Email","Date of birth"},0){public boolean isCellEditable(int r,int c){return false;}}; private final JTable table=new JTable(model); private List<Student> current=List.of();
    public ManageStudentsFrame(){super("Manage students");setSize(1050,560);setLocationRelativeTo(null);setLayout(new BorderLayout(8,8)); JPanel filters=new JPanel();filters.add(new JLabel("Search:"));filters.add(search);filters.add(new JLabel("Branch:"));filters.add(branches);filters.add(new JLabel("Semester:"));filters.add(semesters);JButton go=new JButton("Search");filters.add(go);add(filters,BorderLayout.NORTH);add(new JScrollPane(table),BorderLayout.CENTER); JPanel actions=new JPanel();addAction(actions,"Refresh",this::refresh);addAction(actions,"Edit selected",this::edit);addAction(actions,"Delete selected",this::delete);addAction(actions,"Export CSV",this::export);addAction(actions,"Import CSV",this::importCsv);add(actions,BorderLayout.SOUTH);go.addActionListener(e->refresh());refresh();}
    private void addAction(JPanel p,String text,Runnable action){JButton b=new JButton(text);b.addActionListener(e->action.run());p.add(b);}
    private void refresh(){try{String selectedBranch=(String)branches.getSelectedItem();branches.removeAllItems();branches.addItem("All");for(String b:db.branches())branches.addItem(b);branches.setSelectedItem(selectedBranch==null?"All":selectedBranch);String branch=(String)branches.getSelectedItem();Integer semester="All".equals(semesters.getSelectedItem())?null:Integer.valueOf((String)semesters.getSelectedItem());current=db.findStudents(search.getText(),branch,semester);model.setRowCount(0);for(Student s:current)model.addRow(new Object[]{s.rollNumber(),s.name(),s.branch(),s.semester(),s.contact(),s.email(),s.dateOfBirth()});}catch(SQLException e){error(e);}}
    private Student selected(){int row=table.getSelectedRow();if(row<0){JOptionPane.showMessageDialog(this,"Select a student first.");return null;}return current.get(table.convertRowIndexToModel(row));}
    private void edit(){Student s=selected();if(s!=null)new RegistrationFrame(s).setVisible(true);}
    private void delete(){Student s=selected();if(s==null)return;if(JOptionPane.showConfirmDialog(this,"Delete "+s.name()+"?", "Confirm deletion",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)try{db.deleteStudent(s.rollNumber());refresh();}catch(SQLException e){error(e);}}
    private void export(){JFileChooser c=new JFileChooser();c.setSelectedFile(new File("students.csv"));if(c.showSaveDialog(this)==JFileChooser.APPROVE_OPTION)try{CsvHelper.exportStudents(c.getSelectedFile().toPath(),current);JOptionPane.showMessageDialog(this,"Export complete.");}catch(Exception e){error(e);}}
    private void importCsv(){JFileChooser c=new JFileChooser();if(c.showOpenDialog(this)!=JFileChooser.APPROVE_OPTION)return;try{int count=0;for(Student s:CsvHelper.importStudents(c.getSelectedFile().toPath())){String issue=Validator.validateStudent(s);if(issue!=null)throw new IllegalArgumentException(s.rollNumber()+": "+issue);db.saveStudent(s);count++;}refresh();JOptionPane.showMessageDialog(this,count+" student(s) imported.");}catch(Exception e){error(e);}}
    private void error(Exception e){JOptionPane.showMessageDialog(this,e.getMessage(),"Operation failed",JOptionPane.ERROR_MESSAGE);}
}
