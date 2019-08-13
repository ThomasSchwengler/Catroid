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

abstract class BinaryFunctionToken(val leftTokens: List<Token>, val rightTokens: List<Token>, private val functionText:
String) : FunctionToken() {

    override fun appendText(stringBuilder: FormulaStringBuilder) {
        stringBuilder.beginFunction(functionText)
        leftTokens.forEach { token -> token.appendText(stringBuilder) }
        stringBuilder.nextArgument()
        rightTokens.forEach { token -> token.appendText(stringBuilder) }
        stringBuilder.endFunction()
    }

    class Max(leftTokens: List<Token>, rightTokens: List<Token>) : BinaryFunctionToken(leftTokens, rightTokens,
                                                                                       "maximum of") {
        override fun eval() = ValueToken(Math.max(FormulaInterpreter().eval(leftTokens).value,
                                       FormulaInterpreter().eval(rightTokens).value))
    }

    class Min(leftTokens: List<Token>, rightTokens: List<Token>) : BinaryFunctionToken(leftTokens, rightTokens,
                                                                                       "minimum of") {
        override fun eval() = ValueToken(Math.min(FormulaInterpreter().eval(leftTokens).value,
                FormulaInterpreter().eval(rightTokens).value))
    }

    class Pow(leftTokens: List<Token>, rightTokens: List<Token>) : BinaryFunctionToken(leftTokens, rightTokens,
                                                                                       "power") {

        override fun eval() = ValueToken(Math.pow(FormulaInterpreter().eval(leftTokens).value,
                FormulaInterpreter().eval(rightTokens).value))
    }

    class Mod(leftTokens: List<Token>, rightTokens: List<Token>) : BinaryFunctionToken(leftTokens, rightTokens,
                                                                                       "modulo") {
        override fun eval() = ValueToken(FormulaInterpreter().eval(leftTokens).value %
                FormulaInterpreter().eval(rightTokens).value)
    }
}
