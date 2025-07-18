import java.util.Scanner;

public class Recursion{
    static Scanner kb = new Scanner(System.in);
    // Returns the number of tilings of a 2×n board using L-trominoes and 1×1 squares
    // with the constraint that no two black 1×1 squares are stacked.
    public static long countTilings(int n) {
        if (n == 0) return 1; // base case: empty board
        if (n == 1) return 3; // base case: 3 valid configurations

        long prev2 = 1; // T[0]
        long prev1 = 3; // T[1]
        long current = 0;

        for (int i = 2; i <= n; i++) {
            current = 3 * prev1 + 4 * prev2;
            prev2 = prev1;
            prev1 = current;
        }

        return current;
    }

    public static void main(String[] args) {
        System.out.println("How many columns do you want?");
        int n = kb.nextInt();
         // Example input
        System.out.println("Number of tilings for 2×" + n + " board: " + countTilings(n));
    }
}

