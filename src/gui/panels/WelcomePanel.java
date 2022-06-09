package gui.panels;

import java.awt.CardLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * Dạng xem Bảng điều khiển Chào mừng
*/
public class WelcomePanel extends JPanel {

    // Thuộc tính Bảng điều khiển Chào mừng
    private final CardLayout cardLayoutManager = new CardLayout();
    private final SignUpPanel signUpPanel = new SignUpPanel();
    private final SignInPanel signInPanel = new SignInPanel();
    private final JPanel slider = new JPanel();

    /**
     * Xây dựng bảng điều khiển chào mừng
     */
    public WelcomePanel() {

        this.setLayout(new GridLayout(0, 2));

        slider.setLayout(this.cardLayoutManager);
        slider.add(this.signUpPanel);
        slider.add(this.signInPanel);

        // Thêm vào Bảng điều khiển
        this.add(new WelcomeImage(500, 550));
        this.add(slider);
    }

    /**
     * @return the cardLayoutManager
     */
    public CardLayout getCardLayoutManager() {
        return cardLayoutManager;
    }

    /**
     * @return thanh trượt
     */
    public JPanel getSlider() {
        return slider;
    }

    /**
     * @return the signUpPanel
     */
    public SignUpPanel getSignUpPanel() {
        return signUpPanel;
    }

    /**
     * @return the signInPanel
     */
    public SignInPanel getSignInPanel() {
        return signInPanel;
    }
}
