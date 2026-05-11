import java.util.*;

public class A_star {

    // Goal state taken from user
    static int[][] goal = new int[3][3];

    // Heuristic Function: Misplaced Tiles
    static int getH(int[][] s) {

        int h = 0;

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                if (s[i][j] != 0 && s[i][j] != goal[i][j]) {

                    h++;
                }
            }
        }

        return h;
    }

    // Node Class
    static class Node {

        int[][] mat;
        int g, f;

        Node(int[][] m, int g) {

            this.mat = m;
            this.g = g;

            // f(n) = g(n) + h(n)
            this.f = g + getH(m);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int[][] start = new int[3][3];

        // Input Initial State
        System.out.println("Enter Initial State (0 for blank):");

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                start[i][j] = sc.nextInt();
            }
        }

        // Input Goal State
        System.out.println("Enter Goal State:");

        for (int i = 0; i < 3; i++) {

            for (int j = 0; j < 3; j++) {

                goal[i][j] = sc.nextInt();
            }
        }

        // Priority Queue
        PriorityQueue<Node> pq =
                new PriorityQueue<>(Comparator.comparingInt(n -> n.f));

        // Visited states
        Set<String> visited = new HashSet<>();

        // Add initial node
        pq.add(new Node(start, 0));

        while (!pq.isEmpty()) {

            // Remove node with minimum f(n)
            Node curr = pq.poll();

            String stateStr = Arrays.deepToString(curr.mat);

            if (visited.contains(stateStr))
                continue;

            visited.add(stateStr);

            // Print current state
            System.out.println("\n--- g = " + curr.g +
                    " , h = " + (curr.f - curr.g) +
                    " , f = " + curr.f + " ---");

            for (int[] row : curr.mat) {

                System.out.println(Arrays.toString(row));
            }

            // Goal check
            if (Arrays.deepEquals(curr.mat, goal)) {

                System.out.println("\nSUCCESS: Goal State Reached!");
                break;
            }

            // Find blank space (0)
            int x = 0, y = 0;

            for (int i = 0; i < 3; i++) {

                for (int j = 0; j < 3; j++) {

                    if (curr.mat[i][j] == 0) {

                        x = i;
                        y = j;
                    }
                }
            }

            // Possible moves
            int[][] moves = {
                    {1, 0},   // Down
                    {-1, 0},  // Up
                    {0, 1},   // Right
                    {0, -1}   // Left
            };

            // Generate child states
            for (int[] m : moves) {

                int nx = x + m[0];
                int ny = y + m[1];

                // Check valid move
                if (nx >= 0 && nx < 3 && ny >= 0 && ny < 3) {

                    int[][] nextMat = new int[3][3];

                    // Copy matrix
                    for (int i = 0; i < 3; i++) {

                        nextMat[i] = curr.mat[i].clone();
                    }

                    // Swap blank tile
                    nextMat[x][y] = nextMat[nx][ny];
                    nextMat[nx][ny] = 0;

                    // Add new state
                    pq.add(new Node(nextMat, curr.g + 1));
                }
            }
        }

        sc.close();
    }
}