package nutmeg.core;

import java.io.*;

public class IO {
	public static String loadString(String filePath) {
		try {
			return loadString(new FileInputStream(filePath), filePath);
		} catch(IOException ioex) {
			System.err.println(ioex.getMessage());
			return null;
		}
	}
	
	public static String[] loadStrings(String filePath) {
		try {
			return loadString(new FileInputStream(filePath), filePath).split("\n");
		} catch(IOException ioex) {
			System.err.println(ioex.getMessage());
			return null;
		}
	}
	
	public static String loadString(InputStream stream, String _sFileName) throws IOException {
		if(stream == null) Logger.Throw("NMC", "IO",  "Unable To Use Stream, Stream is Null!",new Exception());
		long tp1, tp2;
		tp1 = System.currentTimeMillis();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line;
		StringBuilder buffer = new StringBuilder();
		while((line = reader.readLine()) != null) {
			buffer.append(line + "\n");
		}
		reader.close();
		tp2 = System.currentTimeMillis();
		Logger.Log("NMC", "IO", "File '"+_sFileName+"' Loaded in "+(tp2 - tp1)+"ms");
		return buffer.toString();
	}	
}
