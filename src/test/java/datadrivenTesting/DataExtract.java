package datadrivenTesting;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class DataExtract {

	public Object[][] getdatafromexcel() throws EncryptedDocumentException, IOException {
		FileInputStream fis = new FileInputStream("C:\\sts\\Redbus\\src\\main\\resources\\RedBusData.xlsx");
		Workbook book = WorkbookFactory.create(fis);
		Sheet sh = book.getSheet("Sheet1");

		int rowcount = sh.getLastRowNum();
		short cellsize = sh.getRow(0).getLastCellNum();

		Object[][] obj = new Object[rowcount][cellsize];

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		for (int i = 1; i <= rowcount; i++) {
			for (int j = 0; j < cellsize; j++) {
				if (sh.getRow(i).getCell(j) != null) {
					CellType cellType = sh.getRow(i).getCell(j).getCellType();
					if (cellType == CellType.NUMERIC) {
						if (DateUtil.isCellDateFormatted(sh.getRow(i).getCell(j))) {
							java.util.Date dateValue = sh.getRow(i).getCell(j).getDateCellValue();
							LocalDate date = dateValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							obj[i - 1][j] = date.format(dateFormatter);
						} else {
							obj[i - 1][j] = String.valueOf(sh.getRow(i).getCell(j).getNumericCellValue());
						}
					} else {
						obj[i - 1][j] = sh.getRow(i).getCell(j).getStringCellValue();
					}
				} else {
					continue;
				}
			}
		}

		return obj;
	}
}