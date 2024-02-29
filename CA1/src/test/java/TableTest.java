import mizdooni.Reservation;
import mizdooni.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    Table table;
    Reservation reservation;
    @BeforeEach
    void setUp() {
        table = new Table(1, "", "", 4);
        table.reservedDateTimes.add("2024-08-15 21:00");
        table.reservedDateTimes.add("2024-08-14 21:00");
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
        ArrayList<String> list = table.reservedDateTimes;
        reservation = new Reservation("username", "restname", 1, "2024-08-14 23:00");
        table.addReservation(reservation);
        list.add(reservation.datetime);
        assertEquals(list, table.reservedDateTimes);
    }
}