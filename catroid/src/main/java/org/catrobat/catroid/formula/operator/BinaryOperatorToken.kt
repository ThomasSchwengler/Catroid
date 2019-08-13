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

package org.catrobat.catroid.formula.operator

import org.catrobat.catroid.formula.FormulaInterpreter
import org.catrobat.catroid.formula.textprovider.FormulaStringBuilder
import org.catrobat.catroid.formula.value.ValueToken
import java.util.Stack

abstract class BinaryOperatorToken(PRIORITY: Int, private val operatorText: String) : OperatorToken(PRIORITY) {

    override fun applyTo(values: Stack<ValueToken>) {
        val rightToken = values.pop()
        val leftToken = values.pop()
        values.push(applyTo(leftToken, rightToken))
    }

    override fun appendText(stringBuilder: FormulaStringBuilder) {
        stringBuilder.append(operatorText)
    }

    abstract fun applyTo(leftToken: ValueToken, rightToken: ValueToken): ValueToken

    class MissingBinaryOperatorToken : BinaryOperatorToken(666, "Missing binary operator") {
        override fun applyTo(leftToken: ValueToken, rightToken: ValueToken): ValueToken {
            throw Exception("Missing Binary Operator")
        }
    }

    class MultOperatorToken : BinaryOperatorToken(2, "×") {
        override fun applyTo(leftToken: ValueToken, rightToken: ValueToken): ValueToken {
            return ValueToken(leftToken.value * rightToken.value)
        }
    }

    class DivOperatorToken : BinaryOperatorToken(2, "÷") {
        override fun applyTo(leftToken: ValueToken, rightToken: ValueToken): ValueToken {
            if (rightToken.value == 0.0) throw Exception("DIVIDED BY 0")
            return ValueToken(leftToken.value / rightToken.value)
        }
    }

    class AddOperatorToken : BinaryOperatorToken(1, "+") {
        override fun applyTo(leftToken: ValueToken, rightToken: ValueToken): ValueToken {
            return ValueToken(leftToken.value + rightToken.value)
        }
    }

    class SubOperatorToken : BinaryOperatorToken(1, "-") {
        override fun applyTo(leftToken: ValueToken, rightToken: ValueToken): ValueToken {
            return ValueToken(leftToken.value - rightToken.value)
        }
    }

    class AndOperatorToken : BinaryOperatorToken(1, "and") {
        override fun applyTo(leftToken: ValueToken, rightToken: ValueToken): ValueToken {
            val value = FormulaInterpreter.eval(leftToken.value) && FormulaInterpreter.eval(rightToken.value)
            return ValueToken(FormulaInterpreter.eval(value))
        }
    }

    class OrOperatorToken : BinaryOperatorToken(0, "or") {
        override fun applyTo(leftToken: ValueToken, rightToken: ValueToken): ValueToken {
            val value = FormulaInterpreter.eval(leftToken.value) || FormulaInterpreter.eval(rightToken.value)
            return ValueToken(FormulaInterpreter.eval(value))
        }
    }

    class EqualsOperatorToken : BinaryOperatorToken(0, "=") {
        override fun applyTo(leftToken: ValueToken, rightToken: ValueToken): ValueToken {
            return ValueToken(FormulaInterpreter.eval(leftToken.value == rightToken.value))
        }
    }

    class GreaterOperatorToken : BinaryOperatorToken(0, ">") {
        override fun applyTo(leftToken: ValueToken, rightToken: ValueToken): ValueToken {
            return ValueToken(FormulaInterpreter.eval(leftToken.value > rightToken.value))
        }
    }

    class GreaterEqualsOperatorToken : BinaryOperatorToken(0, "≥") {
        override fun applyTo(leftToken: ValueToken, rightToken: ValueToken): ValueToken {
            return ValueToken(FormulaInterpreter.eval(leftToken.value >= rightToken.value))
        }
    }

    class SmallerOperatorToken : BinaryOperatorToken(0, "<") {
        override fun applyTo(leftToken: ValueToken, rightToken: ValueToken): ValueToken {
            return ValueToken(FormulaInterpreter.eval(leftToken.value < rightToken.value))
        }
    }

    class SmallerEqualsOperatorToken : BinaryOperatorToken(0, "≤") {
        override fun applyTo(leftToken: ValueToken, rightToken: ValueToken): ValueToken {
            return ValueToken(FormulaInterpreter.eval(leftToken.value <= rightToken.value))
        }
    }
    class NotEqualsOperatorToken : BinaryOperatorToken(0, "≠") {
        override fun applyTo(leftToken: ValueToken, rightToken: ValueToken): ValueToken {
            return ValueToken(FormulaInterpreter.eval(leftToken.value != rightToken.value))
        }
    }
}
