 package csen1002.main.task1;

/**
 * Write your info here
 *
 * @name NourEldean Ghazal
 * @id 43-15747
 * @labNumber 15
 */
public class DFA {
	/**
	 * DFA constructor
	 *
	 * @param description is the string describing a DFA
	 */
	String[] acceptState;
	String[] dfaStates;
	int currentState=0;
	public DFA(String description) {
		// TODO Write Your Code Here
		String[] descriptionList = description.split("#");
		String accept = descriptionList[1];
		String dfa = descriptionList[0];
		this.acceptState = accept.split(",");
		this.dfaStates = dfa.split(";");
	}

	/**
	 * Returns true if the string is accepted by the DFA and false otherwise.
	 *
	 * @param input is the string to check by the DFA.
	 * @return if the string is accepted or not.
	 */
	public boolean run(String input) {
		// TODO Write Your Code Here
		for(int i =0 ; i<input.length();i++){
			
			String[] currentStateInfo = this.dfaStates[currentState].split(",");
			currentState = Integer.parseInt(currentStateInfo[Integer.parseInt(input.charAt(i)+"")+1]);
		}
		for(int i=0; i<this.acceptState.length;i++){
			if(currentState == Integer.parseInt(acceptState[i])){
				return true;
			}
		}
		return false;
	}
	public static void main(String[]args){
		DFA dfa1 = new DFA("0,0,1;1,2,1;2,0,3;3,3,3#1,3");
		System.out.print(dfa1.run("11"));
	}
}

