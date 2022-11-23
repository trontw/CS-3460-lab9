import java.util.Scanner;
import java.lang.Math;
import java.util.*;

public class Jugs {
	
	private static int N;
	private static boolean [][] visited;
    private static boolean [][] pred;
    private static int [][] sum;
   
	private static int start;
	private static int goal;
	private static int a;
	private static int b;
	//private static int [] pred;
	private static int [] state;
	private static int [] newstate;

	private static int [] jug1;
	private static int [] jug2;
	static int [] stateA;
	static int [] stateB;
	static int stateA2;
	static int stateB2;

    /** Return the bit of item i. A 0 indicates this item is on the left side of the river, and 1 indicates that this item is on the right side of the river. */
	private static int goal(int c) {
        goal = c;
		return goal;
	}

    /* Return the state after filling Jug 1 */
	private static int[] fillJug1(int stateA, int a) {
		System.out.println("a inside fillJug1 is = "+a);
		state[0] = a;
		return state;
	}
    /* Return the state after pouring from Jug 1 into Jug 2 */
    private static  int[] pourJug(int jug1, int jug2, int bMax) {
		int prejug2 = jug2;
		//If jug2 is already full, then we chouldn't be here.
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
		state[0] = jug1;
		System.out.println("jug1 inside pourJug  is = "+state[0]);
		state[1] = jug2;
		System.out.println("jug2 inside pourJug  is = "+state[1]);
		return state;
    }
	/* Return the state after emptying jug2 */
	private static int[] emptyJug2(int stateB, int b) {
		System.out.println("b inside emptyJug2 is = "+b);
		state[1] = 0;
		return state;
	}

	/** Checks if state is valid. i.e., that the goat and the cabbage or the wolf and the goat are unattended. */
	private static boolean isValid(int state) {
		return true;
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
		if (a < 0 && b < 0 || a >= N && b >= N) return false;
		System.out.println("Made it past a, b checks.");
		System.out.println("stateA2 = "+state[0]);
		System.out.println("stateB2 = "+state[1]);
		System.out.println("goal is = "+goal);
		//if (sum[state[0]][state[1]]== goal) return true;
		if (state[0] + state[1] == goal) return true;
		System.out.println("Made it past stateA2 and B2 checks.");
        // Mark this state as being visited.
		visited[state[0]][state[1]] = true;
		//System.out.println("visited[[jug1][jug2]] "+visited[state[0]][state[1]]);
		boolean ret = false;
		//First, we must fill jug1 to start
		if (state[0] == 0) {
			state = fillJug1(state[0], a);
			System.out.println("Fill Jug is "+state[0]);
			newstate = state;
			//if (!visited[newstate[start2][c]] && isValid(newstate[start2][c])) {
			if (!visited[newstate[0]][state[0]]) {
				ret = ret | dfs(a, b, bMax);
			   	pred[newstate[0]][state[0]] = true;
			}
		}
		//Next, we check if we can pour from jug1 into jug2
		//First, make sure jug2 is not full
		if (state[1] < bMax) {
			state = pourJug(state[0], state[1], bMax);
			newstate = state;
			if (!visited[newstate[0]][newstate[1]]) {
				ret = ret | dfs(a, b, bMax);
				pred[newstate[0]][newstate[1]] = true;
			}
		}
		System.out.println("After pourJug, stateA is "+state[0]+" and stateB is "+state[1]);
		// Next, check if we need to empty jug2 
		//This would only be if jug1 is not zero and jug2 is full)
		System.out.println("HEY, what is stateB2 right now? = "+state[1]);
		if (state[1] == bMax && state[0] > 0) {
			System.out.println("*** Getting ready to empty jug2. ***");
			state = emptyJug2(state[1], b);
			newstate = state;
			System.out.println("YO, stateB2 after emptying jug2 is "+state[1]);
			if (!visited[state[0]][newstate[1]]) {
				ret = ret | dfs(a, b, bMax);
				pred[state[0]][newstate[1]] = true;
		}
	}
		//int newstate = moveMe(state, me);
		//if (!visited[newstate] && isValid(newstate)) {
		//	ret = ret | dfs(newstate);
		//	pred[newstate] = state;
		//}
		return ret;
	}

	/** Print the path from the start to state recursively -- first print the path from start to pred[state], and then print the current state. */
	private static void print(int state) {
		if (state == -1) return;

		//print(pred[(int) state]);

		String left = "";
		String right = "";
		String helper = "CGWM";

		System.out.println(left + " | " + right);
	}
    static void printSumTable(int[][] sum2, int a){
        for (int i = 0; i < a; ++i){
            for (int j = 0; j < a; ++j){
                System.out.print(" " + sum2[i][j]+" ");    
            }
            System.out.println();
        }
        System.out.println("----------------");
    }
   
    static void printStateTable(int[][] grid){
        for (int i = 0; i < grid.length; ++i){
            for (int j = 0; j < 2; ++j){
                System.out.print(" " + grid[i][j]+" ");    
            }
            System.out.println();
        }
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
 
        sum = new int[N][N];
		//int[][] grid = new int[8][8]; 
		visited = new boolean[a1 + 1][b1 + 1];
        pred = new boolean[a1 + 1][b1 + 1];
		pred[0][0] = false;
		state = new int[N];
		newstate = new int[N];
		visited[0][0] = true;
		goal = c1;
		a = a1;
		b = b1;
		start = 0;
		int bMax = b;
		if (dfs(a,  b, bMax)) {
			System.out.println("Yay!");
			print(goal);
		}
        //for (int i = 0; i < a; i++) {
        //    for (int j = 0; j < b; j++) {
		//	    sum[i][j] = i + j;
        //    }
		//}
        //System.out.println("Sum table: ");
        //if (a > b)
        //    printSumTable(sum, a);
        //else 
        //    printSumTable(sum, b);
        /*System.out.println("State table: ");
            printStateTable(grid);*/
	}
}