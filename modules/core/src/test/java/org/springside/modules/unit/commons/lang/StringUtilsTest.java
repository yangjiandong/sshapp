package org.springside.modules.unit.commons.lang;

import static org.junit.Assert.*;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.junit.Test;

//commons lang StringUtils
public class StringUtilsTest {

    @Test
    public void testBlank() {
        assertTrue(StringUtils.isBlank(""));
        assertTrue(StringUtils.isBlank("\n\n\t"));
        assertTrue(StringUtils.isBlank(null));
        assertTrue(!StringUtils.isBlank("Test"));

        assertEquals(null, StringUtils.trimToNull(null));
        assertEquals(null, StringUtils.trimToNull(""));
        assertEquals(null, StringUtils.trimToNull("\t"));

        assertTrue(!StringUtils.isNotBlank(null));
    }

    //字符串缩写
    @Test
    public void testAbbreviate() {

        assertEquals("test...", StringUtils.abbreviate("testsccome", 7));
        assertEquals("中午吃饭...", StringUtils.abbreviate("中午吃饭返丑么才额是哦", 7));

    }

    @Test
    public void testSplit() {
        String input = "Franticall oblong";
        // "," " ," 有区别
        //String[] array1 = StringUtils.split(input,",", 2);
        String[] array1 = StringUtils.split(input, " ,", 2);
        assertEquals("Franticall", array1[0]);
        assertEquals("oblong", array1[1]);

        System.out.println(ArrayUtils.toString(array1));
    }

    @Test
    public void testNestedStrings() {
        String htmlContent = "<html>\n" +

        "  <head>\n" +

        "    <title>Test Page</title>\n" +

        "  </head>\n" +

        "  <body>\n" +

        "    <p>This is a TEST!</p>\n" +

        "  </body>\n" +

        "</html>";

        //Extract the title from this XHTML content

        String title = StringUtils.substringBetween(htmlContent, "<title>",

        "</title>");

        assertEquals("Test Page", title);

        //
        //        StringUtils.substringBetween( )
        //        Captures content between two strings
        //
        //        StringUtils.substringAfter( )
        //        Captures content that occurs after the specified string
        //
        //        StringUtils.substringBefore( )
        //        Captures content that occurs before a specified string
        //
        //        StringUtils.substringBeforeLast( )
        //        Captures content after the last occurrence of a specified string
        //
        //        StringUtils.substringAfterLast( )
        //        Captures content before the last occurrence of a specified string

    }

    //剔除指定字符
    @Test
    public void testStrip() {
        String original = "-------***---SHAZAM!---***-------";
        String stripped = StringUtils.strip(original, "-*");

        assertEquals("SHAZAM!", stripped);

    }

    //制定加强头信息
    @Test
    public void emphasizedHeader() {
        String title = "Test";
        int width = 30;
        // Construct heading using StringUtils: repeat( ), center( ), and join( )

        String stars = StringUtils.repeat("*", width);
        String centered = StringUtils.center(title, width, "*");
        String heading = StringUtils.join(new Object[] { stars, centered, stars }, "\n");

        System.out.println(heading);

    }

    //换行
    @Test
    public void testWrapp() {
        String message = "One Two Three Four Five ccccccccccccccccccccccccccccccccccccccccccc";
        // Wrap the text.
        String wrappedString = WordUtils.wrap(message, 20, "\n", false);
        System.out.println("Wrapped Message:\n\n" + wrappedString);
    }

    @Test
    public void testReplaceString() {
        String name1 = "Tim O'Reilly";
        String name2 = "Mr. Mason-Dixon!";

        String punctuation = ".-'";
        String name1Temp = StringUtils.replaceChars(name1, punctuation, "");
        String name2Temp = StringUtils.replaceChars(name2, punctuation, "");

        assertEquals("Tim OReilly", name1Temp);
        assertEquals("Mr MasonDixon!", name2Temp);
    }

    //Commons Codec
    //The Commons Codec library is a small library, which includes encoders and decoders for common encoding algorithms, such as Hex, Base64; and phonetic encoders, such as Metaphone, DoubleMetaphone, and Soundex. This tiny component was created to provide a definitive implementation of Base64 and Hex, encouraging reuse and reducing the amount of code duplication between various Apache projects.

}
