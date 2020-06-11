package sandbox.tests;

import nutmeg.game.engine.core.Application;

public class ExampleApp extends Application {

	@Override
	public void Setup() {
		AddLayer(new ExampleLayer());
	}
	
}
