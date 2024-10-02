import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        // creates a charList to change the filetype to and sets up the start and end
        // lists
        int[] start = new int[2];
        int[] end = new int[2];
        char[][] mazeList = null;

        // reads the file if nothing is thrown which it gets caught if it does
        try {
            File mazeFile = new File("maze.dat");
            Scanner scan = new Scanner(mazeFile);

            // gets the rows and columns of the maze and inputs it into a charList
            String line = scan.nextLine();
            int row = Integer.parseInt(line.split("\\s+")[0]);
            int col = Integer.parseInt(line.split("\\s+")[1]);
            mazeList = new char[row][col];

            // populates the charList with all the values and finds out position to start
            // and end position while doing so
            for (int i = 0; i < row; i++) {
                String data = scan.nextLine().trim();
                for (int j = 0; j < data.length(); j++) {
                    mazeList[i][j] = data.charAt(j);
                    if (data.charAt(j) == '+') {
                        start[0] = i;
                        start[1] = j;
                    }
                    if (data.charAt(j) == '-') {
                        end[0] = i;
                        end[1] = j;
                    }
                }
                if (scan.hasNextLine() != true) {
                    break;
                }
            }
            scan.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
        mazeRun(mazeList, start[1], start[0], start);

    }

    public static void pMaze(char[][] maze) {
        for (char[] line : maze) {
            for (char c : line) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    public static boolean canMove(char[][] maze, int col, int row, int[] start) {
        if (row >= 0 && col >= 0 && row < maze.length && col < maze[0].length && (maze[row][col] == '-'
                || maze[row][col] == ' ' || (row == start[0] && col == start[1] && maze[row][col] == '+'))) {
            return true;
        }
        return false;
    }

    public static boolean solve(char[][] maze, int column, int row, int[] start) {
        if (canMove(maze, column, row, start)) {
            if (maze[row][column] == '-') {
                return true;
            }
            maze[row][column] = '+';

            // right
            if (solve(maze, column + 1, row, start)) {
                return true;
            }

            // down
            if (solve(maze, column, row + 1, start)) {
                return true;
            }

            // left
            if (solve(maze, column - 1, row, start)) {
                return true;
            }

            // up
            if (solve(maze, column, row - 1, start)) {
                return true;
            }
            // backtrack
            maze[row][column] = '.';
            return false;
        }
        return false;
    }

    public static void mazeRun(char[][] maze, int column, int row, int[] start) {
        if (solve(maze, column, row, start) == true) {
            pMaze(maze);
        }
        if (solve(maze, column, row, start) == true) {
            System.out.println("Could not find the end of the maze");
        }
    }
}
