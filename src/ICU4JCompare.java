import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.util.ULocale;

import java.text.DecimalFormat;

public class ICU4JCompare {
  static final RuleBasedNumberFormat crd = new RuleBasedNumberFormat(ULocale.ENGLISH, RuleBasedNumberFormat.SPELLOUT);
  static final RuleBasedNumberFormat ord = new RuleBasedNumberFormat(ULocale.ENGLISH, RuleBasedNumberFormat.SPELLOUT);

  public static void main (String[] args) {
    crd.setDefaultRuleSet("%spellout-numbering");
    ord.setDefaultRuleSet("%spellout-ordinal");
    // Quick test
    for (long jj = 1000; jj < 1000000000L; jj *= 11) {
      for (long ii = 1; ii <= 1000; ii++) {
        long number = jj + ii;
        test(number, number % 123 == 0);
      }
    }
  }

  private static void test (double number, boolean print) {
    String crdIbm = crd.format(number);
    String ordIbm = ord.format(number);
    String crdWrh = NumberPronunciation.sayCardinal((long) number);
    String ordWrh = NumberPronunciation.sayOrdinal((long) number);
    if (!crdIbm.equals(crdWrh) || !ordIbm.equals(ordWrh)) {
      throw new IllegalStateException("bad: " + number);
    }
    if (print) {
      DecimalFormat decimalFormat = new DecimalFormat("#,###");
      System.out.println(String.format("%24s - %80s, %80s", decimalFormat.format(number), crdWrh, ordWrh));
    }
  }
}
