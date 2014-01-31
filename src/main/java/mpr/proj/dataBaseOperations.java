package mpr.proj;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.util.List;
import java.util.Map;









import mpr.proj.pedigree.*;

public class dataBaseOperations {

public static void dodajKonia() {
	
	KolekcjeIOperacje.wpiszKoniaDoBazy(KolekcjeIOperacje.pobierzKoniaOdUzytkownika());
}


public static void dodajHodowce() {
	
	KolekcjeIOperacje.dopiszHodowce(KolekcjeIOperacje.pobierzHodowceOdUzytkownika());
	
}

public static void dodajKraj() {
	
	KolekcjeIOperacje.dopiszKraj(KolekcjeIOperacje.pobierzKrajOdUzytkownika());
	
}

public static void pokazKonie() {
	Map<Integer,Horse> kolekcja =KolekcjeIOperacje.pobierzKolekcjeKonizBazy();
		
	
	for(Map.Entry<Integer,Horse> entry: kolekcja.entrySet()){
			if (entry.getValue() != null){
			System.out.println("Id: "+entry.getValue().getID()+" Imie:"+entry.getValue().getName()+" Płeć: "+entry.getValue().getSex().toString()+" Kolor:"+entry.getValue().getColor().getLname()+" Data Urodzin:"+entry.getValue().getDob().getDate().toString()+" Imie Ojca: "+KolekcjeIOperacje.ifHorseNullImie(entry.getValue().getSire())+" Id Ojca: "+KolekcjeIOperacje.ifHorseNullId(entry.getValue().getSire())+" Imie Matki:"+KolekcjeIOperacje.ifHorseNullImie(entry.getValue().getDam())+" Id Matki: "+KolekcjeIOperacje.ifHorseNullId(entry.getValue().getDam())+" Imie hodowcy: "+entry.getValue().getBreeder().getName()+" Id Hodowcy: "+entry.getValue().getBreeder().getId()+" Kraj Pochodzenia: "+entry.getValue().getBreeder().getCountry().getName());	
			}
		}
	
}


public static void pokazHodowcow() {
	Map<Integer,Breeder> kolekcja =KolekcjeIOperacje.pobierzKolekcjeHodowcowZBazy();
	
	for(Map.Entry<Integer,Breeder> entry: kolekcja.entrySet()){
		
		System.out.println("Id Hodowcy: "+entry.getValue().getId()+" Imie Hodowcy:"+ entry.getValue().getName()+" Kraj pochodzenia: "+entry.getValue().getCountry().getName());
	}
		

}

public static void modyfikujKonia() {
	System.out.println("Podaj numer wpisu do edycji");
	
	KolekcjeIOperacje.modyfikujWpisKonia(EasyIn.getInt() , KolekcjeIOperacje.pobierzKoniaOdUzytkownika());
	
}



public static void modyfikujHodowce() {
	System.out.println("Podaj numer wpisu do edycji");

	KolekcjeIOperacje.modyfikujWpisHodowcy(EasyIn.getInt() ,KolekcjeIOperacje.pobierzHodowceOdUzytkownika());
	
	
	
	
	
}


public static void wyszukajPotomstwoKonia() {
	System.out.println("Podaj id konia którego potomkow szukamy");
	int wybor = EasyIn.getInt();
	System.out.println("Potomkowie konia "+ KolekcjeIOperacje.pobierzKonia(wybor).getName());
	wyszukajPotomstwo(KolekcjeIOperacje.pobierzKolekcjeKonizBazy(),KolekcjeIOperacje.pobierzKonia(wybor) );
	
}

private static void wyszukajPotomstwo(Map<Integer ,Horse> kolekcja ,Horse kon) {
	
	for(Map.Entry<Integer,Horse> entry: kolekcja.entrySet())
	{
		if(KolekcjeIOperacje.ifHorseNullId(entry.getValue().getSire())==kon.getID() || KolekcjeIOperacje.ifHorseNullId(entry.getValue().getDam())==kon.getID())
		{
		System.out.println(" Imie: "+entry.getValue().getName()+" ID"+entry.getValue().getID());
		}
		
	}
	
}


public static void wygenerujRodowodKonia() {
	System.out.println("Podaj id konia do rodowodu");
	int wybor = EasyIn.getInt();
	System.out.println("Podaj ilość pokoleń w rodowodzie");
	int glebokosc = EasyIn.getInt();
	Export.exportPDF(KolekcjeIOperacje.pobierzKonia(wybor), glebokosc);
	
	
}

public static void kasujKonia(int wybor) {
	
	 try        {
         Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
         String queryStr = "DELETE FROM HORSE WHERE ID=(?)";
         PreparedStatement stmt = con.prepareStatement(queryStr);
         stmt.setInt(1, wybor);
         stmt.executeUpdate();
 }
 catch (Exception ex) {
         System.out.println(ex.getMessage());
 }        
	
}

public static void kasujHodowce(int wybor) {
	
	 try        {
         Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
         String queryStr = "DELETE FROM BREEDER WHERE ID=(?)";
         PreparedStatement stmt = con.prepareStatement(queryStr);
         stmt.setInt(1, wybor);
         stmt.executeUpdate();
 }
	 catch (Exception ex) {
         System.out.println(ex.getMessage());
 }   
}


public static void dodajKolor() {
	KolekcjeIOperacje.dopiszKolor(KolekcjeIOperacje.pobierzKolorOdUzytkownika());
	
}
public static void kasujKolor(int wybor) {
	
	 try        {
         Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
         String queryStr = "DELETE FROM COLOR WHERE ID=(?)";
         PreparedStatement stmt = con.prepareStatement(queryStr);
         stmt.setInt(1, wybor);
         stmt.executeUpdate();
 }
	 catch (Exception ex) {
         System.out.println(ex.getMessage());
 }   
}


public static void pokazKraje() {
List<Country> kolekcja =KolekcjeIOperacje.pobierzKolekcjeKrajowZBazy();
	
	for(Country entry: kolekcja){
		
		System.out.println("Id: "+entry.getId()+" Nazwa:"+entry.getName()+"Nazwa Kodowa: "+entry.getCode());
	}
	
}


public static void kasujKraj(int wybor) {
	
	 try        {
         Connection con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/workdb","sa","");;
         String queryStr = "DELETE FROM COUNTRY WHERE ID=(?)";
         PreparedStatement stmt = con.prepareStatement(queryStr);
         stmt.setInt(1, wybor);
         stmt.executeUpdate();
 }
	 catch (Exception ex) {
         System.out.println(ex.getMessage());
 }   
	
}


public static void pokazKolory() {
List<Color> kolekcja =KolekcjeIOperacje.pobierzKolekcjeKolorowZBazy();
	
	for(Color entry: kolekcja){
		
		System.out.println("Id: "+entry.getID()+" Nazwa:"+entry.getLname()+" Nazwa skrocona: "+entry.getSname());
	}
	
}


public static void modyfikujKraj() {
	System.out.println("Podaj numer wpisu do edycji");
	KolekcjeIOperacje.modyfikujWpisKraju(EasyIn.getInt(),KolekcjeIOperacje.pobierzKrajOdUzytkownika());

	
	
	
}


public static void modyfikujKolor() {
	System.out.println("Podaj numer wpisu do edycji");
	KolekcjeIOperacje.modyfikujWpisKoloru(EasyIn.getInt(),KolekcjeIOperacje.pobierzKolorOdUzytkownika());

	
}


	
}
