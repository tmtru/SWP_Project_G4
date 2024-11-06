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
import model.*;

/**
 *
 * @author ADMIN
 */
public class NewDAO extends DBContext {

    public List<New> getAllNews(int cId) {
        List<New> news = new ArrayList<>();
        AccountDAO accountDAO = new AccountDAO();
        NhaTroDAO nhaTroDAO = new NhaTroDAO();
        String sql = "SELECT * FROM new where creator = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, cId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                New n = new New();
                n.setId(rs.getInt("id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setCreated_in(rs.getTimestamp("created_in"));
                NhaTro nt = nhaTroDAO.getNhaTroById2(rs.getInt("id_nhatro"));
                n.setNhaTro(nt);
                Account account = accountDAO.getAccountById2(rs.getInt("creator"));
                n.setCreator(account);

                news.add(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return news;
    }

    public New getNewByID(int id) {

        AccountDAO accountDAO = new AccountDAO();
        NhaTroDAO nhaTroDAO = new NhaTroDAO();
        String sql = "select * from new where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                New n = new New();
                n.setId(rs.getInt("id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setCreated_in(rs.getTimestamp("created_in"));
                NhaTro nt = nhaTroDAO.getNhaTroById2(rs.getInt("id_nhatro"));
                n.setNhaTro(nt);
                Account account = accountDAO.getAccountById2(rs.getInt("creator"));
                n.setCreator(account);
                return n;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<New> getNewByNhaTro(int id_nhatro) {
        List<New> list = new ArrayList<>();
        AccountDAO accountDAO = new AccountDAO();
        NhaTroDAO nhaTroDAO = new NhaTroDAO();
        String sql = "select * from new where id_nhatro = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, id_nhatro);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                New n = new New();
                n.setId(rs.getInt("id"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setCreated_in(rs.getTimestamp("created_in"));
                NhaTro nt = nhaTroDAO.getNhaTroById2(rs.getInt("id_nhatro"));
                n.setNhaTro(nt);
                Account account = accountDAO.getAccountById2(rs.getInt("creator"));
                n.setCreator(account);
                list.add(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<New> Paging(List<New> news, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, news.size());

        if (fromIndex > toIndex) {
            fromIndex = toIndex;
        }

        return news.subList(fromIndex, toIndex);
    }

    public void insertNew(New newRecord) {
        String sql = "INSERT INTO new (title, content,  id_nhatro, creator) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newRecord.getTitle());
            ps.setString(2, newRecord.getContent()); // Insert formatted content as HTML
            ps.setInt(3, newRecord.getNhaTro().getID_NhaTro());
            ps.setInt(4, newRecord.getCreator().getID_Account());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NewDAO ndao = new NewDAO();
        System.out.println(ndao.getNewByNhaTro(1).size());
    }

}
