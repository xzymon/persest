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

import com.xzymon.persest.model.Consumption;
import com.xzymon.persest.model.MockConsumption;
import com.xzymon.persest.model.Product;
import com.xzymon.persest.model.PurchasedProduct;
import com.xzymon.persest.model.Store;

/**
 * Servlet implementation class MockConsumptionServlet
 */
@WebServlet("/ConsumptionServlet")
public class ConsumptionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name="jdbc/persestDB")
	private DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsumptionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("consumption", getConsumption());
		request.setAttribute("purchases", getPurchases());
		
		request.getRequestDispatcher("/WEB-INF/consumption.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ncp = request.getParameter("ncp");
		String ncnum = request.getParameter("ncnum");
		String ncden = request.getParameter("ncden");
		String ncc = request.getParameter("ncc");
		String ncd = request.getParameter("ncd");
		String dc = request.getParameter("dc");
		
		Connection conn = null;
		
		if(ncp!=null && ncp!="" && ncnum!=null && ncnum!="" && ncden!=null && ncden!="" && ncd!=null && ncd!=""){
			try{
				Long l_ncp = Long.decode(ncp);
				Integer i_ncnum = Integer.decode(ncnum);
				Integer i_ncden = Integer.decode(ncden);
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date d_npd = new Date(dateFormat.parse(ncd).getTime());
				
					conn = ds.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("INSERT INTO konsumpcja_realna(id_zakupu, licznik_skonsumowanej_czesci, mianownik_skonsumowanej_czesci, uwagi, data) VALUES(?, ?, ?, ?, ?)");
					pstmt.setLong(1, l_ncp);
					pstmt.setLong(2, i_ncnum);
					pstmt.setLong(3, i_ncden);
					pstmt.setString(4, ncc);
					pstmt.setDate(5, d_npd);
					pstmt.executeUpdate();
				
				
			} catch (NumberFormatException ex){
				ex.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		
		if(dc!=null && dc!=""){
			try{
				Long l_dc = Long.decode(dc);
				deleteConsumption(l_dc);
			} catch (NumberFormatException ex){
				ex.printStackTrace();
			}
		}
		
		doGet(request, response);
	}

	private List<Consumption> getConsumption(){
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
	
	private List<PurchasedProduct> getPurchases(){
		List<PurchasedProduct> results = new LinkedList<PurchasedProduct>();
		
		Connection conn = null;
		
		try {
			conn = ds.getConnection();
			Statement stmt = conn.createStatement();
			PurchasedProduct pp = null;
			ResultSet rs = stmt.executeQuery("SELECT id_zakupu, id_produkt, id_sklepu, cena_calk, cena_ulamk, uwagi, data FROM zakupione_produkty");
			while(rs.next()){
				pp = new PurchasedProduct();
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
