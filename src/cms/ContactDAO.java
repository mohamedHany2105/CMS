// ContactDAO.java
package CMS;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ContactDAO {

    // Database connection details - consider moving to a config file or class
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=CMS;encrypt=true;trustServerCertificate=true;";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "protopkicker"; 
// the code provided is responsible for connecting the database 
    // Load the JDBC driver
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            // Handle driver loading error, e.g., log it or throw a runtime exception
            JOptionPane.showMessageDialog(null, "SQL Server JDBC Driver not found! " + e.getMessage(), "Driver Error", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException("Failed to load SQL Server JDBC Driver", e);
        }
    }

    // Method to get a database connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Method to fetch all contacts
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT sno, Name, [phone number], address, email FROM CMS ORDER BY sno"; // Added ORDER BY for consistency

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Contact contact = new Contact();
                contact.setSno(rs.getInt("sno"));
                contact.setName(rs.getString("Name"));
                contact.setPhone(rs.getString("phone number")); // Assuming phone number is stored as String
                contact.setAddress(rs.getString("address"));
                contact.setEmail(rs.getString("email"));
                contacts.add(contact);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error fetching contacts: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            // Consider more sophisticated error handling or logging
        }
        return contacts;
    }

    // Method to save a new contact
    public boolean saveContact(Contact contact) {
        String query = "INSERT INTO CMS (Name, [phone number], address, email) VALUES (?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, contact.getName());
            pst.setString(2, contact.getPhone());
            pst.setString(3, contact.getAddress());
            pst.setString(4, contact.getEmail());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                // Optionally retrieve the generated key (sno) if needed
                // try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                // if (generatedKeys.next()) {
                // contact.setSno(generatedKeys.getInt(1));
                // }
                // }
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error saving contact: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // Method to update an existing contact
    public boolean updateContact(Contact contact) {
        // Assuming sno is the primary key and is present in the contact object
        if (contact.getSno() <= 0) {
             JOptionPane.showMessageDialog(null, "Invalid contact ID for update.", "Update Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String query = "UPDATE CMS SET Name = ?, [phone number] = ?, address = ?, email = ? WHERE sno = ?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, contact.getName());
            pst.setString(2, contact.getPhone());
            pst.setString(3, contact.getAddress());
            pst.setString(4, contact.getEmail());
            pst.setInt(5, contact.getSno());

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating contact: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // Method to delete a contact by sno
    public boolean deleteContact(int sno) {
        if (sno <= 0) {
            JOptionPane.showMessageDialog(null, "Invalid contact ID for deletion.", "Delete Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        String query = "DELETE FROM CMS WHERE sno = ?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, sno);
            int affectedRows = pst.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting contact: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
