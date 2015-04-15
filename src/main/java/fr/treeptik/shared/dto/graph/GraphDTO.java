package fr.treeptik.shared.dto.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GraphDTO implements Serializable {

    private String name;
    private String mid;
    private String unite;
    private List<PointGraphDTO> points;

    public String getName() {
        if(name == null){
            name = "";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMid() {
        if(mid == null){
            mid = "";
        }
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getUnite() {
        if(unite == null){
            unite = "";
        }
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public List<PointGraphDTO> getPoints() {
        if(points == null){
            points = new ArrayList<PointGraphDTO>();
        }
        return points;
    }

    public void setPoints(List<PointGraphDTO> points) {
        this.points = points;
    }
}
