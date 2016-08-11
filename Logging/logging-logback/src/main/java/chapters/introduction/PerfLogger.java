package chapters.introduction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerfLogger {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger("PERF_LOGGER");
        for(int i=1; i<1000; i++) {
            logger.debug("Hello perfworld.");
        }
    }
}
