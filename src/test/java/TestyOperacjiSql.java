import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import mpr.proj.dataBaseOperations;
import mpr.proj.pedigree.Breeder;
import mpr.proj.pedigree.Color;
import mpr.proj.pedigree.Country;
import mpr.proj.pedigree.KolekcjeIOperacje;

import org.junit.Test;


public class TestyOperacjiSql {

	
	@Test
	public void testTabeliHodowca(){
		
		Random gen = new Random();
		Breeder zachowaj = KolekcjeIOperacje.pobierzHodowce(5);
		String test = ""+gen.nextInt(5000);
		
		KolekcjeIOperacje.modyfikujWpisHodowcy(5, new Breeder(0,test,new Country(7, "Angli", "AN")));
			assertEquals(test,KolekcjeIOperacje.pobierzHodowce(5).getName());
		
		Breeder hodowcaTestowy = new Breeder(0,test,new Country(7, "Angli", "AN"));
		KolekcjeIOperacje.dopiszHodowce(hodowcaTestowy);
		
		try{
			Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
			String queryStr = "SELECT * FROM BREEDER WHERE NAME='"+test+"'";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryStr);
			assertEquals(true,rs.next());
			assertEquals(rs.getString(2),test);
			dataBaseOperations.kasujHodowce(rs.getInt(1));
			con.close();
			
		}
		catch(Exception ex)	{
			System.out.println(ex.getMessage());
		}
		
		KolekcjeIOperacje.modyfikujWpisHodowcy(5, zachowaj);
		}
		
	}


