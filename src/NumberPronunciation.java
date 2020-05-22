/*
 *  This code is a partial replacement for the com.ibm.icu.text.RuleBasedNumberFormat package in the ICU4J library
 *
 *  Author: Wayne Holder, 2019
 *  License: MIT (https://opensource.org/licenses/MIT)
 */

public class NumberPronunciation {
  private static final String[] cDigits = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
  private static final String[] cTeens = {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen",
                                          "eighteen", "nineteen"};
  private static final String[] cTens = {"twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
  private static final String[] cThous = {"thousand", "million", "billion", "trillion", "quadrillion", "quintillion"};

  private static final String[] oDigits = {"first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth"};
  private static final String[] oTeens = {"tenth", "eleventh", "twelfth", "thirteenth", "fourteenth", "fifteenth", "sixteenth",
                                          "seventeenth", "eighteenth", "nineteenth"};
  private static final String[] oTens = {"twentieth", "thirtieth", "fortieth", "fiftieth", "sixtieth", "seventieth", "eightieth",
                                         "ninetieth"};

  /**
   * Convert number into a pronounceable ordinal string, such as:
   *  "one million three hundred thirty-one thousand two hundred twenty-ninth"
   * @param number number to convert (0 - max long value)
   * @return pronounceable ordinal string
   */
  public static String sayOrdinal (long number) {
    if (number > 0) {
      return sayIt(number, true);
    } else {
      return "zeroth";
    }
  }

  /**
   * Convert number into a pronounceable cardinal string, such as:
   *  "one million three hundred thirty-one thousand two hundred twenty-nine"
   * @param number number to convert (0 - max long value)
   * @return pronounceable ordinal string
   */
  public static String sayCardinal (long number) {
    if (number > 0) {
      return sayIt(number, false);
    } else {
      return "zero";
    }
  }

  private static String sayIt (long number, boolean ordinal) {
    String[] parts = String.format("%d", number).split("\\.");
    StringBuilder buf = new StringBuilder();
    String msd = parts[0];
    // Break into sets of 3 digits
    int len = msd.length();
    int pre = len - (len / 3) * 3;
    int idx = 0;
    int step = pre > 0 ? pre : 3;
    while (idx < len) {
      int end = idx + step;
      int thous = (len - end) / 3;
      String t1 = msd.substring(idx, end);
      int segVal = Integer.parseInt(t1);
      String t2 = msd.substring(end);
      long remain = thous > 0 ? Long.parseLong(t2) : 0;
      if (segVal > 0) {
        addSeg(buf, ordinal, segVal, thous, remain);
      }
      if (remain == 0) {
        break;
      }
      idx += step;
      step = 3;
    }
    return buf.toString();
  }

  private static void addSeg (StringBuilder buf, boolean ordinal, int val, int thous, long remain) {
    String[] digits = thous > 0 ? cDigits : ordinal ? oDigits : cDigits;
    if (val >= 100) {
      if (buf.length() > 0) {
        buf.append(" ");
      }
      buf.append(cDigits[val / 100 - 1]);
      buf.append(" hundred");
      val = val % 100;
    }
    if (val >= 20) {
      if (buf.length() > 0) {
        buf.append(" ");
      }
      String[] tens = thous > 0 ? cTens : ordinal ? val % 10 == 0 ? oTens : cTens : cTens;
      buf.append(tens[val / 10 - 2]);
      int rem = val % 10;
      if (rem != 0) {
        buf.append("-");
        buf.append(digits[rem - 1]);
      }
    } else if (val >= 10) {
      if (buf.length() > 0) {
        buf.append(" ");
      }
      String[] teens = thous > 0 ? cTeens : ordinal ? oTeens : cTeens;
      buf.append(teens[val - 10]);
    } else if (val > 0) {
      if (buf.length() > 0) {
        buf.append(" ");
      }
      buf.append(digits[val - 1]);
    }
    if (thous > 0) {
      buf.append(" ");
      buf.append(cThous[thous - 1]);
      if (ordinal && remain == 0) {
        buf.append("th");
      }
    } else {
      if (ordinal && val == 0 && remain == 0) {
        buf.append("th");
      }
    }
  }
}
