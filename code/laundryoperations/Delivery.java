package laundryoperations;
import java.time.LocalDateTime;
public class Delivery {
 private final String deliveryID,orderID,recipientID,address;
 private boolean verified;
 private LocalDateTime deliveryTime;

 public Delivery(String id,String order,String recipient,String address,boolean verified,LocalDateTime time){
  deliveryID=id;
  orderID=order;
  recipientID=recipient;
  this.address=address;
  this.verified=verified;
  deliveryTime=time;
 }

 public String getDeliveryID(){
  return deliveryID;
 }
 public String getOrderID(){
  return orderID;
 }
 public String getRecipientID(){
  return recipientID;
 }
 public String getAddress(){
  return address;
 }
 public boolean isVerified(){
  return verified;
 }
 public LocalDateTime getDeliveryTime(){
  return deliveryTime;
 }

 public boolean verifyRecipient(String id){
  verified=recipientID.equals(id);
  return verified;
 }
 public void markAsDelivered(){
  if(!verified)throw new IllegalStateException("Verify recipient first.");
  deliveryTime=LocalDateTime.now();
 }
}
