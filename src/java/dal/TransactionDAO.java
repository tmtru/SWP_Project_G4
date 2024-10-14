/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import model.DichVu;
import model.HoaDon;
import model.Phong;
import model.Transaction;

/**
 *
 * @author Admin
 */
public class TransactionDAO extends DBContext {

    // Method to get all transactions by ID_HoaDon abcd
    public List<Transaction> getTransactionsByIdHoaDon(int idHoaDon) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM `transaction` WHERE ID_HoaDon = ? AND isActive=1 ORDER BY ID_Transaction DESC";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idHoaDon);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setID_Transaction(rs.getInt("ID_Transaction"));
                    transaction.setMaGiaoDich(rs.getString("MaGiaoDich"));
                    transaction.setTransaction(rs.getTimestamp("TransactionDate"));
                    transaction.setAmount(rs.getFloat("Amount"));
                    transaction.setPaymentMethod(rs.getString("PaymentMethod"));
                    transaction.setID_HoaDon(rs.getInt("ID_HoaDon"));
                    transaction.setMoTa(rs.getNString("MoTa"));
                    transactions.add(transaction);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public String formatCurrency(float amount) {
        Locale vn = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(vn);
        return currencyVN.format(amount);
    }

    // Method to get total money by ID_HoaDon
    public float getTotalMoneyByIdHoaDon(int idHoaDon) {
        float totalMoney = 0;
        String query = "SELECT SUM(Amount) AS TotalMoney FROM `transaction` WHERE ID_HoaDon = ? AND isActive=1";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idHoaDon);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalMoney = rs.getFloat("TotalMoney");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalMoney;
    }

    public List<Transaction> getAllTransactionsByNhaTroId(int idNhaTro) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT t.ID_Transaction, t.MaGiaoDich, t.TransactionDate, t.Amount, t.PaymentMethod, t.ID_HoaDon, t.MoTa "
                + "FROM `transaction` t "
                + "JOIN hoa_don hd ON t.ID_HoaDon = hd.ID_HoaDon "
                + "JOIN hop_dong h ON hd.ID_HopDong = h.ID_HopDong "
                + "JOIN phong_tro p ON h.ID_PhongTro = p.ID_Phong "
                + "JOIN nha_tro n ON p.ID_NhaTro = n.ID_NhaTro "
                + "WHERE n.ID_NhaTro = ? AND t.isActive = 1 "
                + "ORDER BY t.ID_Transaction DESC";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idNhaTro);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setID_Transaction(rs.getInt("ID_Transaction"));
                    transaction.setMaGiaoDich(rs.getString("MaGiaoDich"));
                    transaction.setTransaction(rs.getTimestamp("TransactionDate"));
                    transaction.setAmount(rs.getFloat("Amount"));
                    transaction.setPaymentMethod(rs.getString("PaymentMethod"));
                    transaction.setID_HoaDon(rs.getInt("ID_HoaDon"));
                    transaction.setMoTa(rs.getNString("MoTa"));
                    transactions.add(transaction);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transactions;
    }

    //lấy transaction này của room nào thực hiện
    public Phong getRoomOfTransaction(int idTransaction) {
        Phong room = null;
        String query = "SELECT p.ID_Phong, p.TenPhongTro, p.ID_LoaiPhong, p.Tang, p.Dien_Tich, "
                + "p.Gia, p.Trang_thai, p.ID_NhaTro "
                + "FROM phong_tro p "
                + "JOIN hop_dong h ON p.ID_Phong = h.ID_PhongTro "
                + "JOIN hoa_don hd ON h.ID_HopDong = hd.ID_HopDong "
                + "JOIN `transaction` t ON hd.ID_HoaDon = t.ID_HoaDon "
                + "WHERE t.ID_Transaction = ? AND t.isActive = 1";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idTransaction);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    room = new Phong();
                    room.setID_Phong(rs.getInt("ID_Phong"));
                    room.setTenPhongTro(rs.getString("TenPhongTro"));
                    room.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                    room.setTang(rs.getInt("Tang"));
                    room.setDien_tich(rs.getFloat("Dien_Tich"));
                    room.setGia(rs.getInt("Gia"));
                    room.setTrang_thai(rs.getString("Trang_thai"));
                    room.setID_NhaTro(rs.getInt("ID_NhaTro"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return room;
    }

    public boolean addTransaction(Transaction transaction) {
        String insertQuery = "INSERT INTO `transaction` (MaGiaoDich, TransactionDate, Amount, PaymentMethod, ID_HoaDon, isActive, MoTa) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String updateBillQuery = "UPDATE hoa_don SET Trang_thai = ? WHERE ID_HoaDon = ?"; // Cập nhật trạng thái hóa đơn

        try (PreparedStatement insertPs = connection.prepareStatement(insertQuery)) {
            insertPs.setString(1, transaction.getMaGiaoDich());
            insertPs.setTimestamp(2, new Timestamp(transaction.getTransaction().getTime())); // Chuyển đổi từ Date sang Timestamp
            insertPs.setFloat(3, transaction.getAmount());
            insertPs.setString(4, transaction.getPaymentMethod());
            insertPs.setInt(5, transaction.getID_HoaDon());
            insertPs.setBoolean(6, true); // Đánh dấu là active
            insertPs.setNString(7, transaction.getMoTa());

            int rowsInserted = insertPs.executeUpdate();
            if (rowsInserted > 0) { // Nếu thêm giao dịch thành công
                // Lấy tổng tiền hiện tại của hóa đơn
                float totalMoney = getTotalMoneyByIdHoaDon(transaction.getID_HoaDon());

                // Lấy số tiền phải thanh toán của hóa đơn
                float requiredAmount = getRequiredAmountByIdHoaDon(transaction.getID_HoaDon());

                // Kiểm tra nếu tổng tiền bằng với số tiền phải thanh toán
                if (totalMoney >= requiredAmount) {
                    try (PreparedStatement updatePs = connection.prepareStatement(updateBillQuery)) {
                        updatePs.setInt(1, 1); // Giả sử trạng thái 1 là "Đã thanh toán"
                        updatePs.setInt(2, transaction.getID_HoaDon());
                        updatePs.executeUpdate();
                    }
                }
                return true; // Trả về true nếu thêm giao dịch thành công
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi xảy ra
    }

// Phương thức để lấy số tiền phải thanh toán của hóa đơn
    private float getRequiredAmountByIdHoaDon(int idHoaDon) {
        float requiredAmount = 0;
        String query = "SELECT Tong_gia_tien FROM hoa_don WHERE ID_HoaDon = ?"; // Sử dụng cột Tong_gia_tien
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idHoaDon);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    requiredAmount = rs.getFloat("Tong_gia_tien");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requiredAmount;
    }

    public boolean deactivateTransaction(int idTransaction) {
        // Lấy ID hóa đơn từ giao dịch
        int idHoaDon = getIdHoaDonByTransactionId(idTransaction); // Phương thức này cần được thực hiện

        // Lấy số tiền cần phải thanh toán
        float requiredAmount = getRequiredAmountByIdHoaDon(idHoaDon);

       

        boolean isDeactivated = false;

        // Hủy giao dịch
        String query = "UPDATE `transaction` SET isActive = 0 WHERE ID_Transaction = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idTransaction);
            int rowsUpdated = ps.executeUpdate();
            isDeactivated = rowsUpdated > 0; // Kiểm tra xem có cập nhật thành công không
        } catch (SQLException e) {
            e.printStackTrace();
        }
         // Lấy tổng tiền đã thanh toán
        float totalMoney = getTotalMoneyByIdHoaDon(idHoaDon);
System.out.println("Required Amount: " + requiredAmount);
System.out.println("Total Money: " + totalMoney);
        // Kiểm tra điều kiện để cập nhật trạng thái hóa đơn
        if (isDeactivated && totalMoney < requiredAmount) {
            String updateBillQuery = "UPDATE hoa_don SET Trang_thai = ? WHERE ID_HoaDon = ?";
            try (PreparedStatement ps = connection.prepareStatement(updateBillQuery)) {
                ps.setInt(1, 0); // Giả sử 0 là trạng thái không còn hiệu lực
                ps.setInt(2, idHoaDon);
                int rowsUpdated = ps.executeUpdate(); 
                if (rowsUpdated > 0) {
            System.out.println("Bill status updated successfully.");
        } else {
            System.out.println("Failed to update bill status.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        }

        return isDeactivated; // Trả về true nếu đã hủy giao dịch thành công
    }

    private int getIdHoaDonByTransactionId(int idTransaction) {
        int idHoaDon = 0; // Khởi tạo ID hóa đơn
        String query = "SELECT ID_HoaDon FROM `transaction` WHERE ID_Transaction = ?"; // Truy vấn lấy ID hóa đơn
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idTransaction);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idHoaDon = rs.getInt("ID_HoaDon");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idHoaDon; // Trả về ID hóa đơn
    }

}
