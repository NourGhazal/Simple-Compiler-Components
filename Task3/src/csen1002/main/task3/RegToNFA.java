package csen1002.main.task3;

import java.util.Stack;

/**
 * Write your info here
 * 
 * @name Noureldean Ghazal
 * @id 43-15747
 * @labNumber 15
 */

public class RegToNFA {

	
	String regularExpresion;
	String finalNfa;
	/**
	 * Constructs an NFA corresponding to a regular expression based on Thompson's
	 * construction
	 * 
	 * @param regex The regular expression in postfix notation for which the NFA is
	 *              to be constructed
	 */
	public RegToNFA(String regex) {
		// TODO Auto-generated constructor stub
		this.regularExpresion = regex;
		Stack<String> answerSteps=  new Stack<String>();
		int currentStart = 0;
		for(int i=0; i<regularExpresion.length();i++) {
			switch(regularExpresion.charAt(i)) 
			{
			case '|': {
				String nfa = handleUnion(answerSteps.pop(), answerSteps.pop(), currentStart);
				answerSteps.push(nfa);
				currentStart+=2;
				break;
				}
			case '.': {
				String nfa = handleConcat(answerSteps.pop(), answerSteps.pop(), currentStart);
				answerSteps.push(nfa);
				break;
			}
			case '*': {
				String nfa = handleStar(answerSteps.pop(),currentStart);
				answerSteps.push(nfa);
				currentStart+=2;
				break;
			}
			case'1': {	
				String nfa = currentStart+"#"+(currentStart+1)+"#"+"#"+currentStart+","+(currentStart+1)+"#"+"";
				answerSteps.push(nfa);
				currentStart+=2;
				break;
				}
			case'0': {	
				String nfa = currentStart+"#"+(currentStart+1)+"#"+currentStart+","+(currentStart+1)+"#"+"#"+"";
				answerSteps.push(nfa); 
				currentStart+=2;
				break;
				}
			case'e': {	
				String nfa = currentStart+"#"+(currentStart+1)+"#"+"#"+"#"+currentStart+","+(currentStart+1)+"";
				answerSteps.push(nfa); 
				currentStart+=2;
				break;
				}
			}
		}
		
		this.finalNfa = currentStart+"#"+answerSteps.pop();
	}

	/**
	 * @return Returns a formatted string representation of the NFA. The string
	 *         representation follows the one in the task description
	 */
	@Override
	public String toString() {
	
		String [] finalNfaSplited = this.finalNfa.split("#",-1);
		String zeroTransitions = finalNfaSplited[3]; 
		String oneTransitions = finalNfaSplited[4]; 
		String epsilonTransitions = finalNfaSplited[5];
		
		return finalNfaSplited[0]+"#"+finalNfaSplited[1]+"#"+finalNfaSplited[2]+"#"
				+sortString(zeroTransitions)+"#"+sortString(oneTransitions)+"#"+sortString(epsilonTransitions);
		
	}
	public String sortString(String x) {
		String[] splitedX = x.split(";");
		for (int i =0;i<splitedX.length;i+=1) {
			for(int j = splitedX.length-1 ; j>i;j-=1) {
				if(Integer.parseInt(splitedX[i].split(",")[0]) > Integer.parseInt(splitedX[j].split(",")[0])) {
					String x1 = splitedX[i];
					String y1 =splitedX[j];
					splitedX[i] = y1;
					splitedX[j]=x1;
				}
				if(Integer.parseInt(splitedX[i].split(",")[0]) == Integer.parseInt(splitedX[j].split(",")[0])) {
				if(Integer.parseInt(splitedX[i].split(",")[1])>Integer.parseInt(splitedX[j].split(",")[1])) {
					String x1 = splitedX[i];
					String y1 =splitedX[j];
					splitedX[i] = y1;
					splitedX[j]=x1;
				}
				}
			}
		}
		return String.join(";", splitedX);
	}
	public String handleUnion(String nfaA, String nfaB, int currentStart) {
		String []nfaASplited = nfaA.split("#",-1);
		String []nfaBSplited = nfaB.split("#",-1);
		String zreoTransitions = (nfaASplited[2].length()>0 && nfaBSplited[2].length()>0)?(nfaASplited[2]+";"+nfaBSplited[2]):(nfaASplited[2]+nfaBSplited[2]);
		String oneTransitions = (nfaASplited[3].length()>0 && nfaBSplited[3].length()>0)?(nfaASplited[3]+";"+nfaBSplited[3]):(nfaASplited[3]+nfaBSplited[3]);
		String epsilonTransitions=  ((nfaASplited[4].length()>0 && nfaBSplited[4].length()>0)?(nfaASplited[4]+";"+nfaBSplited[4]):(nfaASplited[4]+nfaBSplited[4]))
				+((nfaASplited[4].length()==0 && nfaBSplited[4].length()==0)?"":";")+	
				(currentStart+","+nfaASplited[0])+";"+(currentStart+","+nfaBSplited[0])+";"+
				(nfaASplited[1]+","+(currentStart+1))+";"+(nfaBSplited[1]+","+(currentStart+1));
		return  currentStart+"#"+(currentStart+1)+"#"+
				zreoTransitions+"#"+
				oneTransitions+"#"+
				epsilonTransitions;
				
	}

	public String handleConcat(String nfaA, String nfaB, int currentStart) {
		String []nfaASplited = nfaA.split("#",-1);
		String []nfaBSplited = nfaB.split("#",-1);
		
		String zreoTransitions = (nfaASplited[2].length()>0 && nfaBSplited[2].length()>0)?(nfaASplited[2]+";"+nfaBSplited[2]):(nfaASplited[2]+nfaBSplited[2]);
		String oneTransitions = (nfaASplited[3].length()>0 && nfaBSplited[3].length()>0)?(nfaASplited[3]+";"+nfaBSplited[3]):(nfaASplited[3]+nfaBSplited[3]);
		String epsilonTransitions= ((nfaASplited[4].length()>0 && nfaBSplited[4].length()>0)?(nfaASplited[4]+";"+nfaBSplited[4]):(nfaASplited[4]+nfaBSplited[4]))
									+((nfaASplited[4].length()==0 && nfaBSplited[4].length()==0)?"":";")+	
									(nfaBSplited[1]+","+nfaASplited[0]);
		return  nfaBSplited[0]+"#"+(nfaASplited[1])+"#"+
				zreoTransitions+"#"+
				oneTransitions+"#"+
				epsilonTransitions;
				
	}
	public String handleStar(String nfaA, int currentStart) {
		String []nfaASplited = nfaA.split("#",-1);
		String zreoTransitions = nfaASplited[2];
		String oneTransitions = nfaASplited[3];
		String epsilonTransitions=( nfaASplited[4].length()>0?nfaASplited[4]+";":"")+
				(currentStart+","+nfaASplited[0])+";"+(nfaASplited[1]+","+(currentStart+1))+";"+
				currentStart+","+(currentStart+1)+";"+(nfaASplited[1]+","+nfaASplited[0]);
		
		return  currentStart+"#"+(currentStart+1)+"#"+
				zreoTransitions+"#"+
				oneTransitions+"#"+
				epsilonTransitions;
	}
	

}
