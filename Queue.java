/**
	This class implements a circular array.
	It expands if the queue becomes full.
**/
public class Queue {
	public static QNode [] queue;		// Array that stores the queue elements.
	public static int front;				// Index pointing to the front of the queue.
	public static int end;					// Index pointing to the element that is one less than the end of the queue.
	public static int numElements;	// The number of elements currently stored in the queue.
	public static int size;					// The capacity of the allocated array. If the number of elements reaches this capacity, we need to expand.
	public static Queue q = new Queue();
	/**
		Constructor: Allocates a queue with inital size of 1000.
	**/
	public Queue() {
		//numElements = 0;
		//size = 1000;
		numElements = 0;
		size = 1000;
		front = size - 1;//changed from size -1 because it was too unpredictable
		end = size - 1;//changed from size -1 because it was too unpredictable
		queue = new QNode[size];
	}
	
	/**
		This function enqueues a new element p into the queue. 
		This also expands the array if it is full.

		Expand the array, by first creating another one with 
		twice the size and copying the contents of the old array.
	**/
	public void enqueue(QNode p) {
		//If array is full, increase size and copy old into new
		//System.out.println("numeElements in ENQ = "+numElements);
		//System.out.println("size in ENQ before loop = "+size);
		//System.out.println("front before loop = "+front);
		//System.out.println("end before loop = "+end);
		if (numElements == size) {
			//System.out.println("Tell me I'm HERE!!!");
			QNode[] new_arr = new QNode[size * 2];
			//front += 1;
			//QNode[] temp = queue;			
			int old_size = size;
			size = (size * 2);		
			for (int i = 0; i < old_size; ++i) {
				new_arr[size - (i + 1)] = queue[(front - i + old_size)%old_size]; 
				//front = (front - 1 + size)%size;
			}			
			//front = ((front + (size - 1)) - old_size);
			front = (front + old_size);
			end = (end + size)%size;
			queue = new_arr;
			print();
		}
		/* insert new element here */
		queue[end] = p;
		//end = (end + 1)%queue.length;
		end = (end - 1 + size)%size;
		++numElements;
	}

	/**
		This function removes and returns the front element in the queue.
	**/
	public QNode dequeue() {
		if (Queue.numElements == 0) {
			return null;
		} 
		//System.out.println("Tell me I'm HERE in DQ!!!");
		//System.out.println("numeElements in DQ = "+numElements);
		//System.out.println("size in DQ before loop = "+size);
		//System.out.println("front before changes = "+front);
		//System.out.println("end before changes = "+end);
		QNode temp = queue[front];
		//queue[front] = null;
		front = (front - 1 + size)%size;
		//System.out.println("front after changes = "+front);
		//System.out.println("end after changes = "+end);
		--numElements;
		return temp;	// remove this line once the funciton is completed.
	}

	/**
		This function returns true if the queue is empty, otherwise returns false.
	**/
	public boolean isEmpty() {
		if (numElements == 0) 
			return true;
		return false;
	}
	/**
		This function prints the contents of the queue.
	**/
	public static void print() {
		//System.out.println("Front before print is = "+Queue.front);
		//System.out.println("End before print is = "+Queue.end);
		//for (int i = front; i < end; ++i) {
		for (int i = 0; i < numElements; ++i) {
			int check = (front - i + size)%size;
			if (Queue.queue[check] != null) {
				//System.out.println(q.queue[i].getDist()+" "+q.queue[i].getWord());
				System.out.println(Queue.queue[check].getDist()+" "+Queue.queue[check].getWord());
			}
		}
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
		System.out.println("Size of queue inside copyArray is "+size);
		queue = array;
		/* 
		queue[front]= array[front];
		queue[end] = array[end];
		queue[numElements] = array[numElements];*/
	}
	//Temp Driver Code
	public static void main(String[] args) {
		int n = 10;
		//QNode[] arr = new QNode[n];
		//Queue q = new Queue();

		//q.enqueue(new QNode(1, "lost"));
		//q.enqueue(new QNode(2, "loot"));
		//q.enqueue(new QNode(3, "coot"));
		//q.enqueue(new QNode(4, "boot"));
		//q.enqueue(new QNode(5, "boat"));
		//q.enqueue(new QNode(6, "bode"));
		//q.enqueue(new QNode(7, "coat"));
		//q.enqueue(new QNode(8, "moat"));
		//q.enqueue(new QNode(9, "rote"));
		//System.out.println("|----------- After 1st Enqueue: ---------|");
		//print();
		//q.enqueue(new QNode(10, "rite"));
		//q.enqueue(new QNode(11, "lost"));
		//q.enqueue(new QNode(12, "loot"));
		//System.out.println("|---- After 2nd Enqueue (overflow 10 by 3 places:---|");
		//print();
		//System.out.println("Before enqueue:");
		 
		//QNode deq = q.dequeue();
		//QNode deq1 = q.dequeue();
		//QNode deq2 = q.dequeue();
		//QNode deq3 = q.dequeue();
		//System.out.println("After dequeue, taking us down to 8 indexes:");
		//print();
		

		//q.enqueue(new QNode(13, "coot"));
		//q.enqueue(new QNode(14, "boot"));
		//System.out.println("|-----------After 3rd Enqueue:---------|");
		//print();
		/* 
		QNode deq4 = q.dequeue();
		QNode deq5 = q.dequeue();
		QNode deq6 = q.dequeue();
		QNode deq7 = q.dequeue();;
		QNode deq8 = q.dequeue();
		QNode deq9 = q.dequeue();
		QNode deq10 = q.dequeue();
		QNode deq11 = q.dequeue();
		QNode deq12 = q.dequeue();
		System.out.println("After dequeue:");
		print();
			*/
		
		

	}
}
