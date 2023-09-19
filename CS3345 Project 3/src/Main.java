// CS3345 Project 3 - Evan Wright
import java.util.*;

class main {
    private int rows; // length and width of the following maze
    private int cols;
    public final int[][] maze;
    public static String[][] pathMaze;
    public static String listDir = " ";
    public String empty = "   "; // hidden paths or dead ends
    public String truePath = " # "; // used to show the correct path

    public main(int x, int y) {
        rows = x;
        cols = y;
        maze = new int[rows][cols];
        pathMaze = new String [rows][cols];
        buildMaze(0, 0); // start recursive calls for knocking down walls in the maze from top left point
    }

    private void buildMaze(int mazeX, int mazeY) {
        // create list of all possible directions (N, E, W, S)
        Direction[] dirs = Direction.values();
        Collections.shuffle(Arrays.asList(dirs));
        // loop through directions and knock down walls
        for (Direction dir : dirs) {
            int xCoord = mazeX + dir.xCoord; // apply bit offset to change the wallscape
            int yCoord = mazeY + dir.yCoord;
            if ((xCoord >= 0) && (xCoord < rows) && (yCoord >= 0) && (yCoord < cols) && (maze[xCoord][yCoord] == 0)) {
                // edge checking thru the given coord between the max # of rows/columns and 0
                // then recursively continue building the maze
                maze[mazeX][mazeY] += dir.bit;
                maze[xCoord][yCoord] += dir.inverse.bit;
                pathMaze[mazeX][mazeY] = " # "; // signifies the correct path
                buildMaze(xCoord, yCoord);

                // save the direction into the list
                if(dir.bit == 1)
                    listDir = "N" + listDir;
                if(dir.bit == 2)
                    listDir = "S" + listDir;
                if(dir.bit == 4)
                    listDir = "W" + listDir;
                if(dir.bit == 8)
                    listDir = "E" + listDir;

            }
        }
    }

    public void printMaze() {
        String output = "";
        for (int i = 0; i < cols; i++) {
            // top row
            for (int j = 0; j < rows; j++) {
                if((maze[j][i] & 1) == 0) { // check which walls were knocked down
                    output = "----";
                }
                else {
                    output = "-" + empty;
                }
                System.out.print(output);
            }
            System.out.println("-");
            // west-most wall
            for (int j = 0; j < rows; j++) {
                if((maze[j][i] & 4) == 0) {
                    output = "|" + empty;
                }
                else {
                    output = " " + empty;
                }
                System.out.print(output);
            }
            System.out.println("|");
        }
        // bottom row
        for (int j = 0; j < rows; j++) {
            System.out.print("----");
        }
        System.out.println("|");
    }
    public void printTruePath() {
        String output = "";
        for (int i = 0; i < cols; i++) {
            // top row
            for (int j = 0; j < rows; j++) {
                if(i == rows && j == cols) {
                    break;
                }
                if((maze[j][i] & 1) == 0) {
                    output = "----";
                }
                else {
                    output = "-" + empty;
                }
                System.out.print(output);

            }
            System.out.println("-");
            // west-most wall
            for (int j = 0; j < rows; j++) {
                if(i == rows && j == cols) {
                    break;
                }
                if((maze[j][i] & 4) == 0) {
                    output = "|" + truePath;
                }
                else {
                    output = " "  + truePath;
                }
                System.out.print(output);
            }
            System.out.println("|");
        }
        // bottom row
        for (int j = 0; j < rows; j++) {
            System.out.print("----");
        }
        System.out.println("-");
    }

    // uses bits 0000 - 1111, each bit tracking a direction
    // these bits determine which walls are open and which are closed
    private enum Direction {
        // first bit represents N, second represents S, third is W, fourth is E
        // together, numbers from 0-15 can represent all possible wall combinations
        N(1, 0, -1), S(2, 0, 1), W(4, -1, 0), E(8, 1, 0),;
        int bit;
        int xCoord;
        int yCoord;
        Direction inverse;

        static { // enables backtracking given the opposing direction
            N.inverse = S;
            S.inverse = N;
            E.inverse = W;
            W.inverse = E;
        }
        Direction(int bit, int xCoord, int yCoord) {
            this.bit = bit;
            this.xCoord = xCoord;
            this.yCoord = yCoord;
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("What would you like the length to be?");
        int rows = input.nextInt();
        System.out.println("What would you like the height to be?");
        int cols = input.nextInt();

        main maze = new main(rows, cols);
        maze.printMaze();
        System.out.println("Press '1' to show the correct path");
        if(input.nextInt() == 1) {
            System.out.println();
            maze.printTruePath();
            System.out.println(listDir);
        }
    }
}