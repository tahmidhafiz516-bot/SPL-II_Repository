package usersaccess;
import java.sql.*;
import java.util.*;
public class UserRepository {
 public boolean managerExists()throws SQLException{
  try(Connection c=Database.connect();
      Statement s=c.createStatement();
      ResultSet r=s.executeQuery("SELECT COUNT(*) FROM users WHERE role='MANAGER'")){
   r.next();
   return r.getInt(1)>0;
  }
 }

 public void create(String id,String name,String email,String hash,UserRole role,String sid,String room,Boolean res,String fid,String dep,String office,String staff,String shift,boolean verified)throws SQLException{
  String q="INSERT INTO users(user_id,name,email,password_hash,role,is_verified,student_id,room_number,is_residential,faculty_id,department,office_room_number,staff_id,shift_name) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
  try(Connection c=Database.connect();
      PreparedStatement p=c.prepareStatement(q)){
   p.setString(1,id);
   p.setString(2,name);
   p.setString(3,email);
   p.setString(4,hash);
   p.setString(5,role.name());
   p.setBoolean(6,verified);
   p.setString(7,sid);
   p.setString(8,room);
   if(res==null)p.setNull(9,Types.BOOLEAN);
   else p.setBoolean(9,res);
   p.setString(10,fid);
   p.setString(11,dep);
   p.setString(12,office);
   p.setString(13,staff);
   p.setString(14,shift);
   p.executeUpdate();
  }
 }

 public Map<String,Object> byEmail(String email)throws SQLException{
  try(Connection c=Database.connect();
      PreparedStatement p=c.prepareStatement("SELECT * FROM users WHERE email=?")){
   p.setString(1,email);
   try(ResultSet r=p.executeQuery()){
    if(!r.next())return null;
    Map<String,Object> m=new HashMap<>();
    for(String k:new String[]{"user_id","name","email","password_hash","role","is_verified","student_id","room_number","is_residential","faculty_id","department","office_room_number","staff_id","shift_name"})m.put(k,r.getObject(k));
    return m;
   }
  }
 }

 public List<String> pending()throws SQLException{
  List<String>a=new ArrayList<>();
  try(Connection c=Database.connect();
      Statement s=c.createStatement();
      ResultSet r=s.executeQuery("SELECT user_id,name,email,role FROM users WHERE is_verified=FALSE")){
   while(r.next())a.add(r.getString(1)+" | "+r.getString(2)+" | "+r.getString(3)+" | "+r.getString(4));
  }
  return a;
 }

 public void approve(String id)throws SQLException{
  try(Connection c=Database.connect();
      PreparedStatement p=c.prepareStatement("UPDATE users SET is_verified=TRUE WHERE user_id=?")){
   p.setString(1,id);
   if(p.executeUpdate()==0)throw new SQLException("User not found.");
  }
 }
}
