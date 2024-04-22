public class RBT {
    private Node root;
    private int size;

    /* CONSTRUCTOR */
    public RBT() {
	this.root = null;
	this.size = 0;
    }

    /* PUBLIC METHODS */

    /***
     *insert a new key into tree
     *or update the count if the key already exists
     */
    public void put(int key) {
	if(root == null) {
	    root = new Node(key);
	    size++;
	}
	else 
	    root = put(key, root);
	root.setColor(Node.BLACK);
    }
    
    /***
     *get the count associated with the given key;
     *return 0 if key doesn't exist in the tree
     */
    public int get(int key) {
	Node x = root;
	while(x != null) {
	    if(key == x.key()) 
		return x.count();
	    if(key > x.key())
		x = x.right();
	    else
		x = x.left();
	}
	return 0;
    }

    /***
     *get the color of a node
     ***/
    public String getColor(int key) {
	Node x = get(key, root);
	if(x == null)
	    return null;
	if(x.isRed())
	    return "RED";
	return "BLACK";
    }

    /***
     *return true if the tree
     *is empty and false 
     *otherwise
     */
    public boolean isEmpty() {
	return root == null;
    }

    /***
     *return the number of Nodes
     *in the tree
     */
    public int size() {
	return size;
    }

    /***
     *returns the height of the tree
     */
    public int height() {
	return height(root);
    }

    /***
     *returns the height of node 
     *with given key in the tree;
     *return -1 if the key does
     *not exist in the tree
     */
    public int height(int key) {
	Node x = get(key, root);
	return height(x);
    }

    /***
     *returns true if the key is in the tree
     *and false otherwise
     ***/
    public boolean contains(int key) {
	Node x = get (key, root);
	if(x != null)
	    return true;
	return false;
    }


     /***
     *return the depth of the node
     *with the given key in the tree;
     *return -1 if the key does not exist
     *in the tree
     ***/
    public int depth(int key) {
	//TO BE IMPLEMENTED
	return -1;
    }

    /***
     *return the size of the subtree 
     *rooted at the given key
     ***/
    public int size(int key) {
	//TO BE IMPLEMENTED
	return -1;
    }

     /***
     *return the minimum key
     ***/
    public int min() throws EmptyTreeException {
	//TO BE IMPLEMENTED
	return -1;
    }

     /***
     *return the maximum key
     ***/
    public int max() throws EmptyTreeException {
	//TO BE IMPLEMENTED
	return -1;
    }

    /***
     *return the largest key
     *that is less than or equal
     *to the parameter
     ***/
    public int floor(int key) throws KeyDoesNotExistException {
	//TO BE IMPLEMENTED
	return -1
    }

     /***
     *return the smallest key
     *that is greater than or equal
     *to the parameter
     ***/
    public int ceil(int key) throws KeyDoesNotExistException {
	//TO BE IMPLEMENTED
	return -1;
    }

    /***
     *return the number of keys
     *that are less than the parameter
     ***/
    public int rank(int key) {
	//TO BE IMPLEMENTED
	return -1;
    }

    /***
     *return the key at the given rank
     ***/
    public int select(int rank) throws NoSuchRankException {
	//TO BE IMPLEMENTED
	return -1;
    }

    /***
     *return the number of keys in [lo...hi]
     ***/
    public int size(int lo, int hi) {
	//TO BE IMPLEMENTED
	return -1;
    }

    /***
     *return a String representation of the tree
     *level by level
     ***/
    public String toString() {
	//TO BE IMPLEMENTED
	return null;
    }

    /* PRIVATE METHODS */

    /***
     *return the height of x
     *or -1 if x is null
     */
    private int height(Node x) {
	if(x == null)
	    return -1;
	return x.height();
    }

    /***
     *recursive helper method for insert
     */
    private Node put(int key, Node x) {
	if(key == x.key()) {
	    x.incCount();
	} else if (key > x.key()) {
	    if(x.right() == null) {
		x.setRight(new Node(key));
		size++;
	    } else {
		x.setRight(put(key, x.right()));
	    }
	} else {
	    if(x.left() == null) {
		x.setLeft(new Node(key));
		size++;
	    } else {
		x.setLeft(put(key, x.left()));
	    }
	}
	x = balance(x);
	x.setHeight(Math.max(height(x.left()), height(x.right())) + 1);
	return x;
    }

    /***
     *recursive method for getting Node 
     *with given key
     */
    private Node get(int key, Node x) {
	if(x == null)
	    return null;
	if(key == x.key())
	    return x;
	else if(key > x.key())
	    return get(key, x.right());
	return get(key, x.left());
    }

    /***
     *rotate left
     ***/
    private Node rotateLeft(Node h) {
	Node x = h.right();
	h.setRight(x.left());
	x.setLeft(h);
	x.setColor(h.color());
	h.setColor(Node.RED);
	x.setHeight(h.height());
	h.setHeight(1 + Math.max(height(h.left()), height(h.right())));
	return x;
    }

    /***
     *rotate right
     ***/
    private Node rotateRight(Node h) {
	Node x = h.left();
	h.setLeft(x.right());
	x.setRight(h);
	x.setColor(h.color());
	h.setColor(Node.RED);
	x.setHeight(h.height());
	h.setHeight(1 + Math.max(height(h.left()), height(h.right())));
	return x;
    }

    /***
     *color flip
     ***/
    private void colorFlip(Node h) {
	h.setColor(Node.RED);
	h.left().setColor(Node.BLACK);
	h.right().setColor(Node.BLACK);
	if(h == root)
	    h.setColor(Node.BLACK);
    }

    /***
     *balance
     ***/
    private Node balance(Node h) {
	if(h.right() != null && h.right().isRed() && h.right().right() != null && h.right().right().isRed()) {
	    h = rotateLeft(h);
	    colorFlip(h);
	}
	else if(h.left() != null && h.right() != null && h.left().isRed() && h.right().isRed())
	    colorFlip(h);
	else if(h.left() != null && h.left().isRed())
	    h = rotateRight(h);
	return h;
    }
}    
