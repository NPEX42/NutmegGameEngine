package nutmeg.game.engine.ecs;

import nutmeg.game.engine.core.Application;

public abstract class Script {
	public void EarlyUpdate(Application app) {}
	public abstract void Update( Application app);
	public void LateUpdate(Application app) {}
	
	public abstract void Start(Application app);
}
