import static org.junit.Assert.*;
import mpr.proj.dataBaseOperations;
import mpr.proj.pedigree.Breeder;
import mpr.proj.pedigree.Country;
import mpr.proj.pedigree.KolekcjeIOperacje;

import org.junit.Test;


public class TestyOperacjiSql {

	@Test
	public void test() {
		
		
		KolekcjeIOperacje.dopiszHodowce(new Breeder(9999, "Zenek", new Country(7, "Angli", "AN")));
		
	}
	@Test
	public void testModify(){
		
		Breeder zachowaj = KolekcjeIOperacje.pobierzHodowce(5);
		
		KolekcjeIOperacje.modyfikujWpisHodowcy(5, new Breeder(0,"Staszek",new Country(7, "Angli", "AN")));
			assertEquals(KolekcjeIOperacje.pobierzHodowce(5).getName(),"Staszek");
		KolekcjeIOperacje.modyfikujWpisHodowcy(5, zachowaj);
		assertEquals(KolekcjeIOperacje.pobierzHodowce(5).getId(),5);
		assertEquals(KolekcjeIOperacje.pobierzHodowce(5).getCountry().getId(),1);
	}

}
