package student;

import org.glassfish.grizzly.http.server.HttpServer;
import student.adventure.AdventureDesign;
import student.adventure.GameEngine;
import student.server.AdventureResource;
import student.server.AdventureServer;
import student.server.BreakingBadAdventureService;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Main class that uses a scanner input to call methods, and is used as a UI. Commands include "go", "take",
 * "drop", "examine", and more. Accounts for spelling and spacing errors. This class also stores the server
 * for the API.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        HttpServer server = AdventureServer.createServer(AdventureResource.class);
        server.start();
    }
}
