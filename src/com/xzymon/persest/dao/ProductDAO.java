package com.xzymon.persest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import com.xzymon.persest.model.Product;

public class ProductDAO {
	private DataSource ds;
	
	public ProductDAO(DataSource ds){
		this.ds = ds;
	}
	
	public List<Product> getProducts(){
		List<Product> results = null;
		
		Connection conn = null;
		Statement stmt = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id_produkt, id_kategorii, nazwa, jednostka FROM produkty");
			results = new LinkedList<Product>();
			Product p = null;
			while(rs.next()){
				p = new Product();
				p.setId(rs.getLong(1));
				p.setCategoryId(rs.getLong(2));
				p.setName(rs.getString(3));
				p.setUnitId(rs.getLong(4));
				results.add(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(conn != null){
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
	
	public void addProduct(Long categoryId, String name, Integer intQuantity, Short partQuantityNumerator,  Long unitId){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO produkty(id_kategorii, nazwa, jednostka) VALUES(?, ?, ?)");
			pstmt.setLong(1, categoryId);
			pstmt.setString(2, name);
			pstmt.setLong(3, unitId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex){
			ex.printStackTrace();
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
	
	public void deleteProduct(Long id){
		Connection conn = null;
		
		try {
			conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM produkty WHERE id_produkt=?");
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
