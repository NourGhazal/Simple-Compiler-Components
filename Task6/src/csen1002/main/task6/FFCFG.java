package csen1002.main.task6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import csen1002.main.task5.CFG;

/**
 * Write your info here
 * 
 * @name Noureldin Ghazal
 * @id 43-15747
 * @labNumber 15
 */

public class FFCFG {

	/**
	 * Constructs a CFG for which the First and Follow are to be computed
	 * 
	 * @param description A string representation of a CFG as specified in the task
	 *                    description
	 */
	HashMap<String,StringBuilder> firsts=new HashMap<String,StringBuilder>();
	HashMap<String,StringBuilder> follows=new HashMap<String,StringBuilder>();
	ArrayList<String> variables = new ArrayList<String>();
	HashMap<String,String> rules=new HashMap<String,String>();

	public FFCFG(String description) {
		
		String [] descriptionSplited = description.split(";");
//		StringBuilder newDesription = new StringBuilder("");
		for(int i =0; i < descriptionSplited.length;i++) 
		{
		String [] rulesSplited =descriptionSplited[i].split(",");
//		String [] rulesSplited =descriptionSplited[i].split(",");
		variables.add(rulesSplited[0]);
		rules.put(rulesSplited[0], String.join(",",Arrays.copyOfRange(rulesSplited, 1, rulesSplited.length)));
		firsts.put(rulesSplited[0], new StringBuilder(""));
		follows.put(rulesSplited[0], new StringBuilder(""));
//		newDesription.append(rulesSplited[0]);
//		newDesription.append(",");
//		StringBuilder x = new StringBuilder(String.join(",",Arrays.copyOfRange(rulesSplited, 1, rulesSplited.length)));
//		newDesription.append(x.reverse());
//		newDesription.append(";");
		} 
		
		for(int i =0 ; i<variables.size();i++) {
			String var = variables.get(i);
			String[] ruleSplited = rules.get(var).split(",");
			computeFirsts(rules,ruleSplited, var);

			
		}
		HashMap<String,StringBuilder> copyFollow= new HashMap<String,StringBuilder>();
		for(int i =0 ; i<variables.size();i++) {
		String var = variables.get(i);
		computeFollows(rules, var,copyFollow);

		}
		String cc ="";
		for (String key : copyFollow.keySet()) {
			System.out.println(key+" KEY");
			System.out.println(copyFollow.get(key) + "VAR");
			System.out.println(follows.get(key).toString() + "append var foolow");
			StringBuilder toAppendKeys =  copyFollow.get(key);
			for(int i=0;i<toAppendKeys.length();i++) {
				follows.get(toAppendKeys.substring(i, i+1)).append(follows.get(key));
			}
			
		}
		
	
	}
	
	
	
	public void  computeFirsts(HashMap<String,String> rules,String[] ruleSplited,String var) {
			for(int j=0;j<ruleSplited.length;j++) {
				for(int k=0 ; k<ruleSplited[j].length();k++) {
				String var2 = ruleSplited[j].charAt(k)+"";
				StringBuilder first =firsts.get(var);
				if(! variables.contains(var2)) {
					first.append(var2);
					break;
				}
				else {
					if(!var2.equals(var)) {
					computeFirsts(rules,rules.get(var2).split(","), var2);
					StringBuilder x = firsts.get(var2);
					//find epsilons closures from other variables and deleting it if there was preceding terminals
					int index = x.indexOf("e");
					if(index>0 && k <ruleSplited[j].length()-1 ) {
						x.deleteCharAt(index);
					}
					first.append(x);
					}
					if(!canGoToEpsilon(rules, var2)) {				
						break;
					}
				}
			
			}
				}
			
		}

	public void  computeFollows(HashMap<String,String> rules,String var,HashMap<String,StringBuilder> copyFollow) {
//		HashMap<String,String> copyFollow= new HashMap<String,String>();
		StringBuilder currentFollow = follows.get(var);
		if(variables.get(0).equals(var) && currentFollow.indexOf(var)<0) {
			currentFollow.append("$");
		}
		String[] varRules=rules.get(var).split(",");
		for(String varRule :varRules) {
			for (int i=0; i<varRule.length();i++) {
				String var2 = varRule.charAt(i)+"";
				if(variables.contains(var2)) {
					if(i!=varRule.length()-1) {
						int j =1;
						while(i<varRule.length()-j) {
						String var3 = varRule.charAt(i+j)+"";
						if(variables.contains(var3)) {
							String first = firsts.get(var3).toString();
							System.out.println(first.toString());
							follows.get(var2).append(first);
						if(firsts.get(var3).indexOf("e")<0) {
							break;
						}
						else {
							if(i+j ==varRule.length()-1 ) {
								follows.get(var2).append(follows.get(var3));
								if(copyFollow.get(var3)==null)
								{
								copyFollow.put(var3,new StringBuilder(var2));
									}
								else {
								copyFollow.get(var3).append(var2);
								}
								
							}
						}
						}
						else {
							follows.get(var2).append(var3);
							break;
						}
						
						j++;
						}
					}
					else {
//						System.out.println(var+"VAR");
//						System.out.println(var2+"V2AR");
//						System.out.println(currentFollow+"follow");
						if(copyFollow.get(var)==null)
						{
						copyFollow.put(var,new StringBuilder(var2));
							}
						else {
						copyFollow.get(var).append(var2);
						}
						System.out.println("I have PUT KEY "+var +" ANDVAL +"+ copyFollow.get(var));
						follows.get(var2).append(currentFollow);
					}
				}
			}
		}
		
		
	
	}
	
	
	public Boolean containsTerminal(String rule) {
		for(char c : rule.toCharArray()) {
			if(!variables.contains(c+"")) {
				return true;
			}
		}
		return false;
	}

	public Boolean canGoToEpsilon(HashMap<String,String> rules,String var) {
		String [] ruleSplited = rules.get(var).split(",");
		ArrayList<String> toInspect = new ArrayList<String>();
		for(String rule: ruleSplited ) {
			if(rule.contains("e")) {
				return true;
			}
			
			if(!containsTerminal(rule)) {
				for(int i =0 ;i < rule.length();i++) {
					toInspect.add(rule.charAt(i)+"");
				}
			}
//			Boolean carry=true;
//			for(String var2: toInspect) {
//				int index1 = variables.indexOf(var);
//				int index2 = variables.indexOf(var2);
//				if(!(var2.equals(var)) && index2>index1 ) {
//					carry = carry & canGoToEpsilon(rules, var2);
//				}
//				
//			}
//			if(carry==true && !toInspect.isEmpty()) {
//				return carry;
//			}
//			if(variables.contains(rule)) {
//				return canGoToEpsilon(rules,rule);
//			}
		}
		return false;
		}

	/**
	 * Calculates the First of each variable in the CFG.
	 * 
	 * @return A string representation of the First of each variable in the CFG,
	 *         formatted as specified in the task description.
	 */
	public String first() {
		// return the output like the desired format
		StringBuilder returnVal = new StringBuilder(""); 
		 Set<String> keys = firsts.keySet();
		for (String key :variables) {
			String firstValue = firsts.get(key).toString();
			Set<Character> charSet = new LinkedHashSet<Character>();
			for (char c: firstValue.toCharArray()) 
			{
			       charSet.add(c); 
			}
			 ArrayList<Character> charArrayList = new ArrayList<>(charSet);
			 Collections.sort(charArrayList);			
			returnVal.append(key);
			returnVal.append(",");
			 for(char c : charArrayList) {
				 returnVal.append(c);
			 }
			 returnVal.append(";");
			  }
		returnVal.deleteCharAt(returnVal.length()-1);
		return returnVal.toString();
	}

	/**
	 * Calculates the Follow of each variable in the CFG.
	 * 
	 * @return A string representation of the Follow of each variable in the CFG,
	 *         formatted as specified in the task description.
	 */
	public String follow() {
		StringBuilder returnVal = new StringBuilder(""); 
		 Set<String> keys = follows.keySet();
		for (String key :variables) {
			String firstValue = follows.get(key).toString();
			Set<Character> charSet = new LinkedHashSet<Character>();
			for (char c: firstValue.toCharArray()) 
			{
			       charSet.add(c); 
			}
			 ArrayList<Character> charArrayList = new ArrayList<>(charSet);
			 Collections.sort(charArrayList);			
			returnVal.append(key);
			returnVal.append(",");
			Boolean dolarSign = false;
			 for(char c : charArrayList) {
				 if(c == 'e') {
					 continue;
				 }
				 if(c!='$') {
					 returnVal.append(c); 
				 }
				 else{
					 dolarSign = true; 
				 }
				 
			 }
			 if(dolarSign) {
				 returnVal.append("$");
			 }
			 returnVal.append(";");
			  }
		returnVal.deleteCharAt(returnVal.length()-1);
		return returnVal.toString();
	}

}
