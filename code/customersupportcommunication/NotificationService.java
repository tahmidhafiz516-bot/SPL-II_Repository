package customersupportcommunication;
import usersaccess.Database;
import java.sql.*;
import java.util.UUID;
public class NotificationService {
 public void send(String recipient,String message)throws SQLException{
  try(Connection c=Database.connect();
      PreparedStatement p=c.prepareStatement("INSERT INTO notifications(notification_id,recipient_id,message) VALUES(?,?,?)")){
   p.setString(1,UUID.randomUUID().toString());
   p.setString(2,recipient);
   p.setString(3,message);
   p.executeUpdate();
  }
 }

 public void sendRegistrationApproval(String id)throws SQLException{
  send(id,"Your IUT Laundry Management System account has been approved.");
 }

 public void sendRegistrationDenial(String id,String reason)throws SQLException{
  send(id,"Registration denied: "+reason);
 }

 public void sendStatusUpdate(String id,String message)throws SQLException{
  send(id,message);
 }
}
