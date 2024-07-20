package dto.type;

import java.awt.*;

public class CircleCategoryDTO implements Comparable<CircleCategoryDTO> {

    private String category;
    private Long value = 0L;
    private Color color;

    public CircleCategoryDTO(String category, Long value, Color color) {
        this.category = category;
        this.value = value;
        this.color = color;
    }

    public CircleCategoryDTO() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public int compareTo(CircleCategoryDTO o) {
        // Сравняване по категория
        int categoryComparison = this.category.compareTo(o.getCategory());
        if (categoryComparison != 0) {
            return categoryComparison;
        }

        // Сравняване по цвят
        int redComparison = Integer.compare(this.color.getRed(), o.getColor().getRed());
        if (redComparison != 0) {
            return redComparison;
        }

        int greenComparison = Integer.compare(this.color.getGreen(), o.getColor().getGreen());
        if (greenComparison != 0) {
            return greenComparison;
        }

        int blueComparison = Integer.compare(this.color.getBlue(), o.getColor().getBlue());
        if (blueComparison != 0) {
            return blueComparison;
        }

        // Ако категорията и цветът са еднакви, връщаме 0 за равенство
        return 0;
    }

}
