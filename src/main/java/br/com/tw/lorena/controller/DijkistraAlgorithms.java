package br.com.tw.lorena.controller;

import br.com.tw.lorena.model.Edje;
import br.com.tw.lorena.model.Town;
import br.com.tw.lorena.view.Printer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class DijkistraAlgorithms {

    private List<Town> towns;

	public void execute() throws IOException {
		calculateLengthShortestRouteBetweenTwoTowns(towns.get(0), towns.get(1));
	}

    private void calculateLengthShortestRouteBetweenTwoTowns(Town startTown, Town goalTown) throws IOException {
        Town townStart = startTown;
        Town townGoal = goalTown;
        computePathsByDijkstra(townStart);
        List<Town> path = getShortestPathTo(townGoal);

        if(path.get(0).equals(townGoal) && townGoal.getMinDistance() != 0)
            Printer.printNoSuchRouteTwoTowns(townStart, townGoal);
        else
            Printer.printShortestRouteBetweenTwoTowns(townStart, townGoal, path);
    }
	

	private void computePathsByDijkstra(Town startTown) {
        startTown.setMinDistance(0.);
        PriorityQueue<Town> townQueue = new PriorityQueue<Town>();
        townQueue.add(startTown);

        while (!townQueue.isEmpty()) {
        	Town u = townQueue.poll();
        	for (Edje e : u.getAdjacencies())
            {
            	Town v = e.goalTown;
                double distance = e.distance;
                double distanceThroughU = u.getMinDistance() + distance;
        if (distanceThroughU < v.getMinDistance()) {
            townQueue.remove(v);
            v.setMinDistance(distanceThroughU);
            v.setPrevious(u);
            townQueue.add(v);
        }
            }
        }
    }

    private List<Town> getShortestPathTo(Town goalTown) {
        List<Town> path = new ArrayList<Town>();
        for (Town vertex = goalTown; vertex != null; vertex = vertex.getPrevious())
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

    public void setTowns(List<Town> towns) {
        this.towns = towns;
    }
}
