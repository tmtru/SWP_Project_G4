package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.GoogleAccount;

public class GoogleAccountDAO extends DBContext {

    /**
     * Add Google Account to the ACCOUNT table
     *
     * @param email
     * @return boolean
     */
    public boolean addGoogleAccount(String email) {
        // Tạo username từ email
        String username = email.split("@")[0];

        // Kiểm tra xem email đã tồn tại chưa
        if (isEmailExist(email)) {
            return false; // Email đã tồn tại
        }

        String sql = "INSERT INTO ACCOUNT (Email, Username, Password, Role) VALUES (?, ?, NULL, 'Khach Thue')";

        try (PreparedStatement st = connection.prepareStatement(sql)){
            
            st.setString(1, email);
            st.setString(2, username);

            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEmailExist(String email) {
        String sql = "SELECT * FROM ACCOUNT WHERE Email = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)){
            
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public GoogleAccount getAccount(String email) {
        String sql = "SELECT * FROM ACCOUNT WHERE Email = ?";
        try (PreparedStatement st = connection.prepareStatement(sql);){
            
            st.setString(1, email);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int idAccount = rs.getInt("ID_Account");
                String retrievedUsername = rs.getString("Username");

                GoogleAccount account = new GoogleAccount();
                account.setID_Account(idAccount);
                account.setUsername(retrievedUsername);
                return account; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy tài khoản
    }
}
