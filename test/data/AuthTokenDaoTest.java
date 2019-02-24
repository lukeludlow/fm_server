package data;

import model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenDaoTest {

    private Database db;
    private AuthTokenDao authtokenDao;
    private AuthToken bitcoin;
    private AuthToken anotherBitcoin;
    private AuthToken findBitcoin;
    private AuthToken findAnotherBitcoin;

    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
        db.clearAll();
        authtokenDao = new AuthTokenDao(db);
        bitcoin = new AuthToken("69", "lukeludlow");
        anotherBitcoin = new AuthToken("123", "nunya");
        findBitcoin = null;
        findAnotherBitcoin = null;
    }
    @AfterEach
    void tearDown() throws Exception {
        db.clearAll();
    }

    @Test
    @DisplayName("insert authToken")
    void testInsert() throws Exception {
        authtokenDao.insert(bitcoin);
        findBitcoin = authtokenDao.find(bitcoin.getAuthtoken());
        assertNotNull(findBitcoin);
        assertEquals(bitcoin, findBitcoin);
    }
    @Test
    @DisplayName("insert authToken fail (insert same authToken twice)")
    void testInsertFail() {
        assertThrows(DatabaseException.class,
                () -> {
                    authtokenDao.insert(bitcoin);
                    authtokenDao.insert(bitcoin);
                });
    }
    @Test
    @DisplayName("insert authToken fail (empty primary key)")
    void testInsertFail2() {
        assertThrows(DatabaseException.class,
                () -> {
                    bitcoin.setAuthtoken(null);
                    authtokenDao.insert(bitcoin);
                });
    }
    @Test
    @DisplayName("find success")
    void testFind() throws Exception {
        authtokenDao.insert(bitcoin);
        findBitcoin = authtokenDao.find(bitcoin.getAuthtoken());
        assertNotNull(findBitcoin);
        assertEquals(bitcoin, findBitcoin);
    }
    @Test
    @DisplayName("find success (find multiple entries multiple times)")
    void testFind2() throws Exception {
        authtokenDao.insert(bitcoin);
        authtokenDao.insert(anotherBitcoin);
        findBitcoin = authtokenDao.find(bitcoin.getAuthtoken());
        findAnotherBitcoin = authtokenDao.find(anotherBitcoin.getAuthtoken());
        assertNotNull(findBitcoin);
        assertNotNull(anotherBitcoin);
        assertEquals(bitcoin, findBitcoin);
        assertEquals(anotherBitcoin, findAnotherBitcoin);
        findBitcoin = authtokenDao.find(bitcoin.getAuthtoken());
        findAnotherBitcoin = authtokenDao.find(anotherBitcoin.getAuthtoken());
        assertNotNull(findBitcoin);
        assertNotNull(anotherBitcoin);
        assertEquals(bitcoin, findBitcoin);
        assertEquals(anotherBitcoin, findAnotherBitcoin);
    }
    @Test
    @DisplayName("find fail (authToken has not been inserted)")
    void testFindFail() throws Exception {
        findBitcoin = authtokenDao.find(bitcoin.getAuthtoken());
        assertNull(findBitcoin);
    }
    @Test
    @DisplayName("find fail (wrong authtokenname)")
    void testFindFail2() throws Exception {
        authtokenDao.insert(bitcoin);
        findBitcoin = authtokenDao.find("xX_authtoken_Xx");
        assertNull(findBitcoin);
    }
    @Test
    @DisplayName("delete success")
    void testDelete() throws Exception {
        authtokenDao.insert(bitcoin);
        findBitcoin = authtokenDao.find(bitcoin.getAuthtoken());
        assertNotNull(findBitcoin);
        int deleteCount = authtokenDao.delete(bitcoin.getAuthtoken());
        findBitcoin = authtokenDao.find(bitcoin.getAuthtoken());
        assertNull(findBitcoin);
        assertEquals(1, deleteCount);
    }
    @Test
    @DisplayName("delete fail (authToken does not exist)")
    void testDeleteFail() throws Exception {
        authtokenDao.delete(bitcoin.getAuthtoken());
    }
    @Test
    @DisplayName("find many (all tokens belonging to a user)")
    void testFindMany() throws Exception {
        AuthToken t2 = new AuthToken("2", "lukeludlow");
        AuthToken t3 = new AuthToken("3", "lukeludlow");
        authtokenDao.insert(bitcoin);
        authtokenDao.insert(t2);
        authtokenDao.insert(t3);
        List<AuthToken> tokens = authtokenDao.findMany(bitcoin.getUsername());
        assertEquals(3, tokens.size());
        assertEquals(bitcoin, tokens.get(0));
        assertEquals(t2, tokens.get(1));
        assertEquals(t3, tokens.get(2));
    }
    @Test
    @DisplayName("find many fail")
    void testFindManyFail() throws Exception {
        List<AuthToken> tokens = authtokenDao.findMany(bitcoin.getUsername());
        assertEquals(0, tokens.size());
    }
    @Test
    @DisplayName("delete many (delete all tokens belonging to a user)")
    void testDeleteMany() throws Exception {
        AuthToken t2 = new AuthToken("2", "lukeludlow");
        AuthToken t3 = new AuthToken("3", "lukeludlow");
        authtokenDao.insert(bitcoin);
        authtokenDao.insert(t2);
        authtokenDao.insert(t3);
        List<AuthToken> tokens = authtokenDao.findMany(bitcoin.getUsername());
        assertEquals(3, tokens.size());
        assertEquals(bitcoin, tokens.get(0));
        assertEquals(t2, tokens.get(1));
        assertEquals(t3, tokens.get(2));
        int deleteCount = authtokenDao.deleteMany(bitcoin.getUsername());
        tokens = authtokenDao.findMany(bitcoin.getUsername());
        assertEquals(3, deleteCount);
        assertEquals(0, tokens.size());
    }
}