package mpr.proj;



import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mpr.proj.pedigree.*;

public abstract class dataBaseOperations {

public static void dodajKonia() {
	EasyIn.clear();
	System.out.println("Podaj imie konia");
	String imie = EasyIn.getString();
	System.out.println("Podaj płeć konia 1) Gelding 2) Mare 3) Stallion");
	Sex plec = Sex.valueOf(EasyIn.getInt());
	System.out.println("Podaj rok urodzenia konia");
	Date dataUrodzin = null;
	dataUrodzin.setYear(EasyIn.getInt());
	System.out.println("Podaj miesiac urodzenia konia");
	dataUrodzin.setMonth(EasyIn.getInt());
	System.out.println("Podaj dzien urodzenia konia");
	dataUrodzin.setDate(EasyIn.getInt());
	DateOfBirth dataUrodzinFormated = new DateOfBirth();
	dataUrodzinFormated.setDate(dataUrodzin, false);
	
	System.out.println("Kolor konia");
	Color kolorFormated = null;
	String kolor = EasyIn.getString();
	kolor.toLowerCase();
	if(sprawdzCzyKolorJestWBazie(kolor)){
		kolorFormated=pobierzKolorZBazy(kolor);
	}
	else
	{
		System.out.println("Nie ma takiego koloru w bazie dodaj kolor i sproboj ponownie");
		return;
	}
	
	
	
	
	Horse kon = new Horse(0, imie, plec, dataUrodzinFormated, kolorFormated, kon, kon, null);
}

private static Color pobierzKolorZBazy(String kolor) {
	try	{
		Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
		Set<Color> dane = new HashSet<Color>();
		String queryStr = "SELECT * FROM COLOR";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryStr);
		while(rs.next())	{
			dane.add(new Color(rs.getInt(0), rs.getString(1), rs.getString(2)));
		}
		for(Color c: dane)
		{
			if(c.getLname()==kolor || c.getSname()==kolor)
			{
				con.close();
				return c;
			}
		}
		con.close();
		
	}
	catch (Exception ex)	{
		System.out.println(ex.getMessage());
	}
	
	return null;
}

public static boolean sprawdzCzyKolorJestWBazie(String kolor) {
	try	{
		Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
		Set<Color> dane = new HashSet<Color>();
		String queryStr = "SELECT * FROM COLOR";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryStr);
		while(rs.next())	{
			dane.add(new Color(rs.getInt(0), rs.getString(1), rs.getString(2)));
		}
		for(Color c: dane)
		{
			if(c.getLname()==kolor || c.getSname()==kolor)
			{
				con.close();
				return true;
			}
		}
		con.close();
	}
	catch (Exception ex)	{
		System.out.println(ex.getMessage());
	}
	return false;

	
}

public static void dodajHodowce() {
	// TODO Auto-generated method stub
	
}

public static void dodajKraj() {
	// TODO Auto-generated method stub
	
}

public static void pokazKonie() {
	Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
	

	List<Horse> lista1 = new ArrayList<Horse>();
	String queryStr = "SELECT * FROM HORSE";
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(queryStr);
	while(rs.next())	{
		Sex.STALLION.toString();
		Horse e = new Horse(rs.getLong("ID"), rs.getString(1), Sex.valueOf(rs.getInt("SEX")), rs.getDate(3),new Color ( rs.getString("COLOR")), e, e, null);
		lista1.add(e);
	}

}

public static void pokazHodowcow() {
	// TODO Auto-generated method stub
	
}

public static void modyfikujKonia() {
	// TODO Auto-generated method stub
	
}

public static void modyfikujHodowce() {
	// TODO Auto-generated method stub
	
}

public static void pokazKonia() {
	// TODO Auto-generated method stub
	
}

public static void wyszukajPotomstoKonia() {
	// TODO Auto-generated method stub
	
}

public static void wygenerujRodowodKonia() {
	// TODO Auto-generated method stub
	
}

	
}
