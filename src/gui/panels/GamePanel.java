package gui.panels;

import gui.AppJButton;
import static gui.SudokuGame.APP_GREEN;
import static gui.SudokuGame.BKGD_DARK_GRAY;
import static gui.SudokuGame.BKGD_LIGHT_GRAY;
import static java.awt.Component.CENTER_ALIGNMENT;
import gui.model.Cell;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * Xem bảng điều khiển trò chơi Sudoku
 */
public class GamePanel extends JPanel {
    
    // Thuộc tính Bảng trò chơi
    private List<Cell> viewCellList;
    private final JButton endGameBtn;
    private final JButton viewRulesBtn;
    private final JButton hintBtn;
    private final JPanel grid;
    private JLabel levelTitle;
    private JLabel timeLabel;
    
    /**
     * Xây dựng bảng điều khiển trò chơi.
     */
    public GamePanel() {
        
        this.setLayout(new BorderLayout());

        // Biểu ngữ
        JPanel banner = new JPanel();
        banner.setLayout(new BoxLayout(banner, BoxLayout.LINE_AXIS));
        banner.setPreferredSize(new Dimension(1000, 115));
        banner.setBackground(BKGD_DARK_GRAY);
        banner.setAlignmentX(CENTER_ALIGNMENT);
        
            // Sudoku Logo
            LogoImage jP1 = new LogoImage(115, 115);
            jP1.setBackground(BKGD_DARK_GRAY);
            jP1.setPreferredSize(new Dimension(115, 115));
            jP1.setMaximumSize(new Dimension(115, 115));
            jP1.setAlignmentY(CENTER_ALIGNMENT);
            
            // Khoảng trống
            banner.add(Box.createRigidArea(new Dimension(5,0)));
            banner.add(jP1);
            
            // Nội dung Biểu ngữ Động
            JPanel jP2 = new JPanel();
            jP2.setBackground(BKGD_DARK_GRAY);
            jP2.setPreferredSize(new Dimension(200, 100));
            jP2.setLayout(new GridLayout(2,0));
            
                timeLabel = new JLabel();
                timeLabel.setFont(new Font("Avenir", Font.PLAIN, 36));
                timeLabel.setForeground(Color.WHITE);
                timeLabel.setVerticalAlignment(JLabel.BOTTOM);
                timeLabel.setHorizontalAlignment(JLabel.RIGHT);
                jP2.add(timeLabel);

                levelTitle = new JLabel();
                levelTitle.setFont(new Font("Avenir", Font.PLAIN, 24));
                levelTitle.setForeground(Color.WHITE);
                levelTitle.setVerticalAlignment(JLabel.TOP);
                levelTitle.setHorizontalAlignment(JLabel.RIGHT);
                jP2.add(levelTitle);
                
            banner.add(jP2);
            banner.add(Box.createRigidArea(new Dimension(15,0)));
        this.add(banner, BorderLayout.NORTH);

        // Nội dung chính
        JPanel main = new JPanel();
        main.setLayout(null);
        main.setBackground(BKGD_DARK_GRAY);           
                       
            JPanel actions = new JPanel();
            actions.setLayout(new GridLayout(3,1));
            actions.setSize(135, 90);
            actions.setLocation(0, 415 - actions.getHeight());

                // Nút Lấy Gợi ý
                hintBtn = new AppJButton("Gợi ý", 14, BKGD_LIGHT_GRAY, APP_GREEN);
                actions.add(hintBtn);
            
                // Nút Xem Quy tắc
                viewRulesBtn = new AppJButton("xem luật chơi", 14, BKGD_LIGHT_GRAY, APP_GREEN);
                actions.add(viewRulesBtn);
            
                // Nút Đăng xuất
                endGameBtn = new AppJButton("kết thúc", 14, BKGD_LIGHT_GRAY, APP_GREEN);
                actions.add(endGameBtn);
            main.add(actions);
            
            // Bảng lưới trò chơi
            grid = new JPanel();
            grid.setLayout(new GridLayout(9, 9));
            grid.setPreferredSize(new Dimension(120, 120));
            grid.setMaximumSize(new Dimension(433, 433));
            grid.setBorder(new LineBorder(APP_GREEN, 2));
            grid.setBackground(BKGD_DARK_GRAY.darker());
            grid.setForeground(Color.white);
            grid.setLocation(285, 0);
            grid.setSize(400, 400);
            
        main.add(grid);          
        this.add(main);
    }    
    
    /**
     * @return the endGameBtn
     */
    public JButton getEndGameBtn() {
        return endGameBtn;
    }

    /**
     * @return the viewRulesBtn
     */
    public JButton getViewRulesBtn() {
        return viewRulesBtn;
    }

    /**
     * @return the hintBtn
     */
    public JButton getHintBtn() {
        return hintBtn;
    }

    /**
     * @return the levelTitle
     */
    public JLabel getLevelTitle() {
        return levelTitle;
    }

    /**
     * @param levelTitle levelTitle để thiết lập
     */
    public void setLevelTitle(JLabel levelTitle) {
        this.levelTitle = levelTitle;
    }

    /**
     * @return the timeLabel
     */
    public JLabel getTimeLabel() {
        return timeLabel;
    }

    /**
     * @param timeLabel thời gianLabel để thiết lập
     */
    public void setTimeLabel(JLabel timeLabel) {
        this.timeLabel = timeLabel;
    }

    /**
     * @return lưới
     */
    public JPanel getGrid() {
        return grid;
    }

    /**
     * @return dạng xemCellList
     */
    public List<Cell> getViewCellList() {
        return viewCellList;
    }

    /**
     * @param viewCellList dạng xemCellList để đặt
     */
    public void setViewCellList(List<Cell> viewCellList) {
        this.viewCellList = viewCellList;
    }
}
