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
    public ProcurementStaff getStaff(String staffCode){
	ProcurementStaff staff = new ProcurementStaff(staffCode, dbAccess);
	boolean b_staff = staff.checkStaff();
	if(b_staff){
	    return staff;
	}else return null;
    }
    public Material getMaterial(String materialCode){
	Material material = new Material(materialCode, dbAccess);
	boolean b_material = material.checkMaterial();
	if(b_material){
	    return material;
	}else return null;
    }
}
