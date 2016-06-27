package phohawkenics.validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import phohawkenics.common.Constants;

public class RequestValidator extends Validator{
	
	public boolean isNotValidTopic(String T) {
		if (containsOnlyAlphabetic(T))
			return false;
		return true;
	}
	
	public boolean isValidYear(String Y) {
		if (containsOnlyNumbers(Y)) {
			if (Y.length() == 4)
			return true;
		}
		return false;
	}
	
	public boolean isValidMonth(String M) {
		if (containsOnlyNumbers(M)) {
			if (M.length() <= 2) {
			int monthInt = Integer.parseInt(M);
			if (monthInt < 1 || monthInt > 12) 
				return false;
			else 
				return true;
			}
		}
		return false;
	}
	
	public boolean isValidDay(String D) {
		if (containsOnlyNumbers(D)) {
			if (D.length() <= 2) {
			int dayInt = Integer.parseInt(D);
			if (dayInt < 1 || dayInt > 31) 
				return false;
			else 
				return true;
			}
		}
		return false;
	}
	
	public boolean isValidHour(String h) {
		if (containsOnlyNumbers(h)) {
			if (h.length() <= 2) {
			int hourInt = Integer.parseInt(h);
			if (hourInt < 0 || hourInt > 24) 
				return false;
			else 
				return true;
			}
		}
		return false;
	}
	
	public boolean isValidMin (String m) {
		if (containsOnlyNumbers(m)) {
			if (m.length() <= 2) {
			int hourInt = Integer.parseInt(m);
			if (hourInt < 0 || hourInt > 60) 
				return false;
			else 
				return true;
			}
		}
		return false;
	}
	
	public boolean startIsLessThanEnd(String startTime, String endTime) {
		String startArray[] = startTime.split(Constants.SEPERATOR_TIME_H);
		String endArray[] = endTime.split(Constants.SEPERATOR_TIME_H);
		if (Integer.parseInt(startArray[0]) > Integer.parseInt(endArray[0])) {
			return false;
		} 
		else {
			if (Integer.parseInt(startArray[0]) == Integer.parseInt(endArray[0])){
				if (Integer.parseInt(startArray[1]) > Integer.parseInt(endArray[0]))
					return false;
			}
		}
		return true;
	}
	
	public boolean isDateBeforePresent (String date, String startTime) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date currentDate = new Date();
		String dateInfo[] = dateFormat.format(currentDate).split(Constants.SEPERATOR_INPUT);
		String dateArray[] = dateInfo[0].split(Constants.SEPERATOR_DATE);
		int curYear = Integer.parseInt(dateArray[0]);
		int curMonth = Integer.parseInt(dateArray[1]);
		int curDay = Integer.parseInt(dateArray[2]);
		
		String inputDate[] = date.split(Constants.SEPERATOR_DATE);
		int inYear = Integer.parseInt(inputDate[0]);
		int inMonth = Integer.parseInt(inputDate[1]);
		int inDay = Integer.parseInt(inputDate[2]);
		
		if (inYear > curYear)
			return false;
			
		if (inYear == curYear && inMonth >= curMonth && inDay >= curDay) {
			String timeArray[] = dateInfo[1].replace(":", Constants.SEPERATOR_TIME_H).split(Constants.SEPERATOR_TIME_H);
			int curHour = Integer.parseInt(timeArray[0]);
			int curMin = Integer.parseInt(timeArray[1]);
			String inputTime[] = startTime.split(Constants.SEPERATOR_TIME_H);
			int inHour = Integer.parseInt(inputTime[0]);
			int inMin = Integer.parseInt(inputTime[1]);
			
			if(inYear == curYear && inMonth == curMonth && inDay == curDay){
				if (inHour > curHour) {
					return false;
				} 
				else if (inHour == curHour)
						if (inMin > curMin)
							return false;
			}
			else
				return false;
		}
		return true;
	}
}
