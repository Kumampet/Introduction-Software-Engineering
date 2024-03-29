package se1ex12.src;
/*
 * University of Aizu [Introduction to Software Engineering] Exercise material
 *
 * Class: RecordProcurementPanel
 *
 * Date:2019/05/30
 *
 * author:s1250050 Tsuyoshi Kumamoto
 *
 */

/*
 * Implement the following methods: recordProcurement show
 */
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class RecordProcurementPanel implements ActionListener {
  // Controls for values
  private JTextField procurementId;
  private JTextField staffCode;
  private JLabel staffNameLabel;
  private JTextField materialCode;
  private JLabel materialNameLabel;
  private JTextField amount;
  private JTextField requestedDate;
  private JLabel processStatusMessageLabel;
  // Event related controls
  private JButton recordProcurementButton;
  private JButton exitButton;
  // Labels for error messages
  private JLabel procurementIdError;
  private JLabel staffCodeError;
  private JLabel materialCodeError;
  private JLabel amountError;
  private JLabel requestedDateError;
  // Panel and frame for display
  private JPanel pnlCenter;
  private JFrame frame;

  public RecordProcurementPanel() {
    pnlCenter = new JPanel();

    procurementId = new JTextField(5);
    procurementIdError = new JLabel("");

    staffCode = new JTextField(4);
    staffCodeError = new JLabel("");
    staffNameLabel = new JLabel("");

    materialCode = new JTextField(4);
    materialCodeError = new JLabel("");
    materialNameLabel = new JLabel("");

    amount = new JTextField(3);
    amountError = new JLabel("");

    requestedDate = new JTextField(MyCalendar.formatDate(new Date()));
    requestedDateError = new JLabel("");

    recordProcurementButton = new JButton("Record Procurement");
    recordProcurementButton.setActionCommand("Record Procurement");
    recordProcurementButton.addActionListener(this);
    exitButton = new JButton("Exit");
    processStatusMessageLabel = new JLabel("");
    exitButton.addActionListener(this);

    // Layout
    pnlCenter.setLayout(new GridLayout(10, 3, 10, 10));
    pnlCenter.add(new JLabel("Procurement Id"));
    pnlCenter.add(procurementId);
    pnlCenter.add(procurementIdError);

    pnlCenter.add(new JLabel("Staff Code"));
    pnlCenter.add(staffCode);
    pnlCenter.add(staffCodeError);
    pnlCenter.add(new JLabel("Staff Name"));
    pnlCenter.add(staffNameLabel);
    pnlCenter.add(new JLabel("     "));

    pnlCenter.add(new JLabel("Material Code"));
    pnlCenter.add(materialCode);
    pnlCenter.add(materialCodeError);
    pnlCenter.add(new JLabel("Material Name"));
    pnlCenter.add(materialNameLabel);
    pnlCenter.add(new JLabel("     "));

    pnlCenter.add(new JLabel("Amount(Kg)"));
    pnlCenter.add(amount);
    pnlCenter.add(amountError);

    pnlCenter.add(new JLabel("Requested Date"));
    pnlCenter.add(requestedDate);
    pnlCenter.add(requestedDateError);

    pnlCenter.add(new JLabel("Register"));
    pnlCenter.add(recordProcurementButton);
    pnlCenter.add(exitButton);

    pnlCenter.add(new JLabel("Process Status Message"));
    pnlCenter.add(new JLabel("              "));
    pnlCenter.add(new JLabel("              "));

    frame = new JFrame("Record Procurement Panel");
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(pnlCenter, "Center");
    frame.getContentPane().add(processStatusMessageLabel, "South");
    frame.setSize(600, 400);
    frame.setVisible(true);
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Record Procurement")) {
      // Check for missing input
      int errCnt = 0;

      procurementIdError.setText("");
      staffCodeError.setText("");
      materialCodeError.setText("");
      amountError.setText("");
      requestedDateError.setText("");
      processStatusMessageLabel.setText("");

      if ("".equals(this.procurementId.getText())) {
        procurementIdError.setText("Please input");
        errCnt = errCnt + 1;
      }
      if ("".equals(this.staffCode.getText())) {
        staffCodeError.setText("Please input");
        errCnt = errCnt + 1;
      }
      if ("".equals(this.materialCode.getText())) {
        materialCodeError.setText("Please input");
        errCnt = errCnt + 1;
      }
      if ("".equals(this.amount.getText())) {
        amountError.setText("Please input");
        errCnt = errCnt + 1;
      } else {
        try {
          Integer.parseInt(this.amount.getText());
        } catch (NumberFormatException ne) {
          amountError.setText("Please input integer value");
          errCnt = errCnt + 1;

        }
      }
      if ("".equals(this.requestedDate.getText())) {
        requestedDateError.setText("Please input");
        errCnt = errCnt + 1;
      }
      if (errCnt != 0) {
        return;
      }
      // Finish check for missing input
      try {
        recordProcurement(procurementId.getText(), staffCode.getText(), materialCode.getText(),
            Integer.parseInt(amount.getText()), MyCalendar.getDate(requestedDate.getText()));
      } catch (ClassNotFoundException ex) {
        Logger.getLogger(RecordProcurementPanel.class.getName()).log(Level.SEVERE, null, ex);
      } catch (SQLException ex) {
        Logger.getLogger(RecordProcurementPanel.class.getName()).log(Level.SEVERE, null, ex);
      }
    } else {
      // Exit button clicked
      frame.dispose();
    }
  }

  // Implement the following method "recordProcurement"
  private void recordProcurement(String procurementId, String staffCode, String materialCode, Integer amount, Date requestedDate) throws ClassNotFoundException, SQLException {
    // Define necessary variables
    ProcurementControl procurementControl = new ProcurementControl();

    boolean status = false;
    String processStatusMessage=null;
    String staffName = null;
    String materialName = null;

    // Main process
    Staff staff = procurementControl.getStaff(staffCode);
    Material material = procurementControl.getMate(materialCode);

    if(staff == null){
      processStatusMessage = "The code is incorrect!";
    }else{
      staffName = staff.getName();
    }

    if(material == null){
      processStatusMessage = "The code is incorrect!";
    }else{
      materialName = material.getName();
    }

    // Finally, call method "show"
    show(staffName, materialName, processStatusMessage);
  }


  // Implement the following method "show"
  private void show(String staffName, String materialName, String processStatusMessage) {

    // Check if staffName is empty, if not set it to staffNameLabel, if empty set error message
    if(staffName == null){
      staffNameLabel.setText("");
      staffCodeError.setText("Staff code is incorrect!");
    }else{
      staffNameLabel.setText(staffName);
      staffCodeError.setText("");
    }


    // Check if materialName is empty, if not, set it to materialNameLabel, if empty set error
    if(materialName == null){
      materialNameLabel.setText(staffName);
      materialCodeError.setText("Material Code is incorrect!");
      // Set the processStatusMessage
      if(processStatusMessage != null){
        processStatusMessageLabel.setText(processStatusMessage);
      }else{
        processStatusMessageLabel.setText("Correct!");
      }
    }else{
      materialNameLabel.setText(materialName);
      materialCodeError.setText("");
    }
    // message



    // Set the processStatusMessage

  }
}
