package org.ssh.app.util;

/*
Milyn - Copyright (C) 2006

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License (version 2.1) as published by the Free Software
Foundation.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU Lesser General Public License for more details:
http://www.gnu.org/licenses/lgpl.txt
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * File utilities.
 *
 * @author <a href="mailto:tom.fennelly@jboss.com">tom.fennelly@jboss.com</a>
 * @author <a href="mailto:quest.run@gmail.com">pprun</a>
 */
public abstract class FileUtils {

    /**
     * Read the contents of the specified file.
     * @param file The file to read.
     * @return The file contents as byte array.
     * @throws IOException Error readiong file.
     */
    public static byte[] readFile(File file) throws IOException {


        if (!file.exists()) {
            throw new IllegalArgumentException("No such file '" + file.getAbsoluteFile() + "'.");
        } else if (file.isDirectory()) {
            throw new IllegalArgumentException("File '" + file.getAbsoluteFile() + "' is a directory.  Cannot read.");
        }

        InputStream stream = new FileInputStream(file);
        try {
            return StreamUtils.readStream(stream);
        } finally {
            stream.close();
        }
    }

    /**
     * Read the contents of the specified file.
     * @param file The file to read.
     * @return The file contents as String.
     * @throws IOException Error readiong file.
     */
    public static String readFileAsString(File file) throws IOException {


        if (!file.exists()) {
            throw new IllegalArgumentException("No such file '" + file.getAbsoluteFile() + "'.");
        } else if (file.isDirectory()) {
            throw new IllegalArgumentException("File '" + file.getAbsoluteFile() + "' is a directory.  Cannot read.");
        }

        InputStream stream = new FileInputStream(file);
        try {
            return StreamUtils.readStreamAsString(stream);
        } finally {
            stream.close();
        }
    }

    public static void writeFile(byte[] bytes, File file) throws IOException {
        if (file.isDirectory()) {
            throw new IllegalArgumentException("File '" + file.getAbsoluteFile() + "' is an existing directory.  Cannot write.");
        }

        FileOutputStream stream = new FileOutputStream(file);
        try {
            stream.write(bytes);
            stream.flush();
        } finally {
            stream.close();
        }
    }

    public static void copyFile(String from, String to) throws IOException {
        File fromFile = new File(from);
        File toFile = new File(to);

        writeFile(readFile(fromFile), toFile);
    }

    /**
     * normalize the path
     * @param path
     * @return
     */
    public static String getPath(String dir) {
        String path = dir;
        if (path != null && !path.endsWith(File.separator)) {
            path += File.separator;
        }
        return path;
    }

    /**
     * normalize the path, using the passed in separator, this is used for
     * remote path such as FTP servers file path;
     * @param path
     * @return
     */
    public static String getPath(String dir, String separator) {
        String path = dir;
        if (path != null
                && !path.endsWith(separator)
                && !path.endsWith(File.separator)) {
            path += separator;
        }
        return path;
    }

    /**
     * This method will create the structure of the directory path if it hasn't
     * existed.
     *
     * @param path the directory path to be test.
     * @return
     */
    public static File ensureDirExist(String path) {
        File dir = new File(path);
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                throw new IllegalArgumentException("Existing file with same name for the directory already exists");
            }
            // else we are fine.
        } else {
            if (!dir.mkdirs()) {
                throw new IllegalArgumentException("Cannot create directory : " + path);
            }
        }
        return dir;
    }

    /**
     * While creating a new file, it the file existing, append an increasing number to the file name.
     * @param path
     * @return the file but the actual file was not created until a actually IO wrapper, such as FileStreamOutput wrapped
     * it and write content.
     */
    public static File getNewFile(String path, String fileName, String extension) {

        if (extension != null && extension.isEmpty() == false) {
            extension = "." + extension;
        } else {
            extension = "";
        }

        File file = new File(path + File.separator + fileName + extension);
        int i = 0;
        while (file.exists()) {
            // existing? append a increasing number
            file = new File(path + File.separator + fileName + "_" + ++i + extension);
        }

        return file;
    }

    /**
     * Zip the <tt>sourceFile</tt> into <tt>toFile</tt>.
     *
     * @param sourceFile
     * @param toFile
     * @throws java.io.IOException
     */
    public static void zip(File fromFile, File toFile) throws IOException {
        zip(new FileInputStream(fromFile), toFile);
    }

    /**
     * zip the contents of an {@link InputStream}. Note that once the
     * zip file has been created, the {@link InputStream} will be closed.
     * @param is
     * @param toFile
     * @throws IOException
     */
    public static void zip(InputStream is, File toFile) throws IOException {
        zip(is, toFile, true);
    }

    /**
     * zip the contents of an {@link InputStream}. Note that once the
     * zip file has been created, the {@link InputStream} will be closed.
     * @param is
     * @param toFile
     * @throws IOException
     */
    public static void zip(InputStream is, File toFile, boolean closeInputStream) throws IOException {
        final int BUFFER_SIZE = 2048;
        BufferedInputStream in = new BufferedInputStream(is, BUFFER_SIZE);
        try {
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(toFile)));
            try {
                // just the name instead of full path + name
                ZipEntry entry = new ZipEntry(toFile.getName());
                out.putNextEntry(entry);
                byte data[] = new byte[BUFFER_SIZE];
                int count;
                while ((count = in.read(data, 0, BUFFER_SIZE)) != -1) {
                    out.write(data, 0, count);
                }
            } finally {
                out.close();
            }
        } finally {
            if (closeInputStream) {
                in.close();
            }
        }
    }

    /**
     * Copy file from one to another
     * @param in
     * @param out
     * @throws Exception
     */
    public static void copyFile(File in, File out) throws IOException {
        FileChannel sourceChannel = new FileInputStream(in).getChannel();
        try {
            FileChannel destinationChannel = new FileOutputStream(out).getChannel();
            try {
                sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
            } finally {
                destinationChannel.close();
            }
        } finally {
            sourceChannel.close();
        }
    }
}

/**
 * Stream Utilities.
 *
 * @author tfennelly
 */
abstract class StreamUtils {

    /**
     * Read the supplied InputStream and return as a byte array.
     *
     * @param stream
     *            The stream to read.
     * @return byte array containing the Stream data.
     * @throws IOException
     *             Exception reading from the stream.
     */
    public static byte[] readStream(InputStream stream) throws IOException {


        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        byte[] byteBuf = new byte[1024];
        int readCount = 0;

        while ((readCount = stream.read(byteBuf)) != -1) {
            bytesOut.write(byteBuf, 0, readCount);
        }

        return bytesOut.toByteArray();
    }

    /**
     * Read the supplied InputStream and return as a byte array.
     *
     * @param stream
     *            The stream to read.
     * @return A String containing the Stream data.
     * @throws IOException
     *             Exception reading from the stream.
     */
    public static String readStreamAsString(InputStream stream) throws IOException {


        return new String(readStream(stream), Charset.forName("UTF-8"));
    }

    public static byte[] readFile(File file) throws IOException {

        InputStream stream = new FileInputStream(file);
        try {
            return readStream(stream);
        } finally {
            stream.close();
        }
    }

    public static void writeFile(File file, byte[] data) throws IOException {

        OutputStream stream = new FileOutputStream(file);
        try {
            stream.write(data);
        } finally {
            try {
                stream.flush();
            } finally {
                stream.close();
            }
        }
    }

    public static String readStream(Reader stream) throws IOException {

        StringBuilder streamString = new StringBuilder();
        char[] readBuffer = new char[256];
        int readCount = 0;

        while ((readCount = stream.read(readBuffer)) != -1) {
            streamString.append(readBuffer, 0, readCount);
        }

        return streamString.toString();
    }

    /**
     * Compares the 2 streams.
     * <p/>
     * Calls {@link #trimLines(InputStream)} on each stream before comparing.
     * @param s1 Stream 1.
     * @param s2 Stream 2.
     * @return True if the streams are equal not including leading and trailing
     * whitespace on each line and blank lines, otherwise returns false.
     */
    public static boolean compareCharStreams(InputStream s1, InputStream s2) {
        StringBuffer s1Buf, s2Buf;

        try {
            s1Buf = trimLines(s1);
            s2Buf = trimLines(s2);

            return s1Buf.toString().equals(s2Buf.toString());
        } catch (IOException e) {
            // fail the comparison
        }

        return false;
    }

    /**
     * Compares the 2 streams.
     * <p/>
     * Calls {@link #trimLines(java.io.Reader)} on each stream before comparing.
     * @param s1 Stream 1.
     * @param s2 Stream 2.
     * @return True if the streams are equal not including leading and trailing
     * whitespace on each line and blank lines, otherwise returns false.
     */
    public static boolean compareCharStreams(Reader s1, Reader s2) {
        StringBuffer s1Buf, s2Buf;

        try {
            s1Buf = trimLines(s1);
            s2Buf = trimLines(s2);

            return s1Buf.toString().equals(s2Buf.toString());
        } catch (IOException e) {
            // fail the comparison
        }

        return false;
    }

    /**
     * Compares the 2 streams.
     * <p/>
     * Calls {@link #trimLines(java.io.Reader)} on each stream before comparing.
     * @param s1 Stream 1.
     * @param s2 Stream 2.
     * @return True if the streams are equal not including leading and trailing
     * whitespace on each line and blank lines, otherwise returns false.
     */
    public static boolean compareCharStreams(String s1, String s2) {
        return compareCharStreams(new StringReader(s1), new StringReader(s2));
    }

    /**
     * Read the lines lines of characters from the stream and trim each line
     * i.e. remove all leading and trailing whitespace.
     * @param charStream Character stream.
     * @return StringBuffer containing the line trimmed stream.
     * @throws IOException
     */
    public static StringBuffer trimLines(Reader charStream) throws IOException {
        StringBuffer stringBuf = new StringBuffer();
        BufferedReader reader = new BufferedReader(charStream);
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuf.append(line.trim());
        }

        return stringBuf;
    }

    /**
     * Read the lines lines of characters from the stream and trim each line
     * i.e. remove all leading and trailing whitespace.
     * @param charStream Character stream.
     * @return StringBuffer containing the line trimmed stream.
     * @throws IOException
     */
    public static StringBuffer trimLines(InputStream charStream) throws IOException {
        return trimLines(new InputStreamReader(charStream, "UTF-8"));
    }
}
