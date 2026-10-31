package usersaccess;
public class Student extends User {
 private final String studentID,roomNumber;
 private final boolean residential;

 public Student(String id,String n,String e,boolean v,String sid,String room,boolean res){
  super(id,n,e,UserRole.STUDENT,v);
  studentID=sid;
  roomNumber=room;
  residential=res;
 }

 public String getStudentID(){
  return studentID;
 }
 public String getRoomNumber(){
  return roomNumber;
 }
 public boolean isResidential(){
  return residential;
 }
}
