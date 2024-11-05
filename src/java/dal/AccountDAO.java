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
        String sql = "SELECT COUNT(DISTINCT a.ID_Account) AS tenant_count "
                + "FROM account a "
                + "JOIN khach_thue kt ON a.ID_Account = kt.ID_Account "
                + "JOIN hop_dong hd ON kt.ID_KhachThue = hd.ID_KhachThue "
                + "JOIN phong_tro pt ON hd.ID_PhongTro = pt.ID_Phong "
                + "JOIN nha_tro nt ON pt.ID_NhaTro = nt.ID_NhaTro "
                + "JOIN quan_ly ql ON nt.ID_NhaTro = ql.ID_NhaTro "
                + "WHERE a.Role = 'tenant' "
                + "AND ql.ID_Account = ? ";

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
        String sql = "SELECT DISTINCT a.ID_Account, a.Email, a.Username, a.Role, a.isActive "
                + "FROM account a "
                + "JOIN khach_thue kt ON a.ID_Account = kt.ID_Account "
                + "JOIN hop_dong hd ON kt.ID_KhachThue = hd.ID_KhachThue "
                + "JOIN phong_tro pt ON hd.ID_PhongTro = pt.ID_Phong "
                + "JOIN nha_tro nt ON pt.ID_NhaTro = nt.ID_NhaTro "
                + "JOIN quan_ly ql ON nt.ID_NhaTro = ql.ID_NhaTro "
                + "WHERE a.Role = 'tenant' "
                + "AND ql.ID_Account = ? "
                + "AND a.Username LIKE ? LIMIT ?, ?";

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
        String sql = "SELECT COUNT(DISTINCT a.ID_Account) AS tenant_count "
                + "FROM account a "
                + "JOIN khach_thue kt ON a.ID_Account = kt.ID_Account "
                + "JOIN hop_dong hd ON kt.ID_KhachThue = hd.ID_KhachThue "
                + "JOIN phong_tro pt ON hd.ID_PhongTro = pt.ID_Phong "
                + "JOIN nha_tro nt ON pt.ID_NhaTro = nt.ID_NhaTro "
                + "JOIN quan_ly ql ON nt.ID_NhaTro = ql.ID_NhaTro "
                + "WHERE a.Role = 'tenant' "
                + "AND ql.ID_Account = ? "
                + "AND a.Username LIKE ? ";

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

    public List<Account> getAccountsByIdHouse(int idHouse, int start, int accountsPerPage) {
        List<Account> accountsByIdHouses = new ArrayList<>();
        String sql = "SELECT \n"
                + "    tenant_account.ID_Account AS Tenant_Account_ID,\n"
                + "    tenant_account.Email AS Tenant_Email,\n"
                + "    tenant_account.Username AS Tenant_Username,\n"
                + "    tenant_account.Role AS Tenant_Role,\n"
                + "    tenant.isActive AS Tenant_isActive,\n"
                + "    manager_account.ID_Account AS Manager_Account_ID,\n"
                + "    manager_account.Email AS Manager_Email,\n"
                + "    manager_account.Username AS Manager_Username,\n"
                + "    manager_account.Role AS Manager_Role,\n"
                + "    manager.isActive AS Manager_isActive\n"
                + "FROM \n"
                + "    nha_tro AS house\n"
                + "LEFT JOIN \n"
                + "    phong_tro AS room ON room.ID_NhaTro = house.ID_NhaTro\n"
                + "LEFT JOIN \n"
                + "    hop_dong AS contract ON contract.ID_PhongTro = room.ID_Phong\n"
                + "LEFT JOIN \n"
                + "    khach_thue AS tenant ON tenant.ID_KhachThue = contract.ID_KhachThue\n"
                + "LEFT JOIN \n"
                + "    account AS tenant_account ON tenant_account.ID_Account = tenant.ID_Account AND tenant_account.Role = 'tenant'\n"
                + "LEFT JOIN \n"
                + "    quan_ly AS manager ON house.ID_NhaTro = manager.ID_NhaTro\n"
                + "LEFT JOIN \n"
                + "    account AS manager_account ON manager_account.ID_Account = manager.ID_Account\n"
                + "WHERE \n"
                + "    house.ID_NhaTro = ? LIMIT ?, ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idHouse);
            stmt.setInt(2, start);
            stmt.setInt(3, accountsPerPage);
            ResultSet rs = stmt.executeQuery();

            Account managerAccount = null; // Khởi tạo biến cho manager

            // Duyệt qua kết quả và thêm vào danh sách accountsByIdHouses
            while (rs.next()) {
                // Thêm thông tin của tenant (nếu có)
                if (rs.getInt("Tenant_Account_ID") > 0) { // Kiểm tra tenant có tồn tại không
                    Account tenantAccount = new Account();
                    tenantAccount.setID_Account(rs.getInt("Tenant_Account_ID"));
                    tenantAccount.setEmail(rs.getString("Tenant_Email"));
                    tenantAccount.setUsername(rs.getString("Tenant_Username"));
                    tenantAccount.setRole(rs.getString("Tenant_Role"));
                    tenantAccount.setActive(rs.getBoolean("Tenant_isActive"));
                    accountsByIdHouses.add(tenantAccount);
                }

                // Thêm thông tin của manager (nếu có) chỉ một lần
                if (rs.getInt("Manager_Account_ID") > 0) { // Kiểm tra manager có tồn tại không
                    if (managerAccount == null) { // Nếu managerAccount chưa được khởi tạo
                        managerAccount = new Account();
                        managerAccount.setID_Account(rs.getInt("Manager_Account_ID"));
                        managerAccount.setEmail(rs.getString("Manager_Email"));
                        managerAccount.setUsername(rs.getString("Manager_Username"));
                        managerAccount.setRole(rs.getString("Manager_Role"));
                        managerAccount.setActive(rs.getBoolean("Manager_isActive"));
                        accountsByIdHouses.add(managerAccount); // Thêm manager vào danh sách
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountsByIdHouses;
    }

    public int getTotalAccountsByIdHouse(int idHouse) {
        String sql = "SELECT COUNT(DISTINCT tenant_account.ID_Account) AS Total_Tenant_Accounts,\n"
                + "       COUNT(DISTINCT manager_account.ID_Account) AS Total_Manager_Accounts\n"
                + "FROM nha_tro AS house\n"
                + "LEFT JOIN phong_tro AS room ON room.ID_NhaTro = house.ID_NhaTro\n"
                + "LEFT JOIN hop_dong AS contract ON contract.ID_PhongTro = room.ID_Phong\n"
                + "LEFT JOIN khach_thue AS tenant ON tenant.ID_KhachThue = contract.ID_KhachThue\n"
                + "LEFT JOIN account AS tenant_account ON tenant_account.ID_Account = tenant.ID_Account AND tenant_account.Role = 'tenant'\n"
                + "LEFT JOIN quan_ly AS manager ON house.ID_NhaTro = manager.ID_NhaTro\n"
                + "LEFT JOIN account AS manager_account ON manager_account.ID_Account = manager.ID_Account\n"
                + "WHERE house.ID_NhaTro = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idHouse);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int totalTenantAccounts = rs.getInt("Total_Tenant_Accounts");
                int totalManagerAccounts = rs.getInt("Total_Manager_Accounts");

                // Trả về tổng số tài khoản mà không bị duplicate
                return totalTenantAccounts + (totalManagerAccounts > 0 ? 1 : 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu không có tài khoản nào
    }

    public List<Account> getAccountsByIdHouseAndUsername(int idHouse, String usernameSearchTerm, int start, int accountsPerPage) {
        List<Account> accountsByIdHouses = new ArrayList<>();
        String sql = "SELECT \n"
                + "    tenant_account.ID_Account AS Account_ID,\n"
                + "    tenant_account.Email AS Tenant_Email,\n"
                + "    tenant_account.Username AS Tenant_Username,\n"
                + "    tenant_account.Role AS Tenant_Role,\n"
                + "    tenant.isActive AS Tenant_isActive,\n"
                + "    manager_account.ID_Account AS Manager_Account_ID,\n"
                + "    manager_account.Email AS Manager_Email,\n"
                + "    manager_account.Username AS Manager_Username,\n"
                + "    manager_account.Role AS Manager_Role,\n"
                + "    manager.isActive AS Manager_isActive\n"
                + "FROM \n"
                + "    account AS tenant_account\n"
                + "JOIN \n"
                + "    khach_thue AS tenant ON tenant_account.ID_Account = tenant.ID_Account\n"
                + "JOIN \n"
                + "    hop_dong AS contract ON tenant.ID_KhachThue = contract.ID_KhachThue\n"
                + "JOIN \n"
                + "    phong_tro AS room ON contract.ID_PhongTro = room.ID_Phong\n"
                + "JOIN \n"
                + "    nha_tro AS house ON room.ID_NhaTro = house.ID_NhaTro\n"
                + "JOIN \n"
                + "    quan_ly AS manager ON house.ID_NhaTro = manager.ID_NhaTro\n"
                + "JOIN \n"
                + "    account AS manager_account ON manager.ID_Account = manager_account.ID_Account\n"
                + "WHERE \n"
                + "    tenant_account.Role = 'tenant' \n"
                + "    AND house.ID_NhaTro = ? \n"
                + "    AND (tenant_account.Username LIKE ? OR manager_account.Username LIKE ?) LIMIT ?, ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idHouse);
            stmt.setString(2, "%" + usernameSearchTerm + "%"); // Tìm ki?m theo tenant's username
            stmt.setString(3, "%" + usernameSearchTerm + "%"); // Tìm ki?m theo manager's username
            stmt.setInt(4, start);
            stmt.setInt(5, accountsPerPage);
            ResultSet rs = stmt.executeQuery();

            // Duy?t qua k?t qu? và thêm vào danh sách accounts
            while (rs.next()) {
                // Thêm thông tin c?a tenant
                Account tenantAccount = new Account();
                tenantAccount.setID_Account(rs.getInt("Account_ID"));
                tenantAccount.setEmail(rs.getString("Tenant_Email"));
                tenantAccount.setUsername(rs.getString("Tenant_Username"));
                tenantAccount.setRole(rs.getString("Tenant_Role"));
                tenantAccount.setActive(rs.getBoolean("Tenant_isActive"));
                accountsByIdHouses.add(tenantAccount);

                // Thêm thông tin c?a manager
                Account managerAccount = new Account();
                managerAccount.setID_Account(rs.getInt("Manager_Account_ID"));
                managerAccount.setEmail(rs.getString("Manager_Email"));
                managerAccount.setUsername(rs.getString("Manager_Username"));
                managerAccount.setRole(rs.getString("Manager_Role"));
                managerAccount.setActive(rs.getBoolean("Manager_isActive"));
                accountsByIdHouses.add(managerAccount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountsByIdHouses;
    }

    public int countAccountsByIdHouseAndUsername(int idHouse, String usernameSearchTerm) {
        String sql = "SELECT COUNT(*) AS Total_Accounts\n"
                + "FROM \n"
                + "    account AS tenant_account\n"
                + "JOIN \n"
                + "    khach_thue AS tenant ON tenant_account.ID_Account = tenant.ID_Account\n"
                + "JOIN \n"
                + "    hop_dong AS contract ON tenant.ID_KhachThue = contract.ID_KhachThue\n"
                + "JOIN \n"
                + "    phong_tro AS room ON contract.ID_PhongTro = room.ID_Phong\n"
                + "JOIN \n"
                + "    nha_tro AS house ON room.ID_NhaTro = house.ID_NhaTro\n"
                + "JOIN \n"
                + "    quan_ly AS manager ON house.ID_NhaTro = manager.ID_NhaTro\n"
                + "JOIN \n"
                + "    account AS manager_account ON manager.ID_Account = manager_account.ID_Account\n"
                + "WHERE \n"
                + "    tenant_account.Role = 'tenant' \n"
                + "    AND house.ID_NhaTro = ? \n"
                + "    AND (tenant_account.Username LIKE ? OR manager_account.Username LIKE ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idHouse);
            stmt.setString(2, "%" + usernameSearchTerm + "%"); // Tìm kiếm theo tenant's username
            stmt.setString(3, "%" + usernameSearchTerm + "%"); // Tìm kiếm theo manager's username
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("Total_Accounts");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();

        System.out.println(dao.getAccount("minhanh", "123"));
    }

}
