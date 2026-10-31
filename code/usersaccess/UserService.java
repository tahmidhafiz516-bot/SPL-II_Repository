package usersaccess;
import java.sql.SQLException;
import java.util.*;
public class UserService {
 private final UserRepository repo;
 public UserService(UserRepository r){
  repo=r;
 }

 public String register(String name,String email,String password,UserRole role,String sid,String room,Boolean res,String fid,String dep,String office,String staff,String shift)throws SQLException{
  if(name.isBlank()||!email.contains("@"))throw new IllegalArgumentException("Enter a name and valid email.");
  boolean verified=role==UserRole.MANAGER&&!repo.managerExists();
  String id=UUID.randomUUID().toString();
  repo.create(id,name.trim(),email.trim().toLowerCase(),PasswordUtil.hash(password),role,sid,room,res,fid,dep,office,staff,shift,verified);
  return id;
 }

 public User login(String email,String password)throws SQLException{
  Map<String,Object> m=repo.byEmail(email.trim().toLowerCase());
  if(m==null||!PasswordUtil.verify(password,(String)m.get("password_hash")))throw new IllegalArgumentException("Invalid email or password.");
  if(!Boolean.TRUE.equals(m.get("is_verified")))throw new IllegalArgumentException("Account awaits manager approval.");
  String id=(String)m.get("user_id"),n=(String)m.get("name"),e=(String)m.get("email");
  UserRole role=UserRole.valueOf((String)m.get("role"));
  return switch(role){
   case STUDENT -> new Student(id,n,e,true,(String)m.get("student_id"),(String)m.get("room_number"),Boolean.TRUE.equals(m.get("is_residential")));
   case FACULTY -> new Faculty(id,n,e,true,(String)m.get("faculty_id"),(String)m.get("department"),(String)m.get("office_room_number"));
   case MANAGER -> new LaundryManager(id,n,e,true,(String)m.get("faculty_id"),(String)m.get("department"),(String)m.get("office_room_number"));
   case STAFF -> new LaundryStaff(id,n,e,true,(String)m.get("staff_id"),(String)m.get("shift_name"));
  };
 }
}
