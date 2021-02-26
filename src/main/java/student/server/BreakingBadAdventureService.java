package student.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import student.adventure.AdventureDesign;
import student.adventure.DirectionsOnMap;
import student.adventure.GameEngine;
import student.adventure.Room;
import student.server.AdventureException;
import student.server.AdventureService;
import student.server.Command;
import student.server.GameStatus;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * This class is used for the API service. It is used to create, destroy, and update the gameEngine
 * using a web input
 */
public class BreakingBadAdventureService implements AdventureService {
    private static int id;
    private final Map<Integer, GameEngine> gameEngineMap = new HashMap<>();
    File file = new File("src/main/resources/BreakingBad.json");

    /**
     * Resets the state of the game
     */
    @Override
    public void reset() {
        id = 0;
    }

    /**
     * Creates a newGame, incrementing the id, creating a new GameEngine, and storing it into the Map
     * of id's and Game Engines
     *
     * @return
     * @throws AdventureException
     */
    @Override
    public int newGame() throws AdventureException {
        id++;
        GameEngine gameEngine = new GameEngine(file);
        gameEngineMap.put(id, gameEngine);
        return id;
    }

    /**
     * Returns the game status of the API. This includes checking for errors, storing the id, items,
     * room display, image URL, and more
     *
     * @param id the instance id
     * @return gameStatus - the status of the game
     */
    @Override
    public GameStatus getGame(int id) {
        GameEngine gameEngine = gameEngineMap.get(id);
        Map<String, List<String>> currentCommands = getInputs(gameEngine);
        AdventureState state = new AdventureState();
        GameStatus gameStatus = new GameStatus(false, id, gameEngine.getMessage(), gameEngine.getImageURL(), "", state,
                currentCommands);
        return gameStatus;
    }

    /**
     * Destroys the game engine stored in the instance id and returns true. If either are null,
     * it returns false
     *
     * @param id the instance id
     * @return boolean
     */
    @Override
    public boolean destroyGame(int id) {
        GameEngine gameEngineToRemove = gameEngineMap.remove(id);
        if (gameEngineToRemove == null) {
            return false;
        }
        return true;
    }

    /**
     * Executes the current command for the game engine.
     *
     * @param id      the instance id
     * @param command the issued command
     */
    @Override
    public void executeCommand(int id, Command command) {
        GameEngine gameEngine = gameEngineMap.get(id);
        String currentCommand = command.getCommandName() + " " + command.getCommandValue();
        boolean reachedEnd = gameEngine.inputFromWeb(currentCommand);
    }

    /**
     * Creates a map of command names and values, and stores the current state of the room
     * into the map based off of the command name
     *
     * @param currentEngine - the gameEngine identified by the ID
     * @return currentCommands - the list of commands that the player has in the certain room
     */
    public Map<String, List<String>> getInputs(GameEngine currentEngine) {
        ArrayList<String> emptyList = new ArrayList<>();
        Map<String, List<String>> currentCommands = new HashMap<>();
        currentCommands.put("go", currentEngine.getDirections());
        currentCommands.put("drop", currentEngine.getUserItems());
        currentCommands.put("take", currentEngine.getRoomItems());
        currentCommands.put("examine", emptyList);
        currentCommands.put("history", emptyList);
        return currentCommands;
    }

    /**
     * Not used in this assignment
     * @return null
     */
    @Override
    public SortedMap<String, Integer> fetchLeaderboard() {
        return null;
    }
}
