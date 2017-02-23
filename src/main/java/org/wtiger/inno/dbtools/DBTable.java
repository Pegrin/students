package org.wtiger.inno.dbtools;

import org.wtiger.inno.models.tables.TTable;
import org.wtiger.inno.models.tables.rows.TableRow;

import java.sql.*;
import java.util.ArrayDeque;

abstract public class DBTable<T extends TTable<TR>, TR extends TableRow> {
    protected Connection connection;
    protected String tName;

    DBTable(Connection con, String tName) {
        connection = con;
        this.tName = tName;
    }

    public ResultSet getRows() throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery("SELECT * FROM " + tName);
    }

    public int deleteAll() throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeUpdate("DELETE FROM " + tName);
    }

    public void loadObjectsToBase(T t) throws SQLException {
        connection.setAutoCommit(false);
        if (t.getListOfRows().size() == 0) return;
        PreparedStatement q = getFullInsertStatement();
        for (TR tr :
                t.getListOfRows()) {
            setParamsForFullInsertStatement(tr, q);
            q.addBatch();
        }
        q.executeBatch();
        connection.commit();
        connection.setAutoCommit(true);
    }

    abstract public ResultSet getRowByID(String id) throws SQLException;

    abstract public TR getObjectFromRS(ResultSet resultSet) throws SQLException;

    public void setObjects(T t, PreparedStatement q) throws SQLException{
        t.setListOfRows(new ArrayDeque<>());
        ResultSet resultSet = getRows();
        while (resultSet.next()) {
            TR tr = getObjectFromRS(resultSet);
            t.getListOfRows().add(tr);
        }
    };

    abstract public void setParamsForFullInsertStatement(TR tr, PreparedStatement q) throws SQLException;

    abstract public PreparedStatement getFullInsertStatement() throws SQLException;
}
