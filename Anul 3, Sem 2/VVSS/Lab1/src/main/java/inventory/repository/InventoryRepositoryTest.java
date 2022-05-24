package inventory.repository;

import inventory.model.InhousePart;
import inventory.model.Part;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InventoryRepositoryTest {

    private InventoryRepository repo;
    private Part part;
    private double price = -23;

    @BeforeEach
    void setUp() {
        try{
            repo = new InventoryRepository();
        }catch (Exception e) {
            e.printStackTrace();
        }
        price-=1;
    }

    @AfterEach
    void tearDown() { }

    // ECP TESTS:

    @Test
    @DisplayName("Invalid Name")
    void addPart_InvalidName_ECP() {
        part = new InhousePart(1, "", 32,10, 1, 15, 10);
        assertThrows(Exception.class, () -> repo.addPart(part));
    }


    @RepeatedTest(5)
    @DisplayName("Invalid Price")
    void addPart_InvalidPrice_ECP() {
        part = new InhousePart(1, "P1", price, 10, 1, 15, 10);
        assertThrows(Exception.class, () -> repo.addPart(part));
    }

    @Test
    @DisplayName("Invalid Minimum 1")
    void addPart_InvalidMinimum1_ECP() {
        part = new InhousePart(1,"P2", 32, 13, 3, 2, 10);
        assertThrows(Exception.class, () -> repo.addPart(part));
    }

    @Test
    @DisplayName("Invalid Minimum 2")
    void addPart_InvalidMinimum2_ECP() {
        part = new InhousePart(1,"P2",32,13,30,31,10);
        assertThrows(Exception.class, () -> repo.addPart(part));
    }

    @ParameterizedTest
    @ValueSource(strings = {"5", "1", "3"})
    @DisplayName("Invalid Maximum")
    void addPart_InvalidMaximum_ECP(String str) {
        part = new InhousePart(1,"P3",32,13,1, Integer.parseInt(str),10);
        assertThrows(Exception.class, () -> repo.addPart(part));
    }

    @Test
    @DisplayName("Invalid Stock")
    void addPart_InvalidStock_ECP() {
        part = new InhousePart(1,"P4",32,-100,1,5,10);
        assertThrows(Exception.class, () -> repo.addPart(part));
    }

    // BVA TESTS:

    @Test()
    @DisplayName("Invalid Minimum")
    void addPart_InvalidMinimum_BVA() {
        part = new InhousePart(1,"P1",10.3,1,2,10,10);
        assertThrows(Exception.class, () -> repo.addPart(part));
    }

    @RepeatedTest(3)
    @DisplayName("Valid Minimum:")
    void addPart_ValidMinimum_BVA() {
        part = new InhousePart(1,"P1",10.3,3,2,10,10);
        try {
            repo.addPart(part);
            assert true;
        } catch(Exception e) {
            assertEquals("", e.getMessage());
        }
    }

    @Test()
    @DisplayName("Valid Maximum")
    void addPart_ValidMaximum_BVA() {
        part = new InhousePart(1,"P2",10.3,11,2,10,10);
        try {
            repo.addPart(part);
            assert true;
        } catch(Exception e){
            assertEquals("Inventory level is higher than the maximum value. ",e.getMessage());
        }
    }

    @Tag("Invalid_Maximum")
    @Test()
    void addPart_InvalidMaximum_BVA() {
        part = new InhousePart(1,"P3",10.3,9,2,7,10);
        assertThrows(Exception.class, () -> repo.addPart(part));
    }
}