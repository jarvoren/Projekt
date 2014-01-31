import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import mpr.proj.dataBaseOperations;
import mpr.proj.pedigree.Breeder;
import mpr.proj.pedigree.Color;
import mpr.proj.pedigree.Country;
import mpr.proj.pedigree.DateOfBirth;
import mpr.proj.pedigree.Horse;
import mpr.proj.pedigree.KolekcjeIOperacje;
import mpr.proj.pedigree.Sex;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TestyOperacjiSql {
	
	
	
	
	private Connection con;
	@Before
	public void polaczenie(){
		try{
			
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");
		}
		catch(Exception ex)	{
			System.out.println(ex.getMessage());
		}
		}

	@Test
	public void testTabeliHodowca(){
		
		Random gen = new Random();
		String test = ""+gen.nextInt(5000);
		
		
		
		
		
		Breeder hodowcaTestowy = new Breeder(0,test,new Country(7, "Angli", "AN"));
		KolekcjeIOperacje.dopiszHodowce(hodowcaTestowy);
		
		
		try{
			
			String queryStr = "SELECT * FROM BREEDER WHERE NAME='"+test+"'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryStr);
			
			assertEquals(true,rs.next());
			assertEquals(rs.getString(2),test);
			
			
			hodowcaTestowy.setName("Waldek");
			KolekcjeIOperacje.modyfikujWpisHodowcy(rs.getInt(1),hodowcaTestowy);
			assertEquals("Waldek",KolekcjeIOperacje.pobierzHodowce(rs.getInt(1)).getName());
			
			
			dataBaseOperations.kasujHodowce(rs.getInt(1));
			assertEquals(null ,KolekcjeIOperacje.pobierzHodowce(rs.getInt(1)));
			
		
			
		}
		catch(Exception ex)	{
			System.out.println(ex.getMessage());
		}
		
		}
	@Test
	public void testTabeliKoni(){
		
		Random gen = new Random();
		String test = ""+gen.nextInt(5000);
		
		
		
		
		
		Horse konTestowy = new Horse(0, test, Sex.STALLION, new DateOfBirth(new Date(1500)), new Color(0, test, test), KolekcjeIOperacje.pobierzKonia(2), KolekcjeIOperacje.pobierzKonia(2), new Breeder(1,test,null));
		KolekcjeIOperacje.wpiszKoniaDoBazy(konTestowy);
		
		
		try{
			
			String queryStr = "SELECT * FROM HORSE WHERE NAME='"+test+"'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryStr);
			
			assertEquals(true,rs.next());
			assertEquals(rs.getString(2),test);
			
			konTestowy.setName("Waldek");
			
			KolekcjeIOperacje.modyfikujWpisKonia(rs.getInt(1), konTestowy );
			assertEquals("Waldek",KolekcjeIOperacje.pobierzKonia(rs.getInt(1)).getName());
			
			
			dataBaseOperations.kasujKonia(rs.getInt(1));
			assertEquals(null ,KolekcjeIOperacje.pobierzKonia(rs.getInt(1)));
			
		
			
		}
		catch(Exception ex)	{
			System.out.println(ex.getMessage());
		}
		
		}
	@Test
	public void testTabeliKraju(){
		
		Random gen = new Random();
		String test1 = ""+gen.nextInt(5000);
		String test3 = ""+gen.nextInt(5000);
		
		
		
		
		Country krajTestowy = new Country(0, test1, "BZ");
		KolekcjeIOperacje.dopiszKraj(krajTestowy);		
		
		
		
		try{
			
			String queryStr = "SELECT * FROM COUNTRY WHERE NAME='"+test1+"'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryStr);
			
			assertEquals(true,rs.next());
			assertEquals(rs.getString(2),test1);
			
			krajTestowy.setName(test3);
			
			KolekcjeIOperacje.modyfikujWpisKraju(rs.getInt(1), krajTestowy );
			assertEquals(test3,KolekcjeIOperacje.pobierzKraj(rs.getInt(1)).getName());
			
			
			dataBaseOperations.kasujKraj(rs.getInt(1));
			assertEquals(null ,KolekcjeIOperacje.pobierzKraj(rs.getInt(1)));
			
			
		}
		catch(Exception ex)	{
			System.out.println(ex.getMessage());
		}
		
		}
	
	@Test
	public void testTabeliKoloru(){
		
		Random gen = new Random();
		String test1 = ""+gen.nextInt(5000);
		String test3 = ""+gen.nextInt(5000);
		
		
		
		
		Color kolorTestowy = new Color(0, test1, test3);
		KolekcjeIOperacje.dopiszKolor(kolorTestowy);		
		
		
		
		try{
			
			String queryStr = "SELECT * FROM COLOR WHERE LNAME='"+test1+"'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryStr);
			
			assertEquals(true,rs.next());
			assertEquals(rs.getString(2),test1);
			
			kolorTestowy.setLname(test3);
			
			KolekcjeIOperacje.modyfikujWpisKoloru(rs.getInt(1), kolorTestowy );
			assertEquals(test3,KolekcjeIOperacje.pobierzKolor(rs.getInt(1)).getLname());
			
			
			dataBaseOperations.kasujKolor(rs.getInt(1));
			assertEquals(null ,KolekcjeIOperacje.pobierzKolor(rs.getInt(1)));
			

			
		}
		catch(Exception ex)	{
			System.out.println(ex.getMessage());
		}
		
		}
	@After
	public void zamknij(){
		try {
			con.close();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
	}
	
		
	}


