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

    public static final String SCHEDULE_PATH = "/schedule";

    public static final String TENNIS_COURT_PATH = "/tennis-court";

    public static final String WITH_SCHEDULES_PATH = "/with-schedules";

    public static final String GUEST_ID_VARIABLE = "/{guestId}";

    public static final String GUEST_NAME_VARIABLE = "/{guestName}";

    public static final String RESERVATION_ID_VARIABLE = "/{reservationId}";

    public static final String SCHEDULE_ID_VARIABLE = "/{scheduleId}";

    public static final String START_DATE_VARIABLE = "/{startDate}";

    public static final String END_DATE_VARIABLE = "/{endDate}";

    public static final String TENNIS_COURT_ID_VARIABLE = "/{tennisCourtId}";
}
