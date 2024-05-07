/* @Author Abass Shikur
 *  This is my original work
 */

import java.util.Objects;

public class Road<V> implements Comparable<Road<V>> {
    private V source;
    private V destination;
    private int weight;
    private String name;

    public Road(V source, V destination, int weight, String name) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.name = name;
    }
    
 // Getter methods for the source, destination, weight, and name
    public V getSource() {
        return source;
    }

    public V getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Road<V> other) {
        return this.name.compareTo(other.name);
    }
    
    // Method to check if two roads are equal
    @Override
    public boolean equals(Object x) {
        if (this == x) 
        	return true;
        if (x == null)
        	return false;
        Road<?> road = (Road<?>) x;
        return weight == road.weight && Objects.equals(name, road.name) && ((Objects.equals(source, road.source) && Objects.equals(destination, road.destination)) || (Objects.equals(source, road.destination) && Objects.equals(destination, road.source)));
    }


    @Override
    public int hashCode() {
        return Objects.hash(source, destination, weight, name);
    }

    @Override
    public String toString() {
        boolean sourceFirst = this.source.toString().compareTo(this.destination.toString()) < 0;
        V first = sourceFirst ? this.source : this.destination;
        V second = sourceFirst ? this.destination : this.source;
        return first + " via " + name + " to " + second + " " + weight + " mi";
    }

}