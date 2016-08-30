package emailverwaltung;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
    /**
	 * Creates a default TableModel for JFrameEmailverwaltung
	 */
	private static final long serialVersionUID = 1L;
	private String[] columnNames = EmailKontaktDao.getColumnNames();
	private Object[][] data = null;
	boolean editable = true;
	
	public TableModel(Object[][] data) {
		this.data = data;
		
	}
    
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
    	Object obj = null;
		try {
			obj = data[row][col];
			
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
		
        return obj;
    }

    public void setValueAt(Object value, int row, int col) {
    	data[row][col] = value;
    	fireTableCellUpdated(row, col);
    }
    
    public void setData(Object[][] data) {
    	this.data = data;
    	fireTableDataChanged();
    	
    }
    
    public void fireUpdate() {
    	data = EmailKontaktDao.getTableData();
    	fireTableDataChanged();
    	
    }
    
    public void setEditable(boolean editable) {
    	this.editable = editable;
    }
    
    public boolean isCellEditable(int row, int col) {
    	if(!editable) {
    		return false;
    	}
    	
        if (col == 0) {
            return false;
            
        } else {
            return true;
            
        }
    }
}