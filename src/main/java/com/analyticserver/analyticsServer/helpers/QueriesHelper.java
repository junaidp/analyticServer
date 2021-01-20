package com.analyticserver.analyticsServer.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class QueriesHelper {
	Gson gson = new Gson();
	
	
	public String readExcel() {
		
		String json = "";
		OPCPackage pkg;
		try {
		InputStream file = getExcelFile();
		pkg = OPCPackage.open(file);

		XSSFWorkbook wb = new XSSFWorkbook(pkg);
		//This reads the worksheet in the excel file , for example 0
		Sheet sheetLease = wb.getSheetAt(0);

		LinkedHashMap<String, LinkedHashMap<String, String>> map = calculateLease(wb);
		System.out.println("calculation done ");
		 json =  gson.toJson(map);
		System.out.println("converted to json");
		wb.close();
		System.out.println("returning json");
		}catch(Exception ex) {
			System.out.println(ex);
		}
		return json;
		
		
	}
	
	private InputStream getExcelFile() throws Exception {
		// PUT THE EXCEL FILE IN static FOLDER AND CHANGE THE NAME here instead of exampleFile.xls
		String fileName = "static/exampleFile.xlsx";
		ClassLoader classLoader =  this.getClass().getClassLoader();
		InputStream file = classLoader.getResourceAsStream(fileName);
		return file;
	}
	
	
	public LinkedHashMap<String, LinkedHashMap<String, String>> calculateLease(XSSFWorkbook wb) throws InvalidFormatException, IOException {


		System.out.println("Going to read excel file");
		 //FormulaEvaluator can be used if we need to use some formulas in excel sheet
		//FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		//XSSFSheet sheet = wb.getSheet("Name of first tab of excel file");//
		Sheet sheet = wb.getSheetAt(0);

		LinkedHashMap<String, LinkedHashMap<String, String>> mapSheet = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		System.out.println("In sheet" +sheet.getSheetName());
		
		int count = 0;

		for (Row r : sheet) {
			///ONLY PUT COLUMN No in map id
			int row = r.getRowNum();

			if(r.getRowNum()>= 0 )
			{

				LinkedHashMap<String, String> mapRow = new LinkedHashMap<String, String>();

				System.out.println("In Row" +r.getRowNum());
				for (Cell c : r) {
					CellType cellType = null;
					if (c.getCellType() == CellType.FORMULA) {
					
					}
					else
					{
						cellType = c.getCellType();
					}
					putinMap(mapRow, c, cellType);
				}
				mapSheet.put(row+1+"", mapRow);
				count ++;
			}

		}


		System.out.println("returning Lease map");
		return mapSheet;
	}
	
	
	protected void putinMap(LinkedHashMap<String, String> mapSheet,
			Cell c, CellType cellType) {


		String cellLocation =  c.getColumnIndex()+"";

		switch(cellType) {


		case NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(c)) {
				mapSheet.put(cellLocation, c.getDateCellValue()+"");
			}
			else {
				c.getRow().getRowNum();
				mapSheet.put(cellLocation, c.getNumericCellValue()+"");
			}
			break;
		case STRING:
			mapSheet.put(cellLocation, c.getRichStringCellValue()+"");
			break;
		case BOOLEAN:
			mapSheet.put(cellLocation, c.getBooleanCellValue()+"");
		default:
			mapSheet.put(cellLocation, "-"+"");
			break;
		}
	}


}
