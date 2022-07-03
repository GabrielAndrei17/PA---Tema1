import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Statistics {
	static class Task {
		public static final String INPUT_FILE = "statistics.in";
		public static final String OUTPUT_FILE = "statistics.out";
		
		//fq[c] = 1 if c appear in a string
		public static int[] fq = new int[26];
		public int n;

		//array of frequencies for a string
		public Elem[] arr;
		public class Elem{
			int[] fr;
			//lenght for the string
			int len;
			//sortBy will be used to sort the array of Elem
			int sortBy;

			public Elem(String st){
				fr = new int[26];

				len = st.length();
				if(len == 0)
					return;
				
				for (int i = 0; i < len; i++) {
					int c = (int)(st.charAt(i) - 'a');
					fr[c]++;
					fq[c] = 1;
				}

			}

			//will return true if this concatenated with el wil have c as dominant element
			public Boolean isDominant(int c, Elem el){
				if(fr[c] + el.fr[c] > (len + el.len)/2)
					return true;
				return false;
			}

			public void concat(Elem el){

				len = len + el.len;
				for(int i = 0; i < 26; i++){

					fr[i] += el.fr[i];
				}
				
			}

			//ratio of character c in the string
			public void sortBy(int c){
				sortBy = len  - 2 * fr[c];
			}
			
		}

		private void readInput() {
			try {
				
				BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
	
				//Read n
				String line = br.readLine();
				n = Integer.parseInt(line);

                arr = new Elem[n];
                int i = 0;

				//Read the strings
				while ((line = br.readLine()) != null) {
					
					arr[i] = new Elem(line);
					i++;
        		}

                //Print the result
                writeOutput(getResult());
				br.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		private void writeOutput(int result) throws IOException {
            PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
			pw.printf("%d\n", result);
			pw.close();
		}
		
		private int getResult() {
			int maxim = -1;

			//we take every caracter which appear in the array
			for(int c = 0; c < 26; c++)
				if(fq[c] == 1){

								
					int l = 0;
					Elem str = new Elem("");
					//we try to make the element dominant in a new string 

					for(int i = 0; i < n; i++){
					
						arr[i].sortBy(c);
					}

					//sort the array by the ratio of c
					Arrays.sort(arr, (a, b) -> a.sortBy - b.sortBy);

					for(int i = 0; i < n; i++){
						if(str.isDominant(c, arr[i])){

					 		str.concat(arr[i]);
					 		l++;
						}
					 	else break;
					}

					if(l > maxim){

						maxim = l;
					}
			}

			if(maxim == 0)
				return -1;
			return maxim;

		}
	
		public void solve() {
			readInput();
		}
	}
	public static void main(String[] args) {
		new Task().solve();
	}
}