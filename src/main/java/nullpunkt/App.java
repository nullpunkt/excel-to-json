package nullpunkt;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Hello world!
 *
 */
public class App {
	
	private static final DateFormat formatDate = new SimpleDateFormat("dd.MM.yy");
//	private static final DateFormat formatDateTime = new SimpleDateFormat("dd.MM.yy hh:mm:ss");
	
	public static void main( String[] args ) throws Exception
    {
        
    	if(args.length!=1) {
    		System.out.println("Usage: java -jar excel-to-csv.jar [source]");
    		return;
    	}
    	
        InputStream inp = new FileInputStream(args[0]);
        Workbook wb = WorkbookFactory.create(inp);
        
        int max = 0;
        for(int i=0; i<wb.getNumberOfSheets(); i++) {
        	Sheet sheet = wb.getSheetAt(0);
        	if(sheet==null) {
        		continue;
        	}
        	for(int j=sheet.getFirstRowNum(); j<=sheet.getLastRowNum(); j++) {
        		Row row = sheet.getRow(j);
        		if(row==null) {
        			continue;
        		}
        		for(int k=row.getFirstCellNum(); k<row.getLastCellNum(); k++) {
        			Cell cell = row.getCell(k);
        			if(cell==null) {
        				continue;
        			}
        			if(max<k)max = k;
        		}
        	}
        }
        
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<wb.getNumberOfSheets(); i++) {
        	Sheet sheet = wb.getSheetAt(i);
        	if(sheet==null) {
        		continue;
        	}
        	for(int j=sheet.getFirstRowNum(); j<=sheet.getLastRowNum(); j++) {
        		Row row = sheet.getRow(j);
        		if(row==null) {
        			continue;
        		}
        		for(int k=0; k<=max; k++) {
        			Cell cell = row.getCell(k);
        			if(cell!=null) {
        				sb.append(cellToString(cell).replace("\n", "").replace("\r", ""));
        			}
        			if(k!=max) {
        				sb.append(";");
        			}        			
        		}
        		if(sb.length()>0)sb.append("\n");
        	}
        }
        
        inp.close();
        System.out.print(sb.toString());
    }

	private static String cellToString(Cell cell) {

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue() ? "true" : "false";
		case Cell.CELL_TYPE_FORMULA:
	        switch(cell.getCachedFormulaResultType()) {
	            case Cell.CELL_TYPE_NUMERIC:
	    			if(HSSFDateUtil.isCellDateFormatted(cell)) {
	    				return formatDate.format(cell.getDateCellValue());
	    			}
	    			return Double.valueOf(cell.getNumericCellValue()).toString();
	            case Cell.CELL_TYPE_STRING:
	    			return cell.getRichStringCellValue().toString();
	        }
			
		case Cell.CELL_TYPE_NUMERIC:
			if(HSSFDateUtil.isCellDateFormatted(cell)) {
				return formatDate.format(cell.getDateCellValue());
			}
			return Double.valueOf(cell.getNumericCellValue()).toString();
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		
		default:
			return "";
		}

	}
}
