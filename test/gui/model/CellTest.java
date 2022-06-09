package gui.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Kiểm tra Dữ liệu Ô.
 */
public class CellTest {

    /**
     * Kiểm tra phương pháp isLocked, của lớp Cell.
     */
    @Test
    public void testIsLockedTrue() {
        System.out.println("isLocked");
        Cell instance = new Cell(1, 1);
        instance.setLocked(true);
        boolean expResult = true;
        boolean result = instance.isLocked();
        assertEquals(expResult, result);
    }

    /**
     * Kiểm tra phương pháp đặt Khóa, của Lớp Ô.
     */
    @Test
    public void testSetLockedFalse() {
        System.out.println("setLocked");
        boolean locked = false;
        Cell instance = new Cell(1, 1);
        instance.setLocked(locked);
    }

    /**
     * Kiểm tra phương pháp isEmpty, của lớp Cell.
     */
    @Test
    public void testIsEmptyTrue() {
        System.out.println("isEmpty");
        Cell instance = new Cell(1, 1);
        instance.setUserValue(0);
        boolean expResult = true;
        boolean result = instance.isEmpty();
        assertEquals(expResult, result);
    }

    /**
     * Kiểm tra phương pháp getUserValue, thuộc lớp Cell.
     */
    @Test
    public void testGetUserValueEight() {
        System.out.println("getUserValue");
        Cell instance = new Cell(5, 2);
        instance.setUserValue(8);
        int expResult = 8;
        int result = instance.getUserValue();
        assertEquals(expResult, result);
    }

    /**
     * Kiểm tra phương pháp toString, của lớp Cell.
     */
    @Test
    public void testToStringThree() {
        System.out.println("toString");
        Cell instance = new Cell(5, 2);
        instance.setUserValue(3);
        String expResult = "[3]";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
