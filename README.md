# projects

Here are some assorted projects that I've worked on over the years. Feel free to peruse, comment on my coding style, try to tell me that my opening curly brace should go on the same line as my method signature, etc.

Here's what you'll find:

##ExcerptGenerator
A C# program that, given a corpus of text, writes a sentence to the command line using trigrams from the text, generating a mock "excerpt." The corpus it's currently set to use is Jane Austen's Pride and Prejudice, but the code can be modified to point to any text dump of words. Project Gutenberg's a pretty good resource for finding large bodies of text, and, in my experience, it's always more fun to generate a sentence using archaic language than it is to generate one using more a recent vocabulary.

##KnightPhone
A Java program that, given a number _n_. writes to the command line the number of _n_-digit phone numbers that a chess knight could dial.
```
  1 2 3
  4 5 6
  7 8 9
    0
```
A chess knight is said to be able to dial numbers in an L-formation, so, for example, if the knight begins at _1_, it can jump to _6_ or _8_ as its next digit, forming two 2-digit numbers: _16_ and _18_. Then, from the _6_ and the _8_, it can jump to the next number to form 3-digit numbers.
