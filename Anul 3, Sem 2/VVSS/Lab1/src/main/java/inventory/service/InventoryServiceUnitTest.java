package inventory.service;

import inventory.model.InhousePart;
import inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

public class InventoryServiceUnitTest {

    private InventoryRepository repo;
    private InventoryService service;

    @BeforeEach
    public void setUp() {
        repo = mock(InventoryRepository.class);
        service = new InventoryService(repo);
    }

    @Test
    public void addInhousePart_Success() {

        InhousePart part = new InhousePart(1, "part1", 12.2, 5, 1, 10, 1);
//        Mockito.doNothing().when(service).addInhousePart(part.getName(), part.getPrice(), part.getUnitsStock(), part.getMinUnits(), part.getMaxUnits(), part.getMachineId());
        Mockito.doNothing().when(repo).addPart(part);

//        service.addInhousePart(part.getName(), part.getPrice(), part.getUnitsStock(), part.getMinUnits(), part.getMaxUnits(), part.getMachineId());

        assert 1 == service.getAllParts().size();
    }

    @Test
    public void addInhousePart_Fail() {

    }

}
