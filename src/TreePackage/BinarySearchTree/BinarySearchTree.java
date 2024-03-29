package TreePackage.BinarySearchTree;

import TreePackage.BinaryNode;
import TreePackage.BinaryTree;
import TreePackage.BinaryTreeInterface;

/**
 * {@inheritDoc}
 */
public class BinarySearchTree<T extends Comparable<? super T>>
        extends BinaryTree<T> implements SearchTreeInterface<T>
{
    public BinarySearchTree()
    {
        super();
    } // end default constructor

    public BinarySearchTree(T rootEntry)
    {
        super();
        setRootNode(new BinaryNode<T>(rootEntry));
    } // end constructor

    // Disable setTree (see Segment 26.6)
    public void setTree(T rootData, BinaryTreeInterface<T> leftTree,
                        BinaryTreeInterface<T> rightTree)
    {
        throw new UnsupportedOperationException();
    } // end setTree

    public T getEntry(T anEntry)
    {
        return findEntry(getRootNode(), anEntry);
    } // end getEntry

    private T findEntry(BinaryNode<T> rootNode, T anEntry)
    {
        T result = null;

        if (rootNode != null)
        {
            T rootEntry = rootNode.getData();

            if (anEntry.equals(rootEntry))
                result = rootEntry;
            else if (anEntry.compareTo(rootEntry) < 0)
                result = findEntry(rootNode.getLeftChild(), anEntry);
            else
                result = findEntry(rootNode.getRightChild(), anEntry);
        } // end if

        return result;
    } // end findEntry

    public boolean contains(T entry)
    {
        return getEntry(entry) != null;
    } // end contains

    public T add(T newEntry)
    {
        T result = null;

        if (isEmpty())
            setRootNode(new BinaryNode<>(newEntry));
        else{
            result = addEntry(getRootNode(), newEntry);
            setRootNode(balance(getRootNode()));
        }

        return result;
    } // end add

    // Adds anEntry to the nonempty subtree rooted at rootNode.
    private T addEntry(BinaryNode<T> rootNode, T anEntry)
    {
        // Assertion: rootNode != null
        T result = null;
        int comparison = anEntry.compareTo(rootNode.getData());

        if (comparison == 0)
        {
            result = rootNode.getData();
            rootNode.setData(anEntry);
        }
        else if (comparison < 0)
        {
            if (rootNode.hasLeftChild()) {
                result = addEntry(rootNode.getLeftChild(), anEntry);
                rootNode.setLeftChild(balance(rootNode.getLeftChild()));
            }else{
                rootNode.setLeftChild(new BinaryNode<>(anEntry));
            }
        }
        else
        {
            // Assertion: comparison > 0

            if (rootNode.hasRightChild()) {
                result = addEntry(rootNode.getRightChild(), anEntry);
                rootNode.setRightChild(balance(rootNode.getRightChild()));
            }else{
                rootNode.setRightChild(new BinaryNode<>(anEntry));
            }
        } // end if

        return result;
    } // end addEntry

    public T remove(T anEntry)
    {
        ReturnObject oldEntry = new ReturnObject(null);
        BinaryNode<T> newRoot = removeEntry(getRootNode(), anEntry, oldEntry);
        setRootNode(newRoot);

        return oldEntry.get();
    } // end remove

    // Removes an entry from the tree rooted at a given node.
    // Parameters:
    //    rootNode A reference to the root of a tree.
    //    anEntry  The object to be removed.
    //    oldEntry An object whose data field is null.
    //    Returns: The root node of the resulting tree; if anEntry matches
    //             an entry in the tree, oldEntry's data field is the entry
    //             that was removed from the tree; otherwise it is null.
    private BinaryNode<T> removeEntry(BinaryNode<T> rootNode, T anEntry,
                                      ReturnObject oldEntry)
    {
        if (rootNode != null)
        {
            T rootData = rootNode.getData();
            int comparison = anEntry.compareTo(rootData);

            if (comparison == 0)       // anEntry == root entry
            {
                oldEntry.set(rootData);
                rootNode = removeFromRoot(rootNode);
            }
            else if (comparison < 0)   // anEntry < root entry
            {
                BinaryNode<T> leftChild = rootNode.getLeftChild();
                BinaryNode<T> subtreeRoot = removeEntry(leftChild, anEntry, oldEntry);
                rootNode.setLeftChild(subtreeRoot);
            }
            else                       // anEntry > root entry
            {
                BinaryNode<T> rightChild = rootNode.getRightChild();
                // A different way of coding than for left child:
                rootNode.setRightChild(removeEntry(rightChild, anEntry, oldEntry));
            } // end if
        } // end if

        return rootNode;
    } // end removeEntry

    // Removes the entry in a given root node of a subtree.
    // rootNode is the root node of the subtree.
    // Returns the root node of the revised subtree.
    private BinaryNode<T> removeFromRoot(BinaryNode<T> rootNode)
    {
        // Case 1: rootNode has two children
        if (rootNode.hasLeftChild() && rootNode.hasRightChild())
        {
            // Find node with largest entry in left subtree
            BinaryNode<T> leftSubtreeRoot = rootNode.getLeftChild();
            BinaryNode<T> largestNode = findLargest(leftSubtreeRoot);

            // Replace entry in root
            rootNode.setData(largestNode.getData());

            // Remove node with largest entry in left subtree
            rootNode.setLeftChild(removeLargest(leftSubtreeRoot));
        } // end if

        // Case 2: rootNode has at most one child
        else if (rootNode.hasRightChild())
            rootNode = rootNode.getRightChild();
        else
            rootNode = rootNode.getLeftChild();

        // Assertion: If rootNode was a leaf, it is now null

        return rootNode;
    } // end removeFromRoot

    // Finds the node containing the largest entry in a given tree.
    // rootNode is the root node of the tree.
    // Returns the node containing the largest entry in the tree.
    private BinaryNode<T> findLargest(BinaryNode<T> rootNode)
    {
        if (rootNode.hasRightChild())
            rootNode = findLargest(rootNode.getRightChild());

        return rootNode;
    } // end findLargest

    // Removes the node containing the largest entry in a given tree.
    // rootNode is the root node of the tree.
    // Returns the root node of the revised tree.
    private BinaryNode<T> removeLargest(BinaryNode<T> rootNode)
    {
        if (rootNode.hasRightChild())
        {
            BinaryNode<T> rightChild = rootNode.getRightChild();
            rightChild = removeLargest(rightChild);
            rootNode.setRightChild(rightChild);
        }
        else
            rootNode = rootNode.getLeftChild();

        return rootNode;
    } // end removeLargest

    private class ReturnObject
    {
        private T item;

        private ReturnObject(T entry)
        {
            item = entry;
        } // end constructor

        public T get()
        {
            return item;
        } // end get

        public void set(T entry)
        {
            item = entry;
        } // end set
    } // end ReturnObject

    /**
     * checks if the tree is unbalanced and balances the tree
     * @param node root node of tree
     * @return the subtree after being balanced
     */
    public BinaryNode<T> balance(BinaryNode<T> node){
        //gets the height difference of the subtrees
        int height = heightBalance(node);

        //if the height is difference is 0 or 1 return
        if(height == 0 || height == 1){
            return node;
        }

        //if height is greater than 1, the right subtree is unbalanced
        if(height > 1){
            //node creating imbalance is in right subtree
            if(heightBalance(node.getRightChild()) > 0){
                node = leftRotation(node);

            //node creating imbalance in left subtree
            }else{
                node = rightLeftRotation(node);
            }
        //if height less than -1, left subtree is unbalanced
        }else if(height < -1){
            //node creating imbalance is in left subtree
            if(heightBalance(node.getLeftChild()) < 0){
                node = rightRotation(node);

            //node creating imbalance is in right subtree
            }else{
                node = leftRightRotation(node);
            }
        }
        return node;
    }

    private int heightBalance(BinaryNode<T> node){
        //height/depth of the subtrees
        int rightHeight;
        int leftHeight;

        //no left child, thus leftHeight is 0
        if(node.getLeftChild() == null){
            leftHeight = 0;
        //get height of the left child
        }else{
            leftHeight = node.getLeftChild().getHeight();
        }

        //no right child, thus rightHeight is 0
        if(node.getRightChild() == null){
            rightHeight = 0;
        //get height of the right child
        }else{
            rightHeight = node.getRightChild().getHeight();
        }
        //returns the height difference
        return rightHeight - leftHeight;
    }

    /**
     * balances the left subtree of the left child
     * @param node the root node of the tree
     * @return the balanced subtree
     */
    private BinaryNode<T> rightRotation(BinaryNode<T> node){
        //temp node to hold the left child, and act as the pivot
        BinaryNode<T> tempNode = node.getLeftChild();
        //changes left child to hold the temp nodes right child
        node.setLeftChild(tempNode.getRightChild());
        //changes temp nodes right child to the root node
        tempNode.setRightChild(node);
        return tempNode;
    }

    /**
     * balances the right subtree of the right child
     * @param node the root node of the tree
     * @return the balanced subtree
     */
    private BinaryNode<T> leftRotation(BinaryNode<T> node){
        //temp node to hold the right child, and act as the pivot
        BinaryNode<T> tempNode = node.getRightChild();
        //right child will hold temp nodes left child
        node.setRightChild(tempNode.getLeftChild());
        //changes temp nodes right child to the root node
        tempNode.setLeftChild(node);
        return tempNode;
    }

    /**
     * Balances the right subtree os the left child
     * @param node the root node of the tree
     * @return the balanced subtree
     */
    private BinaryNode<T> leftRightRotation(BinaryNode<T> node){
        //balances the right subtree of the left child
        BinaryNode<T> tempNode = node.getLeftChild();
        node.setLeftChild(leftRotation(tempNode));
        //balances the left subtree
        return rightRotation(node);
    }

    /**
     * balances the left subtree of the right child
     * @param node the root node
     * @return the balance tree
     */
    private BinaryNode<T> rightLeftRotation(BinaryNode<T> node){
        //balances the left subtree of the right child
        BinaryNode<T> tempNode = node.getRightChild();
        node.setRightChild(rightRotation(tempNode));
        //balances the right subtree
        return leftRotation(node);
    }
} // end BinarySearchTree
