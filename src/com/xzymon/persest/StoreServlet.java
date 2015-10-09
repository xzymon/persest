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

import com.xzymon.persest.model.Store;

/**
 * Servlet implementation class StoreServlet
 */
@WebServlet("/StoreServlet")
public class StoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@Resource(name="jdbc/persestDB")
	private DataSource ds;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StoreServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("stores", getStores());
		
		request.getRequestDispatcher("/WEB-INF/stores.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nsn = request.getParameter("nsn");
		String nsc = request.getParameter("nsc");
		String nss = request.getParameter("nss");
		String nsnb = request.getParameter("nsnb");
		String ds = request.getParameter("ds");
		
		if(nsn!=null && nsn!=""){
			addStore(nsn, nsc, nss, nsnb);
		}
		
		if(ds!=null && ds!=""){
			try{
				Long l_ds = Long.decode(ds);
				deleteStore(l_ds);
			} catch (NumberFormatException ex){
				ex.printStackTrace();
			}
		}
		
		doGet(request, response);
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
	
	private void addStore(String name, String city, String street, String number){
		Connection conn = null;
		
		try {
			conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO sklepy(nazwa, adres_miasto, adres_ulica, adres_nr) VALUES(?, ?, ?, ?)");
			pstmt.setString(1, name);
			pstmt.setString(2, city);
			pstmt.setString(3, street);
			pstmt.setString(4, number);
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
	
	private void deleteStore(Long id){
		Connection conn = null;
		
		try {
			conn = ds.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM sklepy WHERE id_sklepu=?");
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
