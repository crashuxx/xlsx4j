package org.xlsx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

class FileXLSXResource implements XLSXResource {

    private static Log logger = LogFactory.getLog(FileXLSXResource.class);

    String filename;

    public FileXLSXResource(String filename) {
        this.filename = filename;
    }

    public InputStream get(String name) throws IOException {

        InputStream inputStream = new FileInputStream(filename);

        long start = System.nanoTime();
        byte[] buffer = new byte[2048];
        ZipInputStream zipStream = new ZipInputStream(inputStream);

        try {
            ZipEntry entry;
            while ((entry = zipStream.getNextEntry()) != null) {

                if( name.equals(entry.getName()) ) {
                    return zipStream;
                }
            }
        } catch (Exception e) {
            zipStream.close();
        }

        throw new FileNotFoundException(name);
    }

    public boolean has(String name) {

        try {
            get(name);
            return true;
        } catch (IOException e) {
            logger.warn(e,e);
        }

        return false;
    }

    @Override
    public void close() {
    }
}
