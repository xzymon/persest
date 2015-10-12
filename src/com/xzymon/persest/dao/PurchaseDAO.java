package com.xzymon.persest.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import com.xzymon.persest.model.Purchase;

public class PurchaseDAO {
	private DataSource ds;
	
	public PurchaseDAO(DataSource ds){
		this.ds = ds;
	}
	
	public List<Purchase> getPurchasedProducts(){
		List<Purchase> results = new LinkedList<Purchase>();
		
		Connection conn = null;
		
		try {
			conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			Purchase pp = null;
			ResultSet rs = stmt.executeQuery("SELECT id_zakupu, id_produkt, id_sklepu, cena_calk, cena_ulamk, uwagi, data FROM zakupione_produkty");
			while(rs.next()){
				pp = new Purchase();
				pp.setId(rs.getLong(1));
				pp.setProductId(rs.getLong(2));
				pp.setStoreId(rs.getLong(3));
				pp.setIntPrice(rs.getInt(4));
				pp.setCentPrice(rs.getShort(5));
				pp.setComment(rs.getString(6));
				pp.setDate(rs.getDate(7));
				results.add(pp);
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
	
	public void addPurchasedProduct(Long productId, Long storeId, Integer intPrice, Short centPrice, String comment, Date date){
		Connection conn = null;
		
		try{
			conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO zakupione_produkty(id_produkt, id_sklepu, cena_calk, cena_ulamk, uwagi, data) VALUES(?, ?, ?, ?, ?, ?)");
			pstmt.setLong(1, productId);
			pstmt.setLong(2, storeId);
			pstmt.setInt(3, intPrice);
			pstmt.setShort(4, centPrice);
			pstmt.setString(5, comment);
			pstmt.setDate(6, date);
			pstmt.executeUpdate();
				
				
		} catch (NumberFormatException ex){
			ex.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void deletePurchase(Long id){
		Connection conn = null;
		try {
			conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM zakupione_produkty WHERE id_zakupu=?");
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
