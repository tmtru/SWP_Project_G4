/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.RoomType;

/**
 *
 * @author ADMIN
 */
public class RoomTypeDAO extends DBContext {

    public List<RoomType> getRopmTypes() {
        String sql = "SELECT * FROM loai_phong";
        List<RoomType> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                RoomType roomType = new RoomType();

                roomType.setId(rs.getInt("ID_LoaiPhong"));
                roomType.setName(rs.getString("TenLoaiPhong"));
                roomType.setDescription(rs.getString("Mo_ta"));
                list.add(roomType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list; // Tráº£ vá» null náº¿u khÃ´ng tÃ¬m tháº¥y tÃ i khoáº£n
    }
}
