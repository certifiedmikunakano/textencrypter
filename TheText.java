package test;
import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import javax.imageio.*;

public class TheText {

	private int safePrime;
	private int[] safePrimes = {5, 7, 11, 23, 47, 59, 83, 107, 167, 179, 227, 263, 347, 359, 383, 467, 479, 503, 563, 587, 719, 839, 863, 887, 983, 1019, 1187, 1283, 1307, 1319, 1367, 1439};
	private File textFile;
	private String fileName;
	private ArrayList <Character> chars = new ArrayList<Character>();
	private int targetLength;
	
	public ArrayList<Character> getCharacterArray () throws FileNotFoundException {
		File f = new File (fileName);
		Scanner scan = new Scanner(f);
		ArrayList<Character> baka = new ArrayList<Character>();
		while (scan.hasNext()) {
			String str = scan.nextLine();
			for (int i = 0; i < str.length(); i++) {
				if (!(Character.isWhitespace(str.charAt(i)))) {
					baka.add(str.charAt(i));
				}
			}
		}
		return baka;
	}
	
	public TheText (String fn) {
		fileName = fn;
	}
	
	public void encipher () throws IOException {
		File f = new File (fileName);
		Scanner scan = new Scanner(f);
		while (scan.hasNext()) {
			String str = scan.nextLine();
			for (int i = 0; i < str.length(); i++) {
				if (!(Character.isWhitespace(str.charAt(i)))) {
					chars.add(str.charAt(i));
				}
			}
		}
		int currentLength = chars.size();
		for (int i = 0; i < safePrimes.length; i++) {
			if (Math.pow(safePrimes[i] - 1, 2) >= currentLength) {
				safePrime = safePrimes[i];
				targetLength = (int) Math.pow(safePrime - 1, 2);
				break;
			}
		}
		for (int j = 0; j < targetLength - currentLength; j++) {
			Random random = new Random();
			char c = (char) (random.nextInt(26) + 'a');
			chars.add(c);
		}
		
		ArrayList <Integer> proots = new ArrayList <Integer> ();
	   	 for (int i = 0; i < safePrime - 1; i++) {
	  		  proots.add(i);
	   	 }
	   	 for (int i = 0; i < safePrime; i++) {
	  		  int j = proots.indexOf((i*i) % safePrime);
	  		  if (j != -1) {
	  			  proots.remove(j);
	  		  }
	  		 
	   	 }
	   	 Random random = new Random();
	   	int randomIndex1 = random.nextInt(proots.size());
 		 int randomIndex2 = random.nextInt(proots.size());
 		 int randomIndex3 = random.nextInt(proots.size());
 		 int randomIndex4 = random.nextInt(proots.size());
 		 int randomIndex5 = random.nextInt(proots.size());
 		 int randomIndex6 = random.nextInt(proots.size());

 		 int proot1 = proots.get(randomIndex1);
 		 int proot2 = proots.get(randomIndex2);
 		 int proot3 = proots.get(randomIndex3);
 		 int proot4 = proots.get(randomIndex4);
 		 int proot5 = proots.get(randomIndex5);
 		 int proot6 = proots.get(randomIndex6);
 		 
		ArrayList<Character> scrambled1 = scramble(chars, proot1);
		ArrayList<Character> scrambled2 = scramble2(scrambled1, proot2);
		ArrayList<Character> scrambled3 = scrambleD1(scrambled2, proot3, proot4);
		ArrayList<Character> finalScrambled = scrambleD2(scrambled3, proot5, proot6);

		chars = finalScrambled;
		System.out.println("scrambling is done");
		
		
		PrintWriter pw = new PrintWriter(fileName);
		
		for (int i = 0; i < safePrime - 1; i++) {
			StringBuilder line = new StringBuilder();
			for (int j = 0; j < safePrime - 1; j++) {
				
				line.append(coords(i, j, chars));
			}
			
			pw.println(line.toString());
		}
		pw.close();
		System.out.println("writing done");
		
		String secretCode = proot1 + " " + proot2 + " " + proot3 + " " + proot4 + " " + proot5 + " " + proot6;
		 
 		System.out.println("Your decryption code is: \n" + secretCode + ". \nKeep this code to yourself but don't lose it!");
		
 	
		
	}
	
	public void decipher() throws IOException {
		File f = new File (fileName);
		Scanner scan = new Scanner(f);
		Scanner saulGoodman = new Scanner(System.in);
		while (scan.hasNext()) {
			String str = scan.nextLine();
			for (int i = 0; i < str.length(); i++) {
				if (!(Character.isWhitespace(str.charAt(i)))) {
					chars.add(str.charAt(i));
				}
			}
		}
		safePrime = (int) (Math.sqrt(chars.size()) + 1);
		System.out.println("Enter the decryption code for that image: ");
	  	 
	   	 String codestring = saulGoodman.nextLine();
	   	 String[] rootsArray = codestring.split(" ");
	    
	   	 int root1 = Integer.parseInt(rootsArray[0]);
	   	 int root2 = Integer.parseInt(rootsArray[1]);
	   	 int root3 = Integer.parseInt(rootsArray[2]);
	   	 int root4 = Integer.parseInt(rootsArray[3]);
	   	 int root5 = Integer.parseInt(rootsArray[4]);
	   	 int root6 = Integer.parseInt(rootsArray[5]);
	   	 
	   	ArrayList<Character> unscrambled1 = unscrambleD2 (chars, root5, root6);
	   	ArrayList<Character> unscrambled2 = unscrambleD1 (unscrambled1, root3, root4);
	   	ArrayList<Character> unscrambled3 = unscrambleRows (unscrambled2, root2);
	   	ArrayList<Character> finalUnscrambled = unscrambleCols (unscrambled3, root1);

	   	chars = finalUnscrambled;
	   	 System.out.println("unscrambling is done");
		
		PrintWriter pw = new PrintWriter(fileName);
		
		for (int i = 0; i < safePrime - 1; i++) {
			StringBuilder line = new StringBuilder();
			for (int j = 0; j < safePrime - 1; j++) {
				
				line.append(coords(i, j, chars));
			}
			
			pw.println(line.toString());
		}
		pw.close();
		System.out.println("writing done");
	}
	
	private  ArrayList<Character> encrypt (ArrayList <Character> original, int pr) {
	  	  ArrayList<Character> newArr = new ArrayList<Character>();
	  	  for (int i = 0; i < original.size(); i++) {
	  		  newArr.add(original.get(prToThe(pr, i) - 1));
	  	  }
	  	  return newArr;
		}
	    
		private  ArrayList<Character> encrypt2 (ArrayList <Character> original, int pr) {
	  	  ArrayList<Character> newArr = new ArrayList<Character>();
	  	  for (int i = 0; i < original.size(); i++) {
	  		  newArr.add(original.get(prToThe(pr, i) - 1));
	  	  }
	  	  return newArr;
		}
	
	
		private ArrayList<Character> scramble (ArrayList<Character> original, int pr) {
	  		  ArrayList<Character> scrambled = new ArrayList<Character>();
	  		 for (int i = 0; i < Math.pow(safePrime - 1, 2); i++) {
	  			 scrambled.add('a');
	  		 }
	  		  
	  		  //scramble each row
	  		  for (int y = 0; y < safePrime - 1 ; y++) {
	  			  ArrayList<Character> origRow = new ArrayList <Character> ();
	  			  for (int j = 0; j < safePrime - 1; j++) {
	  				  
	  				  char c = coords (j, y, original);
	  				  origRow.add(c);
	  			  }
	  			 
	  			  ArrayList<Character> scrambledRow = encrypt (origRow, pr);
	  			  for (int j = 0; j < safePrime - 1; j++) {
	  				  
	  				  char newChar = scrambledRow.get(j);
	  				  scrambled.set(index(j, y), newChar);
	  			  }
	  		  }
	  		  return scrambled;
	  	  }
		
		
		
		private ArrayList<Character> scramble2 (ArrayList<Character> original, int pr) {
	  		  ArrayList<Character> scrambled = new ArrayList<Character>();
	  		for (int i = 0; i < Math.pow(safePrime - 1, 2); i++) {
	  			 scrambled.add('a');
	  		 }
	  		//scramble each column
		  	  for (int x = 0; x < safePrime - 1; x++) {
		  		  ArrayList<Character> origCol = new ArrayList <Character> ();
		  		  for (int j = 0; j < safePrime - 1; j++) {
		  			  char c = coords (x, j, original);
		  			  origCol.add(c);
		  		  }
		  		 
		  		  ArrayList<Character> scrambledCol = encrypt2 (origCol, pr);
		  		  for (int j = 0; j < safePrime - 1; j++) {
		  			  char newChar = scrambledCol.get(j);
		  			  scrambled.set(index(x, j), newChar);
		  		  }
		  	  }
		  	  return scrambled;
	  	  }
		
		private char coords (int x, int y, ArrayList <Character> al) {
			int s = safePrime - 1;
			return al.get(s * x + y);
			
		}
	    
		private int index (int x, int y) {
			int s = safePrime - 1;
			return s * x + y;
		}
		
		private ArrayList<Character> scrambleD1 (ArrayList<Character> original, int pr1, int pr2) {
	  		  ArrayList<Character> scrambled = new ArrayList<Character>();
	  		for (int i = 0; i < Math.pow(safePrime - 1, 2); i++) {
	  			 scrambled.add('a');
	  		 }
	     	  //scramble each row
	     	  for (int y = 0; y < safePrime - 1; y++) {
	     		  ArrayList<Character> origRow = new ArrayList <Character> ();
	     		  for (int j = 0; j < safePrime - 1; j++) {
	     			  int modifiedY = (y + (prToThe(pr1, j))) % (safePrime - 1);
	     			  
	     			  char c = coords(j, modifiedY, original);
	     			  origRow.add(c);
	     		  }
	     		 
	     		  ArrayList<Character> scrambledRow = encrypt (origRow, pr2);
	     		  for (int j = 0; j < safePrime - 1; j++) {
	     			  int modifiedY = (y + (prToThe(pr1, j))) % (safePrime - 1);
	     			  char newChar = scrambledRow.get(j);
	     			  scrambled.set(index(j, modifiedY), newChar);
	     		  }
	     	  }
	     	  return scrambled;
	       }
		
		private ArrayList<Character> scrambleD2 (ArrayList<Character> original, int pr1, int pr2) {
	  		  ArrayList<Character> scrambled = new ArrayList<Character>();
	  		for (int i = 0; i < Math.pow(safePrime - 1, 2); i++) {
	  			 scrambled.add('a');
	  		 }
	 		  //scramble each column
	 		  for (int x = 0; x < safePrime - 1; x++) {
	 			  ArrayList<Character> origCol = new ArrayList <Character> ();
	 			  for (int j = 0; j < safePrime - 1; j++) {
	 				  int modifiedX = (x + (prToThe(pr1, j))) % (safePrime - 1);
	 				  char c = coords(modifiedX, j, original);
	 				  origCol.add(c);
	 			  }
	 			 
	 			  ArrayList<Character> scrambledCol = encrypt2 (origCol, pr2);
	 			  for (int j = 0; j < safePrime - 1; j++) {
	 				  int modifiedX = (x + (prToThe(pr1, j))) % (safePrime - 1);
	 				  char newChar = scrambledCol.get(j);
	 				  scrambled.set(index(modifiedX, j), newChar);
	 			  }
	 		  }
	 		  return scrambled;
	   	}

		
	
	
	
	
	private int prToThe (int base, int k) {
	  	  int value = 1;
	  	  for (int i = 0; i < k; i++) {
	  		  value*=base;
	  		  value%=safePrime;
	  	  }
	  	  return value;
		}
	
	private int discreteLogBasePrMod1019_ (int base, int k) {
		  return discreteLogarithm(base, k, safePrime);
	}
	
	static int discreteLogarithm(int a, int b, int m)
	{
    	int n = (int) (Math.sqrt (m) + 1);
 
    	// Calculate a ^ n
    	int an = 1;
    	for (int i = 0; i < n; ++i)
        	an = (an * a) % m;
 
    	int[] value=new int[m];
 
    	// Store all values of a^(n*i) of LHS
    	for (int i = 1, cur = an; i <= n; ++i)
    	{
        	if (value[ cur ] == 0)
            	value[ cur ] = i;
        	cur = (cur * an) % m;
    	}
 
    	for (int i = 0, cur = b; i <= n; ++i)
    	{
        	// Calculate (a ^ j) * b and check
        	// for collision
        	if (value[cur] > 0)
        	{
            	int ans = value[cur] * n - i;
            	if (ans < m)
                	return ans;
        	}
        	cur = (cur * a) % m;
    	}
    	return -1;
	}
	
	//use pr1
   	private  ArrayList<Character> decryptRows (ArrayList <Character> original, int pr) {
 		  ArrayList<Character> newArr = new ArrayList<Character>();
 		  for (int i = 0; i < original.size(); i++) {
 			 
 			  newArr.add(original.get(discreteLogBasePrMod1019_(pr, i+1) % (safePrime - 1)));
 		  }
 		  return newArr;
   	}
  	 
   	//use pr2
   	private  ArrayList<Character> decryptCols (ArrayList <Character> original, int pr) {
 		  ArrayList<Character> newArr = new ArrayList<Character>();
 		  for (int i = 0; i < original.size(); i++) {
 			  newArr.add(original.get(discreteLogBasePrMod1019_(pr, i+1) % (safePrime - 1)));
 		  }
 		  return newArr;
   	}
   	
   	private ArrayList<Character> unscrambleCols (ArrayList<Character> original, int pr) {
		  ArrayList<Character> unscrambled = new ArrayList<Character>();
		  for (int i = 0; i < Math.pow(safePrime - 1, 2); i++) {
	  			 unscrambled.add('a');
	  		 }
		  //scramble each row
		  for (int y = 0; y < safePrime - 1; y++) {
			  ArrayList<Character> origRow = new ArrayList <Character> ();
			  for (int j = 0; j < safePrime - 1; j++) {
				  char c = coords(j, y, original);
				  origRow.add(c);
			  }
			 
			  ArrayList<Character> unscrambledRow = decryptRows (origRow, pr);
			  for (int j = 0; j < safePrime - 1; j++) {
				  char newChar = unscrambledRow.get(j);
				  unscrambled.set(index(j, y), newChar);
			  }
			  System.out.println("Column " + y + " unscrambled");

		  }
		  return unscrambled;
	  }
 
	private ArrayList<Character> unscrambleRows (ArrayList<Character> original, int pr) {
	  ArrayList<Character> unscrambled = new ArrayList<Character>();
	  for (int i = 0; i < Math.pow(safePrime - 1, 2); i++) {
			 unscrambled.add('a');
		 }
	  //scramble each column
	  for (int x = 0; x < safePrime - 1; x++) {
		  ArrayList<Character> origCol = new ArrayList <Character> ();
		  for (int j = 0; j < safePrime - 1; j++) {
			  
			  char c = coords(x, j, original);
			  origCol.add(c);
		  }
		 
		  ArrayList<Character> unscrambledCol = decryptCols (origCol, pr);
		  for (int j = 0; j < safePrime - 1; j++) {
			  char newChar = unscrambledCol.get(j);
			  unscrambled.set(index(x, j), newChar);
		  }
		  System.out.println("Row " + x + " unscrambled");

	  }
	  return unscrambled;
	}
	
	private ArrayList<Character> unscrambleD1 (ArrayList<Character> original, int pr1, int pr2) {
		ArrayList<Character> unscrambled = new ArrayList<Character>();
		for (int i = 0; i < Math.pow(safePrime - 1, 2); i++) {
 			 unscrambled.add('a');
 		 }
 		  //scramble each row
 		  for (int y = 0; y < safePrime - 1; y++) {
 			  ArrayList<Character> origRow = new ArrayList <Character> ();
 			  for (int j = 0; j < safePrime - 1; j++) {
					  int modifiedY = (y + (prToThe(pr1, j))) % (safePrime - 1);
					  
 				  char c = coords(j, modifiedY, original);
 				  origRow.add(c);
 			  }
 			 
 			  ArrayList<Character> unscrambledRow = decryptRows (origRow, pr2);
 			  for (int j = 0; j < safePrime - 1; j++) {
					  int modifiedY = (y + (prToThe(pr1, j))) % (safePrime - 1);
 				  char newChar = unscrambledRow.get(j);
 				  unscrambled.set(index(j, modifiedY), newChar);
 			  }
 			  System.out.println("Column " + y + " unscrambled");

 		  }
 		  return unscrambled;
 	  }
	 
 	private ArrayList<Character> unscrambleD2 (ArrayList<Character> original, int pr1, int pr2) {
		ArrayList<Character> unscrambled = new ArrayList<Character>();
		for (int i = 0; i < Math.pow(safePrime - 1, 2); i++) {
 			 unscrambled.add('a');
 		 }
 		  //scramble each column
 		  for (int x = 0; x < safePrime - 1; x++) {
 			  ArrayList<Character> origCol = new ArrayList <Character> ();
 			  for (int j = 0; j < safePrime - 1; j++) {
   				  int modifiedX = (x + (prToThe(pr1, j))) % (safePrime - 1);
   				  
 				  char c = coords(modifiedX, j, original);
 				  origCol.add(c);
 			  }
 			 
 			  ArrayList<Character> unscrambledCol = decryptCols (origCol, pr2);
 			  for (int j = 0; j < safePrime - 1; j++) {
   				  int modifiedX = (x + (prToThe(pr1, j))) % (safePrime - 1);
 				  char newChar = unscrambledCol.get(j);
 				  unscrambled.set(index(modifiedX, j), newChar);
 			  }
 			  System.out.println("Row " + x + " unscrambled");

 		  }
 		  return unscrambled;
   	}
	

	
}
