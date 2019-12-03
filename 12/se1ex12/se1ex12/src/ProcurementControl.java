package se1ex12.src;

/*
 * University of Aizu [Introduction to Software Engineering] Exercise material
 *
 * Class: ProcurementControl
 *
 * Date: 2019/05/30
 *
 * author: s1250050 Tsuyoshi Kumamoto
 *
 */
/*
 *
 * Complete the implementation of this class "ProcurementControl"
 *
 */
import java.sql.SQLException;
import java.util.Date;

public class ProcurementControl {
  // Declare necessary variables
  private Procurement procurement;
  private DBAccess dbAccess;

  public ProcurementControl() throws ClassNotFoundException, SQLException {
    // Initialization of DBAccess
    dbAccess = new DBAccess();
  }

  // Declare and implement necessary methods
  public Staff getStaff(String staffCode){
    Staff staff = new Staff(staffCode);
    boolean b_staff = staff.checkStaff();
    if(b_staff){
      return staff;
    }else return null;
  }
  public Material getMatrial(String materialCode){
    Material material = new Material(materialCode);
    boolean b_material = material.checkMaterial();
    if(b_material){
      return material;
    }else return null;
  }
}
