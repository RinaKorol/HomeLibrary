package com.example.labwithbase;

import java.sql.*;


public class DataLoader {
    private static final String url = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String user = "sys as sysdba";
    private static final String password = "UaBS$6dc";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public DataLoader(){

    }

    public ResultSet getRS(String query){

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();

            // executing SELECT query
            rs = stmt.executeQuery(query);

            return rs;
            /* while (rs.next()) {
                int count = rs.getInt(1);
                String name = rs.getString(2);
                System.out.println(count +" "+ name);
            } */

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
       /* } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { }
            try { stmt.close(); } catch(SQLException se) {  }
            try { rs.close(); } catch(SQLException se) {  }
        } */
            return null;
        }
    }

}
