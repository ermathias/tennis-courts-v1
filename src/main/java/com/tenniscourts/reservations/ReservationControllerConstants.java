package com.tenniscourts.reservations;

import static com.tenniscourts.constants.GlobalConstants.API_V1_CONTEXT;

class ReservationControllerConstants {

    static final String CONTEXT = API_V1_CONTEXT + "/reservation";
    static final String CANCEL = "/cancel";
    static final String RESCHEDULE = "/reschedule";
    static final String ID_PARAM = "/{reservationId}";
    static final String SCHEDULE_ID_PARAM = "/{scheduleId}";
}
