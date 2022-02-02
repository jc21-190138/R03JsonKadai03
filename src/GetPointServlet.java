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
		//↓↓servlet内でのデータベース接続↓↓
		PrintWriter out = response.getWriter();
		final String driverName = "com.mysql.jdbc.Driver";
		final String url = "jdbc:mysql://192.168.54.190:3306/jsonkadai03";
		final String id = "jsonkadai03";
		final String pass = "JsonKadai03";
		//↑↑servlet内でのデータベース接続↑↑
		
		//↓↓contextを使用したデータベース接続↓↓
		/*try {	
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource)ic.lookup(
					"java:/comp/env/jdbc/jsonkadai03");
			Connection con = ds.getConnection();
			String point=null;*/							
			//↑↑contextを使用したデータベース接続↑↑
			
		

			//↓↓servlet内でのデータベース接続↓↓
		try {
			
			Class.forName(driverName);
			Connection con=DriverManager.getConnection(url,id,pass);
			String point=null;
			//↑↑servlet内でのデータベース接続↑↑
			
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
		
		
		if(rs.next()){
			point= rs.getString("point");
			}
		
		
		st.close();
		con.close();
		
		request.setAttribute("POINT", point);

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/getPoint.jsp");
		rd.forward(request, response);
		} catch (Exception e) {
			out.println("<pre>");
			e.printStackTrace(out);
		}
	}
}
	
		
		

		

