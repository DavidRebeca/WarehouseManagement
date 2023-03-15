package Model;

public class Comanda {

    private int idComanda;

    private int idProdus;

    private int idClient;

    private int cantitate;

    private String data;

    public Comanda() {
    }


    public Comanda(int idComanda, int idProdus, int idClient, int cantitate, String data) {
        this.idComanda = idComanda;
        this.idProdus=idProdus;
        this.idClient=idClient;
        this.cantitate=cantitate;
        this.data=data;
    }

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
