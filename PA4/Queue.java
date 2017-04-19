//-----------------------------------------------------------------------------
// Name: Mohammad Mirabian
// CruzID: mmirabia
// StudentID# 1377020
// Date: 5/22/2015
// File Name: Queue.java
// File Purpose: ADT for QueueInterface.java
//-----------------------------------------------------------------------------

public class Queue implements QueueInterface {

	private class Node {
		Object item;
		Node next;

		Node(Object item) {

			this.item = item;
			next = null;

		}
	}

	Node head;
	Node back;
	int numItems;

	Queue() {

		this.head = null;
		this.back = null;
		numItems = 0;

	}

	// isEmpty()
	// pre: none
	// post: returns true if this Queue is empty, false otherwise
	public boolean isEmpty() {

		if (numItems == 0)
			return true;
		else
			return false;

	}

	// length()
	// pre: none
	// post: returns the length of this Queue.
	public int length() {

		return numItems;

	}

	// enqueue()
	// adds newItem to back of this Queue
	// pre: none
	// post: !isEmpty()
	public void enqueue(Object newItem) {
		int i = 1;
		if (head == null) {

			head = new Node(newItem);
			numItems++;
		}

		else {
			Node N = head;
			while (N.next != null) {
				N = N.next;
			}

			N.next = new Node(newItem);
			back = N.next;
			numItems++;

		}
	}

	// dequeue()
	// deletes and returns item from front of this Queue
	// pre: !isEmpty()
	// post: this Queue will have one fewer element
	public Object dequeue() throws QueueEmptyException {

		Node N = head;

		if (head == null) {
			throw new QueueEmptyException(
					"Usage: using dequeue() on empty queue");
		}

		if (numItems > 1) {

			head = N.next;
			numItems--;
			return N.item;

		}

		else {
			head = null;
			numItems--;
			return N.item;
		}

	}

	// peek()
	// pre: !isEmpty()
	// post: returns item at front of Queue
	public Object peek() throws QueueEmptyException {

		if (isEmpty()) {
			throw new QueueEmptyException("Usage: using peek() on empty queue");
		} else {
			Node N = head;
			return N.item;

		}

	}

	// dequeueAll()
	// sets this Queue to the empty state
	// pre: !isEmpty()
	// post: isEmpty()
	public void dequeueAll() throws QueueEmptyException {
		if (isEmpty()) {
			throw new QueueEmptyException(
					"Usage: using dequeueAll() on empty queue");
		} else {

			head = null;
			back = null;
			numItems = 0;

		}

	}

	// toString()
	// overrides Object's toString() method
	public String toString() {

		String s = "";
		Node N = head;
		while (N != null) {
			s += N.item + " ";
			N = N.next;
		}
		return s;

	}

}
