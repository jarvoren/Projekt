package mpr.proj.pedigree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class KolekcjeIOperacje {
	public static Map<Integer ,Horse> pobierzKolekcjeKonizBazy() {
		try{
			Connection con = DriverManager.getConnection("1jdbc:hsqldb:hsql://localhost/workdb","sa","");;
			Map<Integer ,Horse> kolekcja = new HashMap<Integer ,Horse>();
			String queryStr = "SELECT * FROM HORSE";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryStr);
			while(rs.next())	{
				kolekcja.put((int) rs.getLong(1),new Horse(rs.getLong(1), rs.getString(2), Sex.valueOf(rs.getInt(3)), new DateOfBirth(rs.getDate(5)), pobierzKolor(rs.getInt(4)), pobierzKonia(rs.getInt(8)), pobierzKonia(rs.getInt(7)),pobierzHodowce(rs.getInt(9)) ));
			
			}
			con.close();
			return kolekcja;
			
			
			
			
		}
		catch(Exception ex)	{
				System.out.println(ex.getMessage());
				return null;
			}
	}
	
	public static Horse pobierzKonia(int int1) {
		try{
			Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
			
			String queryStr = "SELECT * FROM HORSE WHERE id="+int1;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryStr);
			if(rs.next())	{
				con.close();
				return new Horse(rs.getLong(1), rs.getString(2), Sex.valueOf(rs.getInt(3)), new DateOfBirth(rs.getDate(5)), pobierzKolor(rs.getInt(4)), pobierzKonia(rs.getInt(8)), pobierzKonia(rs.getInt(7)),pobierzHodowce(rs.getInt(9)) );
			
			}
			else 
			{
				con.close();
				return null;
			}
			
		}
		catch (Exception ex)	{
				System.out.println(ex.getMessage());
			
		}
		return null;
			
			
		}
	public static Breeder pobierzHodowce(int int1) {
		try{
		Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
		
		String queryStr = "SELECT * FROM BREEDER WHERE ID="+int1;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryStr);
		if (rs.next())
		{
			return new Breeder(int1, rs.getString(2), pobierzKraj(rs.getInt(3)));
		}
		else
		{
			con.close();
			return null;
		}
		}
		catch (Exception ex)	{
			System.out.println(ex.getMessage());
		}
		return null;
	}
	public static Country pobierzKraj(int int1) {
		try
		{
			Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
			
			String queryStr = "SELECT * FROM COUNTRY WHERE ID="+int1;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryStr);
			if (rs.next())
			{
				return new Country(int1, rs.getString(2), rs.getString(3));
			}
			else
			{
				con.close();
				return null;
			}
			}
			catch (Exception ex)	{
				System.out.println(ex.getMessage());
			}
		return null;
	}
	public static List<Color> pobierzKoloryZBazy() {
		try	{
			Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
			List<Color> kolekcja = new ArrayList<Color>();
			String queryStr = "SELECT * FROM COLOR";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryStr);
			while(rs.next())	{
				kolekcja.add(new Color(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
			
			con.close();
			return kolekcja;
		}
		catch (Exception ex)	{
			System.out.println(ex.getMessage());
		}
		return null;

		
	}
	public static void wpiszKoniaDoBazy(Horse kon) {
		try{
			Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
			
			String queryStr = "INSERT INTO HORSE (NAME , SEX, COLOR , DOB, YEARONLY, DAM, SIRE , BREEDER) VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement stmt = con.prepareStatement(queryStr);
			stmt.setString(1, kon.getName());
	        stmt.setInt(2, kon.getIntOfSex());
	        stmt.setInt(3, kon.getColor().getID());
	        stmt.setString(4, kon.getDob().toString());
	        stmt.setBoolean(5, false);
	        stmt.setInt(6, (int) kon.getDam().getID());
	        stmt.setInt(7, (int) kon.getSire().getID());
	        stmt.setInt(8, (int) kon.getBreeder().getId());
			
			stmt.executeUpdate();
			
			
			con.close();
			}
			
			
			

		catch (Exception ex)	{
			System.out.println(ex.getMessage());
		}
		
		
	}
	public static Color pobierzKolor(int int1) {
		try{
			Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
			String queryStr = "SELECT * FROM COLOR WHERE id="+int1;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryStr);
			if(rs.next())	{
				return new Color(rs.getLong(1), rs.getString(2), rs.getString(3));
			}
			con.close();
			
		}
		catch(Exception ex)	{
			System.out.println(ex.getMessage());
		}
		return null;
		}

	
}
