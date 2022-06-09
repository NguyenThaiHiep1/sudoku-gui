package gui;

import gui.panels.RulesPanel;
import gui.panels.GamePanel;
import gui.panels.HomePanel;
import gui.panels.WelcomePanel;
import java.awt.CardLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * Đây là Trình quản lý Bảng trò chơi Sudoku (VIEW) cho tất cả các bảng.
*/
public class SudokuGamePanel extends JPanel {

    // Thuộc tính Trình quản lý XEM
    private final WelcomePanel welcomePanel;
    private final CardLayout cardLayoutManager = new CardLayout();
    private final JPanel content = new JPanel();
    private final HomePanel homePanel;
    private final GamePanel gamePanel;
    private final RulesPanel rulesPanel;

    /**
     * Xem Trình xây dựng Trình quản lý.
     */
    public SudokuGamePanel() {

        // Bố trí Lưới
        this.setLayout(new GridLayout());

        // Thiết lập Bảng điều khiển
        this.welcomePanel = new WelcomePanel();
        this.homePanel = new HomePanel();
        this.gamePanel = new GamePanel();
        this.rulesPanel = new RulesPanel();

        // Bảng Xem Nội dung
        content.setLayout(cardLayoutManager);
        content.add(this.welcomePanel);
        content.add(this.homePanel);
        content.add(this.gamePanel);
        content.add(this.rulesPanel);

        // Đặt Mã định danh Bảng điều khiển
        cardLayoutManager.addLayoutComponent(this.welcomePanel, "welcome");
        cardLayoutManager.addLayoutComponent(this.homePanel, "home");
        cardLayoutManager.addLayoutComponent(this.gamePanel, "game");
        cardLayoutManager.addLayoutComponent(this.rulesPanel, "rules");

        // Thiết lập Pa-nen Khởi động
        cardLayoutManager.show(content, "welcome");

        // Thêm Nội dung
        this.add(content);
    }

    /**
     * @return chào mừngPanel
     */
    public WelcomePanel getWelcomePanel() {
        return welcomePanel;
    }

    /**
     * @trả lại nhàPanel
     */
    public HomePanel getHomePanel() {
        return homePanel;
    }

    /**
     * @return trò chơiPanel
     */
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    /**
     * @return the cardLayoutManager
     */
    public CardLayout getCardLayoutManager() {
        return cardLayoutManager;
    }

    /**
     * @return nội dung
     */
    public JPanel getContent() {
        return content;
    }

    /**
     * @return quy tắcPanel
     */
    public RulesPanel getRulesPanel() {
        return rulesPanel;
    }
}
