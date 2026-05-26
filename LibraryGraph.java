import java.util.*;

public class LibraryGraph {

    private Map<String, List<String>> graph;

    public LibraryGraph() {
        graph = new HashMap<>();
    }

    // Add Book
    public void addBook(String book) {

        if (!graph.containsKey(book)) {
            graph.put(book, new ArrayList<>());
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Book already exists.");
        }
    }

    // Add Connection Between Books
    public void addConnection(String book1, String book2) {

        if (graph.containsKey(book1) && graph.containsKey(book2)) {

            graph.get(book1).add(book2);
            graph.get(book2).add(book1);

            System.out.println("Connection added successfully.");

        } else {

            System.out.println("One or both books not found.");
        }
    }

    // Display Graph
    public void displayGraph() {

        if (graph.isEmpty()) {
            System.out.println("Library graph is empty.");
            return;
        }

        System.out.println("\nLibrary Book Network:");

        for (String book : graph.keySet()) {

            System.out.print(book + " -> ");

            for (String connectedBook : graph.get(book)) {
                System.out.print(connectedBook + " ");
            }

            System.out.println();
        }
    }

    // BFS Traversal
    public void bfs(String startBook) {

        if (!graph.containsKey(startBook)) {
            System.out.println("Book not found.");
            return;
        }

        Set<String> visited = new HashSet<>();

        Queue<String> queue = new LinkedList<>();

        visited.add(startBook);

        queue.add(startBook);

        System.out.println("\nBFS Traversal:");

        while (!queue.isEmpty()) {

            String book = queue.poll();

            System.out.println(book);

            for (String neighbor : graph.get(book)) {

                if (!visited.contains(neighbor)) {

                    visited.add(neighbor);

                    queue.add(neighbor);
                }
            }
        }
    }

    // DFS Traversal
    public void dfs(String startBook) {

        if (!graph.containsKey(startBook)) {
            System.out.println("Book not found.");
            return;
        }

        Set<String> visited = new HashSet<>();

        System.out.println("\nDFS Traversal:");

        dfsHelper(startBook, visited);
    }

    private void dfsHelper(String book, Set<String> visited) {

        visited.add(book);

        System.out.println(book);

        for (String neighbor : graph.get(book)) {

            if (!visited.contains(neighbor)) {

                dfsHelper(neighbor, visited);
            }
        }
    }

    // Search by Book Name
    public void searchBook(String bookName) {

        if (graph.containsKey(bookName)) {
            System.out.println("Book found in library.");
        } else {
            System.out.println("Book not found.");
        }
    }

    // Count Books
    public void countBooks() {

        System.out.println("Total Books in Library: " + graph.size());
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        LibraryGraph library = new LibraryGraph();

        int choice;

        do {

            System.out.println("\n===== LibraryNet Graph System =====");

            System.out.println("1. Add Book");
            System.out.println("2. Add Connection Between Books");
            System.out.println("3. Display Book Network");
            System.out.println("4. BFS Traversal");
            System.out.println("5. DFS Traversal");
            System.out.println("6. Search Book by Name");
            System.out.println("7. Count Books");
            System.out.println("8. Exit");

            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            sc.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter Book Name: ");

                    String book = sc.nextLine();

                    library.addBook(book);

                    break;

                case 2:

                    System.out.print("Enter First Book Name: ");

                    String book1 = sc.nextLine();

                    System.out.print("Enter Second Book Name: ");

                    String book2 = sc.nextLine();

                    library.addConnection(book1, book2);

                    break;

                case 3:

                    library.displayGraph();

                    break;

                case 4:

                    System.out.print("Enter Starting Book for BFS: ");

                    String bfsBook = sc.nextLine();

                    library.bfs(bfsBook);

                    break;

                case 5:

                    System.out.print("Enter Starting Book for DFS: ");

                    String dfsBook = sc.nextLine();

                    library.dfs(dfsBook);

                    break;

                case 6:

                    System.out.print("Enter Book Name to Search: ");

                    String searchBook = sc.nextLine();

                    library.searchBook(searchBook);

                    break;

                case 7:

                    library.countBooks();

                    break;

                case 8:

                    System.out.println("Exiting system...");

                    break;

                default:

                    System.out.println("Invalid choice.");
            }

        } while (choice != 8);

        sc.close();
    }
}