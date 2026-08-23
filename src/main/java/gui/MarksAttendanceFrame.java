package gui;

import database.StudentDBOperations;
import model.AcademicRecord;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class MarksAttendanceFrame extends JFrame {
    private final StudentDBOperations db=new StudentDBOperations(); private final JTextField roll=new JTextField(10),subject=new JTextField(12),internal=new JTextField(5),external=new JTextField(5),attended=new JTextField(5),total=new JTextField(5); private final JLabel summary=new JLabel(" ");
    private final DefaultTableModel model=new DefaultTableModel(new String[]{"Subject","Internal","External","Total","Grade","Attendance"},0){public boolean isCellEditable(int r,int c){return false;}};
    public MarksAttendanceFrame(){super("Marks and attendance");setSize(760,460);setLocationRelativeTo(null);setLayout(new BorderLayout());JPanel p=new JPanel(new FlowLayout(FlowLayout.LEFT));field(p,"Roll",roll);field(p,"Subject",subject);field(p,"Internal / 30",internal);field(p,"External / 70",external);field(p,"Attended",attended);field(p,"Total lectures",total);JButton save=new JButton("Save record");JButton load=new JButton("Load student records");p.add(save);p.add(load);add(p,BorderLayout.NORTH);add(new JScrollPane(new JTable(model)),BorderLayout.CENTER);add(summary,BorderLayout.SOUTH);save.addActionListener(e->save());load.addActionListener(e->load());}
    private void field(JPanel p,String label,JTextField input){p.add(new JLabel(label+":"));p.add(input);}
    private void save(){try{AcademicRecord a=new AcademicRecord(roll.getText().trim(),subject.getText().trim(),Double.parseDouble(internal.getText()),Double.parseDouble(external.getText()),Integer.parseInt(attended.getText()),Integer.parseInt(total.getText()));if(a.rollNumber().isBlank()||a.subject().isBlank()||a.internalMarks()<0||a.externalMarks()<0||a.totalLectures()<0||a.attendedLectures()<0||a.attendedLectures()>a.totalLectures())throw new IllegalArgumentException("Enter valid marks and lecture counts.");db.saveAcademic(a);load();}catch(Exception e){JOptionPane.showMessageDialog(this,e.getMessage(),"Cannot save record",JOptionPane.ERROR_MESSAGE);}}
    private void load(){try{model.setRowCount(0);double marks=0;int n=0;for(AcademicRecord a:db.academicRecords(roll.getText().trim())){model.addRow(new Object[]{a.subject(),a.internalMarks(),a.externalMarks(),a.totalMarks(),a.grade(),String.format("%.1f%%",a.attendancePercentage())});marks+=a.totalMarks();n++;}summary.setText(n==0?"No records found.":String.format("Average marks: %.2f%%",marks/n));}catch(SQLException e){JOptionPane.showMessageDialog(this,e.getMessage(),"Database error",JOptionPane.ERROR_MESSAGE);}}
}
