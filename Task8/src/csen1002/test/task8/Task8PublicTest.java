import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.Vocabulary;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import csen1002.main.task8.task8Lexer;

public class Task8PublicTest {

	public static String tokenStream(String input){
		//In case Task8Lexer is not defined correctly:
		//1- If you modified the grammar in the g4 file, regenerate the ANTLR recognizer to make sure that the latest grammar is generated
		//2- Make sure that the location and the package are configured properly
		task8Lexer lexer = new task8Lexer(CharStreams.fromString(input));
		Vocabulary vocabulary = lexer.getVocabulary();
		List<? extends Token> tokens = lexer.getAllTokens();
		StringBuilder stringBuilder = new StringBuilder();
		for (Token token:tokens) {
			stringBuilder.append(token.getText()).append(",").append(vocabulary.getSymbolicName(token.getType())).append(";");
		}
		return stringBuilder.toString();
	}


	@Test(timeout=100)
	public void testFDFA1T0() {
		Assert.assertEquals("011100,Q2;", tokenStream("011100"));
	}
	@Test(timeout=100)
	public void testFDFA1T1() {
		Assert.assertEquals("10100111010,Q3;", tokenStream("10100111010"));
	}
	@Test(timeout=100)
	public void testFDFA1T2() {
		Assert.assertEquals("11011001111101,Q4;", tokenStream("11011001111101"));
	}
	@Test(timeout=100)
	public void testFDFA1T3() {
		Assert.assertEquals("0001001100,Q2;", tokenStream("0001001100"));
	}
	@Test(timeout=100)
	public void testFDFA1T4() {
		Assert.assertEquals("10011,Q4;", tokenStream("10011"));
	}
	@Test(timeout=100)
	public void testFDFA1T5() {
		Assert.assertEquals("000,Q3;00111,Q4;", tokenStream("00000111"));
	}
	@Test(timeout=100)
	public void testFDFA1T6() {
		Assert.assertEquals("0000001,Q4;", tokenStream("0000001"));
	}
	@Test(timeout=100)
	public void testFDFA1T7() {
		Assert.assertEquals("0111100111011101110,Q3;", tokenStream("0111100111011101110"));
	}
	@Test(timeout=100)
	public void testFDFA1T8() {
		Assert.assertEquals("0011000001110,Q3;", tokenStream("0011000001110"));
	}
	@Test(timeout=100)
	public void testFDFA1T9() {
		Assert.assertEquals("010101011000,Q3;", tokenStream("010101011000"));
	}



}