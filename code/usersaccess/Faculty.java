package usersaccess;
public class Faculty extends User {
 private final String facultyID,department,officeRoomNumber;

 public Faculty(String id,String n,String e,boolean v,String fid,String dep,String office){
  super(id,n,e,UserRole.FACULTY,v);
  facultyID=fid;
  department=dep;
  officeRoomNumber=office;
 }
 public String getFacultyID(){
  return facultyID;
 }
 public String getDepartment(){
  return department;
 }
 public String getOfficeRoomNumber(){
  return officeRoomNumber;
 }
}
