package chapters.introduction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class DemoMDC {
    static Logger logger = LoggerFactory.getLogger(DemoMDC.class);
    static public void main(String[] args) throws Exception {
        int numMdc = 20;
        int numLogPerMdc = 20;
        
        if(args.length > 0) {
            numMdc = Integer.valueOf(args[0]);
        }
        
        if(args.length > 1) {
            numLogPerMdc = Integer.valueOf(args[1]);
        }
        
        for(int i=0; i<numMdc; i++) {
            logWithMdc("myCorrId" + i, "myBizFnt" + i, "myJobId" + i, numLogPerMdc);
        }
    }

    private static void logWithMdc(String cid, String bid, String jobId, int runCount) {
        MDC.put("correlationId", cid);
        MDC.put("Business-Function", bid);
        MDC.put("jobId", jobId);
        
        for(int i=0; i<runCount; i++) {
            if(i % 5 == 0) {
                MDC.put("Business-Function", bid + i);
                MDC.put("jobId", jobId + i);
            }
            
            log(i);
        }
    }

    private static void log(int i) {
        logger.info("Check enclosed. " + i);
        logger.debug("The most beautiful two words in English. " + i);
        logger.error("oops", new Exception("An Exception " + i));
    }
    
}
