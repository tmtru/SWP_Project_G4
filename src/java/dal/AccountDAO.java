package dal;

import java.util.ArrayList;
import java.util.List;
import model.Account;
import java.sql.*;

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
    

    public boolean addAccount(Account account) {
        String sql = "INSERT INTO account (Email, Username, Password, Role, isActive) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = new DBContext().connection; PreparedStatement stmt = conn.prepareStatement(sql)) {

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
        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, account.getEmail());
            stmt.setString(2, account.getUsername());
            stmt.setString(3, account.getPassword());  // Note: In production, ensure this is a hashed password
            stmt.setString(4, account.getRole());
            stmt.setBoolean(5, account.isActive());
            stmt.setInt(6, account.getIdAccount());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAccountStatus(int accountId, boolean isActive) {
        String sql = "UPDATE account SET isActive = ? WHERE ID_Account = ?";
        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
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
        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        try (Connection conn = new DBContext().connection;
             PreparedStatement stmt = conn.prepareStatement(sql)) {
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

    public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();
        dao.getAllAccounts().stream().forEach(item -> {
            System.out.println(item);
        });
    }

}
