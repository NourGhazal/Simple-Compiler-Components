package csen1002.main.task4;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Write your info here
 * 
 * @name Noureldean Ghazal	
 * @id 43-15747
 * @labNumber 15
 */
public class FDFA {
	
	Stack<Integer> states = new Stack<Integer>();
	int cursor=0;
	int currentState=0;
	ArrayList<String> actions = new ArrayList<String>();
	String[] acceptStates;
	String[] dfaStates;
	
	/**
	 * FDFA constructor
	 * 
	 * @param description is the string describing a FDFA
	 */
	public FDFA(String description) {
		String[] dfa =description.split("#");
		acceptStates = dfa[1].split(",");
		states.push(0);
		dfaStates = dfa[0].split(";");
	}

	/**
	 * Returns a string of actions.
	 * 
	 * @param input is the string to simulate by the FDFA.
	 * @return string of actions.
	 */
	public String run(String input) {
		String currentInput = input;
		String returnedAnswer = "";
		while(currentInput.length()>0) {
			if(cursor < currentInput.length()) {
				String[] state = dfaStates[currentState].split(",");
				currentState =Integer.parseInt(state[Integer.parseInt(currentInput.charAt(cursor)+"")+1]);
				states.push(currentState);
				cursor++;
			}
			else {
				int cursorCopy = cursor;
				int y =states.peek();
				while(!states.isEmpty()){
					int x = states.pop();
					cursorCopy --;
					for(int i =0;i<acceptStates.length;i++) {
						if(x == Integer.parseInt(acceptStates[i]) && cursorCopy>0) {
							returnedAnswer += currentInput.substring(0,cursorCopy+1)+","+dfaStates[x].split(",")[3]+";";
							states = new Stack<Integer>();
							cursor=0;
							currentInput=currentInput.substring(cursorCopy+1);
							currentState=0;
							break;
						}
					}
					if(cursorCopy==0) {
						returnedAnswer += currentInput+","+dfaStates[y].split(",")[3]+";";
						states = new Stack<Integer>();
						cursor=0;
						currentState=0;
						currentInput="";
					}
				
				}
				states.push(0);
			}
			
		}
		return returnedAnswer;
	}
}
