import java.sql.*;
import java.util.Scanner;

public class BusReservation {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String url = "jdbc:oracle:thin:@localhost:1521/FREEPDB1";
        String user = "system";
        String password = "your_password";

        try {
            Connection con =
                DriverManager.getConnection(url, user, password);

            System.out.println("1. View Available Seats");
            System.out.println("2. Reserve a Seat");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            if (choice == 1) 
                {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(
                    "SELECT * FROM BUS_SEATS WHERE STATUS='AVAILABLE'");

                while (rs.next()) {
                    System.out.println("Seat: " + rs.getInt("SEAT_NO"));
                }
                rs.close();
                st.close();
            }
            else if (choice == 2) {
                System.out.print("Enter seat number: ");
                int seat = sc.nextInt();

                PreparedStatement ps = con.prepareStatement(
                    "UPDATE BUS_SEATS SET STATUS='BOOKED' " +
                    "WHERE SEAT_NO=? AND STATUS='AVAILABLE'");

                ps.setInt(1, seat);

                int rows = ps.executeUpdate();

                if (rows > 0)
                    System.out.println("Seat reserved successfully!");
                else
                    System.out.println("Seat unavailable or invalid!");

                ps.close();
            }
            else {
                System.out.println("Invalid choice!");
            }

            con.close();
            sc.close();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}