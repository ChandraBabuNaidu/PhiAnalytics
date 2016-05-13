package ramana;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BGBCLogin {
	public static void main(String[] args) throws IOException {
		FirefoxDriver driver;
		driver=new FirefoxDriver();
		driver.manage().window().maximize();
		driver.get("http://23.253.83.87:8090/Home/Login");
		String result=null;
		File MyFile=new File("D:\\BGBC.xlsx");
		FileInputStream Ifile=new FileInputStream(MyFile);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook=new XSSFWorkbook(Ifile);
		Sheet sheet=workbook.getSheet("Sheet1");
		int rowCount=sheet.getLastRowNum();
		System.out.println(rowCount);
		for(int i=1;i<=rowCount;i++)
		{
			Row row=sheet.getRow(i);
			String UserName=(String)row.getCell(0).getStringCellValue();
			//System.out.println(row.getCell(1).getCellType());
			String password1=""+(int)(row.getCell(1).getNumericCellValue());
			driver.findElement(By.id("Email")).sendKeys(UserName);
			driver.findElement(By.id("Password")).sendKeys((password1));
			driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/form/input[2]")).click();
			driver.findElement(By.id("Email")).clear();
			driver.findElement(By.id("Password")).clear();
			if("BGBC Login".equals(driver.getTitle()))
			{
				result="Login is successfull";
				driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div[1]/nav/ul/li[5]/a")).click();
			}
			else
			{
				result="Login is unsuccessfull";
			}
			Cell cell=row.createCell(2);
			cell.setCellValue(result);
		}
		Ifile.close();
		FileOutputStream Ofile=new FileOutputStream(new File("D:\\BGBC.xlsx"));
					workbook.write(Ofile);
					Ofile.close();
					driver.close();
	}

}
