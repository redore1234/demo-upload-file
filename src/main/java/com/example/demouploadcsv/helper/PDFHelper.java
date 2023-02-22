package com.example.demouploadcsv.helper;

import com.example.demouploadcsv.domain.Tutorial;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.lowagie.text.pdf.PdfWriter.*;

/**
 * The type Pdf helper.
 */
public class PDFHelper {
    private enum pdfElement {
        /**
         * Id pdf element.
         */
        ID,
        /**
         * Title pdf element.
         */
        Title,
        /**
         * Description pdf element.
         */
        Description,
        /**
         * Published pdf element.
         */
        Published
    }

    /**
     * Generate pdf byte array input stream.
     *
     * @param tutorials the tutorials
     * @return the byte array input stream
     * @throws DocumentException the document exception
     */
    public static ByteArrayInputStream generatePDF(List<Tutorial> tutorials) throws DocumentException {
        //Create Document Object
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Creating font
        // Setting font style and size
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN,20, Font.BOLD, Color.BLACK);
        // Creating paragraph
        Paragraph paTitle = new Paragraph("LIST OF THE TUTORIALS", fontTitle);
        // Aligning the paragraph in the document
        paTitle.setAlignment(Paragraph.ALIGN_CENTER);

            // Creating a table of the 4 columns
        PdfPTable table = new PdfPTable(4);
        // Setting width of the table, its columns and spacing
        table.setWidthPercentage(100f);
        table.setWidths(new int[] {4,6,6,4});
        table.setSpacingBefore(5);
        table.setSpacingAfter(5);
        // Creating font
        // Setting font style and size
        Font fontHeader = FontFactory.getFont(FontFactory.TIMES_ROMAN,12,Font.BOLD, Color.BLACK);
        // Adding headings in the created table cell or header
        // Adding cell to table
//        table.addCell(new PdfPCell(new Phrase(String.valueOf(pdfElement.ID), fontHeader)));
//        table.addCell(new PdfPCell(new Phrase(String.valueOf(pdfElement.Title), fontHeader)));
//        table.addCell(new PdfPCell(new Phrase(String.valueOf(pdfElement.Description), fontHeader)));
//        table.addCell(new PdfPCell(new Phrase(String.valueOf(pdfElement.Published), fontHeader)));
        PdfPCell hCell;
        hCell = new PdfPCell(new Phrase(String.valueOf(pdfElement.ID), fontHeader));
        hCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hCell);
        hCell = new PdfPCell(new Phrase(String.valueOf(pdfElement.Title), fontHeader));
        hCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hCell);
        hCell = new PdfPCell(new Phrase(String.valueOf(pdfElement.Description), fontHeader));
        hCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hCell);
        hCell = new PdfPCell(new Phrase("Published", fontHeader));
        hCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hCell);

        // Iterating list of tutorials
        for (Tutorial tutorial : tutorials) {
            // Adding data to each cell
            table.addCell(new PdfPCell(new Phrase(String.valueOf(tutorial.getId()))));
            table.addCell(new PdfPCell(new Phrase(tutorial.getTitle())));
            table.addCell(new PdfPCell(new Phrase(tutorial.getDescription())));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(tutorial.isPublished()))));
        }
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        // Add header to PDF
        addHeaderToPDF(document);
        // Add footer to PDF
        addFooterToPDF(document);
        // Open the created document
        document.open();
        // Adding the created paragraph in the document
        document.add(paTitle);
        document.add(table);

//        // start second page
//        document.newPage();
//        // Creating a table of the 4 columns
//        PdfPTable table2 = new PdfPTable(3);
//        // Setting width of the table, its columns and spacing
//        table2.setWidthPercentage(100f);
//        table2.setWidths(new int[] {4,6,6});
//        table2.setSpacingBefore(5);
//        table2.setSpacingAfter(5);
//        // Iterating list of tutorials
//        for (Tutorial tutorial : tutorials) {
//            // Adding data to each cell
//            table2.addCell(new PdfPCell(new Phrase(String.valueOf(tutorial.getId()))));
//            table2.addCell(new PdfPCell(new Phrase(tutorial.getTitle())));
//            table2.addCell(new PdfPCell(new Phrase(tutorial.getDescription())));
//        }
//        document.add(table2);
        // Close the created document
        document.close();

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private static void addHeaderToPDF(Document document) throws DocumentException {
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        // Creating font
        // Setting font style and size
        Font fontHeader = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontHeader.setSize(10);
        String contentHeader = "Date: " +currentDateTime + "\n"
                                + "Location: Ho Chi Minh City" + "\n";

        HeaderFooter header = new HeaderFooter(
                new Phrase(contentHeader, fontHeader), false);
        header.setBorder(Rectangle.NO_BORDER);
        header.setAlignment(Element.ALIGN_RIGHT);
        document.setHeader(header);
    }

    private static void addFooterToPDF(Document document) throws DocumentException {
        Font fontFooter = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontFooter.setSize(10);
        HeaderFooter footer = new HeaderFooter(
                new Phrase("Page: ", fontFooter), true);
        footer.setBorder(Rectangle.NO_BORDER);
        footer.setAlignment(Element.ALIGN_CENTER);
        document.setFooter(footer);
    }
}
