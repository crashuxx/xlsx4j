package org.xlsx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

class StreamXLSXResource implements XLSXResource {

    private static Log logger = LogFactory.getLog(StreamXLSXResource.class);

    private InputStream inputStream;
    private Map<String, File> files;

    public StreamXLSXResource(InputStream inStream) {
        inputStream = inStream;
        files = new HashMap<String, File>();
    }

    public InputStream get(String name) throws IOException {

        if (!has(name)) {
            throw new FileNotFoundException(name);
        }

        return new FileInputStream(files.get(name));
    }

    public boolean has(String name) {

        if (files.isEmpty()) {
            try {
                extract();
            } catch (IOException e) {
                logger.warn(e,e);
            }
        }

        return files.containsKey(name);
    }

    private void extract() throws IOException {

        long start = System.nanoTime();
        byte[] buffer = new byte[2048];
        ZipInputStream zipStream = new ZipInputStream(inputStream);

        try {
            ZipEntry entry;
            while ((entry = zipStream.getNextEntry()) != null) {

                long startTime = System.nanoTime();
                File file = File.createTempFile(getClass().getName(), null);
                file.deleteOnExit();

                OutputStream output = null;

                try {
                    output = new FileOutputStream(file);

                    int len = 0;
                    while ((len = zipStream.read(buffer)) > 0) {
                        output.write(buffer, 0, len);
                    }

                    files.put(entry.getName(), file);

                } finally {
                    if (output != null) {
                        output.close();
                    }
                }
                logger.debug(entry.getName() + " " + ((System.nanoTime() - startTime) / 1000000) + " ms");
            }
        } finally {
            zipStream.close();
        }

        logger.debug("All " + ((System.nanoTime() - start) / 1000000) + " ms");
    }

    @Override
    public void close() {
        for (Map.Entry<String, File> entry : files.entrySet()) {
            entry.getValue().delete();
        }
    }
}
