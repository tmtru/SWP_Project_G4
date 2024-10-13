package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.GoogleAccount;

public class GoogleAccountDAO extends DBContext {

    /**
     * Add Google Account to the ACCOUNT table method.
     *
     * @param email
     * @return boolean
     */
    public boolean addGoogleAccount(String email) {
        String username = email.split("@")[0];

        if (isEmailExist(email)) {
            return false; 
        }

        String sql = "INSERT INTO ACCOUNT (Email, Username, Password, Role) VALUES (?, ?, NULL, 'guest')";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, username);

            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check if email exists method.
     * 
     * @param email
     * @return 
     */
    public boolean isEmailExist(String email) {
        String sql = "SELECT * FROM ACCOUNT WHERE Email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get account from ACCOUNT table method.
     * 
     * @param email
     * @return 
     */
    public GoogleAccount getAccount(String email) {
        String sql = "SELECT * FROM ACCOUNT WHERE Email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int idAccount = rs.getInt("ID_Account");;
                String retrievedUsername = rs.getString("Username");

                GoogleAccount account = new GoogleAccount();
                account.setID_Account(idAccount);
                account.setEmail(email);
                account.setUsername(retrievedUsername);
                return account; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void main(String[] args) {
        // Tạo một instance của GoogleAccountDAO
        GoogleAccountDAO googleAccountDAO = new GoogleAccountDAO();
        
        // Email bạn muốn kiểm tra và thêm
        String emailToAdd = "toanthhe181060@fpt.edu.vn";
        
        // Gọi hàm addGoogleAccount để thêm email
        boolean result = googleAccountDAO.addGoogleAccount(emailToAdd);
        
        // Kiểm tra kết quả trả về
        if (result) {
            System.out.println("Account added successfully for email: " + emailToAdd);
        } else {
            System.out.println("Account already exists or failed to add: " + emailToAdd);
        }
    }
}
