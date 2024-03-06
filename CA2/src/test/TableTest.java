import objects.Table;
import objects.Reservation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    Table table;
    Reservation reservation;
    @BeforeEach
    void setUp() {
        table = new Table(1, "", "", 4);
        table.reservedDateTimes.add(LocalDateTime.parse("2024-08-15T21:00"));
        table.reservedDateTimes.add(LocalDateTime.parse("2024-08-14T21:00"));
    }

    @AfterEach
    void tearDown() {
        table = null;
    }

    @Test
    void hasDateTimeConflict() {
        reservation = new Reservation("username", "restname", 1, "2024-08-14 21:00");
        assertTrue(table.hasDateTimeConflict(reservation));
    }
    @Test
    void hasNotDateTimeConflict() {
        reservation = new Reservation("username", "restname", 1, "2024-08-14 23:00");
        assertFalse(table.hasDateTimeConflict(reservation));
    }

    @Test
    void ThrowExceptionWhenConflictHappensInAddReservation() throws Exception {
        reservation = new Reservation("username", "restname", 1, "2024-08-14 21:00");
        Exception thrown = assertThrows(
                Exception.class,
                () -> table.addReservation(reservation)
        );

        assertEquals(thrown.getMessage(), "This table is already reserved");
    }

    @Test
    void AddToReservationWhenConflictNotHappensInAddReservation() throws Exception {
        ArrayList<LocalDateTime> list = table.reservedDateTimes;
        reservation = new Reservation("username", "restname", 1, "2024-08-14 23:00");
        table.addReservation(reservation);
        list.add(reservation.datetimeFormatted);
        assertEquals(list, table.reservedDateTimes);
    }
}