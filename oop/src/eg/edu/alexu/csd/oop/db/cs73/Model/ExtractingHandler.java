package eg.edu.alexu.csd.oop.db.cs73.Model;

import java.sql.SQLException;
import java.util.ArrayList;

public class ExtractingHandler {
	public String[][] getColumnsValues(String[] splittedQuery) throws SQLException {
		int length = 0;
		for(int i = 3 ; i < splittedQuery.length ; i ++) {
			if(splittedQuery[i].equalsIgnoreCase("values")) {
				break ;
			}
			length++;
		}
		String [][] columnsValues= null;
		if(length != 0) {
			columnsValues = new String [2][length];
    	for(int i = 3 , j = i + length+1 , k = 0 ;i-3 < length &&j < splittedQuery.length; j++, i++ , k++) {
    		columnsValues[0][k] = splittedQuery[i];
    		columnsValues[1][k] = splittedQuery[j];
    	}}
    	else {
    		columnsValues = new String [][] {new String [0] , new String[splittedQuery.length-4]};
    		for(int i = 4 , j = 0 ; i < splittedQuery.length ;j++ , i++) {
    			columnsValues[1][j] = splittedQuery[i];
    		}
    	}
		return columnsValues;
	}
	
	public String[] getColumns(String[] splittedQuery) throws SQLException {
		String [] columns = new String [splittedQuery.length-3];
		if(splittedQuery.length-3 == 0 && !splittedQuery[0].equalsIgnoreCase("drop")) {
			throw new SQLException("Wrong Create Query");
		}
		for(int i = 3 , j = 0 ; i < splittedQuery.length && j < columns.length ; i++,j++ ) {
			columns[j] = splittedQuery[i];
		}
		return columns;
	}
	
	public ArrayList<ArrayList<String>> getUpdatedColumnsValues(String[] splittedQuery){
			ArrayList<ArrayList<String>> columnsValues = new ArrayList<ArrayList<String>>();
			columnsValues.add(new ArrayList<String>());
			columnsValues.add(new ArrayList<String>());
			int length = getWhereIndex(splittedQuery) == -1 ? splittedQuery.length : getWhereIndex(splittedQuery) ;
			for(int i = 3 ; i < length ; i+=2) {
				columnsValues.get(0).add(splittedQuery[i]);
				columnsValues.get(1).add(splittedQuery[i+1]);
			}
			return columnsValues;
    }
	
	private int getWhereIndex(String [] splittedQuery) {
		int i = 0;
		for(String x : splittedQuery) {
			if(x.equalsIgnoreCase("where")) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public ArrayList<String> getWhere(String[] splittedQuery) {
		int whereIndex = getWhereIndex(splittedQuery);
		ArrayList<String> whereValue = new ArrayList<>();
		if(whereIndex != -1)
		for(int i = whereIndex+1 ; i < splittedQuery.length ; i ++) {
			whereValue.add(splittedQuery[i]);
		}
		return whereValue;
	}
}