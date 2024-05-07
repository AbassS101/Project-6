/* @Author Abass Shikur
 *  This is my original work
 */

import java.util.Objects;

public class Town implements Comparable<Town> {
    private String name;

    public Town(String name) {
        this.name = name;
    }
    

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Town x) {
        return this.name.compareTo(x.name);
    }

    // This method checks if this town is equal to another object
    @Override
    public boolean equals(Object x) {
        if (this == x) 
        	return true;
        if (x == null) 
        	return false;
        Town town = (Town) x;
        return Objects.equals(name, town.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}