package csen1002.main.task5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Write your info here
 * 
 * @name Noureldin Ghazal
 * @id 43-15747
 * @labNumber 15
 */
public class CFG {
	ArrayList<String> litrilas = new ArrayList<String>();
	HashMap<String,String> rules=new HashMap<String,String>();

	/**
	 * CFG constructor
	 * 
	 * @param description is the string describing a CFG
	 */
	public CFG(String description) {
		String [] descriptionSplited = description.split(";");
		for(int i =0;i<descriptionSplited.length;i++) {
			String [] ruleSplited = descriptionSplited[i].split(",");
			String rule = String.join(",",Arrays.copyOfRange(ruleSplited, 1, ruleSplited.length));
			litrilas.add(ruleSplited[0]);
			rules.put(ruleSplited[0], rule);
//			rules.add(descriptionSplited[i]);
		}
	}

	/**
	 * Returns a string of elimnated left recursion.
	 * 
	 * @param input is the string to simulate by the CFG.
	 * @return string of elimnated left recursion.
	 */
	public String lre() {
		HashMap<String,String> updatedRules=new HashMap<String,String>();
		for(int i=0; i<this.litrilas.size();i++) {
			String litral = this.litrilas.get(i);
			String ruleSplited = this.rules.get(litral);
			for(int j=0;j<i;j++) {
				String subLitral = this.litrilas.get(j);
				updatedRules.put(litral, substitute(subLitral,ruleSplited,updatedRules.get(subLitral)));
				ruleSplited = updatedRules.get(litral);
			}
			String dashLitral=litral+"'";
			String leftRec[] = getLeftRecursionRule(litral,ruleSplited);
			if(leftRec!=null) {
				updatedRules.put(litral,leftRec[0]);
				updatedRules.put(dashLitral,leftRec[1]);
			}
			else {
				updatedRules.put(litral,ruleSplited);
			}
			// we replace all left appearances of the litral in his rule
		}
		//turn updated rule to the format required in the string then return it
		
		
		return turnUpdatedRulesToFormat(updatedRules);
	}
	public String turnUpdatedRulesToFormat(HashMap<String,String> updatedRules) {
		String returnString ="";
		for(int i =0; i<this.litrilas.size();i++) {
			String litral = this.litrilas.get(i);
			String dashedLitrals = litral+"'";
			returnString+=litral+","+updatedRules.get(litral)+";";
			if(updatedRules.containsKey(dashedLitrals)) {
				returnString+=dashedLitrals+","+updatedRules.get(dashedLitrals)+";";
			}
		}
//		for(Map.Entry<String, String> entry:updatedRules.entrySet()) {
//			returnString+=entry.getKey()+","+entry.getValue()+";";
//		}
		return returnString.substring(0,returnString.length()-1);
	}
	public String substitute(String subLitral, String ruleSplited,String subRuleSplited) {
		String[] ruleSplitedArray = ruleSplited.split(",");
		String [] subRuleSplitedArray = subRuleSplited.split(",");
		String result="";
		for(int i=0;i<ruleSplitedArray.length;i++) {
			if((ruleSplitedArray[i].charAt(0)+"").equals(subLitral)) {
				String after = ruleSplitedArray[i].substring(1);
				for(int k=0;k<subRuleSplitedArray.length;k++) {
					result +=  subRuleSplitedArray[k] +after+",";
				}
			}
			else {
				result+=ruleSplitedArray[i]+",";
			}
		}

		return result.substring(0,result.length()-1);
		}
	public String[] getLeftRecursionRule(String litral,String ruleSplited) {
		String [] ruleSplitedArr = ruleSplited.split(",");
		String leftRec = "";
		String updteRule="";
		String dashLitral=litral+"'";
		String[] result = new String[2];
		for(int i = 0;i<ruleSplitedArr.length;i++) {
			if(litral.equals(ruleSplitedArr[i].charAt(0)+"")) {
				leftRec+=ruleSplitedArr[i].substring(1)+dashLitral+",";
			}
			else {
				updteRule+=ruleSplitedArr[i]+dashLitral+",";
			}
		}
		if(leftRec.length()>0) {
			result[0]=updteRule.substring(0,updteRule.length()-1);
			result[1] = leftRec+"e";
			return result;
		}
		return null;
	}
	
	
}
