import java.util.Scanner;

class BTreeNode {

    int t;
    int n;
    int keys[];
    String bookNames[];
    BTreeNode children[];
    boolean leaf;

    public BTreeNode(int t, boolean leaf) {

        this.t = t;
        this.leaf = leaf;

        keys = new int[2 * t - 1];
        bookNames = new String[2 * t - 1];

        children = new BTreeNode[2 * t];

        n = 0;
    }

    public void traverse() {

        int i;

        for (i = 0; i < n; i++) {

            if (!leaf)
                children[i].traverse();

            System.out.println("Book ID: " + keys[i] +
                    " | Book Name: " + bookNames[i]);
        }

        if (!leaf)
            children[i].traverse();
    }

    public BTreeNode search(int key) {

        int i = 0;

        while (i < n && key > keys[i])
            i++;

        if (i < n && keys[i] == key)
            return this;

        if (leaf)
            return null;

        return children[i].search(key);
    }

    public void insertNonFull(int key, String name) {

        int i = n - 1;

        if (leaf) {

            while (i >= 0 && keys[i] > key) {

                keys[i + 1] = keys[i];
                bookNames[i + 1] = bookNames[i];

                i--;
            }

            keys[i + 1] = key;
            bookNames[i + 1] = name;

            n++;
        }

        else {

            while (i >= 0 && keys[i] > key)
                i--;

            if (children[i + 1].n == 2 * t - 1) {

                splitChild(i + 1, children[i + 1]);

                if (keys[i + 1] < key)
                    i++;
            }

            children[i + 1].insertNonFull(key, name);
        }
    }

    public void splitChild(int i, BTreeNode y) {

        BTreeNode z = new BTreeNode(y.t, y.leaf);

        z.n = t - 1;

        for (int j = 0; j < t - 1; j++) {

            z.keys[j] = y.keys[j + t];
            z.bookNames[j] = y.bookNames[j + t];
        }

        if (!y.leaf) {

            for (int j = 0; j < t; j++)
                z.children[j] = y.children[j + t];
        }

        y.n = t - 1;

        for (int j = n; j >= i + 1; j--)
            children[j + 1] = children[j];

        children[i + 1] = z;

        for (int j = n - 1; j >= i; j--) {

            keys[j + 1] = keys[j];
            bookNames[j + 1] = bookNames[j];
        }

        keys[i] = y.keys[t - 1];
        bookNames[i] = y.bookNames[t - 1];

        n++;
    }
}

class BTree {

    BTreeNode root;
    int t;

    public BTree(int t) {

        this.t = t;
        root = null;
    }

    public void traverse() {

        if (root != null)
            root.traverse();
        else
            System.out.println("Library is empty.");
    }

    public BTreeNode search(int key) {

        if (root == null)
            return null;

        return root.search(key);
    }

    public void insert(int key, String name) {

        if (root == null) {

            root = new BTreeNode(t, true);

            root.keys[0] = key;
            root.bookNames[0] = name;

            root.n = 1;
        }

        else {

            if (root.n == 2 * t - 1) {

                BTreeNode s = new BTreeNode(t, false);

                s.children[0] = root;

                s.splitChild(0, root);

                int i = 0;

                if (s.keys[0] < key)
                    i++;

                s.children[i].insertNonFull(key, name);

                root = s;
            }

            else
                root.insertNonFull(key, name);
        }
    }

    // Count Books
    public int countBooks(BTreeNode node) {

        if (node == null)
            return 0;

        int count = node.n;

        if (!node.leaf) {

            for (int i = 0; i <= node.n; i++) {
                count += countBooks(node.children[i]);
            }
        }

        return count;
    }

    // Delete Book Simulation
    public void deleteBook(int key) {

        if (search(key) != null) {
            System.out.println("Book ID " + key +
                    " deleted successfully (simulation).");
        } else {
            System.out.println("Book not found.");
        }
    }
}

public class BTreeLibrary {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BTree tree = new BTree(3);

        int choice;

        do {

            System.out.println("\n===== LibraryNet B-Tree System =====");

            System.out.println("1. Insert Book");
            System.out.println("2. Search Book");
            System.out.println("3. Display Books");
            System.out.println("4. Count Books");
            System.out.println("5. Delete Book");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter Book Name: ");
                    String name = sc.nextLine();

                    tree.insert(id, name);

                    System.out.println("Book inserted successfully.");

                    break;

                case 2:

                    System.out.print("Enter Book ID to search: ");

                    int searchId = sc.nextInt();

                    BTreeNode result = tree.search(searchId);

                    if (result != null)
                        System.out.println("Book found in library.");
                    else
                        System.out.println("Book not found.");

                    break;

                case 3:

                    System.out.println("\nLibrary Catalog:");

                    tree.traverse();

                    break;

                case 4:

                    int totalBooks = tree.countBooks(tree.root);

                    System.out.println("Total Books in Library: " + totalBooks);

                    break;

                case 5:

                    System.out.print("Enter Book ID to delete: ");

                    int deleteId = sc.nextInt();

                    tree.deleteBook(deleteId);

                    break;

                case 6:

                    System.out.println("Exiting system...");

                    break;

                default:

                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);

        sc.close();
    }
}