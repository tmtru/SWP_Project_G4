package dal;


import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.HopDong;
import java.sql.Date;

public class HopDongDAO extends DBContext {
    public List<HopDong> getHopDongsByKhachThueID(int ID_KhachThue) {
        List<HopDong> hopDongs = new ArrayList<>();
        String sql = "SELECT * FROM hop_dong WHERE ID_KhachThue = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
             
            ps.setInt(1, ID_KhachThue);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                HopDong hopDong = new HopDong();
                hopDong.setID_HopDong(rs.getInt("ID_HopDong"));
                hopDong.setID_KhachThue(rs.getInt("ID_KhachThue"));
                hopDong.setID_Phongtro(rs.getInt("ID_Phongtro"));
                hopDong.setNgay_gia_tri(rs.getDate("Ngay_gia_tri"));
                hopDong.setNgay_het_han(rs.getDate("Ngay_het_han"));
                hopDong.setTien_Coc(rs.getInt("Tien_Coc"));
                
                hopDongs.add(hopDong);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Xử lý lỗi
        }
        
        return hopDongs;
    }

    public List<HopDong> findAll(int offset, int noOfRecords) {
        List<HopDong> hopDongList = new ArrayList<>();
        String sql = "SELECT * FROM hop_dong LIMIT ?, ?";

        try {
            Connection conn = new DBContext().connection;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, offset);
            pstmt.setInt(2, noOfRecords);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    HopDong hopDong = mapResultSetToHopDong(rs);
                    hopDongList.add(hopDong);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Consider logging the exception or throwing a custom exception
        }

        return hopDongList;
    }

    public int getNoOfRecords() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total FROM hop_dong";

        try {
            Connection conn = new DBContext().connection;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Consider logging the exception or throwing a custom exception
        }

        return count;
    }

    private HopDong mapResultSetToHopDong(ResultSet rs) throws SQLException {
        return new HopDong(
                rs.getInt("ID_HopDong"),
                rs.getInt("ID_KhachThue"),
                rs.getInt("ID_PhongTro"),
                rs.getDate("Ngay_gia_tri"),
                rs.getDate("Ngay_het_han"),
                rs.getInt("Tien_Coc")
        );
    }
    
     public boolean addHopDong(HopDong hopDong) {
        String sql = "INSERT INTO hop_dong (ID_KhachThue, ID_PhongTro, Ngay_gia_tri, Ngay_het_han, Tien_Coc) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hopDong.getID_KhachThue());
            ps.setInt(2, hopDong.getID_Phongtro());
            ps.setDate(3, hopDong.getNgay_gia_tri());
            ps.setDate(4, hopDong.getNgay_het_han());
            ps.setInt(5, hopDong.getTien_Coc());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public HopDong getHopDongById(int id) {
        String sql = "SELECT * FROM hop_dong WHERE ID_HopDong = ?";
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new HopDong(
                    rs.getInt("ID_HopDong"),
                    rs.getInt("ID_KhachThue"),
                    rs.getInt("ID_PhongTro"),
                    rs.getDate("Ngay_gia_tri"),
                    rs.getDate("Ngay_het_han"),
                    rs.getInt("Tien_Coc")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean updateHopDong(HopDong hopDong) {
        String sql = "UPDATE hop_dong SET ID_KhachThue=?, ID_PhongTro=?, Ngay_gia_tri=?, Ngay_het_han=?, Tien_Coc=? WHERE ID_HopDong=?";
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, hopDong.getID_KhachThue());
            ps.setInt(2, hopDong.getID_Phongtro());
            ps.setDate(3, hopDong.getNgay_gia_tri());
            ps.setDate(4, hopDong.getNgay_het_han());
            ps.setInt(5, hopDong.getTien_Coc());
            ps.setInt(6, hopDong.getID_HopDong());
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean deleteHopDong(int id) {
        String sql = "DELETE FROM hop_dong WHERE ID_HopDong=?";
        try (Connection conn = new DBContext().connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public static void main(String[] args) {
        HopDongDAO hopDongDAO = new HopDongDAO();
        int offset = 0; // Adjust based on your pagination logic
        int noOfRecords = 10; // Number of records you want to retrieve
        
        // Call the findAll method
        List<HopDong> hopDongList = hopDongDAO.findAll(offset, noOfRecords);

        // Check if the list is not empty and print the results
        if (hopDongList != null && !hopDongList.isEmpty()) {
            for (HopDong hopDong : hopDongList) {
                System.out.println(hopDong);  // Assuming HopDong has a meaningful toString() method
            }
        } else {
            System.out.println("No records found.");
        }
    }
}
