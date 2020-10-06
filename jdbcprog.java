import java.sql.*;
import java.io.*;
class JDBC2
{
	public static void main(String args[]) throws SQLException, ClassNotFoundException, IOException
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/emp","root","");
		Statement st=con.createStatement();
		boolean flag=st.execute("create table stud(rno int(2) primary key, sname varchar(20))");
		if(!flag)
			System.out.println("Table Created Successfully");
		else
			System.out.println("Error");
		System.out.println("*****Insert Record*****");
		String no,nm;
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter Number:=");
		no=br.readLine();
		int rno=Integer.parseInt(no);
		System.out.println("Enter Name:=");
		nm=br.readLine();
		PreparedStatement ps=con.prepareStatement("insert into stud values(?,?)");
		ps.setInt(1,rno);
		ps.setString(2,nm);
		int i=ps.executeUpdate();
		if(i>0)
			System.out.println("\nRecord Added Succesfully\n");
		else
			System.out.println("Some error");
		System.out.println("\n*****Records*****\n");
		ps=con.prepareStatement("select * from stud");
		ResultSet rs=ps.executeQuery();
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+" "+rs.getString(2));	
		}
		ps.close();
		rs.close();
		con.close();
	}
}
