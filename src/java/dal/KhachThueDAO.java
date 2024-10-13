package dal;

import model.KhachThue;
import model.Account;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class KhachThueDAO extends DBContext{
    
    public KhachThue getKhachThueByAccountId(int id) {
        KhachThue khachThue = null;
        AccountDAO accountDAO = new AccountDAO();
        String sql = "select * from khach_thue where ID_Account = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                khachThue = new KhachThue();
                khachThue.setId(rs.getInt("ID_KhachThue"));
                //khachThue.setName(rs.getNString("TenKhachThue"));
                khachThue.setDob(rs.getDate("Ngay_sinh"));
                khachThue.setPhone(rs.getNString("SDT"));
                khachThue.setCccd(rs.getNString("CCCD"));
                khachThue.setJob(rs.getNString("Nghe_nghiep"));
                khachThue.setAddress(rs.getNString("HK_thuong_tru"));
                Account account = accountDAO.getAccountById2(rs.getInt("ID_Account"));
                khachThue.setAccount(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return khachThue;
    }
}
