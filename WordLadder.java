import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class WordLadder {
	private static String start;
	private static String end;
	
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
	private static int track = 0;
	/**
		Constructor: s is the initial size of the heap.
	*/
	private static void WordLadder(String s, String e) {
		start = s;
		end = e;
	}

	private static boolean BFS() {
		/*
		   Solution to find the shortest set of words that transforms the 
		   start word to the end word.
		 * To set to infinity, set the value much greater than the total number of 
		 * words in the Dictionary. (which is 3352)
		 */
		//Initialize the visited nodes to StringMap R
		R = new StringMap();
		Queue Q = new Queue();
		QNode DQ;
		//StringNode[] dist = new StringNode[6000];
		int hop = 0;
		int source = 0;
		//for (StringNode curr = R.table[i]; curr != null; curr = curr.getNext()){
		//System.out.println("Made it to INIT routine.");

		//Enqueue input (initiall dist = 0, word = lose)
		Q.enqueue(new QNode(0,start));
		R.insert(start, start);
		//System.out.println("numElements is = "+Queue.numElements);
		int dist = 0;
		while (!Q.isEmpty()){
			DQ = Q.dequeue();
			//Increment the distance (dist) to next node ring
			//++source;		
			//++hop;
			dist = DQ.getDist();
			DQ.setDist(dist + 1);	
			//Now, look for alternatives and make them their own node.
			//System.out.println("Searching for alternatives (neighbor nodes) ...");
			for (int i = 0; i < DQ.getWord().length(); i++) {	
				StringBuffer sb = new StringBuffer(DQ.getWord());
				for (char j = 'a'; j <= 'z'; j++) {
					sb.setCharAt(i, j);
					//Building the Q at first hop(1):
					if (T.find(sb.toString()) != null){													
						++count;					
						//Enqueue each node onto the Q, setting dist to infinity, word to name of node (i.e. lose)
						if (DQ.getWord().compareTo(end) == 0) {
							//System.out.println("Hop before Q.enqueue is = "+hop);
							System.out.println("sb = "+sb.toString());
							System.out.println("DQ Dist is = "+DQ.getDist());
							System.out.println("DQ Word is = "+DQ.getWord());
							//R.insert(sb.toString(), end);
							return true;
						}
						//Insert all nodes into R StringMap (BackPointer): key = name, value = prev node
						//First check to make sure input is not already stored in R
						//Then enqueue the sb.toString() and add it to the Key in R
						if (R.find(sb.toString()) == null)	{
							Q.enqueue(new QNode(DQ.getDist(),sb.toString()));
							R.insert(sb.toString(), DQ.getWord());	
						}						
					}			
				}	
			} 
		}
		return false;	
	}
	
	public static  void print(String end2){
		//System.out.println("The input end2 is: "+end2);
		StringNode tt = R.find(end2);
		if (end2 == start) {
			System.out.println(tt.getKey());
			return;
		}
		//System.out.println("Key = "+tt.getKey());
		//System.out.println(tt.getKey());
		print(tt.getValue());
		System.out.println(tt.getKey());
	}

	public static void main(String [] args) throws IOException {
		// Loading the dictionary of words into the StringMap T.
		T = new StringMap();
		R = new StringMap();
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
		
		if (BFS()) {
			System.out.println("Yay, A word Ladder is possible.");
			print(end);
		} else {
			System.out.println("Duh, Impossible.");
		}
	}		
}