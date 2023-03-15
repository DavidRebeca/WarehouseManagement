package Model;

public class Client {

    private int idClient;

    private String nume;

    private String nrTelefon;

    private String adresa;

    public Client() {
    }


    public Client(int idClient, String nume, String nrTelefon, String adresa) {
        this.idClient = idClient;
        this.nume=nume;
        this.nrTelefon=nrTelefon;
        this.adresa=adresa;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(String nrTelefon) {
        this.nrTelefon = nrTelefon;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}
