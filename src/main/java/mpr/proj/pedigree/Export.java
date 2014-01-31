package mpr.proj.pedigree;






import java.io.FileOutputStream;

import com.lowagie.text.Anchor;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Section;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.*;


public abstract class Export {
	  
	private static Font catFont = new Font(Font.TIMES_ROMAN , Font.BOLD);
	
	public static void exportPDF(Horse kon, int glebokosc)	{
		try {
			System.out.println("tworzenie pliku...");
			Document document = new Document(PageSize.A4.rotate());
			PdfWriter.getInstance(document, new FileOutputStream(kon.getName()+"_ancestors.pdf"));
			document.open();
			document.addTitle("Drzewo rodowodowe");
			Anchor anchor = new Anchor("Drzewo rodowodowe konia "+kon.getName(), catFont);
			Chapter catPart = new Chapter(new Paragraph(anchor), 1);
			createTable(catPart, kon, glebokosc);
			document.add(catPart);
			document.close();
			System.out.println("Wyeksportowano do pliku "+kon.getName()+"_ancestors.pdf");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void pobierzRodzicow(Horse kon, int glebokosc, PdfPTable table)	{
		PdfPCell cell;
        String imie;
        if (kon != null) {
        	imie = kon.getName();
        } else {
        	imie = "Nieznane";
        }
        cell = new PdfPCell(new Phrase(imie));
        cell.setRowspan((int) Math.pow(2,glebokosc));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        if (kon.getSire() != null && glebokosc > 0) {
        	pobierzRodzicow(kon.getSire(), glebokosc-1, table);
        } else if (kon.getSire() == null && glebokosc > 0) {
        	pobierzRodzicow(new Horse(), glebokosc-1,table);
        }
        if (kon.getDam() != null && glebokosc > 0) {
        	pobierzRodzicow(kon.getDam(), glebokosc-1, table);
        } else if (kon.getDam() == null && glebokosc > 0) {
        	pobierzRodzicow(new Horse(), glebokosc-1, table);
        }
	}
	
	private static void createTable(Section subCatPart, Horse kon, int glebokosc) throws BadElementException {
		PdfPTable table = new PdfPTable(glebokosc+1);
		pobierzRodzicow(kon, glebokosc, table);
	    subCatPart.add(table);
	}

	
}


