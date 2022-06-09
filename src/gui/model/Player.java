package gui.model;

/**
 * Thông tin liên quan đến người chơi Sudoku
*/
public class Player {

    // Thuộc tính Người chơi
    private String fullname;
    private String email;
    private String password;
    private int score;

    /**
     * Xây dựng Trình phát Sudoku.
     *
     * @param fullname tên đầy đủ của người chơi
     * @param email địa chỉ email của người chơi
     * @param password mật khẩu của người chơi
     * @param score điểm số của người chơi
     */
    public Player(String fullname, String email, String password, int score) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.score = score;
    }

    /**
     * Xây dựng một cầu thủ cơ bản cho bảng tính điểm.
     *
     * @param fullname Tên đầy đủ của người chơi
     * @param score điểm số của người chơi
     */
    public Player(String fullname, int score) {
        this.fullname = fullname;
        this.score = score;
    }

    /**
     * @return tên đầy đủ
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return mật khẩu
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return điểm số
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score điểm số cần thiết lập
     */
    public void setScore(int score) {
        this.score = score;
    }
}
