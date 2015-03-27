package fr.treeptik.controller.fake;

import fr.treeptik.model.Point;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FakeMesureController_DataWithDateComparator_Should {

    private FakeMesureController fakeMesureController = new FakeMesureController();

    @Test
    public void checkFirstElementBuild() {

        Calendar calendarBegin = Calendar.getInstance();
        calendarBegin.set(2015,2,15,0,0);
        Date dateBegin = calendarBegin.getTime();

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.set(2015,2,20,0,0);
        Date dateEnd = calendarEnd.getTime();

        List<Point> list = fakeMesureController.dataWithDateComparator(dateBegin, dateEnd);
        assertThat(list.size(), is(60));

        for(Point each : list){
            Calendar c = Calendar.getInstance();
            c.setTime(each.getDate());
            assertTrue(c.after(calendarBegin));
            assertTrue(c.before(calendarEnd));
        }
    }
}