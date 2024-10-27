package kr.ccfc.util;

/**
 * table转excel
 * 跨行元素元数据
 */
public class CrossRangeCellMeta {

    public CrossRangeCellMeta(int firstRowIndex, int firstColIndex, int rowSpan, int colSpan) {
        super();
        this.firstRowIndex = firstRowIndex;
        this.firstColIndex = firstColIndex;
        this.rowSpan = rowSpan;
        this.colSpan = colSpan;
    }

    private int firstRowIndex;
    private int firstColIndex;
    private int rowSpan;// 跨越行数
    private int colSpan;// 跨越列数

    public int getFirstRow() {
        return firstRowIndex;
    }

    public int getLastRow() {
        return firstRowIndex + rowSpan - 1;
    }

    public int getFirstCol() {
        return firstColIndex;
    }

    public int getLastCol() {
        return firstColIndex + colSpan - 1;
    }

    public int getColSpan() {
        return colSpan;
    }

    public int getFirstRowIndex() {
        return firstRowIndex;
    }

    public void setFirstRowIndex(int firstRowIndex) {
        this.firstRowIndex = firstRowIndex;
    }

    public int getFirstColIndex() {
        return firstColIndex;
    }

    public void setFirstColIndex(int firstColIndex) {
        this.firstColIndex = firstColIndex;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }
}
