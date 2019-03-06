package service;

import model.FamilyTree;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FillFactoryTest {

    User luke;
    FillFactory factory;

    @BeforeEach
    void setUp() throws Exception {
        luke = new User("lukeludlow", "password", "luke@luke.com","luke","ludlow","m","123");
        factory = new FillFactory(luke);
    }
    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testFill0() {
        factory.fill(0);
        FamilyTree familyTree = factory.getFamilyTree();
        assertEquals(1, familyTree.getAllNodes().size());
        assertEquals(1, familyTree.getAllPeople().size());
        assertEquals(1, familyTree.getAllEvents().size());
    }

    @Test
    void testFill1() {
        factory.fill(1);
        FamilyTree familyTree = factory.getFamilyTree();
        assertEquals(3, familyTree.getAllNodes().size());
        assertEquals(3, familyTree.getAllPeople().size());
        assertEquals(7, familyTree.getAllEvents().size());
    }

    @Test
    void testFill4() {
        factory.fill(4);
        FamilyTree familyTree = factory.getFamilyTree();
        assertEquals(31, familyTree.getAllNodes().size());
        assertEquals(31, familyTree.getAllPeople().size());
        assertEquals(91, familyTree.getAllEvents().size());
    }

    @Test
    void testReadJsonData() {
        assertEquals("Torgeson", factory.getSurnames()[0]);
        assertEquals("Maston", factory.getSurnames()[1]);
        assertEquals("Lisenby", factory.getSurnames()[2]);
        assertEquals("Canada", factory.getLocations()[0].getCountry());
        assertEquals("Denmark", factory.getLocations()[1].getCountry());
        assertEquals("Canada", factory.getLocations()[2].getCountry());
    }



}