/*
 * Copyright (c) 2017 Villu Ruusmann
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
package org.jpmml.evaluator;

import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TextTokenizerTest {

	@Test
	public void split(){
		Pattern pattern = Pattern.compile("\\s+");

		TextTokenizer tokenizer = new TextSplitter(pattern);

		assertEquals(Collections.emptyList(), tokenizer.tokenize(""));

		assertEquals(Collections.emptyList(), tokenizer.tokenize(" "));
		assertEquals(Collections.emptyList(), tokenizer.tokenize("\t\t\t"));

		assertEquals(Collections.emptyList(), tokenizer.tokenize(","));
		assertEquals(Collections.emptyList(), tokenizer.tokenize(",,"));
		assertEquals(Collections.emptyList(), tokenizer.tokenize(", ,"));
		assertEquals(Collections.emptyList(), tokenizer.tokenize(" , , "));

		assertEquals(Arrays.asList("one"), tokenizer.tokenize("one"));
		assertEquals(Arrays.asList("one"), tokenizer.tokenize(" one"));
		assertEquals(Arrays.asList("one"), tokenizer.tokenize("one "));
		assertEquals(Arrays.asList("one"), tokenizer.tokenize("\u00BFone?"));

		assertEquals(Arrays.asList("one", "two", "three"), tokenizer.tokenize("one two three"));
		assertEquals(Arrays.asList("one", "two", "three"), tokenizer.tokenize("one!,\t\u00BFtwo?,\tthree."));
	}

	@Test
	public void match(){
		Pattern pattern = Pattern.compile("\\S+");

		TextTokenizer tokenizer = new TextMatcher(pattern);

		assertEquals(Collections.emptyList(), tokenizer.tokenize(""));

		assertEquals(Collections.emptyList(), tokenizer.tokenize(" "));
		assertEquals(Collections.emptyList(), tokenizer.tokenize("\t\t\t"));

		assertEquals(Arrays.asList(","), tokenizer.tokenize(","));
		assertEquals(Arrays.asList(",,"), tokenizer.tokenize(",,"));
		assertEquals(Arrays.asList(",", ","), tokenizer.tokenize(", ,"));
		assertEquals(Arrays.asList(",", ","), tokenizer.tokenize(" , , "));

		assertEquals(Arrays.asList("one", "two", "three"), tokenizer.tokenize("one two three"));
		assertEquals(Arrays.asList("one!,", "\u00BFtwo?,", "three."), tokenizer.tokenize("one!,\t\u00BFtwo?,\tthree."));

		pattern = Pattern.compile("\\w{4,}");

		tokenizer = new TextMatcher(pattern);

		assertEquals(Arrays.asList("three", "four", "five", "seven"), tokenizer.tokenize("one two three four five six seven"));
		assertEquals(Arrays.asList("three"), tokenizer.tokenize("one!,\t\u00BFtwo?,\tthree."));
	}
}