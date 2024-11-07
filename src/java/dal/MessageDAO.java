package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Message;

public class MessageDAO extends DBContext {

    public List<Message> getMessagesBetween(int senderId, int receiverId, int idNha) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM message WHERE ((sender_id = ? AND receiver_id = ? AND ID_NhaTro = ?) OR (sender_id = ? AND receiver_id = ? AND ID_NhaTro = ?)) ORDER BY timestamp";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.setInt(3, idNha);
            ps.setInt(4, receiverId);
            ps.setInt(5, senderId);
            ps.setInt(6, idNha);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Message msg = new Message();
                msg.setId(rs.getInt("ID_Message"));
                msg.setSenderId(rs.getInt("sender_id"));
                msg.setReceiverId(rs.getInt("receiver_id"));
                msg.setContent(rs.getString("content"));
                msg.setNhatroID(rs.getInt("ID_NhaTro"));
                msg.setTimestamp(rs.getTimestamp("timestamp"));
                msg.setRead(rs.getBoolean("is_read"));
                messages.add(msg);
            }
            markMessagesAsRead(senderId, receiverId, idNha);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public List<Account> getTenantsWithUnreadCounts(int landlordId) {
    List<Account> tenants = new ArrayList<>();
    String sql = "SELECT a.*, COALESCE(unread_counts.unread_count, 0) AS unread_count "
            + "FROM account a "
            + "INNER JOIN ("
            + "    SELECT DISTINCT CASE "
            + "        WHEN sender_id = ? THEN receiver_id "
            + "        ELSE sender_id "
            + "    END AS user_id "
            + "    FROM message "
            + "    WHERE sender_id = ? OR receiver_id = ? "
            + ") m ON a.ID_Account = m.user_id "
            + "LEFT JOIN ("
            + "    SELECT sender_id AS user_id, COUNT(*) AS unread_count "
            + "    FROM message "
            + "    WHERE receiver_id = ? AND is_read = FALSE "
            + "    GROUP BY sender_id"
            + ") unread_counts ON a.ID_Account = unread_counts.user_id "
            + "WHERE a.Role = 'tenant' OR a.Role = 'guest'";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, landlordId); // For CASE WHEN sender_id = ? THEN receiver_id ELSE sender_id
        ps.setInt(2, landlordId); // WHERE sender_id = ?
        ps.setInt(3, landlordId); // OR receiver_id = ?
        ps.setInt(4, landlordId); // For unread_counts WHERE receiver_id = ?
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Account account = new Account();
            account.setID_Account(rs.getInt("ID_Account"));
            account.setEmail(rs.getString("Email"));
            account.setUsername(rs.getString("Username"));
            account.setRole(rs.getString("Role"));
            account.setActive(rs.getBoolean("isActive"));
            account.setUnreadCount(rs.getInt("unread_count")); // Ensure this field exists in Account class
            tenants.add(account);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return tenants;
}


    public void insertMessage(Message message) {
        String sql = "INSERT INTO message (sender_id, receiver_id, content, ID_NhaTro) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message.getSenderId());
            ps.setInt(2, message.getReceiverId());
            ps.setString(3, message.getContent());
            ps.setInt(4, message.getNhatroID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void markMessagesAsRead(int senderId, int receiverId, int houseId) {
    String sql = "UPDATE message SET is_read = TRUE WHERE sender_id = ? AND receiver_id = ? AND ID_NhaTro = ? AND is_read = FALSE";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, senderId);
        ps.setInt(2, receiverId);
        ps.setInt(3, houseId);
        ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public int getUnreadMessageCount(int userId) {
        String sql = "SELECT COUNT(*) FROM message WHERE receiver_id = ? AND is_read = FALSE";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        MessageDAO mdao = new MessageDAO();
        System.out.println(mdao.getTenantsWithUnreadCounts(1).size());
    }
}
