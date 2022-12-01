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
		//for (StringNode curr = R.table[i]; curr != null; curr = curr.getNext()){
		System.out.println("Made it to INIT routine.");
		if (T.find(start) != null){
			hop = 1;
			int source = 0;
			System.out.println(start + " is valid.");
			//Q.queue[0].setDist(0);
			//Q.queue[0].setWord(start);
			//System.out.println("Source word is = "+Q.queue[0].getWord());
			//Now, look for alternatives and make them their own node.
			System.out.println("Searching for alternatives (neighbor nodes) ...");
			for (int i = 0; i < start.length(); i++) {	
				StringBuffer sb = new StringBuffer(start);
				for (char j = 'a'; j <= 'z'; j++) {
					sb.setCharAt(i, j);
					//Building the Q at first hop(1):
					// Q = {source}
					if (T.find(sb.toString()) != null){													
						//R.insert(sb.toString(), start);<-- needed for Visited nodes later
						//System.out.println("Found sb = "+sb.toString());
						++count;
						//Enqueue each node onto the Q, setting dist to infinity, word to name of node (i.e. lose)
						if (sb.toString().compareTo(start) != 0)	
							Q.enqueue(new QNode(infinity,sb.toString()));
						//Insert all nodes into R StringMap (BackPointer): key = name, value = prev node
						R.insert(sb.toString(), start);	
					}			
				}
			} //Last, put source node onto Q (FIFO)
			Q.enqueue(new QNode(source,start));
		}		
		//System.out.println("Source word is = "+Q.queue[1].getWord());	

		while (!Q.isEmpty()){
			//Get next node in the Q and see if valid
			String temp = isValid(start, end);
			if ( temp != null){
				System.out.println("Good word is = "+temp);
			}	
			Q.dequeue();
		}
			/*    sk = new Scanner(System.in);
			while (sk.hasNext()) {
				String word = sk.next();
				if (x.find(word))
					System.out.println(word + " is correct.");
				else {
						System.out.println("Suggesting alternatives ...");
			
						for (int i = 0; i < word.length(); i++) {
							StringBuffer sb = new StringBuffer(word); 
							for (char j = 'a'; j <= 'z'; j++) {
							sb.setCharAt(i, j);
							if (x.find(sb.toString()))
										System.out.println(sb.toString());
							}
					}
				}
			} */
		//	StringMap s = new StringMap();
		//  find all possible variations of the 'start' word and place in Q
			//i = next node in Q
			//s = s.insert(start, s.table[5].setValue("lost"););
			
		//}
		System.out.println("After enqueue:  and count = "+count);
		

		for (int i = 0;  i < count + 1; ++i) {
			if (Q.queue[i] != null)
				System.out.println(Q.queue[i].getDist()+" "+Q.queue[i].getWord());
		}
		for (int i = 1;  i < count + 1; ++i) {
			if (R.table[i] != null) {
				System.out.println("Inside if --->  the i = "+i);
				System.out.println("Inside if ---> R.table[i].getKey() = "+R.table[i].getKey());
				System.out.println(R.table[i].getKey()+" "+R.table[i].getValue());
			}
		}
		//System.out.println(R.table[1].getKey()+" "+R.table[1].getValue());
	}
}

//System.out.println(q.queue[i].getDist()+" "+q.queue[i].getWord());
//System.out.println(Queue.queue[Queue.front+i].getDist()+" "+Queue.queue[Queue.front+i].getWord());