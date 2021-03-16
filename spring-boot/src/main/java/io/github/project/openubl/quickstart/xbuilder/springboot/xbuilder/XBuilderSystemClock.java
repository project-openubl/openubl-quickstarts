package io.github.project.openubl.quickstart.xbuilder.springboot.xbuilder;

import io.github.project.openubl.xmlbuilderlib.clock.SystemClock;

import java.util.Calendar;
import java.util.TimeZone;

public class XBuilderSystemClock implements SystemClock {
    @Override
    public TimeZone getTimeZone() {
        return TimeZone.getTimeZone("America/Lima");
    }

    @Override
    public Calendar getCalendarInstance() {
        return Calendar.getInstance();
    }
}
