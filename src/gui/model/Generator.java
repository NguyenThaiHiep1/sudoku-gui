package gui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Tạo câu đố Sudoku mới.
 */
public class Generator {

    // Thuộc tính Máy phát điện
    private Grid newGrid;
    private int numEmpty;
    private final Solution solution;

    /**
     * Xây dựng một giải pháp mới cho lưới điện.
     */
    public Generator() {
        // Tạo giải pháp cho phiên bản mới
        this.solution = new Solution();
    }

    /**
     * Tạo lưới Sudoku phụ thuộc vào độ khó.
     *
     * @param diff độ khó do người dùng chọn
     * @return một lưới hợp lệ với các ô đang mở được lấp đầy bởi người dùng
     */
    public Grid generateGrid(Difficulty diff) {

        // Nhận giải pháp cho lưới 
        this.setGrid(new Grid(diff));
        this.solution.solveFor(getGrid()).findSolution(getGrid().getCellList(), 1);

        // Đặt giá trị giải pháp cho từng ô
        for (Cell cell : getGrid()) {
            cell.setSolutionValue();
        }

        // Loại bỏ một số chữ số khỏi lưới
        setNumEmpty(diff.numEmptyCells());
        emptyCells(getNumEmpty());

        // Lưu & trả về lưới hợp lệ với các ô đang mở 
        this.getGrid().provisionCells();
        return this.getGrid();
    }

    /**
     * @param grid lưới chứa các ô
     * @return danh sách tất cả các ô trống trên lưới
     */
    public static List<Cell> allEmptyCells(Grid grid) {
        List<Cell> emptyList = new ArrayList<>();
        // Add the cell to emptyList if cell is empty
        for (Cell cell : grid) {
            if (cell.isEmpty()) {
                emptyList.add(cell);
            }
        }
        return emptyList;
    }

    /**
     * Loại bỏ số lượng chữ số phụ thuộc vào độ khó từ lưới điện.
     *
     * @param numRemove số lượng chữ số (ô) để trống
     */
    public void emptyCells(int numRemove) {
        // Set every cell provisional value
        getGrid().provisionCells();

        // Thực thi cho từng ô trong lưới
        while (getGrid().iterator().hasNext()) {

            // Nếu ô không trống, hãy làm trống nó
            Cell cell = getGrid().iterator().next();
            cell.storeProvisionalValue();

            if (!cell.isEmpty()) {
                cell.setUserValue(0);
            } else {
                continue;
            }

            // Tìm giải pháp cho lưới điện
            this.solution.solveFor(getGrid());

            // Dừng làm trống ô nếu đạt đến số ô trống đã chỉ định
            if (numRemove == allEmptyCells(getGrid()).size()) {
                break;
            } else {
                // Nếu một giải pháp duy nhất có thể được sản xuất với các tế bào bị thiếu
                if (this.solution.findSolution(allEmptyCells(getGrid()), 3) != 1) {
                    getGrid().fetchCellProvision();
                } else {
                    cell.storeProvisionalValue();
                }
            }
        }

        // Khóa tất cả các ô gợi ý      
        lockHints();
    }

    /**
     * Khóa các ô còn sót lại (gợi ý) để ngăn chúng được chỉnh sửa bởi
     * người dùng.
     */
    private void lockHints() {
        for (Cell cell : getGrid()) {
            if (cell.isEmpty()) {
                cell.setLocked(false);
            } else {
                cell.setLocked(true);
            }
        }
    }

    /**
     * @return số ô trống dự định
     */
    public int getNumEmpty() {
        return numEmpty;
    }

    /**
     * @param numEmpty số ô trống dự định
     */
    private void setNumEmpty(int numEmpty) {
        this.numEmpty = numEmpty;
    }

    /**
     * @return lưới 
     */
    public Grid getGrid() {
        return newGrid;
    }

    /**
     * @param newGrid lưới mới để thiết lập
     */
    private void setGrid(Grid newGrid) {
        this.newGrid = newGrid;
    }
}
