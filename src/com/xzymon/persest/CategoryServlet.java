package com.xzymon.persest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.xzymon.persest.model.Category;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/persestDB")
	private DataSource ds;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CategoryServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Category> categories = getCategories();
		request.setAttribute("categories", categories);
		
		request.getRequestDispatcher("/WEB-INF/categories.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String ncn = request.getParameter("ncn");
		String ncc = request.getParameter("ncc");
		
		if(ncn!=null && ncn!="" && ncc!=null && ncc!=""){
			addCategory(ncn, ncc);
		}
		
		List<Category> categories = getCategories();
		request.setAttribute("categories", categories);
		
		request.getRequestDispatcher("/WEB-INF/categories.jsp").forward(request, response);
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
	
	private Long addCategory(Category cat){
		return addCategory(cat.getName(), cat.getCode());
	}
	
	private Long addCategory(String catName, String catCode){
		Long result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement("INSERT INTO kategorie(nazwa,kod) VALUES(?,?)");
			pstmt.setString(1, catName);
			pstmt.setString(2, catCode);
			if(pstmt.execute()){
				rs = pstmt.getGeneratedKeys();
				while(rs.next()){
					result = rs.getLong(1);
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
		
		return result;
	}

}
