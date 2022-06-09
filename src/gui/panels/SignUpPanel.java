package gui.panels;

import gui.AppJButton;
import static gui.SudokuGame.APP_GREEN;
import static gui.SudokuGame.BKGD_DARK_GRAY;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Dạng xem Bảng điều khiển Đăng ký
*/
public class SignUpPanel extends JPanel {

    // Thuộc tính Bảng đăng ký
    private final JTextField fullnameText;
    private final JTextField emailText;
    private final JPasswordField passwordText;
    private final AppJButton signupButton;
    private final AppJButton signinButton;

    /**
     * Xây dựng Bảng đăng ký
     */
    public SignUpPanel() {

        this.setLayout(new GridLayout(9, 0));
        this.setBackground(BKGD_DARK_GRAY);

        // Nhãn Tiêu đề
        JLabel actionLabel = new JLabel("Đăng nhập và đăng ký");
        actionLabel.setFont(new Font("Avenir", Font.PLAIN, 24));
        actionLabel.setForeground(Color.white);
        actionLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(actionLabel);

        // Nhãn tên đầy đủ
        JLabel fullnameLabel = new JLabel("Họ và tên");
        fullnameLabel.setFont(new Font("Halvetica Neue", Font.PLAIN, 14));
        fullnameLabel.setForeground(Color.white);
        fullnameLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(fullnameLabel);

        // Trường Văn bản Tên đầy đủ
        fullnameText = new JTextField();
        fullnameText.setBackground(BKGD_DARK_GRAY);
        fullnameText.setForeground(Color.white);
        fullnameText.setHorizontalAlignment(JLabel.CENTER);
        fullnameText.setFont(new Font("Halvetica Neue", Font.PLAIN, 14));
        fullnameText.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, APP_GREEN));
        this.add(fullnameText);

        // Nhãn Email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Halvetica Neue", Font.PLAIN, 14));
        emailLabel.setForeground(Color.white);
        emailLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(emailLabel);

        // Trường Văn bản Tên đầy đủ
        emailText = new JTextField();
        emailText.setBackground(BKGD_DARK_GRAY);
        emailText.setForeground(Color.white);
        emailText.setHorizontalAlignment(JLabel.CENTER);
        emailText.setFont(new Font("Halvetica Neue", Font.PLAIN, 14));
        emailText.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, APP_GREEN));
        this.add(emailText);

        // Nhãn Mật khẩu
        JLabel passwordLabel = new JLabel("Mật khẩu");
        passwordLabel.setFont(new Font("Halvetica Neue", Font.PLAIN, 14));
        passwordLabel.setForeground(Color.white);
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(passwordLabel);

        // Trường Văn bản Mật khẩu
        passwordText = new JPasswordField();
        passwordText.setBackground(BKGD_DARK_GRAY);
        passwordText.setForeground(Color.white);
        passwordText.setHorizontalAlignment(JLabel.CENTER);
        passwordText.setFont(new Font("Halvetica Neue", Font.PLAIN, 14));
        passwordText.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, APP_GREEN));
        this.add(passwordText);

        // Nút Đăng ký
        signupButton = new AppJButton("Đăng ký", 14, APP_GREEN, BKGD_DARK_GRAY);
        this.add(signupButton);

        // Nút Đăng nhập
        signinButton = new AppJButton("Tôi đã có tài khoản", 10, BKGD_DARK_GRAY, APP_GREEN);
        this.add(signinButton);

    }

    /**
     * Xóa trường văn bản trong dạng xem này.
     */
    public void clear() {
        fullnameText.setText("");
        emailText.setText("");
        passwordText.setText("");
    }

    /**
     * @return the signupButton
     */
    public JButton getSignupButton() {
        return signupButton;
    }

    /**
     * @return the signinButton
     */
    public JButton getSigninButton() {
        return signinButton;
    }

    /**
     * @return the fullnameText
     */
    public JTextField getFullnameText() {
        return fullnameText;
    }

    /**
     * @return the emailText
     */
    public JTextField getEmailText() {
        return emailText;
    }

    /**
     * @return the passwordText
     */
    public JPasswordField getPasswordText() {
        return passwordText;
    }
}
