package gui.panels;

import gui.AppJButton;
import static gui.SudokuGame.APP_GREEN;
import static gui.SudokuGame.BKGD_DARK_GRAY;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Lưu trữ view cho quy tắc Panel
*/
public class RulesPanel extends JPanel {

    // Thuộc tính bảng Quy tắc
    private final String rules;
    private final AppJButton backBtn;

    /**
     * Xây dựng Bảng Quy tắc.
     */
    public RulesPanel() {

        this.rules = "<html>Các quy tắc rất đơn giản, có 81 ô vuông trên lưới. <br>\"\n" +
"                + \"Một lưới được chia thành 9 subgrids (khối), mỗi ô chứa 9 ô. <br><br>\"\n" +
"                + \"Ban đầu, một ô có thể chứa một chữ số hoặc trống rỗng. <br>\"\n" +
"                + \"Sử dụng những \\\"gợi ý\\\", tùy thuộc vào bạn để điền chính xác mọi ô \"\n" +
"                + \"trên lưới có chữ số riêng (1-9). <br><br>\"\n" +
"                + \"Cái bẫy là gì? <br>\"\n" +
"                + \" > Mỗi chữ số chỉ phải xuất hiện một lần trong mỗi hàng. <br>\"\n" +
"                + \" > Mỗi chữ số chỉ phải xuất hiện một lần trong mỗi cột. <br>\"\n" +
"                + \" > Mỗi chữ số chỉ phải xuất hiện một lần trong mỗi subgrid. <br><br>\"\n" +
"                + \"Nghe có vẻ dễ dàng, phải không? <br>\"\n" +
"                + \"Làm thế nào về việc thay đổi mức độ khó của trò chơi? \"\n" +
"                + \"Thực tế có hơn 10 tỷ lưới độc đáo. <br><br>\"\n" +
"                + \"chơi vui vẻ!!</html>";

        this.setLayout(new GridLayout(1, 0));
        this.setBackground(BKGD_DARK_GRAY);

        // Nội dung chính của Panel
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.setBackground(BKGD_DARK_GRAY);

            JLabel titleLabel = new JLabel("Luật chơi");
            titleLabel.setFont(new Font("Avenir", Font.PLAIN, 24));
            titleLabel.setForeground(Color.white);
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            content.add(titleLabel, BorderLayout.NORTH);

            JLabel text = new JLabel(this.rules);
            text.setFont(new Font("Avenir", Font.PLAIN, 14));
            text.setForeground(Color.white);
            text.setBackground(BKGD_DARK_GRAY);
            text.setBorder(new EmptyBorder(10, 10, 10, 10));
            content.add(text, BorderLayout.CENTER);

            backBtn = new AppJButton("Quay lại", 24, APP_GREEN, BKGD_DARK_GRAY);
            content.add(backBtn, BorderLayout.SOUTH);

        this.add(new WelcomeImage(500, 550));
        this.add(content);

    }

    /**
     * @return nút Quay lại
     */
    public AppJButton getBackBtn() {
        return backBtn;
    }
}
