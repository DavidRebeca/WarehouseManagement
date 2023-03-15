package Presentation;

import BusinessLogic.ClientBLL;
import Model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
/***
 * Clasa ViewClient are rolul de a construi fereastra GUI corespunzatoare operatiilor pe clienti.
 */
public class ViewClient extends JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTable table1;
    private JButton ADDButton;
    private JButton EDITButton;
    private JButton DELETEButton;
    private JButton BACKButton;
    private JButton EXITButton;
    private JButton VIEWButton;
    private JButton SEARCHButton;
    private JComboBox comboBox1;
    private JPanel panel;
    private ClientBLL clientBLL;
 /***
 * Constructorul clasei
 */
    ViewClient(){
        setContentPane(panel1);
        setTitle("CLIENT");
        setSize(600, 400);
        setLocation(800, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        String[] coloane={"ID", "NAME", "PHONE", "ADDRESS"};

        DefaultTableModel model=new DefaultTableModel();

        for (int i=0; i<4; i++)
            model.addColumn(coloane[i]);

        clientBLL=new ClientBLL();
        List<Integer> ids= clientBLL.takeAllIds();

        for(Integer i: ids)
            comboBox1.addItem(Integer.toString(i));
        SEARCHButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String s= comboBox1.getSelectedItem().toString();
                int id=Integer. parseInt(s);
                Client c=clientBLL.findClientById(id);
                textField1.setText(Integer.toString(c.getIdClient()));
                textField2.setText(c.getNume());
                textField3.setText(c.getNrTelefon());
                textField4.setText(c.getAdresa());
            }
        });
        VIEWButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
                List<Client> clienti=clientBLL.takeAll();
                for (Client c: clienti){
                    String[] linii=new String[4];
                    linii[0]=Integer.toString(c.getIdClient());
                    linii[1]=c.getNume();
                    linii[2]=c.getNrTelefon();
                    linii[3]=c.getAdresa();
                    model.addRow(linii);

                }
                table1.setModel(model);

            }
        });
        ADDButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                Client c=new Client(Integer.parseInt(textField1.getText()), textField2.getText(), textField3.getText(), textField4.getText());
                try {
                    clientBLL.insertClient(c);
                    dispose();
                    new ViewClient();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
        EDITButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                Client c=new Client(Integer.parseInt(textField1.getText()), textField2.getText(), textField3.getText(), textField4.getText());
                try {
                    clientBLL.updateClient(c, Integer.parseInt(comboBox1.getSelectedItem().toString()));
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
        DELETEButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                try {
                    clientBLL.deleteClient(Integer.parseInt(comboBox1.getSelectedItem().toString()));
                    dispose();
                    new ViewClient();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
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
}
