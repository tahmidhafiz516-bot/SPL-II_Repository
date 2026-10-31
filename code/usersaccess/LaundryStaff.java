package usersaccess;
public class LaundryStaff extends User {
 private final String staffID,shift;

 public LaundryStaff(String id,String n,String e,boolean v,String sid,String shift){
  super(id,n,e,UserRole.STAFF,v);
  staffID=sid;
  this.shift=shift;
 }
 public String getStaffID(){
  return staffID;
 }
 public String getShift(){
  return shift;
 }
}
