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

abstract class UnaryOperatorToken(PRIORITY: Int, private val operatorText: String) : OperatorToken(PRIORITY) {

    override fun applyTo(values: Stack<ValueToken>) {
        values.push(applyTo(values.pop()))
    }

    abstract fun applyTo(token: ValueToken) : ValueToken

    override fun appendText(stringBuilder: FormulaStringBuilder) {
        stringBuilder.append(operatorText)
    }

    class NotOperatorToken : UnaryOperatorToken(2, "not") {
        override fun applyTo(token: ValueToken) : ValueToken =
                ValueToken(FormulaInterpreter.eval(!FormulaInterpreter.eval(token.value)))
    }
}
