/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.LoaiPhong;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author hihihihaha
 */
public class LoaiPhongDAO extends DBContext {

    public List<LoaiPhong> getAllLoaiPhong() {
        List<LoaiPhong> list = new ArrayList<>();
        String sql = "SELECT * FROM loai_phong";
        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                LoaiPhong l = new LoaiPhong();
                l.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                l.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
                list.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LoaiPhong> getAvailableLoaiPhong() {
        List<LoaiPhong> loaiPhongList = new ArrayList<>();
        String sql = "SELECT DISTINCT ID_LoaiPhong, TenLoaiPhong, Mo_ta FROM loai_phong; ORDER BY TenLoaiPhong"; 

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                LoaiPhong n = new LoaiPhong();
                n.setID_LoaiPhong(rs.getInt("ID_LoaiPhong"));
                n.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
                n.setMo_ta(rs.getString("Mo_ta"));
                loaiPhongList.add(n);
            }
        } catch (SQLException e) {
            System.out.println("Error in LoaiPhongDAO.getAvailableLoaiPhong: " + e.getMessage());
        }

        return loaiPhongList;
    }
}
