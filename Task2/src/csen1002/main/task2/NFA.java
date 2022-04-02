package csen1002.main.task2;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

/**
 * Write your info here
 * 
 * @name Noureldin Ghazal
 * @id 43-15747
 * @labNumber 15
 */
public class NFA{
	ArrayList<String> currentState;
	String[] zeroTransitions;
	String[] oneTransitions;
	String[] epsilonTransitions;
	String[] acceptStates;
	
	/**
	 * NFA constructor
	 * 
	 * @param description is the string describing a NFA
	 */
	public NFA(String description) {
		String[] splitedDescription = description.split("#");
		zeroTransitions = splitedDescription[0].split(";");
		oneTransitions = splitedDescription[1].split(";");
		epsilonTransitions = splitedDescription[2].split(";");
		acceptStates = splitedDescription[3].split(",");
		currentState = new ArrayList<String>();
		currentState.add("0");
		
	}

	/**
	 * Returns true if the string is accepted by the NFA and false otherwise.
	 * 
	 * @param input is the string to check by the NFA.
	 * @return if the string is accepted or not.
	 */
	public boolean run(String input) {
		for(int i =0 ; i<input.length();i++){
		ArrayList<String> newState = new ArrayList<String>();
		for(int j =0;j<epsilonTransitions.length;j++) {
			String[] epsilonTransition =epsilonTransitions[j].split(","); 
			for(int k=0 ; k<currentState.size();k++ ){
				if(currentState.get(k).equals(epsilonTransition[0])) {
					currentState.add(epsilonTransition[1]);
				}
			}
			}
		if(input.charAt(i) == '0') {
			for(int j =0;j<zeroTransitions.length;j++) {
				String[] zeroTransition =zeroTransitions[j].split(","); 
				for(int k=0 ; k<currentState.size();k++ ){
					if(currentState.get(k).equals(zeroTransition[0])) {
						newState.add(zeroTransition[1]);
					}
				}
			}
		}
		else {
			for(int j =0;j<oneTransitions.length;j++) {
				String[] oneTransition =oneTransitions[j].split(","); 
				for(int k=0 ; k<currentState.size();k++ ){
					if(currentState.get(k).equals(oneTransition[0])) {
						newState.add(oneTransition[1]);
					}
				}
			}	
		}
		
		for(int j =0;j<epsilonTransitions.length;j++) {
			String[] epsilonTransition =epsilonTransitions[j].split(","); 
//			for(int k=0 ; k<currentState.size();k++ ){
//				if(currentState.get(k).equals(epsilonTransition[0])) {
//					currentState.add(epsilonTransition[1]);
//				}
//			}
		for(int k=0 ; k<newState.size();k++ ){
			if(newState.get(k).equals(epsilonTransition[0])) {
				newState.add(epsilonTransition[1]);
			}
		}
		}
			currentState = newState;	
		}
		
		for(int i = 0; i<acceptStates.length;i++) {
			if(currentState.contains(acceptStates[i]))
			{
				return true;
			}
		}
		return false;
	}
	public static void main(String[]args) {
		NFA NFA2 = new NFA("1,2;4,5;8,9#3,4;6,7#0,1;0,3;2,1;2,3;5,6;5,8;7,10;9,10#10");
		System.out.println(NFA2.run("0010"));

	}
}
