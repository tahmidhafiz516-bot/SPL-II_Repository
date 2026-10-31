package usersaccess;
import java.sql.*;
public final class Database {
 private Database(){}
 private static String env(String k,String d){
  String v=System.getenv(k);
  return v==null?d:v;
 }

 public static Connection connect() throws SQLException {
  try {
   Class.forName("com.mysql.cj.jdbc.Driver");
  } catch (ClassNotFoundException e) {
   throw new SQLException(
           "MySQL JDBC driver is missing from the runtime classpath.", e
   );
  }
  return DriverManager.getConnection(env("IUT_DB_URL","jdbc:mysql://localhost:3306/iut_laundry?useSSL=false&serverTimezone=UTC"),env("IUT_DB_USER","root"),env("IUT_DB_PASSWORD",""));
 }

 public static void initialize() throws SQLException {
  String[] ddl={
   "CREATE TABLE IF NOT EXISTS users(user_id VARCHAR(64) PRIMARY KEY,name VARCHAR(120) NOT NULL,email VARCHAR(190) NOT NULL UNIQUE,password_hash VARCHAR(255) NOT NULL,role VARCHAR(20) NOT NULL,is_verified BOOLEAN NOT NULL DEFAULT FALSE,student_id VARCHAR(64),room_number VARCHAR(64),is_residential BOOLEAN,faculty_id VARCHAR(64),department VARCHAR(120),office_room_number VARCHAR(64),staff_id VARCHAR(64),shift_name VARCHAR(64))",
   "CREATE TABLE IF NOT EXISTS laundry_orders(order_id VARCHAR(64) PRIMARY KEY,customer_id VARCHAR(64) NOT NULL,total_quantity INT NOT NULL DEFAULT 0,delivery_preference VARCHAR(30) NOT NULL,order_status VARCHAR(30) NOT NULL,order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,completion_date TIMESTAMP NULL,FOREIGN KEY(customer_id) REFERENCES users(user_id))",
   "CREATE TABLE IF NOT EXISTS garments(garment_id VARCHAR(64) PRIMARY KEY,order_id VARCHAR(64) NOT NULL,owner_id VARCHAR(64) NOT NULL,item_type VARCHAR(100) NOT NULL,color VARCHAR(60) NOT NULL,service_type VARCHAR(30) NOT NULL,current_status VARCHAR(30) NOT NULL,FOREIGN KEY(order_id) REFERENCES laundry_orders(order_id) ON DELETE CASCADE,FOREIGN KEY(owner_id) REFERENCES users(user_id))",
   "CREATE TABLE IF NOT EXISTS complaints(complaint_id VARCHAR(64) PRIMARY KEY,customer_id VARCHAR(64) NOT NULL,order_id VARCHAR(64) NOT NULL,complaint_type VARCHAR(100) NOT NULL,description TEXT NOT NULL,status VARCHAR(30) NOT NULL DEFAULT 'OPEN',resolution_notes TEXT,date_submitted TIMESTAMP DEFAULT CURRENT_TIMESTAMP,FOREIGN KEY(customer_id) REFERENCES users(user_id),FOREIGN KEY(order_id) REFERENCES laundry_orders(order_id))",
   "CREATE TABLE IF NOT EXISTS rewash_requests(rewash_id VARCHAR(64) PRIMARY KEY,original_order_id VARCHAR(64) NOT NULL,customer_id VARCHAR(64) NOT NULL,garment_ids TEXT NOT NULL,reason TEXT NOT NULL,rewash_status VARCHAR(30) NOT NULL DEFAULT 'REQUESTED',created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,FOREIGN KEY(original_order_id) REFERENCES laundry_orders(order_id),FOREIGN KEY(customer_id) REFERENCES users(user_id))",
   "CREATE TABLE IF NOT EXISTS deliveries(delivery_id VARCHAR(64) PRIMARY KEY,order_id VARCHAR(64) NOT NULL UNIQUE,recipient_id VARCHAR(64) NOT NULL,delivery_address VARCHAR(255) NOT NULL,is_verified BOOLEAN NOT NULL DEFAULT FALSE,delivery_time TIMESTAMP NULL,FOREIGN KEY(order_id) REFERENCES laundry_orders(order_id),FOREIGN KEY(recipient_id) REFERENCES users(user_id))",
   "CREATE TABLE IF NOT EXISTS notifications(notification_id VARCHAR(64) PRIMARY KEY,recipient_id VARCHAR(64) NOT NULL,message TEXT NOT NULL,created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,is_read BOOLEAN NOT NULL DEFAULT FALSE,FOREIGN KEY(recipient_id) REFERENCES users(user_id))"
  };
  try(Connection c=connect();
      Statement s=c.createStatement()){for(String q:ddl)s.execute(q);
  }
 }
}
