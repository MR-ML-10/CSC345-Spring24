import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DequeTest2 {

	@Test
	void testAddFirst() {
		Deque<Integer> q = new Deque<Integer>();
		q.addFirst(1);
		assertEquals("[1]", q.toString());
		q.addFirst(1);
		assertEquals("[1, 1]", q.toString());
		q.addFirst(1);
		assertEquals("[1, 1, 1]", q.toString());
		q.addFirst(1);
		assertEquals("[1, 1, 1, 1]", q.toString());
	}

	@Test
	void testAddLast() {
		Deque<Integer> q = new Deque<Integer>();
		q.addLast(1);
		assertEquals("[1]", q.toString());
		q.addLast(2);
		assertEquals("[1, 2]", q.toString());
		q.addLast(3);
		assertEquals("[1, 2, 3]", q.toString());
		q.addLast(4);
		assertEquals("[1, 2, 3, 4]", q.toString());
	}

	@Test
	void testRemoveFirst() throws EmptyDequeException {
		Deque<Integer> q = new Deque<Integer>();
		q.addLast(1);
		assertEquals("[1]", q.toString());
		q.addLast(2);
		assertEquals("[1, 2]", q.toString());
		q.addLast(3);
		assertEquals("[1, 2, 3]", q.toString());
		q.addLast(4);
		assertEquals("[1, 2, 3, 4]", q.toString());

		assertEquals(1, q.removeFirst());
		assertEquals("[2, 3, 4]", q.toString());

		assertEquals(2, q.removeFirst());
		assertEquals("[3, 4]", q.toString());
		assertEquals(3, q.removeFirst());
		assertEquals("[4]", q.toString());
		assertEquals(4, q.removeFirst());
		assertEquals("[]", q.toString());
	}

	@Test
	void testRemoveLast() throws EmptyDequeException {
		Deque<Integer> q = new Deque<Integer>();
		q.addLast(1);
		assertEquals("[1]", q.toString());
		q.addLast(2);
		assertEquals("[1, 2]", q.toString());
		q.addLast(3);
		assertEquals("[1, 2, 3]", q.toString());
		q.addLast(4);
		assertEquals("[1, 2, 3, 4]", q.toString());

		assertEquals(4, q.removeLast());
		assertEquals("[1, 2, 3]", q.toString());

		assertEquals(3, q.removeLast());
		assertEquals("[1, 2]", q.toString());

		assertEquals(2, q.removeLast());
		assertEquals("[1]", q.toString());

		assertEquals(1, q.removeLast());
		assertEquals("[]", q.toString());
	}

	@Test
	void testPeakFirst() throws EmptyDequeException {
		Deque<Integer> q = new Deque<Integer>();
		q.addLast(1);
		assertEquals("[1]", q.toString());
		q.addLast(2);
		assertEquals("[1, 2]", q.toString());
		q.addLast(3);
		assertEquals("[1, 2, 3]", q.toString());
		q.addLast(4);
		assertEquals("[1, 2, 3, 4]", q.toString());
		assertEquals(1, q.peekFirst());

		q.addFirst(2);
		assertEquals(2, q.peekFirst());

		q.addFirst(3);
		assertEquals(3, q.peekFirst());

		q.addFirst(4);
		assertEquals(4, q.peekFirst());

		q.addFirst(-654);
		assertEquals(-654, q.peekFirst());
	}

	@Test
	void testPeakLast() throws EmptyDequeException {
		Deque<Integer> q = new Deque<>();
		q.addLast(1);
		assertEquals("[1]", q.toString());
		q.addLast(2);
		assertEquals("[1, 2]", q.toString());
		q.addLast(3);
		assertEquals("[1, 2, 3]", q.toString());
		q.addLast(4);
		assertEquals("[1, 2, 3, 4]", q.toString());
		assertEquals(1, q.peekFirst());

		q.addFirst(4);
		assertEquals(4, q.peekLast());

		q.addLast(3);
		assertEquals(3, q.peekLast());

		q.addLast(4);
		assertEquals(4, q.peekLast());

		q.addLast(-654);
		assertEquals(-654, q.peekLast());
		q.toString();
	}

	@Test
	void testIsEmpty() throws EmptyDequeException {
		Deque<Integer> q = new Deque<>();
		assertTrue(q.isEmpty());
		q.addLast(-654);
		assertFalse(q.isEmpty());

		q.removeFirst();
		assertTrue(q.isEmpty());
		System.err.println(q.toString());

		q.addFirst(-654);
		assertFalse(q.isEmpty());
		System.err.println(q.toString());

		q.addFirst(-654);
		assertFalse(q.isEmpty());
		System.err.println(q.toString());

		q.addFirst(-654);
		assertFalse(q.isEmpty());
		System.err.println(q.toString());

		q.addFirst(-654);
		assertFalse(q.isEmpty());

		q.addFirst(-654);
		assertFalse(q.isEmpty());
		q.addFirst(-654);
		assertFalse(q.isEmpty());
		q.addFirst(-654);
		assertFalse(q.isEmpty());
		q.addFirst(-654);
		assertFalse(q.isEmpty());
		System.err.println(q.toString());

	}

}
