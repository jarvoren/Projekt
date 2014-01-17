package mpr.proj;



import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	
	DateOfBirth dataUrodzinFormated = pobierzDateUrodzeniaOdUzytkownika();
	
	
	System.out.println("Podaj kolor konia");
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
	System.out.println("Podaj imie ojca a nastepnie date jego urodzenia");
	String imieOjca = EasyIn.getString();
	DateOfBirth dataUrodzeniaOjca = pobierzDateUrodzeniaOdUzytkownika();
	
	Horse ojciec = pobierzKonia(imieOjca , dataUrodzeniaOjca);
	System.out.println("Podaj imie matki a nastepnie date jej urodzenia");
	String imieMatki = EasyIn.getString();
	DateOfBirth dataUrodzeniaMatki = pobierzDateUrodzeniaOdUzytkownika();
	Horse matka = pobierzKonia(imieMatki , dataUrodzeniaMatki);
	System.out.println("Podaj imie hodowcy");
	String imieHodowcy = EasyIn.getString();
	Breeder hodowca = pobierzHodowceZBazy(imieHodowcy);
	if(hodowca == null)
	{
		System.out.println("Nie ma takiego hodowcy w bazie , dodaj go i sproboj ponownie"); 
		return;
	}
	
	
	Horse kon = new Horse(0, imie, plec, dataUrodzinFormated, kolorFormated,  matka, ojciec, hodowca);
	
	wpiszKoniaDoBazy(kon);
}

private static void wpiszKoniaDoBazy(Horse kon) {
	try{
		Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
		
		String queryStr = "INSERT INTO HORSE (NAME , SEX, COLOR , DOB, YEARONLY, DAM, SIRE , BREEDER) VALUES(?,?,?,?,?,?,?,?)";
		PreparedStatement stmt = con.prepareStatement(queryStr);
		stmt.setString(1, kon.getName());
        stmt.setInt(2, kon.getIntOfSex());
        stmt.setInt(3, pobierzKolorZBazy(kon.getColor().getLname()).getID());
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

private static Breeder pobierzHodowceZBazy(String imieHodowcy) {
	try{
		Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
		
		String queryStr = "SELECT * FROM BREEDER";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryStr);
		while(rs.next())	{
			if(rs.getString(1)==imieHodowcy){
			con.close();
			return new Breeder(rs.getLong(0), rs.getString(1), pobierzKraj(rs.getInt(3)));
			}
		}
		con.close();
		return null;
		
	}
	catch (Exception ex)	{
		System.out.println(ex.getMessage());
	}
	return null;

	
	
}

private static Horse pobierzKonia(String imieKonia,
		DateOfBirth dataUrodzeniakonia) {
	try{
	Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
	Set<Horse> dane = new HashSet<Horse>();
	String queryStr = "SELECT * FROM HORSE";
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(queryStr);
	while(rs.next())	{
		dane.add(new Horse(rs.getLong(0), rs.getString(1), Sex.valueOf(rs.getInt(2)), new DateOfBirth(rs.getDate(4)), new Color(rs.getInt(3)), pobierzKoniaZBazy(rs.getInt(7)), pobierzKoniaZBazy(rs.getInt(6)),pobierzHodowce(rs.getInt(8)) ));
	
	}
	con.close();
	
}
catch (Exception ex)	{
	System.out.println(ex.getMessage());
}

return null;
	
	
	
	
	
	
	
}

private static Breeder pobierzHodowce(int int1) {
	try{
	Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
	
	String queryStr = "SELECT * FROM BREEDER WHERE ID="+int1;
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery(queryStr);
	if (rs.next())
	{
		return new Breeder(int1, rs.getString(1), pobierzKraj(rs.getInt(2)));
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

private static Country pobierzKraj(int int1) {
	try{
		Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
		
		String queryStr = "SELECT * FROM COUNTRY WHERE ID="+int1;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryStr);
		if (rs.next())
		{
			return new Country(int1, rs.getString(1), rs.getString(2));
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

private static Horse pobierzKoniaZBazy(int int1) {
	try{
		Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
		
		String queryStr = "SELECT * FROM HORSE WHERE id="+int1;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryStr);
		if(rs.next())	{
			con.close();
			return new Horse(rs.getLong(0), rs.getString(1), Sex.valueOf(rs.getInt(2)), new DateOfBirth(rs.getDate(4)), new Color(rs.getInt(3)), pobierzKoniaZBazy(rs.getInt(7)), pobierzKoniaZBazy(rs.getInt(6)),pobierzHodowce(rs.getInt(8)) );
		
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
		List<Color> dane = new ArrayList<Color>();
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
	System.out.println("Podaj imie hodowcy");
	String imie = EasyIn.getString();
	System.out.println("Podaj kraj pochodzenia hodowcy");
	String nazwaKraju = EasyIn.getString();
	nazwaKraju.toUpperCase();
	Country kraj = pobierzKraj(nazwaKraju);
	if (kraj==null)
	{
		System.out.println("Nie ma takiego kraju w bazie , dopisz kraj i sproboj ponownie");
		return;
	}
	
	
	try{
		
		Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
		
		String queryStr = "INSERT INTO BEEDER (NAME , COUNTRY) VALUES (?,?)";
		PreparedStatement stmt = con.prepareStatement(queryStr);
		stmt.setString(1, imie);
        stmt.setInt(2, kraj.getId());
        
		
		stmt.executeUpdate();
		
		
		con.close();
		}
		
		
		

	catch (Exception ex)	{
		System.out.println(ex.getMessage());
	}
}

private static Country pobierzKraj(String nazwaKraju) {
	try	{
		Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");
		String queryStr = "SELECT * FROM COUNTRY";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryStr);
		while(rs.next())
		{
			if(rs.getString(1)==nazwaKraju)
			{
				return new Country(rs.getInt(0),rs.getString(1), rs.getString(2));
			}
		}
		
	}
	catch (Exception ex)	{
		System.out.println(ex.getMessage());
	}
	return null;
}

public static void dodajKraj() {
	// TODO Auto-generated method stub
	
}

public static void pokazKonie() {
	
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
