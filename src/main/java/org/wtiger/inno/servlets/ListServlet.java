package org.wtiger.inno.servlets;

import org.wtiger.inno.dbtools.DBUsers;
import org.wtiger.inno.models.tables.TUsers;
import org.wtiger.inno.models.tables.rows.TRUsers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * Created by olymp on 23.02.2017.
 */

@WebServlet("/list")
public class ListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        boolean auth = false;
        try (Connection con = getConPostgres("localhost", "5432",
                "litportal", "postgres")) {
            String uuid = req.getParameter("uuid");
            DBUsers dbUsers = new DBUsers(con);
            if (uuid!=null){
                if (req.getParameter("delete")!=null){
                    dbUsers.deleteByID(uuid);
                }else {
                    TRUsers user = new TRUsers();
                    user.setUser_uuid(uuid);
                    user.setLogin(req.getParameter("login"));
                    user.setPassword(req.getParameter("password"));
                    user.setEmail(req.getParameter("email"));
                    user.setRole(Long.parseLong(req.getParameter("role")));
                    user.setVisible_name(req.getParameter("visible_name"));
                    TUsers users = new TUsers();
                    users.setListOfRows(new ArrayDeque<TRUsers>());
                    users.getListOfRows().add(user);
                    dbUsers.loadObjectsToBase(users);
                }
            }
            ResultSet set = dbUsers.getRows();
            ArrayList<TRUsers> arrayList = new ArrayList<>();
            while (set.next()){
                arrayList.add(dbUsers.getObjectFromRS(set));
            }
            req.setAttribute("userlist", arrayList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/list.jsp").forward(req, resp);
    }

    public static Connection getConPostgres(String host, String port, String db, String user) throws SQLException {
        String password = "hrenasword";
        return DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + db + "",
                user, password);
    }
}
