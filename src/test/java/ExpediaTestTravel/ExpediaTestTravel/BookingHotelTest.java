package ExpediaTestTravel.ExpediaTestTravel;

import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ExpediaTestTravel.ExpediaTestTravel.HotelBookEXT.BookHotelPageEXT;
import ExpediaTestTravel.ExpediaTestTravel.HotelBookEXT.SearchHotelPageEXT;
import ExpediaTestTravel.ExpediaTestTravel.HotelBookEXT.SearchHotelResultPageEXT;

import com.paypal.selion.annotations.WebTest;
import com.paypal.selion.platform.utilities.WebDriverWaitUtils;

/**
 * Test Case:
 * 		1.  Book from next available Monday to next three weeks, book Cheapest Hotel
 * @author mousumisen
 *
 */
public class BookingHotelTest {

	private String hotelDestination = "San Fra";
	private Date checkInDate;
	private Date checkOutDate;
	private int JurneyDuration = 21;
	private String hotelName = "Extended Stay downtown";
	
	BookHotelPageEXT bookHotelPageEXT = new BookHotelPageEXT();
	SearchHotelResultPageEXT searchHotelResultPageEXT =  new SearchHotelResultPageEXT();
	SearchHotelPageEXT searchHotelPageEXT = new SearchHotelPageEXT();
	
	/**
	 * generate checkIn date and checkOut date
	 */
	@BeforeTest
	public void init() {
		Date checkInDate = this.genarateNextMonday();
		Date checkOutDate = this.genarateReturnDay(checkInDate, JurneyDuration);
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}
	
	/**
	 * method to generate next Monday
	 * @return
	 */
	public Date genarateNextMonday() {
		Date today = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(today);
		ca.set(Calendar.WEEK_OF_YEAR, ca.get(Calendar.WEEK_OF_YEAR) + 1);
		ca.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return ca.getTime();
	}
	
	/**
	 * method to generate Check Out
	 * @return
	 */
	public Date genarateReturnDay(Date date, int during) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DAY_OF_YEAR, during);
		return ca.getTime();
	}
	
	/**
	 * Search Hotel for booking
	 */
	public void searchHotelforBooking() {
		searchHotelPageEXT.openWebsite();
		searchHotelPageEXT.searchHotel(hotelDestination, checkInDate, checkOutDate);
		WebDriverWaitUtils.waitUntilElementIsPresent("css=#hotel1");
	}
	
	/**
	 * Test Case: 1. Book from next available Monday to next three weeks, book Cheapest Hotel
	 */
	
	public void selectCheapestHotel() {
		this.searchHotelforBooking();
		searchHotelResultPageEXT.getSortByPriceButton().clickAndExpect(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#hotel1")));
		WebDriverWaitUtils.waitUntilElementIsPresent("css=.bColumn");
		searchHotelResultPageEXT.openHotelDetails();
		bookHotelPageEXT.bookFinalHotel();
	}
	
	/**
	 * Test Case: 2. Book from next available Monday to next three weeks, book Cheapest Hotel and rating four star and above
	 */
	public void selectCheapestHotelwithAboveThreeStar() {
		this.searchHotelforBooking();
		searchHotelResultPageEXT.getSortByPriceButton().clickAndExpect(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#hotel1")));
		WebDriverWaitUtils.waitUntilElementIsPresent("css=.bColumn");
		searchHotelResultPageEXT.sortByFiveAndFurStar();
		WebDriverWaitUtils.waitUntilElementIsPresent("css=.bColumn");
		searchHotelResultPageEXT.openHotelDetails();
		bookHotelPageEXT.bookFinalHotel();
	}
	
	/**
	 * Test Case: 3. Book from next available Monday to next three weeks, book Hotel by name "Extended Stay"
	 */
	@Test
	@WebTest
	public void selectHotelByName() {
		this.searchHotelforBooking();
		searchHotelResultPageEXT.searchHotelByName(hotelName);
		searchHotelResultPageEXT.openHotelDetails();
		bookHotelPageEXT.bookFinalHotel();
	}
	
	
	

}
