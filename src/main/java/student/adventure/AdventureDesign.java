package student.adventure;

public class AdventureDesign {

    //the full list of rooms in the JSON file
    public Room[] rooms;
    //the name of the room that one starts in, stored in a String
    public String startingRoom;
    //the name of the room that one ends in, stored in a String
    public String endingRoom;

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

    /**
     * Complete constructor for the AdventureDesign class
     * @param startingRoom - the starting room of the adventure
     * @param endingRoom - the ending room of the adventure
     * @param rooms - the full array of the rooms available
     */
    public AdventureDesign(String startingRoom, String endingRoom, Room[] rooms) {
        this.startingRoom = startingRoom;
        this.endingRoom = endingRoom;
        this.rooms = rooms;
    }
}
