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
import java.util.zip.DataFormatException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.xzymon.persest.model.Product;
import com.xzymon.persest.model.Purchase;
import com.xzymon.persest.model.Store;
import com.xzymon.persest.model.Unit;

/**
 * Servlet implementation class PurchaseServlet
 */
@WebServlet("/PurchaseServlet")
public class PurchaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name="jdbc/persestDB")
	private DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PurchaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("purchases", getPurchasedProducts());
		request.setAttribute("products", getProducts());
		request.setAttribute("stores", getStores());
		request.setAttribute("units", getUnits());
		
		request.getRequestDispatcher("/WEB-INF/purchases.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String np = request.getParameter("np");
		String nps = request.getParameter("nps");
		String npp = request.getParameter("npp");
		String npc = request.getParameter("npc");
		String npd = request.getParameter("npd");
		String dp = request.getParameter("dp");
		
		if(np!=null && np!="" && nps!=null && nps!="" && npp!=null && npp!="" && npd!=null && npd!=""){
			try{
				Long l_np = Long.decode(np);
				Long l_nps = Long.decode(nps);
				Short centPrice = 0;
				Integer intPrice;
				String[] split = null;
				if(npp.contains(".")){
					split = npp.split("\\.");
				} else {
					split = new String[]{npp};
				}
		
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date d_npd = new Date(dateFormat.parse(npd).getTime());
				
				intPrice = Integer.decode(split[0]);
				if(split.length>1){
					centPrice = Short.decode(split[1]);
				}
				
				addPurchasedProduct(l_np, l_nps, intPrice, centPrice, npc, d_npd);
			} catch (NumberFormatException ex){
				ex.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(dp!=null && dp!=""){
			try{
				Long l_dp = Long.decode(dp);
				deletePurchase(l_dp);
			} catch (NumberFormatException ex){
				ex.printStackTrace();
			}
		}
		
		doGet(request, response);
	}

	private List<Purchase> getPurchasedProducts(){
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
}
