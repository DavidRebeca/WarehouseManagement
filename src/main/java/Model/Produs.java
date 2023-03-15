package Model;


public class Produs {

    private int idProdus;

    private String denumireProdus;

    private double pret;

    private int stoc;


    public Produs(int idProdus, String denumire, double pret, int stoc) {
        this.idProdus = idProdus;
        this.denumireProdus= denumire;
        this.pret=pret;
        this.stoc=stoc;
    }


    public Produs() {
     }


    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    public String getDenumireProdus() {
        return denumireProdus;
    }

    public void setDenumireProdus(String denumire) {
        this.denumireProdus = denumire;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public int getStoc() {
        return stoc;
    }

    public void setStoc(int stoc) {
        this.stoc = stoc;
    }
}
