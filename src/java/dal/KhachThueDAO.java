/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import model.Account;
import model.KhachThue;
import model.NhaTro;

/**
 *
 * @author ADMIN
 */
public class KhachThueDAO extends DBContext {

    public List<KhachThue> getKhachThueByNhaTro(int id) {
        KhachThue khachThue = null;
        List<KhachThue> list = new ArrayList<>();
        AccountDAO accountDAO = new AccountDAO();
        String sql = "SELECT \n"
                + "   \n"
                + "   \n"
                + "   khach_thue.*,\n"
                + "    phong_tro.TenPhongTro AS RoomName,\n"
                + "    nha_tro.TenNhaTro AS BuildingName\n"
                + "FROM phong_tro\n"
                + "\n"
                + "JOIN nha_tro ON phong_tro.ID_NhaTro = nha_tro.ID_NhaTro\n"
                + "\n"
                + "\n"
                + " JOIN hop_dong ON hop_dong.ID_PhongTro = phong_tro.ID_Phong\n"
                + " JOIN khach_thue ON khach_thue.ID_KhachThue = hop_dong.ID_KhachThue\n"
                + "WHERE nha_tro.ID_NhaTro = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                khachThue = new KhachThue();
                khachThue.setId(rs.getInt("ID_KhachThue"));
                khachThue.setName(rs.getNString("ten_khach"));
                khachThue.setDob(rs.getDate("Ngay_sinh"));
                khachThue.setPhone(rs.getNString("SDT"));
                Account account = accountDAO.getAccountById2(rs.getInt("ID_Account"));
                khachThue.setCccd(rs.getNString("CCCD"));
                khachThue.setHk_thuong_tru(rs.getNString("HK_thuong_tru"));
                khachThue.setJob(rs.getNString("Nghe_nghiep"));
                khachThue.setRoomName(rs.getNString("RoomName"));
                khachThue.setAccount(account);
                list.add(khachThue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<KhachThue> Paging(List<KhachThue> khachThues, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, khachThues.size());

        if (fromIndex > toIndex) {
            // Handle the case where fromIndex is greater than toIndex
            fromIndex = toIndex;
        }

        return khachThues.subList(fromIndex, toIndex);
    }
    public static void main(String[] args) {
        
       KhachThueDAO khachThueDAO = new KhachThueDAO();
       List<KhachThue> list = khachThueDAO.getKhachThueByNhaTro(1);
        for (KhachThue khachThue : list) {
            System.out.println(khachThue);
        }
    }
}
