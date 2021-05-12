package student.adventure;

import java.util.ArrayList;

public class Room {
    //name of the room/location
    private String name;
    //description of the room that one is in
    private String description;
    //the items that are available to pick up in the room
    private String[] items;
    //the directions that one can go in at that certain location
    private DirectionsOnMap[] directions;
    private String imageURL;

    //Empty constructor for Jackson Mapping
    public Room() {

    }
    /**
     * getter to retrieve the name of the room
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * getter to retrieve the description of the room
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * getter to retrieve the items in the room, stored in a String array
     * @return items
     */
    public String[] getItems() {
        return items;
    }

    public String getImageURL() {
        return imageURL;
    }
    /**
     * getter to retrieve the directions in the room, stored in a different class DirectionsOnMap
     * @return directions
     */
    public DirectionsOnMap[] getDirections() {
        return directions;
    }

    /**
     * setter to modify the list of items in a location after a take/drop method
     * @param newItems - the new modified list
     */
    public void setNewItems(String[] newItems) {
        items = newItems;
    }

    /**
     * Full constructor for the Room class
     * @param name - name of the room
     * @param description - description of the room
     * @param items - items located in the room
     * @param directions - possible locations from the room
     */
    public Room(String name, String description, String[] items, String imageURL, DirectionsOnMap[] directions) {
        this.name = name;
        this.description = description;
        this.items = items;
        this.imageURL = imageURL;
        this.directions = directions;
    }
}
