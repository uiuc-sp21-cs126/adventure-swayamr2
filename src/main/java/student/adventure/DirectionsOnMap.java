package student.adventure;

/**
 * This class works much like a linked list, where the Direction acts as a key, and the next room is viewed
 * as a value
 */
public class DirectionsOnMap {
    //the direction one is going in
    private String directionName;
    //the next room one will go in following the currentDirection
    private String room;

    public DirectionsOnMap() {

    }
    /**
     * getter to retrieve the name of the direction
     * @return directionName
     */
    public String getDirectionName() {
        return directionName;
    }

    /**
     * getter to retrieve the name of the room that will be reached following a said direction
     * @return room
     */
    public String getRoom() {
        return room;
    }

    /**
     * Full constructor for the DirectionsOnMap class
     * @param directionName - name of the direction(north, east, south, west, etc.)
     * @param roomName - name of the room in said direction
     */
    public DirectionsOnMap(String directionName, String roomName) {
        this.directionName = directionName;
        this.room = roomName;
    }
}
