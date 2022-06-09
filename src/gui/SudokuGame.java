package gui;

import gui.model.Player;
import gui.model.Grid;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 * Đây là Trình quản lý Trò chơi Sudoku (MODEL).
*/
public class SudokuGame {

    // Màu ứng dụng Sudoku
    public static final Color BKGD_DARK_GRAY = new Color(42, 54, 63);
    public static final Color BKGD_LIGHT_GRAY = new Color(62, 74, 83);
    public static final Color APP_GREEN = new Color(146, 208, 80);

    // Thuộc tính Mô hình Sudoku
    private final Database sudokuDB;
    private ArrayList highscores;
    private Player player;
    private Grid puzzle;
    private int hintsUsed;
    private Timer timer;

    /**
     * Mặc định Công cụ xây dựng mô hình Sudoku.
     */
    public SudokuGame() {
        this.sudokuDB = new Database();
        setHighScores();
    }

    /**
     * Tăng điểm số của người chơi khi hoàn thành.
     *
     * @param pointsAwarded Số lượng điểm cần tăng thêm
     */
    public void increaseScore(int pointsAwarded) {
        this.player.setScore(this.player.getScore() + pointsAwarded);
        this.sudokuDB.updateHighscore(this.player);
    }

    /**
     * @return the sudokuDB
     */
    public Database getSudokuDB() {
        return sudokuDB;
    }

    /**
     * @return người chơi
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @param người chơi mà người chơi để thiết lập
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return câu đố
     */
    public Grid getPuzzle() {
        return puzzle;
    }

    /**
     * @param puzzle câu đố để thiết lập
     */
    public void setPuzzle(Grid puzzle) {
        this.puzzle = puzzle;
    }

    /**
     * @return danh sách điểm số mới nhất
     */
    public ArrayList getHighscores() {
        setHighScores();
        return highscores;
    }

    /**
     * Truy xuất phiên bản cập nhật của điểm số
     */
    private void setHighScores() {
        this.highscores = getSudokuDB().getHighScores();
    }

    /**
     * @return số lượng gợi ý được sử dụng
     */
    public int getHintsUsed() {
        return hintsUsed;
    }

    /**
     * @param hintsUsed số lượng gợi ý được sử dụng
     */
    public void setHintsUsed(int hintsUsed) {
        this.hintsUsed = hintsUsed;
    }

    /**
     * @return phiên bản gợi ý được định dạng chuỗi được sử dụng
     */
    public String getStringHintsUsed() {
        return this.getHintsUsed() + "/" + this.getPuzzle().getDifficulty().getMaxHints();
    }

    /**
     * @return bộ đếm thời gian
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * @param Timer giờ bộ đếm thời gian để đặt
     */
    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
