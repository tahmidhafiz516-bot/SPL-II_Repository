package customersupportcommunication;
import laundryoperations.*;
import usersaccess.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
public class Main {
 private static final Scanner in=new Scanner(System.in);
 private static final UserRepository users=new UserRepository();
 private static final UserService userService=new UserService(users);
 private static final LaundryRepository orders=new LaundryRepository();
 private static final LaundryService laundry=new LaundryService(orders);
 private static final NotificationService notices=new NotificationService();

 public static void main(String[] args){
  try{Database.initialize();System.out.println("=== IUT Laundry Management System ===");
   boolean run=true;
   while(run){
    System.out.println("\n1 Register\n2 Login\n0 Exit");
    try{
     switch(ask("Select: "))
     {
      case "1"->register();
      case "2"->login();
      case "0"->run=false;
      default->System.out.println("Invalid choice.");
     }
    } catch (Exception e){
     System.out.println("Error: "+e.getMessage());
    }
   }
  } catch (SQLException e){
   System.err.println("MySQL connection failed. Check MySQL and IUT_DB_URL/IUT_DB_USER/IUT_DB_PASSWORD. "+e.getMessage());
  }
 }

 private static void register()throws SQLException{
  System.out.println("1 Student  2 Faculty  3 Manager  4 Staff");
  UserRole role=switch(ask("Role: "))
  {
   case "1"->UserRole.STUDENT;
   case "2"->UserRole.FACULTY;
   case "3"->UserRole.MANAGER;
   case "4"->UserRole.STAFF;
   default->throw new IllegalArgumentException("Invalid role.");
  };
  String name=ask("Name: "),email=ask("Email: "),password=ask("Password (8+ characters): ");
  String sid=null,room=null,fid=null,dep=null,office=null,staff=null,shift=null;
  Boolean res=null;
  if(role==UserRole.STUDENT){
   sid=ask("Student ID: ");
   room=ask("Room number: ");
   res=ask("Residential? (y/n): ").equalsIgnoreCase("y");
  }
  else if(role==UserRole.FACULTY||role==UserRole.MANAGER){
   fid=ask("Faculty ID: ");
   dep=ask("Department: ");
   office=ask("Office room: ");
  }
  else{
   staff=ask("Staff ID: ");
   shift=ask("Shift: ");
  }
  String id=userService.register(name,email,password,role,sid,room,res,fid,dep,office,staff,shift);
  System.out.println("Account ID: "+id+(role==UserRole.MANAGER&&!users.managerExists()?"":""));
  System.out.println("Account submitted. First manager is auto-approved; other accounts await manager approval.");
 }

 private static void login()throws SQLException{
  User u=userService.login(ask("Email: "),ask("Password: "));
  System.out.println("Welcome "+u.getName()+" ("+u.getRole()+")");
  boolean active=true;
  while(active){
   System.out.println("\n1 Create order\n2 Add garment\n3 List my orders\n4 Submit complaint\n5 Submit rewash request\n6 Manager: list pending accounts\n7 Manager: approve account\n8 Staff/Manager: update order status\n9 Staff/Manager: create delivery\n10 Verify delivery\n0 Logout");
   try{
    switch(ask("Select: ")){
     case "1"->createOrder(u);
     case "2"->addGarment(u);
     case "3"->{
      List<String> list=laundry.listOrders(u);
      if(list.isEmpty())System.out.println("No orders.");
      else list.forEach(System.out::println);
     }
     case "4"->complaint(u);
     case "5"->rewash(u);
     case "6"->{
      manager(u);List<String> p=users.pending();
      if(p.isEmpty())System.out.println("No pending accounts.");
      else p.forEach(System.out::println);
     }
     case "7"->{
      manager(u);
      String id=ask("User ID to approve: ");
      users.approve(id);
      notices.sendRegistrationApproval(id);
      System.out.println("Approved.");
     }
     case "8"->{
      staffOrManager(u);
      String id=ask("Order ID: ");
      LaundryOrder.OrderStatus s=switch(ask("1 PLACED, 2 IN_PROGRESS, 3 COMPLETED, 4 DELIVERED: ")){
       case "1"->LaundryOrder.OrderStatus.PLACED;
       case "2"->LaundryOrder.OrderStatus.IN_PROGRESS;
       case "3"->LaundryOrder.OrderStatus.COMPLETED;
       case "4"->LaundryOrder.OrderStatus.DELIVERED;
       default->throw new IllegalArgumentException("Invalid status.");
      };
      laundry.updateStatus(id,s);
      System.out.println("Status updated.");
     }
     case "9"->{
      staffOrManager(u);
      laundry.createDelivery(ask("Order ID: "),ask("Recipient user ID: "),ask("Delivery address: "));
      System.out.println("Delivery record created.");
     }
     case "10"->{
      String order=ask("Order ID: "),recipient=ask("Recipient user ID: ");
      if(!recipient.equals(u.getUserID())&&u.getRole()!=UserRole.STAFF&&u.getRole()!=UserRole.MANAGER)throw new IllegalArgumentException("Only recipient/staff/manager may verify.");
      laundry.verifyDelivery(order,recipient);
      System.out.println("Delivery verified.");
     }
     case "0"->active=false;
     default->System.out.println("Invalid choice.");
    }
   } catch (Exception e){
    System.out.println("Error: "+e.getMessage());
   }
  }
 }

 private static void createOrder(User u)throws SQLException{
  LaundryOrder.DeliveryPreference p=ask("1 Room delivery, 2 Self pickup: ").equals("1")?LaundryOrder.DeliveryPreference.ROOM_DELIVERY:LaundryOrder.DeliveryPreference.SELF_PICKUP;
  String id=laundry.createOrder(u,p);
  System.out.println("Created order: "+id);
  if(ask("Add garment now? (y/n): ").equalsIgnoreCase("y")) addGarmentTo(u,id);
 }

 private static void addGarment(User u)throws SQLException{
  addGarmentTo(u,ask("Order ID: "));
 }

 private static void addGarmentTo(User u,String id)throws SQLException{
  String type=ask("Item type: "),color=ask("Color: ");
  Garment.ServiceType s=ask("1 Wash and iron, 2 Iron only: ").equals("2")?Garment.ServiceType.IRON_ONLY:Garment.ServiceType.WASH_AND_IRON;
  System.out.println("Garment ID: "+laundry.addGarment(u,id,type,color,s));
 }

 private static void complaint(User u)throws SQLException{
  String id=laundry.submitComplaint(u,ask("Order ID: "),ask("Complaint type: "),ask("Description: "));
  notices.sendStatusUpdate(u.getUserID(),"Complaint submitted: "+id);
  System.out.println("Complaint ID: "+id);
 }

 private static void rewash(User u)throws SQLException{
  String order=ask("Order ID: ");
  List<String> ids=Arrays.stream(ask("Garment IDs separated by commas: ").split(",")).map(String::trim).filter(x->!x.isEmpty()).collect(Collectors.toList());
  String id=laundry.submitRewash(u,order,ids,ask("Reason: "));
  notices.sendStatusUpdate(u.getUserID(),"Rewash request submitted: "+id);
  System.out.println("Rewash request ID: "+id);
 }

 private static void manager(User u){
  if(u.getRole()!=UserRole.MANAGER)throw new IllegalArgumentException("Manager role required.");
 }

 private static void staffOrManager(User u){
  if(u.getRole()!=UserRole.STAFF&&u.getRole()!=UserRole.MANAGER)throw new IllegalArgumentException("Staff or manager role required.");
 }

 private static String ask(String prompt){
  System.out.print(prompt);
  return in.nextLine().trim();
 }
}
