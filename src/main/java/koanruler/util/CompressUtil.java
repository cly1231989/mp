package koanruler.util;

import koanruler.webservice.RmpServiceImpl;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created by user1 on 2017/12/18.
 */
public class CompressUtil {
    private final static Logger logger = LoggerFactory.getLogger(CompressUtil.class);

    public static byte[] compressData(String result) {
        long beginTime = System.currentTimeMillis();

        try {
            byte[] data = result.getBytes("UTF-8");
            final int decompressedLength = data.length;

            LZ4Compressor compressor = LZ4Factory.safeInstance().fastCompressor();
            int maxCompressedLength = compressor.maxCompressedLength(decompressedLength);
            byte[] compressed = new byte[maxCompressedLength];
            int compressedLength = compressor.compress(data, 0, decompressedLength, compressed, 0, maxCompressedLength);

            byte[] re = new byte[compressedLength];
            System.arraycopy(compressed, 0, re, 0, compressedLength);

            logger.debug("compress data time: " + (System.currentTimeMillis() - beginTime));
            return re;
        } catch (UnsupportedEncodingException e) {
            logger.debug(e.getMessage());
            return null;
        }
    }
}
