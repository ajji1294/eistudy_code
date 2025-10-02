import java.util.Scanner;

class Rocket {
    private int stage; // 0: pre-launch, 1: stage1, 2: stage2
    private int fuel; // in percent
    private int altitude; // km
    private int speed; // km/h
    private boolean preLaunchDone;
    private boolean launched;

    public Rocket() {
        stage = 0;
        fuel = 100;
        altitude = 0;
        speed = 0;
        preLaunchDone = false;
        launched = false;
    }

    public void startChecks() {
        preLaunchDone = true;
        System.out.println("All systems are 'Go' for launch.");
    }

    public void launch() {
        if (!preLaunchDone) {
            System.out.println("⚠️ Pre-launch checks not done. Run start_checks first.");
            return;
        }
        launched = true;
        stage = 1;
        System.out.println("Launch initiated!");
        updateState(1);
    }

    public void fastForward(int seconds) {
        if (!launched) {
            System.out.println("⚠️ Launch not initiated yet. Run launch first.");
            return;
        }
        updateState(seconds);
    }

    private void updateState(int seconds) {
        for (int i = 1; i <= seconds; i++) {
            if (fuel <= 0) {
                System.out.println("Mission Failed due to insufficient fuel.");
                break;
            }

            // Simple simulation rules
            fuel -= 2; // 2% fuel per second
            altitude += 10; // 10 km per second
            speed += 1000; // 1000 km/h per second

            // Stage separation at 50 km altitude
            if (stage == 1 && altitude >= 50) {
                stage = 2;
                System.out.println("Stage 1 complete. Separating stage. Entering Stage 2.");
            }

            // Orbit achieved at 200 km
            if (altitude >= 200) {
                System.out.println("Orbit achieved! Mission Successful.");
                break;
            }

            System.out.println("Stage: " + stage + ", Fuel: " + fuel + "%, Altitude: " + altitude + " km, Speed: " + speed + " km/h");
        }
    }
}

// Main Application
public class RocketLaunchSimulator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Rocket rocket = new Rocket();

        System.out.println("Rocket Launch Simulator");
        System.out.println("Commands: start_checks, launch, fast_forward X, exit");

        while (true) {
            System.out.print("> ");
            String input = sc.nextLine();
            String[] parts = input.split(" ");

            if (parts[0].equalsIgnoreCase("exit")) {
                System.out.println("Exiting simulator.");
                break;
            } else if (parts[0].equalsIgnoreCase("start_checks")) {
                rocket.startChecks();
            } else if (parts[0].equalsIgnoreCase("launch")) {
                rocket.launch();
            } else if (parts[0].equalsIgnoreCase("fast_forward")) {
                if (parts.length != 2) {
                    System.out.println("Usage: fast_forward X");
                } else {
                    try {
                        int seconds = Integer.parseInt(parts[1]);
                        rocket.fastForward(seconds);
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Invalid number of seconds.");
                    }
                }
            } else {
                System.out.println("⚠️ Unknown command.");
            }
        }

        sc.close();
    }
}
