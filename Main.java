package org.example;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

class TicketBookingSystem
{
    private static String movie;
    private static String show_Time;
    private static int seat;
    static int count=100;
    private static String user;
    public TicketBookingSystem(String movie,String show_Time,int seat,String user)
    {
        TicketBookingSystem.movie =movie;
        TicketBookingSystem.show_Time =show_Time;
        TicketBookingSystem.seat =seat;
        TicketBookingSystem.user =user;
    }
    public static String getmovie()
    {
        return movie;
    }
    public static String getshow()
    {
        return show_Time;
    }
    public int getseat()
    {
        return seat;
    }
    public String getuser()
    {
        return user;
    }
    public void setmovie(String movie)
    {
        TicketBookingSystem.movie =movie;
    }
    public void setshow(String show_Time)
    {
        TicketBookingSystem.show_Time =show_Time;
    }
    public void setseat(int seat)
    {
        TicketBookingSystem.seat =seat;
    }
    public void setuser(String user)
    {
        TicketBookingSystem.user =user;
    }
    public void showmovies()
    {
        String[] movies=new String[]{"Titanic", "Avengers:InfinityWar", "Ironman", "DDLJ", "Kaithi", "Salaar", "KGF", "KGF2", "Saaho", "Ramayan", "RRR", "Kalki2898AD", "Bahubali:The Begining","Bahubali2:The Conclusion","Animal"};
        System.out.println("Available Movies");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (String s : movies) {
            System.out.println(s);
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
    public void bookTicket()
    {
        Scanner sc=new Scanner(System.in);
        String[] movies={"Titanic", "Avengers:InfinityWar", "Ironman", "DDLJ", "Kaithi", "Salaar", "KGF", "KGF2", "Saaho", "Ramayan", "RRR", "Kalki2898AD", "Bahubali:The Begining","Bahubali2:The Conclusion","Animal"};
        if(seat<=count)
        {
            for (String s : movies) {
                if (s.equalsIgnoreCase(getmovie())) {
                    System.out.println("Generating ticket for movie" + " " + getmovie() + " " + "to" + " " + getuser());
                    count = count - seat;
                    System.out.println("Enter Seat Numbers(comma seperated ,e.g,A1,A2):)");
                    String seatNumbers=sc.next();
                    paymentMethod();
                    System.out.println("Ticket Generated Successfully "+seatNumbers);
                    Database.saveBooking(getmovie(),getshow(),getseat(),getuser(),seatNumbers);
                    saveBooking(getmovie(),getshow(),getseat(),getuser(),seatNumbers);
                    System.out.println("Movie"+" "+getmovie()+" "+"showTme"+" "+getshow()+" "+"Booked Seats"+" "+getseat()+" "+"for"+" "+getuser()+" "+"seatNumbers"+" "+seatNumbers);
                    return;
                }
            }
            System.out.println(getmovie()+" "+"movie"+" "+"Not Available");
        }
        else
        {
            System.out.println("Sorry "+" "+getuser()+" "+"Ticket Not Available");
        }
    }
    public void paymentMethod()
    {
        int ch;
        Scanner sc=new Scanner(System.in);
        System.out.println("Select Your Payment Method");
        System.out.println("1.CASH");
        System.out.println("2.UPI");
        System.out.println("3.Credit Card/Debit Card");
        ch=sc.nextInt();
        switch(ch)
        {
            case 1:
                System.out.println("Payment Successful Via CASH");
                break;
            case 2:
                System.out.println("Payment Successful Via UPI");
                break;
            case 3:
                System.out.println("Payment Successful Via Credit Card/Debit Card");
                break;
        }
    }
    public void saveBooking(String movie,String show_Time,int seat,String user_name,String seat_numbers)
    {
        try
        {
            FileWriter writer=new FileWriter("bookings.txt",true);
            writer.write("Movie "+movie+" "+"showTime "+show_Time+" "+"seats "+seat+" "+"User "+user_name+" "+"Seat Numbers "+seat_numbers);
            writer.close();
            System.out.println("Booking Details Saved to File");
        }
        catch(Exception e)
        {
            System.out.println("Booking details not saved");
        }
    }
    public void viewBookings()
    {
        try
        {
            File file=new File("bookings.txt");
            Scanner sc=new Scanner(file);
            System.out.println("Booking History");
            while(sc.hasNextLine())
            {
                System.out.println(sc.nextLine());
            }
            sc.close();
        }
        catch(Exception e)
        {
            System.out.println("Sorry File Not Found");
        }
    }
    static class Database
    {
        private static final String url="jdbc:postgresql://localhost:5432/postgres";
        private static  final String user="postgres";
        private static final String pass="system";
        //private static Connection con;

        public static void saveBooking(String movie,String show_Time,int seat,String user_name,String seat_numbers)
        {
            try
            {
                Class.forName("org.postgresql.Driver");
                Connection con= DriverManager.getConnection(url,user,pass);
                String sql="insert into Booking(movie,show_Time,seat,user_name,seat_numbers) values(?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, movie);
                ps.setString(2,show_Time);
                ps.setInt(3,seat);
                ps.setString(4,user_name);
                ps.setString(5,seat_numbers);
                ps.executeUpdate();
                System.out.println("----------------------------------------------------------------------------------------------------");
                System.out.println("Booking Saved");
                System.out.println("----------------------------------------------------------------------------------------------------");
                ps.close();
                con.close();
            }
            catch(Exception e)
            {
                //noinspection ThrowablePrintedToSystemOut
                System.out.println(e);
            }
        }
        public static void deleteBooking(String movie, String user_name)
        {
            try
            {
                Class.forName("org.postgresql.Driver"); // Optional if using JDBC 4+, but safe
                Connection con = DriverManager.getConnection(url, user, pass);
                String sql = "DELETE FROM Booking WHERE movie = ? AND user_name = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, movie);
                ps.setString(2, user_name);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0)
                {
                    System.out.println("Booking Canceled Successfully.");
                }
                else
                {
                    System.out.println("No booking found " + movie + " " + user_name);
                }
                ps.close();
                con.close();
            }
            catch (Exception e) {
                System.out.println("Booking Can Not Be Deleted");
            }
        }
    }
    public static void main(String[] a)
    {
        String movie=" ";
        String show_Time="";
        int seat=0;
        String user=" ";
        TicketBookingSystem t=new TicketBookingSystem(movie,show_Time,seat,user);
        Scanner sc=new Scanner(System.in);
        System.out.println("                                                                                       Hi ,Welcome To TicketsShow                                                                                                              ");
        while(true)
        {
            System.out.println("1.See Movies");
            System.out.println("2.Book Ticket");
            System.out.println("3.Cancel Booking");
            System.out.println("4.View Booking");
            System.out.println("5.Exit");
            int ch=sc.nextInt();
            switch(ch)
            {
                case 1:
                    t.showmovies();
                    break;
                case 2:
                    System.out.println("Enter movie  Name");
                    movie=sc.next();
                    System.out.println("Available show Timing For"+" "+movie+" "+"12:00 Am 3:00 Am 6:00 Am");
                    System.out.println("Enter show timing");
                    show_Time=sc.next();
                    System.out.println("Enter No of Seats to Book");
                    seat=sc.nextInt();
                    System.out.println("Enter Your Name Any One Of You");
                    user=sc.next();
                    t.setmovie(movie);
                    t.setshow(show_Time);
                    t.setseat(seat);
                    t.setuser(user);
                    t.bookTicket();
                    break;
                case 3:
                    try
                    {
                        System.out.println("Enter Movie Name");
                        movie=sc.next();
                        System.out.println("Enter User Name");
                        user=sc.next();
                    }
                    catch(Exception e)
                    {
                        System.out.println("Invalid Input Enter Information Correctly");
                    }
                    t.setmovie(movie);
                    t.setuser(user);
                    Database.deleteBooking(movie,user);
                    break;
                case 4:
                    t.viewBookings();
                    break;
                case 5:
                    System.out.println("Thank You To Visit  ");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}