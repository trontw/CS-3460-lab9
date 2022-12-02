import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class WordLadder {
	private static String start;
	private static String end;
	private static String infinity;
	private static StringMap T;	// This map stores the dictionary of words.
	private static StringMap R;	/* This map keeps track of all the words that 
									are visited during breadth-first-search.
									The key field is the word that is visited, and its 
									value field can hold the predecessor pointer (pred[i]).*/
	private static Queue Q;	// A queue to perform the breadth-first-search.
	private static Queue Qtemp;
	private static int [] pred;
	private static int count = 0;
	private static StringBuffer sb;
	private static boolean BFS;
	/**
		Constructor: s is the initial size of the heap.
	*/
	private static void WordLadder(String s, String e) {
		start = s;
		end = e;
	}

	public static String isValid(String start2, String end2) {
		for (int i = 0; i < count + 1; ++i) {
			if (Q.queue[i] != null) {
				String w = Q.queue[i].getWord();
				// lose -> lost -> lest -> best -> beat
				for (int k = 0; k < start2.length(); k++) {	
					//StringBuffer sb = new StringBuffer(start2);
					if  (w.charAt(k) == end2.charAt(k) ) {
						return w;
					}
				}				
			}
		}
		return null;
	}

	public static void main(String [] args) throws IOException {
		// Loading the dictionary of words into the StringMap T.
		T = new StringMap();
		BFS = false;
		File file = new File("dictionary4");
		Scanner f = new Scanner(file);
		while (f.hasNext()) {
			String word = f.nextLine();
			T.insert(word, "");
		}
		f.close();

		Scanner kb = new Scanner(System.in);
		System.out.print("Enter the start word: ");
		start = kb.nextLine();
		System.out.print("Enter the end word: ");
		end = kb.nextLine();
		/*
		 	TODO: Solution to find the shortest set of words that transforms the start word to the end word.
		 * To set to infinity, set the value much greater than the total number of 
		 * words in the Dictionary. (which is 3352)
		 */
		//Initialize the visited nodes to StringMap R
		R = new StringMap();
		//pred = new int[R.size];
		Queue Qtemp = new Queue();
		Queue Q = new Queue();
		StringNode[] pred = new StringNode[R.size];
		//StringNode[] dist = new StringNode[6000];
		int hop = 0;
		int infinity = 4000;
		//private static Queue q = new Queue();
		for (int i = 0; i < R.size; ++i) {
			pred[i] = null;
			//dist[i] = dist[infinity];//Initialize to Infinity
		}
		int source = 0;
		//for (StringNode curr = R.table[i]; curr != null; curr = curr.getNext()){
		System.out.println("Made it to INIT routine.");

		//Enqueue input (initiall dist = 0, word = lose)
		Q.enqueue(new QNode(0,start));
		System.out.println("numElements is = "+Queue.numElements);
		int dist = 0;
		while (!Q.isEmpty()){
			//if (T.find(start) != null){
			QNode DQ = Q.dequeue();
	
			//Increment the distance (dist) to next node ring
			//++hop;
			++source;
			//if (DQ != null) {
				++hop;
				dist = DQ.getDist();
				System.out.println("DQ just dist ---- PRE ADD ------> "+dist);
			
				DQ.setDist(dist + 1);
			
				System.out.println("DQ just dist ---- POST ADD ------> "+DQ.getDist());
				System.out.println("----------->Hop before DQ != null is = "+hop);
			
				System.out.println("DQ word = "+DQ.getWord());
				//Now, look for alternatives and make them their own node.
				//System.out.println("Searching for alternatives (neighbor nodes) ...");
				for (int i = 0; i < DQ.getWord().length(); i++) {	
					StringBuffer sb = new StringBuffer(DQ.getWord());
					for (char j = 'a'; j <= 'z'; j++) {
						sb.setCharAt(i, j);
						//Building the Q at first hop(1):
						// Q = {source}
						if (T.find(sb.toString()) != null){													
							//R.insert(sb.toString(), start);<-- needed for Visited nodes later						
							++count;					
							//Enqueue each node onto the Q, setting dist to infinity, word to name of node (i.e. lose)
							System.out.println("Hop before Q.enqueue is = "+hop);
							System.out.println("sb = "+sb.toString());
							System.out.println("DQ Dist is = "+DQ.getDist());
							System.out.println("DQ Word is = "+DQ.getWord());
							if (DQ.getWord().compareTo(end) == 0) {
								BFS = true;
							}
							if (R.find(sb.toString()) == null)	{
								Q.enqueue(new QNode(DQ.getDist()+1,sb.toString()));
								R.insert(sb.toString(), DQ.getWord());	
							}
							//Insert all nodes into R StringMap (BackPointer): key = name, value = prev node
						}			
					}
				} //Append j to the end of the Q
				//Q.enqueue(new QNode(DQ.getDist()+1,DQ.getWord()));
			//}	
		}	
		System.out.println("After enqueue:  and count = "+count);

		//for (int i = 0;  i < count + 1; ++i) {
		//	if (Q.queue[i] != null)
		//		System.out.println(Q.queue[i].getDist()+" "+Q.queue[i].getWord());
		//}
		for (int i = 1;  i < count + 1; ++i) {
			int check = (Queue.front - 1 + Queue.size)%Queue.size;
			//if (Queue.queue[check] != null && Queue.queue[i] != null) {
			//if (R.table[check] != null && R.table[i] != null) {
				///System.out.println("Inside if --->  the i = "+i);
				///System.out.println("Inside if ---> R.table[i].getKey() = "+R.table[i].getKey());
				///System.out.println(R.table[i].getKey()+" "+R.table[i].getValue());
				//if (R.table[i].getValue() == end || R.table[i].getKey() == end) {
				//	BFS = true;
				//}
			//}
		}
		if (BFS) {
			System.out.println("Yay, A word Ladder is possible.");
			for (int i = 1;  i < count + 1; ++i) {
				int check = (Queue.front - 1 + Queue.size)%Queue.size;
				if (R.table[check] != null || R.table[i] != null) {
					///System.out.println("Inside if ---> R.table[i].getKey() = "+R.table[i].getKey());
					System.out.println(R.table[i].getValue());
				}
			}
		} else {
			System.out.println("Duh, Impossible.");
		}

	}		
}

//System.out.println(q.queue[i].getDist()+" "+q.queue[i].getWord());
//System.out.println(Queue.queue[Queue.front+i].getDist()+" "+Queue.queue[Queue.front+i].getWord());