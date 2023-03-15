package Presentation;

import BusinessLogic.ClientBLL;
import BusinessLogic.ComandaBLL;
import BusinessLogic.ProdusBLL;
import Model.Client;
import Model.Comanda;
import Model.Produs;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;
/***
 * Clasa ViewComanda are rolul de a construi fereastra GUI corespunzatoare operatiilor pe comenzi.
 */
public class ViewComanda extends JFrame{
    private JButton COMANDAButton;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField textField1;
    private JButton BACKButton;
    private JButton EXITButton;
    private JTextField textField2;
    private JButton DOWNLOADBILLButton;
    private JPanel panel;
    private JTextField textField3;
    private JComboBox comboBox3;
    private ComandaBLL comandaBLL;
    private ProdusBLL produsBLL;
    private ClientBLL clientBLL;
    /***
     * Constructorul clasei
     */
    ViewComanda(){
        setContentPane(panel);
        setTitle("ORDER");
        setSize(600, 400);
        setLocation(800, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        comandaBLL=new ComandaBLL();
        produsBLL=new ProdusBLL();
        clientBLL=new ClientBLL();
        List<Integer> ids3= comandaBLL.takeAllIds();

        for(Integer i: ids3)
            comboBox3.addItem(Integer.toString(i));

        List<Integer> ids= produsBLL.takeAllIds();

        for(Integer i: ids)
            comboBox1.addItem(Integer.toString(i));

        List<Integer> ids2= clientBLL.takeAllIds();

        for(Integer i: ids2)
            comboBox2.addItem(Integer.toString(i));
        COMANDAButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                Comanda c=new Comanda(0, Integer.parseInt(comboBox1.getSelectedItem().toString()),
                        Integer.parseInt(comboBox2.getSelectedItem().toString()), Integer.parseInt(textField1.getText()),
                        textField3.getText());
                Produs p=produsBLL.findProdusById(Integer.parseInt(comboBox1.getSelectedItem().toString()));
                if(c.getCantitate()>p.getStoc())
                    textField2.setText("STOC INSUFICIENT!");
                else
                {
                    try {
                        comandaBLL.insertComanda(c);
                        produsBLL.updateStoc(p,c.getCantitate());
                        textField2.setText("COMANDA A FOST INREGISTRATA!");
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }

                }

            }
        });
        DOWNLOADBILLButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e)  {
                Comanda c=comandaBLL.findComandaById(Integer.parseInt(comboBox3.getSelectedItem().toString()));
                Produs p=produsBLL.findProdusById(c.getIdProdus());
                Client cl=clientBLL.findClientById(c.getIdClient());
                double pretComanda=c.getCantitate()*p.getPret();

                Document document = new Document();


                try {
                    PdfWriter.getInstance(document, new FileOutputStream("Factura.pdf"));
                    document.open();
                } catch (DocumentException ex) {
                    ex.printStackTrace();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
                Paragraph title = new Paragraph();

                title.setAlignment(Element.ALIGN_CENTER);

                title.add(new Paragraph("                          FACTURA COMANDA NR. "+c.getIdComanda(), titleFont));

                addEmptyLine(title, 2);
                try {
                    document.add(title);
                } catch (DocumentException ex) {
                    ex.printStackTrace();
                }
                Font clientFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
                Paragraph clientParagraf = new Paragraph();
                clientParagraf.add(new Paragraph("Nume: "+cl.getNume(), clientFont));
                clientParagraf.add(new Paragraph("Nr. telefon: : "+cl.getNrTelefon(), clientFont));
                clientParagraf.add(new Paragraph("Adresa: : "+cl.getAdresa(), clientFont));
                addEmptyLine(clientParagraf, 5);

                try {
                    document.add(clientParagraf);
                } catch (DocumentException ex) {
                    ex.printStackTrace();
                }

                PdfPTable table = new PdfPTable(3);
                addTableHeader(table);
                addRows(table, p.getDenumireProdus(),  Integer.toString(c.getCantitate()), Double.toString(p.getPret()));

                try {
                    document.add(table);
                } catch (DocumentException ex) {
                    ex.printStackTrace();
                }
                Font totalFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
                Paragraph total = new Paragraph();
                addEmptyLine(total, 5);
                total.setAlignment(2);
                total.add(new Paragraph("                                                                                          TOTAL: "+pretComanda, totalFont));
                try {
                    document.add(total);
                } catch (DocumentException ex) {
                    ex.printStackTrace();
                }
                document.close();

            }
        });
        BACKButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                dispose();
                new ViewOperations();
            }
        });
        EXITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);

            }
        });
    }
    private void addTableHeader(PdfPTable table) {
        Stream.of("Denumire", "Cantitate", "Pret")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
    private void addRows(PdfPTable table, String denumire, String cantitate, String pret) {
        table.addCell(denumire);
        table.addCell(cantitate);
        table.addCell(pret);
    }
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
