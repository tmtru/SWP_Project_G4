/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.List;
import model.NhaTro;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import model.Account;
import model.QuanLi;

/**
 *
 * @author Admin
 */
public class QuanLiDAO extends DBContext {
    // Phương thức lấy danh sách nhà trọ của một quản lý theo account quan li do so huu

    public List<NhaTro> getNhaTroByManagerId(int managerId) {
        List<NhaTro> nhaTroList = new ArrayList<>();
        String query = "SELECT * FROM quanlynhatrodb.quan_ly as ql\n"
                + "Left join quanlynhatrodb.nha_tro as nt on ql.ID_NhaTro=nt.ID_NhaTro WHERE ql.ID_QuanLy=?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, managerId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                NhaTro nhaTro = new NhaTro();
                nhaTro.setID_NhaTro(rs.getInt("ID_NhaTro"));
                nhaTro.setTenNhaTro(rs.getNString("TenNhaTro"));
                nhaTro.setID_ChuTro(rs.getInt("ID_ChuTro"));  // Chuyển sang getInt
                nhaTro.setDia_chi(rs.getNString("Dia_Chi"));
                nhaTro.setMo_ta(rs.getNString("Mo_ta"));
                nhaTroList.add(nhaTro);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý lỗi
        }

        return nhaTroList; // Trả về danh sách nhà trọ
    }

    //phuong thuc lay quan li theo ID account
    public QuanLi getQuanLiByIDAccount(int idAccount) {
        String query = "SELECT * FROM quanlynhatrodb.quan_ly as ql\n"
                + "WHERE ql.ID_Account=?;";
        QuanLi quanLi = null;
        AccountDAO accdao = new AccountDAO();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Thiết lập giá trị cho tham số trong câu truy vấn
            preparedStatement.setInt(1, idAccount);

            // Thực hiện truy vấn
            ResultSet rs = preparedStatement.executeQuery();

            // Kiểm tra kết quả và tạo đối tượng QuanLi từ kết quả trả về
            if (rs.next()) {
                int idQuanLy = rs.getInt("ID_QuanLy");
                String tenQuanLy = rs.getString("TenQuanLy");
                Date ngaySinh = rs.getDate("Ngay_sinh");
                String sdt = rs.getString("SDT");
                String cccd = rs.getString("CCCD");
                int idNhaTro = rs.getInt("ID_NhaTro");
                Account acc = accdao.getAccountById(idAccount);

                // Tạo đối tượng QuanLi từ dữ liệu đã lấy
                quanLi = new QuanLi(idQuanLy, tenQuanLy, ngaySinh, sdt, cccd, idNhaTro, acc);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý lỗi SQL
        }

        return quanLi;
    }
}
