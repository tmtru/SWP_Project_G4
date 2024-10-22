/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Account;
import model.ChuTro;
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

    public boolean insertQuanLy(int ID_Account, String TenQuanLy, Date Ngay_sinh, String SDT, String CCCD, int Id_NhaTro) {
        String sql = "INSERT INTO quan_ly (TenQuanLy, Ngay_sinh, SDT, CCCD, ID_Account, ID_NhaTro, isActive) VALUES (?, ?, ?, ?, ?, ?, 1)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Set the values for the SQL insert statement
            ps.setInt(5, ID_Account);  // Account ID
            ps.setNString(1, TenQuanLy);                 // Manager Name
            ps.setDate(2, new java.sql.Date(Ngay_sinh.getTime()));  // Date of Birth (convert java.util.Date to java.sql.Date)
            ps.setNString(3, SDT);                // Phone Number
            ps.setNString(4, CCCD);                 // CCCD (ID)
            ps.setInt(6, Id_NhaTro);
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

    public static void main(String[] args) {
        QuanLyDAO quanLyDAO = new QuanLyDAO();
        String dobStr = "2004-10-10";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dob = null;
        try {
            dob = dateFormat.parse(dobStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        quanLyDAO.insertQuanLy(24, "hi", dob, "0320482323", "2342343243", 2);
    }
}
