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

import com.xzymon.persest.model.MockConsumption;

public class MockConsumptionDAO {
	private DataSource ds;
	
	public MockConsumptionDAO(DataSource ds){
		this.ds = ds;
	}
	
	public List<MockConsumption> getMockConsumption(){
		List<MockConsumption> results = new LinkedList<MockConsumption>();
		
		Connection conn = null;
		
		try {
			conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			MockConsumption mc = null;
			ResultSet rs = stmt.executeQuery("SELECT id_konsumpcji_wirtualnej, id_produkt, mianownik_skonsumowanej_czesci, licznik_skonsumowanej_czesci, cena_calk, cena_ulamk, uwagi, data FROM konsumpcja_wirtualna");
			while(rs.next()){
				mc = new MockConsumption();
				mc.setId(rs.getLong(1));
				mc.setProductId(rs.getLong(2));
				mc.setConsumedDenominator(rs.getInt(3));
				mc.setConsumedNumerator(rs.getInt(4));
				mc.setIntPrice(rs.getInt(5));
				mc.setCentPrice(rs.getShort(6));
				mc.setComment(rs.getString(7));
				mc.setDate(rs.getDate(8));
				results.add(mc);
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
	
	public void addMockConsumption(Long productId, Integer consumedNumerator, Integer consumedDenominator, Integer intPrice, Short centPrice, String comment, Date date){
		Connection conn = null;
		try{
			conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO konsumpcja_wirtualna(id_produkt, licznik_skonsumowanej_czesci, mianownik_skonsumowanej_czesci, cena_calk, cena_ulamk, uwagi, data) VALUES(?, ?, ?, ?, ?, ?, ?)");
			pstmt.setLong(1, productId);
			pstmt.setLong(2, consumedNumerator);
			pstmt.setLong(3, consumedDenominator);
			pstmt.setInt(4, intPrice);
			pstmt.setShort(5, centPrice);
			pstmt.setString(6, comment);
			pstmt.setDate(7, date);
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
	
	public void deleteMockConsumption(Long id){
		Connection conn = null;
		try {
			conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM konsumpcja_wirtualna WHERE id_konsumpcji_wirtualnej=?");
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
