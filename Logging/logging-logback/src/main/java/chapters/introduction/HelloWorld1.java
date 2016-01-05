package chapters.introduction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld1 {

  public static void main(String[] args) {

    Logger logger = LoggerFactory.getLogger("chapters.introduction.HelloWorld1");
    logger.debug("Hello world.");

    
    System.out.println("START");
	try {
		throw new RuntimeException("hi", new RuntimeException("cause"));
//		throw new RuntimeException("hi");
	}		catch (RuntimeException e) {
		{
			logger.error("my err msg ", e);
			
		}
	}
	System.out.println("END");
    
  }
}

