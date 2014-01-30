import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
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

import org.junit.Test;


public class TestyOperacjiSql {

	
	@Test
	public void testTabeliHodowca(){
		
		Random gen = new Random();
		String test = ""+gen.nextInt(5000);
		
		
		
		
		
		Breeder hodowcaTestowy = new Breeder(0,test,new Country(7, "Angli", "AN"));
		KolekcjeIOperacje.dopiszHodowce(hodowcaTestowy);
		
		
		try{
			Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
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
			
			con.close();
			
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
			Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
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
			
			con.close();
			
		}
		catch(Exception ex)	{
			System.out.println(ex.getMessage());
		}
		
		}
		
	}


