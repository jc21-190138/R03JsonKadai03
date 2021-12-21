
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.jasper.tagplugins.jstl.core.Out;

/**
 * Servlet implementation class GetPointServlet
 */
@WebServlet("/getPoint")
public class GetPointServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetPointServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @ see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 * response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc/jsonkadai03");
			Connection con = ds.getConnection();
			String keyword = request.getParameter("keyword");
			PreparedStatement st = con.prepareStatement("select * from point_table");
			st.setString(1, "%" + keyword + "%");
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString("shop_id"));
				System.out.println(":");
				System.out.println(rs.getString("user_id"));
				System.out.println(":");
				System.out.println(rs.getInt("point"));
			}
			st.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
