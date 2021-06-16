import java.io.IOException;
import java.sql.*;
import java.io.*;

class BorrowersScript {

    static final String DB_URL = "jdbc:mysql://localhost:3306/LibraryManagementSystem";
    static final String USER = "root";
    static final String PASS = "Km102395@";

    static String csvFilePath = "C:\\MS CS\\Library-Management-System\\borrowers.csv";

    public static void main(String[] args) throws Exception {

        try
        {
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            con.setAutoCommit(false); //autoCommit makes every statement as a transaction
            String query = "INSERT INTO Borrowers (CardId, Ssn, FirstName, LastName, Phone) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
 
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            
            String lineText = null;

            lineReader.readLine(); // skip header line in csv
 
            while ((lineText = lineReader.readLine()) != null) 
            {
                String[] data = lineText.split(",");
                int CardId = Integer.parseInt(data[0]);
                String Ssn = data[1].replace("-", "");
                String FirstName = data[2];
                String LastName = data[3];
                String Phone = data[8];
 
                statement.setInt(1, CardId);
                statement.setString(2, Ssn);
                statement.setString(3, FirstName);
                statement.setString(4, LastName);
                statement.setString(5, Phone);
 
                statement.addBatch();
                statement.executeBatch();
            }

            lineReader.close();
            con.commit();
            con.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            System.out.println("File Not Found");
        }
    }
}
