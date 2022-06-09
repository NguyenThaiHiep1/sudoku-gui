package gui.model;

import java.util.Objects;
import javax.swing.JTextField;

/**
 * Thông tin về một ô riêng lẻ trong lưới
*/
public class Cell extends JTextField {

    // Thuộc tính Ô
    protected int userValue;
    protected int solutionValue;
    protected int provisionalValue;
    private boolean locked;
    private final CellPosition position;
    private static final String COLOUR_RED = "\u001B[31m";
    private static final String COLOUR_RESET = "\u001B[0m";

    /**
     * Xây dựng đối tượng Ô ở hàng và cột
     *
     * @param row hàng của vị trí ô
     * @param column cột của vị trí ô
     */
    public Cell(int row, int column) {
        this.position = new CellPosition(row, column);
    }

    /**
     * @return vị trí của ô này
     */
    public CellPosition getPosition() {
        return this.position;
    }

    /**
     * @return đúng nếu tế bào bị khóa, nếu khác sai
     */
    public boolean isLocked() {
        return this.locked;
    }

    /**
     * @param locked khóa / mở khóa ô để ngăn chỉnh sửa
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * @return đúng nếu ô trống rỗng (tức là 0), sai nếu ô không trống (tức là không 0)
     */
    public boolean isEmpty() {
        return getUserValue() == 0;
    }

    /**
     * @return giá trị người dùng đã chọn cho ô này
     */
    public int getUserValue() {
        return this.userValue;
    }

    /**
     * @param userValue giá trị người dùng đã chọn cho ô này
     */
    public void setUserValue(int userValue) {
        this.userValue = userValue;
    }

    /**
     * @return giá trị thực (giải pháp) của tế bào
     */
    public int getSolutionValue() {
        return this.solutionValue;
    }

    /**
     * Đặt giá trị giải pháp của ô thành userValue
     */
    public void setSolutionValue() {
        this.solutionValue = userValue;
    }

    /**
     * Đặt giá trị tạm thời của ô vào mục nhập của người dùng
     */
    public void storeProvisionalValue() {
        this.provisionalValue = this.userValue;
    }

    /**
     * Nhận được giá trị tạm thời của ô
     */
    public void fetchProvisionalValue() {
        this.userValue = this.provisionalValue;
    }

    /**
     * @return biểu diễn trực quan của ô này trong lưới
     */
    @Override
    public String toString() {
        if (this.isLocked()) {
            // Bỏ dòng sau nếu bạn không sử dụng NetBeans:
            //return "[" + getUserValue() + "]";

            // NetBeans chỉ! In các ô bị khóa/tạo màu đỏ (không liên quan đến GUI):
            return "[" + COLOUR_RED + getUserValue() + COLOUR_RESET + "]";
        }
        return ("[" + (isEmpty() ? "_" : getUserValue()) + "]");
    }

    /**
     * @return mô tả văn bản của ô
     */
    public String cellDescription() {
        // Description: Cell position + cell's subgrid + cell's value/empty
        String description = "Cell at " + getPosition() + " (subgrid " + (getPosition().getSubgrid() + 1) + ")";
        description += (isLocked() ? " cannot be edited. " : (!isEmpty() ? " contains " + getUserValue() + "." : " is clear."));
        return description;
    }

    /**
     * So sánh ô 'this' với ô đầu vào
     *
     * @param object đối tượng đầu vào sẽ được so sánh với đối tượng 'này'
     * (Cell)
     * @return đúng nếu các đối tượng bằng nhau, các đối tượng khác sai
     */
    @Override
    public boolean equals(Object object) {
        return object != null
                && object.getClass() == this.getClass()
                && ((Cell) object).getUserValue() == this.getUserValue()
                && ((Cell) object).getPosition().getRow() == this.getPosition().getRow()
                && ((Cell) object).getPosition().getColumn() == this.getPosition().getColumn();
    }

    /**
     * @return mã băm được tạo cho ô
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.userValue;
        hash = 79 * hash + this.solutionValue;
        hash = 79 * hash + this.provisionalValue;
        hash = 79 * hash + (this.locked ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.position);
        return hash;
    }
}
