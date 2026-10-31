package laundryoperations;
import java.util.List;
public class RewashRequest {
 public enum Status {
  REQUESTED,
  APPROVED,
  IN_PROGRESS,
  COMPLETED
 }
 private final String rewashID,orderID,customerID,reason;
 private final List<String> garmentIDs;
 private Status status;

 public RewashRequest(String id,String order,String customer,List<String> garments,String reason,Status status){
  rewashID=id;
  orderID=order;
  customerID=customer;
  garmentIDs=List.copyOf(garments);
  this.reason=reason;
  this.status=status;
 }

 public String getRewashID(){
  return rewashID;
 }
 public String getOrderID(){
  return orderID;
 }
 public String getCustomerID(){
  return customerID;
 }
 public List<String> getGarmentIDs(){
  return garmentIDs;
 }
 public String getReason(){
  return reason;
 }
 public Status getStatus(){
  return status;
 }
 public void updateStatus(Status s){
  status=s;
 }
}
