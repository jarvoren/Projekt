package mpr.proj;



import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	
	
	System.out.println("Podaj id koloru konia");
	int kolorId = EasyIn.getInt();
	System.out.println("Podaj id");
	int idOjca = EasyIn.getInt();
	DateOfBirth dataUrodzeniaOjca = pobierzDateUrodzeniaOdUzytkownika();
	
	Horse ojciec = KolekcjeIOperacje.pobierzKonia(idOjca);
	System.out.println("Podaj id matki");
	int idMatki = EasyIn.getInt();
	DateOfBirth dataUrodzeniaMatki = pobierzDateUrodzeniaOdUzytkownika();
	Horse matka = KolekcjeIOperacje.pobierzKonia(idMatki);
	System.out.println("Podaj id hodowcy");
	int id = EasyIn.getInt();
	Breeder hodowca = KolekcjeIOperacje.pobierzHodowce(id);
	Horse kon = new Horse(0, imie, plec, dataUrodzinFormated,KolekcjeIOperacje.pobierzKolor(kolorId),  matka, ojciec, hodowca);
	
	KolekcjeIOperacje.wpiszKoniaDoBazy(kon);
}

private static boolean sprawdzCzyKolorJestWBazie(String kolor) {
	List<Color> kolekcja = KolekcjeIOperacje.pobierzKoloryZBazy();
	for(Color c : kolekcja)
	{
		if(c.getLname()==kolor || c.getSname()==kolor)
		{
			return true;
		}
	}
	return false;
	
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
			return new Breeder(rs.getLong(1), rs.getString(2),KolekcjeIOperacje.pobierzKraj(rs.getInt(3)));
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
		dane.add(new Horse(rs.getLong(1), rs.getString(2), Sex.valueOf(rs.getInt(3)), new DateOfBirth(rs.getDate(5)), pobierzKolor(rs.getInt(4)), pobierzKoniaZBazy(rs.getInt(8)), pobierzKoniaZBazy(rs.getInt(7)),pobierzHodowce(rs.getInt(9)) ));
	
	}
	con.close();
	for(Horse h : dane)
	{
		if(h.getName()== imieKonia && h.getDob()== dataUrodzeniakonia)
		{
			con.close();
			return h;
		}
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
			dane.add(new Color(rs.getInt(1), rs.getString(2), rs.getString(3)));
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
				return new Country(rs.getInt(1),rs.getString(2), rs.getString(3));
			}
		}
		
	}
	catch (Exception ex)	{
		System.out.println(ex.getMessage());
	}
	return null;
}

public static void dodajKraj() {
	System.out.println("Podaj nazwę kraju jaki chcesz dodać");
	String nazwa = EasyIn.getString();
	System.out.println("Podaj Kod Kraju");
	String kod = EasyIn.getString();

	
	try{
		
		Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
		
		String queryStr = "INSERT INTO COUNTRY (NAME , CODE) VALUES (?,?)";
		PreparedStatement stmt = con.prepareStatement(queryStr);
		stmt.setString(1, nazwa);
        stmt.setString(2, kod);
        	
		stmt.executeUpdate();
			
		con.close();
		}
		
		
		

	catch (Exception ex)	{
		System.out.println(ex.getMessage());
	}
	
}

public static void pokazKonie() {
	try	{
		Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");
		String queryStr = "SELECT * FROM HORSE";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryStr);
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+Sex.valueOf(rs.getInt(3)).toString()+"  "+ pobierzKolorZBazy(rs.getInt(4)).getLname()+"  "+ rs.getDate(5).toString()+"Matka: "+ pobierzKoniaZBazy(rs.getInt(7)).getName()+"  Ojciec:  "+pobierzKoniaZBazy(rs.getInt(8)).getName()+"  Hodowca:  "+pobierzHodowce(rs.getInt(9)));	
		}
		
	}
	catch (Exception ex)	{
		System.out.println(ex.getMessage());
	}
	
	
}




public static void pokazHodowcow() {
	try	{
		Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");
		String queryStr = "SELECT * FROM BREEDER";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryStr);
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"   "+"  "+rs.getString(2)+"   "+pobierzKraj(rs.getInt(3)).getName());	
		}
		
	}
	catch (Exception ex)	{
		System.out.println(ex.getMessage());
	}
	
}

public static void modyfikujKonia() {
	System.out.println("Podaj numer wpisu do edycji");
	int wybor = EasyIn.getInt();
	Horse kon = pobierzKoniaZBazy(wybor);
	System.out.println("Podaj imie konia");
	kon.setName(EasyIn.getString());
	System.out.println("Podaj płeć konia 1) Gelding 2) Mare 3) Stallion");
	kon.setSex(Sex.valueOf(EasyIn.getInt()));
	
	kon.setDob(pobierzDateUrodzeniaOdUzytkownika());
	
	
	System.out.println("Podaj kolor konia");
	
	String kolor = EasyIn.getString();
	kolor.toLowerCase();
	if(sprawdzCzyKolorJestWBazie(kolor)){
		kon.setColor(pobierzKolorZBazy(kolor));
	}
	else
	{
		System.out.println("Nie ma takiego koloru w bazie dodaj kolor i sproboj ponownie");
		return;
	}
	System.out.println("Podaj imie ojca a nastepnie date jego urodzenia");
	String imieOjca = EasyIn.getString();
	DateOfBirth dataUrodzeniaOjca = pobierzDateUrodzeniaOdUzytkownika();
	
	kon.setSire(pobierzKonia(imieOjca , dataUrodzeniaOjca));
	System.out.println("Podaj imie matki a nastepnie date jej urodzenia");
	String imieMatki = EasyIn.getString();
	DateOfBirth dataUrodzeniaMatki = pobierzDateUrodzeniaOdUzytkownika();
	kon.setDam(pobierzKonia(imieMatki , dataUrodzeniaMatki));
	System.out.println("Podaj imie hodowcy");
	String imieHodowcy = EasyIn.getString();
	kon.setBreeder(pobierzHodowceZBazy(imieHodowcy));
	if(kon.getBreeder() == null)
	{
		System.out.println("Nie ma takiego hodowcy w bazie , dodaj go i sproboj ponownie"); 
		return;
	}
	modyfikujWpisKonia(kon);
	
}

private static void modyfikujWpisKonia(Horse kon) {
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
        stmt.setInt(9,(int) kon.getID());
        stmt.executeUpdate(); 
        
}
catch (Exception ex) {
        System.out.println(ex.getMessage());
}
	
}

public static void modyfikujHodowce() {
	System.out.println("Podaj numer wpisu do edycji");
	int wybor = EasyIn.getInt();
	Breeder hodowca = pobierzHodowce(wybor);
	
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
	try        {
        Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
        String queryStr = "UPDATE BREEDER SET NAME=(?), COUNTRY=(?) WHERE ID=(?)";
        PreparedStatement stmt = con.prepareStatement(queryStr);
        stmt.setString(1, imie);
        stmt.setString(2, kraj.getName());
        stmt.setInt(3, wybor);
        stmt.executeUpdate(); 
        
}
catch (Exception ex) {
        System.out.println(ex.getMessage());
}
	
	
	
	
	
}

public static void pokazKonia() {

	System.out.println("Wyszukaj konia po 1) Imieniu 2) ID");
	switch(EasyIn.getInt())
	{
	case 1:
	{
		try{
		System.out.println("Podaj imie konia");
		Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");
		String queryStr = "SELECT * FROM HORSE WHERE NAME="+EasyIn.getString();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryStr);
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+Sex.valueOf(rs.getInt(3)).toString()+"  "+ pobierzKolorZBazy(rs.getInt(4)).getLname()+"  "+ rs.getDate(5).toString()+"Matka: "+ pobierzKoniaZBazy(rs.getInt(7)).getName()+"  Ojciec:  "+pobierzKoniaZBazy(rs.getInt(8)).getName()+"  Hodowca:  "+pobierzHodowce(rs.getInt(9)));
		}
		con.close();
		
	}
	catch(Exception ex)	{
		System.out.println(ex.getMessage());
	}
	
	
		
		break;
	}
	case 2:
	{
		System.out.println("Podaj ID konia");
		int wybor = EasyIn.getInt();
		
		wyswietlKoniaPoId(wybor);
		
		break;
	}
	default: {System.out.println("Kiepski wybor");}
	}
	
}

private static void wyswietlKoniaPoId(int wybor) {
	try{
		Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");
		String queryStr = "SELECT * FROM HORSE WHERE ID="+wybor;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(queryStr);
		while(rs.next())
		{
			System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+Sex.valueOf(rs.getInt(3)).toString()+"  "+ pobierzKolorZBazy(rs.getInt(4)).getLname()+"  "+ rs.getDate(5).toString()+"Matka: "+ pobierzKoniaZBazy(rs.getInt(7)).getName()+"  Ojciec:  "+pobierzKoniaZBazy(rs.getInt(8)).getName()+"  Hodowca:  "+pobierzHodowce(rs.getInt(9)));
		}
		con.close();
		
	}
	catch(Exception ex)	{
		System.out.println(ex.getMessage());
	}
	
}

public static void wyszukajPotomstwoKonia() {
	System.out.println("Podaj głębokość wyszukiwania");
	int glebokosc = EasyIn.getInt();
	System.out.println("Podaj id konia którego potomkow szukamy");
	int wybor = EasyIn.getInt();
	System.out.println("Potomkowie konia "+pobierzKoniaZBazy(wybor).getName());
	wyszukajPotomstwo(glebokosc , wybor);
	
}

private static void wyszukajPotomstwo(int glebokosc, int wybor) {
	glebokosc--;
	Set<Horse> dane = pobierzKolekcjeKonizBazy();
	for(Horse h : dane)
	{
		if(wybor==h.getDam().getID())
		{
			System.out.println(h.getName());
			if(glebokosc!=0){wyszukajPotomstwo(glebokosc, (int) h.getID());}
		}
		if(wybor==h.getSire().getID())
		{
			System.out.println(h.getName());
			if(glebokosc!=0){wyszukajPotomstwo(glebokosc, (int) h.getID());}
		}
	}
	
}


public static void wygenerujRodowodKonia() {
	System.out.println("Podaj id konia do rodowodu");
	int wybor = EasyIn.getInt();
	System.out.println("Podaj ilość pokoleń w rodowodzie");
	
	
}

public static void kasujKonia() {
	System.out.println("Podaj id konia do skasowania");
	int wybor = EasyIn.getInt();
	 try        {
         Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
         String queryStr = "DELETE FROM HORSE WHERE ID=(?)";
         PreparedStatement stmt = con.prepareStatement(queryStr);
         stmt.setInt(1, wybor);
         
 }
 catch (Exception ex) {
         System.out.println(ex.getMessage());
 }        
	
}

public static void kasujHodowce() {
	System.out.println("Podaj id Hodowcy do skasowania");
	int wybor = EasyIn.getInt();
	 try        {
         Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
         String queryStr = "DELETE FROM BREEDER WHERE ID=(?)";
         PreparedStatement stmt = con.prepareStatement(queryStr);
         stmt.setInt(1, wybor);
         
 }
	 catch (Exception ex) {
         System.out.println(ex.getMessage());
 }   
}

	
}
