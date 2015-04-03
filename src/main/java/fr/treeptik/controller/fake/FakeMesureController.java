package fr.treeptik.controller.fake;


import fr.treeptik.model.Point;
import fr.treeptik.model.TypeCaptAlerteMesure;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FakeMesureController {


    private FakeMesureControllerDataListBuilder fakeMesureControllerDataListBuilder = new FakeMesureControllerDataListBuilder();
    List<FakeMesureControllerData> list;

    public FakeMesureController() {
        this.list = fakeMesureControllerDataListBuilder.create();
    }

    public List<Point> dataWithDateComparator(Date dateBegin, Date dateEnd) {
        List<Point> points = new ArrayList<Point>();
        for (FakeMesureControllerData eachData : list) {
            if (isDateValidWithBeginAndEndDates(eachData, dateBegin, dateEnd)
                    && isDateValidWithNow(eachData)) {
                points.add(point(eachData));
            }
        }
        return points;
    }



    public List<Point> data() {
        List<Point> points = new ArrayList<Point>();
        for (FakeMesureControllerData eachData : list) {
            if (isDateValidWithNow(eachData)) {
                points.add(point(eachData));
            }
        }
        return points;
    }

    private boolean isDateValidWithNow(FakeMesureControllerData data) {
        return data.getDate().before(new Date());
    }

    private boolean isDateValidWithBeginAndEndDates(FakeMesureControllerData data, Date dateBegin, Date dateEnd) {
        Date date = data.getDate();
        return date.after(dateBegin) && date.before(dateEnd);
    }


    private Point point(FakeMesureControllerData data) {
        Point point = new Point();
        point.setValeur(data.getValue());
        point.setDate(data.getDate());
        point.setTypeCaptAlerteMesure(type());
        return point;
    }

    private TypeCaptAlerteMesure type() {
        TypeCaptAlerteMesure type = new TypeCaptAlerteMesure();
        type.setDescription("");
        return type;
    }
}