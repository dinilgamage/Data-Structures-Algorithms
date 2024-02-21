/**
 * Implements Dijkstra's algorithm using a custom priority queue.
 */
public class Dijkstra {

    /**
     * A custom priority queue class for managing cities and their distances.
     */
    private static class CustomPriorityQueue {
        private final int[] cities; // Holds the cities as integers.
        private final int[] distances; // Holds the corresponding distances.
        private int size; // Current size of the priority queue.

        /**
         * Constructs a CustomPriorityQueue with a specified capacity.
         *
         * @param capacity The initial capacity of the queue.
         */
        public CustomPriorityQueue(int capacity) {
            cities = new int[capacity];
            distances = new int[capacity];
            size = 0;
        }

        /**
         * Adds a city and its distance to the queue.
         *
         * @param city     The city to add.
         * @param distance The distance of the city.
         */
        public void add(int city, int distance) {
            cities[size] = city;
            distances[size] = distance;
            size++;
            heapifyUp();
        }

        /**
         * Removes and returns the city with the smallest distance.
         *
         * @return The city with the smallest distance.
         */
        public int poll() {
            int city = cities[0];
            swap(0, size - 1);
            size--;
            heapifyDown();
            return city;
        }

        /**
         * Checks if the queue is empty.
         *
         * @return true if the queue is empty, false otherwise.
         */
        public boolean isEmpty() {
            return size == 0;
        }

        /**
         * Adjusts the heap upwards to maintain the min-heap property after an addition.
         * This method is called after a new element is added to the heap at the end (bottom) of the tree structure.
         * It compares the newly added element with its parent and swaps them if the child's distance is smaller than the parent's distance,
         * ensuring that the smallest distance is always at the top of the heap. The process is repeated until the newly added element
         * is either at the top of the heap or larger than its parent, maintaining the min-heap property throughout the heap.
         */
        private void heapifyUp() {
            int index = size - 1; // Start with the last element added.
            while (index > 0) { // Continue until the element is at the root (index 0).
                int parent = (index - 1) / 2; // Calculate parent's index.
                if (distances[index] < distances[parent]) { // If child is less than parent, swap them.
                    swap(index, parent);
                    index = parent; // Move up to the parent's index for next iteration.
                } else {
                    break; // The heap property is satisfied.
                }
            }
        }


        /**
         * Adjusts the heap downwards to maintain the min-heap property after the removal of the top element.
         * This method is called after the top element is removed and the last element is placed at the top.
         * It compares the new top element with its children and swaps it with the smaller child if necessary,
         * ensuring the min-heap property is maintained. The process is repeated until the element is either
         * smaller than both its children or has reached a leaf position, thus maintaining the min-heap structure.
         */
        private void heapifyDown() {
            int index = 0; // Start from the top of the heap.
            while (true) {
                int leftChild = 2 * index + 1; // Calculate left child's index.
                int rightChild = 2 * index + 2; // Calculate right child's index.
                int smallest = index; // Assume the current index holds the smallest distance.

                // Find the smallest distance among the current element and its children.
                if (leftChild < size && distances[leftChild] < distances[smallest]) {
                    smallest = leftChild; // Update if left child is smaller.
                }
                if (rightChild < size && distances[rightChild] < distances[smallest]) {
                    smallest = rightChild; // Update if right child is smaller.
                }

                if (smallest != index) { // If a smaller child is found, swap and continue.
                    swap(index, smallest);
                    index = smallest; // Move down to the smallest child's index for the next iteration.
                } else {
                    break; // The heap property is maintained.
                }
            }
        }


        /**
         * Swaps two elements in the heap. This is a utility method used by both heapifyUp and heapifyDown methods
         * to swap the positions of two elements in the heap's underlying arrays (cities and distances).
         * Swapping is essential for maintaining the heap's order property during the heapify operations.
         *
         * @param i The index of the first element to be swapped.
         * @param j The index of the second element to be swapped.
         */
        private void swap(int i, int j) {
            int tempCity = cities[i]; // Temporary storage for the first city.
            int tempDist = distances[i]; // Temporary storage for the first distance.
            cities[i] = cities[j]; // Swap cities.
            distances[i] = distances[j]; // Swap distances.
            cities[j] = tempCity; // Complete the swap for cities.
            distances[j] = tempDist; // Complete the swap for distances.
        }

    }

    /**
     * Executes Dijkstra's algorithm to find the shortest paths from a start city to all other cities in a graph.
     * The graph is represented as a distance matrix, where the value at distanceMatrix[i][j] represents the cost
     * to travel from city i to city j. If there is no direct path between two cities, it should be represented as
     * Integer.MAX_VALUE. The algorithm returns an array of the shortest distances from the start city to every other city.
     *
     * @param distanceMatrix The graph represented as a distance matrix, where each cell [i][j] holds the distance
     *                       from city i to city j. The distance from a city to itself is 0, and Integer.MAX_VALUE
     *                       represents an absence of direct path between two cities.
     * @param startCity      The index of the starting city from which distances are calculated.
     * @return An array where each element at index i represents the shortest distance from the start city to city i.
     */
    public static int[] dijkstra(int[][] distanceMatrix, int startCity) {
        int n = distanceMatrix.length; // Number of cities in the graph.
        int[] dist = new int[n]; // Holds the shortest distances from startCity to every other city.
        int[] prev = new int[n]; // Tracks the previous city in the shortest path from startCity to every other city.
        boolean[] visited = new boolean[n]; // Keeps track of which cities have been visited during the algorithm's execution.

        // Initialize distances to all cities as infinity and previous cities as -1 (indicating no previous city).
        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
            prev[i] = -1;
        }
        // Distance from the start city to itself is always 0.
        dist[startCity] = 0;

        // Initialize a custom priority queue to manage cities based on their current shortest distances.
        CustomPriorityQueue customPriorityQueue = new CustomPriorityQueue(n);
        // Add the start city to the queue with a distance of 0.
        customPriorityQueue.add(startCity, 0);

        // Continue the algorithm until all cities have been processed.
        while (!customPriorityQueue.isEmpty()) {
            // Poll the city with the shortest distance from the priority queue.
            int currentCity = customPriorityQueue.poll();

            // Skip this city if it has already been visited to avoid processing it again.
            if (visited[currentCity]) continue;
            // Mark the current city as visited.
            visited[currentCity] = true;

            // Iterate through all possible neighbors of the current city.
            for (int neighbor = 0; neighbor < n; neighbor++) {
                // Skip if the neighbor is the current city itself or has already been visited.
                if (currentCity == neighbor || visited[neighbor]) continue;

                // Calculate the new distance to this neighbor through the current city.
                int newDist = dist[currentCity] + distanceMatrix[currentCity][neighbor];

                // If the new distance is shorter, update the distance and previous city for this neighbor.
                if (newDist < dist[neighbor]) {
                    dist[neighbor] = newDist;
                    prev[neighbor] = currentCity;
                    // Add the neighbor to the priority queue with its updated distance.
                    customPriorityQueue.add(neighbor, newDist);
                }
            }
        }

        // Optionally, print paths can be called to visually represent the shortest paths from the start city.
        printPaths(startCity, prev, dist);

        // Return the array of shortest distances from the start city to all other cities.
        return dist;
    }


    /**
     * Prints the shortest paths from the start city to all other cities.
     *
     * @param startCity The starting city.
     * @param prev      The array of previous cities in the path.
     * @param dist      The distances to each city.
     */
    private static void printPaths(int startCity, int[] prev, int[] dist) {
        System.out.println("Shortest paths and distances from city A:");
        for (int i = 0; i < prev.length; i++) {
            System.out.print("To city " + (char) ('A' + i) + ": ");
            printPath(startCity, i, prev);
            System.out.println(" (Distance: " + dist[i] + ")");
        }
    }

    // Recursively prints the path from the start city to a given city.
    private static void printPath(int startCity, int currentCity, int[] prev) {
        if (currentCity == startCity) {
            System.out.print((char) ('A' + currentCity));
        } else {
            printPath(startCity, prev[currentCity], prev);
            System.out.print(" -> " + (char) ('A' + currentCity));
        }
    }

    public static void main(String[] args) {
        // Example usage of the Dijkstra algorithm.
        int[][] distanceMatrix = {
                {0, 10, 15, 20, 30},
                {10, 0, 35, 25, 40},
                {15, 35, 0, 30, 50},
                {20, 25, 30, 0, 70},
                {30, 40, 50, 70, 0}
        };

        int startCity = 0; // Starting city.
        int[] shortestDistances = dijkstra(distanceMatrix, startCity);
    }
}
