package gui.model;

import java.util.Random;

/**
 * Thông tin về chỉnh độ khó
 */
public enum Difficulty {

    // Trung bình với ± 2 (phương sai), phải nhỏ hơn 64 để giải pháp là duy nhất.
    // LƯU Ý: Số lượng ô trống càng cao, càng mất nhiều thời gian để tạo ra một giải pháp duy nhất.
    BEGINNER(20, 3),
    INTERMEDIATE(35, 5),
    ADVANCED(43, 8);

    // Thuộc tính Độ khó
    private final int variance = 2;
    private final int maxHints;
    private final int emptyCells;

    /**
     * Tạo ra một số giả ngẫu nhiên của các ô trống.
     *
     * @param averageBlanks số lần trống trung bình ± 2 (phương sai)
     * @param maxHints số lượng gợi ý có sẵn cho mức độ khó khăn
     */
    Difficulty(int averageBlanks, int maxHints) {
        this.emptyCells = new Random().nextInt(((averageBlanks + variance) - (averageBlanks - variance)) + 1) + (averageBlanks - variance);
        this.maxHints = maxHints;
    }

    /**
     * @return số lượng gợi ý tối đa được phép
     */
    public int getMaxHints() {
        return maxHints;
    }

    /**
     * @return số ô trống
     */
    public int numEmptyCells() {
        return emptyCells;
    }

    /**
     * @return Đại diện kiểu được liệt kê theo trường hợp tiêu đề, thân thiện với người dùng
     */
    @Override
    public String toString() {
        return new StringBuffer(this.name().length())
                .append(Character.toTitleCase(this.name().charAt(0)))
                .append(this.name().toLowerCase().substring(1)).toString();
    }
}
