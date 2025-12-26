/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fitzonefitnesssystem;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Aleph
 */
public class DatabaseManager {
    
    private static final String url = "jdbc:mysql://localhost:3306/fitzone_db" ;
    private static final String user = "root" ;
    private static final String password = "" ;               

    public static Connection getConnection () throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }
    
    public boolean addMember (Member member){
        String sql = "INSERT INTO members (fullname,contactNum,type,rating,duration,totalcharge) VALUES (?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = getConnection();PreparedStatement pstmt = conn.prepareStatement(sql)){
        pstmt.setString(1, member.getMembername());
        
        pstmt.setString(2, member.getContactNum());
       
        pstmt.setString(3, member.getMembership()); 
        
        pstmt.setString(4, member.getRate());       
        
        pstmt.setInt(5, member.getDuration());
        
        pstmt.setDouble(6, member.getTotal());     
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } 
        
        catch (SQLException e){
            System.out.println("Error saving: " + e.getMessage());
            e.printStackTrace();
            return false;        
                
            }
    }
    
    public ArrayList<Member> searchMember(String nameQuery) {

        ArrayList<Member> list = new ArrayList<>();

        String sql = "SELECT * FROM members WHERE fullName LIKE ?";



        try (Connection conn = getConnection();

             PreparedStatement pstmt = conn.prepareStatement(sql)) {



            pstmt.setString(1, "%" + nameQuery + "%"); 

            ResultSet rs = pstmt.executeQuery();



            while (rs.next()) {
                Member m = new Member(

                    rs.getString("memberID"),

                    rs.getString("fullName"),

                    rs.getString("contactNum"),

                    rs.getString("type"),

                    rs.getString("rating"),

                    rs.getInt("duration"),

                    rs.getDouble("totalCharge")

                );

                list.add(m);

            }

        } catch (SQLException e) {

            System.out.println("Error searching: " + e.getMessage());

        }

        return list;

    }



    // 4. Display All Members (Requirement: Display All)

    public ArrayList<Member> getAllMembers() {

        ArrayList<Member> list = new ArrayList<>();

        String sql = "SELECT * FROM members";



        try (Connection conn = getConnection();

             PreparedStatement pstmt = conn.prepareStatement(sql);

             ResultSet rs = pstmt.executeQuery()) {


            while (rs.next()) {

                Member m = new Member(

                    rs.getString("memberID"),

                    rs.getString("fullName"),

                    rs.getString("contactNum"),

                    rs.getString("type"),

                    rs.getString("rating"),

                    rs.getInt("duration"),

                    rs.getDouble("totalCharge")

                );

                list.add(m);

            }

        } catch (SQLException e) {

            System.out.println("Error loading all members: " + e.getMessage());

        }

        return list;

    }
    

    public boolean updateMember(Member member) {

        String sql = "UPDATE members SET fullName=?, contactNum=?, type=?, rating=?, duration=?, totalCharge=? WHERE memberID=?";



        try (Connection conn = getConnection();

             PreparedStatement pstmt = conn.prepareStatement(sql)) {



            pstmt.setString(1, member.getMembername());

            pstmt.setString(2, member.getContactNum());

            pstmt.setString(3, member.getMembership());

            pstmt.setString(4, member.getRate());

            pstmt.setInt(5, member.getDuration());

            pstmt.setDouble(6, member.getTotal());

            pstmt.setString(7, member.getMemberid()); 



            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;



        } catch (SQLException e) {

            System.out.println("Error updating: " + e.getMessage());

            return false;

        }

    }

    public boolean deleteMember(int id) {

        String sql = "DELETE FROM members WHERE memberID=?";



        try (Connection conn = getConnection();

             PreparedStatement pstmt = conn.prepareStatement(sql)) {



            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;



        } catch (SQLException e) {

            System.out.println("Error deleting: " + e.getMessage());

            return false;

        }

    }
    
}
    
