package fr.treeptik.controller.fake;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class FakeMesureControllerDataListBuilder_Should {

    FakeMesureControllerDataListBuilder builder = new FakeMesureControllerDataListBuilder();

    @Test
    public void checkCompleteBuild() {
        List<FakeMesureControllerData> list = builder.create();
        assertThat(list.size(), is(356));
        for(FakeMesureControllerData eachData: list){
            assertNotNull(eachData.getDate());
            assertNotNull(eachData.getValue());
        }
    }

    @Test
    public void checkFirstElementBuild() {
        List<FakeMesureControllerData> list = builder.create();
        assertThat(list.get(0).getValue(), is((float) 72.1));
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