package commons.utils;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import java.math.BigInteger;
import java.util.List;

/**
 * @author guorui1
 */
public class SimHash {

    public static final int HASH_LEN = 64;

    private final BigInteger hash;

    private SimHash(BigInteger hash) {
        this.hash = hash;
    }

    /**
     * 给出一段文本，计算其SimHash
     */
    public static SimHash simhash(String text) {
        int[] v = new int[HASH_LEN];
        List<Term> termList = HanLP.segment(text);
        for (Term term : termList) {
            BigInteger t = hash(term.word);
            for (int i = 0; i < HASH_LEN; i++) {
                BigInteger bitmask = new BigInteger("1").shiftLeft(i);
                if (t.and(bitmask).signum() != 0) {
                    v[i] += 1;
                } else {
                    v[i] -= 1;
                }
            }
        }
        BigInteger hash = new BigInteger("0");
        for (int i = 0; i < HASH_LEN; i++) {
            if (v[i] >= 0) {
                hash = hash.add(new BigInteger("1").shiftLeft(i));
            }
        }
        return new SimHash(hash);
    }

    private static BigInteger hash(String text) {
        if (text == null || text.isEmpty()) {
            return new BigInteger("0");
        }
        char[] sourceArray = text.toCharArray();
        BigInteger x = BigInteger.valueOf(((long) sourceArray[0]) << 7);
        BigInteger m = new BigInteger("1000003");
        BigInteger mask = new BigInteger("2").pow(HASH_LEN).subtract(new BigInteger("1"));
        for (char item : sourceArray) {
            BigInteger temp = BigInteger.valueOf(item);
            x = x.multiply(m).xor(temp).and(mask);
        }
        x = x.xor(new BigInteger(String.valueOf(text.length())));
        if (x.equals(new BigInteger("-1"))) {
            x = new BigInteger("-2");
        }
        return x;
    }

    public int distance(SimHash o) {
        return distance(this.hash, o.hash);
    }

    public static int distance(String s1, String s2) {
        return SimHash.simhash(s1).distance(SimHash.simhash(s2));
    }

    /**
     * 已知两个指纹串，计算距离
     */
    public static int distance(BigInteger h1, BigInteger h2) {
        BigInteger x = h1.xor(h2);
        int tot = 0;
        while (x.signum() != 0) {
            tot += 1;
            x = x.and(x.subtract(new BigInteger("1")));
        }
        return tot;
    }

    @Override
    public String toString() {
        return hash.toString(2);
    }
}
