package Library_Mgmt;
import java.sql.*;
public class Link 
{
	String username = "root";
	String pass = "root";
	Connection c1 = null;
	String url = "jdbc:mysql://localhost:3306/library_management"; // direct connect to database in url
	
	public int Affected_Rows_Link(String str1)
	{
		try 
		{
			c1 = DriverManager.getConnection(url, username, pass);
			Statement s1 = c1.createStatement();
			int numOfChangedRows = s1.executeUpdate(str1);	
			return numOfChangedRows;
		} 
		 
		catch(SQLException e1) 
		{
			System.out.println("CONNECTION ERROR - " + e1.getMessage());
			return 0;
		}
	}
	public ResultSet Data_Link(String str2) 
	{	
		try 
		{	
			try 
			{ 						/// remove the nested try catch later 
				Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e2) 
			{	
				e2.printStackTrace();
			}
			c1 = DriverManager.getConnection(url, username, pass);
			Statement s2 = c1.createStatement();
			ResultSet r1 = s2.executeQuery(str2);
		    return r1;
		} 

		catch(SQLException e3) 
		{
			System.out.println("CONNECTION ERROR - " + e3.getMessage());
			return null;
		}
	}
}
