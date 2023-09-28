package hcmus.edu.vn.main;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;

public class Main3 {

	public static void main(String[] args) throws ParseException {
	

		
		String partern = "dd/MM/yyyy";
		String str1 = "16/06/2022";
		String str2 = "16/12/2022";
		
		Date date1 = DateUtils.parseDate(str1, partern);
		
		Date date2 = DateUtils.parseDate(str2, partern);
		
		long time = date2.getTime() - date1.getTime();
		
		System.out.println(time);
		
		long days = TimeUnit.DAYS.convert(time, TimeUnit.MILLISECONDS);
		System.out.println(days);
		
		System.out.println(date1);
		System.out.println(date2);
		
		String rate = "6.60";
		Long amount = 1000000L;
		
		System.out.println(Double.parseDouble(rate));
		
		Double interestRate = amount * (Double.parseDouble(rate) / 100) /365 * days;
		
		System.out.println(interestRate);
		System.out.println(Math.round(interestRate));

	}

}
