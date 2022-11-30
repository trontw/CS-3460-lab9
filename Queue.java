/**
	This class implements a circular array.
	It expands if the queue becomes full.
**/
public class Queue {
	private static QNode [] queue;		// Array that stores the queue elements.
	private static int front;				// Index pointing to the front of the queue.
	private static int end;					// Index pointing to the element that is one less than the end of the queue.
	private static int numElements;	// The number of elements currently stored in the queue.
	private static int size;					// The capacity of the allocated array. If the number of elements reaches this capacity, we need to expand.
	private static Queue q = new Queue();
	/**
		Constructor: Allocates a queue with inital size of 1000.
	**/
	public Queue() {
		//numElements = 0;
		//size = 1000;
		numElements = 0;
		size = 10;
		front =  - 1;
		end =  - 1;
		queue = new QNode[size + 1];
	}
	
	/**
		This function enqueues a new element p into the queue. 
		This also expands the array if it is full.

		Expand the array, by first creating another one with 
		twice the size and copying the contents of the old array.
	**/
	public void enqueue(QNode p) {
		//If array is full, increase size and copy old into new
		if (numElements == size) {
			size = 2 * size;
			queue = new QNode[size];
			copyArray(queue);
			/* insert new element here */
			queue[numElements + 1] = p;
			++end;
			++numElements;
		}
		/* insert new element here */
		System.out.println("numElements before queue is "+numElements);
		System.out.println("ENQUEUE: front is = "+front);
		System.out.println("ENQUEUE: end pre increment is = "+end);
		++end;
		queue[numElements + 1] = p;
		++numElements;
	}

	/**
		This function removes and returns the front element in the queue.
	**/
	public QNode[] dequeue() {
		System.out.println("DEQUEUE: front before changes is = "+front);
		if (numElements == 0) {
			return null;
		} else if (front == end) {
			Queue.front =  - 1;
			Queue.end =  - 1;
		} else {
			Queue.front = Queue.front + 3;
		}
		System.out.println("front after amending is "+Queue.queue[Queue.front].getDist()+" "+Queue.queue[front].getWord());
		System.out.println("DEQUEUE: end is = "+end);
		//front = (front + 3)%size;
		//QNode element = queue[front];
		//Queue element = new Queue();
		//Queue.front  = element.front;
		--numElements;
		return Queue.queue;	// remove this line once the funciton is completed.
	}

	/**
		This funciton returns true if the queue is empty, otherwise returns false.
	**/
	public boolean isEmpty() {
		if (numElements == 0) 
			return true;
		return false;
	}
	/**
		This function prints the contents of the queue.
	**/
	public void print() {
        // TODO: print the contents of the queue from front to end. Please print each element on its own line. You may use the toString() method of QNode to print it on a line.
	}
	/**
	 * This function copies the contesnt of the array passsed in,
	 * into the queue. (put in:line6: private QNode [] queue;)
	 * 
	 * This is actually called when expanding the array size
	 */
	private static void copyArray(QNode [] array) {
		//TODO: Code to copy the array into queue. Make sure that the queue
		// parameters -> front, end, and numElements and size are all set
		// correctly.
		queue = array;
	}
	//Temp Driver Code
	public static void main(String[] args) {
		int n = 10;
		//QNode[] arr = new QNode[n];
		//Queue q = new Queue();

		q.enqueue(new QNode(1, "lost"));
		q.enqueue(new QNode(2, "loot"));
		q.enqueue(new QNode(3, "coot"));
		q.enqueue(new QNode(4, "boot"));
		q.enqueue(new QNode(5, "boat"));
		System.out.println("Before enqueue:");
		//System.out.println(q.queue.getDist(),q.getWord());
		for (int i = 0; i < n; ++i) {
			if (Queue.queue[i] != null)
				System.out.println(q.queue[i].getDist()+" "+q.queue[i].getWord());
		}
		q.enqueue(new QNode(6, "beat"));
		System.out.println("After enqueue:");
		for (int i = 0;  i < n; ++i) {
			if (Queue.queue[i] != null)
				System.out.println(q.queue[i].getDist()+" "+q.queue[i].getWord());
		}
		QNode [] deq = q.dequeue();
		System.out.println("deq is ="+Queue.queue[front]);
		System.out.println("After dequeue:");
		for (int i = 0;  i < numElements + 1; ++i) {
			if (Queue.queue[Queue.front+i] != null) {
				//System.out.println(q.queue[i].getDist()+" "+q.queue[i].getWord());
				System.out.println(Queue.queue[Queue.front+i].getDist()+" "+Queue.queue[Queue.front+i].getWord());
			}
		}
		
	}
}
