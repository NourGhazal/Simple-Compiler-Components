/**
 * Write your info here
 *
 * @name John Smith
 * @id 43-0234
 * @labNumber 07
 */

grammar Task9;

@members {
	/**
	 * Compares two integer numbers
	 *
	 * @param x the first number to compare
	 * @param y the second number to compare
	 * @return 1 if x is equal to y, and 0 otherwise
	 */
	public static int equals(int x, int y) {
	    return x == y ? 1 : 0;
	}
}

s returns [int check]:
//    a[0] b[0] c[0] {$check = equals($a.val,$b.val) * equals($b.val,$c.val) ;} |
    a[0] c[0] b[0] {$check = equals($a.val,$b.val) * equals($b.val,$c.val) ;} ;
//    b[0] a[0] c[0]  {$check = equals($a.val,$b.val) * equals($b.val,$c.val) ;} |
//    b[0] c[0] a[0] {$check = equals($a.val,$b.val) * equals($b.val,$c.val) ;}|
//    c[0]  a[0] b[0] {$check = equals($a.val,$b.val) * equals($b.val,$
//    c.val) ;} |
//    c[0] b[0] a[0] {$check = equals($a.val,$b.val) * equals($b.val,$c.val) ;};
 // Write the definition of parser rule "s" here
a [int inh] returns [int val]:
    'a' a[$inh +1] {$val = 1 + $a.val;} |  {$val = $inh;} ;

b [int inh] returns [int val]:
    'b' b[$inh +1] {$val = 1 + $b.val ;} |  {$val = $inh;} ;

c [int inh] returns [int val]:
     'c' c[$inh +1] {$val = 1 + $c.val ;} |  {$val = $inh ;} ;

// Write additional lexer and parser rules here