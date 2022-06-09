package gui.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Tìm một giải pháp duy nhất cho lưới điện.
*/
public class Solution {

    // Thuộc tính Giải pháp
    private Grid grid;
    private int result;
    private int loop;

    /**
     * Xây dựng một lưới điện thô để giải quyết.
     *
     * @param grid lưới Sudoku để giải quyết cho
     * @return the lưới nguyên thủy
     */
    public Solution solveFor(Grid grid) {
        this.grid = grid;
        this.result = 0;
        this.setLoop(0);
        return this;
    }

    /**
     * Tìm một giải pháp duy nhất cho lưới này bằng cách sử dụng đệ quy để xác nhận từng
     * giá trị.
     *
     * @param emptyCells lưới trống
     * @param numEmpty số lượng ô trống tuyệt đối
     * @return giải pháp thực sự
     */
    public int findSolution(List<Cell> emptyCells, int numEmpty) {
        if (getLoop() < emptyCells.size()) {
            for (int digit : shuffleValues()) {
                if (grid.meetsConstraints(emptyCells.get(getLoop()), digit)) {
                    emptyCells.get(getLoop()).setUserValue(digit);
                    setLoop(getLoop() + 1);
                    if (findSolution(emptyCells, numEmpty) >= numEmpty) {
                        return result;
                    }
                }
            }
            emptyCells.get(getLoop()).setUserValue(0);
            setLoop(getLoop() - 1);
            return result;
        } else {
            setLoop(getLoop() - 1);
            return ++result;
        }
    }

    /**
     * Xáo trộn danh sách các giá trị có thể.
     *
     * @return một danh sách xáo trộn các giá trị có thể (1-9)
     */
    public List<Integer> shuffleValues() {
        List<Integer> possibleValues = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(possibleValues);
        return possibleValues;
    }

    /**
     * @return bộ đếm vòng lặp
     */
    private int getLoop() {
        return loop;
    }

    /**
     * @param loop vòng lặp để đặt thành
     */
    private void setLoop(int loop) {
        this.loop = loop;
    }
}
