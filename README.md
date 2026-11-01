# SPL-II_Repository
# IUT Laundry Management System

A Java-based laundry management application developed for the Islamic University of Technology (IUT). The system is designed to manage customer accounts, laundry orders, garment records, complaints, rewash requests, deliveries, and notifications.

The application uses Java, MySQL, JDBC, and Maven.

---

## 1. Features

The system provides the following functionality:

### User Management and Access
- Student and faculty account registration.
- User authentication.
- Manager approval of customer accounts.
- Role-based user management.
- Password hashing using salted PBKDF2.

### Laundry Operations
- Creation and management of laundry orders.
- Garment record management.
- Laundry order status updates.
- Delivery management and verification.
- Rewash request management.

### Customer Support and Communication
- Complaint submission and management.
- Notification record management.

---

## 2. Technologies Used

| Technology | Purpose |
|---|---|
| Java 17 | Main programming language |
| MySQL | Relational database |
| JDBC | Database connectivity |
| MySQL Connector/J 9.4.0 | MySQL JDBC driver |
| Maven | Build and dependency management |
| IntelliJ IDEA | Recommended development environment |

---

## 3. Project Structure

The project follows a modular Maven directory structure.

```text
IUT-Laundry-Management-System/
│
├── pom.xml
├── README.md
│
├── database/
│   └── schema.sql
│
└── src/
    │
    ├── usersaccess/
    │   ├── Database.java
    │   ├── UserRole.java
    │   ├── PasswordUtil.java
    │   ├── User.java
    │   ├── Student.java
    │   ├── Faculty.java
    │   ├── LaundryManager.java
    │   ├── LaundryStaff.java
    │   ├── UserRepository.java
    │   └── UserService.java
    │
    ├── laundryoperations/
    │   ├── Garment.java
    │   ├── LaundryOrder.java
    │   ├── Delivery.java
    │   ├── RewashRequest.java
    │   ├── LaundryRepository.java
    │   └── LaundryService.java
    │
    └── customersupportcommunication/
        ├── Complaint.java
        ├── Notification.java
        ├── ComplaintRepository.java
        ├── NotificationService.java
        └── Main.java
```

### Package Responsibilities

- **usersaccess:** Handles user accounts, authentication, roles, and database connection management.
- **laundryoperations:** Handles laundry orders, garments, deliveries, and rewash requests.
- **customersupportcommunication:** Handles complaints, notifications, and the main application entry point.

---

## 4. Prerequisites

Before building and running the project, install the following:

1. **JDK 17:** The project is configured for Java 17.
2. **IntelliJ IDEA:** Recommended IDE for development.
3. **MySQL Server:** Required for persistent database storage.
4. **MySQL Workbench:** Recommended for database setup and management.
5. **Apache Maven:** Required for building and running the project through Maven.

Make sure that the Java and MySQL installations are working correctly.

Check the Java installation using:

```bash
java -version
```

Check the Maven installation using:

```bash
mvn -version
```

If Maven is not recognized in PowerShell, configure its installation directory in the Windows PATH environment variable or use IntelliJ IDEA's built-in Maven tool window.

---

## 5. Database Setup

The application uses a MySQL database named:

```text
iut_laundry
```

### Step 1: Start MySQL Server

Make sure the MySQL server is running.

On Windows:

1. Press `Win + R`.
2. Enter `services.msc`.
3. Locate the installed MySQL service.
4. Start the service if it is not already running.

The service name may differ depending on the MySQL installation.

### Step 2: Create the Database and Tables

The database schema is provided in:

```text
database/schema.sql
```

Using MySQL Workbench:

1. Open MySQL Workbench.
2. Connect to your local MySQL server.
3. Open `database/schema.sql`.
4. Execute the SQL script.
5. Verify that the `iut_laundry` database and its tables have been created.

Alternatively, execute the script from a terminal:

```bash
mysql -u root -p < database/schema.sql
```

Enter your MySQL root password when prompted.

The schema contains the following tables:

- `users`
- `laundry_orders`
- `garments`
- `complaints`
- `rewash_requests`
- `deliveries`
- `notifications`

---

## 6. Environment Configuration

The application reads database connection information from environment variables.

The following variables are supported:

| Variable | Description | Example |
|---|---|---|
| `IUT_DB_URL` | MySQL database connection URL | `jdbc:mysql://localhost:3306/iut_laundry?useSSL=false&serverTimezone=UTC` |
| `IUT_DB_USER` | MySQL username | `root` |
| `IUT_DB_PASSWORD` | MySQL password | Your configured MySQL password |

### Windows PowerShell

Set the variables for the current terminal session:

```powershell
$env:IUT_DB_URL="jdbc:mysql://localhost:3306/iut_laundry?useSSL=false&serverTimezone=UTC"

$env:IUT_DB_USER="root"

$env:IUT_DB_PASSWORD="your_mysql_password"
```

Replace `your_mysql_password` with the actual password configured during MySQL installation.

These commands only configure the variables for the current PowerShell session.

### IntelliJ IDEA

To configure the variables in IntelliJ:

1. Open the project.
2. Select **Run → Edit Configurations**.
3. Select the Application configuration for `Main`.
4. Locate **Environment variables**.
5. Add the following entries:

```text
IUT_DB_URL=jdbc:mysql://localhost:3306/iut_laundry?useSSL=false&serverTimezone=UTC

IUT_DB_USER=root

IUT_DB_PASSWORD=your_mysql_password
```

6. Click **Apply** and then **OK**.

Do not commit actual database passwords to the Git repository.

---

## 7. Building the Project

### Option A: Using IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Select **File → Open**.
3. Select the project root directory containing `pom.xml`.
4. Allow IntelliJ to import the Maven project.
5. Set the Project SDK to Java 17.
6. Open the Maven tool window.
7. Expand the project.
8. Under **Lifecycle**, execute:

```text
clean
compile
```

The compilation process should generate the compiled classes inside:

```text
target/classes/
```

### Option B: Using the Terminal

Open a terminal in the project root directory and execute:

```bash
mvn clean compile
```

If compilation succeeds, Maven will report:

```text
BUILD SUCCESS
```

---

## 8. Running the Application

Before running the application, make sure that:

- MySQL Server is running.
- The `iut_laundry` database exists.
- The database credentials are configured correctly.
- The project has compiled successfully.
- MySQL Connector/J is available through Maven.

### Option A: Using IntelliJ IDEA

1. Navigate to:

```text
src/main/java/customersupportcommunication/Main.java
```

2. Open `Main.java`.
3. Verify that its package declaration is:

```java
package customersupportcommunication;
```

4. Verify that the class contains the application entry point:

```java
public static void main(String[] args)
```

5. Click the green Run button beside the `main` method.

If IntelliJ cannot recognize the main class:

- Right-click `src/main/java`.
- Select **Mark Directory as → Sources Root**.
- Recheck the package declaration.
- Rebuild the project.
- Check the Application run configuration.

The fully qualified main class name is:

```text
customersupportcommunication.Main
```

### Option B: Using Maven

From the project root directory, execute:

```bash
mvn exec:java
```

This uses the main-class configuration specified in `pom.xml`.

The application should display its console menu when startup is successful.

---

## 9. Testing

Testing should be performed at both the individual component level and the integrated application level.

### 9.1 Compilation Test

Execute:

```bash
mvn clean compile
```

This verifies that Maven can compile the project's source code and resolve its declared dependencies.

### 9.2 Database Connectivity Test

Start MySQL and configure the environment variables.

Then run the application.

A successful startup indicates that the application has progressed through its database initialization process.

If the application reports a database connection error, verify:

- MySQL Server is running.
- The database URL is correct.
- The database exists.
- The username and password are correct.
- MySQL Connector/J is available on the runtime classpath.

### 9.3 Functional Testing

The following test cases are recommended for checking the application's functionality.

| Test ID | Test Scenario | Expected Result |
|---|---|---|
| TC-01 | Register a student account | Registration information is recorded |
| TC-02 | Register a faculty account | Registration information is recorded |
| TC-03 | Log in using valid credentials | User authentication succeeds |
| TC-04 | Log in using invalid credentials | Authentication is rejected |
| TC-05 | Approve a customer account as manager | Account approval is recorded |
| TC-06 | Create a laundry order | Order information is stored |
| TC-07 | Add garments to an order | Garment information is recorded |
| TC-08 | Update an order status | Updated status is stored |
| TC-09 | Submit a complaint | Complaint information is recorded |
| TC-10 | Submit a rewash request | Rewash request is recorded |
| TC-11 | Create a delivery record | Delivery information is stored |
| TC-12 | Verify a delivery | Delivery verification is recorded |

These are recommended test cases, not a claim that every test has already passed.

### 9.4 Integration Testing

Integration testing should verify that the different modules work together.

Recommended workflows include:

**Workflow 1: Customer Registration**

```text
Customer Registration
        |
        v
User Service
        |
        v
User Repository
        |
        v
MySQL Database
```

**Workflow 2: Laundry Order Submission**

```text
Customer Login
        |
        v
Laundry Order Creation
        |
        v
Laundry Service
        |
        v
Laundry Repository
        |
        v
MySQL Database
```

**Workflow 3: Complaint Submission**

```text
Customer
   |
   v
Complaint Submission
   |
   v
Complaint Repository
   |
   v
MySQL Database
```

### 9.5 Automated Testing

If automated unit tests are added under:

```text
src/test/java/
```

they can be executed using:

```bash
mvn test
```

The current project documentation does not establish a verified automated test suite or test-coverage percentage. These should be reported only after the corresponding tests have been created and executed.

---

## 10. Troubleshooting

### Problem 1: `mvn` Is Not Recognized

**Error:**

```text
mvn : The term 'mvn' is not recognized
```

**Solution:**

- Install Apache Maven.
- Add Maven's `bin` directory to the Windows PATH variable.
- Open a new terminal.
- Alternatively, use IntelliJ's built-in Maven tool window.

### Problem 2: `ClassNotFoundException: customersupportcommunication.Main`

**Possible causes:**

- Incorrect package declaration.
- Incorrect source directory.
- Incorrect Maven main-class configuration.
- Compilation failure.

**Solution:**

Verify that:

```text
src/main/java/customersupportcommunication/Main.java
```

contains:

```java
package customersupportcommunication;
```

Then execute:

```bash
mvn clean compile
```

Check whether the following compiled file exists:

```text
target/classes/customersupportcommunication/Main.class
```

### Problem 3: `No suitable driver found for jdbc:mysql`

**Possible causes:**

- MySQL Connector/J is missing from the runtime classpath.
- IntelliJ is using an incorrect module configuration.
- Maven dependencies have not been reloaded.

**Solution:**

1. Open `pom.xml`.
2. Verify that the MySQL Connector/J dependency is declared.
3. Reload the Maven project.
4. Check the Application run configuration's module classpath.
5. Rebuild the project.

The dependency should include:

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>9.4.0</version>
</dependency>
```

### Problem 4: MySQL Communications Link Failure

**Possible causes:**

- MySQL Server is stopped.
- The database port is incorrect.
- The server is not listening on the expected address.

**Solution:**

- Start the MySQL service.
- Verify the configured port.
- Confirm that the connection URL uses the correct host and port.

### Problem 5: Access Denied for MySQL User

**Possible causes:**

- Incorrect username.
- Incorrect password.
- Insufficient database privileges.

**Solution:**

Verify `IUT_DB_USER` and `IUT_DB_PASSWORD` and confirm that the configured MySQL account has the necessary privileges.

### Problem 6: Unknown Database `iut_laundry`

**Cause:**

The database has not been created.

**Solution:**

Execute:

```text
database/schema.sql
```

using MySQL Workbench or the MySQL command-line client.

---

## 11. Important Notes

- A running MySQL Server is required for normal application operation.
- The database schema must be initialized before using the application.
- Java 17 is the intended project configuration.
- Database credentials should be configured through environment variables.
- The application currently uses a console-based interface.
- Notifications are stored in the database; external email and SMS delivery are not included.
- A successful compilation does not by itself prove that all application workflows work correctly.
- Complete integration and functional testing should be performed before production deployment.

---

## 12. License and Academic Use

This project was developed as part of the Software Project Lab II coursework at the Islamic University of Technology.

Third-party libraries remain subject to their respective licenses.

The project should be used and distributed in accordance with applicable institutional requirements and third-party licensing terms.
