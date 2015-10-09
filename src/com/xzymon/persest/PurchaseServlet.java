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
import com.xzymon.persest.model.PurchasedProduct;
import com.xzymon.persest.model.Store;

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
		
		Connection conn = null;
		
		if(np!=null && np!="" && nps!=null && nps!="" && npp!=null && npp!="" && npd!=null && npd!=""){
			try{
				Long l_np = Long.decode(np);
				Long l_nps = Long.decode(nps);
				Short centPrice = 0;
				Integer intPrice;
				String[] split = null;
				if(npp.contains(".")){
					split = npp.split(".");
				} else {
					split = new String[]{npp};
				}
		
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date d_npd = new Date(dateFormat.parse(npd).getTime());
				
					intPrice = Integer.decode(split[0]);
					if(split.length>1){
						centPrice = Short.decode(split[1]);
					}
					conn = ds.getConnection();
					PreparedStatement pstmt = conn.prepareStatement("INSERT INTO zakupione_produkty(id_produkt, id_sklepu, cena_calk, cena_ulamk, uwagi, data) VALUES(?, ?, ?, ?, ?, ?)");
					pstmt.setLong(1, l_np);
					pstmt.setLong(2, l_nps);
					pstmt.setInt(3, intPrice);
					pstmt.setShort(4, centPrice);
					pstmt.setString(5, npc);
					pstmt.setDate(6, d_npd);
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

	private List<PurchasedProduct> getPurchasedProducts(){
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
}
