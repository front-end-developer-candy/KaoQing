import com.maplesoft.enok.kaoqing.model.Clock;
import junit.framework.Assert;

public class Test {

    @org.junit.Test
    public void testClockDiff() {
        String time1 = "09:52";
        String time2 = "19:24";
        Assert.assertEquals("09:32", Clock.diff(time2, time1).toString());
    }

}
