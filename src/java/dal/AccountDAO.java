package dal;

import java.util.ArrayList;
import java.util.List;
import model.Account;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO extends DBContext {

    public AccountDAO() {
        super();
    }

    private Account getFromResultSet(ResultSet rs) throws SQLException {
        return new Account(
                rs.getInt("ID_Account"),
                rs.getString("Email"),
                rs.getString("Username"),
                rs.getString("Password"),
                rs.getString("Role"),
                rs.getBoolean("isActive")
        );
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                accounts.add(getFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public Account getAccountById(int id) {
        String sql = "SELECT * FROM account WHERE ID_Account = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account getAccountById2(int id) {
        String sql = "SELECT * FROM account WHERE ID_Account = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setID_Account(rs.getInt("ID_Account"));
                    account.setEmail(rs.getString("Email"));
                    account.setUsername(rs.getString("Username"));
                    account.setPassword(rs.getString("Password"));
                    account.setRole(rs.getString("Role"));
                    return account;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Account> getAccountsPaginated(int start, int accountsPerPage) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account LIMIT ?, ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, start);
            stmt.setInt(2, accountsPerPage);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    accounts.add(getFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public int getTotalAccounts() {
        String sql = "SELECT COUNT(*) FROM account";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Account> getAllAccountsForNhaTro() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account where role = 'User' or role ='Quản lý'";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                accounts.add(getFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public boolean addAccount(Account account) {
        String sql = "INSERT INTO account (Email, Username, Password, Role, isActive) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, account.getEmail());
            stmt.setString(2, account.getUsername());
            stmt.setString(3, account.getPassword());  // Note: In production, ensure this is a hashed password
            stmt.setString(4, account.getRole());
            stmt.setBoolean(5, account.isActive());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAccount(Account account) {
        String sql = "UPDATE account SET Email = ?, Username = ?, Password = ?, Role = ?, isActive = ? WHERE ID_Account = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, account.getEmail());
            stmt.setString(2, account.getUsername());
            stmt.setString(3, account.getPassword());  // Note: In production, ensure this is a hashed password
            stmt.setString(4, account.getRole());
            stmt.setBoolean(5, account.isActive());
            stmt.setInt(6, account.getID_Account());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAccountStatus(int accountId, boolean isActive) {
        String sql = "UPDATE account SET isActive = ? WHERE ID_Account = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setBoolean(1, isActive);
            stmt.setInt(2, accountId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Account> searchAccountsByName(String searchTerm, int start, int accountsPerPage) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account WHERE username LIKE ? LIMIT ?, ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchTerm + "%");
            stmt.setInt(2, start);
            stmt.setInt(3, accountsPerPage);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Account account = getFromResultSet(rs);
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public int getTotalAccountsBySearch(String searchTerm) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM account WHERE username LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public Account getAccount(String username, String password) {
        String sql = "SELECT * FROM ACCOUNT WHERE Username = ? AND Password = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                int idAccount = rs.getInt("ID_Account");
                String retrievedUsername = rs.getString("Username");
                String retrievedPassword = rs.getString("Password");
                String email = rs.getString("Email");

                Account account = new Account();
                account.setID_Account(idAccount);
                account.setUsername(retrievedUsername);
                account.setPassword(retrievedPassword);
                account.setEmail(email);
                account.setRole(rs.getString("Role"));
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Tráº£ vá» null náº¿u khÃ´ng tÃ¬m tháº¥y tÃ i khoáº£n
    }

    public boolean isExist(String username, String password) {
        String sql = "SELECT COUNT(*) FROM ACCOUNT WHERE Username = ? AND Password = ?";
        try (PreparedStatement st = connection.prepareStatement(sql);) {

            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isGuess(String username, String password) {
        String sql = "SELECT COUNT(*) FROM ACCOUNT WHERE Username = ? AND Password = ? AND Role = 'tenant'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isOwner(String username, String password) {
        String sql = "SELECT COUNT(*) FROM CHU_TRO WHERE Username = ? AND Password = ? AND Role = 'landlord'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Check if the specific account is a Manager
     *
     * @param username
     * @param password
     * @return
     */
    public boolean isManager(String username, String password) {
        String sql = "SELECT COUNT(*) FROM QUAN_LY WHERE Username = ? AND Password = ? AND Role = 'manager'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Insert an account into ACCOUNT table
     *
     * @param username
     * @param email
     * @param password
     * @return
     */
    public boolean addAccount(String email, String username, String password) {
        String sql = "INSERT INTO ACCOUNT (Email, Username, Password, Role) VALUES (?, ?, ?, 'guest')";

        try (PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, email);
            st.setString(2, username);
            st.setString(3, password);

            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check if the specific email exist
     *
     * @param email
     * @return
     */
    public boolean isEmailExist(String email) {
        String sql = "SELECT * FROM ACCOUNT WHERE Email = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check if password updated successfully
     *
     * @param email
     * @param newPassword
     * @return
     */
    public boolean updatePassword(String email, String newPassword) {
        String sql = "UPDATE ACCOUNT SET Password = ? WHERE Email = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, newPassword);
            st.setString(2, email);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Account> getTenantAccountsByManager(int ID_Account, int start, int accountsPerPage) {
        List<Account> tenants = new ArrayList<>();
        String sql = "SELECT DISTINCT a.ID_Account, a.Email, a.Username, a.Role, a.isActive\n"
                + "FROM account a\n"
                + "JOIN khach_thue kt ON a.ID_Account = kt.ID_Account\n"
                + "JOIN hop_dong hd ON kt.ID_KhachThue = hd.ID_KhachThue\n"
                + "JOIN phong_tro pt ON hd.ID_PhongTro = pt.ID_Phong\n"
                + "JOIN nha_tro nt ON pt.ID_NhaTro = nt.ID_NhaTro\n"
                + "JOIN quan_ly ql ON nt.ID_NhaTro = ql.ID_NhaTro\n"
                + "WHERE a.Role = 'tenant' \n"
                + "  AND ql.ID_Account = ? LIMIT ?, ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, ID_Account);
            stmt.setInt(2, start);
            stmt.setInt(3, accountsPerPage);
            ResultSet rs = stmt.executeQuery();

            // Duyệt qua kết quả và thêm vào danh sách tenants
            while (rs.next()) {
                Account account = new Account();
                account.setID_Account(rs.getInt("ID_Account"));
                account.setEmail(rs.getString("Email"));
                account.setUsername(rs.getString("Username"));
                account.setRole(rs.getString("Role"));
                account.setActive(rs.getBoolean("isActive"));
                tenants.add(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenants;
    }

    public int countTenantAccountsByManager(int ID_Account) {
        int count = 0;
        String sql = "SELECT COUNT(DISTINCT a.ID_Account) AS tenant_count " +
                     "FROM account a " +
                     "JOIN khach_thue kt ON a.ID_Account = kt.ID_Account " +
                     "JOIN hop_dong hd ON kt.ID_KhachThue = hd.ID_KhachThue " +
                     "JOIN phong_tro pt ON hd.ID_PhongTro = pt.ID_Phong " +
                     "JOIN nha_tro nt ON pt.ID_NhaTro = nt.ID_NhaTro " +
                     "JOIN quan_ly ql ON nt.ID_NhaTro = ql.ID_NhaTro " +
                     "WHERE a.Role = 'tenant' " +
                     "AND ql.ID_Account = ? ";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set giá trị ID_QuanLy vào truy vấn
            stmt.setInt(1, ID_Account);
            ResultSet rs = stmt.executeQuery();

            // Lấy kết quả count
            if (rs.next()) {
                count = rs.getInt("tenant_count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public List<Account> searchTenantByUsername(int ID_Account, String username, int start, int accountsPerPage) {
        List<Account> tenants = new ArrayList<>();
        String sql = "SELECT DISTINCT a.ID_Account, a.Email, a.Username, a.Role, a.isActive " +
                     "FROM account a " +
                     "JOIN khach_thue kt ON a.ID_Account = kt.ID_Account " +
                     "JOIN hop_dong hd ON kt.ID_KhachThue = hd.ID_KhachThue " +
                     "JOIN phong_tro pt ON hd.ID_PhongTro = pt.ID_Phong " +
                     "JOIN nha_tro nt ON pt.ID_NhaTro = nt.ID_NhaTro " +
                     "JOIN quan_ly ql ON nt.ID_NhaTro = ql.ID_NhaTro " +
                     "WHERE a.Role = 'tenant' " +
                     "AND ql.ID_Account = ? " +
                     "AND a.Username LIKE ? LIMIT ?, ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set giá trị ID_QuanLy và username vào truy vấn
            stmt.setInt(1, ID_Account);
            stmt.setString(2, "%" + username + "%"); // Sử dụng LIKE cho tìm kiếm theo mẫu
            stmt.setInt(3, start);
            stmt.setInt(4, accountsPerPage);

            ResultSet rs = stmt.executeQuery();

            // Duyệt qua kết quả và thêm vào danh sách tenants
            while (rs.next()) {
                Account account = new Account();
                account.setID_Account(rs.getInt("ID_Account"));
                account.setEmail(rs.getString("Email"));
                account.setUsername(rs.getString("Username"));
                account.setRole(rs.getString("Role"));
                account.setActive(rs.getBoolean("isActive"));
                tenants.add(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenants;
    }
    
    public int countTenantsByUsername(int ID_Account, String username) {
        int count = 0;
        String sql = "SELECT COUNT(DISTINCT a.ID_Account) AS tenant_count " +
                     "FROM account a " +
                     "JOIN khach_thue kt ON a.ID_Account = kt.ID_Account " +
                     "JOIN hop_dong hd ON kt.ID_KhachThue = hd.ID_KhachThue " +
                     "JOIN phong_tro pt ON hd.ID_PhongTro = pt.ID_Phong " +
                     "JOIN nha_tro nt ON pt.ID_NhaTro = nt.ID_NhaTro " +
                     "JOIN quan_ly ql ON nt.ID_NhaTro = ql.ID_NhaTro " +
                     "WHERE a.Role = 'tenant' " +
                     "AND ql.ID_Account = ? " +
                     "AND a.Username LIKE ? ";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set giá trị ID_QuanLy và username vào truy vấn
            stmt.setInt(1, ID_Account);
            stmt.setString(2, "%" + username + "%"); // Sử dụng LIKE để tìm kiếm theo mẫu

            ResultSet rs = stmt.executeQuery();

            // Lấy kết quả count
            if (rs.next()) {
                count = rs.getInt("tenant_count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();

        System.out.println(dao.getAccount("minhanh", "123"));
    }

}
