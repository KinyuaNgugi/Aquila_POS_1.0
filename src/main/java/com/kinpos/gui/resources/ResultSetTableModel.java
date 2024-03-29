/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kinpos.gui.resources;

/**
 * @author GEORGE
 */

import javax.swing.table.AbstractTableModel;
import java.sql.*;

public class ResultSetTableModel extends AbstractTableModel {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private int rows;
    private boolean connectedtoDatabase = false;

    // constructor initializes resultSet and obtains its meta data object;
    // determines number of rows

    public ResultSetTableModel(String url, String username, String password, String query)
            throws SQLException {
        //connect to the database
        connection = DriverManager.getConnection(url, username, password);

        //create a statement to query the database
        statement = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        //update database connection status
        connectedtoDatabase = true;

        //set query and execute it
        setQuery(query);
    }//end constructor

    //get the class that representsa the column type
    public Class getColumnClass(int column) throws IllegalStateException {
        // ensure database connection is available
        if (!connectedtoDatabase)
            throw new IllegalStateException("Not Connected to Database");

        try {
            // determine Java class of column
            String className = metaData.getColumnClassName(column + 1);
            // return Class object that represents className
            return Class.forName(className);
        }//end try
        catch (Exception exception) {
            exception.printStackTrace();
        }//end catch
        return Object.class; // if problems occur above, assume type Object
    } // end method getColumnClass

    // get number of columns in ResultSet
    public int getColumnCount() throws IllegalStateException {
        // ensure database connection is available
        if (!connectedtoDatabase)
            throw new IllegalStateException("Not Connected to Database");
        // determine number of columns
        try {
            return metaData.getColumnCount();
        } // end try

        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch

        return 0; // if problems occur above, return 0 for number of columns
    } // end method getColumnCount

    // get name of a particular column in ResultSet 
    public String getColumnName(int column) throws IllegalStateException {
        // ensure database connection is available
        if (!connectedtoDatabase)
            throw new IllegalStateException("Not Connected to Database");
        //determine column name
        try {
            return metaData.getColumnName(column + 1);
        } // end try

        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        return ""; // if problems, return empty string for column name
    } // end method getColumnName

    // return number of rows in ResultSet
    /*public int getRowCount() throws IllegalStateException {
        if (!connectedtoDatabase)
            throw new IllegalStateException("Not Connected to Database");
        return rows;

    }*///end method getRowcount

    public int getRowCount() {

        return rows;

    }
    // obtain value in particular row and column
    public Object getValueAt(int row, int column)
            throws IllegalStateException {
        if (!connectedtoDatabase)
            throw new IllegalStateException("Not Connected to Database");

        // obtain a value at specified ResultSet row and column
        try {
            resultSet.absolute(row + 1);
            return resultSet.getObject(column + 1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        return ""; // if problems, return empty string object
    }//// end method getValueAt

    // set new database query string
    public void setQuery(String query)
            throws SQLException, IllegalStateException {
        if (!connectedtoDatabase)
            throw new IllegalStateException("Not Connected to Database");
        // specify query and execute it
        resultSet = statement.executeQuery(query);
        // obtain meta data for ResultSet
        metaData = resultSet.getMetaData();

        // determine number of rows in ResultSet
        resultSet.last(); // move to last row
        rows = resultSet.getRow(); // get row number

        // notify JTable that model has changed
        fireTableStructureChanged();
    }//end method setquery

    // close Statement and Connection
    public void disconnectFromDatabase() {
        if (connectedtoDatabase) {
            // close Statement and Connection
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } // end try
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
            } // end catch
            finally // update database connection status
            {
                connectedtoDatabase = false;
            } // end finally
        } // end if
    } // end method disconnectFromDatabase
} // end class ResultSetTableModel