/*
 * Copyright (c) 2019 Villu Ruusmann
 *
 * This file is part of JPMML-Evaluator
 *
 * JPMML-Evaluator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPMML-Evaluator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with JPMML-Evaluator.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpmml.evaluator.functions;

import java.util.List;

import org.jpmml.evaluator.FieldValue;

abstract
public class BinaryFunction extends AbstractFunction {

	public BinaryFunction(String name){
		super(name);
	}

	public BinaryFunction(String name, List<String> aliases){
		super(name, aliases);
	}

	abstract
	public FieldValue evaluate(FieldValue left, FieldValue right);

	@Override
	public FieldValue evaluate(List<FieldValue> arguments){
		checkFixedArityArguments(arguments, 2);

		return evaluate(getRequiredArgument(arguments, 0), getRequiredArgument(arguments, 1));
	}
}