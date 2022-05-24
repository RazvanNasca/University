package inventory.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class InventoryRepositoryUnitTest {
    private InventoryRepository repo;

    @BeforeEach
    public void setUp() {
        repo = mock(InventoryRepository.class);
    }

    @Test
    public void addPart_Success() {

    }

    @Test
    public void addPart_Fail() {

    }
}
