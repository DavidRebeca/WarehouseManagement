package Presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/***
 * Clasa ViewOperations are rolul de a construi prima fereastra a interfatei grafice cu utilizatorul.
 */

public class ViewOperations extends JFrame{
    /***
     * Butonul pentru deschidereea ferestrei Produse
     */
    private JButton PRODUSEButton;
    /***
     * Butonul pentru deschidereea ferestrei Clienti
     */
    private JButton CLIENTIButton;
    /***
     * Butonul pentru deschidereea ferestrei Comanda
     */
    private JButton COMANDAButton;
    private JPanel panel;

    /***
     * Constructorul clasei
     */
    public ViewOperations(){
        setContentPane(panel);
        setTitle("Operations");
        setSize(350, 400);
        setLocation(800, 200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        PRODUSEButton.addActionListener(new ActionListener() {

            @Override public void actionPerformed(ActionEvent e) {
                dispose();
                new ViewProduse();
            }

        });
        CLIENTIButton.addActionListener(new ActionListener() {

            @Override public void actionPerformed(ActionEvent e) {
                dispose();
                new ViewClient();
            }

        });
        COMANDAButton.addActionListener(new ActionListener() {

            @Override public void actionPerformed(ActionEvent e) {
                dispose();
                new ViewComanda();
            }

        });

    }
}
