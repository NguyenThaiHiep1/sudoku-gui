package gui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Thông tin về lưới Sudoku
 */
public class Grid implements Iterable<Cell> {

    // Thuộc tính Lưới
    private final int SIZE = 9;
    private final Cell[][] cells;
    private final List<Cell> cellList;
    private final List<List<Cell>> subgrids;
    private final Difficulty difficulty;

    /**
     * Xây dựng đối tượng Lưới
     *
     * @param diff mức độ khó khăn của lưới
     */
    public Grid(Difficulty diff) {
        this.cells = new Cell[this.SIZE][this.SIZE];
        this.cellList = new ArrayList<>(this.SIZE * this.SIZE);
        this.subgrids = generateSubgrids();
        this.difficulty = diff;
        initialiseGrid();
    }

    /**
     * Thiết lập lưới Sudoku
     */
    private void initialiseGrid() {
        for (int row = 0; row < this.SIZE; row++) {
            for (int column = 0; column < this.SIZE; column++) {
                Cell cell = new Cell(row, column);
                this.cells[row][column] = cell;
                this.cellList.add(cell);
                this.subgrids.get(cell.getPosition().getSubgrid()).add(cell);
            }
        }
    }

    /**
     * Tạo danh sách danh sách ô cho hình thành subgrid
     *
     * @return lưới Danh sách chứa danh sách ô
     */
    private List<List<Cell>> generateSubgrids() {
        List<List<Cell>> gridList = new ArrayList<>(this.SIZE);
        for (int i = 0; i < this.SIZE; i++) {
            gridList.add(new ArrayList<>());
        }
        return gridList;
    }

    /**
     * @return danh sách tất cả các ô trong lưới này
     */
    public List<Cell> getCellList() {
        return this.cellList;
    }

    /**
     * @return danh sách các ô thuộc mỗi subgrid
     */
    public List<List<Cell>> getSubgrids() {
        return this.subgrids;
    }

    /**
     * @return độ khó
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * Ngay lập tức tiết lộ giải pháp
     *
     * @param entireGrid Cho dù mọi tế bào trong lưới có nên
     * tiết lộ
     */
    public void hint(boolean entireGrid) {
        ArrayList<Cell> emptyCells = new ArrayList();

        for (Cell cell : cellList) {
            if (cell.isEmpty()) {
                emptyCells.add(cell);
            }
        }

        Collections.shuffle(emptyCells);

        for (Cell cell : emptyCells) {
            if (entireGrid) {
                cell.userValue = cell.getSolutionValue();
                cell.setLocked(true);
            } else if (!entireGrid && cell.isEmpty()) {
                cell.setUserValue(cell.getSolutionValue());
                cell.setLocked(true);
                return;
            }
        }
    }

    /**
     * Kiểm tra xem lưới hiện tại có được giải quyết hay không (bởi người dùng)
     *
     * @return đúng nếu được giải quyết, những người khác sai
     */
    public boolean isSolved() {
        for (Cell cell : this) {
            if (cell.userValue != cell.getSolutionValue()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Phương pháp lưu trữ cuộc gọiProvisionalValue() trên mọi Ô trong lưới.
     */
    public void provisionCells() {
        for (Cell cell : this) {
            cell.storeProvisionalValue();
        }
    }

    /**
     * gọi fetchProvisionalValue() phương pháp trên mọi Ô trong lưới.
     */
    public void fetchCellProvision() {
        for (Cell cell : this) {
            cell.fetchProvisionalValue();
        }
    }

    /**
     * Đánh giá xem lưới điện có được lấp đầy hay không
     *
     * @return đúng nếu được lấp đầy, khác sai
     */
    public boolean isFilled() {
        for (int i = 0; i < this.cellList.size(); i++) {
            if (this.cellList.get(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Kiểm tra xem giá trị của người dùng có đáp ứng các ràng buộc của quy tắc vị trí hay không
     *
     * @param cell ô để đặt giá trị
     * @param value giá trị để đi vào ô đó
     * @return đúng nếu tất cả các ràng buộc đã được đáp ứng, những người khác sai
     */
    public boolean meetsConstraints(Cell cell, int value) {
        return checkRow(cell.getPosition().getRow(), value)
                && checkColumn(cell.getPosition().getColumn(), value)
                && checkSubgrid(cell, value);
    }

    /**
     * Kiểm tra xem giá trị chỉ xảy ra một lần trong toàn bộ hàng
     *
     * @param row hàng cần kiểm tra
     * @param value giá trị cần kiểm tra
     * @return đúng nếu giá trị chỉ xảy ra một lần, nếu không sai
     */
    private boolean checkRow(int row, int value) {
        for (Cell cell : cells[row]) {
            if (value == cell.getUserValue()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Kiểm tra xem giá trị chỉ xảy ra một lần trong toàn bộ cột
     *
     * @param column cột cần kiểm tra
     * @param value giá trị cần kiểm tra
     * @return đúng nếu giá trị chỉ xảy ra một lần, nếu không sai
     */
    private boolean checkColumn(int column, int value) {
        for (Cell[] columnCells : cells) {
            if (value == columnCells[column].getUserValue()) {
                //System.err.println("CELL col: " + columnCells[column].getPosition() + " conflicts.");
                return false;
            }
        }
        return true;
    }

    /**
     * Kiểm tra xem giá trị chỉ xảy ra một lần trong lưới con đã chọn
     *
     * @param currentCell ô cần kiểm tra
     * @param value the giá trị để kiểm tra
     * @return đúng nếu giá trị chỉ xảy ra một lần, nếu không sai
     */
    private boolean checkSubgrid(Cell currentCell, int value) {
        for (Cell cell : subgrids.get(currentCell.getPosition().getSubgrid())) {
            if (value == cell.getUserValue()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Lấy ô ở vị trí lưới (1A-9I)
     *
     * @param row hàng chứa ô
     * @param column cột chứa ô
     * @return ô ở vị trí được chỉ định
     */
    public Cell getCell(int row, char column) {
        return this.cells[row - 1][(Character.toUpperCase(column) - 65)];
    }

    /**
     * Lấy ô ở vị trí tọa độ số nguyên
     *
     * @param xPos vị trí x (1-9)
     * @param yPos vị trí y (1-9)
     * @return ô ở vị trí được chỉ định
     */
    public Cell getCell(int xPos, int yPos) {
        return this.cells[xPos - 1][yPos - 1];
    }

    /**
     * @return Hiển thị biểu diễn trực quan của lưới Sudoku trong
     * bảng điều khiển.
     */
    @Override
    public String toString() {
        // Chuỗi đầu ra để thêm vào
        String result = "\n";
        result += printColumnChars();

        // Viền trên cùng
        result += ("\n   |---------|---------|---------|\n");

        // Đối với mỗi hàng ô
        for (int i = 0; i < 9; i++) {
            // Append row numbers with correct padding
            if (i == 3 || i == 6) {
                result += ("   |---------|---------|---------|\n");
            }
            result += " " + (i + 1) + " |";

            // Thêm một đại diện của các ô
            for (int j = 0; j < 9; j++) {
                result += (cells[i][j]);
                if (j == 2 || j == 5) {
                    result += ("|");
                }
            }
            result += ("| " + (i + 1) + "\n");
        }

        // Viền Dưới cùng
        result += ("   |---------|---------|---------|\n");
        result += printColumnChars();
        return result;
    }

    /**
     * Tạo chữ cột với đệm chính xác
     *
     * @return một hàng tiêu đề cột tách biệt không gian (A-I)
     */
    private String printColumnChars() {
        String output = "";
        // Thêm chữ cái cột với đệm chính xác
        for (int i = 1; i <= this.SIZE; i++) {
            if (i == 1) {
                output += "     " + (char) (i + 64);
            } else {
                if (i == 4 || i == 7) {
                    output += "   " + (char) (i + 64);
                } else {
                    output += "  " + (char) (i + 64);
                }
            }
        }
        return output;
    }

    /**
     * @return a unique cell iterator
     */
    @Override
    public ListIterator<Cell> iterator() {
        return shuffleCells().listIterator();
    }

    /**
     * Xáo trộn danh sách các ô trong lưới.
     *
     * @return danh sách các ô bị xáo trộn
     */
    public ArrayList<Cell> shuffleCells() {
        ArrayList<Cell> shuffledCells = new ArrayList<>(cellList);
        Collections.shuffle(shuffledCells);
        return shuffledCells;
    }
}
