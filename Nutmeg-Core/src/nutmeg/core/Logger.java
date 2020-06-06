package nutmeg.core;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Logger {
	private static PrintStream out = System.out;
	private static PrintStream err = System.err;
	public static void Log(String _sLib, String _sClass, String _sMsg) {
		out.println("["+_sLib+"/"+_sClass+"]: "+_sMsg);
	}
	
	public static void Warn(String _sLib, String _sClass, String _sMsg) {
		err.println("["+_sLib+"/"+_sClass+"]: "+_sMsg);
	}
	
	public static void Assert(String _sLib, String _sClass, String _sMsg, boolean _bCondition) {
		if(!_bCondition) { err.println("["+_sLib+"/"+_sClass+"]: "+_sMsg); Nutmeg.bClose = true; }
	}
	
	public static void Throw(String _sLib, String _sClass, String _sMsg, Exception _exException) {
		err.println("["+_sLib+"/"+_sClass+"]: "+_sMsg); 
		err.println("===================================");
		err.println(_exException.getMessage());
		err.println("===================================");
		System.exit(0x10);
	}
	
	public static void RedirOutFile(String filePath) throws FileNotFoundException {
		out = new PrintStream(filePath);
	}
	
	public static void RedirErrFile(String filePath) throws FileNotFoundException {
		err = new PrintStream(filePath);
	}
}
