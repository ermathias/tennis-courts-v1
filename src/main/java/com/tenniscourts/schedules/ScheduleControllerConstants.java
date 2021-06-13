package com.tenniscourts.schedules;

import static com.tenniscourts.constants.GlobalConstants.API_V1_CONTEXT;

class ScheduleControllerConstants {

    static final String CONTEXT = API_V1_CONTEXT + "/schedule";
    static final String LIST_BY_DATES = "/list/{startDate}/{endDate}";
    static final String SCHEDULE_ID = "/{scheduleId}";
}
