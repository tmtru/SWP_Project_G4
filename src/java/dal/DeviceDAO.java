
package dal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Device;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ADMIN
 */
public class DeviceDAO extends DBContext {

    public List<Device> getDevices() {
        String sql = "SELECT * FROM thiet_bi";
        List<Device> list = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Device device = new Device();

                device.setId(rs.getInt("ID_ThietBi"));
                device.setName(rs.getString("TenThietBi"));
                device.setDescription(rs.getString("Mo_ta"));
                device.setQuantity(rs.getInt("So_luong"));
                device.setPrice(rs.getInt("Gia_tien"));
                list.add(device);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list; 
    }
    public static void main(String[] args) {
        DeviceDAO ddao = new DeviceDAO();
        List<Device> list = ddao.getDevices();
        for (Device device : list) {
            System.out.println(device);
        }
    }
}
