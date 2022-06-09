package gui.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Kiểm tra một số yếu tố độ khó.
 */
public class DifficultyTest {

    /**
     * Kiểm tra phương pháp getMaxHints, về độ khó của lớp.
     */
    @Test
    public void testGetMaxHintsAdvanced() {
        System.out.println("getMaxHints");
        Difficulty instance = Difficulty.ADVANCED;
        int expResult = 8;
        int result = instance.getMaxHints();
        assertEquals(expResult, result);
    }

    /**
     * Kiểm tra phương pháp numEmptyCells, của lớp Độ khó.
     */
    @Test
    public void testNumEmptyCellsBeginner() {
        System.out.println("numEmptyCells");
        Difficulty instance = Difficulty.BEGINNER;
        int result = instance.numEmptyCells();
        assertTrue(result >= 18 && result <= 22);
    }
}
