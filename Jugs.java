import java.util.Scanner;
import java.lang.Math;
import java.util.*;

public class Jugs {
	
	private static int N;
	private static boolean [][] visited;
    //private static int[][] pred;
	private static int[][] PredA;
	private static int[][] PredB;
	private static String[][] predString;

    private static int [][] sum;
   
	private static int goal;
	private static int a;
	private static int b;
	private static int c;
	private static int d;
	private static int jug1;
	private static int jug2;

	//private static int [] state;

    /** Return the bit of item i. A 0 indicates this item is on the left side of the river, and 1 indicates that this item is on the right side of the river. */
	private static int goal(int c) {
        goal = c;
		return goal;
	}

    /* Return the state after filling Jug 1 */
	private static int[] fillJug1(int b) {
		System.out.println("jug1 inside fillJug1 is "+jug1+" b inside fillJug1 is = "+b);
		return new int[] {jug1, b};
	}
    /* Return the state after pouring from Jug1 into Jug2 */
    private static  int[] pourJug(int jug1, int jug2, int bMax) {
		int prejug2 = jug2;
		c = jug1;
		d = jug2;
		System.out.println("jug1 before pouring  is = "+jug1);
		System.out.println("jug2 before pouring  is = "+jug2);
		//If jug2 is already full, then we shouldn't be here.
		//Next, since jug2 is not full, pour jug1 into jug2
		jug2 += jug1;
		//First, check if adding overflows jug2 and update jug2
		if (jug2 >= bMax) {
			//cap jug2 at max
			jug2 = bMax;
		} 
		//Update jug1 if it poured into jug2
		int diff = jug2 - prejug2;
		jug1 = jug1 - diff;
		c = jug1;
		System.out.println("jug1 after pouring  is = "+c);
		d = jug2;
		System.out.println("jug2 after pouring  is = "+d);
		return new int[] {c, d};
    }
	/* Return the state after emptying jug2 */
	private static int[] emptyJug2(int a) {
		System.out.println("a inside emptyJug2 is = "+a);
		return new int[] {a, 0};
	}

	/* Do a depth first search from the current state. 
	   If goal is reachable, return true, otherwise return false. 
	   bool[][] table = new bool[n][m]
	   table[a][b] = true if you visited it before
	*/
	private static boolean dfs (int a, int b, int bMax) {
		System.out.println("Made it inside dfs.");
		//System.out.println("a = "+a);
		//System.out.println("b = "+b);
		int [] state = new int[N];
		int[][][] pred = new int[N][N][2];
		pred[c][d][0] = a;
		pred[c][d][1] = b;
		if ((a < 0 || b < 0) || (a >= N || b >= N)) return false;
		//if (sum[state[0]][state[1]]== goal) return true;
		if (a + b == goal) return true;
		
		boolean ret = false;

		System.out.println("Jug1 = "+pred[c][d][0]);
		System.out.println("Jug2 = "+pred[c][d][1]);
		System.out.println("Made it past Jug1 and Jug2 checks.");
        // Mark this state as being visited, initially (0,0).
		
		//returns 'true', which means must convert boolean to int for pred table
		System.out.println("visited[[jug1][jug2]] is "+visited[pred[c][d][0]][pred[c][d][1]]);
		System.out.println("-----------------------------");
		
		//First, we must fill jug1 to start
		if (pred[c][d][0] == 0) {
			state = fillJug1(b);
			pred[c][d][0] = state[0];
			System.out.println("Fill Jug1 is "+pred[c][d][0]);
			if (!visited[pred[c][d][0]][pred[c][d][1]]) {
				visited[pred[c][d][0]][pred[c][d][1]] = true;
				predString[pred[c][d][0]][pred[c][d][1]] = "Fill Jug 1 ";
				PredA[pred[c][d][0]][pred[c][d][1]] = pred[c][d][0];
				PredB[pred[c][d][0]][pred[c][d][1]] = pred[c][d][1];
				ret = ret | dfs(pred[c][d][0], pred[c][d][1], bMax);
			}
		}
		//Next, we check if we can pour from jug1 into jug2
		//First, make sure jug2 is not full
		if (pred[c][d][1] < bMax) {
			state = pourJug(pred[c][d][0], pred[c][d][1], bMax);
			pred[c][d][0] = state[0];
			pred[c][d][1] = state[1];
			//pourJug returns updated state of both Jug1 and Jug2
			if (!visited[pred[c][d][0]][pred[c][d][1]]) {
				visited[pred[c][d][0]][pred[c][d][1]] = true;
				predString[pred[c][d][0]][pred[c][d][1]] = "Pour Jug 1 -> Jug 2 ";
				PredA[pred[c][d][0]][pred[c][d][1]] = pred[c][d][0];
				PredB[pred[c][d][0]][pred[c][d][1]] = pred[c][d][1];
				ret = ret | dfs(pred[c][d][0], pred[c][d][1], bMax);
			}
		}
		System.out.println("After pourJug, Jug1 is "+pred[c][d][0]+" and Jug2 is "+pred[c][d][1]);
		// Next, check if we need to empty jug2 
		//This would only be if jug1 is not zero and jug2 is full)
		System.out.println("HEY, what is Jug2 right now? = "+pred[c][d][1]);
		if (pred[c][d][1] == bMax && pred[c][d][0] > 0) {
			System.out.println("*** Getting ready to empty Jug2. ***");
			state = emptyJug2(a);
			//pred[c][d][0] = state[0];
			pred[c][d][1] = state[1];
			System.out.println("YO, Jug2 after emptying Jug2 is "+pred[c][d][1]);
			if (!visited[pred[c][d][0]][pred[c][d][1]]) {
				visited[pred[c][d][0]][pred[c][d][1]] = true;
				predString[pred[c][d][0]][pred[c][d][1]] = "Empty Jug 2 ";
				PredA[pred[c][d][0]][pred[c][d][1]] = pred[c][d][0];
				PredB[pred[c][d][0]][pred[c][d][1]] = pred[c][d][1];
				ret = ret | dfs(pred[c][d][0],pred[c][d][1], bMax);
			}
		}
		return ret;
	}
	/** Print the path from the start to state recursively 
	 * -- first print the path from start to pred[state], 
	 * and then print the current state. 
	 * */
	private static void print(int x, int y) {
		System.out.println("Almost Done ---> inside recursive print.");
		if (x == 0 && y == 0) return;
		print(PredA[x][y], PredB[x][y]);
		// int jug1 = PredA[x][y];	
		// int jug2 = PredB[x][y];
		jug1 = x;
		jug2 = y;
		System.out.println("jug1 inside PRINT = "+jug1+" and jug2 = "+jug2);
		System.out.println(predString[jug1][jug2] + " [a = "+jug1+", b = "+jug2+"]");
	}
	/* 
	static void printSumTable(int[][] sum2, int a){
        for (int i = 0; i < a; ++i){
            for (int j = 0; j < a; ++j){
                System.out.print(" " + sum2[i][j]+" ");}
            System.out.println();}
        System.out.println("----------------");
    }*/
   
    static void printVisitedTable(boolean[][] visited2, int a, int b){
        for (int i = 0; i < a; ++i){
            for (int j = 0; j < b; ++j){
                System.out.print(" " + visited2[i][j]+" ");}
            System.out.println();}
        System.out.println("----------------");
    }
	static void printPredTable(int[][] pred, int a, int b){
        for (int i = 0; i < a + 1; ++i){
            for (int j = 0; j < b + 1; ++j){
                System.out.print(" " + pred[i][j]+" "); }
            System.out.println();}
        System.out.println("----------------");
    }
	public static void main(String [] args) {
		N = 16;
        Scanner kb = new Scanner(System.in);
        //For Jugs A and B, you are trying to fill enough to get C qty.
        //Using the rules of emptying/filling completely
        System.out.println("Enter A: ");
        int a1 = kb.nextInt();
        System.out.println("Enter B: ");
        int b1 = kb.nextInt();
        System.out.println("Enter C: ");
        int c1 = kb.nextInt();
		
		predString = new String[N][N];
        sum = new int[N][N];
		//visited = new boolean[a1 + 1][b1 + 1];
        visited = new boolean[a1 + 1][b1 + 1];
		for (int i = 0; i < (a1 + 1); ++i) {
			for (int j = 0; j < (b1 + 1); ++j) {
				visited[i][j] = false;
			}
		}
		PredA = new int[a1 + 1][b1 + 1];
		PredB = new int[a1 + 1][b1 + 1];
		//state = new int[N];
		
		goal = c1;
		a = a1;
		b = b1;
		jug1 = a;
		jug2 = b;
		int bMax = b;
		//System.out.println("visited INIT table: ");
		//printVisitedTable(visited, a, b);
		visited[0][0] = true;
		if (dfs(0,  0, bMax)) {
			System.out.println("Yay! Found a solution.");
			print(a, b);
		} else
			System.out.println("Impossible!");
		
		//System.out.println("visited POST table: ");
		//printVisitedTable(visited, a, b);
		//System.out.println("pred POST table: ");
		//printPredTable(pred, a, b);
		System.out.println("PredA POST table: ");
		printPredTable(PredA, a, b);
		System.out.println("PredB POST table: ");
		printPredTable(PredB, a, b);
	}
}