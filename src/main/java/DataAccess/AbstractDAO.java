package DataAccess;

import Connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * The Abstract<T> class is a class that uses generic and reflection elements to define methods applicable to all classes in the Model
 * @param <T> type- generic
 */
    public class AbstractDAO<T> {

        protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;


    @SuppressWarnings("unchecked")
        public AbstractDAO() {
            this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        }

    /***
     * returns a String of the form SELECT * FROM table_Name WHERE Primary key=x
     * @return the corresponding SQL statement in the form of a String
     */
        private String createSelectQuery() {
            Field[] getfields = type.getDeclaredFields();
            getfields[0].setAccessible(true);
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT ");
            sb.append(" * ");
            sb.append(" FROM ");
            sb.append(type.getSimpleName());
            sb.append(" WHERE " + getfields[0].getName() + " =?");

            return sb.toString();
        }
    /***
     * returns a String of the form INSERT INTO table_Name VALUES X, Y, Z
     * @return the corresponding SQL statement in the form of a String
     */
        private String createInsertQuery(T t) throws IllegalAccessException {
         String s="INSERT INTO "+type.getSimpleName()+"(";
         Field[] getfields = type.getDeclaredFields();
            for(int i = 1; i < getfields.length; i++){
                getfields[i].setAccessible(true);
                if(i== getfields.length-1)
                    s=s + getfields[i].getName()+") ";
                else
                    s=s + getfields[i].getName()+ ", ";
            }
             s=s+"VALUES (";
            for(int i = 1; i < getfields.length; i++){
              getfields[i].setAccessible(true);
              String aux;
              if(getfields[i].get(t) instanceof String)
                  aux="'"+getfields[i].get(t)+"'";
              else aux=getfields[i].get(t).toString();
              if(i== getfields.length-1)
                  s=s + aux+");";
              else
                  s=s + aux+ ", ";
          }
            return s;
        }
    /***
     * returns a String of the form UPDATE tableName SET a1=V1, a2=V2 ... , an=Vn WHERE Pimara key = X
     * @return the corresponding SQL statement in the form of a String
     */
        private String createUpdateQuery(T t) throws IllegalAccessException {
            String s="UPDATE "+type.getSimpleName()+" SET ";
            Field[] getfields = type.getDeclaredFields();
            for(int i = 0; i < getfields.length; i++){
                getfields[i].setAccessible(true);
                String aux;
                if(getfields[i].get(t) instanceof String)
                    aux="'"+getfields[i].get(t)+"'";
                else aux=getfields[i].get(t).toString();

                if(i == getfields.length-1)
                    s=s + getfields[i].getName()+"="+aux;
                else
                    s=s + getfields[i].getName()+"="+aux+", "; //get the field and convert into string
            }

            getfields[0].setAccessible(true);
            s=s+" WHERE "+ getfields[0].getName()+"=?;";   System.out.println(s);
            return s;
        }
    /***
     * returns a String of the form DELETE FROM table_Name WHERE Primary key=X
     * @return the corresponding SQL statement in the form of a String
     */
        private String createDeleteQuery() {
            String s= "DELETE FROM "+type.getSimpleName()+" WHERE ";
            Field[] getfields = type.getDeclaredFields();
            getfields[0].setAccessible(true);
            s=s+ getfields[0].getName()+"=?;";
            return s;
        }
    /***
     * returns a String of the form SELECT * FROM tableName
     * @return the corresponding SQL statement in the form of a String
     */
        private String createFindAllQuery(){
            String s= "SELECT * FROM "+type.getSimpleName()+";";
            return s;
        }

    /***
     * execute the query built with the function createFindAllQuery()
     * @return list of objects of type T
     * @throws IllegalArgumentException
     */
        public List<T> findAll() throws IllegalArgumentException{
            Connection connection = null;
            PreparedStatement  statement = null;
            ResultSet resultSet = null;
            String query = createFindAllQuery();
            try {
                connection = ConnectionFactory.getConnection();
                statement = connection.prepareStatement(query);
                resultSet = statement.executeQuery();
                return createObjects(resultSet);

            } catch(SQLException e) {
                LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
            } finally {
                ConnectionFactory.close(resultSet);
                ConnectionFactory.close(statement);
                ConnectionFactory.close(connection);
            }
            return null;
        }

    /***
     * execute the query built with the function createFindAllQuery()
     * @param id the key of the searched element
     * @return searched element
     */
        public T findById(int id) {
            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            String query = createSelectQuery();
            try {
                connection = ConnectionFactory.getConnection();
                statement = connection.prepareStatement(query);
                statement.setInt(1, id);
                resultSet = statement.executeQuery();

                return createObjects(resultSet).get(0);
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
            } finally {
                ConnectionFactory.close(resultSet);
                ConnectionFactory.close(statement);
                ConnectionFactory.close(connection);
            }
            return null;
        }

    /***
     * creates a list of type T objects from the ResultSet given as a parameter
     * @param resultSet object of the ResultSet type to be transformed into a list
     * @return list of objects of type T
     */
        private List<T> createObjects(ResultSet resultSet) {
            List<T> list = new ArrayList<T>();
            Constructor[] ctors = type.getDeclaredConstructors();
            Constructor ctor = null;
            for (int i = 0; i < ctors.length; i++) {
                ctor = ctors[i];
                if (ctor.getGenericParameterTypes().length == 0)
                    break;
            }
            try {
                while (resultSet.next()) {
                    ctor.setAccessible(true);
                    T instance = (T)ctor.newInstance();
                    for (Field field : type.getDeclaredFields()) {
                        String fieldName = field.getName();
                        Object value = resultSet.getObject(fieldName);
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                        Method method = propertyDescriptor.getWriteMethod();
                        method.invoke(instance, value);
                    }
                    list.add(instance);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IntrospectionException e) {
                e.printStackTrace();
            }
            return list;
        }

    /***
     * execute the query built with the function createInsertQuery(T t)
     * @param t the object to be inserted
     * @throws IllegalAccessException
     */

        public void insert(T t) throws IllegalAccessException {

            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            String query = createInsertQuery(t);
            try {
                connection = ConnectionFactory.getConnection();
                statement = connection.prepareStatement(query);
                int ok = statement.executeUpdate();

            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
            } finally {
                ConnectionFactory.close(resultSet);
                ConnectionFactory.close(statement);
                ConnectionFactory.close(connection);
            }

        }

    /***
     * execute the query built with the function createUpdateQuery(T t)
     * @param t the object with which the update is made
     * @param id the key of the element being edited
     * @throws IllegalAccessException
     */
        public void update(T t, int id) throws IllegalAccessException {

            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            String query = createUpdateQuery(t);
            try {
                connection = ConnectionFactory.getConnection();
                statement = connection.prepareStatement(query);
                statement.setInt(1, id);
                int ok = statement.executeUpdate();

            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, type.getName() + "DAO: update " + e.getMessage());
            } finally {
                ConnectionFactory.close(resultSet);
                ConnectionFactory.close(statement);
                ConnectionFactory.close(connection);
            }

        }

    /***
     * execute the query built with the function createDeleteQuery()
     * @param id the key of the element to be deleted
     */
    public void delete(int id){

            Connection connection = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            String query = createDeleteQuery();
            try {
                connection = ConnectionFactory.getConnection();
                statement = connection.prepareStatement(query);
                statement.setInt(1, id);
                int ok = statement.executeUpdate();

            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, type.getName() + "DAO: delete " + e.getMessage());
            } finally {
                ConnectionFactory.close(resultSet);
                ConnectionFactory.close(statement);
                ConnectionFactory.close(connection);
            }

        }
    }


