package laundryoperations;
public class Garment {
 public enum ServiceType {
  WASH_AND_IRON,
  IRON_ONLY
 }
 public enum GarmentStatus {
  RECEIVED,
  WASHING,
  DRYING,
  IRONING,
  READY_FOR_DELIVERY,
  DELIVERED
 }
 private final String garmentID,orderID,ownerID,itemType,color;
 private final ServiceType serviceType;
 private GarmentStatus status;

 public Garment(String id,String oid,String owner,String type,String color,ServiceType service,GarmentStatus status){
  garmentID=id;
  orderID=oid;
  ownerID=owner;
  itemType=type;
  this.color=color;
  serviceType=service;
  this.status=status;
 }

 public String getGarmentID(){
  return garmentID;
 }
 public String getOrderID(){
  return orderID;
 }
 public String getOwnerID(){
  return ownerID;
 }
 public String getItemType(){
  return itemType;
 }
 public String getColor(){
  return color;
 }
 public ServiceType getServiceType(){
  return serviceType;
 }
 public GarmentStatus getStatus(){
  return status;
 }
 public void updateStatus(GarmentStatus s){
  status=s;
 }

 public String getGarmentDetails(){
  return garmentID+" | "+itemType+" | "+color+" | "+serviceType+" | "+status;
 }
}
