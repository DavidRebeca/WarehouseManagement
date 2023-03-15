package DataAccess;

import Connection.ConnectionFactory;
import Model.Produs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdusDAO extends AbstractDAO<Produs>{
    /***
     * decrementeaza stocul produsului p cu un numar egal cu parametrul nr
     * @param p produsul al carui stoc urmeaza sa fie decrementat
     * @param nr reprezinta cu cat se decrementeaza stocul
     */
    public void decrementeazaStoc(Produs p, int nr){
        String s="UPDATE produs SET stoc=stoc-"+nr+" WHERE idProdus= "+p.getIdProdus();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(s);
            int ok = statement.executeUpdate();

        } catch (SQLException e) {
           e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }


    }

}
