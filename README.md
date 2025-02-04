# Banking Management System - JDBC

This is a **Banking Management System** built using **Java Database Connectivity (JDBC)**. It allows users to perform basic banking operations such as account creation, deposits, withdrawals, balance inquiries, and fund transfers. The system uses **MySQL** as the backend database.

---

## **Features**
1. **Account Management**
   - Create a new bank account.
   - Check account balance.

2. **Transaction Management**
   - Deposit money into an account.
   - Withdraw money from an account.
   - Transfer funds between accounts.

3. **Database Integration**
   - Uses JDBC to connect to a MySQL database.
   - Stores customer details, account information, and transaction history.

---

## **Technologies Used**
- **Java** (Core Java, JDBC)
- **MySQL** (Database)
- **MySQL Connector/J** (JDBC Driver for MySQL)

---

## **Prerequisites**
Before running the project, ensure you have the following installed:
1. **Java Development Kit (JDK)** (version 8 or higher)
2. **MySQL Server**
3. **MySQL Connector/J** (JDBC driver for MySQL)
4. **IDE** (e.g., IntelliJ IDEA, Eclipse, or any other)

---

## **Setup Instructions**

### **1. Database Setup**
1. Create a MySQL database.
2. Create the required tables by running the SQL scripts.
3. Create the database connection class.

### **2. Add MySQL JDBC Driver**
1. Download the MySQL Connector/J driver from the [official website](https://dev.mysql.com/downloads/connector/j/).
2. Add the JAR file to your project's classpath.

### **3. Run the Application**
1. Compile and run.
2. Use the menu to perform banking operations.

---

## **Project Structure**
```
banking-management-system-jdbc/
│
├── src/
│   ├── DatabaseConnection.java       // Handles database connection
│   ├── AccountManager.java           // Manages account creation and balance inquiry
│   ├── TransactionManager.java       // Handles deposits, withdrawals, and transfers
│   └── BankingSystem.java            // Main class with the menu-driven interface
│
├── lib/                              // Add MySQL Connector/J JAR here
│
└── README.md                         // Project documentation
```
## **Contributing**
Contributions are welcome! If you find any issues or want to add new features, feel free to open a pull request.
---

## **Author**
- [Saqib Rashid Bhat]
- GitHub:https://github.com/Saqibbhat321
- Email: saqib70241@gmail.com

---

## **Acknowledgments**
- Inspired by real-world banking systems.
- Built as a learning project for JDBC and database integration in Java.

---
