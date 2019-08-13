/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2017 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.test.formula;

import org.catrobat.catroid.formula.Formula;
import org.catrobat.catroid.formula.FormulaInterpreter;
import org.catrobat.catroid.formula.Token;
import org.catrobat.catroid.formula.function.BinaryFunctionToken.Max;
import org.catrobat.catroid.formula.function.BinaryFunctionToken.Min;
import org.catrobat.catroid.formula.function.BinaryFunctionToken.Mod;
import org.catrobat.catroid.formula.function.BinaryFunctionToken.Pow;
import org.catrobat.catroid.formula.function.FunctionToken;
import org.catrobat.catroid.formula.function.UnaryFunctionToken.Abs;
import org.catrobat.catroid.formula.function.UnaryFunctionToken.Acos;
import org.catrobat.catroid.formula.function.UnaryFunctionToken.Asin;
import org.catrobat.catroid.formula.function.UnaryFunctionToken.Atan;
import org.catrobat.catroid.formula.function.UnaryFunctionToken.Ceil;
import org.catrobat.catroid.formula.function.UnaryFunctionToken.Cos;
import org.catrobat.catroid.formula.function.UnaryFunctionToken.Exp;
import org.catrobat.catroid.formula.function.UnaryFunctionToken.Floor;
import org.catrobat.catroid.formula.function.UnaryFunctionToken.Lg;
import org.catrobat.catroid.formula.function.UnaryFunctionToken.Ln;
import org.catrobat.catroid.formula.function.UnaryFunctionToken.Round;
import org.catrobat.catroid.formula.function.UnaryFunctionToken.Sin;
import org.catrobat.catroid.formula.function.UnaryFunctionToken.Sqrt;
import org.catrobat.catroid.formula.function.UnaryFunctionToken.Tan;
import org.catrobat.catroid.formula.operator.BinaryOperatorToken.AddOperatorToken;
import org.catrobat.catroid.formula.operator.BinaryOperatorToken.AndOperatorToken;
import org.catrobat.catroid.formula.operator.BinaryOperatorToken.DivOperatorToken;
import org.catrobat.catroid.formula.operator.BinaryOperatorToken.EqualsOperatorToken;
import org.catrobat.catroid.formula.operator.BinaryOperatorToken.GreaterEqualsOperatorToken;
import org.catrobat.catroid.formula.operator.BinaryOperatorToken.GreaterOperatorToken;
import org.catrobat.catroid.formula.operator.BinaryOperatorToken.MultOperatorToken;
import org.catrobat.catroid.formula.operator.BinaryOperatorToken.NotEqualsOperatorToken;
import org.catrobat.catroid.formula.operator.BinaryOperatorToken.OrOperatorToken;
import org.catrobat.catroid.formula.operator.BinaryOperatorToken.SmallerEqualsOperatorToken;
import org.catrobat.catroid.formula.operator.BinaryOperatorToken.SmallerOperatorToken;
import org.catrobat.catroid.formula.operator.BinaryOperatorToken.SubOperatorToken;
import org.catrobat.catroid.formula.operator.BracketOperator;
import org.catrobat.catroid.formula.operator.UnaryOperatorToken.NotOperatorToken;
import org.catrobat.catroid.formula.value.ValueToken;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class FormulaTest {
	private void assertFormulaResult(double expectedResult, Formula formula) {
		FormulaInterpreter interpreter = new FormulaInterpreter();
		assertEquals(expectedResult, interpreter.eval(formula.getTokens()).getValue());
	}

	@Test
	public void testAdd() {
		List<Token> tokens = Arrays.asList(new ValueToken(5), new AddOperatorToken(), new ValueToken(0.8));

		Formula formula = new Formula(tokens);
		assertFormulaResult(5 + 0.8, formula);
	}

	@Test
	public void testSub() {
		List<Token> tokens = Arrays.asList(new ValueToken(5), new SubOperatorToken(), new ValueToken(0.8));

		Formula formula = new Formula(tokens);
		assertFormulaResult(5 - 0.8, formula);
	}

	@Test
	public void testMult() {
		List<Token> tokens = Arrays.asList(new ValueToken(5), new MultOperatorToken(), new ValueToken(0.8));

		Formula formula = new Formula(tokens);
		assertFormulaResult(5 * 0.8, formula);
	}

	@Test
	public void testDiv() {
		List<Token> tokens = Arrays.asList(new ValueToken(5), new DivOperatorToken(), new ValueToken(0.8));

		Formula formula = new Formula(tokens);
		assertFormulaResult(5 / 0.8, formula);
	}

	@Test
	public void testInvalidDiv() {
		List<Token> tokens = Arrays.asList(new ValueToken(5), new DivOperatorToken(), new ValueToken(0));

		Formula formula = new Formula(tokens);

		try {
			FormulaInterpreter interpreter = new FormulaInterpreter();
			interpreter.eval(formula.getTokens()).getValue();
			Assert.fail("Division by 0 should NEVER work!");
		} catch (Exception e) {
			assertEquals("DIVIDED BY 0", e.getMessage());
		}
	}

	@Test
	public void testMathOperatorPrecedence() {
		List<Token> tokens = Arrays.asList(
				new ValueToken(100),
				new AddOperatorToken(),
				new ValueToken(5),
				new MultOperatorToken(),
				new ValueToken(0.1));

		Formula formula = new Formula(tokens);
		assertFormulaResult(100 + 5 * 0.1, formula);
	}

	@Test
	public void testBracketsInMathFormulaWithMultiply() {
		List<Token> tokens = Arrays.asList(
				new BracketOperator.LeftBracket(),
				new ValueToken(100),
				new AddOperatorToken(),
				new ValueToken(5),
				new BracketOperator.RightBracket(),
				new MultOperatorToken(),
				new ValueToken(0.1));

		Formula formula = new Formula(tokens);
		assertFormulaResult((100 + 5) * 0.1, formula);
	}

	@Test
	public void testBracketsInMathFormulaWithDivide() {
		List<Token> tokens = Arrays.asList(
				new ValueToken(-2),
				new MultOperatorToken(),
				new BracketOperator.LeftBracket(),
				new ValueToken(100),
				new SubOperatorToken(),
				new ValueToken(5),
				new BracketOperator.RightBracket(),
				new DivOperatorToken(),
				new ValueToken(0.1));

		Formula formula = new Formula(tokens);
		assertFormulaResult(-2 * (100 - 5) / 0.1, formula);
	}

	private void testFunction(double expectedResult, FunctionToken functionToken) {
		List<Token> tokens = Collections.singletonList(functionToken);
		Formula formula = new Formula(tokens);

		assertFormulaResult(expectedResult, formula);
	}

	@Test
	public void testSin() {
		List<Token> tokens = Arrays.asList(new ValueToken(100), new DivOperatorToken(), new ValueToken(5));

		Sin func = new Sin(tokens);
		testFunction(Math.sin(100 / 5f), func);
	}

	@Test
	public void testCos() {
		List<Token> tokens = Arrays.asList(new ValueToken(100), new DivOperatorToken(), new ValueToken(5));

		Cos func = new Cos(tokens);
		testFunction(Math.cos(100 / 5f), func);
	}

	@Test
	public void testTan() {
		List<Token> tokens = Arrays.asList(new ValueToken(100), new DivOperatorToken(), new ValueToken(5));

		Tan func = new Tan(tokens);
		testFunction(Math.tan(100 / 5f), func);
	}

	@Test
	public void testLn() {
		List<Token> tokens = Arrays.asList(new ValueToken(100), new DivOperatorToken(), new ValueToken(5));

		Ln func = new Ln(tokens);
		testFunction(Math.log(100 / 5f), func);
	}

	@Test
	public void testLog() {
		List<Token> tokens = Arrays.asList(new ValueToken(100), new DivOperatorToken(), new ValueToken(5));

		Lg func = new Lg(tokens);
		testFunction(Math.log10(100 / 5f), func);
	}

	@Test
	public void testSqrt() {
		List<Token> tokens = Arrays.asList(new ValueToken(100), new DivOperatorToken(), new ValueToken(5));

		Sqrt func = new Sqrt(tokens);
		testFunction(Math.sqrt(100 / 5f), func);
	}

	@Test
	public void testAbs() {
		List<Token> tokens = Arrays.asList(new ValueToken(-2), new MultOperatorToken(), new ValueToken(5));

		Abs func = new Abs(tokens);
		testFunction(Math.abs(-2 * 5f), func);
	}

	@Test
	public void testAsin() {
		List<Token> tokens = Arrays.asList(new ValueToken(100), new DivOperatorToken(), new ValueToken(5));

		Asin func = new Asin(tokens);
		testFunction(Math.asin(100 / 5f), func);
	}

	@Test
	public void testAcos() {
		List<Token> tokens = Arrays.asList(new ValueToken(100), new DivOperatorToken(), new ValueToken(5));

		Acos func = new Acos(tokens);
		testFunction(Math.acos(100 / 5f), func);
	}

	@Test
	public void testAtan() {
		List<Token> tokens = Arrays.asList(new ValueToken(100), new DivOperatorToken(), new ValueToken(5));

		Atan func = new Atan(tokens);
		testFunction(Math.atan(100 / 5f), func);
	}

	@Test
	public void testExp() {
		List<Token> tokens = Arrays.asList(new ValueToken(100), new DivOperatorToken(), new ValueToken(5));

		Exp func = new Exp(tokens);
		testFunction(Math.exp(100 / 5f), func);
	}

	@Test
	public void testFloor() {
		List<Token> tokens = Collections.singletonList(new ValueToken(0.1));

		Floor func = new Floor(tokens);
		testFunction(Math.floor(0.1), func);
	}

	@Test
	public void testCeil() {
		List<Token> tokens = Collections.singletonList(new ValueToken(0.1));

		Ceil func = new Ceil(tokens);
		testFunction(Math.ceil(0.1), func);
	}

	@Test
	public void testRoundUp() {
		List<Token> tokens = Collections.singletonList(new ValueToken(0.8));

		Round func = new Round(tokens);
		testFunction(Math.round(0.8), func);
	}

	@Test
	public void testRoundDown() {
		List<Token> tokens = Collections.singletonList(new ValueToken(0.1));

		Round func = new Round(tokens);
		testFunction(Math.round(0.1), func);
	}

	@Test
	public void testGreater() {
		List<Token> tokens = Arrays.asList(new ValueToken(5), new GreaterOperatorToken(), new ValueToken(0.8));

		Formula formula = new Formula(tokens);
		assertFormulaResult(1, formula);
	}

	@Test
	public void testGreaterEquals() {
		List<Token> tokens = Arrays.asList(new ValueToken(5), new GreaterEqualsOperatorToken(), new ValueToken(0.8));

		Formula formula = new Formula(tokens);
		assertFormulaResult(1, formula);
	}

	@Test
	public void testSmaller() {
		List<Token> tokens = Arrays.asList(new ValueToken(5), new SmallerOperatorToken(), new ValueToken(0.8));

		Formula formula = new Formula(tokens);
		assertFormulaResult(0, formula);
	}

	@Test
	public void testSmallerEquals() {
		List<Token> tokens = Arrays.asList(new ValueToken(5), new SmallerEqualsOperatorToken(), new ValueToken(0.8));

		Formula formula = new Formula(tokens);
		assertFormulaResult(0, formula);
	}

	@Test
	public void testEquals() {
		List<Token> tokens = Arrays.asList(new ValueToken(5), new EqualsOperatorToken(), new ValueToken(0.8));

		Formula formula = new Formula(tokens);
		assertFormulaResult(0, formula);
	}

	@Test
	public void testNotEquals() {
		List<Token> tokens = Arrays.asList(new ValueToken(5), new NotEqualsOperatorToken(), new ValueToken(0.8));

		Formula formula = new Formula(tokens);
		assertFormulaResult(1, formula);
	}

	@Test
	public void testAnd() {
		List<Token> tokens = Arrays.asList(new ValueToken(1), new AndOperatorToken(), new ValueToken(0));

		Formula formula = new Formula(tokens);
		assertFormulaResult(0, formula);
	}

	@Test
	public void testOr() {
		List<Token> tokens = Arrays.asList(new ValueToken(1), new OrOperatorToken(), new ValueToken(0));

		Formula formula = new Formula(tokens);
		assertFormulaResult(1, formula);
	}

	@Test
	public void testNot() {
		List<Token> tokens = Arrays.asList(new NotOperatorToken(), new ValueToken(0));

		Formula formula = new Formula(tokens);
		assertFormulaResult(1, formula);
	}

	@Test
	public void testBooleanOperatorPrecedence() {
		List<Token> tokens = Arrays.asList(
				new NotOperatorToken(),
				new ValueToken(0),
				new AndOperatorToken(),
				new ValueToken(1),
				new OrOperatorToken(),
				new ValueToken(0));

		Formula formula = new Formula(tokens);
		assertFormulaResult(1, formula);
	}

	@Test
	public void testBracketsInBooleanFormulaInverted() {
		List<Token> tokens = Arrays.asList(
				new NotOperatorToken(),
				new BracketOperator.LeftBracket(),
				new ValueToken(0),
				new AndOperatorToken(),
				new ValueToken(1),
				new BracketOperator.RightBracket());

		Formula formula = new Formula(tokens);
		assertFormulaResult(1, formula);

	}

	@Test
	public void testBracketsInBooleanFormula() {
		List<Token> tokens = Arrays.asList(
				new BracketOperator.LeftBracket(),
				new ValueToken(0),
				new OrOperatorToken(),
				new ValueToken(1),
				new BracketOperator.RightBracket(),
				new AndOperatorToken(),
				new ValueToken(1));

		Formula formula = new Formula(tokens);
		assertFormulaResult(1, formula);
	}

	@Test
	public void testMax() {
		List<Token> leftTokens = Collections.singletonList(new ValueToken(0.1));
		List<Token> rightTokens = Collections.singletonList(new ValueToken(100));

		Max func = new Max(leftTokens, rightTokens);
		testFunction(Math.max(0.1, 100), func);
	}

	@Test
	public void testMin() {
		List<Token> leftTokens = Collections.singletonList(new ValueToken(0.1));
		List<Token> rightTokens = Collections.singletonList(new ValueToken(100));

		Min func = new Min(leftTokens, rightTokens);
		testFunction(Math.min(0.1, 100), func);
	}

	@Test
	public void testPow() {
		List<Token> leftTokens = Collections.singletonList(new ValueToken(0.1));
		List<Token> rightTokens = Collections.singletonList(new ValueToken(100));

		Pow func = new Pow(leftTokens, rightTokens);
		testFunction(Math.pow(0.1, 100), func);
	}

	@Test
	public void testMod() {
		List<Token> leftTokens = Collections.singletonList(new ValueToken(100));
		List<Token> rightTokens = Collections.singletonList(new ValueToken(0.1));

		Mod func = new Mod(leftTokens, rightTokens);
		testFunction(100 % 0.1, func);
	}
}
