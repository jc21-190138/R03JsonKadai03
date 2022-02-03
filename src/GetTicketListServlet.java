

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetPointServlet
 */
@WebServlet("/getTicketList")
public class GetTicketListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTicketListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//↓↓servlet内でのデータベース接続↓↓
			PrintWriter out = response.getWriter();
			final String driverName = "com.mysql.jdbc.Driver";
			final String url = "jdbc:mysql://192.168.54.190:3306/jsonkadai03";
			final String id = "jsonkadai03";
			final String pass = "JsonKadai03";
			try {
				
				Class.forName(driverName);
				Connection con=DriverManager.getConnection(url,id,pass);
				String point=null;
		//↑↑servlet内でのデータベース接続↑↑
		
		//↓↓店舗IDとユーザーIDの取得↓↓
				String tenpoid = request.getParameter("TENPO_ID");
				String userid = request.getParameter("USER_ID");
		//↑↑店舗IDとユーザーIDの取得↑↑
				
				PreparedStatement st = con.prepareStatement("SELECT * FROM point_table WHERE tenpo_id = ? and user_id=?");	
				st.setString(1,tenpoid);
				st.setString(2,userid);
				ResultSet rs=st.executeQuery();
				if(rs.next()){
					point= rs.getString("point");
					}
				
				int point2 = Integer.parseInt(point);
				PreparedStatement at = con.prepareStatement("SELECT * FROM ticket_table WHERE POINT<=? && tenpo_id=?");
				at.setInt(1, point2);
				at.setString(2, tenpoid);
				ResultSet result = at.executeQuery();
				
				List<String[]> list = new ArrayList<>();
				
				while(result.next()==true) {
					String[] s = new String[3];
					s[0]=result.getString("ticket_id");
					s[1]=result.getString("ticket_name");
					s[2]=result.getString("POINT");
					list.add(s);
				}
				
				
				request.setAttribute("list",list);

		
		
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/getTicketList.jsp");
				rd.forward(request, response);
			}catch (Exception e) {
				out.println("<pre>");
				e.printStackTrace(out);
			}
	}
}
