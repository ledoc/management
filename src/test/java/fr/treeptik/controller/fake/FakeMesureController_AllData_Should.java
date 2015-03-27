package fr.treeptik.controller.fake;

import fr.treeptik.model.Point;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FakeMesureController_AllData_Should {

    private FakeMesureController fakeMesureController = new FakeMesureController();

    @Test
    public void checkCompleteBuild() {
        List<Point> list = fakeMesureController.data();
        //assertThat(list.size(), is(356));
        for(Point eachPoint: list){
            assertNotNull(eachPoint.getDate());
            assertNotNull(eachPoint.getValeur());
            assertNotNull(eachPoint.getTypeCaptAlerteMesure().getDescription());
        }
    }

    @Test
    public void checkFirstElementBuild() {
        List<Point> list = fakeMesureController.data();
        assertThat(list.get(0).getValeur(), is((float) 72.1));
        Calendar c = Calendar.getInstance();
        c.setTime(list.get(0).getDate());
        assertThat(c.get(Calendar.DATE), is(1));
        assertThat(c.get(Calendar.MONTH), is(2));
        assertThat(c.get(Calendar.YEAR), is(2015));
        assertThat(c.get(Calendar.HOUR), is(1));
        assertThat(c.get(Calendar.MINUTE), is(30));
        assertThat(c.get(Calendar.AM_PM), is(Calendar.AM));
    }

}