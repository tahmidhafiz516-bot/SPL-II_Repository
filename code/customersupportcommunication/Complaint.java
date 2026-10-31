package customersupportcommunication;
import java.time.LocalDateTime;
public class Complaint {
 public enum Status {
  OPEN,
  IN_REVIEW,
  RESOLVED
 }

 private final String complaintID,customerID,orderID,type,description;
 private Status status;
 private final LocalDateTime dateSubmitted;
 private String resolutionNotes;

 public Complaint(String id,String customer,String order,String type,String description,Status status,LocalDateTime date){
  complaintID=id;
  customerID=customer;
  orderID=order;
  this.type=type;
  this.description=description;
  this.status=status;
  dateSubmitted=date;
 }

 public String getComplaintID(){
  return complaintID;
 }
 public String getCustomerID(){
  return customerID;
 }
 public String getOrderID(){
  return orderID;
 }
 public String getType(){
  return type;
 }
 public String getDescription(){
  return description;
 }
 public Status getStatus(){
  return status;
 }
 public LocalDateTime getDateSubmitted(){
  return dateSubmitted;
 }
 public void updateStatus(Status s){
  status=s;
 }
 public void addResolutionNotes(String n){
  resolutionNotes=n;
 }
 public String getResolutionNotes(){
  return resolutionNotes;
 }
}
