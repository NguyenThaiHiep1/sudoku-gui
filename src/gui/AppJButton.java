package gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;

/**
 * Nút Tùy chỉnh cho Panels Sudoku
 */
public class AppJButton extends JButton {

    /**
     * Nút Tùy chỉnh cho Panels Sudoku 
     *
     * @param buttonText văn bản của nút
     * @param fontSize kích thước phông chữ của văn bản
     * @param background màu nền
     * @param foreground màu tiền cảnh
     */
    public AppJButton(String buttonText, int fontSize, Color background, Color foreground) {

        // Thuộc tính Nút
        this.setText(buttonText);
        this.setFont(new Font("Halvetica Neue", Font.PLAIN, fontSize));
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.setOpaque(true);
        this.setBackground(background);
        this.setForeground(foreground);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Thay đổi màu nền hiệu ứng di chuột
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(new Color(72, 84, 93));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(background);
            }
        });
    }
}
