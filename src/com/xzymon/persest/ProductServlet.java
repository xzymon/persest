package com.xzymon.persest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.xzymon.persest.model.Category;
import com.xzymon.persest.model.Product;
import com.xzymon.persest.model.Unit;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/persestDB")
	private DataSource ds;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("products", getProducts());
		request.setAttribute("categories", getCategories());
		request.setAttribute("units", getUnits());
		
		request.getRequestDispatcher("/WEB-INF/products.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String npci = request.getParameter("npci");
		String npn = request.getParameter("npn");
		String npu = request.getParameter("npu");
		
		if(npci != null && npci != "" && npn != null && npn != "" && npu != null && npu != ""){
			try{
				Long l_ncid = Long.decode(npci);
				Long l_ncu = Long.decode(npu);
				addProduct(l_ncid, npn, l_ncu);
			} catch(NumberFormatException ex){
				
			}
		}
		
		doGet(request, response);
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
	
	public void addProduct(Long categoryId, String name, Long unitId){
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO produkty(id_kategorii, nazwa, jednostka) VALUES(?, ?, ?)");
			pstmt.setLong(1, categoryId);
			pstmt.setString(2, name);
			pstmt.setLong(3, unitId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception ex){
			ex.printStackTrace();
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
	
	private List<Category> getCategories() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Category cat = null;
		
		List<Category> categories = new ArrayList<Category>();
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			if(stmt.execute("SELECT id_kategorii, nazwa, kod FROM kategorie")){
				rs = stmt.getResultSet();
				while(rs.next()){
					cat = new Category();
					cat.setId(rs.getLong("id_kategorii"));
					cat.setName(rs.getString("nazwa"));
					cat.setCode(rs.getString("kod"));
					categories.add(cat);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return categories;
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
			rs = stmt.executeQuery("SELECT id_jednostki, nazwa, kod FROM jednostki_produktu");
			while(rs.next()){
				unit = new Unit();
				unit.setId(rs.getLong(1));
				unit.setName(rs.getString(2));
				unit.setCode(rs.getString(3));
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
