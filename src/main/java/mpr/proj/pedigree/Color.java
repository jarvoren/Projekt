package mpr.proj.pedigree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class Color {

	private long id;
	private String lname;
	private String sname;

	public Color(long id, String lname, String sname) {
		this.id = id;
		this.lname = lname;
		this.sname = sname;
	}

	public Color(int int1) {
		try{
		Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
		Set<Color> dane = new HashSet<Color>();
		String queryStr = "SELECT * FROM COLOR";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryStr);
		while(rs.next())	{
			dane.add(new Color(rs.getLong(0), rs.getString(1), rs.getString(2)));
		}
		for(Color c: dane)
		{
			if(c.id==int1)
			{
				con.close();
				this.id=int1;
				this.lname=c.lname;
				this.sname=c.sname;
			}
		}
		con.close();
		
	}
	catch(Exception ex)	{
		System.out.println(ex.getMessage());
	}
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getLname() {
		return lname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSname() {
		return sname;
	}

	public int getID() {
		return (int) this.id;
		
	}

}
