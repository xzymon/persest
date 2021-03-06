package com.xzymon.persest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.xzymon.persest.model.Unit;

/**
 * Servlet implementation class UnitServlet
 */
@WebServlet("/UnitServlet")
public class UnitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Resource(name="jdbc/persestDB")
	private DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Unit> units = getUnits();
		
		request.setAttribute("units", units);
		
		request.getRequestDispatcher("/WEB-INF/units.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nun = request.getParameter("nun");
		String nuc = request.getParameter("nuc");
		String nuqd = request.getParameter("nuqd");
		String du = request.getParameter("du");
		
		
		if(nun != null && nun != "" && nuc != null && nuc != "" && nuqd != null && nuqd != ""){
			try{
				Short s_nuqd = Short.decode(nuqd);
				addUnit(nun, nuc, s_nuqd);
			} catch (NumberFormatException ex){
				ex.printStackTrace();
			}
		}
		
		if(du != null && du != ""){
			try{
				Long ldu = Long.decode(du);
				deleteUnit(ldu);
			} catch (NumberFormatException ex){
				
			}
		}
		
		doGet(request, response);
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
			rs = stmt.executeQuery("SELECT id_jednostki, nazwa, ilosc_ulamk_mianownik, kod FROM jednostki_produktu");
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
			pstmt = conn.prepareStatement("INSERT INTO jednostki_produktu(nazwa, kod, ilosc_ulamk_mianownik) VALUES(?,?,?)");
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
