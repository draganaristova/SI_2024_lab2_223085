import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SILab2Test {
    private List<Item> items(Item... items) {
        return new ArrayList<>(Arrays.asList(items));
    }

    @Test
    void checkEveryBranch() {
        RuntimeException exception;

        // Тест случај 1: allItems = null, payment = any
        exception = assertThrows(RuntimeException.class, () -> SILab2.checkCart(null, 1));
        assertTrue(exception.getMessage().contains("allItems list can't be null!"));

        // Тест случај 2: allItems = [], payment = 0
        assertTrue(SILab2.checkCart(new ArrayList<Item>(), 0));

        // Тест случај 3: allItems = [], payment = -1
        assertFalse(SILab2.checkCart(new ArrayList<Item>(), -1));

        // Тест случај 4: allItems = [{"", null, 200, 0.4}], payment = any
        exception = assertThrows(RuntimeException.class, () -> SILab2.checkCart(items(new Item("", null, 200, 0.4f)), 1));
        assertTrue(exception.getMessage().contains("No barcode!"));

        // Тест случај 5: allItems = [{"", "023456", 500, 0.4}], payment = 2
        assertFalse(SILab2.checkCart(items(new Item("", "023456", 500, 0.4f)), 2));

        // Тест случај 6: allItems = [{"Dragana", "02a3456", 500, 0.4}], payment = any
        exception = assertThrows(RuntimeException.class, () -> SILab2.checkCart(items(new Item("Dragana", "02a3456", 500, 0.4f)), 1));
        assertTrue(exception.getMessage().contains("Invalid character in item barcode!"));

        // Тест случај 7: allItems = [{"Dragana", "123456", 200, -1}], payment = any
        assertFalse(SILab2.checkCart(items(new Item("Dragana", "123456", 200, -1f)), 100));
    }

    @Test
    void checkMultipleConditions() {
        // Тест случај 1: allItems = [{"", "12345", 350, 0.2f}], payment = 2
        assertFalse(SILab2.checkCart(items(new Item("", "12345", 350, 0.2f)), 2));

        // Тест случај 2: allItems = [{"", "12345", 350, 0}], payment = 2
        assertFalse(SILab2.checkCart(items(new Item("", "12345", 350, 0f)), 2));

        // Тест случај 3: allItems = [{"", "01234", 350, 0.2f}], payment = 300
        assertTrue(SILab2.checkCart(items(new Item("", "01234", 350, 0.2f)), 300));

        // Тест случај 4: allItems = [{"", "01234", 100, 0.2f}], payment = 2
        assertFalse(SILab2.checkCart(items(new Item("", "01234", 100, 0.2f)), 2));
    }
}