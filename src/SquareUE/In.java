package SquareUE; /**
 * In.java
 * Institute for Pervasive Computing
 * Department of Cooperative Information Systems
 * Johannes Kepler University Linz, Austria
 * http://www.pervasive.jku.at
 *
 * Copyright (c) 2009 Michael Matscheko
 * Copyright (c) 2014,2017 Wieland Schwinger
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * last change: 2017-09-20 Wieland Schwinger
 */



import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * Input is a utility class, providing easy to use methods to read any primitive
 * data type from the default system input, without having to deal with enhanced
 * java concepts like exceptions, streams or error handling.
 *
 * <p>
 * Example: <blockquote>
 *
 * <pre>
 * System.out.print(&quot;Enter number a: &quot;);
 * double a = Input.readDouble();
 * System.out.print(&quot;Enter number b: &quot;);
 * double b = Input.readDouble();
 * System.out.println(&quot;a + b = &quot; + (a + b));
 * </pre>
 *
 * </blockquote>
 *
 * <p>
 * There are read-Methods for the following data types: <tt>char</tt>,
 * <tt>boolean</tt>, <tt>byte</tt>, <tt>short</tt>, <tt>int</tt>,
 * <tt>long</tt>, <tt>float</tt>, <tt>double</tt>, <tt>String</tt>
 *
 * <p>
 * Values are expected to be on separate lines: You have to press enter after
 * each value, therefore moving to the next line. If you have to read multiple
 * values from a single line, use {@link #readString()} to read a full line as
 * text, split it into several parts and convert each part into whatever data
 * type you need. Of course this is not recommended for programming beginners.
 *
 * <p>
 * There is one additional method to read several characters from a single line:
 * {@link #readCharSequence}. This method will return character after character
 * from one line. When the end of the line is reached, a line feed sequence is
 * returned (this is operating system dependent, see {@link #readCharSequence()}
 * for examples), then readCharSequence will continue with the next line.
 *
 * <p>
 * All methods will automatically handle errors:
 * <ul>
 * <li>If an invalid value is entered, the user is told to enter a new, correct
 * value. (e.g. if an integer is expected and letters are entered, a message
 * like "Not a valid int, please try again" will be displayed, and the user is
 * forced to enter a new value) </li>
 * <li>If an unresolvable error is encountered (e.g. the system input stream
 * has been closed), the application is immediately terminated.</li>
 * </ul>
 *
 * @version 1.0, 2010-01-12
 */
public class In {

	// the System Input Stream
	private static final InputStream in = System.in;

	// the full line feed (newline) character sequence (e.g. \n, or \r\n on
	// windows systems)
	public static final String FULL_LF;

	// the last character of the line feed sequence. we will only check for
	// that to detect line breaks. of course this behaviour is incorrect, but
	// works well with any system known to me, and is a lot easier and faster
	public static final int LF;

	// the last character of a "raw" read 
	public static final char LF_CHAR = '\n';

	// line buffer
	private static StringBuilder line = new StringBuilder(1024);

	// current offset in line buffer
	private static int lineOffset = 0;

	// error messages
	private final static String ERROR_MSG_CHAR 		= "Not a valid char, please try again: ";
	private final static String ERROR_MSG_BOOLEAN 	= "Not a valid boolean, please try again: ";
	private final static String ERROR_MSG_BYTE 		= "Not a valid byte, please try again: ";
	private final static String ERROR_MSG_SHORT		= "Not a valid short, please try again: ";
	private final static String ERROR_MSG_INT		= "Not a valid int, please try again: ";
	private final static String ERROR_MSG_LONG		= "Not a valid long, please try again: ";
	private final static String ERROR_MSG_FLOAT		= "Not a valid float, please try again: ";
	private final static String ERROR_MSG_DOUBLE	= "Not a valid double, please try again: ";

	// init
	static {
		FULL_LF = System.getProperty("line.separator", "\n");
		LF = FULL_LF.charAt(FULL_LF.length() - 1);
	}

	// hide default constructor
	private In() {
	}

	// read next line into the line buffer, including any line feed chars
	private static void openLine() {
		closeLine(); // make sure the buffer is empty
		try {
			int c;
			do {
				c = in.read();
				if (c == -1) throw new IOException("Input stream closed.");
				line.append((char) c);
			} while (c != LF);
		} catch (IOException e) {
			// fatal error, terminate application
			System.out.println();
			System.out.println("Fatal Error: " + e.getMessage());
			System.exit(0);
		}
	}

	// clear/reset line buffer
	private static void closeLine() {
		line.setLength(0);
		lineOffset = 0;
	}

	// read and return next line, excluding any line feed chars
	private static String nextLine() {
		openLine();
		String s = line.toString();
		if (s.endsWith(FULL_LF)) s = s.substring(0, s.length() - FULL_LF.length());
		closeLine();
		return s;
	}

	/**
	 * Read the next character from a character sequence.
	 *
	 * <p>
	 * This method will return the character received from the system. The 
	 * line feed characters however are modified to always return '\n' 
	 * als line feed characters independently of your systems setting. 
	 *  Be aware that entering
	 * "ab" and pressing enter will produce a character sequence of <tt>'a'</tt>,
	 * <tt>'b'</tt> and finally '\n', independent on your
	 * operating system.
	 *
	 * <p>
	 * Line feed sequences:
	 * <ul>
	 * <li>Any Windows: <tt>'\r'</tt> followed by <tt>'\n'</tt></li>
	 * <li>Any unix based system (e.g. Linux): <tt>'\n'</tt></li>
	 * <li>MacOS prior to MacOS X (MacOS X is unix-based): <tt>'\r'</tt></li>
	 * <li>There might be others, but it is highly unlikely you will encounter
	 * any.</li>
	 * </ul>
	 *
	 * <p>
	 * Java provides the local line feed sequence as a String in the system
	 * property <tt>line.separator</tt>. To retrieve this property, use the
	 * method <tt>System.getProperty("line.separator")</tt>.
	 *
	 * <p>
	 * Calling any other read method than readCharSequence will drop the
	 * remaining characters of the sequence.
	 *
	 * <p>
	 * The following example will read 2 characters from a character sequence,
	 * ignore any remaining characters on the line, and then read a floating
	 * point number: <blockquote>
	 *
	 * <pre>
	 * System.out.print(&quot;Enter at least two characters: &quot;);
	 * char c1 = Input.readCharSequence();
	 * char c2 = Input.readCharSequence();
	 * System.out.print(&quot;Enter a number: &quot;);
	 * double x = Input.readDouble();
	 * </pre>
	 *
	 * </blockquote>
	 *
	 * <p>
	 * Note: readCharSequence will wait until a full line has been entered,
	 * before returning the first character of the sequence. This is actually a
	 * limitation by Java, since the user is allowed to edit the line: the
	 * sequence may change until the enter key has been pressed.
	 *
	 * @return A <tt>char</tt> value.
	 */
	public static char read() {

		if (line.length() == 0) {
			openLine();
		}
		char c = line.charAt(lineOffset++);
		if (lineOffset > line.length()-FULL_LF.length()+1) {
			closeLine();
			return LF_CHAR;
		}
		return c;
	}


	/**
	 *
	 * @deprecated
	 *
	 * Read the next character from a character sequence.
	 *
	 * <p>
	 * This method will return ANY character received from the system without
	 * modifications, including line feed characters. Be aware that entering
	 * "ab" and pressing enter will produce a character sequence of <tt>'a'</tt>,
	 * <tt>'b'</tt> and a line feed sequence, which is dependent on your
	 * operating system.
	 *
	 * <p>
	 * Line feed sequences:
	 * <ul>
	 * <li>Any Windows: <tt>'\r'</tt> followed by <tt>'\n'</tt></li>
	 * <li>Any unix based system (e.g. Linux): <tt>'\n'</tt></li>
	 * <li>MacOS prior to MacOS X (MacOS X is unix-based): <tt>'\r'</tt></li>
	 * <li>There might be others, but it is highly unlikely you will encounter
	 * any.</li>
	 * </ul>
	 *
	 * <p>
	 * Java provides the local line feed sequence as a String in the system
	 * property <tt>line.separator</tt>. To retrieve this property, use the
	 * method <tt>System.getProperty("line.separator")</tt>.
	 *
	 * <p>
	 * Calling any other read method than readCharSequence will drop the
	 * remaining characters of the sequence.
	 *
	 * <p>
	 * The following example will read 2 characters from a character sequence,
	 * ignore any remaining characters on the line, and then read a floating
	 * point number: <blockquote>
	 *
	 * <pre>
	 * System.out.print(&quot;Enter at least two characters: &quot;);
	 * char c1 = Input.readCharSequence();
	 * char c2 = Input.readCharSequence();
	 * System.out.print(&quot;Enter a number: &quot;);
	 * double x = Input.readDouble();
	 * </pre>
	 *
	 * </blockquote>
	 *
	 * <p>
	 * Note: readCharSequence will wait until a full line has been entered,
	 * before returning the first character of the sequence. This is actually a
	 * limitation by Java, since the user is allowed to edit the line: the
	 * sequence may change until the enter key has been pressed.
	 *
	 * @return A <tt>char</tt> value.
	 */
	@Deprecated
	private static char readCharSequence() {
		if (line.length() == 0) openLine();
		char c = line.charAt(lineOffset++);
		if (lineOffset >= line.length()) closeLine();
		return c;
	}

	/**
	 * Read a single character.
	 *
	 * <p>
	 * Like all the other read-methods, readChar expects a SINGLE value
	 * (character) in one line. If you want to read several consecutive
	 * characters from a line, use the method {@link #readCharSequence}.
	 *
	 * @return A <tt>char</tt> value.
	 */
	public static char readChar() {
		char c = '\0';
		boolean valid = false;
		do {
			String s = nextLine();
			//Out.print("\n lenght of c " + s.length());
			if (s.length() == 1) {
				c = s.charAt(0);
				valid = true;
			} else {
				System.out.print(ERROR_MSG_CHAR);
			}
		} while (!valid);
		return c;
	}

	/**
	 * Read a boolean value.
	 *
	 * <p>
	 * The expressions "true", "on", "yes" and "1" mean <tt>true</tt>,
	 * "false", "off", "no" and "0" mean <tt>false</tt>. Evaluation is case
	 * insensitive, so entering "TRUE" or "True" is also considered to be a
	 * correct boolean value.
	 *
	 * @return A <tt>boolean</tt> value.
	 */
	public static boolean readBoolean() {
		boolean b = false;
		boolean valid = false;
		do {
			String s = nextLine();
			if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("on") || s.equalsIgnoreCase("yes")
					|| s.equals("1")) {
				b = true;
				valid = true;
			} else if (s.equalsIgnoreCase("false") || s.equalsIgnoreCase("off")
					|| s.equalsIgnoreCase("no") || s.equals("0")) {
				b = false;
				valid = true;
			} else {
				System.out.print(ERROR_MSG_BOOLEAN);
			}
		} while (!valid);
		return b;
	}

	/**
	 * Read an 8 Bit integer number.
	 *
	 * <p>
	 * Valid numbers have to be within [-128, 127].
	 *
	 * @return A <tt>byte</tt> value.
	 */
	public static byte readByte() {
		byte n = 0;
		boolean valid = false;
		do {
			try {
				n = Byte.parseByte(nextLine());
				valid = true;
			} catch (NumberFormatException e) {
				System.out.print(ERROR_MSG_BYTE);
			}
		} while (!valid);
		return n;
	}

	/**
	 * Read a 16 Bit integer number.
	 *
	 * <p>
	 * Valid numbers have to be within [-32768, 32767].
	 *
	 * @return A <tt>short</tt> value.
	 */
	public static short readShort() {
		short n = 0;
		boolean valid = false;
		do {
			try {
				n = Short.parseShort(nextLine());
				valid = true;
			} catch (NumberFormatException e) {
				System.out.print(ERROR_MSG_SHORT);
			}
		} while (!valid);
		return n;
	}

	/**
	 * Read a 32 Bit integer number.
	 *
	 * <p>
	 * Valid numbers have to be within [-2147483648, 2147483647].
	 *
	 * @return An <tt>int</tt> value.
	 */
	public static int readInt() {
		int n = 0;
		boolean valid = false;
		do {
			try {
				String s = nextLine();
				if(s.contains("\n")) {
					char[] dummy = s.toCharArray();
					char[] goodInt = new char[dummy.length-1];
					int index = dummy.length - 1;
					System.arraycopy(dummy,0,goodInt,0,index);
					s = new String(goodInt);
				}
				n = Integer.parseInt(s);
				valid = true;
			} catch (NumberFormatException e) {
				System.out.print(ERROR_MSG_INT);
			}
		} while (!valid);
		return n;
	}

	/**
	 * Read a 64 Bit integer number.
	 *
	 * <p>
	 * Valid numbers have to be within [-9223372036854775808,
	 * 9223372036854775807].
	 *
	 * @return A <tt>long</tt> value.
	 */
	public static long readLong() {
		long n = 0;
		boolean valid = false;
		do {
			try {
				String s = nextLine();
				if(s.contains("\n")) {
					char[] dummy = s.toCharArray();
					char[] goodLong = new char[dummy.length-1];
					int index = dummy.length - 1;
					System.arraycopy(dummy,0,goodLong,0,index);
					s = new String(goodLong);
				}
				n = Long.parseLong(s);
				valid = true;
			} catch (NumberFormatException e) {
				System.out.print(ERROR_MSG_LONG);
			}
		} while (!valid);
		return n;
	}

	/**
	 * Read a 32 Bit floating point number.
	 *
	 * <p>
	 * Floating point numbers have to be in the english/international format,
	 * using a dot as separator (e.g. 123.456 or 1.2E8).
	 *
	 * @return A <tt>float</tt> value.
	 */
	public static float readFloat() {
		float n = 0;
		boolean valid = false;
		do {
			try {
				String s = nextLine();
				if(s.contains("\n")) {
					char[] dummy = s.toCharArray();
					char[] goodFloat = new char[dummy.length-1];
					int index = dummy.length - 1;
					System.arraycopy(dummy,0,goodFloat,0,index);
					s = new String(goodFloat);
				}
				n = Float.parseFloat(s);
				valid = true;
			} catch (NumberFormatException e) {
				System.out.print(ERROR_MSG_FLOAT);
			}
		} while (!valid);
		return n;
	}

	/**
	 * Read a 64 Bit floating point number.
	 *
	 * <p>
	 * Floating point numbers have to be in the english/international format,
	 * using a dot as separator (e.g. 123.456 or 1.2E8).
	 *
	 * @return A <tt>double</tt> value.
	 */
	public static double readDouble() {
		double n = 0;
		boolean valid = false;
		do {
			try {
				String s = nextLine();
				if(s.contains("\n")) {
					char[] dummy = s.toCharArray();
					char[] goodDouble = new char[dummy.length-1];
					int index = dummy.length - 1;
					System.arraycopy(dummy,0,goodDouble,0,index);
					s = new String(goodDouble);
				}
				n = Double.parseDouble(s);
				valid = true;
			} catch (NumberFormatException e) {
				System.out.print(ERROR_MSG_DOUBLE);
			}
		} while (!valid);
		return n;
	}

	/**
	 * Read a single text line.
	 *
	 * <p>
	 * A single line, excluding any line feed characters, is returned. If an
	 * empty line is entered, an empty String (<tt>""</tt>) is returned.
	 * This method will never return <tt>null</tt>.
	 *
	 * <p>
	 * There is no method to read multiple text lines at once. Call readString
	 * repeatedly to do that.
	 *
	 * @return A <tt>String</tt> value.
	 */
	public static String readString() {
		String line = nextLine();
		String retStr = line;
		if(line.contains("\n")) {
			char[] strArr = line.toCharArray();
			char[] strArr2 = Arrays.copyOf(strArr, strArr.length - 1);
			retStr = new String(strArr2);
		}
		return retStr;
	}


}
