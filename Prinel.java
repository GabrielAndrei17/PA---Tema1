import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Prinel {
	static class Task {
		public static final String INPUT_FILE = "prinel.in";
		public static final String OUTPUT_FILE = "prinel.out";
        
        public static int[] steps;

		private void readInput() {
			try {
				
				BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
	
				//Read n
				String line = br.readLine();
				String[] array = line.split(" ");
				int n = Integer.parseInt(array[0]);
                int k = Integer.parseInt(array[1]);

                int[] costs = new int[n];
                int[] points = new int[n];

                //steps[input] -> number of steps to generate input from 1 
                steps = new int[1000001];

				//Read stepss
				line = br.readLine();
				array = line.split(" ");

                //lastGen = last generated steps
                int lastGen = 1;
                steps[1] = 0;

                for(int i = 0; i < n; i++){

                    int input = Integer.parseInt(array[i]);
                    costs[i] = getResult(input, lastGen);

                    lastGen = input;
                    //costs[i] = 1;
                }
                
                //Read points
                line = br.readLine();
					
                array = line.split(" ");

				for(int i = 0; i < n; i++)
                    points[i] = Integer.parseInt(array[i]);

                int sum = dP(n, k, points, costs);

				//Write the result

				writeOutput(sum);

				br.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		private void writeOutput(long result) throws IOException {
			
            PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
            pw.printf("%d\n", result);
            pw.close();
			
		}
		
        // get minimum number of steps to write input from 1
		private int getResult(int input, int lastGen){
            if(input <= 1)
                return 0;

            //if it was already generated
			if(steps[input] != 0){

                return steps[input];
            }

            for(int i = lastGen + 1; i <= input; i++){
                
                //search the previous in the number's divisors
                //and take the one with minimum steps
                int minim = steps[i - 1];
                for(int d = 2; d * d <= i; d++){
                    if(i % d == 0){
                
                        int aux = steps[i - i/d];
                        if(minim > aux)
                            minim = aux;
                
                        aux = steps[i - d];
                        if(minim > aux)
                            minim = aux;
                    }
                }

                //save the number of steps in a vector
                steps[i] = 1 + minim;
            }

            return steps[input];
		}

        //DP as backpack problem
        private int dP(int n, int c, int[] val, int[] cost){

            int[][] mat = new int[n + 1][c + 1];

            for (int r = 0; r < c + 1; r++) {
                mat[0][r] = 0;
            }
            for (int i = 0; i < n + 1; i++) {
                mat[i][0] = 0;
            }

            for (int num = 1; num <= n; num++) {

                for (int capacity = 1; capacity <= c; capacity++) {

                    int maxValWithoutCurr = mat[num - 1][capacity];
                    int maxValWithCurr = 0;
                    
                    // we use num -1 to account for the extra row at the top
                    int current = cost[num - 1];

                    // if we can fit the current number
                    if (capacity >= current) {
                        maxValWithCurr = val[num - 1];
                        
                        int remainingCapacity = capacity - current; // remainingCapacity must be at least 0
                        maxValWithCurr += mat[num - 1][remainingCapacity]; // add the maximum value obtainable with the remaining capacity
                    }
                    
                    mat[num][capacity] = Math.max(maxValWithoutCurr, maxValWithCurr); // pick the larger of the two
                }
            }

            return mat[n][c];
        }
		
		public void solve() {
			readInput();
		}
	}
	public static void main(String[] args) {
		new Task().solve();
	}
}