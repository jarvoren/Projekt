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
import java.util.Map.Entry;
import java.util.Set;

import mpr.proj.pedigree.*;

public abstract class dataBaseOperations {

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
		
			System.out.println("Id: "+entry.getValue().getID()+" Płeć: "+entry.getValue().getSex().toString()+" Kolor:"+entry.getValue().getColor().getLname()+" Data Urodzin:"+entry.getValue().getDob().getDate().toString()+" Imie Ojca: "+KolekcjeIOperacje.ifHorseNullImie(entry.getValue().getSire())+" Id Ojca: "+KolekcjeIOperacje.ifHorseNullId(entry.getValue().getSire())+" Imie Matki:"+KolekcjeIOperacje.ifHorseNullImie(entry.getValue().getDam())+" Id Matki: "+KolekcjeIOperacje.ifHorseNullId(entry.getValue().getDam())+" Imie hodowcy: "+entry.getValue().getBreeder().getName()+" Id Hodowcy: "+entry.getValue().getBreeder().getId()+" Kraj Pochodzenia: "+entry.getValue().getBreeder().getCountry().getName());	
		}
	
}

public static void pokazHodowcow() {
	Map<Integer,Breeder> kolekcja =KolekcjeIOperacje.pobierzKolekcjeHodowcowZBazy();
	
	for(Map.Entry<Integer,Breeder> entry: kolekcja.entrySet()){
		
		System.out.println("Id Hodowcy: "+entry.getValue().getId()+" Imie Hodowcy:"+ entry.getValue().getName()+" Kraj pochodzenia: ");
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
	System.out.println("Podaj głębokość wyszukiwania");
	int glebokosc = EasyIn.getInt();
	System.out.println("Podaj id konia którego potomkow szukamy");
	int wybor = EasyIn.getInt();
	System.out.println("Potomkowie konia "+ KolekcjeIOperacje.pobierzKonia(wybor).getName());
	wyszukajPotomstwo(glebokosc , wybor);
	
}

private static void wyszukajPotomstwo(int glebokosc, int wybor) {
	glebokosc--;
	Map<Integer ,Horse> kolekcja = KolekcjeIOperacje.pobierzKolekcjeKonizBazy();
	for(Map.Entry<Integer,Horse> entry: kolekcja.entrySet())
	{
		if(wybor==entry.getValue().getDam().getID())
		{
			System.out.println(entry.getValue().getName());
			if(glebokosc!=0){wyszukajPotomstwo(glebokosc, (int) entry.getValue().getID());}
		}
		if(wybor==entry.getValue().getSire().getID())
		{
			System.out.println(entry.getValue().getName());
			if(glebokosc!=0){wyszukajPotomstwo(glebokosc, (int) entry.getValue().getID());}
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
