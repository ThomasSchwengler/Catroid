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

package org.catrobat.catroid.formula.function

import org.catrobat.catroid.formula.FormulaInterpreter
import org.catrobat.catroid.formula.Token
import org.catrobat.catroid.formula.textprovider.FormulaStringBuilder
import org.catrobat.catroid.formula.value.ValueToken

abstract class UnaryFunctionToken(val tokens: List<Token>, private val functionText:
String) : FunctionToken() {

    override fun appendText(stringBuilder: FormulaStringBuilder) {
        stringBuilder.beginFunction(functionText)
        tokens.forEach { token -> token.appendText(stringBuilder) }
        stringBuilder.endFunction()
    }

    class Sin(tokens: List<Token>) : UnaryFunctionToken(tokens, "sine") {
        override fun eval(interpreter: FormulaInterpreter) = ValueToken(Math.sin(interpreter.eval(tokens).value))
    }

    class Cos(tokens: List<Token>) : UnaryFunctionToken(tokens, "cosine") {
        override fun eval(interpreter: FormulaInterpreter) = ValueToken(Math.cos(interpreter.eval(tokens).value))
    }

    class Tan(tokens: List<Token>) : UnaryFunctionToken(tokens, "tangent") {
        override fun eval(interpreter: FormulaInterpreter) = ValueToken(Math.tan(interpreter.eval(tokens).value))
    }

    class Ln(tokens: List<Token>) : UnaryFunctionToken(tokens, "natural logarithm") {
        override fun eval(interpreter: FormulaInterpreter) = ValueToken(Math.log(interpreter.eval(tokens).value))
    }

    class Lg(tokens: List<Token>) : UnaryFunctionToken(tokens, "decimal logarithm") {
        override fun eval(interpreter: FormulaInterpreter) = ValueToken(Math.log10(interpreter.eval(tokens).value))
    }

    class Sqrt(tokens: List<Token>) : UnaryFunctionToken(tokens, "square root") {
        override fun eval(interpreter: FormulaInterpreter) = ValueToken(Math.sqrt(interpreter.eval(tokens).value))
    }

    class Abs(tokens: List<Token>) : UnaryFunctionToken(tokens, "absolute value") {
        override fun eval(interpreter: FormulaInterpreter): ValueToken = ValueToken(Math.abs(interpreter.eval(tokens).value))
    }

    class Asin(tokens: List<Token>) : UnaryFunctionToken(tokens, "arcsine") {
        override fun eval(interpreter: FormulaInterpreter) = ValueToken(Math.asin(interpreter.eval(tokens).value))
    }

    class Acos(tokens: List<Token>) : UnaryFunctionToken(tokens, "arccosine") {
        override fun eval(interpreter: FormulaInterpreter) = ValueToken(Math.acos(interpreter.eval(tokens).value))
    }

    class Atan(tokens: List<Token>) : UnaryFunctionToken(tokens, "arctangent") {
        override fun eval(interpreter: FormulaInterpreter) = ValueToken(Math.atan(interpreter.eval(tokens).value))
    }

    class Exp(tokens: List<Token>) : UnaryFunctionToken(tokens, "exponent") {
        override fun eval(interpreter: FormulaInterpreter) = ValueToken(Math.exp(interpreter.eval(tokens).value))
    }

    class Floor(tokens: List<Token>) : UnaryFunctionToken(tokens, "floor") {
        override fun eval(interpreter: FormulaInterpreter) = ValueToken(Math.floor(interpreter.eval(tokens).value))
    }

    class Ceil(tokens: List<Token>) : UnaryFunctionToken(tokens, "ceiling") {
        override fun eval(interpreter: FormulaInterpreter) = ValueToken(Math.ceil(interpreter.eval(tokens).value))
    }

    class Round(tokens: List<Token>) : UnaryFunctionToken(tokens, "round") {
        override fun eval(interpreter: FormulaInterpreter) = ValueToken(Math.round(interpreter.eval(tokens).value).toDouble())
    }
}
