package com.bhargo.user.generatefiles;

import android.content.Context;

import com.bhargo.user.Java_Beans.ControlObject;
import com.bhargo.user.Java_Beans.DataTableColumn_Bean;
import com.bhargo.user.utils.ImproveHelper;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GeneratePDFReport {
    public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD);
    public static Font FONT_SUBTITLE = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    public static Font FONT_BODY = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    public static Font FONT_TABLE_HEADER = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    public static Font FONT_HEADER_FOOTER = new Font(Font.FontFamily.UNDEFINED, 7, Font.ITALIC);
    private static List<DataTableColumn_Bean> dataTableColumn_beans;
    private static List<List<String>> llTableData = new ArrayList<>();
    private static ControlObject controlObject;
    //we will add some products to arrayListRProductModel to show in the PDF document


    //creating a PdfWriter variable. PdfWriter class is available at com.itextpdf.text.pdf.PdfWriter
    private PdfWriter pdfWriter;

    /**
     * iText allows to add metadata to the PDF which can be viewed in your Adobe Reader. If you right click
     * on the file and to to properties then you can see all these information.
     *
     * @param document
     */
    private static void addMetaData(Document document, String title) {
        document.addTitle(title);
        document.addSubject("none");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Bhargo");
        document.addCreator("Bhargo");
    }

    /**
     * In this method title(s) of the document is added.
     *
     * @param document
     * @throws DocumentException
     */
    private static void addTitlePage(Document document, String title)
            throws DocumentException, IOException {

        //BaseFont  bFont = BaseFont.createFont("mangal.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        BaseFont unicode = BaseFont.createFont("assets/MangalRegular.ttf", "UTF-8", true);
        BaseFont unicodet = BaseFont.createFont("assets/MangalRegular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font FONT_TITLE_ = new Font(unicodet, 22, Font.NORMAL);

        Paragraph paragraph = new Paragraph();
       /* // Adding several title of the document. Paragraph class is available in  com.itextpdf.text.Paragraph
        Paragraph childParagraph = new Paragraph("पोलियो Sample " + controlObject.getDisplayName()); //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
//new String("पोलियो".getBytes(), "UTF-8")
        childParagraph = new Paragraph("Sample पोलियो" + controlObject.getDisplayName(), FONT_TITLE_); //public static Font FONT_SUBTITLE = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);

        childParagraph = new Paragraph(new String("पोलियो Test".getBytes(), StandardCharsets.UTF_8), FONT_TITLE_); //public static Font FONT_SUBTITLE = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);*/


        Paragraph  childParagraph = new Paragraph(title, FONT_TITLE); //public static Font FONT_SUBTITLE = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
        addEmptyLine(paragraph, 2);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        /*childParagraph = new Paragraph("Report generated on: " + new Date(), FONT_SUBTITLE);
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);*/


        document.add(paragraph);
        //End of adding several titles

    }

    /**
     * In this method the main contents of the documents are added
     *
     * @param document
     * @throws DocumentException
     */

    private static void addContent(Document document) throws DocumentException, IOException {

        Paragraph reportBody = new Paragraph();
        reportBody.setFont(FONT_BODY); //

        // Creating a table
        createTable(reportBody);

        // now add all this to the document
        document.add(reportBody);

    }

    /**
     * This method is responsible to add table using iText
     *
     * @param reportBody
     * @throws BadElementException
     */
    private static void createTable(Paragraph reportBody)
            throws DocumentException, IOException {

        List<String> headerNames = new ArrayList<>();
        //List<Float>  headerWidth=new ArrayList<>();
        for (int j = 0; j < dataTableColumn_beans.size(); j++) {
            boolean isEnabled = dataTableColumn_beans.get(j).isColumnEnabled();
            if (isEnabled) {
                headerNames.add(dataTableColumn_beans.get(j).getHeaderName());
                //headerWidth.add(Float.parseFloat(dataTableColumn_beans.get(j).getColumnWidth()));
            }
        }
        float colWidthd[]=new float[headerNames.size()];
        for (int i = 0; i < headerNames.size(); i++) {
            colWidthd[i]=200;
        }

       // float[] columnWidths_ = {5, 5, 5, 2}; //total 4 columns and their width. The first three columns will take the same width and the fourth one will be 5/2.
        PdfPTable table = new PdfPTable(headerNames.size());

       // table.setWidthPercentage(100); //set table with 100% (full page)
        table.getDefaultCell().setUseAscender(true);


        //Adding table headers
        for (int i = 0; i < headerNames.size(); i++) {
            //childParagraph = new Paragraph("Sample "+controlObject.getDisplayName(),FONT_TITLE_); //public static Font FONT_SUBTITLE = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
            BaseFont unicodet = BaseFont.createFont("assets/MangalRegular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            BaseFont unicodett = BaseFont.createFont();
            Font FONT_TITLE_ = new Font(unicodet);
            FONT_TITLE_.setColor(BaseColor.WHITE);
           /* PdfPCell cell = new PdfPCell(new Phrase(headerNames.get(0), FONT_TITLE));*/
            FONT_TABLE_HEADER.setColor(BaseColor.WHITE);
            PdfPCell cell = new PdfPCell(new Phrase(headerNames.get(i), FONT_TABLE_HEADER));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER); //alignment
            //cell.setBackgroundColor(new BaseColor(1000)); //cell background color.
            cell.setBackgroundColor(BaseColor.RED);
            //cell.setFixedHeight(60); //cell height
            table.addCell(cell);
        }
        //End of adding table headers

        //Body
        for (int i = 0; i < llTableData.size(); i++) {
            List<String> colData = llTableData.get(i);
            for (int j = 0; j < colData.size(); j++) {
                PdfPCell cell = new PdfPCell(new Phrase(colData.get(j)));
                cell.setFixedHeight(40);
                table.addCell(cell);
            }
        }
        reportBody.add(table);

    }

    /**
     * This method is used to add empty lines in the document
     *
     * @param paragraph
     * @param number
     */
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private File getOutputFilePath(Context context, String fileName, String fileType) {
        File createFile;
        File fileStorage = ImproveHelper.createFolder(context, "ImproveUserFiles/CreateFiles");
        //String filePath = fileName.replace(" ","_") + "_" + System.currentTimeMillis() + fileType;
        String filePath = fileName.replace(" ", "_") + fileType;
        createFile = new File(fileStorage, filePath);//Create Output file
        return createFile;
    }

    public void setHeaderData(List<DataTableColumn_Bean> dataTableColumn_beans, ControlObject controlObject) {
        GeneratePDFReport.dataTableColumn_beans = dataTableColumn_beans;
        GeneratePDFReport.controlObject = controlObject;
    }

    public void setBodyData(List<List<String>> llTableData) {
        GeneratePDFReport.llTableData = llTableData;
    }

    public boolean createPDF(Context context, String reportName, File pdfFilePath) {

        try {
            //constructing the PDF file
            File pdfFile = pdfFilePath;

            //Creating a Document with size A4. Document class is available at  com.itextpdf.text.Document
            Document document = new Document(PageSize.A4);

            //assigning a PdfWriter instance to pdfWriter
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));

            //PageFooter is an inner class of this class which is responsible to create Header and Footer
            PageHeaderFooter event = new PageHeaderFooter();
            pdfWriter.setPageEvent(event);

            //Before writing anything to a document it should be opened first
            document.open();

            //Adding meta-data to the document
            addMetaData(document, reportName);
            //Adding Title(s) of the document
            addTitlePage(document, reportName);
            //Adding main contents of the document
            addContent(document);
            //Closing the document
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            ImproveHelper.showToast(context, e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * This is an inner class which is used to create header and footer
     *
     * @author XYZ
     */

    class PageHeaderFooter extends PdfPageEventHelper {
        Font ffont = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);

        public void onEndPage(PdfWriter writer, Document document) {

            /**
             * PdfContentByte is an object containing the user positioned text and graphic contents
             * of a page. It knows how to apply the proper font encoding.
             */
            PdfContentByte cb = writer.getDirectContent();

            /**
             * In iText a Phrase is a series of Chunks.
             * A chunk is the smallest significant part of text that can be added to a document.
             *  Most elements can be divided in one or more Chunks. A chunk is a String with a certain Font
             */
            Phrase footer_poweredBy = new Phrase("Powered By Bhargo", FONT_HEADER_FOOTER); //
            Phrase footer_pageNumber = new Phrase("Page " + document.getPageNumber(), FONT_HEADER_FOOTER);

            // Header
            // ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, header,
            // (document.getPageSize().getWidth()-10),
            // document.top() + 10, 0);

            // footer: show page number in the bottom right corner of each age
            ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT,
                    footer_pageNumber,
                    (document.getPageSize().getWidth() - 10),
                    document.bottom() - 10, 0);
//			// footer: show page number in the bottom right corner of each age
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    footer_poweredBy, (document.right() - document.left()) / 2
                            + document.leftMargin(), document.bottom() - 10, 0);
        }
    }

}
