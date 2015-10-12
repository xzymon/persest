package com.xzymon.persest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import com.xzymon.persest.model.Unit;

public class UnitDAO {
	private DataSource ds;
	
	public UnitDAO(DataSource ds){
		this.ds = ds;
	}
	
	public List<Unit> getUnits(){
		List<Unit> results = new LinkedList<Unit>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Unit unit = null;
		
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT id_jednostki, nazwa, kod, ilosc_ulamk_mianownik FROM jednostki_produktu");
			while(rs.next()){
				unit = new Unit();
				unit.setId(rs.getLong(1));
				unit.setName(rs.getString(2));
				unit.setQuantityDenominator(rs.getShort(3));
				unit.setCode(rs.getString(4));
				results.add(unit);
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
	
	public void addUnit(Unit unit){
		addUnit(unit.getName(), unit.getCode(), unit.getQuantityDenominator());
	}

	public void addUnit(String name, String code, Short quantityDenominator){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO jednostki_produktu(nazwa, kod) VALUES(?,?,?)");
			pstmt.setString(1, name);
			pstmt.setString(2, code);
			pstmt.setShort(3, quantityDenominator);
			pstmt.executeUpdate();
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
	}
	
	public void deleteUnit(Long unitId){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("DELETE FROM jednostki_produktu WHERE id_jednostki=?");
			pstmt.setLong(1, unitId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(conn != null){
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
