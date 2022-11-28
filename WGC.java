public class WGC {
	
	private static int N;
	private static boolean [] visited;
	private static int start;
	private static int goal;
	//private static int [] pred;

	/** Return the bit of item i. A 0 indicates this item is on the left side of the river, and 1 indicates that this item is on the right side of the river. */
	private static int bit(int state, int i) {
		return (state >> i) & 0x01;
	}

	/* Return the state after moving across carrying item i. */
	private static int moveOther(int state, int i, int me) {
		int newMe = 1 - me;
		int newState = (state & ~(0x01 << 3)) | (newMe << 3);
		return (newState & ~(0x01 << i)) | (newMe << i);
	}

	/** Return the state after moving myself across the river. */
	private static int moveMe(int state, int me) {
		int newMe = 1 - me;
		return (state & ~(0x01 << 3)) | (newMe << 3);
	}

	/** Checks if state is valid. i.e., that the goat and the cabbage or the wolf and the goat are unattended. */
	private static boolean isValid(int state) {
		int c = bit(state, 0);
		int g = bit(state, 1);
		int w = bit(state, 2);
		int m = bit(state, 3);

		if (c == g && c != m) return false;
		if (g == w && g != m) return false;
		return true;
	}

	/** Do a depth first search from the current state. If goal is reachable, return true, otherwise return false. */
	private static boolean dfs (int state) {
		if (state < 0 || state >= N) return false;
		if (state == goal) return true;

		visited[state] = true;			// Mark this state as being visited.
		boolean ret = false;
		int me = bit(state, 3);
		for (int i = 0; i < 3; i++) {	
			int other = bit(state, i);
			if (me == other) {
				// I can carry the other item across the river.
				int newstate = moveOther(state, i, me);

				if (!visited[newstate] && isValid(newstate)) {
					ret = ret | dfs(newstate);
					pred[newstate] = state;
				}
			}
		}
		
		// Move alone.
		int newstate = moveMe(state, me);
		if (!visited[newstate] && isValid(newstate)) {
			ret = ret | dfs(newstate);
			pred[newstate] = state;
		}
		return ret;
	}
	
	/** Print the path from the start to state recursively -- first print the path from start to pred[state], and then print the current state. */
	private static void print(int state) {
		if (state == -1) return;

		print(pred[state]);
		System.out.println("STATE ---------> inside recursive print = "+state);
		String left = "";
		String right = "";
		String helper = "CGWM";
		//System.out.println("PRINT ---------> inside recursive print.");
		for (int i = 0; i < 4; i++) {
			int c = bit(state, i);
			if (c == 0) {
				left = left + helper.charAt(i);
				right = right + " ";
			}
			else {
				right = right + helper.charAt(i);
				left = left + " ";
			}
		}
		System.out.println(left + " | " + right);
	}

	public static void main(String [] args) {
		N = 16;
		visited = new boolean[N];
		pred = new int[N];
		for (int i = 0; i < N; i++) {
			pred[i] = -1;
		}

		start = 0;
		goal = 15;
		if (dfs(start)) {
			System.out.println("Yay!");
			print(goal);
		}
	}
}