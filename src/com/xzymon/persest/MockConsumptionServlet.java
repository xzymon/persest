package com.xzymon.persest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.xzymon.persest.model.MockConsumption;
import com.xzymon.persest.model.Product;
import com.xzymon.persest.model.Purchase;
import com.xzymon.persest.model.Store;

/**
 * Servlet implementation class MockConsumptionServlet
 */
@WebServlet("/MockConsumptionServlet")
public class MockConsumptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name="jdbc/persestDB")
	private DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MockConsumptionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("consumption", getMockConsumption());
		request.setAttribute("products", getProducts());
		
		request.getRequestDispatcher("/WEB-INF/mockConsumption.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ncp = request.getParameter("ncp");
		String ncnum = request.getParameter("ncnum");
		String ncden = request.getParameter("ncden");
		String ncpri = request.getParameter("ncpri");
		String ncc = request.getParameter("ncc");
		String ncd = request.getParameter("ncd");
		String dc = request.getParameter("dc");
		
		if(ncp!=null && ncp!="" && ncnum!=null && ncnum!="" && ncden!=null && ncden!="" && ncpri!=null && ncpri!="" && ncd!=null && ncd!=""){
			try{
				Long l_ncp = Long.decode(ncp);
				Integer i_ncnum = Integer.decode(ncnum);
				Integer i_ncden = Integer.decode(ncden);
				
				Short centPrice = 0;
				Integer intPrice;
				String[] split = null;
				if(ncpri.contains(".")){
					split = ncpri.split("\\.");
					//System.out.format("if: %1$s splited into %2$d strings%n", ncpri, split.length);
				} else {
					split = new String[]{ncpri};
					//System.out.format("else: split has length of %1$d, and split[0] is %2$s%n", split.length, split[0]);
				}
		
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date d_ncd = new Date(dateFormat.parse(ncd).getTime());
				
				intPrice = Integer.decode(split[0]);
				if(split.length>1){
					centPrice = Short.decode(split[1]);
				}
				addMockConsumption(l_ncp, i_ncnum, i_ncden, intPrice, centPrice, ncc, d_ncd);
			} catch (NumberFormatException ex){
				ex.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if(dc!=null && dc!=""){
			try{
				Long l_dc = Long.decode(dc);
				deleteMockConsumption(l_dc);
			} catch (NumberFormatException ex){
				ex.printStackTrace();
			}
		}
		
		doGet(request, response);
	}

	private List<MockConsumption> getMockConsumption(){
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
