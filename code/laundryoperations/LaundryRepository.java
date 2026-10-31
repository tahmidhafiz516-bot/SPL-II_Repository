package laundryoperations;
import usersaccess.Database;
import java.sql.*;
import java.util.*;
public class LaundryRepository {
 public String createOrder(String customer,LaundryOrder.DeliveryPreference pref)throws SQLException{
  String id=UUID.randomUUID().toString();
  try(Connection c=Database.connect();
      PreparedStatement p=c.prepareStatement("INSERT INTO laundry_orders(order_id,customer_id,delivery_preference,order_status) VALUES(?,?,?,'PLACED')")){
   p.setString(1,id);
   p.setString(2,customer);
   p.setString(3,pref.name());
   p.executeUpdate();
  }
  return id;
 }

 public boolean belongsTo(String order,String customer)throws SQLException{
  try(Connection c=Database.connect();
      PreparedStatement p=c.prepareStatement("SELECT 1 FROM laundry_orders WHERE order_id=? AND customer_id=?")){
   p.setString(1,order);
   p.setString(2,customer);
   try(ResultSet r=p.executeQuery()){
    return r.next();
   }
  }
 }

 public String addGarment(String order,String owner,String type,String color,Garment.ServiceType service)throws SQLException{
  String id=UUID.randomUUID().toString();
  try(Connection c=Database.connect()){
   c.setAutoCommit(false);
   try(PreparedStatement p=c.prepareStatement("INSERT INTO garments(garment_id,order_id,owner_id,item_type,color,service_type,current_status) VALUES(?,?,?,?,?,?,'RECEIVED')")){
    p.setString(1,id);
    p.setString(2,order);
    p.setString(3,owner);
    p.setString(4,type);
    p.setString(5,color);
    p.setString(6,service.name());
    p.executeUpdate();
   }
   try(PreparedStatement p=c.prepareStatement("UPDATE laundry_orders SET total_quantity=(SELECT COUNT(*) FROM garments WHERE order_id=?) WHERE order_id=?")){
    p.setString(1,order);
    p.setString(2,order);
    p.executeUpdate();
   }
   c.commit();
  }
  return id;
 }

 public List<String> orders(String customer)throws SQLException{
  List<String> a=new ArrayList<>();
  try(Connection c=Database.connect();
      PreparedStatement p=c.prepareStatement("SELECT order_id,total_quantity,delivery_preference,order_status,order_date FROM laundry_orders WHERE customer_id=? ORDER BY order_date DESC")){
   p.setString(1,customer);
   try(ResultSet r=p.executeQuery()){
    while(r.next())a.add(r.getString(1)+" | items="+r.getInt(2)+" | "+r.getString(3)+" | "+r.getString(4)+" | "+r.getTimestamp(5));
   }
  }
  return a;
 }

 public String complaint(String customer,String order,String type,String description)throws SQLException{
  String id=UUID.randomUUID().toString();
  try(Connection c=Database.connect();
      PreparedStatement p=c.prepareStatement("INSERT INTO complaints(complaint_id,customer_id,order_id,complaint_type,description) VALUES(?,?,?,?,?)")){
   p.setString(1,id);
   p.setString(2,customer);
   p.setString(3,order);
   p.setString(4,type);
   p.setString(5,description);
   p.executeUpdate();
  }
  return id;
 }

 public String rewash(String customer,String order,List<String> garments,String reason)throws SQLException{
  String id=UUID.randomUUID().toString();
  try(Connection c=Database.connect();
      PreparedStatement p=c.prepareStatement("INSERT INTO rewash_requests(rewash_id,original_order_id,customer_id,garment_ids,reason) VALUES(?,?,?,?,?)")){
   p.setString(1,id);
   p.setString(2,order);
   p.setString(3,customer);
   p.setString(4,String.join(",",garments));
   p.setString(5,reason);
   p.executeUpdate();
  }
  return id;
 }

 public void updateStatus(String order,LaundryOrder.OrderStatus status)throws SQLException{
  try(Connection c=Database.connect();
      PreparedStatement p=c.prepareStatement("UPDATE laundry_orders SET order_status=?,completion_date=CASE WHEN ?='COMPLETED' THEN CURRENT_TIMESTAMP ELSE completion_date END WHERE order_id=?")){
   p.setString(1,status.name());
   p.setString(2,status.name());
   p.setString(3,order);
   if(p.executeUpdate()==0)throw new SQLException("Order not found.");
  }
 }

 public void createDelivery(String order,String recipient,String address)throws SQLException{
  try(Connection c=Database.connect();
      PreparedStatement p=c.prepareStatement("INSERT INTO deliveries(delivery_id,order_id,recipient_id,delivery_address) VALUES(?,?,?,?)")){
   p.setString(1,UUID.randomUUID().toString());
   p.setString(2,order);
   p.setString(3,recipient);
   p.setString(4,address);
   p.executeUpdate();
  }
 }

 public void verifyDelivery(String order,String recipient)throws SQLException{
  try(Connection c=Database.connect()){
   c.setAutoCommit(false);
   try(PreparedStatement p=c.prepareStatement("UPDATE deliveries SET is_verified=TRUE,delivery_time=CURRENT_TIMESTAMP WHERE order_id=? AND recipient_id=?")){
    p.setString(1,order);
    p.setString(2,recipient);
    if(p.executeUpdate()==0)throw new SQLException("Delivery not found or recipient mismatch.");
   }
   try(PreparedStatement p=c.prepareStatement("UPDATE laundry_orders SET order_status='DELIVERED' WHERE order_id=?")){
    p.setString(1,order);
    p.executeUpdate();
   }
   try(PreparedStatement p=c.prepareStatement("UPDATE garments SET current_status='DELIVERED' WHERE order_id=?")){
    p.setString(1,order);
    p.executeUpdate();
   }
   c.commit();
  }
 }
}
