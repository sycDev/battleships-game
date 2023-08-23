import java.util.Scanner;

/**
 * <h1>Battle Ships Game</h1>
 * <p>First, the player will choose <b>5</b> coordinates to place total of 5 ships on a <b>10 by 10</b> grid.
 * Then, the computer will deploy <b>5</b> ships on the same grid. After player and computer done deploying their 5 ships,
 * the game starts. The player and computer will <b>take turns</b> to <i>guess a coordinate to sink each other's ships.</i>
 * The game ends when either the player or computer has <b>no ships left</b>.</p>
 *
 * @author Ch'ng Sin Yi
 */

public class BattleShipsGame {
    public static int maxRow = 10; // maximum row of the grid in the map
    public static int maxColumn = 10; // maximum column of the grid in the map

    /**
     * This is the main method for the battleships game
     * @param args an array of String values
     */
    public static void main(String[] args) {
        int playerShips = 5; // total number of the player's ships
        int computerShips = 5; // total number of the computer's ships
        // to store the marking on the map display
        String[][] map = new String[BattleShipsGame.maxRow][BattleShipsGame.maxColumn];
        // to store the location of the player's ships and computer's ships
        int[][] shipCoordinate = new int[BattleShipsGame.maxRow][BattleShipsGame.maxColumn];

        System.out.println("**** Welcome to Battle Ships Game ****\n");
        System.out.println("Right now, the sea is empty.\n");

        // create a new map
        createNewMap(BattleShipsGame.maxRow, BattleShipsGame.maxColumn, map);

        // deploy player's ships
        String[][] updatedMap = deployPlayerShips(playerShips, shipCoordinate, map);
        // display the updated state of the map after deploy all the player's ships
        displayMap(BattleShipsGame.maxRow, BattleShipsGame.maxColumn, updatedMap);

        // deploy computer's ships
        deployComputerShips(computerShips, shipCoordinate);

        // battle starts and get the final number of the ships left by the player and computer
        int[] shipsLeft = startBattle(updatedMap, shipCoordinate, playerShips, computerShips);

        // game over and display the final number of the ships left by the player and computer
        System.out.println("________________________________________");
        System.out.println("Your ships: " + shipsLeft[0] + " | Computer Ships: " + shipsLeft[1]);

        if (shipsLeft[0] == 0) {
            // computer win the battle if player has no ship left
            System.out.println("Computer won the battle");
        } else if (shipsLeft[1] == 0) {
            // player win the battle if computer has no ship left
            System.out.println("Hooray! You win the battle :)");
        }
    }

    /**
     * This method is used to draw a map when the game starts
     * @param maxRow The maximum row of the grid in the map
     * @param maxColumn The maximum row of the grid in the map
     * @param map The map of the game
     */
    //
    public static void createNewMap(int maxRow, int maxColumn, String[][] map) {
        // top row of number that indicates the y coordinate
        System.out.print("   ");
        for (int i = 0; i < maxColumn; i++) {
            System.out.print(i);
        }
        System.out.println();

        // loop through the rows of the grid
        for (int row = 0; row < maxRow; row++) {
            System.out.print(row);
            System.out.print(" |");
            // loop through the columns of the current row
            for (int col = 0; col < maxColumn; col++) {
                map[row][col] = " "; // set all the grid of map to empty space
                System.out.print(map[row][col]);
            }
            System.out.print("| ");
            System.out.print(row);
            System.out.println(" ");
        }

        // bottom row of number that indicates the y coordinate
        System.out.print("   ");
        for (int i = 0; i < maxColumn; i++) {
            System.out.print(i);
        }
    }

    /**
     * This method is used to deploy player's ships
     * @param playerShip The total number of player's ships
     * @param shipCoordinate The location of the player's ships and computer's ships
     * @param map The current state of the map display
     * @return The updated state of the map after deploy all the player's ships
     */
    public static String[][] deployPlayerShips(int playerShip, int[][] shipCoordinate, String[][] map) {
        System.out.println("\nDeploy your ships: ");

        // loop through the process of deploying ships until meet the total number of ships required
        for (int i = 1; i <= playerShip; i++) {
            System.out.print("Enter X coordinate for your " + i + ". ship: ");
            int x = getInputX();
            System.out.print("Enter Y coordinate for your " + i + ". ship: ");
            int y = getInputY();

            // when player choose to place two or more ships on the same location, prompt the player to choose again
            while (map[x][y].equals("@")) {
                System.out.println("You already placed a ship at there, please choose another coordinate for your " + i + ". ship");
                System.out.print("Enter X coordinate for your " + i + ". ship: ");
                x = getInputX();
                System.out.print("Enter Y coordinate for your " + i + ". ship: ");
                y = getInputY();
            }

            shipCoordinate[x][y] = 1; // store the coordinate of the player's ship
            map[x][y] = "@"; // mark the player's ship on the map
        }

        return map;
    }

    /**
     * This method is used to display the map
     * @param maxRow The maximum row of the grid in the map
     * @param maxColumn The maximum column of the grid in the map
     * @param updatedMap The updated state of the map display
     */
    public static void displayMap(int maxRow, int maxColumn, String[][] updatedMap) {
        // top row of number that indicates the y coordinate
        System.out.println();
        System.out.print("   ");
        for (int i = 0; i < maxColumn; i++) {
            System.out.print(i);
        }
        System.out.println();

        // loop through the rows of the grid
        for (int row = 0; row < maxRow; row++) {
            System.out.print(row);
            System.out.print(" |");
            // loop through the columns of the current row
            for (int col = 0; col < maxColumn; col++) {
                System.out.print(updatedMap[row][col]);
            }
            System.out.print("| ");
            System.out.print(row);
            System.out.println(" ");
        }

        // bottom row of number that indicates the y coordinate
        System.out.print("   ");
        for (int i = 0; i < maxColumn; i++) {
            System.out.print(i);
        }
        System.out.println();
    }

    /**
     * This method is used to deploy computer's ships
     * @param computerShips The total number of the computer's ships
     * @param shipCoordinate The location of the player's ships and computer's ships
     */
    public static void deployComputerShips(int computerShips, int[][] shipCoordinate) {
        System.out.println("Computer is deploying ships");

        // generate random number between the bounds of the map to place the computer's ship until all ships are placed
        for (int i = 1; i <= computerShips; i++) {
            int x = (int) (Math.random() * BattleShipsGame.maxRow);
            int y = (int) (Math.random() * BattleShipsGame.maxColumn);

            // when there is duplicate computer's ship or existing player's ship, regenerate random coordinate
            while (shipCoordinate[x][y] == 2 || shipCoordinate[x][y] == 1) {
                x = (int) (Math.random() * BattleShipsGame.maxRow);
                y = (int) (Math.random() * BattleShipsGame.maxColumn);
            }

            shipCoordinate[x][y] = 2; // store the coordinate of the computer's ships
            System.out.println(i + ". ship DEPLOYED");
        }
    }

    /**
     * This method is used to start the battle after player and computer done deploy all their ships
     * @param updatedMap The updated state of the map display
     * @param shipCoordinate The location of the player's ships and computer's ships
     * @param playerShips The number of the player's ships
     * @param computerShips The number of the computer's ships
     * @return An array of the number of player's ships left and computer's ships left
     */
    public static int[] startBattle(String[][] updatedMap, int[][] shipCoordinate, int playerShips, int computerShips) {
        // to store the number of ships left by the player and computer
        int[] shipsLeft = new int[2];
        // to store the coordinates that player had guessed before
        int[][] playerGuessed = new int[BattleShipsGame.maxRow][BattleShipsGame.maxColumn];
        // to store the coordinate that computer had guessed before
        int[][] computerGuessed = new int[BattleShipsGame.maxRow][BattleShipsGame.maxColumn];
        shipsLeft[0] = playerShips; // assign the total number of player's ships
        shipsLeft[1] = computerShips; // assign the total number of computer's ships

        // repeat the turns until either the player left no ship or the computer left no ship
        do {
            shipsLeft = playerTurn(updatedMap, shipsLeft[0], shipsLeft[1], shipCoordinate, playerGuessed);
            shipsLeft = computerTurn(updatedMap, shipsLeft[0], shipsLeft[1], shipCoordinate, computerGuessed);
            displayMap(BattleShipsGame.maxRow, BattleShipsGame.maxColumn, updatedMap);
            System.out.println("\nYour ships: " + shipsLeft[0] + " | Computer Ships: " + shipsLeft[1]);
        } while (shipsLeft[0] > 0 && shipsLeft[1] > 0);

        return shipsLeft;
    }

    /**
     * This method is used to ask player guess the coordinate of where to attack when the player's turn
     * @param map The current state of the map display
     * @param playerShips The number of the player's ships
     * @param computerShips The number of the computer's ships
     * @param shipCoordinate The location of the player's ships and computer's ships
     * @param playerGuessed The coordinate where the player had guessed before
     * @return An array of the number of player's ships left and computer's ships left
     */
    public static int[] playerTurn(String[][] map, int playerShips, int computerShips, int[][] shipCoordinate, int[][] playerGuessed) {
        int[] shipsLeft = new int[2];
        shipsLeft[0] = playerShips; // assign the current number of player's ships
        shipsLeft[1] = computerShips; // assign the current number of computer's ships

        System.out.println("________________________________________");
        System.out.println("YOUR TURN");

        System.out.print("Enter X coordinate: ");
        int x = getInputX();
        System.out.print("Enter Y coordinate: ");
        int y = getInputY();

        // when there is duplicate guessed by player, ask player to input another coordinate
        while (playerGuessed[x][y] == 1) {
            System.out.println("The coordinate has been guessed by you, please choose another coordinate:");
            System.out.print("Enter X coordinate: ");
            x = getInputX();
            System.out.print("Enter Y coordinate: ");
            y = getInputY();
        }

        playerGuessed[x][y] = 1; // store the coordinate that player had guessed

        // possible results of a valid guess
        if (shipCoordinate[x][y] == 2) {
            // if the player guessed the coordinate of computer's ships correctly
            System.out.println("Boom! You sunk the ship!");
            map[x][y] = "!";
            shipCoordinate[x][y] = 0;
            shipsLeft[1]--;
        } else if (shipCoordinate[x][y] == 1) {
            // if the player guessed the coordinate of his/her own ships
            System.out.println("Oh no, you sunk your own ship :(");
            map[x][y] = "x";
            shipCoordinate[x][y] = 0;
            shipsLeft[0]--;
        } else {
            // if the player guessed the coordinate that don't have any ship
            System.out.println("Sorry, you missed");
            // mark the coordinate on the map as "-" only when there is no ship that had already sunk
            if (!"!".equals(map[x][y]) && !"x".equals(map[x][y])) {
                map[x][y] = "-";
            }
        }

        return shipsLeft;
    }

    /**
     * This method is used to generate random coordinate of where to attack when the computer's turn
     * @param map The current state of the map display
     * @param playerShips The number of the player's ships
     * @param computerShips The number of the computer's ships
     * @param shipCoordinate The location of the player's ships and computer's ships
     * @param computerGuessed The coordinate where the computer had guessed before
     * @return An array of the number of player's ships left and computer's ships left
     */
    public static int[] computerTurn(String[][] map, int playerShips, int computerShips, int[][] shipCoordinate, int[][] computerGuessed) {
        int[] shipsLeft = new int[2];
        shipsLeft[0] = playerShips; // assign the current number of player's ships
        shipsLeft[1] = computerShips; // assign the current number of computer's ships

        System.out.println("COMPUTER'S TURN");

        // generate coordinate of computer's guess randomly that is within the bounds of the map
        int x = (int) (Math.random() * BattleShipsGame.maxRow);
        int y = (int) (Math.random() * BattleShipsGame.maxRow);

        // when there is duplicate guessed by computer, generate another coordinate randomly that is within the bounds of the map
        while (computerGuessed[x][y] == 1) {
            x = (int) (Math.random() * BattleShipsGame.maxRow);
            y = (int) (Math.random() * BattleShipsGame.maxColumn);
        }

        computerGuessed[x][y] = 1; // mark the coordinate that guessed by the computer

        // possible results of a valid guess
        if (shipCoordinate[x][y] == 1) {
            // when computer guessed the coordinate of the player's ship correctly
            System.out.println("The Computer sunk one of your ships!");
            map[x][y] = "x";
            shipCoordinate[x][y] = 0;
            shipsLeft[0]--;
        } else if (shipCoordinate[x][y] == 2) {
            // when computer guessed the coordinate of its own ship
            System.out.println("The Computer sunk one of its own ships");
            map[x][y] = "!";
            shipCoordinate[x][y] = 0;
            shipsLeft[1]--;
        } else {
            // when computer guessed the coordinate that don't have any ship
            System.out.println("Computer missed");
        }

        return shipsLeft;
    }

    /**
     * This is a method to get the input of X coordinate from the player and also checking for the X coordinate entered
     * by the player cannot exceed the bounds of the map.
     * @return an integer value of X coordinate from player input
     */
    public static int getInputX() {
        Scanner input = new Scanner(System.in);
        int x = input.nextInt();

        // re-prompt until the player enters a valid guess that the X coordinate cannot exceed the bounds of the map
        while (x < 0 || x >= BattleShipsGame.maxRow) {
            System.out.print("Please enter a number between 0 and " + (BattleShipsGame.maxRow - 1) + " again: ");
            x = input.nextInt();
        }

        return x;
    }

    /**
     * This is a method to get the input of Y coordinate from the player and also checking for the Y coordinate entered
     * by the player cannot exceed the bounds of the map.
     * @return an integer value of Y coordinate from player input
     */
    public static int getInputY() {
        Scanner input = new Scanner(System.in);
        int y = input.nextInt();

        // re-prompt until the player enters a valid guess that the Y coordinate cannot exceed the bounds of the map
        while (y < 0 || y >= BattleShipsGame.maxRow) {
            System.out.print("Please enter a number between 0 and " + (BattleShipsGame.maxRow - 1) + " again: ");
            y = input.nextInt();
        }

        return y;
    }
}