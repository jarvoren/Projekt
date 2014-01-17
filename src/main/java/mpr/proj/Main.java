package mpr.proj;
import java.lang.*;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;

import mpr.proj.*;



public class Main {
    public static void main(String[] args) {
      int w=0;
    	do{
    		
    	
    	
    	System.out.println("Menu");
        System.out.println("1) Dodaj wpis");
        System.out.println("2) Wypisz tabele");
        System.out.println("3) Updatuj wpis");
        System.out.println("4) Skasuj wpis");
        System.out.println("5) Wyszukaj potomstwo konia");
        System.out.println("6) Generuj rodowód w pdf");
        System.out.println("7) Wyjscie");
        
        
        
        switch(EasyIn.getInt())
        {
        case 1: {
        	insideMenus.dodawanieWpisu();
        	
        	break;
        }
        case 2: {
        	
        	insideMenus.wypisywanieDanych();
        	break;
        }
        case 3:{
        	insideMenus.updateDanych();
        	break;
        }
        case 4:{
        	insideMenus.kasowanieDanych();
        	break;
        }
        case 5:{
        	insideMenus.wyszukiwaniePotomstwa();
        	break;
        }
        case 6:{
        	insideMenus.generowanieRodowodu();
        	break;
        }
        case 7:{
        	w=1;
        	break;
        }
        
        
        default :{
        	 System.out.println("Nieprawidłowy wybór try again");
        	 EasyIn.getChar();
        	
        }
        }
        
        
        
        
    	}while(w==0);
    }

}
