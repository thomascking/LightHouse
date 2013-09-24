package org.thomas.lighthouse.editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class XLSEditor extends JTabbedPane implements FileEditor, KeyListener {
	private static final long serialVersionUID = -151802936055292075L;
	
	File file;
	
	JTable[] tables;
	
	public XLSEditor() {
		this.setTabPlacement(JTabbedPane.BOTTOM);
		addKeyListener(this);
	}

	@Override
	public File getFile() {
		return file;
	}

	@Override
	public void setup(File f) {
		file = f;
		try {
			FileInputStream fis = new FileInputStream(f);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			int sheets = workbook.getNumberOfSheets();
			tables = new JTable[sheets];
			for (int i = 0; i < sheets; i++) {
				HSSFSheet sheet = workbook.getSheetAt(i);
				
				int rows = sheet.getPhysicalNumberOfRows();
				int cols = 0;
				for (int r = 0; r < rows; r++) {
					HSSFRow row = sheet.getRow(r);
					if (row == null) continue;
					
					int tmp = row.getPhysicalNumberOfCells();
					if (tmp > cols) cols = tmp;
				}
				
				String[][] data = new String[rows > 50 ? rows : 50][cols > 10 ? cols : 10];
				String[] names = new String[cols > 10 ? cols : 10];
				for (int r = 0; r < rows; r++) {
					HSSFRow row = sheet.getRow(r);
					if (row == null) continue;
					
					for (int c = 0; c < cols; c++) {
						HSSFCell cell = row.getCell(c);
						if (cell == null) continue;
						switch (cell.getCellType()){
							case HSSFCell.CELL_TYPE_NUMERIC:
								data[r][c] = "" + cell.getNumericCellValue();
								break;
							case HSSFCell.CELL_TYPE_STRING:
								data[r][c] = cell.getStringCellValue();
								break;
							default:
								data[r][c] = "";
						}
					}
				}
				for (int j = 0; j < names.length; j++) {
					String val = "";
					int k = j;
					while (k > 0) {
						char c = (char)('A' + j % 26);
						val = c + val;
						k /= 26;
					}
					names[j] = val;
				}
				if (names.length > 0)
					names[0] = "A";
				tables[i] = new JTable(data, names);
				tables[i].setName(sheet.getSheetName());
			}
			for (JTable t : tables) {
				addTab(t.getName(), new JScrollPane(t));
				t.addKeyListener(this);
			}
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}

	public void write(File f) {
		int sheets = this.getTabCount();
		HSSFWorkbook workbook = new HSSFWorkbook();
		for (int i = 0; i < sheets; i++) {
			HSSFSheet sheet = workbook.createSheet(tables[i].getName());
			for (int r = 0; r < tables[i].getRowCount(); r++) {
				HSSFRow row = sheet.createRow(r);
				for (int c = 0; c < tables[i].getColumnCount(); c++) {
					HSSFCell cell = row.createCell(c);
					Object val = tables[i].getValueAt(r, c);
					if (val instanceof String) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue((String)val);
					}
					else if (val instanceof Number) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellValue(((Number)val).intValue());
					}
					else {
						cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
					}
				}
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(f);
			workbook.write(fos);
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_S && e.isControlDown()) {
			write(file);
			e.consume();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
}
