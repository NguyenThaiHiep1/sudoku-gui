package gui;

import gui.model.Player;
import static gui.SudokuGame.APP_GREEN;
import static gui.SudokuGame.BKGD_DARK_GRAY;
import static gui.SudokuGame.BKGD_LIGHT_GRAY;
import gui.model.Cell;
import gui.model.CellPosition;
import gui.model.Difficulty;
import gui.model.Generator;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

/**
 * Đây là trò chơi Sudoku (CONTROLLER).
 */
public class SudokuGameApp extends JFrame {

    // Biến Liên kết Dạng xem Mô hình Cục bộ
    private final SudokuGame model;
    private final SudokuGamePanel view;
    private String rulesCaller; // -> Cho chúng tôi biết nút quay lại trên ngăn quy tắc sẽ chuyển hướng đến dựa trên người gọi của nó ở đâu
    private final KeyListener cellKeyListener;
    private final MouseListener cellMouseListener;

    /**
     * Xây dựng Khung trò chơi Sudoku
     *
     * @param name tiêu đề của cửa sổ ứng dụng
     */
    public SudokuGameApp(String name) {
        super(name);
        this.model = new SudokuGame();
        this.view = new SudokuGamePanel();

        getContentPane().add(this.view);
        setSize(1000, 550);
        setResizable(false);

        // Bộ chọn Độ khó Điền
        for (Difficulty diff : Difficulty.values()) {
            view.getHomePanel().getLevelSelectionModel().addElement(diff);
        }

        // Hành động Cửa sổ
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = {"Thoát Khỏi Sudoku", "Hủy"};
                int result = JOptionPane.showOptionDialog(getParent(), "bạn có chắc là sẻ thoát?\nbạn chưa lưu đó.", "Thoát?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (result == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        // Hành động trên Bảng điều khiển chào mừng
        this.view.getWelcomePanel().getSignUpPanel().getSigninButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getWelcomePanel().getCardLayoutManager().next(view.getWelcomePanel().getSlider());
            }
        });
        this.view.getWelcomePanel().getSignUpPanel().getSignupButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpEvt();
            }
        });
        this.view.getWelcomePanel().getSignInPanel().getSignupButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getWelcomePanel().getCardLayoutManager().next(view.getWelcomePanel().getSlider());
            }
        });
        this.view.getWelcomePanel().getSignInPanel().getSigninButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signInEvt();
            }
        });

        // Hành động trên Bảng điều khiển Trang chủ
        this.view.getHomePanel().getNewGameBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cấp độ cho trò chơi
                Difficulty level = Difficulty.valueOf(view.getHomePanel().getLevelSelector().getSelectedItem().toString().toUpperCase());

                // Tạo trò chơi mới
                Generator puzzle = new Generator();
                puzzle.generateGrid(level);
                model.setPuzzle(puzzle.getGrid());

                // Cấu hình Dạng xem
                view.getGamePanel().setViewCellList(model.getPuzzle().getCellList());
                view.getGamePanel().getLevelTitle().setText(String.valueOf(level));
                update();

                // Chuyển sang Trò chơi 
                view.getCardLayoutManager().show(view.getContent(), "game");

                // Thiết lập Hẹn giờ Trò chơi & Bắt đầu
                long start = Calendar.getInstance().getTimeInMillis() / 1000;
                model.setTimer(new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        long secondsSinceInit = ((Calendar.getInstance().getTimeInMillis() / 1000) - start);
                        view.getGamePanel().getTimeLabel().setText(String.format("%02d:%02d", secondsSinceInit / 60 % 60, secondsSinceInit % 60));
                    }
                }));
                model.getTimer().setInitialDelay(0);
                model.getTimer().start();
            }
        });
        this.view.getHomePanel().getViewRulesBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rulesCaller = "home"; // -> Các quy tắc được gọi từ bảng điều khiển 'trang chủ', vì vậy hãy quay trở lại nó khi được thực hiện
                view.getCardLayoutManager().show(view.getContent(), "rules");
            }
        });
        this.view.getHomePanel().getSignoutBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Có, đăng xuất", "Không bé ơi"};
                int result = JOptionPane.showOptionDialog(getParent(), "Bạn có chắc là sẻ đăng xuất?", "đang đăng xuất?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (result == 0) {
                    view.getCardLayoutManager().show(view.getContent(), "welcome");
                    model.setPlayer(null);
                    model.setPuzzle(null);
                    model.setHintsUsed(0);
                    model.setTimer(null);
                    view.getGamePanel().getHintBtn().setEnabled(true);
                    view.getHomePanel().getLevelSelector().setSelectedIndex(0);
                }
            }
        });

        // Hành động trên Bảng điều khiển Trò chơi & Quy tắc
        this.view.getGamePanel().getHintBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // User wants a hint, check if game has unused hints
                if (model.getHintsUsed() < model.getPuzzle().getDifficulty().getMaxHints()) {
                    model.getPuzzle().hint(false);
                    model.setHintsUsed(model.getHintsUsed() + 1);
                    update();
                    System.err.println("HINT USED: " + model.getStringHintsUsed());
                    if (model.getHintsUsed() == model.getPuzzle().getDifficulty().getMaxHints()) {
                        view.getGamePanel().getHintBtn().setEnabled(false);
                        JOptionPane.showOptionDialog(getParent(), "Đừng làm nó trở thành quá đơn giản!\nđây là lần gợi ý cuối cùng.\n\nDid you KnowBạn biết không?\nSudokus Có thể ngăn ngừa bệnh Alzheimer\nVà chứng mất trí nhớ, vì vậy đừng làm cho nó quá dễ dàng.", "Out of Hints", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    }
                    checkGridCompletion();
                }
            }
        });
        this.view.getGamePanel().getViewRulesBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hiện Quy tắc 
                rulesCaller = "game"; // -> Các quy tắc được gọi từ bảng điều khiển 'trò chơi', vì vậy hãy quay lại nó khi hoàn thành
                view.getCardLayoutManager().show(view.getContent(), "rules");
            }
        });
        this.view.getRulesPanel().getBackBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getCardLayoutManager().show(view.getContent(), rulesCaller); // -> Quay trở lại bảng điều khiển người gọi
            }
        });
        this.view.getGamePanel().getEndGameBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"đã hiểu.", "Hủy"};
                int result = JOptionPane.showOptionDialog(getParent(), "bạn có chăc là sẻ kết thúc trò chơi?\n\nSudoku này được chơi tốt nhất trong một lần ngồi,\n không thể tiếp tục sau này.", "Exit?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (result == 0) {
                    view.getCardLayoutManager().show(view.getContent(), "home");
                    destroyGameInstance();
                }
            }
        });

        // Bộ điều hợp ô
        this.cellKeyListener = new KeyAdapter() {
            /**
             * Xác thực đầu vào của người dùng cho ô
             *
             * @param evt kích hoạt sự kiện quan trọng
             */
            @Override
            public void keyTyped(KeyEvent evt) {
                Cell cell = (Cell) evt.getSource();
                // Bỏ qua mục nhập nếu không phải là 1-9 hoặc văn bản đã tồn tại
                if (!String.valueOf(evt.getKeyChar()).matches("^[1-9]$") || cell.getText().length() == 1) {
                    System.out.println("Input: " + evt.getKeyChar() + " was rejected.");
                    evt.consume();
                } else {
                    // Kiểm tra xem đầu vào có đáp ứng contraints không
                    if (!model.getPuzzle().meetsConstraints(cell, Integer.valueOf(String.valueOf(evt.getKeyChar()).trim()))) {
                        System.err.println("VALUE " + evt.getKeyChar() + " AT " + cell.getPosition() + " KHÔNG ĐÁP ỨNG SUDOKU CONTRAINTS");
                        cell.setText("");
                        cell.setUserValue(0);
                        evt.consume();
                    } else {
                        cell.setUserValue(Integer.valueOf(String.valueOf(evt.getKeyChar()).trim()));
                    }
                    checkGridCompletion();
                }
            }

        };
        this.cellMouseListener = new MouseAdapter() {
            // Cell Hover Attribute
            private Color preActionColor;

            /**
             * Trình xử lý sự kiện để nhấn nút chuột
             *
             * @param evt kích hoạt sự kiện
             */
            @Override
            public void mousePressed(MouseEvent evt) {
                Cell cell = (Cell) evt.getSource();

                // Trên Bấm chuột phải, xóa ô
                if (evt.getButton() == MouseEvent.BUTTON3) {
                    cell.setText("");
                    cell.setUserValue(0);
                }

                cell.selectAll();
            }

            /**
             * Tô sáng các ràng buộc của trò chơi đối với ô lơ lửng
             *
             * @param evt ô đang lơ lửng
             */
            @Override
            public void mouseEntered(MouseEvent evt) {
                Cell cell = (Cell) evt.getSource();
                preActionColor = cell.getBackground();

                // Tô sáng ô Hợp lệ
                for (Cell aCell : view.getGamePanel().getViewCellList()) {
                    if (cell.getPosition().getRow() == aCell.getPosition().getRow()) {
                        aCell.setBackground(APP_GREEN.darker().darker());
                    }
                    if (cell.getPosition().getColumn() == aCell.getPosition().getColumn()) {
                        aCell.setBackground(APP_GREEN.darker().darker());
                    }
                    if (cell.getPosition().getSubgrid() == aCell.getPosition().getSubgrid()) {
                        aCell.setBackground(APP_GREEN.darker().darker());
                    }
                }

                cell.setBackground(APP_GREEN);
            }

            /**
             * Khôi phục màu di chuột từ sự kiện di chuột
             *
             * @param evt ô lơ lửng được thoát ra khỏi
             */
            @Override
            public void mouseExited(MouseEvent evt) {
                Cell cell = (Cell) evt.getSource();

                // Khôi phục Màu
                for (Cell aCell : view.getGamePanel().getViewCellList()) {
                    if (aCell.isLocked()) {
                        aCell.setBackground(BKGD_DARK_GRAY);
                    } else {
                        aCell.setBackground(BKGD_LIGHT_GRAY);
                    }
                }

                cell.setBackground(preActionColor);
            }

        };
    }

    /**
     * Điểm nhập hồ sơ.
     *
     * @param args Đối số khởi động tùy chọn
     */
    public static void main(String[] args) {
        JFrame frame = new SudokuGameApp("Sudoku");
        //ImageIcon img = new ImageIcon("logo.png");
        //frame.setIconImage(img.getImage());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Đăng nhập người dùng vào ứng dụng trên thông tin đăng nhập chính xác, nếu không từ chối
     * them.
     */
    private void signInEvt() {
        // Truy xuất Chi tiết
        String email = this.view.getWelcomePanel().getSignInPanel().getEmailText().getText().trim();
        String password = new String(this.view.getWelcomePanel().getSignInPanel().getPasswordText().getPassword()).trim();
        if (!email.equals("") && !password.equals("")) {
            if (model.getSudokuDB().checkLogin(email, password)) {
                // Đặt Bộ phát
                Player player = model.getSudokuDB().loadPlayer(email, password);
                if (player != null) {
                    model.setPlayer(model.getSudokuDB().loadPlayer(email, password));
                    // Xóa Trường
                    view.getWelcomePanel().getSignInPanel().clear();
                    // Hiện Màn hình Chính
                    refreshHomePanel();
                    view.getCardLayoutManager().show(view.getContent(), "home");
                } else {
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(this, "Lỗi xảy ra trong quá trình đăng nhập, vui lòng thử lại", "Lỗi đăng nhập", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
                }
            } else {
                Object[] options = {"Let me try again"};
                JOptionPane.showOptionDialog(this, "Thông tin đăng nhập bạn đã cung cấp không hợp lệ, vui lòng nhập chính xác hoặc tạo tài khoản mới.", "Chứng danh không hợp lệ", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
            }
        } else {
            Object[] options = {"Alright"};
            JOptionPane.showOptionDialog(this, "Để đăng nhập, tất cả các trường phải được điền vào.", "Trường trống", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
        }
    }

    /**
     * Đăng nhập người dùng và đăng ký chúng trong cơ sở dữ liệu.
     */
    private void signUpEvt() {
        String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        // Tải Thông tin chi tiết người dùng
        String fullname = this.view.getWelcomePanel().getSignUpPanel().getFullnameText().getText().trim();
        String email = this.view.getWelcomePanel().getSignUpPanel().getEmailText().getText().trim();
        String password = new String(this.view.getWelcomePanel().getSignUpPanel().getPasswordText().getPassword()).trim();

        if (!fullname.equals("") && !email.equals("") && !password.equals("")) {
            if (email.matches(emailRegex)) {
                if (model.getSudokuDB().registerUser(fullname, email, password)) {
                    view.getWelcomePanel().getCardLayoutManager().next(view.getWelcomePanel().getSlider());
                    // Xóa Trường
                    view.getWelcomePanel().getSignUpPanel().clear();
                    Object[] options = {"OK"};
                    JOptionPane.showOptionDialog(this, "Đăng ký của bạn đã thành công!\n Bây giờ bạn có thể đăng nhập vào tài khoản của mình.", "Đăng ký thành công", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
                } else {
                    Object[] options = {"Hãy để tôi thử lại"};
                    JOptionPane.showOptionDialog(this, "Đăng ký của bạn không thành công!\nBe chắc chắn không tạo tài khoản trùng lặp.", "Đăng ký không thành công", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, null);
                }
            } else {
                // Email không đáp ứng yêu cầu
                Object[] options = {"Tôi sẽ sửa điều đó."};
                JOptionPane.showOptionDialog(this, "Bạn phải cung cấp địa chỉ email hợp lệ.", "Địa chỉ Email không hợp lệ", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
            }
        } else {
            // Trường Trống
            Object[] options = {"để tôi nhập lại"};
            JOptionPane.showOptionDialog(this, "Để đăng ký, tất cả các trường phải được điền vào.", "Trường trống", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);
        }
    }

    /**
     * Updates (refreshes) the Home panel.
     */
    private void refreshHomePanel() {
        // Đặt Văn bản Tên Người chơi
        view.getHomePanel().getNameLabel().setText(model.getPlayer().getFullname().toUpperCase());

        // Cập nhật Bảng Highscore
        view.getHomePanel().getTableModel().setRowCount(0);
        updateHighscores(model.getHighscores());
    }

    /**
     * Xem trình xử lý sự kiện cập nhật.
     */
    private void update() {
        // Đặt cho từng ô
        for (Cell cell : this.view.getGamePanel().getViewCellList()) {
            cell.setBackground(BKGD_DARK_GRAY);
            cell.setForeground(Color.WHITE);
            cell.setFont(new Font("Halvetica Neue", Font.PLAIN, 36));
            cell.setBorder(new LineBorder(Color.BLACK, 0));
            cell.setHorizontalAlignment(JTextField.CENTER);
            cell.setCaretColor(new Color(32, 44, 53));
            cell.setDragEnabled(false);
            cell.setTransferHandler(null);

            // Thêm dấu tách lưới con
            CellPosition pos = cell.getPosition();
            if (pos.getColumn() == 2 || pos.getColumn() == 5) {
                cell.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(146, 208, 80)));
            } else if (pos.getRow() == 2 || pos.getRow() == 5) {
                cell.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(146, 208, 80)));
            }
            if ((pos.getColumn() == 2 && pos.getRow() == 2) || (pos.getColumn() == 5 && pos.getRow() == 5)
                    || (pos.getColumn() == 2 && pos.getRow() == 5) || (pos.getColumn() == 5 && pos.getRow() == 2)) {
                cell.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, new Color(146, 208, 80)));
            }

            // Xác thực Đầu vào Ô + Chuột của Người dùng 
            cell.removeKeyListener(cellKeyListener);
            cell.removeMouseListener(cellMouseListener);
            if (cell.isLocked()) {
                cell.setEditable(false);
                cell.setHighlighter(null);
            } else {
                cell.setBackground(BKGD_LIGHT_GRAY);
                cell.addMouseListener(cellMouseListener);
                cell.addKeyListener(cellKeyListener);
            }
            if (cell.isEmpty()) {
                cell.setText("");
            } else {
                cell.setText(String.valueOf(cell.getUserValue()));
            }

            // Thêm ô vào lưới của dạng xem
            this.view.getGamePanel().getGrid().add(cell);
        }

    }

    /**
     * Cập nhật mô hình bảng với điểm số của người chơi
     *
     * @param tableContent Danh sách các người chơi bao gồm
     */
    public void updateHighscores(ArrayList<Player> tableContent) {
        // Thêm điểm vào bảng
        for (Player p : tableContent) {
            view.getHomePanel().getTableModel().addRow(playerToObjArray(p));
        }
    }

    /**
     * Trích xuất thông tin điểm chính từ đối tượng Player vào mảng Chuỗi
     *
     * @param player người chơi bị ảnh hưởng
     * @return một mảng chuỗi với điểm số và tên người chơi
     */
    public static Object[] playerToObjArray(Player player) {
        // Chia Player thành các phần tương ứng của nó
        Object[] initList = new Object[2];
        initList[0] = player.getScore();
        initList[1] = player.getFullname();
        return initList;
    }

    /**
     * Kiểm tra độ hoàn thành lưới điện hiện tại của người chơi
     */
    private void checkGridCompletion() {
        if (this.model.getPuzzle().isFilled()) {
            if (this.model.getPuzzle().isSolved()) {
                puzzleCompleted();
            }
        }
    }

    /**
     * Các sự kiện khi hoàn thành lưới điện Sudoku
     */
    private void puzzleCompleted() {
        // Bộ hẹn giờ dừng
        this.model.getTimer().stop();
        String gameTime = view.getGamePanel().getTimeLabel().getText();

        // Khóa tất cả các ô để ngăn chỉnh sửa
        for (Cell cell : this.model.getPuzzle().getCellList()) {
            cell.setLocked(true);
        }

        update();

        // Điểm thưởng
        this.model.increaseScore(10);
        Object[] options = {"OK!"};
        JOptionPane.showOptionDialog(this, "Bạn đã giải được Câu đố.\n\n Thời gian trò chơi: " + gameTime + "\nGợi ý đã dùng: " + this.model.getStringHintsUsed() + "\n\nĐiểm số mới của bạn: " + this.model.getPlayer().getScore() + " Điểm.", "Chúc mừng!", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);

        // Trở về màng hình chính
        refreshHomePanel();
        view.getCardLayoutManager().show(view.getContent(), "home");

        // Hủy bỏ
        destroyGameInstance();
    }

    /**
     * Xóa cài đặt trò chơi sau trò chơi
     */
    private void destroyGameInstance() {
        // Hủy bỏ
        this.model.setPuzzle(null);
        this.model.setHintsUsed(0);
        this.model.getTimer().stop();
        this.model.setTimer(null);
        view.getGamePanel().getHintBtn().setEnabled(true);
        for (Cell cell : this.view.getGamePanel().getViewCellList()) {
            this.view.getGamePanel().getGrid().remove(cell);
        }
    }
}
