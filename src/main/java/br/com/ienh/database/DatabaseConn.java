package br.com.ienh.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConn {
    private Connection conn;
    private static DatabaseConn db;

    private DatabaseConn() throws Exception {
        String url = "jdbc:mysql://localhost:3306/my_only_server?serverTimezone=UTC";
        String usuario = "root";
        String senha = "root";

        // Carrega o driver do MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Estabelece conexão com o banco
        conn = DriverManager.getConnection(url, usuario, senha);
    }

    public static DatabaseConn getDatabaseConnection() throws Exception{
        if(db == null) db = new DatabaseConn();
        return db;
    }

    public Connection getConnection(){
        return conn;
    }
}