package emailverwaltung;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
    /**
	 * Creates a default TableModel for JFrameEmailverwaltung
	 */
	private static final long serialVersionUID = 1L;
	private String[] columnNames = EmailKontaktDao.getColumnNames();
    private Object[][] data = EmailKontaktDao.getTableData();
    
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public void setValueAt(Object value, int row, int col) {
    	data[row][col] = value;
    	fireTableCellUpdated(row, col);
    }
    
    public void addRow(Object[] data) {
    	addRow(data);
    	fireTableDataChanged();
    	
    }
    
    public void fireUpdate() {
    	data = EmailKontaktDao.getTableData();
    	fireTableDataChanged();
    	
    }
    
    public boolean isCellEditable(int row, int col) {
        if (col == 0) {
            return false;
            
        } else {
            return true;
            
        }
    }
}