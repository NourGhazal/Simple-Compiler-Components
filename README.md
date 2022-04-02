# Task 1:
In this task DFA simulation was implemented.
The DFA is created using the following convention:
0,0,1;1,2,1;2,0,3;3,3,3#1,3
- Each state is separated using ;   
- The state definition is separated using ,
- The first number in each state is the state number, the second number is the zero transitions and the third and final number is the one transition 
- After the # is the accept states of the DFA separated by ,

For simplicity the alphabet is considered to be consisting of 0 or 1 only.


# Task 2:
In this task NFA simulation was implemented.
The NFA is created using the following convention:
0,0;1,2;3,3#0,0;0,1;2,3;3,3#1,2#3
- NFA description was separated into blocks using #
- Each block was separated using ; to define the state transitions
- The first block represented zero transitions, the second block represented one transitions, the third block represented the epsilon transitions and the fourth and final block represented accept states separated using ,
- The first number in each state is the state number, the second number is the zero transitions and the third and final number is the one transition 
- After the # is the accept states of the NFA separated by ,

For simplicity the alphabet is considered to be consisting of 0 or 1 only.
# Task 3:
# Task 4:
# Task 5: