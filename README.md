🎟️ Movie Ticket Booking System

This is a Java console-based application for booking movie tickets. The system allows users to select movies, book seats, manage showtimes, and store booking details using a PostgreSQL database along with file handling for backup.


---

🧑‍💻 Technologies Used

Java (OOP principles, Multithreading, File Handling)

JDBC (Java Database Connectivity)

PostgreSQL

IntelliJ IDEA (or any Java IDE)

Features

🎬 Book tickets for selected movies

🪑 Assign seat numbers automatically

🕒 Manage show timings

👥 User-based booking tracking

💾 Store data in both database and file

🧹 Option to delete bookings from both database 

---

🗃️ Database Schema (PostgreSQL)

CREATE TABLE Booking (
    movie VARCHAR(50),
    show_time VARCHAR(10),
    seat INT,
    user_name VARCHAR(50),
    seat_numbers VARCHAR(20)
);

📁 Project Structure

MovieTicketBookingSystem/
│
├── src/
│   ├── org/example/
│   │   ├── TicketBookingSystem.java   
│   │   ├── DatabaseConnection.java  
│   │   └── Main.java                
│
├── bookings.txt                
├── README.md
└── pom.xml        
