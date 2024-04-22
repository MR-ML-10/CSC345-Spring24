
public class QTester {
	public static void main(String[] args) throws EmptyDequeException {
		Deque<Integer> q = new Deque<Integer>();

		q.addLast(5);
		q.toString();

		q.addLast(6);
		q.toString();

		q.addLast(7);
		q.toString();

		q.addFirst(8);
		q.toString();

		q.addFirst(9);
		q.toString();

		q.addLast(10);
		q.toString();

		q.removeFirst();
		q.toString();

		q.removeLast();
		q.toString();
	}
}
