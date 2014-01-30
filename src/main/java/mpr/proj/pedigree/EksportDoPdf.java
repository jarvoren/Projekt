package mpr.proj.pedigree;




	import java.io.FileOutputStream;

	import mpr.proj.pedigree.Horse;

	import com.itextpdf.text.Anchor;
	import com.itextpdf.text.BadElementException;
	import com.itextpdf.text.Chapter;
	import com.itextpdf.text.Document;

	import com.itextpdf.text.Element;
	import com.itextpdf.text.Font;

	import com.itextpdf.text.PageSize;
	import com.itextpdf.text.Paragraph;
	import com.itextpdf.text.Phrase;
	import com.itextpdf.text.Section;
	import com.itextpdf.text.pdf.PdfPCell;
	import com.itextpdf.text.pdf.PdfPTable;
	import com.itextpdf.text.pdf.PdfWriter;

	public abstract class EksportDoPdf {
		  
		private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		
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


