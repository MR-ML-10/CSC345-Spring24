public class Array {
	private int[] array;// the underlying array
	private int accessCount;// counts the number of times the array is accessed

	// constructor that creates an array of size <cap>
	public Array(int cap) {
		this.accessCount = 0;
		this.array = new int[cap];
	}

	public Array(int[] a) {
		this.accessCount = 0;
		this.array = a;
	}

	// gets the length of the array
	public int length() {
		return array.length;
	}

	// gets the value at index i
	public int getVal(int i) {
		int num = array[i];
		accessCount++;
		return num;
	}

	// sets the value at index i to val
	public void setVal(int i, int val) {
		array[i] = val;
		accessCount++;
	}

	// swaps the values at i and j
	public void swap(int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
		accessCount += 4;
	}

	// returns the accessCount
	public int getAccessCount() {
		return accessCount;
	}

	// resets accessCount to 0--used only for testing!!!
	public void resetAccessCount() {
		this.accessCount = 0;
	}
}
