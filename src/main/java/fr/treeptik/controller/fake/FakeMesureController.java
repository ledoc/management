package fr.treeptik.controller.fake;


import fr.treeptik.shared.dto.graph.GraphDTO;
import fr.treeptik.shared.dto.graph.PointGraphDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FakeMesureController {


    private FakeMesureControllerDataListBuilder fakeMesureControllerDataListBuilder = new FakeMesureControllerDataListBuilder();
    List<FakeMesureControllerData> list;

    public FakeMesureController() {
        this.list = fakeMesureControllerDataListBuilder.create();
    }

    public GraphDTO dataWithDateComparator(Date dateBegin, Date dateEnd) {
        GraphDTO graph = new GraphDTO();
        graph.setName("Niveau d'eau");
        graph.setMid("MID ?");
        graph.setUnite("Unité ?");
        List<PointGraphDTO> points = new ArrayList<PointGraphDTO>();
        for (FakeMesureControllerData eachData : list) {
            if (isDateValidWithBeginAndEndDates(eachData, dateBegin, dateEnd)
                    && isDateValidWithNow(eachData)) {
                points.add(point(eachData));
            }
        }
        graph.setPoints(points);
        return graph;
    }

    public GraphDTO data() {
        GraphDTO graph = new GraphDTO();
        graph.setName("Niveau d'eau");
        graph.setMid("MID");
        graph.setUnite("Unité de Valeur");
        List<PointGraphDTO> points = new ArrayList<PointGraphDTO>();
        for (FakeMesureControllerData eachData : list) {
            if (isDateValidWithNow(eachData)) {
                points.add(point(eachData));
            }
        }
        graph.setPoints(points);
        return graph;
    }

    private boolean isDateValidWithNow(FakeMesureControllerData data) {
        return data.getDate().before(new Date());
    }

    private boolean isDateValidWithBeginAndEndDates(FakeMesureControllerData data, Date dateBegin, Date dateEnd) {
        Date date = data.getDate();
        return date.after(dateBegin) && date.before(dateEnd);
    }


    private PointGraphDTO point(FakeMesureControllerData data) {
        PointGraphDTO point = new PointGraphDTO();
        point.setValeur(data.getValue());
        point.setDate(data.getDate());
        return point;
    }


}