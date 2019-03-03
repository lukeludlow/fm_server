package data;

import model.Person;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDao extends Dao<Person> {

    public PersonDao(Database db) {
        super(Person.class, db);
        this.db = db;
        this.setSqlStatements();
    }

    private void setSqlStatements() {
        this.insertSql = "insert into people " +
                "(person_id, descendant, firstname, lastname, gender, father_id, mother_id, spouse_id) " +
                "values (?,?,?,?,?,?,?,?)";
        this.findSql = "select * from people where person_id = ?";
        this.findManySql = "select * from people where descendant = ?";
        this.deleteSql = "delete from people where person_id = ?";
        this.deleteManySql = "delete from people where descendant = ?";
    }

    @Override
    protected Person getObject(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return new Person(
                    rs.getString("person_id"),
                    rs.getString("descendant"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("gender"),
                    rs.getString("father_id"),
                    rs.getString("mother_id"),
                    rs.getString("spouse_id")
            );
        }
        return null;
    }

}
