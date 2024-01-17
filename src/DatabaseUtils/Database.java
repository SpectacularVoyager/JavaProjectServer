package DatabaseUtils;

import java.sql.*;

public class Database {
    String database = "MessagingApp";
    static int port = 3306;
    static String User = "root";
    static String Password = "pass";
    private Connection con;
    private static Database _database;
    public static Database getDatabase(){
        if(_database==null){
            _database=new Database();
        }
        return _database;
    }
    private Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/" + database, User, Password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public PreparedStatement getPreparedStatement(String s) throws SQLException {
        return con.prepareStatement(s);
    }

    public Statement getStatement() throws SQLException {
        return con.createStatement();
    }

    public void commit() throws SQLException {
        System.out.println("COMMITTING");
        con.commit();
    }

    public void rollback() throws SQLException {
        System.out.println("ROLLING BACK");
        con.rollback();
    }

    public void setAutoCommit(boolean val) throws SQLException {
        con.setAutoCommit(val);
    }

    public boolean getAutoCommit() throws SQLException {
        return con.getAutoCommit();
    }

    public void close() {
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void flushPrivileges() throws SQLException {
        con.createStatement().execute("flush privileges");
    }

    public DatabaseMetaData getDatabaseMetaData() throws SQLException {
        return con.getMetaData();
    }
    public ResultSet executeQuery(String query,Object...args) throws SQLException {
        PreparedStatement ps=getPreparedStatement(query);
        for (int i = 0; i < args.length; i++) {
            ps.setString(i+1,args[i].toString());
        }
        return ps.executeQuery();
    }
    public int executeUpdate(String query, Object...args) throws SQLException {
        PreparedStatement ps=getPreparedStatement(query);
        for (int i = 0; i < args.length; i++) {
            ps.setString(i+1,args[i].toString());
        }
        return ps.executeUpdate();
    }
}
