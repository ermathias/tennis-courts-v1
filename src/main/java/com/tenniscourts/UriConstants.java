package com.tenniscourts;

public final class UriConstants
{
    private UriConstants()
    {
        // Private constructor for constants class
    }

    public static final String ALL_PATH = "/all";

    public static final String BOOK_PATH = "/book";

    public static final String GUEST_PATH = "/guest";

    public static final String NAME_PATH = "/name";

    public static final String RESCHEDULE_PATH = "/reschedule";

    public static final String RESERVATION_PATH = "/reservation";

    public static final String GUEST_ID_VARIABLE = "/{guestId}";

    public static final String GUEST_NAME_VARIABLE = "/{guestName}";

    public static final String RESERVATION_ID_VARIABLE = "/{reservationId}";

    public static final String SCHEDULE_ID_VARIABLE = "/{scheduleId}";
}
