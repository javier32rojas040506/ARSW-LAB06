package edu.eci.arsw.blueprints.service.filters.impl;

import edu.eci.arsw.blueprints.service.filters.Filter;
import edu.eci.arsw.blueprints.service.model.Blueprint;
import edu.eci.arsw.blueprints.service.model.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RedundancyFilter implements Filter {
    @Override
    public Blueprint filterPoints(Blueprint bp) {
        List<Point> pts = bp.getPoints();
        List<Point> ptsAnswer = new ArrayList<>();
        for (int i = 0; i< pts.size(); i++){
            if(i==0){
                ptsAnswer = verificarPos0(i, pts, ptsAnswer);
            } else if (i == pts.size()-1) {
                ptsAnswer = verificarPosFinal(i, pts, ptsAnswer);
            } else{
                ptsAnswer = verificarPosMedio(i, pts, ptsAnswer);
            }

        }
        bp.setPoints(ptsAnswer);
        //System.out.println("sip"+ pts);
        //System.out.println("sip"+ ptsAnswer);
        return bp;
    }

    public List<Point> verificarPos0(int i, List<Point> pts, List<Point> ptsAnswer){
        Point point = pts.get(i);
        Point nextPoint = pts.get(i+1);
        Boolean cordX = point.getX() == nextPoint.getX();
        Boolean cordY = point.getY() == nextPoint.getY();
        if(!(cordY && cordX)){
            ptsAnswer.add(point);
        }
        return ptsAnswer;
    }
    public List<Point> verificarPosFinal(int i, List<Point> pts, List<Point> ptsAnswer){
        Point endpoint = pts.get(i);
        Point beforeEndPoint = pts.get(i-1);
        Boolean cordX = beforeEndPoint.getX() == endpoint.getX();
        Boolean cordY = beforeEndPoint.getY() == endpoint.getY();
        if(!(cordY && cordX)){
            ptsAnswer.add(endpoint);
        }
        return ptsAnswer;
    }
    public List<Point> verificarPosMedio(int i, List<Point> pts, List<Point> ptsAnswer){
        Point point = pts.get(i);
        Point nextPoint = pts.get(i+1);
        Point beforeEndPoint = pts.get(i-1);
        Boolean cordX = point.getX() == nextPoint.getX();
        Boolean cordY = point.getY() == nextPoint.getY();
        Boolean cordX2 = beforeEndPoint.getX() == point.getX();
        Boolean cordY2 = beforeEndPoint.getY() == point.getY();
        if(!(cordY && cordX) && !(cordY2 && cordX2)){
            ptsAnswer.add(point);
        }
        return ptsAnswer;
    }

}
