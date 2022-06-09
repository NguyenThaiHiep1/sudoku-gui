package gui.model;

/**
 * Thông tin về vị trí của ô trong lưới
*/
public class CellPosition {

    // Thuộc tính Vị trí Ô
    private final int row;
    private final int column;
    private final int subgrid;

    /**
     * Xây dựng đối tượng CellPosition ở hàng và cột
     *
     * @param row hàng của vị trí ô
     * @param column cột của vị trí ô
     */
    public CellPosition(int row, int column) {
        this.row = row;
        this.column = column;

        // Đánh giá wich sub-grid mà tế bào này liên kết chính nó
        int evaluate = this.row < 3 ? 0 : this.row < 6 ? 2 : 4;
        this.subgrid = (this.row / 3) + (this.column / 3) + evaluate;
    }

    /**
     * @return vị trí hàng
     */
    public int getRow() {
        return this.row;
    }

    /**
     * @return vị trí cột
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * @return vị trí lưới phụ (dựa trên 0)
     */
    public int getSubgrid() {
        return this.subgrid;
    }

    /**
     * @return định dạng vị trí ô (ví dụ: 1A), trên trả về thất bại [hàng,cột]
     * định dạng
     */
    @Override
    public String toString() {
        if (getColumn() + 65 >= 'A' && getColumn() + 65 <= 'Z') {
            return String.valueOf(getRow() + 1) + (char) (getColumn() + 65);
        }
        return String.valueOf(getRow() + 1) + "," + String.valueOf(getColumn() + 1);
    }
}
