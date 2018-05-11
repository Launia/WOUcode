/*
 * This project is for Lab 3 part 6, implementing a DFA.
 * 
 * Launia Davis
 * CS361 - Algorithms
 * March 18, 2018
 */

// The following code is from source: https://www.dennis-grinch.co.uk/tutorial/dfa-in-java#SC101
public class DFA {

	
	private enum States{
		// setting up accept states
		S(false), Q1(true), Q2(false), R1(true), R2(false);
		
		// setting up the alphabet for DFA
		States a;
		States b;
		
		// setting up transition functions
		static{
			S.a = Q1; S.b = R1;
			Q1.a = Q1; Q1.b = Q2;
			Q2.a = Q1; Q2.b = Q2;
			R1.a = R2; R1.b = R1;
			R2.a = R2. R2.b = R1;
		}
		
		States transition(char c) 
		{
			switch (c)
			{
				case 'a':
					return this.a;
				case 'b':
					return this.b;
				default:
					throw new RuntimeException("Symbol is not in the alphabet.");
			}
		}
		
		final boolean accept;
		
		// constructor for state
		// every state is either accepting or not accepting
		States(boolean accept)
		{
			this.accept = accept;
		}
	}
	
	// method goes through each element in a string. makes a call to method States transition (above) 
	// to make sure the element is a part of the alphabet for DFA
	// it will return accept if the string finishes in an accept state
	public static boolean accept(String string)
	{
		States state = States.S;
		
		for(int i = 0; i < string.length(); i++)
		{
			state = state.transition(string.charAt(i));
		}
		return state.accept;
	}
	
	// Main method takes in a string and runs each one through the accept method
	// if statements will check is results are true or false and have a print statement
	// that corresponds with the results
	public static void main(String[] args)
	{
		// Here are the strings to check in the DFA to see if their end result is accept or not
		String string1 = "ababa";
		String string2 = "baba";
		String string3 = "aababaab";
		String string4 = "babaabaaabb";
		String string5 = ""; // empty string
		
		
		boolean result;
		
		result = accept(string1);
		if(result == true)
		{
			System.out.println("String 'ababa' finishes in an accept state.");
		}else{
			System.out.println("String 'ababa' does not finish in an accept state.");
		}
		
		result = accept(string2);
		if(result == true)
		{
			System.out.println("String 'baba' finishes in an accept state.");
		}else{
			System.out.println("String 'baba' does not finish in an accept state.");
		}

		result = accept(string3);
		if(result == true)
		{
			System.out.println("String 'aababaab' finishes in an accept state.");
		}else{
			System.out.println("String 'aababaab' does not finish in an accept state.");
		}

		result = accept(string4);
		if(result == true)
		{
			System.out.println("String 'babaabaaabb' finishes in an accept state.");
		}else{
			System.out.println("String 'babaabaaabb' does not finish in an accept state.");
		}

		result = accept(string5);
		if(result == true)
		{
			System.out.println("The empty string finishes in an accept state.");
		}else{
			System.out.println("The empty string does not finish in an accept state.");
		}

	}
}
