package chapters.introduction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class DemoMDC {
    static Logger logger = LoggerFactory.getLogger(DemoMDC.class);
    static public void main(String[] args) throws Exception {
        int numMdc = 1;
        int numLogPerMdc = 1;
        
        if(args.length > 0) {
            numMdc = Integer.valueOf(args[0]);
        }
        
        if(args.length > 1) {
            numLogPerMdc = Integer.valueOf(args[1]);
        }
        
        
        for(int i=0; i<numMdc; i++) {
            logWithMdc("myCid" + i, "myBid" + i, numLogPerMdc);
        }
    }

    private static void logWithMdc(String cid, String bid, int runCount) {
        MDC.put("Correlation-ID", cid);
        MDC.put("Business-Function", bid);
        
        log(runCount);
    }

    private static void log(int runCount) {
        for(int i=0; i<runCount; i++) {
            logger.info("Check enclosed." + i);
            logger.debug("The most beautiful two words in English." + i);
            logger.error("oops", new Exception("An Exception"));
        }
    }
    
}
