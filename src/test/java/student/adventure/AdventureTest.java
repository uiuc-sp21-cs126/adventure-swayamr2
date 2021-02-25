
package student.adventure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class AdventureTest {
    private static AdventureDesign testDesign;
    private static GameEngine gameEngine;

    @Before
    public void setUp() {
        File file = new File("src/main/resources/BreakingBad.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            testDesign = mapper.readValue(file, AdventureDesign.class);
        } catch (Exception e) {
            System.out.println("Please try again!");
            System.exit(0);
        }
        gameEngine = new GameEngine(file);
    }

    /*
    Test the number of rooms in the AdventureDesign Object, which should be the proper amount from the JSON
     */
    @Test
    public void testNumberOfRooms() {
        Room[] rooms = testDesign.getRooms();
        assertEquals(10, rooms.length);
    }

    /*
    Test the number of directions in the AdventureDesign Object, which should be the proper amount from the JSON
     */
    @Test
    public void testNumberOfDirections() {
        int countDirections = 0;
        for (Room r : testDesign.getRooms()) {
            for (DirectionsOnMap ignored : r.getDirections()) {
                countDirections++;
            }
        }
        assertEquals(21, countDirections);
    }

    /*
   Test the number of items in the AdventureDesign Object, which should be the proper amount from the JSON
    */
    @Test
    public void testNumberOfItems() {
        int countItems = 0;
        for (Room r : testDesign.getRooms()) {
            for (String ignored : r.getItems()) {
                countItems++;
            }
        }
        assertEquals(22, countItems);
    }

    /*
    Test that checks whether the startingRoom is correct for the adventure
     */
    @Test
    public void testStartingRoom() {
        String startingRoom = testDesign.getStartingRoom();
        assertEquals(startingRoom, "Walter White Residence");
    }

    /*
    Test that checks whether the endingRoom is correct for the adventure
     */
    @Test
    public void testEndingRoom() {
        String startingRoom = testDesign.getEndingRoom();
        assertEquals(startingRoom, "DEA Office");
    }

    /*
    Tests a "go" method for a valid, proper input
     */
    @Test
    public void testGoValidInput() {
        Room[] rooms = testDesign.getRooms();
        Room CurrentRoom = rooms[0]; //Walter White Residence
        Room newRoom = gameEngine.updateRoom("east", testDesign);
        assertEquals("Pinkman Residence", newRoom.getName());
    }

    /*
    Tests a "go" method for an input with wrong capitalization
     */
    @Test
    public void testGoIrregularCapitalization() {
        Room newRoom = gameEngine.updateRoom("EAsT", testDesign);
        assertEquals("Pinkman Residence", newRoom.getName());
    }

    /*
    Tests a "go" method for an input with weird spacing
     */
    @Test
    public void testGoIrregularSpacing() {
        Room newRoom = gameEngine.updateRoom("     east    ", testDesign);
        assertEquals("Pinkman Residence", newRoom.getName());
    }

    /*
    Tests a "go" method for an input direction that is not in the room
     */
    @Test
    public void testGoInvalidDirection() {
        Room newRoom = gameEngine.updateRoom("West", testDesign);
        assertEquals("Walter White Residence", newRoom.getName());
    }
    /*
    Tests a "take" method for a valid item input in the room
     */
    @Test
    public void testTakeValidInput() {
        Room[] rooms = testDesign.getRooms();
        Room currentRoom = rooms[0]; //Walter White Residence
        gameEngine.takeItem("money");
        gameEngine.takeItem("hat");
        assertEquals(1, Arrays.asList(gameEngine.getUserItems()).size());
    }

    /*
    Tests a "take" method for an item input with wrong capitalization
    */
    @Test
    public void testTakeIrregularCapitalization() {
        gameEngine.takeItem("mOnEy");
        gameEngine.takeItem("HaT");
        assertEquals(2, gameEngine.getUserItems().size());
    }

    /*
    Tests a "take" method for an item input with weird spacing
    */
    @Test
    public void testTakeIrregularSpacing() {
        Room[] rooms = testDesign.getRooms();
        gameEngine.takeItem("      money");
        gameEngine.takeItem("hat   ");
        assertEquals(2, gameEngine.getUserItems().size());
    }

    /*
    Tests a "take" method for an invalid item input
    */
    @Test
    public void testTakeInvalidInput() {
        gameEngine.takeItem("hello");
        assertEquals(0, gameEngine.getUserItems().size());
    }

    /*
    Tests a "drop" method for a proper input
     */
    @Test
    public void testDropValidInput() {
        gameEngine.takeItem("money");
        gameEngine.dropItem("money");
        assertEquals(0, gameEngine.getUserItems().size());
    }
    /*
    Tests a "drop" method for wrong capitalization
     */
    @Test
    public void testDropIrregularCapitalization() {
        gameEngine.takeItem("mOnEY");
        gameEngine.dropItem("MoNEY");
        assertEquals(0, gameEngine.getUserItems().size());
    }
    /*
    Tests a "drop" method for weird spacing
     */
    @Test
    public void testDropIrregularSpacing() {
        gameEngine.takeItem("      hat");
        gameEngine.dropItem("hat      ");
        assertEquals(0, gameEngine.getUserItems().size());
    }
    /*
    Tests a "drop" method for invalid item inputs
     */
    @Test
    public void testDropInvalidInput() {
        gameEngine.takeItem(" MONEY    ");
        gameEngine.dropItem("bitcoin");
        assertEquals(1, gameEngine.getUserItems().size());
    }
}
