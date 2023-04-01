package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DBUtils {

    public static List<Map<String, String >> getTableData(String query){

        Connection connection = null;
//      variable to hold the conection
        Statement statment = null;
//      helps us execute the queries on the database
        ResultSet result = null;
//      helps us store the result
        ResultSetMetaData resultSetMetaData = null;
//      object to give the information about table and its data

        List<Map<String, String>> tableData = null;

        try {       // needs to start from 1

            connection =  DriverManager.getConnection(ConfigReader.getPropertyValue("dBUrl"),
                          ConfigReader.getPropertyValue("userName"),
                          ConfigReader.getPropertyValue("dBpassword"));
//          getting the database connection from driverManager class
            statment = connection.createStatement();
//          creating a statement to execute the queries
            result = statment.executeQuery(query);
//          executing the queries and storing the results in ResultSet
            resultSetMetaData=result.getMetaData();
//          getting the resultSet object so that we fetch the column names and other info related to table
            tableData = new ArrayList<>();
            while (result.next()) {
                Map<String, String> row =  new LinkedHashMap<>();
                for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                    // gets the column name passes it to the getString method to get the data for that column using loops
                    row.put(resultSetMetaData.getColumnName(i),result.getString(resultSetMetaData.getColumnName(i)));
                }
                tableData.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeResultSet(result);
            DBUtils.closeStatement(statment);
            DBUtils.closeConnection(connection);
        }
        return tableData;
    }

    public static void closeResultSet(ResultSet result) {
        if(result !=null){
            try {
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void closeStatement(Statement statement) {
        if(statement !=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void closeConnection(Connection connection) {
        if(connection !=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

