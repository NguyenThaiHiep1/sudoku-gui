package gui;

import gui.model.Player;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Thông tin liên quan đến Cơ sở dữ liệu trò chơi
 */
public final class Database {

    // Thuộc tính Cơ sở dữ liệu
    private final String dbURL = "jdbc:derby:Game;create=true";
    private final String dbUsername = "sudoku";
    private final String dbPassword = "sudoku";
    private Connection conn;
    private Statement statement;

    /**
     * Xây dựng Cơ sở dữ liệu
     */
    protected Database() {
        if (!connect()) {
            Object[] options = {"OK"};
            JOptionPane.showOptionDialog(null, "Một kết nối với cơ sở dữ liệu không thể được thiết lập.\\n Điều này rất có thể xảy ra vì một phiên bản khác đang mở.\\nTrong chế độ nhúng, chỉ có một quá trình được phép truy cập vào cơ sở dữ liệu derby.\\nThe ứng dụng phụ thuộc rất nhiều vào cơ sở dữ liệu của nó để quản lý người chơi của nó.\n" + "", "Another Instance is Open", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
            System.exit(0);
        }
        dbSetup();
    }

    /**
     * Khởi tạo kết nối với cơ sở dữ liệu.
     *
     * @return đúng nếu kết nối, khác sai
     */
    protected boolean connect() {
        boolean success = false;
        try {
            conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            System.out.println("DATABASE: Connected");
            statement = conn.createStatement();
            success = true;
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return success;
    }

    /**
     * Cấu hình Bảng Cơ sở dữ liệu cho Trò chơi Sudoku
     */
    protected void dbSetup() {
        try {
            // Tạo bảng Player nếu bảng đó không tồn tại
            if (!checkTableExists("Players")) {
                //statement.executeUpdate("DROP TABLE Players"); // --> Có thể được sử dụng ở đây để đặt lại bảng 'Người chơi'.
                statement.executeUpdate("CREATE TABLE Players (FULLNAME VARCHAR(255), EMAIL VARCHAR(255), PASSWORD VARCHAR(255), SCORE INTEGER)");
                System.out.println("Players table was created.");

                // Tải dữ liệu mẫu vào bảng Player
                statement.executeUpdate("INSERT INTO Players (FULLNAME, EMAIL, PASSWORD, SCORE) VALUES "
                        + "('Emily Example', 'emily@example.com', 'example', 30), "
                        + "('Thomas Tester', 'test@example.com', 'test', 20), "
                        + "('Jane Doe', 'doe@example.com', 'jane', 10)");
                System.out.println("Sample data was added to 'Players' table.");
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex);
        }

    }

    /**
     * Kiểm tra xem bảng đã chọn có tồn tại trong cơ sở dữ liệu hay không
     *
     * @param tableName bảng để kiểm tra
     * @return đúng nếu bảng tồn tại, nếu bảng khác sai
     */
    protected boolean checkTableExists(String tableName) {
        boolean tableExists = false;
        try {
            DatabaseMetaData dbMetaData = conn.getMetaData();
            ResultSet rsMeta = dbMetaData.getTables(null, null, null, null);
            while (rsMeta.next()) {
                String currentTableName = rsMeta.getString("TABLE_NAME");
                if (currentTableName.equalsIgnoreCase(tableName)) {
                    tableExists = true;
                    break;
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return tableExists;
    }

    /**
     * Kiểm tra xem thông tin đăng nhập người dùng có chính xác không
     *
     * @param email email của người dùng
     * @param password mật khẩu của người dùng
     * @return đúng nếu chi tiết khớp với cơ sở dữ liệu, nếu không sai
     */
    protected boolean checkLogin(String email, String password) {
        boolean validLogin = false;
        try {
            ResultSet rs = statement.executeQuery("SELECT FULLNAME FROM Players WHERE EMAIL = '" + email + "' AND PASSWORD = '" + password + "'");
            if (rs.next()) {
                // Login is Valid
                validLogin = true;
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return validLogin;
    }

    /**
     * Đăng ký người dùng trong cơ sở dữ liệu (mật khẩu không băm!)
     *
     * @param fullname tên đầy đủ của người dùng
     * @param email địa chỉ email của người dùng
     * @param password mật khẩu của người dùng
     * @return đúng nếu đăng ký thành công, nếu không đăng ký sai
     */
    protected boolean registerUser(String fullname, String email, String password) {
        boolean success = false;
        try {
            if (!checkLogin(email, password)) {
                statement.executeUpdate("INSERT INTO Players (FULLNAME, EMAIL, PASSWORD, SCORE) VALUES ('" + fullname + "', '" + email + "', '" + password + "', 0)");
                success = true;
                System.out.println("User " + fullname + " đã được thêm thành công vào cơ sở dữ liệu");
            } else {
                System.out.println("User " + fullname + " đã tồn tại, chọn thông tin đăng nhập khác.");
            }
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return success;
    }

    /**
     * Cập nhật điểm số của người dùng
     *
     * @param player người chơi bị ảnh hưởng
     */
    protected void updateHighscore(Player player) {
        try {
            statement.executeUpdate("UPDATE Players SET SCORE = " + player.getScore() + " WHERE EMAIL = '" + player.getEmail() + "'");
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Truy xuất điểm số cao cho bảng xếp hạng
     *
     * @return Danh sách điểm số cao của người chơi
     */
    protected ArrayList<Player> getHighScores() {
        ArrayList<Player> players = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM Players");
            while (rs.next()) {
                players.add(new Player(rs.getString("FULLNAME"), rs.getInt("SCORE")));
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println("SQL Exception: " + ex);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return players;
    }

    /**
     * Tải người chơi từ cơ sở dữ liệu
     *
     * @param email địa chỉ email của người chơi
     * @param pass mật khẩu của người chơi
     * @return đối tượng Trình phát của người dùng hiện tại
     */
    protected Player loadPlayer(String email, String pass) {
        Player player = null;
        if (checkLogin(email, pass)) {
            try {
                ResultSet rs = statement.executeQuery("SELECT * FROM Players WHERE EMAIL = '" + email + "' AND password = '" + pass + "'");
                if (rs.next()) {
                    // Truy xuất Thông tin Người chơi và Xây dựng trình phát
                    player = new Player(rs.getString("FULLNAME"), rs.getString("EMAIL"), rs.getString("PASSWORD"), rs.getInt("SCORE"));
                }
                rs.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        return player;
    }
}
