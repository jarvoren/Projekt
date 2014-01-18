package mpr.proj.pedigree;

import java.sql.Connection;
import java.sql.Date;
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

import mpr.proj.EasyIn;

public abstract class KolekcjeIOperacje {
	
	public static void modyfikujWpisKonia(int idDoZmiany, Horse kon) {
		try        {
	        Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
	        String queryStr = "UPDATE HORSE SET NAME=(?), SEX=(?), COLOR=(?), DOB=(?), DAM=(?), SIRE=(?), BREEDER=(?) WHERE ID=(?)";
	        PreparedStatement stmt = con.prepareStatement(queryStr);
	        stmt.setString(1, kon.getName());
	        stmt.setInt(2, kon.getIntOfSex() );
	        stmt.setInt(3, kon.getColor().getID());
	        stmt.setString(4, kon.getDob().getDate().toString());
	        stmt.setInt(6, (int) kon.getDam().getID());
	        stmt.setInt(7,(int) kon.getSire().getID());
	        stmt.setInt(8, (int) kon.getBreeder().getId());
	        stmt.setInt(9,(int) idDoZmiany);
	        stmt.executeUpdate(); 
	        
	}
	catch (Exception ex) {
	        System.out.println(ex.getMessage());
	}
		
	}
	
	public static Map<Integer ,Breeder> pobierzKolekcjeHodowcowZBazy() {
		try{
			Connection con = DriverManager.getConnection("1jdbc:hsqldb:hsql://localhost/workdb","sa","");;
			Map<Integer ,Breeder> kolekcja = new HashMap<Integer ,Breeder>();
			String queryStr = "SELECT * FROM BREEDER";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryStr);
			while(rs.next())	
			{
				kolekcja.put((int) rs.getLong(1), new Breeder(rs.getLong(1), rs.getString(2), pobierzKraj(rs.getInt(3))));
			
			}
			con.close();
			return kolekcja;
			
			
			
			
		}
		catch(Exception ex)	{
				System.out.println(ex.getMessage());
				return null;
			}
	}
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

	public static void dopiszHodowce(Breeder hodowca) {
		try{
			
			Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
			
			String queryStr = "INSERT INTO BEEDER (NAME , COUNTRY) VALUES (?,?)";
			PreparedStatement stmt = con.prepareStatement(queryStr);
			stmt.setString(1, hodowca.getName());
	        stmt.setInt(2, hodowca.getCountry().getId());
	        
			
			stmt.executeUpdate();
			
			
			con.close();
			}
			
			
			

		catch (Exception ex)	{
			System.out.println(ex.getMessage());
		}
		
	}

	public static void dopiszKraj(Country kraj) {
		try{
			
			Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
			
			String queryStr = "INSERT INTO COUNTRY (NAME , CODE) VALUES (?,?)";
			PreparedStatement stmt = con.prepareStatement(queryStr);
			stmt.setString(1, kraj.getName());
	        stmt.setString(2, kraj.getCode());
	        	
			stmt.executeUpdate();
				
			con.close();
			}
			
			
			

		catch (Exception ex)	{
			System.out.println(ex.getMessage());
		}
		
	}
	public static Horse pobierzKoniaOdUzytkownika() {
		EasyIn.clear();
		System.out.println("Podaj imie konia");
		String imie = EasyIn.getString();
		System.out.println("Podaj płeć konia 1) Gelding 2) Mare 3) Stallion");
		Sex plec = Sex.valueOf(EasyIn.getInt());
		DateOfBirth dataUrodzinFormated = pobierzDateUrodzeniaOdUzytkownika();
		System.out.println("Podaj id koloru konia");
		int kolorId = EasyIn.getInt();
		System.out.println("Podaj id");
		int idOjca = EasyIn.getInt();
		Horse ojciec = KolekcjeIOperacje.pobierzKonia(idOjca);
		System.out.println("Podaj id matki");
		int idMatki = EasyIn.getInt();
		Horse matka = KolekcjeIOperacje.pobierzKonia(idMatki);
		System.out.println("Podaj id hodowcy");
		int id = EasyIn.getInt();
		Breeder hodowca = KolekcjeIOperacje.pobierzHodowce(id);
		return new Horse(0, imie, plec, dataUrodzinFormated,KolekcjeIOperacje.pobierzKolor(kolorId),  matka, ojciec, hodowca);
		
		
	}
	private static DateOfBirth pobierzDateUrodzeniaOdUzytkownika() {
		Date dataUrodzin = new Date(0);
		System.out.println("Podaj rok urodzenia konia");
		dataUrodzin.setYear(EasyIn.getInt());
		System.out.println("Podaj miesiac urodzenia konia");
		dataUrodzin.setMonth(EasyIn.getInt());
		System.out.println("Podaj dzien urodzenia konia");
		dataUrodzin.setDate(EasyIn.getInt());
		DateOfBirth dataUrodzinFormated = new DateOfBirth(null);
		dataUrodzinFormated.setDate(dataUrodzin, false);
		return dataUrodzinFormated;
	}
	public static Breeder pobierzHodowceOdUzytkownika() {
		System.out.println("Podaj imie hodowcy");
		String imie = EasyIn.getString();
		System.out.println("Podaj id kraju pochodzenia hodowcy");
		int idKraju = EasyIn.getInt();
		Country kraj = KolekcjeIOperacje.pobierzKraj(idKraju);
		return new Breeder(0, imie, kraj);
	}
	public static Country pobierzKrajOdUzytkownika() {
		System.out.println("Podaj nazwę kraju jaki chcesz dodać");
		String nazwa = EasyIn.getString();
		System.out.println("Podaj Kod Kraju");
		String kod = EasyIn.getString();
		return new Country(0, nazwa, kod);
	}

	public static void modyfikujWpisHodowcy(int wybor ,Breeder hodowca) {
		try        {
	        Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
	        String queryStr = "UPDATE BREEDER SET NAME=(?), COUNTRY=(?) WHERE ID=(?)";
	        PreparedStatement stmt = con.prepareStatement(queryStr);
	        stmt.setString(1, hodowca.getName());
	        stmt.setString(2, hodowca.getCountry().getName());
	        stmt.setInt(3, wybor);
	        stmt.executeUpdate(); 
	        
	}
	catch (Exception ex) {
	        System.out.println(ex.getMessage());
	}
	}

	
}
