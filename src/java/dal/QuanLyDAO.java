/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Account;
import model.ChuTro;
import model.LichGhiChu;
import model.QuanLy;

/**
 *
 * @author ADMIN
 */
public class QuanLyDAO extends DBContext {

    public QuanLy getQuanLyId(int id) {
        QuanLy quanLy = null;
        AccountDAO accountDAO = new AccountDAO();
        String sql = "select * from quan_ly where ID_QuanLy = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                quanLy = new QuanLy();
                quanLy.setId(rs.getInt("ID_QuanLy"));
                quanLy.setName(rs.getNString("TenQuanLy"));
                quanLy.setDob(rs.getDate("Ngay_sinh"));
                quanLy.setPhone(rs.getNString("SDT"));
                quanLy.setCccd(rs.getNString("CCCD"));
                Account account = accountDAO.getAccountById2(rs.getInt("ID_Account"));
                quanLy.setAccount(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quanLy;
    }

    public QuanLy getChuTroByAccountId(int id) {
        QuanLy quanLy = null;
        AccountDAO accountDAO = new AccountDAO();
        String sql = "select * from quan_ly where ID_Account = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                quanLy = new QuanLy();
                quanLy.setId(rs.getInt("ID_QuanLy"));
                quanLy.setName(rs.getNString("TenQuanLy"));
                quanLy.setDob(rs.getDate("Ngay_sinh"));
                quanLy.setPhone(rs.getNString("SDT"));
                quanLy.setCccd(rs.getNString("CCCD"));
                quanLy.setId_nhaTro(rs.getInt("ID_NhaTro"));
                Account account = accountDAO.getAccountById2(rs.getInt("ID_Account"));
                quanLy.setAccount(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quanLy;
    }

    public List<QuanLy> getQuanLyByNhaTro(int id) {
        List<QuanLy> list = new ArrayList<>();
        QuanLy quanLy = null;
        AccountDAO accountDAO = new AccountDAO();
        String sql = "select q.* from quan_ly q JOIN nha_tro n\n"
                + "ON q.ID_NhaTro = n.ID_NhaTro\n"
                + "where q.ID_NhaTro =  ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                quanLy = new QuanLy();
                quanLy.setId(rs.getInt("ID_QuanLy"));
                quanLy.setName(rs.getNString("TenQuanLy"));
                quanLy.setDob(rs.getDate("Ngay_sinh"));
                quanLy.setPhone(rs.getNString("SDT"));
                quanLy.setCccd(rs.getNString("CCCD"));
                Account account = accountDAO.getAccountById2(rs.getInt("ID_Account"));
                quanLy.setAccount(account);
                list.add(quanLy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertQuanLy(QuanLy quanLy) {
        String sql = "INSERT INTO quan_ly (ID_Account, ID_NhaTro, TenQuanLy, Ngay_sinh, SDT, CCCD) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Set the values for the SQL insert statement
            ps.setInt(1, quanLy.getAccount().getID_Account());  // Account ID
            ps.setInt(2, quanLy.getId_nhaTro());                // NhaTro ID
            ps.setNString(3, quanLy.getName());                 // Manager Name
            ps.setDate(4, new java.sql.Date(quanLy.getDob().getTime()));  // Date of Birth (convert java.util.Date to java.sql.Date)
            ps.setNString(5, quanLy.getPhone());                // Phone Number
            ps.setNString(6, quanLy.getCccd());                 // CCCD (ID)

            // Execute the insert query
            return ps.executeUpdate() > 0;  // Returns true if at least one row is affected
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Return false if there is an error
        }
    }

    public boolean updateQuanLy(QuanLy quanLy) {
        String sql = "update quan_ly set id_nhatro = ? where ID_QuanLy = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Set the values for the SQL insert statement
            ps.setInt(1, quanLy.getId_nhaTro());  // Account ID
            ps.setInt(2, quanLy.getId());  // Account ID
            return ps.executeUpdate() > 0;  // Returns true if at least one row is affected
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Return false if there is an error
        }
    }

    public boolean deleteQuanLy(int quanLyId) {
        String sql = "update quan_ly set id_nhatro = null where ID_QuanLy = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quanLyId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;  // Return true if deletion was successful
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Return false if there was an error
        }
    }

    public Integer getIDQuanLyByIDAccount(int idAccount) {
        String sql = "SELECT ID_QuanLy FROM quan_ly WHERE ID_Account = ?";
        Integer idQuanLy = null; 

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idAccount); 
            ResultSet resultSet = statement.executeQuery(); 

            if (resultSet.next()) {
                idQuanLy = resultSet.getInt("ID_QuanLy"); 
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }

        return idQuanLy; 
    }
    
    public boolean saveNote(String date, String note, int idQuanLy) {
        String sql = "INSERT INTO lich_ghi_chu (GhiChu, Ngay, ID_QuanLy, Status) VALUES (?, ?, ?, 1)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, note);
            statement.setString(2, date); // Giữ nguyên kiểu String cho ngày
            statement.setInt(3, idQuanLy);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0; // Trả về true nếu có dòng nào được chèn
        } catch (Exception e) {
            e.printStackTrace(); // In ra lỗi SQL nếu có
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }
    
    public List<LichGhiChu> getGhiChuByIdQuanLy(int idQuanLy) {
        List<LichGhiChu> ghiChuList = new ArrayList<>();
        String sql = "SELECT ID_GhiChu, GhiChu, Ngay, ID_QuanLy, Status FROM lich_ghi_chu WHERE ID_QuanLy = ? AND Status = 1";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idQuanLy);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idGhiChu = resultSet.getInt("ID_GhiChu");
                String ghiChu = resultSet.getString("GhiChu");
                Date ngay = resultSet.getDate("Ngay");
                int status = resultSet.getInt("Status");

                LichGhiChu note = new LichGhiChu(idGhiChu, ghiChu, ngay, idQuanLy, status);
                ghiChuList.add(note);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ghiChuList;
    }

    public static void main(String[] args) {
        QuanLyDAO quanLyDAO = new QuanLyDAO();
        List<QuanLy> list = quanLyDAO.getQuanLyByNhaTro(1);

        for (QuanLy quanLy : list) {
            System.out.println(quanLy);
        }
    }

}
