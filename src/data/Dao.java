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

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dao<T> {

    protected Class<T> type;
    protected Database db;
    protected String insertSql;
    protected String findSql;
    protected String deleteSql;

    public Dao(Class<T> type) {
        this.type = type;
        this.db = new Database();
    }

    public Dao(Class<T> type, Database db) {
        this.type = type;
        this.db = db;
    }

    public void insert(T t) throws DatabaseException {
        try {
            this.db.connect();
            PreparedStatement statement = prepareInsertStatement(t);
            statement.executeUpdate();
            db.closeConnection(true);
        } catch (SQLException ex) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while inserting into database");
        } catch (IllegalAccessException ex) {
            db.closeConnection(false);
            throw new DatabaseException("dao illegal access of model object");
        } catch (DatabaseException ex) {
            db.closeConnection(false);
            throw ex;
        }
    }

    // find UNIQUE element
    public T find(String primaryKey) throws DatabaseException {
        try {
            this.db.connect();
            PreparedStatement statement = prepareFindStatement(primaryKey);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                // TODO fix this
                T t = rs.getObject(1, this.type);
                db.closeConnection(true);
                return t;
            }
            db.closeConnection(true);
        } catch (SQLException ex) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while finding in database");
        } catch (DatabaseException ex) {
            db.closeConnection(false);
            throw ex;
        }
        db.closeConnection(false);
        return null;
    }

    // delete UNIQUE element
    public void delete(String primaryKey) throws DatabaseException {
        try {
            this.db.connect();
            PreparedStatement statement = prepareDeleteStatement(primaryKey);
            statement.executeUpdate();
            db.closeConnection(true);
        } catch (SQLException e) {
            db.closeConnection(false);
            throw new DatabaseException("sql error encountered while deleting in database");
        } catch (DatabaseException e) {
            db.closeConnection(false);
            throw e;
        }
    }

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

    public PreparedStatement prepareDeleteStatement(String primaryKey) throws SQLException {
        String sql = this.deleteSql;
        PreparedStatement statement = this.db.getConnection().prepareStatement(sql);
        statement.setObject(1, primaryKey);
        return statement;
    }

    public void setStatementValues(PreparedStatement statement, T t) throws SQLException, IllegalAccessException {
        Field[] fields = this.type.getDeclaredFields();
        int i = 1;
        for (Field f : fields) {
            System.out.printf("statement.setObject(%d, %s)\n", i,runGetter(f, t));
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

}