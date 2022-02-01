import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
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
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource)ic.lookup(
					"java:/comp/env/jdbc/jsonkadai03");
			Connection con = ds.getConnection();
			String point=null;
		//String userid = "ABCDEFGHIJK1234";
		//String tenpoid = "1234567890";
		
		/*final String driverName = "com.mysql.jdbc.Driver";
		final String url = "jdbc:mysql://192.168.54.190:3306/jsonkadai03";
		final String id = "jsonkadai03";
		final String pass = "JsonKadai03";*/

		//try {
			/*Class.forName(driverName);
			Connection connection=DriverManager.getConnection(url,id,pass);
			PreparedStatement st1 =
					con.prepareStatement(
							"SELECT * FROM point_table WHERE tenpo_id = 1 "
						);
			ResultSet result1 = st1.executeQuery();
			List<String[]> list1 = new ArrayList<>();
			while( result1.next() == true) {
				String[] s = new String[1];
				s[0]=result1.getString("point");
				list1.add(s);
				
			}*/
			String tenpoid = request.getParameter("TENPO_ID");
			String userid = request.getParameter("USER_ID");
		PreparedStatement at = con.prepareStatement("insert ignore into point_table(tenpo_id,user_id,point)values(?,?,500)");
		at.setString(1,tenpoid);
		at.setString(2,userid);
		at.executeUpdate();
			
		PreparedStatement st = con.prepareStatement("SELECT * FROM point_table WHERE tenpo_id = ? and user_id=?");
		

		
		st.setString(1,tenpoid);//ここにqrコードのデータを入れる
		st.setString(2,userid);
		ResultSet rs=st.executeQuery();
		
		/*List<String[]> list = new ArrayList<>();
		while( rs.next() == true) {
			String[] s = new String[1];
			s[0]=rs.getString("point");
			list.add(s);
		}*/
		
		if(rs.next()){
			point= rs.getString("point");
			}
		//String point= rs.getString("point");
		
		
		//st.close();
		//con.close();
		
		request.setAttribute("POINT", point);

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/getPoint.jsp");
		rd.forward(request, response);
		} catch (Exception e) {
			out.println("<pre>");
			e.printStackTrace(out);
		}
	}
}
	
		
		

		

