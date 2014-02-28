package org.xlsx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

interface XLSXResource extends Closeable {

    public InputStream get(String name) throws IOException;
    public boolean has(String name) throws IOException;
}
