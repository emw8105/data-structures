import java.util.*;

public class Project1Test {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        SplayTree sTree = new SplayTree();
        int menuChoice;
        int treeVal;
        System.out.println("---Splay Tree---");

        do {
            System.out.println("0 - Exit\n1 - Insert a number\n2 - Delete a number\n3 - Search for a number");
            menuChoice = input.nextInt();

            switch(menuChoice) {
                case 1 :        // insert a value into the tree
                    System.out.print("Enter a number to insert: ");
                    treeVal = input.nextInt();
                    sTree.insert(treeVal);
                    System.out.println(treeVal + " has been inserted");
                    break;
                case 2 :        // delete a value from the tree
                    System.out.print("Enter a number to delete: ");
                    treeVal = input.nextInt();
                    if (sTree.delete(treeVal)) {    // check if the search finds and deletes a value T/F
                        System.out.println(treeVal + " was deleted");
                    }
                    else {
                        System.out.println(treeVal + " is not found in this tree");
                    }
                    break;
                case 3 :        // search for a value in the tree
                    System.out.print("Enter a number to search for: ");
                    treeVal = input.nextInt();
                    if (sTree.search(treeVal)) {    // check if the search finds a value T/F
                        System.out.println(treeVal + " was found");
                    }
                    else {
                        System.out.println(treeVal + " is not found in this tree");
                    }
                    break;
            }
            if (menuChoice != 0) {
                System.out.print("Preorder traversal: ");
                sTree.printPreorder();
                System.out.println();
            }
            else {
                System.out.print("Final Tree: ");
                sTree.printPreorder();
            }
        } while(menuChoice != 0);
    }
}

class Node {
    Node left;
    Node right;
    Node parent;
    int val;

    // constructor to initialize a node
    // null values can be passed if specific pointers not needed
    Node(int val, Node left, Node right, Node parent) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }
}

class SplayTree {
    // splay tree has a pointer to the root node and a count of all nodes in the tree
    Node root;
    int numNodes;

    // constructor, new splay tree starts with no nodes
    SplayTree() {
        root = null;
    }

    // insertion (int k)
    public void insert (int k) {
        if(root == null) {
            // if this node is the first, make the new node the root
            root = new Node(k, null, null, null);
        }
        else {
            Node node = root; // traversal node, determines location to insert
            Node parent = null; // this node acts as a temporary parent pointer to traverse with
            while (node != null) {
                parent = node;  // set parent to be the current temp node as a placeholder
                if(k > parent.val) {    // traverse to the next node location based on the value of the element
                    node = node.right;  // if inserted val is greater than the current node, go to the right
                }
                else {
                    node = node.left;   // else go to the left
                }
            }
            // once exiting the loop, temp is a null pointer at the location of the inserted value
            node = new Node(k,  null, null, parent); // allocate a new node at that location

            // set the parent node to point to the newly inserted node
            if (k > parent.val) {
                parent.right = node;
            }
            else {
                parent.left = node;
            }
            splay(node);
        }
        numNodes++;
    }

    public boolean delete(int k) {
        Node node = findNode(k);        // first search for the node, findNode also spays it up already
        if (node == null) {             // if the node isn't in the tree, return false
            return false;
        }
        if(node.left != null && node.right != null)     // if the node has 2 children, find the ideal successor node
        {
            // find the maximum node of the left subtree
            // the maximum node will have at most one child, so use it to replace the root
            // as the ideal successor node after deletion of the current root

            Node max = node.left;   // start in the left subtree of the root
            while(max.right != null) { // loop until finding the max value in the left subtree
                max = max.right;
            }
            max.right = node.right;     // replace the node to be deleted with the max node in the left subtree
            node.left.parent = null;    // adjust the pointers
            node.right.parent = max;
            root = node.left;

        } else if (node.left != null) {    // otherwise, if the node only has a left child
            node.left.parent = null;        // then make sure the left child doesn't point to this node as it's parent
            root = node.left;               // then make that child the new root
        } else if (node.right != null) {    // otherwise, if the node only has a right child
            node.right.parent = null;       // then make sure the right child doesn't point to this node as it's parent
            root = node.right;              // then make that child the new root
        } else {                        // if the node has no children, then it's the last node, so the root is now null
            root = null;
        }

        // with nothing left to reference the node, java auto cleanup will delete it
        numNodes--;
        return true;
    }

    // search (int k)
    public boolean search(int k) {
        return findNode(k) != null; // if node is null i.e. not found, then returns false, else is true
    }

    // Because delete and search both have a similar initial sequence, the algorithm for locating
    // the desired node has been condensed into one function called findNode
    private Node findNode(int k) {
        Node node = root;   // start the search at the root
        Node lastNode = null;
        while (node != null) {  // loop through the chain of pointers attempting to find value
            lastNode = node;    // marks last non null pointer
            if (k < node.val) {     // traverse the tree following the rules of binary search trees
                node = node.left;
            } else if (k > node.val) {
                node = node.right;
            } else {      // if the value is found, splay the node to the top and return it
                splay(node);
                return node;
            }
        }
        // if the value is not found, then splay lastNode, i.e. the location of the value in the tree if it existed
        if (lastNode != null) {
            splay(lastNode);
        }
        return null;
    }

    // Splay method determines which rotation to use and loops until given node is the root of the tree
    private void splay(Node node) {
        // keep rotating the nodes until the node is at the root, i.e. has no parent
        while (node.parent != null) {
            Node p = node.parent;
            Node gp = p.parent;

            // if the node has no grandparent, only need to rotate the parent node
            if (gp == null) {
                if (node == p.left) {   // zig rotation
                    rotateRight(p);
                } else {    // zag rotation
                    rotateLeft(p);
                }
            } else {    // if the node has a grandparent, more complex rotations are required
                if (node == p.left && p == gp.left) { // zig-zig rotation
                    rotateRight(gp);
                    rotateRight(p);
                } else if (node == p.right && p == gp.right) { // zag-zag rotation
                    rotateLeft(gp);
                    rotateLeft(p);
                } else if (node == p.right && p == gp.left) { // zig-zag rotation
                    rotateLeft(p);
                    rotateRight(gp);
                } else { // node == p.left && p == gp.right, zag-zig rotation
                    rotateRight(p);
                    rotateLeft(gp);
                }
            }
        }
        // after looping, splayed node will be at the top, set the root to be the node
        root = node;
    }

    // Rotate methods are utilized by the splay function to properly adjust the pointers of given nodes into rotating
    private void rotateLeft(Node n1) {
        Node n2 = n1.right;             // n2 is the right child of the given node
        if (n2 != null) {               // extra check to avoid null pointer exception
            n1.right = n2.left;         // replace right child of given node with left grandchild
            if (n2.left != null) {      // another null pointer avoid check
                n2.left.parent = n1;    // if able to, set the parent pointer of the grandchild to the right child (n2)
            }
            n2.parent = n1.parent;      // set parent node of child to be the parent's parent
            if (n1.parent == null) {    // if the given node has no parent, n2 is now on top of it, so it's now the root
                root = n2;
            } else if (n1 == n1.parent.left) { // if n1 has a parent, make it's left pointer point to n2 instead
                n1.parent.left = n2;
            } else {      // catch exception for if n1's parent doesn't have a left child
                n1.parent.right = n2;
            }
            n2.left = n1;       // lastly, set n2's left pointer to n1 and make n1's parent n2 to complete the rotation
            n1.parent = n2;
        }
    }
    private void rotateRight(Node n1) {
        Node n2 = n1.left;              // n2 is the left child of the given node
        if (n2 != null) {               // extra check to avoid null pointer exception
            n1.left = n2.right;         // replace left child of given node with right grandchild
            if (n2.right != null) {     // another null pointer avoid check
                n2.right.parent = n1;   // if able to, set the parent pointer of the grandchild to the left child (n2)
            }
            n2.parent = n1.parent;      // set parent node of child to be the parent's parent
            if (n1.parent == null) {    // if the given node has no parent, n2 is now on top of it, so it's now the root
                root = n2;
            } else if (n1 == n1.parent.right) { // if n1 has a parent, make it's right pointer point to n2 instead
                n1.parent.right = n2;
            } else {        // catch exception for if n1's parent doesn't have a right child
                n1.parent.left = n2;
            }
            n2.right = n1;         // lastly, set n2's right pointer to n1 and make n1's parent n2 to complete the rotation
            n1.parent = n2;
        }
    }

    /*
    To print from preorder, always start from the root and recursively traverse
    downwards through the tree. Pass a string flag with the function call so that
    the function knows what type of node is being printed, either RT, R, or L.
     */
    public void printPreorder() {
        printPreorder(root, "RT");  // start with the root and give it a special flag
    }
    private void printPreorder(Node node, String nodeType) {
        if (node != null) {     // continue iterating until reaching a leaf
            System.out.print(node.val + nodeType + ", ");   // print node and recursively call children
            printPreorder(node.left, "L");      // left to right recursively to print in preorder
            printPreorder(node.right, "R");
        }
    }
}