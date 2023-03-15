package BusinessLogic;

import DataAccess.ComandaDAO;
import Model.Comanda;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ComandaBLL {

    private ComandaDAO comandaDAO;

    public ComandaBLL(){
        comandaDAO=new ComandaDAO();
    }
    /***
     * Search the database for the order with the given id as a parameter
     * @param id the key of the searched order
     * @return the order whith key=id
     */
    public Comanda findComandaById(int id) {
        Comanda c = comandaDAO.findById(id);
        if (c == null) {
            throw new NoSuchElementException("The order with id =" + id + " not found!");
        }
        return c;
    }
    /***
     * Build a list with all ids from the Comanda tabel
     * @return list of integers
     */
    public List<Integer> takeAllIds() {
        List<Integer> ids = new ArrayList<Integer>();
        List<Comanda> sel = comandaDAO.findAll();
        if(sel!=null){
            for (Comanda c: sel){
                ids.add(c.getIdComanda());
            }
        }
        return ids;
    }
    /***
     * Insert in Comanda tabel
     * @param c the order to be inserted
     * @throws IllegalAccessException
     */
    public void insertComanda(Comanda c) throws IllegalAccessException {
        comandaDAO.insert(c);
    }
}
