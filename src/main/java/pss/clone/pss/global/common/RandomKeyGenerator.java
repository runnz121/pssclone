package pss.clone.pss.global.common;

import java.security.SecureRandom;

public class RandomKeyGenerator{

    public static synchronized String nextSeqNumberKey() {

        long time = 0;
        time = System.currentTimeMillis();

        String temp = Long.toString(time);
        for ( int i=temp.length(); i< 20; i++ )
            temp += new SecureRandom().nextInt(10);

        return temp;
    }
}