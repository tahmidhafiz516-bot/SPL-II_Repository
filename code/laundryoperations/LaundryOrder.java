package laundryoperations;
import java.time.LocalDateTime;
import java.util.*;
public class LaundryOrder {
 public enum DeliveryPreference {
  ROOM_DELIVERY,
  SELF_PICKUP
 }
 public enum OrderStatus {
  PLACED,
  IN_PROGRESS,
  COMPLETED,
  DELIVERED
 }
 private final String orderID,customerID;
 private final DeliveryPreference preference;
 private OrderStatus status;
 private final LocalDateTime orderDate;
 private final List<Garment> garments=new ArrayList<>();

 public LaundryOrder(String id,String customer,DeliveryPreference pref,OrderStatus status,LocalDateTime date){
  orderID=id;
  customerID=customer;
  preference=pref;
  this.status=status;
  orderDate=date;
 }

 public String getOrderID(){
  return orderID;
 }
 public String getCustomerID(){
  return customerID;
 }
 public DeliveryPreference getDeliveryPreference(){
  return preference;
 }
 public OrderStatus getStatus(){
  return status;
 }
 public void updateOrderStatus(OrderStatus s){
  status=s;
 }
 public LocalDateTime getOrderDate(){
  return orderDate;
 }
 public void addGarment(Garment g){
  garments.add(g);
 }
 public List<Garment> getGarments(){
  return List.copyOf(garments);
 }
 public int calculateTotalItems(){
  return garments.size();
 }
}
