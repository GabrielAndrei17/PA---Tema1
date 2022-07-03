import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Walsh {
	static class Task {
		public static final String INPUT_FILE = "walsh.in";
		public static final String OUTPUT_FILE = "walsh.out"; 

		private void readInput() {
			try {
				String line;
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
	
				//Read n
				line = br.readLine();
				String[] array = line.split(" ");
				int n = Integer.parseInt(array[0]);

				//Read every pair
				while ((line = br.readLine()) != null) {
					array = line.split(" ");
					int a = Integer.parseInt(array[0]);
					int b = Integer.parseInt(array[1]);

					//Write the output of that pair
					writeOutput(getResult(n, a, b), pw);
        		}


				br.close();
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		private void writeOutput(long result, PrintWriter pw) throws IOException {
			pw.printf("%d\n", result);
			
		}
		
		private int getResult(int n, int a, int b) {

			if(divideetImpera(n, a, b) % 2 == 0)
				return 0;
			return 1;

		}

		private int divideetImpera(long n, long x, long y){

			if(n == 1){

				if(x == 1 && y == 1)
					return 0;
				if(x == 1 && y == 2)
					return 0;
				if(x == 2 && y == 1)
					return 0;
				if(x == 2 && y == 2)
					return 1;
				return -1;
			}
			long half = n/2;

			if(x <= half && y <= half)
            	return divideetImpera(n / 2, x, y);

        	if(x <= half && y > half)
            	return divideetImpera(n / 2, x, y - half);
        
        	if(x > half && y <= half)
            	return divideetImpera(n / 2, x - half, y);
        
        	if(x > half && y > half)
            	return 1 + divideetImpera(n / 2, x - half, y - half);

        	return -1;

		}
		public void solve() {
			readInput();
		}
	}
	public static void main(String[] args) {
		new Task().solve();
	}
}