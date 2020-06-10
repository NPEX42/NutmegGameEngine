package nutmeg.editor.core;


import nutmeg.core.Nutmeg;

public class SandboxMain {
	public static void main(String[] args) {
		Nutmeg.DisableDebugMode();
		new App().ConstructWindow(1080, 720, "Nutmeg Editor 1.0a");
	}
}
