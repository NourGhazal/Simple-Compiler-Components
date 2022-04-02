# Task 1:
In this task DFA simulation was implemented.
The DFA is created using the following convention:
0,0,1;1,2,1;2,0,3;3,3,3#1,3
- Each state is separated using ";"  
- The state definition is separated using ","
- The first number in each state is the state number, the second number is the zero transitions and the third and final number is the one transition 
- After the # is the accept states of the DFA separated by ","

For simplicity the alphabet is considered to be consisting of the binary alphabet only and the start state is always 0.


# Task 2:
In this task NFA simulation was implemented.
The NFA is created using the following convention:
0,0;1,2;3,3#0,0;0,1;2,3;3,3#1,2#3
- NFA description was separated into blocks using #
- Each block was separated using ";"to define the state transitions
- The first block represented zero transitions, the second block represented one transitions, the third block represented the epsilon transitions and the fourth and final block represented accept states separated using ","
- The first number in each state is the state number, the second number is the zero transitions and the third and final number is the one transition 
- After the # is the accept states of the NFA separated by ","

For simplicity the alphabet is considered to be consisting of the binary alphabet only and the start state is always 0.
# Task 3:
In this task the conversion from a regular expression to a NFA was implemented.
The Resulting NFA  was in the form of  N#I#F#Z#O#E
- N is the number of states in the NFA
- I is the initial  state in the NFA
- F is the final state in the NFA
- Z is the zero transitions in the NFA
- O is the one transition in the NFA
- E is the epsilon transition in the NFA

The following assumptions were made for made for simplicity:
- The alphabet is over the binary alphabet only 
- The regular expression does not include $\phi$
- The empty string $\epsilon$ is represented by e
- The symbol $\omicron$ is represented by .
- The symbol $\cup$ is represented by |
- Regular expressions are represented in postfix notation
- States of the resulting NFA are numbers
# Task 4:
In this task Fallback DFA simulation was implemented.
The Fallback DFA is created using the following convention:
0,0,1,A;1,2,1,B;2,0,3,C;3,3,3,N#0,1,2
- Each state is separated using ";"  
- The state definition is separated using ","
- The first number in each state is the state number, the second number is the zero transitions, the third number is the one transition and the fourth and final character is the transition label(action taken at this state)
- After the # is the accept states of the Fallback DFA separated by ","

The run function should produce an output of the following format S;A;:
- S is the longest accepted String 
- A is the action taken at the end of this String
- S and A may be repeated several times depending on how many strings would be accepted.

For simplicity the alphabet is considered to be consisting of the binary alphabet only and the start state is always 0.
# Task 5: