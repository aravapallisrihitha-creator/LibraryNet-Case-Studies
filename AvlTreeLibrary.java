import java.util.Scanner;

class Node {
    int bookId;
    String bookName;
    int height;
    Node left, right;

    Node(int id, String name) {
        bookId = id;
        bookName = name;
        height = 1;
    }
}

public class AvlTreeLibrary {

    Node root;

    int height(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }

    int getBalance(Node node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    Node insert(Node node, int id, String name) {

        if (node == null)
            return new Node(id, name);

        if (id < node.bookId)
            node.left = insert(node.left, id, name);
        else if (id > node.bookId)
            node.right = insert(node.right, id, name);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // Left Left
        if (balance > 1 && id < node.left.bookId)
            return rightRotate(node);

        // Right Right
        if (balance < -1 && id > node.right.bookId)
            return leftRotate(node);

        // Left Right
        if (balance > 1 && id > node.left.bookId) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left
        if (balance < -1 && id < node.right.bookId) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    void inorder(Node node) {
        if (node != null) {
            inorder(node.left);

            System.out.println("Book ID: " + node.bookId +
                    " | Book Name: " + node.bookName);

            inorder(node.right);
        }
    }

    Node search(Node node, int id) {
        if (node == null || node.bookId == id)
            return node;

        if (id < node.bookId)
            return search(node.left, id);

        return search(node.right, id);
    }

    Node minValueNode(Node node) {
        Node current = node;

        while (current.left != null)
            current = current.left;

        return current;
    }

    Node delete(Node root, int id) {

        if (root == null)
            return root;

        if (id < root.bookId)
            root.left = delete(root.left, id);

        else if (id > root.bookId)
            root.right = delete(root.right, id);

        else {

            if ((root.left == null) || (root.right == null)) {

                Node temp = null;

                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else
                    root = temp;
            } else {

                Node temp = minValueNode(root.right);

                root.bookId = temp.bookId;
                root.bookName = temp.bookName;

                root.right = delete(root.right, temp.bookId);
            }
        }

        if (root == null)
            return root;

        root.height = Math.max(height(root.left),
                height(root.right)) + 1;

        int balance = getBalance(root);

        // Left Left
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // Left Right
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // Right Left
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    void updateBook(Node node, int id, String newName) {

        Node book = search(node, id);

        if (book != null) {
            book.bookName = newName;
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    public static void main(String[] args) {

        AvlTreeLibrary library = new AvlTreeLibrary();

        Scanner sc = new Scanner(System.in);

        int choice;

        do {

            System.out.println("\n===== LibraryNet Avl Tree System =====");
            System.out.println("1. Insert Book");
            System.out.println("2. Search Book");
            System.out.println("3. Display Books");
            System.out.println("4. Update Book");
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

                    library.root = library.insert(library.root, id, name);

                    System.out.println("Book inserted successfully.");
                    break;

                case 2:

                    System.out.print("Enter Book ID to search: ");
                    int searchId = sc.nextInt();

                    Node result = library.search(library.root, searchId);

                    if (result != null) {

                        System.out.println("Book Found:");
                        System.out.println("Book ID: " + result.bookId);
                        System.out.println("Book Name: " + result.bookName);

                    } else {

                        System.out.println("Book not found.");
                    }

                    break;

                case 3:

                    System.out.println("\nLibrary Catalog:");

                    library.inorder(library.root);

                    break;

                case 4:

                    System.out.print("Enter Book ID to update: ");
                    int updateId = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter New Book Name: ");
                    String newName = sc.nextLine();

                    library.updateBook(library.root, updateId, newName);

                    break;

                case 5:

                    System.out.print("Enter Book ID to delete: ");
                    int deleteId = sc.nextInt();

                    library.root = library.delete(library.root, deleteId);

                    System.out.println("Book deleted successfully.");

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