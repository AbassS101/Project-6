/* @Author Abass Shikur
 *  This is my original work
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TownGraphManager implements TownGraphManagerInterface {
	private final Graph<Town, Road<Town>> graph;
    private final Map<String, Town> townMap;

    public TownGraphManager() {
        graph = new Graph<>();
        townMap = new HashMap<>();
    }
    
	// Adds a road with two towns and a road name
    @Override
    public boolean addRoad(String town1, String town2, int weight, String roadName) {
        Town source = townMap.get(town1);
        Town destination = townMap.get(town2);
        if (source == null || destination == null) {
            return false;
        }
        Road road = graph.addEdge(source, destination, weight, roadName);
        return road != null;
    }

    @Override
    public String getRoad(String town1, String town2) {
        Town source = townMap.get(town1);
        Town destination = townMap.get(town2);
        if (source == null || destination == null) {
            return null;
        }
        Road road = graph.getEdge(source, destination);
        return road != null ? road.getName() : null;
    }

    @Override
    public boolean addTown(String tName) {
        Town town = new Town(tName);
        if (townMap.containsKey(tName)) {
            return false;
        }
        townMap.put(tName, town);
        graph.addVertex(town);
        return true;
    }

    @Override
    public Town getTown(String name) {
        Town queryTown = new Town(name);
        for (Town t : graph.vertexSet()) {
            if (t.equals(queryTown)) {
                return t;
            }
        }
        return null;
    }
    @Override
    public boolean containsTown(String townName) {
        return townMap.containsKey(townName);
    }
    
    // Checks if a road connection is there between two towns
    @Override
    public boolean containsRoadConnection(String town1, String town2) {
        Town source = townMap.get(town1);
        Town destination = townMap.get(town2);
        if (source == null || destination == null) {
            return false;
        }
        return graph.containsEdge(source, destination);
    }

    @Override
    public ArrayList<String> allRoads() {
        Set<Road<Town>> roads = graph.edgeSet();
        ArrayList<String> roadNames = new ArrayList<>();
        for (Road<Town> r : roads) {
            roadNames.add(r.getName());
        }
        Collections.sort(roadNames);
        return roadNames;
    }

    @Override
    public boolean deleteRoadConnection(String town1, String town2, String road) {
        Town source = townMap.get(town1);
        Town destination = townMap.get(town2);
        if (source == null || destination == null) {
            return false;
        }
        Road roadToRemove = graph.getEdge(source, destination);
        if (roadToRemove == null || !roadToRemove.getName().equals(road)) {
            return false;
        }
        graph.removeEdge(source, destination, roadToRemove.getWeight(), road);
        return true;
    }

    @Override
    public boolean deleteTown(String townName) {
        Town town = townMap.get(townName);
        if (town == null) {
            return false;
        }
        townMap.remove(townName);
        return graph.removeVertex(town);
    }

    @Override
    public ArrayList<String> allTowns() {
        Set<Town> towns = graph.vertexSet();
        ArrayList<String> townNames = new ArrayList<>();
        for (Town t : towns) {
            townNames.add(t.getName());
        }
        Collections.sort(townNames);
        return townNames;
    }

    // Returns the shortest path between two towns
    @Override
    public ArrayList<String> getPath(String town1, String town2) {
        Town source = townMap.get(town1);
        Town destination = townMap.get(town2);
        if (source == null || destination == null) {
            System.out.println("One or both towns not found in the graph: " + town1 + ", " + town2);
            return new ArrayList<>();
        }
        ArrayList<String> path = graph.shortestPath(source, destination);
        System.out.println("Path from " + town1 + " to " + town2 + ": " + path);
        return path;
    }
    
    // Populates the graph with data from a file
    public void populateTownGraph(File file) throws FileNotFoundException {
        try{
        	Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] tokens = line.split(";");
            if (tokens.length == 3) {
                String[] info = tokens[0].split(",");
                if (info.length == 2) {
                    String roadName = info[0].trim();
                    int distance = Integer.parseInt(info[1].trim());
                    Town city1 = new Town(tokens[1].trim());
                    Town city2 = new Town(tokens[2].trim());
                    // Add the Town objects as vertices and the Road object as an edge
                    addTown(city1.getName());
                    addTown(city2.getName());
                    addRoad(city1.getName(), city2.getName(), distance, roadName);
                }
            } else {
                System.out.println("Skipping invalid line: " + line);
            }
        }
        scan.close();
    } catch (IOException e) {
        System.out.println("Error reading file: " + e.getMessage());
    }
    }
}