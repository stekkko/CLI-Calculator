import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestConsoleCalculator extends TestCase {


    @Test
    public void testRemoveAllInvalidSymbols() {
        Assert.assertEquals("", Processor.removeAllInvalidSymbols(null, "0123456789()+-*/^"));
        Assert.assertEquals("", Processor.removeAllInvalidSymbols(null, null));
        Assert.assertEquals("", Processor.removeAllInvalidSymbols("asdasd", null));
        Assert.assertEquals("1+2", Processor.removeAllInvalidSymbols("as 1 s  df+df 2a", "0123456789()+-*/^"));
        Assert.assertEquals("(1+2)^2", Processor.removeAllInvalidSymbols("as (1 s  df+df 2a)adsf^2", "0123456789()+-*/^"));
    }

    @Test
    public void testBuildExpression() throws WrongArithmeticException {

        List<Object> expected;

        expected = new ArrayList<>();
        Collections.addAll(expected, 3.0, 4.0, '+');
        Assert.assertEquals(expected, Processor.buildExpression("3+4"));

        expected = new ArrayList<>();
        Collections.addAll(expected, 3.0, 4.0, '-');
        Assert.assertEquals(expected, Processor.buildExpression("3-4"));

        expected = new ArrayList<>();
        Collections.addAll(expected, 1.0, 2.0, '+', 4.0, '*', 3.0, '+');
        Assert.assertEquals(expected, Processor.buildExpression("(1+2)*4+3"));

        expected = new ArrayList<>();
        Collections.addAll(expected, 3.0, 4.0, 2.0, '*', 1.0, 5.0, '+', 2.0, '^', '/', '-');
        Assert.assertEquals(expected, Processor.buildExpression("3-4*2/(1+5)^2"));

        expected = new ArrayList<>();
        Collections.addAll(expected, 1.0, 2.0, 4.0, '*', '+', 9.0, 9.0, '*', 0.5, '^', '-', 1000.0, 2.0, '*', '+');
        Assert.assertEquals(expected, Processor.buildExpression("1+2*4-(9*9)^0.5+1000*2"));

    }

    @Test
    public void testEval() throws WrongArithmeticException{
        List<Object> rpn;
        rpn = new ArrayList<>();
        Collections.addAll(rpn, 3.0, 4.0, '-');
        Assert.assertEquals(-1, Processor.eval(rpn), 0.0001f);

        rpn = new ArrayList<>();
        Collections.addAll(rpn, 3.0, 4.0, '+');
        Assert.assertEquals(7, Processor.eval(rpn), 0.0001f);

        rpn = new ArrayList<>();
        Collections.addAll(rpn, 3.0, 4.0, '^');
        Assert.assertEquals(81, Processor.eval(rpn), 0.0001f);

        rpn = new ArrayList<>();
        Collections.addAll(rpn, 3.0, 4.0, '*');
        Assert.assertEquals(12, Processor.eval(rpn), 0.0001f);

        rpn = new ArrayList<>();
        Collections.addAll(rpn, 3.0, 4.0, '/');
        Assert.assertEquals(3 / 4f, Processor.eval(rpn), 0.0001f);

        rpn = new ArrayList<>();
        Collections.addAll(rpn,  1.0, 2.0, '+', 4.0, '*', 3.0, '+');
        Assert.assertEquals(15, Processor.eval(rpn), 0.0001f);

        rpn = new ArrayList<>();
        Collections.addAll(rpn,  3.0, 4.0, 2.0, '*', 1.0, 5.0, '+', 2.0, '^', '/', '-');
        Assert.assertEquals(3 - 8f/36f, Processor.eval(rpn), 0.0001f);

        rpn = new ArrayList<>();
        Collections.addAll(rpn,  1.0, 2.0, 4.0, '*', '+', 9.0, 9.0, '*', 0.5, '^', '-', 1000.0, 2.0, '*', '+');
        Assert.assertEquals(2000, Processor.eval(rpn), 0.0001f);
    }

    @Test(expected = WrongArithmeticException.class)
    public void testExceptionBuildExpression() throws WrongArithmeticException {
        try {
            Processor.buildExpression("1 + 2 a");
        } catch (Exception e) {
            Assert.assertEquals(e.getClass(), WrongArithmeticException.class);
        }
    }
}
