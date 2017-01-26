package net.nullpunkt.exceljson.convert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import net.nullpunkt.exceljson.pojo.ExcelWorkbook;
import net.nullpunkt.exceljson.pojo.ExcelWorksheet;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

//"dd.MM.yy"
public class ExcelToJsonConverter {
	
	private ExcelToJsonConverterConfig config = null;
	
	public ExcelToJsonConverter(ExcelToJsonConverterConfig config) {
		this.config = config;
	}
	
	public static ExcelWorkbook convert(ExcelToJsonConverterConfig config) throws InvalidFormatException, IOException {
		return new ExcelToJsonConverter(config).convert();
	}
	
	public ExcelWorkbook convert()
			throws InvalidFormatException, IOException {
		ExcelWorkbook book = new ExcelWorkbook();

		InputStream inp = new FileInputStream(config.getSourceFile());
		Workbook wb = WorkbookFactory.create(inp);

		book.setFileName(config.getSourceFile());
		int loopLimit =  wb.getNumberOfSheets();
		if (config.getNumberOfSheets() > 0 && loopLimit > config.getNumberOfSheets()) {
			loopLimit = config.getNumberOfSheets();
		}
		int rowLimit 			= config.getRowLimit();
		int startRowOffset 		= config.getRowOffset();
		int currentRowOffset 	= -1;
		int totalRowsAdded 		= 0;


		for (int i = 0; i < loopLimit; i++) {
			Sheet sheet = wb.getSheetAt(i);
			if (sheet == null) {
				continue;
			}
			ExcelWorksheet tmp = new ExcelWorksheet();
			tmp.setName(sheet.getSheetName());
        	for(int j=sheet.getFirstRowNum(); j<=sheet.getLastRowNum(); j++) {
	    		Row row = sheet.getRow(j);
	    		if(row==null) {
	    			continue;
	    		}
	    		boolean hasValues = false;
	    		ArrayList<Object> rowData = new ArrayList<Object>();
	    		for(int k=0; k<=row.getLastCellNum(); k++) {
	    			Cell cell = row.getCell(k);
	    			if(cell!=null) {
	    				Object value = cellToObject(cell);
	    				hasValues = hasValues || value!=null;
	    				rowData.add(value);
	    			} else {
                        rowData.add(null);
                    }
	    		}
	    		if(hasValues||!config.isOmitEmpty()) {
					currentRowOffset++;
	    			if (rowLimit > 0 && totalRowsAdded == rowLimit) {
	    				break;
					}
					if (startRowOffset > 0 && currentRowOffset < startRowOffset) {
	    				continue;
					}
	    			tmp.addRow(rowData);
	    			totalRowsAdded++;
	    		}
	    	}
        	if(config.isFillColumns()) {
        		tmp.fillColumns();
        	}
			book.addExcelWorksheet(tmp);
		}

		return book;
	}
	
	private Object cellToObject(Cell cell) {

		int type = cell.getCellType();
		
		if(type == Cell.CELL_TYPE_STRING) {
			return cleanString(cell.getStringCellValue());
		}
		
		if(type == Cell.CELL_TYPE_BOOLEAN) {
			return cell.getBooleanCellValue();
		}
		
		if(type == Cell.CELL_TYPE_NUMERIC) {
			
			if (cell.getCellStyle().getDataFormatString().contains("%")) {
		        return cell.getNumericCellValue() * 100;
		    }
			
			return numeric(cell);
		}
		
		if(type == Cell.CELL_TYPE_FORMULA) {
	        switch(cell.getCachedFormulaResultType()) {
	            case Cell.CELL_TYPE_NUMERIC:
	    			return numeric(cell);
	            case Cell.CELL_TYPE_STRING:
	    			return cleanString(cell.getRichStringCellValue().toString());
	        }
		}
		
		return null;

	}
	
	private String cleanString(String str) {
		return str.replace("\n", "").replace("\r", "");
	}

	private Object numeric(Cell cell) {
		if(HSSFDateUtil.isCellDateFormatted(cell)) {
			if(config.getFormatDate()!=null) {
				return config.getFormatDate().format(cell.getDateCellValue());	
			}
			return cell.getDateCellValue();
		}
		return Double.valueOf(cell.getNumericCellValue());
	}
}
