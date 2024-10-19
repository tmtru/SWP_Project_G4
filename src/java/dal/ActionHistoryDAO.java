/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.ActionHistory;
import model.NhaTro;
import model.QuanLy;

/**
 *
 * @author ADMIN
 */
public class ActionHistoryDAO extends DBContext {

    public List<ActionHistory> getAllActionWithParam(String searchParam, Integer id_nhatro, Integer id_quanLy) {
        List<ActionHistory> listAction = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        NhaTroDAO ntdao = new NhaTroDAO();
        QuanLyDAO quanLyDAO = new QuanLyDAO();
        try {
            StringBuilder query = new StringBuilder();
            query.append("select * from action_history ")
                    .append("WHERE 1 = 1 ");

            if (searchParam != null && !searchParam.trim().isEmpty()) {
                query.append(" AND title LIKE ? ");
                list.add("%" + searchParam + "%");
            }

            if (id_nhatro != null) {
                query.append(" AND Id_nhaTro = ? ");
                list.add(id_nhatro);
            }
            if (id_quanLy != null) {
                query.append(" AND ID_QuanLy = ? ");
                list.add(id_quanLy);
            }

            query.append("ORDER BY TransactionDate DESC");

            PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
            mapParams(preparedStatement, list);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    ActionHistory actionHistory = new ActionHistory();
                    actionHistory.setId(rs.getInt("id"));
                    QuanLy quanLy = quanLyDAO.getQuanLyId(rs.getInt("ID_QuanLy"));
                    actionHistory.setQuanLy(quanLy);
                    NhaTro nhaTro = ntdao.getNhaTroById(rs.getInt("ID_nhaTro"));
                    actionHistory.setTitle(rs.getString("title"));
                    actionHistory.setContent(rs.getString("content"));
                    actionHistory.setNhaTro(nhaTro);
                    actionHistory.setCreatedDate(rs.getTimestamp("TransactionDate"));

                    listAction.add(actionHistory);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listAction;
    }

    public boolean insertActionHistory(ActionHistory actionHistory) {
        String sql = "INSERT INTO action_history (ID_QuanLy, Id_nhaTro, title, content) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Set the values for the SQL insert statement
            ps.setInt(1, actionHistory.getQuanLy().getId());  // Manager ID
            ps.setInt(2, actionHistory.getNhaTro().getID_NhaTro());  // NhaTro ID
            ps.setString(3, actionHistory.getTitle());  // Action title
            ps.setString(4, actionHistory.getContent());  // Action content

            // Execute the insert query
            return ps.executeUpdate() > 0;  // Returns true if at least one row is affected
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Return false if there is an error
        }
    }

    public List<ActionHistory> Paging(List<ActionHistory> actionHistorys, int page, int pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, actionHistorys.size());

        if (fromIndex > toIndex) {
            // Handle the case where fromIndex is greater than toIndex
            fromIndex = toIndex;
        }

        return actionHistorys.subList(fromIndex, toIndex);
    }

    public void mapParams(PreparedStatement ps, List<Object> args) throws SQLException {
        int i = 1;
        for (Object arg : args) {
            if (arg instanceof Date) {
                ps.setTimestamp(i++, new Timestamp(((Date) arg).getTime()));
            } else if (arg instanceof Integer) {
                ps.setInt(i++, (Integer) arg);
            } else if (arg instanceof Long) {
                ps.setLong(i++, (Long) arg);
            } else if (arg instanceof Double) {
                ps.setDouble(i++, (Double) arg);
            } else if (arg instanceof Float) {
                ps.setFloat(i++, (Float) arg);
            } else {
                ps.setString(i++, (String) arg);
            }

        }
    }

    public static void main(String[] args) {
        ActionHistoryDAO a = new ActionHistoryDAO();
        List<ActionHistory> list = a.getAllActionWithParam(null, null, null);
        List<ActionHistory> list2 = a.Paging(list, 1, 1);
        for (ActionHistory actionHistory : list2) {
            System.out.println(actionHistory);
        }
    }
}
