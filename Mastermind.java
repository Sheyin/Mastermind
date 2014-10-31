import java.util.Scanner;

public class Mastermind {

	public static void main (String[] args) {
		String[] guess = {"these", "are", "my", "guess"};
		int turn = 1;
		System.out.println("\nLet's play a game of Mastermind!");
		System.out.println("I will pick 4 colors.  You guess what they are.");
		System.out.println("   Rules: Duplicate colors allowed - feedback is only per guess per color.");
		System.out.println("   Black = right color, right place.  White = right color, wrong place.");
		System.out.println("   The position of the results do not correspond to colors.");
		String[] solution = GenerateAnswer();
		//System.out.println("Debug: the answer is: ");
		//ReadColors(solution);
		boolean solved = false;	
		do {
			System.out.println("\nTurn #" + turn + ".  Which colors do you guess? (red, orange, yellow, green, blue, purple)");
		
			for (int i=0; i<4; i++){
				System.out.print("Peg #" + (i+1) + ":");
				guess[i]=ChooseColor();
				}
			
			System.out.print("Your choice: ");
			ReadColors(guess);
			
			String[] result = CompareColors(solution, guess);
			solved = CheckAnswer(result);
			System.out.println("\nResults: ");
			ReadColors(result);
			turn++;
			} while (!solved);
		
		System.out.println("Congratulations!  You guessed the code!");
		} // close main
		
	public static String ChooseColor () {		// This method is to have a person choose colors
		Scanner user_input = new Scanner(System.in);
		String color =  user_input.next();
		boolean isValid = ColorCheck(color);
		while (!isValid){		// invalid input
			System.out.println(color + " is not a valid choice.  Please try again. (red, orange, yellow, green, blue, purple)");
			color =  user_input.next();
			isValid = ColorCheck(color);
			}
		return color;
	} //close ChooseColor
		
	public static void ReadColors (String[] readThis){		// This method prints the colors of the specified set
		for(String n : readThis) {
			System.out.print("    " + n);
			}
		System.out.println("  ");
		return;		
		} // Close ReadColors
		
	public static boolean ColorCheck (String checkMe){		// This checks if input is a valid colors
		String[] validColors = {"red", "orange", "yellow", "green", "blue", "purple"};
		boolean isValid = false;
		for(int i=0;i<6;i++) {	// this loops and compares checkMe to the list of eligible colors.
				
/*				for(int j = 0; j < checkMe.length(); ++j) {	// checking to eliminate spaces - not quite working...
					char ch = Character.toLowerCase(checkMe.charAt(j));
				if (Character.isWhitespace(ch)) {
					System.out.println("Only one color per peg!  Please try again!");
					isValid = false;
					return isValid;
					}
				}  */
			
			isValid = validColors[i].equalsIgnoreCase(checkMe);
			if (isValid) {
				return isValid;
				}
			else;
			}
			
		return isValid;
		} // closes ColorCheck
		
	public static String[] GenerateAnswer() {		// This generates a solution to be guessed
		String[] solution = {"red", "orange", "yellow", "green"};
		// random random random
		for (int i = 0; i < 4; i++) {
			int randNum = (int) (Math.random() * 100);
			if ((randNum >= 0) && (randNum < 18)) {
				solution[i] = "red";
				}
			if ((randNum >= 18) && (randNum < 34)) {
				solution[i] = "orange";
				}
			if ((randNum >=34) && (randNum < 50)) {
				solution[i] = "yellow";
				}
			if ((randNum >= 50) && (randNum < 65)) {
				solution[i] = "green";
				}
			if ((randNum >= 65) && (randNum < 81)) {
				solution[i] = "blue";
				}
			if ((randNum >= 81) && (randNum <= 100)) {
				solution[i] = "purple";
				}
			}
		return solution;
	}
	
	public static String[] CompareColors (String[] solution, String[] guess) {		// This method compares the colors of the specified sets
		String[] result = {" "," "," "," "};
		String[] solutionTemp = CopyString(solution);		// need to do this to remove "found" entries (black vs white hits)
		String[] guessTemp = CopyString(guess);		// need to do this to remove "found" entries (duplicate color guess etc)
		int k = 0;
		// this loop is to check for black pegs (i == j)
		for(int i = 0; i < 4; i++) {		// i cycles through guess - change to do-while?
			String examining = guessTemp[i];
			boolean isMatch = false;
			for(int j = 0; j < 4; j++) {	// j cycles through the solution
				isMatch = solutionTemp[j].equalsIgnoreCase(examining);
				if (isMatch && (i == j) && (examining != "blank")) {		// a match of some sort has been found
					result[k] = PlacePeg(isMatch,i,j);
					solutionTemp = RemoveFound(solutionTemp,j);
					guessTemp = RemoveFound(guessTemp,i);
					//Debug(solutionTemp,guessTemp,result,k);
					k++;
					break;
					}
				else;	// do nothing; j should be increased, k should not
				} // end j loop
				// get a new examining (guess[i])
			}	// end i loop
					
		// repeating original loop to check for white pegs (i != j)
			for(int i = 0; i < 4; i++) {		// i cycles through guess
			String examining = guessTemp[i];
			boolean isMatch = false;
			for(int j = 0; j < 4; j++) {	// j cycles through the solution
				isMatch = solutionTemp[j].equalsIgnoreCase(examining);
				if (isMatch && (examining != "blank")) {		// a match of some sort has been found
					result[k] = PlacePeg(isMatch,i,j);
					solutionTemp = RemoveFound(solutionTemp,j);
					guessTemp = RemoveFound(guessTemp,i);
					//Debug(solutionTemp,guessTemp,result,k);
					k++;
					break;
					}
				else;	// do nothing; j should be increased, k should not
				} // end j loop
				// get a new examining (guess[i])
			}	// end i loop
		return result;
		} // close CompareColors
		
		public static String[] CopyString (String[] solutionCopy) {
			String[] newCopy = {" ", " ", " ", " "};
			for(int i = 0; i < 4; i++) {
				newCopy[i] = solutionCopy[i];
				}
			return newCopy;
			}
		
		public static String PlacePeg (boolean isMatch, int guessNum, int solutionNum) {		// This is just to add pegs to results array		
			String result;
				if (solutionNum == guessNum) {
					result = "black";
					}
				else {			// i !=j
					result = "white";
					}
			return result;
		} // closes PlacePeg
		
		public static String[] RemoveFound (String[] solutionTemp, int solutionNum) {		// this removes "found" entries in solution to prevent duplicates when no duplicate guesses are entered
		String[] solution = {" ", " ", " ", " "};
			for(int i = 0; i < 4; i++) {
				if (i == solutionNum) {
					solution[i] = "blank";
					}
				else {
					solution[i] = solutionTemp[i];
					}
				}		
		return solution;
		} // closes RemoveFound
		
		public static boolean CheckAnswer (String[] checkResults){		// This checks if answer is solved
			boolean isSolved = false;
			int i=0;
			do {
				isSolved = checkResults[i].equalsIgnoreCase("black");
				if (!isSolved) {
					return false;
					}
				else i++;	// isSolved is true, found a black peg, keep checking
				} while (i < 4);
		return true;	// if all black
		} // closes CheckAnswer

		// call with Debug(solutionTemp,guessTemp,result,k);
		public static void Debug(String[] solutionTemp, String[] guessTemp, String[] result, int k) { //contains all the debug code
			System.out.println("Debug: black's solutionTemp: ");
			ReadColors(solutionTemp);
			System.out.println("Debug: black's guessTemp: ");
			ReadColors(guessTemp);
			System.out.println("Debug: result's length: " + k);
			ReadColors(result);				
		} // close Debug()
		
	} // close Mastermind
