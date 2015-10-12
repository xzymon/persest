package com.xzymon.persest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.xzymon.persest.model.Category;

public class CategoryDAO {
	private DataSource ds;
	
	public CategoryDAO(DataSource ds){
		this.ds = ds;
	}
	
	private List<Category> getCategories() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Category cat = null;
		
		List<Category> categories = new ArrayList<Category>();
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			if(stmt.execute("SELECT id_kategorii, nazwa, kod FROM kategorie")){
				rs = stmt.getResultSet();
				while(rs.next()){
					cat = new Category();
					cat.setId(rs.getLong("id_kategorii"));
					cat.setName(rs.getString("nazwa"));
					cat.setCode(rs.getString("kod"));
					categories.add(cat);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return categories;
	}
	
	private Long addCategory(Category cat){
		return addCategory(cat.getName(), cat.getCode());
	}
	
	private Long addCategory(String catName, String catCode){
		Long result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO kategorie(nazwa,kod) VALUES(?,?)");
			pstmt.setString(1, catName);
			pstmt.setString(2, catCode);
			if(pstmt.execute()){
				rs = pstmt.getGeneratedKeys();
				while(rs.next()){
					result = rs.getLong(1);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	private void deleteCategory(Long categoryId){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("DELETE FROM kategorie WHERE id_kategorii=?");
			pstmt.setLong(1, categoryId);
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
