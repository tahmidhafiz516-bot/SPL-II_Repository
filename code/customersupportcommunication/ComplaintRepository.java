package customersupportcommunication;
import usersaccess.Database;
import java.sql.*;
public class ComplaintRepository {
 public void updateStatus(String id,Complaint.Status status,String notes)throws SQLException{
  try(Connection c=Database.connect();
      PreparedStatement p=c.prepareStatement("UPDATE complaints SET status=?,resolution_notes=? WHERE complaint_id=?")){
   p.setString(1,status.name());
   p.setString(2,notes);p.setString(3,id);
   if(p.executeUpdate()==0)throw new SQLException("Complaint not found.");
  }
 }
}
