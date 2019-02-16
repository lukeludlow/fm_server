package data;

import model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    @DisplayName("insert authtoken")
    void testInsert() throws Exception {
        authtokenDao.insert(bitcoin);
        findBitcoin = authtokenDao.find(bitcoin.getToken());
        assertNotNull(findBitcoin);
        assertEquals(bitcoin, findBitcoin);
    }
    @Test
    @DisplayName("insert authtoken fail (insert same authtoken twice)")
    void testInsertFail() {
        assertThrows(DatabaseException.class,
                () -> {
                    authtokenDao.insert(bitcoin);
                    authtokenDao.insert(bitcoin);
                });
    }
    @Test
    @DisplayName("insert authtoken fail (empty primary key)")
    void testInsertFail2() {
        assertThrows(DatabaseException.class,
                () -> {
                    bitcoin.setToken(null);
                    authtokenDao.insert(bitcoin);
                });
    }

    @Test
    @DisplayName("find success")
    void testFind() throws Exception {
        authtokenDao.insert(bitcoin);
        findBitcoin = authtokenDao.find(bitcoin.getToken());
        assertNotNull(findBitcoin);
        assertEquals(bitcoin, findBitcoin);
    }
    @Test
    @DisplayName("find success (find multiple entries multiple times)")
    void testFind2() throws Exception {
        authtokenDao.insert(bitcoin);
        authtokenDao.insert(anotherBitcoin);
        findBitcoin = authtokenDao.find(bitcoin.getToken());
        findAnotherBitcoin = authtokenDao.find(anotherBitcoin.getToken());
        assertNotNull(findBitcoin);
        assertNotNull(anotherBitcoin);
        assertEquals(bitcoin, findBitcoin);
        assertEquals(anotherBitcoin, findAnotherBitcoin);
        findBitcoin = authtokenDao.find(bitcoin.getToken());
        findAnotherBitcoin = authtokenDao.find(anotherBitcoin.getToken());
        assertNotNull(findBitcoin);
        assertNotNull(anotherBitcoin);
        assertEquals(bitcoin, findBitcoin);
        assertEquals(anotherBitcoin, findAnotherBitcoin);
    }
    @Test
    @DisplayName("find fail (authtoken has not been inserted)")
    void testFindFail() throws Exception {
        findBitcoin = authtokenDao.find(bitcoin.getToken());
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
        findBitcoin = authtokenDao.find(bitcoin.getToken());
        assertNotNull(findBitcoin);
        int deleteCount = authtokenDao.delete(bitcoin.getToken());
        findBitcoin = authtokenDao.find(bitcoin.getToken());
        assertNull(findBitcoin);
        assertEquals(1, deleteCount);
    }
    @Test
    @DisplayName("delete fail (authtoken does not exist)")
    void testDeleteFail() throws Exception {
        authtokenDao.delete(bitcoin.getToken());
    }

}