package service;

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
    void testReadJsonData() {
        System.out.println(factory.getSurnames());
        System.out.println(factory.getSurnames()[0]);
        System.out.println(factory.getSurnames()[1]);
        System.out.println(factory.getSurnames()[2]);
        System.out.println(factory.getLocations());
        System.out.println(factory.getLocations()[0]);
        System.out.println(factory.getLocations()[1]);
        System.out.println(factory.getLocations()[2]);
    }

    @Test
    void testFill0() {
        factory.fill(0);
        System.out.println(factory.getFamilyTree());
    }

    @Test
    void testFill1() {
        factory.fill(1);
        assertEquals(3, factory.getFamilyTree().getAllNodes().size());
        assertEquals(3, factory.getFamilyTree().getAllPeople().size());
        assertEquals(7, factory.getFamilyTree().getAllEvents().size());
    }

    @Test
    void testFill4() {
        factory.fill(4);
        assertEquals(31, factory.getFamilyTree().getAllNodes().size());
        assertEquals(31, factory.getFamilyTree().getAllPeople().size());
        assertEquals(91, factory.getFamilyTree().getAllEvents().size());
    }


}