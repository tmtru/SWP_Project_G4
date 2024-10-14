/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.ChuTro;

/**
 *
 * @author ADMIN
 */
public class ChuTroDAO extends DBContext {

    public List<ChuTro> getAllChuTro() {
        List<ChuTro> chuTroList = new ArrayList<>();
        AccountDAO accountDAO = new AccountDAO();
        String sql = "SELECT * FROM chu_tro";

        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChuTro chuTro = new ChuTro();
                chuTro.setId(rs.getInt("ID_ChuTro"));
                chuTro.setName(rs.getNString("TenChuTro"));
                chuTro.setDob(rs.getDate("Ngay_sinh"));
                chuTro.setPhone(rs.getNString("SDT"));
                chuTro.setCccd(rs.getNString("CCCD"));
                Account account = accountDAO.getAccountById2(rs.getInt("ID_Account"));
                chuTro.setAccount(account);

                chuTroList.add(chuTro);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chuTroList;
    }

    public ChuTro getChuTroById(int id) {
        ChuTro chuTro = null;
        AccountDAO accountDAO = new AccountDAO();
        String sql = "select * from chu_tro where ID_ChuTro = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                chuTro = new ChuTro();
                chuTro.setId(rs.getInt("ID_ChuTro"));
                chuTro.setName(rs.getNString("TenChuTro"));
                chuTro.setDob(rs.getDate("Ngay_sinh"));
                chuTro.setPhone(rs.getNString("SDT"));
                chuTro.setCccd(rs.getNString("CCCD"));
                Account account = accountDAO.getAccountById2(rs.getInt("ID_Account"));
                chuTro.setAccount(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chuTro;
    }

    public ChuTro getChuTroByAccountId(int id) {
        ChuTro chuTro = null;
        AccountDAO accountDAO = new AccountDAO();
        String sql = "select * from chu_tro where ID_Account = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                chuTro = new ChuTro();
                chuTro.setId(rs.getInt("ID_ChuTro"));
                chuTro.setName(rs.getNString("TenChuTro"));
                chuTro.setDob(rs.getDate("Ngay_sinh"));
                chuTro.setPhone(rs.getNString("SDT"));
                chuTro.setCccd(rs.getNString("CCCD"));
                Account account = accountDAO.getAccountById2(rs.getInt("ID_Account"));
                chuTro.setAccount(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chuTro;
    }
}
