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
        String sql = "INSERT INTO ACCOUNT (Email, Username, Password, Role, isActive) VALUES (?, ?, ?, 'guest', 1)";

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

    public boolean addAccount2(String email, String username, String password) {
        String sql = "INSERT INTO ACCOUNT (Email, Username, Password, Role, isActive) VALUES (?, ?, ?, 'manager', 1)";

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

    public List<Account> getAccounts(String searchTerm, String roleFilter, int page, int pageSize) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE username LIKE ? AND (? = '' OR role = ?) ORDER BY ID_Account LIMIT ?, ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, "%" + searchTerm + "%");
            stmt.setString(2, roleFilter);
            stmt.setString(3, roleFilter);
            stmt.setInt(4, (page - 1) * pageSize);
            stmt.setInt(5, pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("ID_Account");
                String email = rs.getString("Email");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String role = rs.getString("Role");
                boolean active = rs.getBoolean("Active");

                accounts.add(new Account(id, email, username, password, role, active));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public int getTotalAccounts(String searchTerm, String roleFilter) {
        int totalAccounts = 0;
        String sql = "SELECT COUNT(*) FROM accounts WHERE username LIKE ? AND (? = '' OR role = ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + searchTerm + "%");
            stmt.setString(2, roleFilter);
            stmt.setString(3, roleFilter);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalAccounts = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalAccounts;
    }

    public void toggleActive(int accountId, boolean isActive) {
        String sql = "UPDATE accounts SET Active = ? WHERE ID_Account = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, isActive);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Account> getAllAccountsPaginated(int page, int pageSize) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM account ORDER BY ID_Account LIMIT ?, ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, (page - 1) * pageSize);
            stmt.setInt(2, pageSize);

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

    public List<Account> getFilteredAccounts(String searchTerm, String roleFilter, int page, int pageSize) {
        List<Account> accounts = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM account WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (!searchTerm.isEmpty()) {
            sql.append(" AND username LIKE ?");
            params.add("%" + searchTerm + "%");
        }

        if (!roleFilter.isEmpty()) {
            sql.append(" AND role = ?");
            params.add(roleFilter);
        }

        sql.append(" ORDER BY ID_Account LIMIT ?, ?");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            for (Object param : params) {
                stmt.setObject(paramIndex++, param);
            }
            stmt.setInt(paramIndex++, (page - 1) * pageSize);
            stmt.setInt(paramIndex, pageSize);

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

    public int getTotalFilteredAccounts(String searchTerm, String roleFilter) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM account WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (!searchTerm.isEmpty()) {
            sql.append(" AND username LIKE ?");
            params.add("%" + searchTerm + "%");
        }

        if (!roleFilter.isEmpty()) {
            sql.append(" AND role = ?");
            params.add(roleFilter);
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            for (Object param : params) {
                stmt.setObject(paramIndex++, param);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getAccountIdByEmail(String email) {
        String sql = "SELECT ID_Account FROM account WHERE Email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_Account");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu không tìm thấy hoặc có lỗi
    }
}
