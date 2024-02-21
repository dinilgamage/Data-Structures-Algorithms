import java.util.*;

/**
 * This class implements a modified version of Dijkstra's algorithm tailored for the Traveling Salesman Problem (TSP).
 * It finds a path that visits each city exactly once and returns to the starting city, aiming to minimize the total distance traveled.
 */
class DijkstraTSP {

    // Implement Dijkstra's algorithm to find shortest path
    private static class CustomPriorityQueue {
        private final int[] cities;
        private final int[] distances;
        private int size;

        public CustomPriorityQueue(int capacity) {
            cities = new int[capacity];
            distances = new int[capacity];
            size = 0;
        }

        public void add(int city, int distance) {
            cities[size] = city;
            distances[size] = distance;
            size++;
            heapifyUp();
        }

        public int poll() {
            int city = cities[0];
            swap(0, size - 1);
            size--;
            heapifyDown();
            return city;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        private void heapifyUp() {
            int index = size - 1;
            while (index > 0) {
                int parent = (index - 1) / 2;
                if (distances[index] < distances[parent]) {
                    swap(index, parent);
                    index = parent;
                } else {
                    break;
                }
            }
        }

        private void heapifyDown() {
            int index = 0;
            while (true) {
                int leftChild = 2 * index + 1;
                int rightChild = 2 * index + 2;
                int smallest = index;

                if (leftChild < size && distances[leftChild] < distances[smallest]) {
                    smallest = leftChild;
                }

                if (rightChild < size && distances[rightChild] < distances[smallest]) {
                    smallest = rightChild;
                }

                if (smallest != index) {
                    swap(index, smallest);
                    index = smallest;
                } else {
                    break;
                }
            }
        }

        private void swap(int i, int j) {
            int tempCity = cities[i];
            int tempDist = distances[i];
            cities[i] = cities[j];
            distances[i] = distances[j];
            cities[j] = tempCity;
            distances[j] = tempDist;
        }
    }

    public static int[] dijkstra(int[][] distanceMatrix, int startCity) {
        int n = distanceMatrix.length;
        int[] dist = new int[n];
        int[] prev = new int[n];
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
            prev[i] = -1;
        }

        dist[startCity] = 0;

        CustomPriorityQueue customPriorityQueue = new CustomPriorityQueue(n);
        customPriorityQueue.add(startCity, 0);

        while (!customPriorityQueue.isEmpty()) {
            int currentCity = customPriorityQueue.poll();

            if (visited[currentCity]) continue;
            visited[currentCity] = true;

            for (int neighbor = 0; neighbor < n; neighbor++) {
                if (currentCity == neighbor || visited[neighbor]) continue;
                int newDist = dist[currentCity] + distanceMatrix[currentCity][neighbor];
                if (newDist < dist[neighbor]) {
                    dist[neighbor] = newDist;
                    prev[neighbor] = currentCity;
                    customPriorityQueue.add(neighbor, newDist);
                }
            }
        }

        printPaths(startCity, prev, dist);

        return dist;
    }

    private static void printPaths(int startCity, int[] prev, int[] dist) {
        System.out.println("Shortest paths and distances from city A:");
        for (int i = 0; i < prev.length; i++) {
            System.out.print("To city " + (char) ('A' + i) + ": ");
            printPath(startCity, i, prev);
            System.out.println(" (Distance: " + dist[i] + ")");
        }
    }

    private static void printPath(int startCity, int currentCity, int[] prev) {
        if (currentCity == startCity) {
            System.out.print((char) ('A' + currentCity));
        } else {
            printPath(startCity, prev[currentCity], prev);
            System.out.print(" -> " + (char) ('A' + currentCity));
        }
    }

    // Implement modified Dijkstra's algorithm for TSP

    /**
     * Finds the nearest unvisited city from the current city.
     *
     * @param currentCity    The index of the current city.
     * @param visited        An array indicating whether each city has been visited.
     * @param distanceMatrix A 2D array representing the distances between each pair of cities.
     * @return The index of the nearest unvisited city, or -1 if no unvisited cities are reachable.
     */
    private static int nearestUnvisitedCity(int currentCity, boolean[] visited, int[][] distanceMatrix) {
        int shortestDistance = Integer.MAX_VALUE;
        int nearestCity = -1;
        for (int i = 0; i < distanceMatrix.length; i++) {
            // Check if city is unvisited, reachable, and not the current city itself, and update nearest city accordingly.
            if (!visited[i] && distanceMatrix[currentCity][i] < shortestDistance && distanceMatrix[currentCity][i] > 0) {
                shortestDistance = distanceMatrix[currentCity][i];
                nearestCity = i;
            }
        }
        return nearestCity;
    }

    /**
     * Solves the Traveling Salesman Problem using a modified Dijkstra's algorithm approach.
     * It iteratively selects the nearest unvisited city until all cities are visited, then returns to the starting city.
     *
     * @param distanceMatrix A 2D array where each element [i][j] represents the distance from city i to city j.
     */
    private static void solveTSP(int[][] distanceMatrix) {
        boolean[] visited = new boolean[distanceMatrix.length]; // Track visited cities
        List<String> path = new ArrayList<>(); // Store the path taken
        int currentCity = 0; // Start from the first city
        visited[currentCity] = true; // Mark the starting city as visited
        path.add("A");

        int totalDistance = 0; // Track the total distance traveled
        for (int i = 1; i < distanceMatrix.length; i++) {
            int nextCity = nearestUnvisitedCity(currentCity, visited, distanceMatrix);
            if (nextCity != -1) {
                visited[nextCity] = true; // Mark the next city as visited
                path.add(Character.toString('A' + nextCity)); // Add the next city to the path
                totalDistance += distanceMatrix[currentCity][nextCity]; // Update the total distance
                currentCity = nextCity; // Move to the next city
            }
        }
        // Complete the cycle by returning to the starting city
        totalDistance += distanceMatrix[currentCity][0];
        path.add("A"); // Add the starting city to complete the cycle

        // Print the results
        System.out.println("Shortest Path: " + String.join(" -> ", path));
        System.out.println("Total Distance: " + totalDistance);
    }

    /**
     * The main method to run the TSP solution.
     *
     *
     */
    public static void main(String[] args) {
        // Initialize a graph represented as a distance matrix
        int[][] distanceMatrix = {
                {0, 10, 15, 20, 30},
                {10, 0, 35, 25, 40},
                {15, 35, 0, 30, 50},
                {20, 25, 30, 0, 70},
                {30, 40, 50, 70, 0}
        };

        // Solve the TSP with the given distance matrix
        solveTSP(distanceMatrix);
    }
}
