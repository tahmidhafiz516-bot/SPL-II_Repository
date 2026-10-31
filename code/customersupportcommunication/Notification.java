package customersupportcommunication;
import java.time.LocalDateTime;
public class Notification {
 private final String notificationID,recipientID,message;
 private final LocalDateTime timestamp;
 private boolean read;

 public Notification(String id,String recipient,String message,LocalDateTime time,boolean read){
  notificationID=id;
  recipientID=recipient;
  this.message=message;
  timestamp=time;
  this.read=read;
 }

 public String getNotificationID(){
  return notificationID;
 }
 public String getRecipientID(){
  return recipientID;
 }
 public String getMessage(){
  return message;
 }
 public LocalDateTime getTimestamp(){
  return timestamp;
 }
 public boolean isRead(){
  return read;
 }
 public void markRead(){
  read=true;
 }
}
