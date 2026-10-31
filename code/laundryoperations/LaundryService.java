package laundryoperations;
import usersaccess.User;
import java.sql.SQLException;
import java.util.List;
public class LaundryService {
 private final LaundryRepository repo;
 public LaundryService(LaundryRepository r){
  repo=r;
 }

 public String createOrder(User u,LaundryOrder.DeliveryPreference p)throws SQLException{
  return repo.createOrder(u.getUserID(),p);
 }
 public String addGarment(User u,String order,String type,String color,Garment.ServiceType service)throws SQLException{
  if(!repo.belongsTo(order,u.getUserID()))throw new IllegalArgumentException("Order does not belong to your account.");
  return repo.addGarment(order,u.getUserID(),type,color,service);
 }

 public List<String> listOrders(User u)throws SQLException{
  return repo.orders(u.getUserID());
 }

 public String submitComplaint(User u,String order,String type,String desc)throws SQLException{
  if(!repo.belongsTo(order,u.getUserID()))throw new IllegalArgumentException("Order not found for this user.");
  return repo.complaint(u.getUserID(),order,type,desc);
 }

 public String submitRewash(User u,String order,List<String> ids,String reason)throws SQLException{
  if(!repo.belongsTo(order,u.getUserID()))throw new IllegalArgumentException("Order not found for this user.");
  if(ids.isEmpty())throw new IllegalArgumentException("Enter garment IDs.");
  return repo.rewash(u.getUserID(),order,ids,reason);
 }

 public void updateStatus(String order,LaundryOrder.OrderStatus s)throws SQLException{repo.updateStatus(order,s);
 }
 public void createDelivery(String order,String recipient,String address)throws SQLException{
  repo.createDelivery(order,recipient,address);
 }
 public void verifyDelivery(String order,String recipient)throws SQLException{
  repo.verifyDelivery(order,recipient);
 }
}
