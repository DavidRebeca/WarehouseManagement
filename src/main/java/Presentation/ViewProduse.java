package Presentation;

import BusinessLogic.ProdusBLL;
import Model.Produs;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
/***
 * Clasa ViewProduse are rolul de a construi fereastra GUI corespunzatoare operatiilor pe produse.
 */

public class ViewProduse extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTable table1;
    private JButton ADDButton;
    private JButton EDITButton;
    private JButton SEARCHButton;
    private JButton DELETEButton;
    private JButton BACKButton;
    private JButton EXITButton;
    private JComboBox comboBox1;
    private JButton VIEWButton;
    private JPanel panel;
    private ProdusBLL produsBLL;

    /***
     * Constructorul clasei
     */

    ViewProduse(){
        setContentPane(panel);
        setTitle("PRODUCT");
        setSize(800, 600);
        setLocation(800, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        String[] coloane={"ID", "NAME", "PRICE", "STOCK"};

        DefaultTableModel model=new DefaultTableModel();

        for (int i=0; i<4; i++)
            model.addColumn(coloane[i]);

        produsBLL=new ProdusBLL();

        List<Integer> ids= produsBLL.takeAllIds();

        for(Integer i: ids)
            comboBox1.addItem(Integer.toString(i));

        BACKButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                dispose();
                new ViewOperations();
            }
        });
        VIEWButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
                List<Produs> prod=produsBLL.takeAll();
                for (Produs p: prod){
                    String[] linii=new String[4];
                    linii[0]=Integer.toString(p.getIdProdus());
                    linii[1]=p.getDenumireProdus();
                    linii[2]=Double.toString(p.getPret());
                    linii[3]=Integer.toString(p.getStoc());
                    model.addRow(linii);

                }
                table1.setModel(model);

            }
        });
        SEARCHButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
               String s= comboBox1.getSelectedItem().toString();
               int id=Integer. parseInt(s);
                Produs p=produsBLL.findProdusById(id);
               textField1.setText(Integer.toString(p.getIdProdus()));
               textField2.setText(p.getDenumireProdus());
               textField3.setText(Double.toString(p.getPret()));
               textField4.setText(Integer.toString(p.getStoc()));
            }
        });
        ADDButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                Produs p=new Produs(Integer.parseInt(textField1.getText()), textField2.getText(), Double.parseDouble(textField3.getText()), Integer.parseInt(textField4.getText()));
                try {
                   produsBLL.insertProdus(p);
                    dispose();
                    new ViewProduse();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
        EDITButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                Produs p=new Produs(Integer.parseInt(textField1.getText()), textField2.getText(), Double.parseDouble(textField3.getText()), Integer.parseInt(textField4.getText()));
                try {
                    produsBLL.updateProdus(p, Integer.parseInt(comboBox1.getSelectedItem().toString()));
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
        DELETEButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                try {
                   produsBLL.deleteProdus(Integer.parseInt(comboBox1.getSelectedItem().toString()));
                    dispose();
                    new ViewProduse();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
        EXITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.exit(0);

            }
        });

    }
}
