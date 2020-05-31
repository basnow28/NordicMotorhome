package kea.nordicmotorhome;


import kea.nordicmotorhome.Repository.BookingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookingRepositoryTest {
    @Autowired
    BookingRepository bookingRepository;
}
