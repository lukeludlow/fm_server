package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Dao<T> {

    protected Class<T> type;
    protected Database db;
    protected String insertSql;
    protected String findSql;
    protected String findManySql;
    protected String deleteSql;
    protected String deleteManySql;

    public void insert(T t) throws DatabaseException {
        try {
            this.db.connect();
            PreparedStatement statement = prepareInsertStatement(t);
            statement.executeUpdate();
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while inserting into database.\n" + e.getMessage());
        } catch (IllegalAccessException e) {
            db.closeConnection(false);
            throw new DatabaseException("dao illegal access of model object");
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

    // find UNIQUE element
    public T find(String primaryKey) throws DatabaseException {
        try {
            this.db.connect();
            PreparedStatement statement = prepareFindStatement(primaryKey);
            ResultSet rs = statement.executeQuery();
            T t = getObject(rs);
            db.closeConnection(true);
            return t;
        } catch (SQLException ex) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while finding in database");
        } catch (DatabaseException ex) {
            db.closeConnection(false);
            throw ex;
        }
    }

    public List<T> findMany(String key) throws DatabaseException {
        List<T> items = new ArrayList<>();
        try {
            this.db.connect();
            PreparedStatement statement = prepareFindManyStatement(key);
            ResultSet rs = statement.executeQuery();
            T foundObject = null;
            foundObject = getObject(rs);
            while (foundObject != null) {
                items.add(foundObject);
                foundObject = getObject(rs);
            }
            db.closeConnection(true);
            return items;
        } catch (SQLException ex) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while finding in database");
        } catch (DatabaseException ex) {
            db.closeConnection(false);
            throw ex;
        }
    }

    // delete UNIQUE element. return 1 if found and deleted.
    public int delete(String primaryKey) throws DatabaseException {
        int deleteCount = 0;
        try {
            this.db.connect();
            PreparedStatement statement = prepareDeleteStatement(primaryKey);
            deleteCount = statement.executeUpdate();
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while deleting in database");
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
        return deleteCount;
    }

    public int deleteMany(String key) throws DatabaseException {
        int deleteCount = 0;
        try {
            this.db.connect();
            PreparedStatement statement = prepareDeleteManyStatement(key);
            deleteCount = statement.executeUpdate();
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while deleting in database");
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
        return deleteCount;
    }


    // create object from ResultSet. must be overriden to work with that particular type
    public abstract T getObject(ResultSet rs) throws SQLException;


    public PreparedStatement prepareInsertStatement(T t) throws SQLException, IllegalAccessException {
        String sql = this.insertSql;
        PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
        setStatementValues(statement, t);
        return statement;
    }
    public PreparedStatement prepareFindStatement(String primaryKey) throws SQLException {
        String sql = this.findSql;
        PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
        statement.setString(1, primaryKey);
        return statement;
    }
    public PreparedStatement prepareFindManyStatement(String key) throws SQLException {
        String sql = this.findManySql;
        PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
        statement.setString(1, key);
        return statement;
    }
    public PreparedStatement prepareDeleteStatement(String primaryKey) throws SQLException {
        String sql = this.deleteSql;
        PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
        statement.setObject(1, primaryKey);
        return statement;
    }
    public PreparedStatement prepareDeleteManyStatement(String key) throws SQLException {
        String sql = this.deleteManySql;
        PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
        statement.setObject(1, key);
        return statement;
    }

    public void setStatementValues(PreparedStatement statement, T t) throws SQLException, IllegalAccessException {
        Field[] fields = this.type.getDeclaredFields();
        int i = 1;
        for (Field f : fields) {
            statement.setObject(i++, runGetter(f, t));
        }
    }

    public Object runGetter(Field field, T t) {
        for (Method method : this.type.getMethods()) {
            if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3))) {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                    try {
                        return method.invoke(t);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.out.println("Could not determine method: " + method.getName());
                    }
                }
            }
        }
        return null;
    }

    public Dao(Class<T> type) {
        this.type = type;
        this.db = new Database();
    }
    public Dao(Class<T> type, Database db) {
        this.type = type;
        this.db = db;
    }

}
