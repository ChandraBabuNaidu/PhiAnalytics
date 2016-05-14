package proj.Base;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
























import org.testng.Reporter;

import proj.Features.TestUtil;
import proj.Xlsx.Xls_Reader;


public class CommonMethods extends WebPage{
	
	/**
	 * Used for Reporting Pass/Fail of the Executed Test Case 
	 * in Suite A Excel Sheet.
	 * 
	 * 
	 * @param InvokedBrowser
	 * @param methodname
	 * @param isTestPass
	 */
	public void reportTestResult(Xls_Reader ExcelObjectName, String InvokedBrowser,String methodname ,boolean isTestPass)
	{
		if(InvokedBrowser.contentEquals("Firefox"))
		{
		if(isTestPass==true)
		{
			TestUtil.reportDataSetResultFirefox(ExcelObjectName, "Test Cases", TestUtil.getRowNum(ExcelObjectName, methodname), "PASS");
			Setup.APP_LOGS.info(methodname+" Passed");
		}
				
		else
		{
			TestUtil.reportDataSetResultFirefox(ExcelObjectName, "Test Cases", TestUtil.getRowNum(ExcelObjectName, methodname), "FAIL");
			Setup.APP_LOGS.info(methodname+" Failed");
		}
		
		}
		
		else if (InvokedBrowser.contentEquals("Chrome"))
		{
			if(isTestPass==true){
				TestUtil.reportDataSetResultChrome(ExcelObjectName, "Test Cases", TestUtil.getRowNum(ExcelObjectName, methodname), "PASS");
				Setup.APP_LOGS.info(methodname+" Passed");
			}
					
			else
			{
				TestUtil.reportDataSetResultChrome(ExcelObjectName, "Test Cases", TestUtil.getRowNum(ExcelObjectName, methodname), "FAIL");
				Setup.APP_LOGS.info(methodname+" Failed");
			}
		}
		
		else if (InvokedBrowser.contentEquals("IE"))
		{
			if(isTestPass==true){
				TestUtil.reportDataSetResultIE(ExcelObjectName, "Test Cases", TestUtil.getRowNum(ExcelObjectName, methodname), "PASS");
				Setup.APP_LOGS.info(methodname+" Passed");
			}
					
			else
			{
				TestUtil.reportDataSetResultIE(ExcelObjectName, "Test Cases", TestUtil.getRowNum(ExcelObjectName, methodname), "FAIL");
				Setup.APP_LOGS.info(methodname+" Failed");
			}
		}
		
		else if (InvokedBrowser.contentEquals("Safari"))
		{
			if(isTestPass==true){
				TestUtil.reportDataSetResultSafari(ExcelObjectName, "Test Cases", TestUtil.getRowNum(ExcelObjectName, methodname), "PASS");
				Setup.APP_LOGS.info(methodname+" Passed");
			}
					
			else
			{
				TestUtil.reportDataSetResultSafari(ExcelObjectName, "Test Cases", TestUtil.getRowNum(ExcelObjectName, methodname), "FAIL");
				Setup.APP_LOGS.info(methodname+" Failed");
			}
		}
		
	}
	
	
	/**
	 * Used to Created the Data wise folder in ScreenShot Package and
	 * take screen shorts with user selected name
	 * along with date-time time stamp as image name.  
	 * 
	 * @param name -- Name of the Screen shot to be saved excluding the date- time format.
	 * @throws Exception
	 */
	
	@SuppressWarnings("unused")
	public void takeScreenShots(String name) throws Exception
	{
		boolean makeDir = false;
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy") ;
        String curDate =dateFormat.format(date);
        File Path = new File(System.getProperty("user.dir")+ "//screenshots//"+curDate);
        makeDir = Path.mkdir();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy,HH-mm");
		String formattedDate = sdf.format(date);
		formattedDate.toString();
		// edited for the taking screen shot in remote webdriver
		//Augmenter augmenter = new Augmenter(); 
		File scr = ((TakesScreenshot)getWD()).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scr, new File(Path+"//"+ name +"-"+ formattedDate +".jpeg"));
	}
	
	
	public static void xScreenShot() throws InterruptedException {
		try {

			String NewFileNamePath;

			java.awt.Dimension resolution = Toolkit.getDefaultToolkit()
					.getScreenSize();
			Rectangle rectangle = new Rectangle(resolution);

			// Get the dir path
			File directory = new File(".");
			// System.out.println(directory.getCanonicalPath());

			// get current date time with Date() to create unique file name
			DateFormat dateFormat = new SimpleDateFormat(
					"MMM_dd_yyyy__hh_mm_ssaa");
			// get current date time with Date()
			Date date = new Date();
			// System.out.println(dateFormat.format(date));

			// To identify the system
			InetAddress ownIP = InetAddress.getLocalHost();
			// System.out.println("IP of my system is := "+ownIP.getHostAddress());

			NewFileNamePath = directory.getCanonicalPath() + "\\ScreenShots\\"
					+ Thread.currentThread().getStackTrace()[2].getMethodName() + "___" + dateFormat.format(date) + "_"
					+ ownIP.getHostAddress() + ".png";
			System.out.println(NewFileNamePath);

			// Capture the screen shot of the area of the screen defined by the
			// rectangle
			Robot robot = new Robot();
			BufferedImage bi = robot.createScreenCapture(new Rectangle(
					rectangle));
			ImageIO.write(bi, "png", new File(NewFileNamePath));
			NewFileNamePath = "<a"+" href=" + NewFileNamePath + ">ScreenShot"
					+ "</a>";
			// Place the reference in TestNG web report
			Reporter.log(NewFileNamePath);
			Reporter.log( "<a href="+"http://www.w3schools.com/html/"+">link text" +"</a>" );
			

		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void takeAlertScreenShot(String name) throws Exception
	{
		boolean makeDir = false;
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy") ;
        String curDate =dateFormat.format(date);
        File Path = new File(System.getProperty("user.dir")+ "//screenshots//"+curDate);
        makeDir = Path.mkdir();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy,HH-mm");
		String formattedDate = sdf.format(date);
		formattedDate.toString();
		// edited for the taking screen shot in remote webdriver
		//Augmenter augmenter = new Augmenter(); 
		BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		 ImageIO.write(image, "jpeg", new File(Path+"//"+ name +"-"+ formattedDate +".jpeg"));	
	}

	
	/**
	 * to get the present date in "MM-dd-yyyy" format.
	 * @return
	 */
	
	public String currentDate()
	{
		String curDate=null;
		Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy") ;
        curDate =dateFormat.format(date);
        return curDate;
	}
	
	/**
	 * to get the present date in "MM-dd-yyyy" format.
	 * @return
	 */
	
	public String currentBillDate()
	{
		String curDate=null;
		Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy") ;
        curDate =dateFormat.format(date);
        return curDate;
	}
	
	
	
	
	public String getFutureDate(int days)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, days); // Adds user required days
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy") ;
        String FutureDate =dateFormat.format(c.getTime());
		return FutureDate;
	}
	
	
	public String getUserDefinedFutureDate(String dateText,int days) throws ParseException
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy") ;
		Date date = dateFormat.parse(dateText); 
		Calendar c = Calendar.getInstance();
		c.setTime(date); // Now use user defined date.
		c.add(Calendar.DATE, days); // Adds user required days
        String FutureDate =dateFormat.format(c.getTime());
		return FutureDate;
	}
	
	
	public Date getDate(String dateText) throws ParseException
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy") ;
		Date date = dateFormat.parse(dateText); 
		Calendar c = Calendar.getInstance();
		c.setTime(date); // Now use user defined date 
		return date;
	}
	
	
	public String getDataBaseCreditDate(String dateText) throws ParseException
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy") ;
		Date date = dateFormat.parse(dateText); 
		DateFormat out = new SimpleDateFormat("yyyy-MM-dd");
		String outDate = out.format(date);
		return outDate;
	}
	
	/**
	 * to get current date along with time in "MM-dd-yyyy,HH-mm" format.
	 * @return
	 */
	
	public String currentDateTime()
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy,HH-mm");
		String formattedDate = sdf.format(date);
		formattedDate.toString();
		return formattedDate;
	}
	
	
	public String getDateTimeStamp()
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy,hh:mm a");
		String formattedDate = sdf.format(date);
		formattedDate.toString();
		return formattedDate;
	}
	
	
	public String getCutoffTime(String TimeFormat) throws ParseException
	{
		DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Date Time = sdf.parse(TimeFormat);
		DateFormat out = new SimpleDateFormat("hh:mm aa");
		String outTime = out.format(Time);
		return outTime + " CST";
	}
	
	public String getCutoffReferenceTime(String TimeFormat) throws ParseException
	{
		DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Date Time = sdf.parse(TimeFormat);
		DateFormat out = new SimpleDateFormat("hh:mm aa");
		String outTime = out.format(Time);
		return outTime + " CST";
	}
	
	
	/**
	 * to get the unmatched array list
	 * @param array1
	 * @param array2
	 * @return
	 */
	
	public ArrayList<String> getUmatchedInArrayComparision(ArrayList<String> array1, ArrayList<String> array2)
	{
		ArrayList<String> UnmatchedArray = new ArrayList<String>();
		for(int i=0;i<array1.size();i++)
		{
		if(!((array1.get(i)).contentEquals(array2.get(i))))
			UnmatchedArray.add(array1.get(i));	
		}
		return UnmatchedArray;
	}
	
	public boolean isArrayHavingSameData(ArrayList<String> array1)
	{
		ArrayList<String> UnmatchedArray = new ArrayList<String>();
		for(int i=0;i<array1.size();i++)
		{
		if(!((array1.get(0)).contentEquals(array1.get(i))))
			UnmatchedArray.add(array1.get(i));	
		}
		if(UnmatchedArray.size()==0)
		{
			return true;
		}
		else
			return false;
	}

	public ArrayList<String> getUmatchedInBothArrayComparision(ArrayList<String> array1, ArrayList<String> array2)
	{
		ArrayList<String> UnmatchedArray = new ArrayList<String>();
		for(final String value : array1)
		if(!((array2.contains(value)))){
			UnmatchedArray.add(value);	
		}
		for(final String value : array2)
			if(!((array1.contains(value)))){
				UnmatchedArray.add(value);	
			}		
		return UnmatchedArray;
	}
	
	public Map<String, String> getUnmatchedmap1Comparedwithmap2(Map<String, String> objMap1 ,Map<String, String> objMap2)
	{
		//objMap1 = new HashMap<String, String>();
		//objMap2 = new HashMap<String, String>();
        Map<String, String> resultMap = new HashMap<String, String>();
        for (final String key : objMap1.keySet()) {
            if (objMap2.containsKey(key)) {
                if (!objMap1.get(key).equals(objMap2.get(key))) {
                    resultMap.put(key, objMap1.get(key));
                }
            } else {
                resultMap.put(key, objMap1.get(key));
            }
        }
		return resultMap;
	}
	
	public Map<String , String> getUnmatchedmap2keysComparedwithmap1(Map<String, String> objMap1 ,Map<String, String> objMap2)
	{
		//objMap1 = new HashMap<String, String>();
		//objMap2 = new HashMap<String, String>();
        Map<String, String> resultMap = new HashMap<String, String>();
        for (final String key : objMap2.keySet()) {
            if (!objMap1.containsKey(key)) {
            	 resultMap.put(key, objMap2.get(key));
            } 
        }
		return resultMap;
	}
	
	
	
	
	public String getAmountFormat(String Value)
	{
		DecimalFormat dFormat = new DecimalFormat("####,###,###.00");
		 return dFormat.format(Double.valueOf(Value));
	}
	
	public boolean tocheckgivendateisFutureDateornot(String givendateinMMddyyyy) throws ParseException
	{
		
		String sdate=givendateinMMddyyyy;
		Calendar c = Calendar.getInstance();

		// set the calendar to start of today
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		// and get that as a Date
		Date today = c.getTime();
		DateFormat sd= new SimpleDateFormat("MM/dd/yyyy");
		Date gd=sd.parse(sdate);
		if(gd.after(today))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	public String getNextSunday()
	{
		Calendar calendar = Calendar.getInstance();
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		int days = Calendar.SUNDAY - weekday;
		if (days < 0)
		{
		    days += 7;
		}
		calendar.add(Calendar.DAY_OF_YEAR, days);
		Date Dt = calendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy") ;
	    String outDate =dateFormat.format(Dt);
	    return outDate;
	}

	
	public String getNextSaturday()
	{
		Calendar calendar = Calendar.getInstance();
		int weekday = calendar.get(Calendar.DAY_OF_WEEK);
		int days = Calendar.SATURDAY - weekday;
		if (days < 0)
		{
		    days += 7;
		}
		calendar.add(Calendar.DAY_OF_YEAR, days);
		Date Dt = calendar.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy") ;
	    String outDate =dateFormat.format(Dt);
	    return outDate;
	}
	
}
