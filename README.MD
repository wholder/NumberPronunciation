## ***NumberPronunciation***

I wrote this code to replace the use of equivalent code in the `com.ibm.icu.text.RuleBasedNumberFormat` package in the [ICU4J library](https://wiki.eclipse.org/ICU4J).  While very capable, the JAR file for this library weighs in at 12.9 megs and I only needed this one feature for a text to speech project I am working on.  The code here is entirely my own, but I have tested it against the UCU4J library (test code included) for English pronunciation.

### Limitations
Unlike the ICU4J library, which can handle many languages, this code only handles English pronunciation of cardinal and ordinal numbers.


### Credits
 - [IntelliJ IDEA from JetBrains](https://www.jetbrains.com/idea/) (my favorite development environment for Java coding. Thanks for a great IDE, JetBrains!