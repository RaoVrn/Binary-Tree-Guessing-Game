import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class Node {
    int number;
    String data;
    Node left, right;

    public Node(int number, String data) {
        this.number = number;
        this.data = data;
        left = right = null;
    }
}

public class BinaryTree {
    Node root;

    private Node insert(Node root, int number, String data) {
        if (root == null) {
            return new Node(number, data);
        }

        if (number < root.number) {
            root.left = insert(root.left, number, data);
        } else if (number > root.number) {
            root.right = insert(root.right, number, data);
        }

        return root;
    }

    public void buildTreeFromFile(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(" ", 2);
                int number = Integer.parseInt(data[0]);
                String value = data[1];
                root = insert(root, number, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayBinaryTree(Node root, String prefix, boolean isLeft) {
        if (root != null) {
            System.out.println(prefix + (isLeft ? "├── " : "└── ") + root.data);
            displayBinaryTree(root.left, prefix + (isLeft ? "│   " : "    "), true);
            displayBinaryTree(root.right, prefix + (isLeft ? "│   " : "    "), false);
        }
    }

    private void inOrderTraversal(Node root) {
        if (root != null) {
            inOrderTraversal(root.left);
            System.out.print(root.number + ": " + root.data + " ");
            System.out.println(); // Add new line
            inOrderTraversal(root.right);
        }
    }
    
    private void preOrderTraversal(Node root) {
        if (root != null) {
            System.out.print(root.number + ": " + root.data + " ");
            System.out.println(); // Add new line
            preOrderTraversal(root.left);
            preOrderTraversal(root.right);
        }
    }
    
    private void postOrderTraversal(Node root) {
        if (root != null) {
            postOrderTraversal(root.left);
            postOrderTraversal(root.right);
            System.out.print(root.number + ": " + root.data + " ");
            System.out.println(); // Add new line
        }
    }
    

    public void displayTree() {
        displayBinaryTree(root, "", false);
    }

    public void displayTraversals() {
        System.out.print("In-order traversal: ");
        inOrderTraversal(root);
        System.out.println();

        System.out.print("Pre-order traversal: ");
        preOrderTraversal(root);
        System.out.println();

        System.out.print("Post-order traversal: ");
        postOrderTraversal(root);
        System.out.println();
    }

    private static void playGame(Node currentNode, Scanner scanner) {
        while (currentNode != null) {
            System.out.println(currentNode.data);
            if (currentNode.left == null && currentNode.right == null) {
                break;
            }
            String answer = scanner.nextLine().toLowerCase();
            if (answer.equals("yes") && currentNode.left != null) {
                currentNode = currentNode.left;
            } else if (answer.equals("no") && currentNode.right != null) {
                currentNode = currentNode.right;
            } else {
                System.out.println("Invalid answer, please try again.");
            }
        }
    }

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.buildTreeFromFile("questions.txt");

        Scanner scanner = new Scanner(System.in);
        Node currentNode = binaryTree.root;

        while (true) {
            System.out.println("Please select an option:");
            System.out.println("P: Play the game");
            System.out.println("L: Load another game file");
            System.out.println("D: Display the binary tree");
            System.out.println("T: Display tree traversals");
            System.out.println("H: Help information");
            System.out.println("X: Exit the program");

            String option = scanner.nextLine().toUpperCase();

            switch (option) {
                case "P":
                    playGame(currentNode, scanner);
                    break;
                case "L":
                    System.out.println("Please enter the path to the new game file:");
                    String fileName = scanner.nextLine();
                    binaryTree.buildTreeFromFile(fileName);
                    currentNode = binaryTree.root;
                    break;
                case "D":
                    binaryTree.displayTree();
                    break;
                case "T":
                    binaryTree.displayTraversals();
                    break;
                case "H":
                    System.out.println("This is a guessing game where you think of a number and the computer tries to guess it.");
                    System.out.println("You will be asked a series of yes or no questions to help the computer guess your number.");
                    System.out.println("To play the game, select the 'P' option from the menu.");
                    break;
                case "X":
                    System.exit(0);
                default:
                    System.out.println("Invalid option, please try again.");
                    break;
            }
        }
    }
}
