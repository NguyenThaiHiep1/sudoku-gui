package gui.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Kiểm tra dữ liệu người chơi.
*/
public class PlayerTest {

    /**
     * Kiểm tra phương pháp getFullname, của Lớp Player.
     */
    @Test
    public void testGetFullnameSampleName() {
        System.out.println("getFullname");
        Player instance = new Player("Sam Sample", "sam@sample.com", "pass", 25);
        String expResult = "Sam Sample";
        String result = instance.getFullname();
        assertEquals(expResult, result);
    }

    /**
     * Kiểm tra phương pháp getEmail, của lớp Player.
     */
    @Test
    public void testGetEmailSampleEmail() {
        System.out.println("getEmail");
        Player instance = new Player("Sam Sample", "sam@sample.com", "pass", 25);
        String expResult = "sam@sample.com";
        String result = instance.getEmail();
        assertEquals(expResult, result);
    }

    /**
     * Kiểm tra phương pháp getPassword, của Class Player.
     */
    @Test
    public void testGetPasswordPass() {
        System.out.println("getPassword");
        Player instance = new Player("Sam Sample", "sam@sample.com", "pass", 25);
        String expResult = "pass";
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Kiểm tra phương pháp getScore, của class Player.
     */
    @Test
    public void testGetScore25() {
        System.out.println("getScore");
        Player instance = new Player("Sam Sample", "sam@sample.com", "pass", 25);
        int expResult = 25;
        int result = instance.getScore();
        assertEquals(expResult, result);
    }

    /**
     * Kiểm tra phương pháp setScore, của class Player.
     */
    @Test
    public void testSetScore15() {
        System.out.println("setScore");
        int score = 15;
        Player instance = new Player("Sam Sample", "sam@sample.com", "pass", 25);
        instance.setScore(score);
    }
}
