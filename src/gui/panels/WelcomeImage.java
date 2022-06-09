package gui.panels;

import static gui.SudokuGame.BKGD_DARK_GRAY;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Hình ảnh chào mừng của ứng dụng trò chơi Sudoku
*/
public class WelcomeImage extends JPanel {

    // Thuộc tính Hình ảnh Chào mừng
    private BufferedImage background;

    /**
     * Xây dựng hình ảnh chào mừng với kích thước ưa thích
     *
     * @param width chiều rộng của hình ảnh
     * @param height đỉnh cao của hình ảnh
     */
    public WelcomeImage(int width, int height) {
        try {
            this.background = ImageIO.read(getClass().getResource("/fullBackgd.png"));
            setPreferredSize(new Dimension(width, width));
        } catch (Exception ex) {
            System.err.println("Error Welcome Image: " + ex);
            setBackground(BKGD_DARK_GRAY);
        }
    }

    /**
     * Sơn hình ảnh vào thùng chứa
     *
     * @param g không gian đồ họa
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (this.background != null) {
            g.drawImage(this.background, 0, 0, this);
        } else {
            super.paintComponent(g);
        }
    }
}
