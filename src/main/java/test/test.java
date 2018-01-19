package test;

import java.text.ParseException;

import com.xiangshangban.att_simple.utils.computeVacation;

public class test {

	public static void main(String[] args) {
		
		computeVacation cv = new computeVacation();
		
		try {
			int annualLeave = cv.ABCAnnualFormula("10", 0.5, "2016-01-18", 0.5, 0);
			
			System.out.println(annualLeave);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}