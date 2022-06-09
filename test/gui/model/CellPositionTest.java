package gui.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Kiểm tra CellPosition.
*/
public class CellPositionTest {

    /**
     * Kiểm tra phương pháp getRow, của lớp CellPosition.
     */
    @Test
    public void testGetRowAtPositionFiveTwo() {
        System.out.println("getRow");
        CellPosition instance = new CellPosition(5, 2);
        int expResult = 5;
        int result = instance.getRow();
        assertEquals(expResult, result);
    }

    /**
     * Thử nghiệm phương pháp getColumn, thuộc lớp CellPosition.
     */
    @Test
    public void testGetColumnAtPositionSevenNine() {
        System.out.println("getColumn");
        CellPosition instance = new CellPosition(7, 9);
        int expResult = 9;
        int result = instance.getColumn();
        assertEquals(expResult, result);
    }

    /**
     * Kiểm tra phương pháp getSubgrid, của lớp CellPosition.
     */
    @Test
    public void testGetSubgridAtPositionNineEight() {
        System.out.println("getSubgrid");
        CellPosition instance = new CellPosition(9, 8);
        int expResult = 9;
        int result = instance.getSubgrid();
        assertEquals(expResult, result);
    }

    /**
     * Kiểm tra phương pháp toString, của lớp CellPosition.
     */
    @Test
    public void testToStringAtPositionOneOne() {
        System.out.println("toString");
        CellPosition instance = new CellPosition(1, 1);
        String expResult = "2B";
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    /**
     * Kiểm tra phương pháp toString, của lớp CellPosition.
     */
    @Test
    public void testToStringAtPositionMinus2Minus3() {
        System.out.println("toString");
        CellPosition instance = new CellPosition(-2, -3);
        String expResult = "-1,-2";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
}
