package proj.Base;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PayDateCalculations  {

	
	public static String currentDate()
	{
		String curDate=null;
		Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy") ;
        curDate =dateFormat.format(date);
        return curDate;
	}
	
	public static String getDate(Calendar cal)
	{
		 Date Dt = cal.getTime();
	     SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy") ;
	     String outDate =dateFormat.format(Dt);
	     return outDate;
	}

	public static String getDateTimeStamp()
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
		String formattedDate = sdf.format(date);
		formattedDate.toString();
		return formattedDate;
	}
	
	
	public static Date getDate(String dateText) throws ParseException
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy") ;
		Date date = dateFormat.parse(dateText); 
		Calendar c = Calendar.getInstance();
		c.setTime(date); // Now use user defined date 
		return date;
	}
	
	public static Calendar getCalendarTime(String Time) throws ParseException
	{
        Date time1 = new SimpleDateFormat("hh:mm a").parse(Time);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(time1);
        return calendar1;
	}
	
	/*
    public static void main(String[] args) throws Exception {
     
    	String ResultDate = OutDate("05/02/2015 ", 6, "sub");
    	System.out.println("The Result Date is " + ResultDate);
    	
    	System.out.println("The Final Populated Date after Time consideration is" + getFinalDate(ResultDate,"4:16 pm"));
    	
    	
    }*/
    
    public static boolean isCreditDatelessthanCurrenDate(String ResultDate) throws ParseException
    {
    	Date CurrentDate = getDate(currentDate());
    	Date Result = getDate(ResultDate);
    	
    	if(Result.after(CurrentDate) || Result.equals(CurrentDate))
    	{
    		System.out.println("The Final Date is " + ResultDate);
    		return true;
    	}
    	else
    		return false;
    }
    
	public static String getUserDefinedFutureDate(String dateText,int days) throws ParseException
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy") ;
		Date date = dateFormat.parse(dateText); 
		Calendar c = Calendar.getInstance();
		c.setTime(date); // Now use user defined date.
		c.add(Calendar.DATE, days); // Adds user required days
        String FutureDate =dateFormat.format(c.getTime());
		return FutureDate;
	}
    
    public static String getFinalProcessedDate(String ResultDate , String ReferenceTime , int payleadtime ,  ArrayList<String> bankholidays) throws Exception
    {
    	Date CurrentDate = getDate(currentDate());
    	Calendar c = Calendar.getInstance();
    	c.setTime(CurrentDate); // Now use user defined date.
		c.add(Calendar.DATE, 1); 
    	Date oneDayFuture = getDate(getDate(c));
    	String ResultDate1 = OutDate(ResultDate, payleadtime, "sub" ,bankholidays);
    	Date Result = getDate(ResultDate1);
    	
    	if(Result.after(oneDayFuture)  )
    	{
    		System.out.println("The Final Date is " + ResultDate1);
    		return ResultDate1;
    	}
    	else if(Result.equals(oneDayFuture))
    	{
    		Date CurrentTime = getCalendarTime(getDateTimeStamp()).getTime();
    		if(CurrentTime.before(getCalendarTime(ReferenceTime).getTime()) || CurrentTime.equals(getCalendarTime(ReferenceTime).getTime()))
    		{
        	System.out.println("The Final Result Date is " + ResultDate1);
        	return ResultDate1;
    		}
    		else
    		{
    			String ResultDate21 = OutDate(getDate(c), 1, "Add",bankholidays);
            	System.out.println("The Final Result Date 1 is " + ResultDate21);
            	return ResultDate21;
    		}
    	}
    	else 
    	{
    		System.out.println("The Current Time is " + getDateTimeStamp());
    		Date CurrentTime = getCalendarTime(getDateTimeStamp()).getTime();
    		
    		if(CurrentTime.before(getCalendarTime(ReferenceTime).getTime()) || CurrentTime.equals(getCalendarTime(ReferenceTime).getTime()))
    		{
    		String ResultDate11 = OutDate(currentDate(), 1, "Add",bankholidays);
        	System.out.println("The Final Result Date is " + ResultDate1);
        	return ResultDate11;
    		}
    		else
    		{
    			String ResultDate22 = OutDate(currentDate(), 2, "Add",bankholidays);
            	System.out.println("The Final Result Date 1 is " + ResultDate22);
            	return ResultDate22;
    		}
    	}
    }
    

    private static boolean isWorkingDay(Calendar cal , ArrayList<String> bankholidays) throws ParseException, Exception {
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY )
            return false;
       for(int i=0;i<bankholidays.size();i++)
       {
        if(bankholidays.get(i).contentEquals(getDate(cal)))
        return false;
       }
        return true;
    }
    
    
    public static String OutDate(String DueDate , int noofdays, String operation ,  ArrayList<String> bankholidays) throws Exception
    {
    	   Calendar cal =  new GregorianCalendar();
           // cal now contains current date
    	   Date due = getDate(DueDate);
    	   cal.setTime(due);
          System.out.println("The date is " + getDate(cal));
           // add the working days
          if(operation.equalsIgnoreCase("add"))
          { 
        	  for (int i=0; i<noofdays; i++)
              {
                  do {
                      cal.add(Calendar.DATE, 1);
                      System.out.println(cal.getTime());
                  } while ( ! isWorkingDay(cal, bankholidays ));
              }
           System.out.println(cal.getTime());
           System.out.println("The out date is " + getDate(cal));
          }
          else
          {
        	  for (int i=0; i<noofdays; i++)
        	  {
        		  System.out.println("sub");
        		  do {
                      cal.add(Calendar.DATE, -1);
                  }while ( ! isWorkingDay(cal, bankholidays));
        	  }
              System.out.println(cal.getTime());
              System.out.println("The out date is " + getDate(cal));
          }
           return getDate(cal);
    }

}