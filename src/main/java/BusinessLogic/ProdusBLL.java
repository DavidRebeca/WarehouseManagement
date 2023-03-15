package BusinessLogic;

import DataAccess.ProdusDAO;

import Model.Produs;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProdusBLL {


    private ProdusDAO produsDAO;

    public ProdusBLL(){
        produsDAO=new ProdusDAO();
    }

    /***
     * Search the database for the product with the given id as a parameter
     * @param id the key of the searched product
     * @return the product whith key=id
     */
    public Produs findProdusById(int id) {
        Produs p = produsDAO.findById(id);
        if (p == null) {
            throw new NoSuchElementException("The product with id =" + id + " not found!");
        }
        return p;
    }

    /***
     * Build a list with all the products from the Produs table
     * @return the list with all the products in the table
     */
    public List<Produs> takeAll(){
        List<Produs> sel = produsDAO.findAll();
        if(sel==null)
            throw new NoSuchElementException("Tabel gol!");
        return sel;
    }

    /***
     * Insert in Produs table
     * @param pr the product that is inserted
     * @throws IllegalAccessException
     */
    public void insertProdus(Produs pr) throws IllegalAccessException {
      produsDAO.insert(pr);
    }
    /***
     * Edit Produs table
     * @param pr new values for the product
     * @param id the key of the product that is edited
     * @throws IllegalAccessException
     */
    public void updateProdus(Produs pr, int id) throws IllegalAccessException {
        produsDAO.update(pr, id);
    }
    /***
     * Delete from Produs table
     * @param id the key of the product that is deleted
     * @throws IllegalAccessException
     */
    public void deleteProdus(int id) throws IllegalAccessException {
       produsDAO.delete(id);
    }

    /***
     *  decrements the stock of product p by a number equal to parameter nr
     * @param p the product that is edited
     * @param nr number by which the stock of the product decrements
     */
    public void updateStoc(Produs p, int nr){
        produsDAO.decrementeazaStoc(p, nr);
    }
    /***
     * Build a list with all ids from the Produs tabel
     * @return list of integers
     */
    public List<Integer> takeAllIds(){
        List<Integer> ids = new ArrayList<Integer>();
        List<Produs> sel = produsDAO.findAll();
        if(sel!=null){
            for (Produs p: sel){
               ids.add(p.getIdProdus());
            }
        }
        return ids;
    }
}
