/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.AnhNhaTro;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import model.DichVu;

/**
 *
 * @author Admin
 */
public class AnhNhaTroDAO extends DBContext {

    public ArrayList<AnhNhaTro> getAllAnhByNhaTroId(int idNhaTro) {
        ArrayList<AnhNhaTro> anhNhaTroList = new ArrayList<>();
        String query = "SELECT ID_AnhPhong, URL_AnhNhaTro FROM ANH_NHA_TRO WHERE ID_NhaTro = ?";

        // Sử dụng try-with-resources để tự động đóng tài nguyên
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idNhaTro);
            try (ResultSet rs = stmt.executeQuery()) {
                // Tạo một danh sách tạm để chứa các URL
                ArrayList<String> urlList = new ArrayList<>();
                int idAnhNhaTro = -1; // Lưu trữ ID Anh hiện tại

                // Duyệt qua tất cả các kết quả
                while (rs.next()) {
                    if (idAnhNhaTro == -1) {
                        // Lấy ID Anh chỉ một lần vì tất cả đều thuộc về cùng một ID_NhaTro
                        idAnhNhaTro = rs.getInt("ID_AnhPhong");
                    }

                    // Thêm từng URL vào danh sách
                    String url = rs.getString("URL_AnhNhaTro");
                    urlList.add(url);
                }

                // Tạo đối tượng AnhNhaTro và thêm vào danh sách nếu có dữ liệu
                if (!urlList.isEmpty()) {
                    AnhNhaTro anhNhaTro = new AnhNhaTro(idAnhNhaTro, urlList, idNhaTro);
                    anhNhaTroList.add(anhNhaTro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return anhNhaTroList;
    }

    public static void main(String[] args) {
        AnhNhaTroDAO dao = new AnhNhaTroDAO();
        int idNhaTro = 1;  // ID nhà trọ bạn muốn lấy ảnh

        // Gọi phương thức để lấy danh sách ảnh
        List<AnhNhaTro> anhNhaTroList = dao.getAllAnhByNhaTroId(idNhaTro);

        // Hiển thị thông tin
        if (!anhNhaTroList.isEmpty()) {
            for (AnhNhaTro anh : anhNhaTroList) {
                System.out.println("ID Anh: " + anh.getID_AnhNhaTro());
                System.out.println("URL Các ảnh: " + anh.getURL_AnhNhaTro());
                System.out.println("ID Nha Tro: " + anh.getID_NhaTro());
                System.out.println("---------------");
            }
        } else {
            System.out.println("Không tìm thấy ảnh cho nhà trọ với ID: " + idNhaTro);
        }
    }
}
