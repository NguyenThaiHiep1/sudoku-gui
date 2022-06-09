package gui.panels;

import static gui.SudokuGame.BKGD_DARK_GRAY;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Logo của Ứng dụng trò chơi Sudoku
*/
public class LogoImage extends JPanel {

    // Thuộc tính Logo
    private BufferedImage logo;

    /**
     * Xây dựng logo với kích thước ưa thích
     *
     * @param width chiều rộng của logo
     * @param height đỉnh của logo
     */
    public LogoImage(int width, int height) {
        try {
            this.logo = ImageIO.read(getClass().getResource("/logo.png"));
            setPreferredSize(new Dimension(width, width));
        } catch (Exception ex) {
            System.err.println("Error Logo Image: " + ex);
            setBackground(BKGD_DARK_GRAY);
        }
    }

    /**
     * Sơn logo vào thùng chứa
     *
     * @param g không gian đồ họa
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (this.logo != null) {
            g.drawImage(this.logo, 0, 0, 115, 115, this);
        } else {
            super.paintComponent(g);
        }
    }
}
