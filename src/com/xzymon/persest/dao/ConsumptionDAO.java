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

import com.xzymon.persest.model.Consumption;

public class ConsumptionDAO {
	private DataSource ds;
	
	public ConsumptionDAO(DataSource ds){
		this.ds = ds;
	}
	
	public List<Consumption> getConsumption(){
		List<Consumption> results = new LinkedList<Consumption>();
		
		Connection conn = null;
		
		try {
			conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			Consumption c = null;
			ResultSet rs = stmt.executeQuery("SELECT id_konsumpcji_realnej, id_zakupu, mianownik_skonsumowanej_czesci, licznik_skonsumowanej_czesci, uwagi, data FROM konsumpcja_realna");
			while(rs.next()){
				c = new Consumption();
				c.setId(rs.getLong(1));
				c.setPurchaseId(rs.getLong(2));
				c.setConsumedDenominator(rs.getInt(3));
				c.setConsumedNumerator(rs.getInt(4));
				c.setComment(rs.getString(5));
				c.setDate(rs.getDate(6));
				results.add(c);
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
	
	public void addConsumption(Long purchaseId, Integer consumedNumerator, Integer consumedDenominator, String comment, Date date){
		Connection conn = null;
		
		try{
			conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO konsumpcja_realna(id_zakupu, licznik_skonsumowanej_czesci, mianownik_skonsumowanej_czesci, uwagi, data) VALUES(?, ?, ?, ?, ?)");
			pstmt.setLong(1, purchaseId);
			pstmt.setInt(2, consumedNumerator);
			pstmt.setInt(3, consumedDenominator);
			pstmt.setString(4, comment);
			pstmt.setDate(5, date);
			pstmt.executeUpdate();
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
	
	public void deleteConsumption(Long id){
		Connection conn = null;
		try {
			conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM konsumpcja_realna WHERE id_konsumpcji_realnej=?");
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
