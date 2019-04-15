package data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    public Dao(Class<T> type, Database db) {
        this.type = type;
        this.db = db;
    }

    public void insert(T t) throws DatabaseException {
        try {
            PreparedStatement statement = prepareInsertStatement(t);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("sql error encountered while inserting into database. " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new DatabaseException("dao illegal access of model object." + e.getMessage());
        } catch (NullPointerException e) {
            throw new DatabaseException("dao tried to operate on closed connection. " + e.getMessage());
        }
    }

    // find UNIQUE element
    public T find(String primaryKey) throws DatabaseException {
        try {
            PreparedStatement statement = prepareFindStatement(primaryKey);
            ResultSet rs = statement.executeQuery();
            T t = getObject(rs);
            return t;
        } catch (SQLException e) {
            throw new DatabaseException("sql error encountered while finding in database. " + e.getMessage());
        } catch (NullPointerException e) {
            throw new DatabaseException("dao tried to operate on closed connection. " + e.getMessage());
        }
    }

    public ArrayList<T> findMany(String key) throws DatabaseException {
        ArrayList<T> items = new ArrayList<>();
        try {
            PreparedStatement statement = prepareFindManyStatement(key);
            ResultSet rs = statement.executeQuery();
            T foundObject = null;
            foundObject = getObject(rs);
            while (foundObject != null) {
                items.add(foundObject);
                foundObject = getObject(rs);
            }
            return items;
        } catch (SQLException e) {
            throw new DatabaseException("sql error encountered while finding in database. " + e.getMessage());
        } catch (NullPointerException e) {
            throw new DatabaseException("dao tried to operate on closed connection. " + e.getMessage());
        }
    }

    // delete UNIQUE element. return 1 if found and deleted.
    public int delete(String primaryKey) throws DatabaseException {
        int deleteCount = 0;
        try {
            PreparedStatement statement = prepareDeleteStatement(primaryKey);
            deleteCount = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("sql error encountered while deleting in database. " + e.getMessage());
        } catch (NullPointerException e) {
            throw new DatabaseException("dao tried to operate on closed connection. " + e.getMessage());
        }
        return deleteCount;
    }

    public int deleteMany(String key) throws DatabaseException {
        int deleteCount = 0;
        try {
            PreparedStatement statement = prepareDeleteManyStatement(key);
            deleteCount = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("sql error encountered while deleting in database. " + e.getMessage());
        } catch (NullPointerException e) {
            throw new DatabaseException("dao tried to operate on closed connection. " + e.getMessage());
        }
        return deleteCount;
    }


    // create object from ResultSet. must be overriden to work with that particular type
    protected abstract T getObject(ResultSet rs) throws SQLException;


    private PreparedStatement prepareInsertStatement(T t) throws SQLException, IllegalAccessException {
        String sql = this.insertSql;
        PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
        setStatementValues(statement, t);
        return statement;
    }

    private PreparedStatement prepareFindStatement(String primaryKey) throws SQLException {
        String sql = this.findSql;
        PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
        statement.setString(1, primaryKey);
        return statement;
    }

    private PreparedStatement prepareFindManyStatement(String key) throws SQLException {
        String sql = this.findManySql;
        PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
        statement.setString(1, key);
        return statement;
    }

    private PreparedStatement prepareDeleteStatement(String primaryKey) throws SQLException {
        String sql = this.deleteSql;
        PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
        statement.setObject(1, primaryKey);
        return statement;
    }

    private PreparedStatement prepareDeleteManyStatement(String key) throws SQLException {
        String sql = this.deleteManySql;
        PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
        statement.setObject(1, key);
        return statement;
    }

    private void setStatementValues(PreparedStatement statement, T t) throws SQLException {
        Field[] fields = this.type.getDeclaredFields();
        int i = 1;
        for (Field f : fields) {
            statement.setObject(i++, runGetter(f, t));
        }
    }

    private Object runGetter(Field field, T t) {
        for (Method method : this.type.getMethods()) {
            if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3))) {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                    try {
                        return method.invoke(t);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.err.println("could not determine method: " + method.getName());
                    }
                }
            }
        }
        return null;
    }

}
