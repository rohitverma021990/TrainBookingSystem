package com.trainbooking.api;

import com.trainbooking.enums.SeatType;
import com.trainbooking.exception.BadRequestException;
import com.trainbooking.exception.InsufficentSeatsException;
import com.trainbooking.model.Train;
import com.trainbooking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingControllerTest extends BaseTest{

    @BeforeEach
    public void setUp(){
        setUpController();
    }

    @Test
    public void testCreateBooking() throws BadRequestException, InsufficentSeatsException {
        User user1 = this.getTestUser("first1", "last1", "firstLast1@gmail.com");
        Train train = this.getTrain("London", "France");
        String bookingId = bookingController.createBooking("London", "France", user1.getEmailId(), 20, 1, SeatType.ECONOMY);
        assertTrue(!bookingId.equals(""));
    }

    @Test
    public void testCreateMultipleBooking() throws BadRequestException, InsufficentSeatsException {
        User user1 = this.getTestUser("first1", "last1", "firstLast1@gmail.com");
        User user2 = this.getTestUser("first2", "last2", "firstLast2@gmail.com");
        Train train = this.getTrain("London", "France");
        String bookingId1 = bookingController.createBooking("London", "France", user1.getEmailId(), 20, 1, SeatType.ECONOMY);
        String bookingId2 = bookingController.createBooking("London", "France", user2.getEmailId(), 60, 2, SeatType.BUSSINESS);

        assertTrue(!bookingId1.equals("") && !bookingId2.equals(""));
    }

    @Test
    public void testCreateBookingException() throws BadRequestException {
        User user1 = this.getTestUser("first1", "last1", "firstLast1@gmail.com");
        Train train = this.getTrain("London", "France");
        Exception exception = assertThrows(InsufficentSeatsException.class, () -> {
            bookingController.createBooking("London", "France", user1.getEmailId(), 120, 6, SeatType.ECONOMY);
        });
        assertTrue(exception.getStackTrace()!=null);
    }

    @Test
    public void testCancelBooking() throws BadRequestException, InsufficentSeatsException {
        User user1 = this.getTestUser("first1", "last1", "firstLast1@gmail.com");
        User user2 = this.getTestUser("first2", "last2", "firstLast2@gmail.com");
        Train train = this.getTrain("London", "France");
        String bookingId1 = bookingController.createBooking("London", "France", user1.getEmailId(), 20, 1, SeatType.ECONOMY);
        String bookingId2 = bookingController.createBooking("London", "France", user2.getEmailId(), 60, 2, SeatType.BUSSINESS);
        assertTrue(bookingController.cancelBooking(user1.getEmailId()));
    }

    @Test
    public void testModifyBooking() throws BadRequestException, InsufficentSeatsException {
        User user1 = this.getTestUser("first1", "last1", "firstLast1@gmail.com");
        User user2 = this.getTestUser("first2", "last2", "firstLast2@gmail.com");
        Train train = this.getTrain("London", "France");
        String bookingId1 = bookingController.createBooking("London", "France", user1.getEmailId(), 20, 1, SeatType.ECONOMY);
        String bookingId2 = bookingController.createBooking("London", "France", user2.getEmailId(), 60, 2, SeatType.BUSSINESS);
        assertTrue(bookingController.updateBooking(bookingId1, 2, SeatType.BUSSINESS, 40));
        String bookingInfo = bookingController.getBookingInfo(bookingId1);
        System.out.println(bookingInfo);

    }
}
