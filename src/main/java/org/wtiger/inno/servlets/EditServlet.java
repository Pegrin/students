package org.wtiger.inno.servlets;

import org.wtiger.inno.dbtools.DBUsers;
import org.wtiger.inno.models.tables.rows.TRUsers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by olymp on 23.02.2017.
 */

@WebServlet("/edit")
public class EditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection con = getConPostgres("localhost", "5432",
                "litportal", "postgres")) {
            DBUsers dbUsers = new DBUsers(con);
            String uuid = req.getParameter("uuid");
            ResultSet set = dbUsers.getRowByID(uuid);
            if (set.next()){
                TRUsers user = dbUsers.getObjectFromRS(set);
                req.setAttribute("uuid", user.getUser_uuid());
                req.setAttribute("login", user.getLogin());
                req.setAttribute("email", user.getEmail());
                req.setAttribute("password", user.getPassword());
                req.setAttribute("role", user.getRole());
                req.setAttribute("visible_name", user.getVisible_name());
            }
            req.getRequestDispatcher("/edit.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConPostgres(String host, String port, String db, String user) throws SQLException {
        String password = "hrenasword";
        return DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + db + "",
                user, password);
    }

}
