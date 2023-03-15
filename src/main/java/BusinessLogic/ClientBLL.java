package BusinessLogic;

import DataAccess.ClientDAO;
import Model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class ClientBLL {

    private ClientDAO clientDAO;


    public ClientBLL() {
        clientDAO = new ClientDAO();
    }

    /***
     * Search the database for the customer with the given id as a parameter
     * @param id the key of the searched client
     * @return the client whith key=id
     */
    public Client findClientById(int id) {
        Client c = clientDAO.findById(id);
        if (c == null) {
            throw new NoSuchElementException("Client with id-ul =" + id + " not found!");
        }
        return c;
    }

    /***
     * Build a list with all the clients from the Client table
     * @return the list with all the clients in the table
     */
    public List<Client> takeAll(){
        List<Client> sel = clientDAO.findAll();
        if(sel==null)
            throw new NoSuchElementException("Empty tabel!");
        return sel;
    }

    /***
     * Insert into the Client table
     * @param cl client to be inserted
     * @throws IllegalAccessException
     */
    public void insertClient(Client cl) throws IllegalAccessException {
       clientDAO.insert(cl);
    }

    /***
     * Edit the table Client
     * @param cl new values for the client
     * @param id the key for the client that is edited
     * @throws IllegalAccessException
     */
    public void updateClient(Client cl, int id) throws IllegalAccessException {
        clientDAO.update(cl, id);
    }

    /***
     * Delete from table Client
     * @param id the key for the client that is deleted
     * @throws IllegalAccessException
     */
    public void deleteClient(int id) throws IllegalAccessException {
       clientDAO.delete(id);

    }

    /***
     * Build a list with all ids from the Client tabel
     * @return list of integers
     */
    public List<Integer> takeAllIds(){
        List<Integer> ids = new ArrayList<Integer>();
        List<Client> sel = clientDAO.findAll();
        if(sel!=null){
            for (Client c: sel){
                ids.add(c.getIdClient());
            }
        }
        return ids;
    }



}
