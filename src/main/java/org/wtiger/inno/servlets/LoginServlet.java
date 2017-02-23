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
import java.sql.SQLException;

/**
 * Created by olymp on 23.02.2017.
 */


@WebServlet("/")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        boolean auth = false;
        try (Connection con = getConPostgres("localhost", "5432",
                "litportal", "postgres")) {
            DBUsers dbUsers = new DBUsers(con);
            TRUsers user = dbUsers.getObjectByLogin(login.toLowerCase());
            auth = (user != null && user.getPassword().equals(password));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (auth) {
            resp.sendRedirect("/list");
        } else {

            req.setAttribute("auth", false);
            req.setAttribute("login", login);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);;
        }
    }

    public static Connection getConPostgres(String host, String port, String db, String user) throws SQLException {
        String password = "hrenasword";
        return DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + db + "",
                user, password);
    }
}
