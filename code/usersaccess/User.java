package usersaccess;
public abstract class User {
 private final String userID,name,email;
 private final UserRole role;
 private final boolean verified;

 protected User(String id,String name,String email,UserRole role,boolean verified){
  userID=id;
  this.name=name;
  this.email=email;
  this.role=role;
  this.verified=verified;
 }

 public String getUserID(){
  return userID;
 }
 public String getName(){
  return name;
 }
 public String getEmail(){
  return email;
 }
 public UserRole getRole(){
  return role;
 }
 public boolean isVerified(){
  return verified;
 }

 @Override public String toString(){
  return role+" | "+userID+" | "+name+" | "+email+" | verified="+verified;
 }
}
