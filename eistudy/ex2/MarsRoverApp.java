import java.util.*;

// ---- Direction Strategy ----
interface Direction {
    String getName();
    Direction turnLeft();
    Direction turnRight();
    int[] moveForward(int x, int y);
}

class North implements Direction {
    public String getName() { return "N"; }
    public Direction turnLeft() { return new West(); }
    public Direction turnRight() { return new East(); }
    public int[] moveForward(int x, int y) { return new int[]{x, y + 1}; }
}

class South implements Direction {
    public String getName() { return "S"; }
    public Direction turnLeft() { return new East(); }
    public Direction turnRight() { return new West(); }
    public int[] moveForward(int x, int y) { return new int[]{x, y - 1}; }
}

class East implements Direction {
    public String getName() { return "E"; }
    public Direction turnLeft() { return new North(); }
    public Direction turnRight() { return new South(); }
    public int[] moveForward(int x, int y) { return new int[]{x + 1, y}; }
}

class West implements Direction {
    public String getName() { return "W"; }
    public Direction turnLeft() { return new South(); }
    public Direction turnRight() { return new North(); }
    public int[] moveForward(int x, int y) { return new int[]{x - 1, y}; }
}

// ---- Composite Pattern for Grid ----
abstract class GridComponent {
    int x, y;
    GridComponent(int x, int y) { this.x = x; this.y = y; }
    public abstract boolean isObstacle();
}

class Cell extends GridComponent {
    Cell(int x, int y) { super(x, y); }
    public boolean isObstacle() { return false; }
}

class Obstacle extends GridComponent {
    Obstacle(int x, int y) { super(x, y); }
    public boolean isObstacle() { return true; }
}

class Grid {
    private int width, height;
    private Set<String> obstacles = new HashSet<>();

    Grid(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addObstacle(int x, int y) {
        obstacles.add(x + "," + y);
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public boolean hasObstacle(int x, int y) {
        return obstacles.contains(x + "," + y);
    }
}

// ---- Rover ----
class Rover {
    private int x, y;
    private Direction direction;
    private Grid grid;

    Rover(int x, int y, Direction direction, Grid grid) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.grid = grid;
    }

    public void execute(Command command) {
        command.execute(this);
    }

    public void moveForward() {
        int[] next = direction.moveForward(x, y);
        if (grid.isWithinBounds(next[0], next[1]) && !grid.hasObstacle(next[0], next[1])) {
            this.x = next[0];
            this.y = next[1];
        } else {
            System.out.println("⚠️ Movement blocked (boundary or obstacle).");
        }
    }

    public void turnLeft() { this.direction = direction.turnLeft(); }
    public void turnRight() { this.direction = direction.turnRight(); }

    public String getStatus() {
        return "Rover is at (" + x + ", " + y + ") facing " + direction.getName();
    }
}

// ---- Command Pattern ----
interface Command {
    void execute(Rover rover);
}

class MoveCommand implements Command {
    public void execute(Rover rover) { rover.moveForward(); }
}

class LeftCommand implements Command {
    public void execute(Rover rover) { rover.turnLeft(); }
}

class RightCommand implements Command {
    public void execute(Rover rover) { rover.turnRight(); }
}

// ---- Main Application ----
public class MarsRoverApp {
    public static void main(String[] args) {
        Grid grid = new Grid(10, 10);
        grid.addObstacle(2, 2);
        grid.addObstacle(3, 5);

        Rover rover = new Rover(0, 0, new North(), grid);

        // Example commands: M, M, R, M, L, M
        List<Command> commands = Arrays.asList(
            new MoveCommand(), new MoveCommand(),
            new RightCommand(),
            new MoveCommand(),
            new LeftCommand(),
            new MoveCommand()
        );

        for (Command c : commands) {
            rover.execute(c);
            System.out.println(rover.getStatus());
        }

        System.out.println("✅ Final Status: " + rover.getStatus());
    }
}
