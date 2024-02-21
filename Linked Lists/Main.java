public class Main {
    public static void main(String[] args) {

        //Add Test
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);

        System.out.println("Elements added: " + linkedList.toString());
        printTestResult("Add elements", linkedList.toString().equals("[1, 2, 3]"));

        //Get Test
        System.out.println("Element at index 1: " + linkedList.get(1));
        printTestResult("Get element at index 1", linkedList.get(1) == 2);

        //Size Test
        System.out.println("Size of List: " + linkedList.size());
        printTestResult("Size is 3", linkedList.size() == 3);

        //Remove Test
        linkedList.remove(1);
        System.out.println("List after removing element at index 1: " + linkedList.toString());
        printTestResult("Remove element at index 1", linkedList.toString().equals("[1, 3]"));


    }

    private static void printTestResult(String testName, boolean result) {
        System.out.println(testName + ": " + (result ? "PASS" : "FAIL"));
    }
}

