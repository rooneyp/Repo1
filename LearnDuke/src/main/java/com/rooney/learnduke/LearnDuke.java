package com.rooney.learnduke;

import no.priv.garshol.duke.ConfigLoader;
import no.priv.garshol.duke.Configuration;
import no.priv.garshol.duke.Processor;
import no.priv.garshol.duke.matchers.PrintMatchListener;

/**
 * Created by paul on 04/01/2016.
 */
public class LearnDuke {

    public static void main(String[] argv) throws Exception {
        String fileURL = "classpath:countries.xml";

        Configuration config = ConfigLoader.load(fileURL);
        Processor proc = new Processor(config);
        proc.addMatchListener(new PrintMatchListener(true, true, true, false,
                config.getProperties(),
                true));
//        proc.deduplicate();
        proc.link();
        proc.close();
    }
}
