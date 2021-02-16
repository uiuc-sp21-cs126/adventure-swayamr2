package student.adventure;

import java.util.Random;

public class AdventureDesign {

    //the full list of rooms in the JSON file
    public Room[] rooms;
    //the name of the room that one starts in, stored in a String
    public String startingRoom;
    //the name of the room that one ends in, stored in a String
    public String endingRoom;

    //Empty constructor for Jackson Mapping
    public AdventureDesign() {

    }

    /**
     * getter to retrieve the starting room of the adventure
     * @return startingRoom
     */
    public String getStartingRoom() {
        return startingRoom;
    }

    /**
     * getter to retrieve the ending room of the adventure
     * @return endingRoom
     */
    public String getEndingRoom() {
        return endingRoom;
    }

    /**
     * getter to retrieve the full list of rooms in JSON, all stored in the Room object
     * @return rooms
     */
    public Room[] getRooms() {
        return rooms;
    }


    public AdventureDesign(String startingRoom, String endingRoom, Room[] rooms) {
        this.startingRoom = startingRoom;
        this.endingRoom = endingRoom;
        this.rooms = rooms;
    }
}
