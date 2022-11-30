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
	private static int [] state;
    private static int [][] sum;

   	private static int goal;
	private static int a;
	private static int b;
	private static int c;
	private static int d;
	private static int jug1;
	private static int jug2;

	private static int goalA;
	private static int goalB;
	
	//private static int [] state;

    /** Return the bit of item i. A 0 indicates this item is on the left side of the river, and 1 indicates that this item is on the right side of the river. */
	private static int goal(int c) {
        goal = c;
		return goal;
	}

    /* Return the state after filling Jug 1 */
	private static int[] fillJug1(int b) {
		//System.out.println("jug1 inside fillJug1 is "+jug1+" b inside fillJug1 is = "+b);
		return new int[] {jug1, b};
	}
	/* Return the state after filling Jug 1 */
	private static int[] fillJug2(int a) {
		//System.out.println("jug2 inside fillJug1 is "+jug2+" a inside fillJug1 is = "+a);
		return new int[] {a, jug2};
	}
	/* Return the state after pouring from Jug1 into Jug2 */
    private static  int[] pourJugBtoA(int jug1, int jug2, int bMax) {
		//int prejug2 = jug2;
		int prejug1 = jug1;
		c = jug1;
		d = jug2;
		//System.out.println("jug1 before pouring  is = "+jug1);
		//System.out.println("jug2 before pouring  is = "+jug2);
		//If jug1 is already full, then we shouldn't be here.
		//Next, since jug1 is not full, pour jug2 into jug1
		//jug2 += jug1;
		jug1 += jug2;
		//First, check if adding overflows jug1 and update jug1
		if (jug1 >= bMax) {
			jug1 = bMax;
		}
		//Update jug2 if it poured into jug1
		int diff = jug1 - prejug1;
		jug1 = jug1 - diff;
		c = jug1;
		//System.out.println("jug1 after pouring  is = "+c);
		d = jug2;
		//System.out.println("jug2 after pouring  is = "+d);
		return new int[] {c, d};
    }
    /* Return the state after pouring from Jug1 into Jug2 */
    private static  int[] pourJugAtoB(int jug1, int jug2, int bMax) {
		int prejug2 = jug2;
		c = jug1;
		d = jug2;
		//System.out.println("jug1 before pouring  is = "+jug1);
		//System.out.println("jug2 before pouring  is = "+jug2);
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
		//System.out.println("jug1 after pouring  is = "+c);
		d = jug2;
		//System.out.println("jug2 after pouring  is = "+d);
		return new int[] {c, d};
    }
	/* Return the state after emptying jug1 */
	private static int[] emptyJug1(int b) {
		//System.out.println("b inside emptyJug2 is = "+b);
		return new int[] {0, b};
	}
	/* Return the state after emptying jug2 */
	private static int[] emptyJug2(int a) {
		//System.out.println("a inside emptyJug2 is = "+a);
		return new int[] {a, 0};
	}
	

	/* Do a depth first search from the current state. 
	   If goal is reachable, return true, otherwise return false. 
	*/
	private static boolean dfs (int a, int b, int bMax) {
		//System.out.println("Made it inside dfs.");
		//System.out.println("a = "+a);
		//System.out.println("b = "+b);
		//int [] state = new int[N];
		//int[][][] pred = new int[N][N][2];
		//pred[c][d][0] = a;
		//pred[c][d][1] = b;
		if ((a < 0 || b < 0) || (a >= N || b >= N)) return false;
		//visited[a][b] = true;
		if (a + b == goal) {
			goalA = a;
			goalB = b;
			return true;
		}
		boolean ret = false;
		//System.out.println("Jug1 = "+state[0]);
		//System.out.println("Jug2 = "+state[1]);
		//System.out.println("Made it past Jug1 and Jug2 checks.");
        
		//returns 'true', which means must convert boolean to int for pred table
		//System.out.println("visited[[jug1][jug2]] is "+visited[state[0]][state[1]]);
		//System.out.println("-----------------------------");
		//Next, we check if we can pour from jug1 into jug2
		//First, make sure jug2 is not full
		//if (state[1] < bMax) {
		//if (state[0] + state[1] == goal) return true;
		state = pourJugAtoB(state[0], state[1], bMax);
		//pourJug returns updated state of both Jug1 and Jug2
		if (!visited[state[0]][state[1]]) {
			visited[state[0]][state[1]] = true;
			predString[state[0]][state[1]] = "Pour Jug 1 -> Jug 2 ";
			PredA[state[0]][state[1]] = a;
			PredB[state[0]][state[1]] = b;
			ret = ret | dfs(state[0], state[1], bMax);
		}
		state = pourJugBtoA(state[0], state[1], bMax);
		//pourJug returns updated state of both Jug1 and Jug2
		if (!visited[state[0]][state[1]]) {
			visited[state[0]][state[1]] = true;
			predString[state[0]][state[1]] = "Pour Jug 2 -> Jug 1 ";
			PredA[state[0]][state[1]] = a;
			PredB[state[0]][state[1]] = b;
			ret = ret | dfs(state[0], state[1], bMax);
		}
		//First, we must fill jug1 to start
		//if (state[0] == 0) {
		state = fillJug1(b);
		//pred[c][d][0] = state[0];
		//System.out.println("Fill Jug1 is "+state[0]);
		if (!visited[state[0]][state[1]]) {
			visited[state[0]][state[1]] = true;
			predString[state[0]][state[1]] = "Fill Jug 1 ";
			PredA[state[0]][state[1]] = a;
			PredB[state[0]][state[1]] = b;
			ret = ret | dfs(state[0], state[1], bMax);
		}
		//}
		state = fillJug2(a);
			//pred[c][d][0] = state[0];
			//System.out.println("Fill Jug1 is "+state[0]);
			if (!visited[state[0]][state[1]]) {
				visited[state[0]][state[1]] = true;
				predString[state[0]][state[1]] = "Fill Jug 2 ";
				PredA[state[0]][state[1]] = a;
				PredB[state[0]][state[1]] = b;
				ret = ret | dfs(state[0], state[1], bMax);
			}
		
		//}
		state = emptyJug1(b);
		//System.out.println("YO, Jug1 after emptying Jug1 is "+state[1]);
		if (!visited[state[0]][state[1]]) {
			visited[state[0]][state[1]] = true;
			predString[state[0]][state[1]] = "Empty Jug 1 ";
			PredA[state[0]][state[1]] = a;
			PredB[state[0]][state[1]] = b;
			ret = ret | dfs(state[0],state[1], bMax);
		}
		//System.out.println("After pourJug, Jug1 is "+state[0]+" and Jug2 is "+state[1]);
		// Next, check if we need to empty jug2 
		//This would only be if jug1 is not zero and jug2 is full)
		//System.out.println("HEY, what is Jug2 right now? = "+state[1]);
		//if (state[1] == bMax && state[0] > 0) {
		//if (state[0] + state[1] == goal) return true;
		//System.out.println("*** Getting ready to empty Jug2. ***");
		state = emptyJug2(a);
		//System.out.println("YO, Jug2 after emptying Jug2 is "+state[1]);
		if (!visited[state[0]][state[1]]) {
			visited[state[0]][state[1]] = true;
			predString[state[0]][state[1]] = "Empty Jug 2 ";
			PredA[state[0]][state[1]] = a;
			PredB[state[0]][state[1]] = b;
			ret = ret | dfs(state[0],state[1], bMax);
		}
		//}
		return ret;
	}
	/** Print the path from the start to state recursively 
	 * -- first print the path from start to pred[state], 
	 * and then print the current state. 
	 * */
	private static void print(int x, int y) {
		//System.out.println("Almost Done ---> inside recursive print.");
		//System.out.println(x + " " + y);
		if (x == 0 && y == 0) return;
	
		print(PredA[x][y], PredB[x][y]);
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
		N = 1000;
        Scanner kb = new Scanner(System.in);
        //For Jugs A and B, you are trying to fill enough to get C qty.
        //Using the rules of emptying/filling completely
        System.out.print("Enter A: ");
        int a1 = kb.nextInt();
        System.out.print("Enter B: ");
        int b1 = kb.nextInt();
        System.out.print("Enter C: ");
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
		state = new int[N];
		
		goal = c1;
		a = a1;
		b = b1;
		jug1 = a;
		jug2 = b;
		int bMax = b;
		// Mark this state as being visited, initially (0,0).
		visited[0][0] = true;
		if (dfs(0,  0, bMax)) {
			System.out.println("Yay! Found a solution.");
			print(goalA, goalB);
		} else
			System.out.println("Impossible!");
		
		//System.out.println("visited POST table: ");
		//printVisitedTable(visited, a, b);
		//System.out.println("pred POST table: ");
		//printPredTable(pred, a, b);
		//System.out.println("PredA POST table: ");
		//printPredTable(PredA, a, b);
		//System.out.println("PredB POST table: ");
		//printPredTable(PredB, a, b);
	}
}