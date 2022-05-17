package csen1002.main.task7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Write your info here
 * 
 * @name Noureldean Ghazal
 * @id 43-15747
 * @labNumber 15
 */
public class LL1CFG {
	HashMap<String,String> rules=new HashMap<String,String>();
	HashMap<String,String> firsts=new HashMap<String,String>();
	HashMap<String,String> follows=new HashMap<String,String>();
	Set<String> terminals = new LinkedHashSet<String>();
	Set<String> variables = new LinkedHashSet<String>();
	HashMap<String,HashMap<String,ArrayList<String>>> lookupTable = new HashMap<String,HashMap<String,ArrayList<String>>>();
	/**
	 * LL1 CFG constructor
	 * 
	 * @param description is the string describing an LL(1) CFG, first, and follow as represented in the task description.
	 */
	public LL1CFG(String description) {
		terminals.add("$");
		for(char t : description.toCharArray()) {
			if((t>='a' && t<='z') && t!='e') {
				terminals.add(String.valueOf(t));
			}
			if(t>='A'&& t<='Z') {
				variables.add(String.valueOf(t));
			}
		}
		String[] descriptionSplited = description.split("#");
		String rawRules = descriptionSplited[0];
		String rawFirsts = descriptionSplited[1];
		String rawFollows = descriptionSplited[2];
		String[] rawRulesSplited = rawRules.split(";");
		String[] rawFirstsSplited = rawFirsts.split(";");
//		firsts.put(rawFirstsSplited[0], String.join(";",Arrays.copyOfRange(rawFirstsSplited, 1, rawFirstsSplited.length)));
		String[] rawFollowsSplited = rawFollows.split(";");
//		follows.put(rawFollowsSplited[0], String.join(";",Arrays.copyOfRange(rawFollowsSplited, 1, rawFollowsSplited.length)));
		
		for(String rule : rawRulesSplited) {
			String[] ruleSplited = rule.split(",");
			rules.put(ruleSplited[0], String.join(",",Arrays.copyOfRange(ruleSplited, 1, ruleSplited.length)));
		}
		for(String first : rawFirstsSplited) {
			String[] firstSplited = first.split(",");
			firsts.put(firstSplited[0], String.join(",",Arrays.copyOfRange(firstSplited, 1, firstSplited.length)));
		}
		for(String follow : rawFollowsSplited) {
			String[] followSplited = follow.split(",");
			follows.put(followSplited[0], String.join(",",Arrays.copyOfRange(followSplited, 1, followSplited.length)));
		}
		constructLookUpTable();
		
	}
	public void constructLookUpTable() {
		//looping on all variables and terminals we have to create our lookup table
		for(String var : variables) {
			String varFirsts = firsts.get(var);
			for(String terminal : terminals) {
				ArrayList<String> tableRules =null;
		//if a terminal is a first of variable we add the rule driving that first to our lookup table else it should be leaved as null
				if(varFirsts.contains(terminal)) {
					tableRules = getFirstRules(var, terminal);
				}	
				HashMap<String,ArrayList<String>> terminalHash =lookupTable.get(var);
				if(terminalHash == null) {
					terminalHash = new HashMap<String,ArrayList<String>>();
					lookupTable.put(var, terminalHash);
				}
				terminalHash.put(terminal, tableRules);
				
			}
		//if the first of a variable can go to epsilon we should add all the rules resulting in this epsilon to the terminals in the follow 
			if(varFirsts.contains("e")) {
				ArrayList<String> tableRules = getFirstRules(var, "e");
				String varFollows = follows.get(var);
				for(char terminal : varFollows.toCharArray()) {
					String terminalString = String.valueOf(terminal);
					if(terminals.contains(terminalString)) {
						ArrayList<String> existingTableRules = lookupTable.get(var).get(terminalString);
						if(existingTableRules == null) {
							existingTableRules = tableRules;
						}
						else {
							existingTableRules.addAll(tableRules);
						}
						lookupTable.get(var).put(terminalString, existingTableRules);
					}
				}
			}
		}
	}
	public ArrayList<String> getFirstRules(String var, String terminal){
		String ruleCombined = rules.get(var);
		String[] ruleSplited = ruleCombined.split(",");
		ArrayList<String> tableRules =  new ArrayList<String>();
		for(String rule : ruleSplited) {
			if(canGenerateFirst(rule,terminal)) {
				tableRules.add(rule);
			}
		}
		
		return tableRules.size()>0?tableRules: null;
	}
	public boolean canGenerateFirst(String rule, String terminal) {
		if(terminal.equals("e")) {
			//if the rule itself is epsilon or no remaining character form the recursion 
			if(rule.length()==0 || rule.equals("e")) {
				return true;
			}
		String firstCharacterString = String.valueOf(rule.charAt(0));

		//the rule can produce epsilon if all of its characters are variables that can go to epsilon (having epsilon as its first)
		return variables.contains(firstCharacterString) && firsts.get(firstCharacterString).contains("e") && canGenerateFirst(rule.substring(1), terminal);
		}
		else {
		if(rule.length() <= 0 ) {
			return false;
		}
		String firstCharacterString = String.valueOf(rule.charAt(0));

		if(terminals.contains(firstCharacterString) && firstCharacterString.equals(terminal)) {
			return true;
		}
		if(variables.contains(firstCharacterString) && firsts.get(firstCharacterString).contains(terminal)) {
			return true;
		}
		if(variables.contains(firstCharacterString) && firsts.get(firstCharacterString).contains("e")) {
			return canGenerateFirst(rule.substring(1),terminal);
		}
		return false;
		}
	}
	
	/**
	 * Returns A string encoding a derivation is a comma-separated sequence of sentential forms each representing a step in the derivation..
	 * 
	 * @param input is the string to be parsed by the LL(1) CFG.
	 * @return returns a string encoding a left-most derivation.
	 */
	public String parse(String input) {
		Stack<String> ourStack = new  Stack<String>();  
		ourStack.push("S");
		int terminalsRead = 0;
		StringBuilder output = new StringBuilder();
		boolean flag =true;
		while(!ourStack.isEmpty()) {
			
			
			if(terminalsRead<input.length()) {
			if(flag) {
				Stack<String> ourStackCopy = (Stack<String>) ourStack.clone();
				output.append(input.substring(0, terminalsRead));
				while(!ourStackCopy.isEmpty()) {
					output.append(ourStackCopy.pop());
				}
				output.append(",");
				}
				flag=true;
				String var = ourStack.pop();
				String terminal = String.valueOf(input.charAt(terminalsRead));
				if(terminal.equals(var)) {
					terminalsRead++;
					flag = false;
					continue;
				}
				if(!variables.contains(var)) {
					output.append("ERROR");
					return output.toString();
				}
				ArrayList<String> lookupRules = lookupTable.get(var).get(terminal);
				if(lookupRules == null) {
					output.append("ERROR");
					return output.toString();
				}
				if(lookupRules.size() > 1 ) {
					output.append("Not deterministic");
					return output.toString();
				}
				String rule = lookupRules.get(0);
				if(rule.equals("e")) {
					continue;
				}
				for(int i=rule.length()-1;i>=0;i--) {
					ourStack.push(String.valueOf(rule.charAt(i)));
				}
		}
			else {
				String var = ourStack.pop();
				if(!variables.contains(var)) {
					output.append("ERROR");
					return output.toString();
				}
			ArrayList<String> rule =lookupTable.get(var).get("$");
			if(rule!=null && rule.get(0).equals("e")) {
				output.append(input);
				Stack<String> ourStackCopy = (Stack<String>) ourStack.clone();
				while(!ourStackCopy.isEmpty()) {
					output.append(ourStackCopy.pop());
				}
				output.append(",");
			}
			else {
				output.append("ERROR");
				return output.toString();
			}
			}
			
		}
		output.deleteCharAt(output.length()-1);
		return output.toString();
	}
	
	public static void main(String[] args) {
		String [] inputs = {"S,zToS,n,e;T,zTo,No;N,n,e#S,z,n,e;T,z,no;N,n,e#S,$;T,o;N,o","S,zToS,n,e;T,zTo,No;N,n,e#S,z,n,e;T,z,no;N,n,e#S,$;T,o;N,o"
				,"S,zToS,n,e;T,zTo,No;N,n,e#S,z,n,e;T,z,no;N,n,e#S,$;T,o;N,o","S,zToS,n,e;T,zTo,No;N,n,e#S,z,n,e;T,z,no;N,n,e#S,$;T,o;N,o"
				,"S,zToS,n,e;T,zTo,No;N,n,e#S,z,n,e;T,z,no;N,n,e#S,$;T,o;N,o","S,ipD,oSmDc,e;D,VmS,LxS;V,n,e;L,oSc,e#S,i,o,e;D,mn,ox;V,n,e;L,o,e#S,cm$;D,cm$;V,m;L,x"
				,"S,ipD,oSmDc,e;D,VmS,LxS;V,n,e;L,oSc,e#S,i,o,e;D,mn,ox;V,n,e;L,o,e#S,cm$;D,cm$;V,m;L,x","S,ipD,oSmDc,e;D,VmS,LxS;V,n,e;L,oSc,e#S,i,o,e;D,mn,ox;V,n,e;L,o,e#S,cm$;D,cm$;V,m;L,x"
				,"S,ipD,oSmDc,e;D,VmS,LxS;V,n,e;L,oSc,e#S,i,o,e;D,mn,ox;V,n,e;L,o,e#S,cm$;D,cm$;V,m;L,x","S,ipD,oSmDc,e;D,VmS,LxS;V,n,e;L,oSc,e#S,i,o,e;D,mn,ox;V,n,e;L,o,e#S,cm$;D,cm$;V,m;L,x"};
		for(String input : inputs) {
		LL1CFG test = new LL1CFG(input);
//		System.out.println("terminals");
//		for(String terminal : test.terminals) {
//			System.out.print(terminal+ " ");
//		}
//		System.out.println();
//		System.out.println("Vars");
//		for(String var : test.variables) {
//			System.out.print(var+ " ");
//		}
//		System.out.println();
//		System.out.println("Firsts");
//		 for (Map.Entry<String,String> entry : test.firsts.entrySet()) {
//			 System.out.println(entry.getKey() +" -> " + entry.getValue());
//		 }
//		 System.out.println();
//			System.out.println("Follows");
//			 for (Map.Entry<String,String> entry : test.follows.entrySet()) {
//				 System.out.println(entry.getKey() +" -> " + entry.getValue());
//			 }
		System.out.println();
		System.out.println("input: "+ input);
		System.out.println("Lookup Table");
		 for (Map.Entry<String,HashMap<String,ArrayList<String>>> entry : test.lookupTable.entrySet()) {
			 System.out.println();
			 System.out.println("KEY " + entry.getKey());
			 for (Map.Entry<String,ArrayList<String>> secondEntry : entry.getValue().entrySet()) {
				 System.out.print("Terminal " + secondEntry.getKey()+" ");
				 if(secondEntry.getValue() == null) {
					 System.out.print("null ");
				 }
				 else {
					 System.out.print("[");
					 for(String rule: secondEntry.getValue()) {
						 System.out.print(rule+" ");
					 }
					 System.out.print("]");
				 }
			 }
			 
	    }
		}
	}
	

}
