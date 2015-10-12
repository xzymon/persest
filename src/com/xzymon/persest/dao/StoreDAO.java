package com.xzymon.persest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import com.xzymon.persest.model.Store;

public class StoreDAO {
	private DataSource ds;
	
	public StoreDAO(DataSource ds){
		this.ds = ds;
	}
	
	public List<Store> getStores(){
		List<Store> results = new LinkedList<Store>();
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id_sklepu, nazwa, adres_miasto, adres_ulica, adres_nr FROM sklepy");
			Store store = null;
			while(rs.next()){
				store = new Store();
				store.setId(rs.getLong(1));
				store.setName(rs.getString(2));
				store.setCity(rs.getString(3));
				store.setStreet(rs.getString(4));
				store.setNumber(rs.getString(5));
				results.add(store);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return results;
	}
	
	private void addStore(String name, String city, String street, String number){
		Connection conn = null;
		
		try {
			conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO sklepy(nazwa, adres_miasto, adres_ulica, adres_nr) VALUES(?, ?, ?, ?)");
			pstmt.setString(1, name);
			pstmt.setString(2, city);
			pstmt.setString(3, street);
			pstmt.setString(4, number);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void deleteStore(Long id){
		Connection conn = null;
		
		try {
			conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM sklepy WHERE id_sklepu=?");
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
