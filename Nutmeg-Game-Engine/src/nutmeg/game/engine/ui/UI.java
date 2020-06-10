package nutmeg.game.engine.ui;

import java.awt.Color;
import java.util.HashMap;

import org.joml.Vector2f;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.enums.ImGuiBackendFlags;
import imgui.enums.ImGuiConfigFlags;
import imgui.enums.ImGuiDockNodeFlags;
import imgui.gl3.ImGuiImplGl3;
import nutmeg.core.Logger;
import nutmeg.game.engine.core.Application;
import nutmeg.game.engine.core.Window;
import nutmeg.game.engine.input.Keyboard;
import nutmeg.game.engine.input.Mouse;
import nutmeg.gl.core.Texture2D;
@SuppressWarnings("unused")
public class UI {
	private static Application app;
	private static Window wind;
	static ImGuiImplGl3 imGuiGL3;
	static ImGuiIO imguiIO;
	public static void Init(Application parent, Window window) {
        // This line is critical for Dear ImGui to work.
		app = parent;
		wind = window;
        ImGui.createContext();
        ImGui.setNextWindowSize(parent.width, parent.height);
        //ImGui.setNextWindowPos(0, 0);
        window.SetupImGUI(ImGui.getIO());
        ImGui.styleColorsDark();
        imGuiGL3 = new ImGuiImplGl3();
		// Method initializes LWJGL3 renderer.
        // This method SHOULD be called after you've initialized your ImGui configuration (fonts and so on).
        // ImGui context should be created as well.
        imGuiGL3.init("#version 330 core");
	}
	
	public static void Update(Window window, Mouse mouse, Keyboard keyboard, float deltaTime) {
		ImGuiIO io = ImGui.getIO();
		//io.setDisplaySize(window.getfWidth(), window.getfWidth());
        io.setMousePos(mouse.GetMouseX(), mouse.GetMouseY());
        io.setDeltaTime(deltaTime);

        // Update the mouse cursor
        final int imguiCursor = ImGui.getMouseCursor();
        window.UpdateIMGUICursor(imguiCursor);
	}
	
	public static void Begin(String name) {
		ImGui.begin(name);
	}
	
	public static void Begin(String name, float width, float height) {
		ImGui.setNextWindowSize(width, height);
		ImGui.begin(name);
	}
	
	public static void BeginDockSpace(String name, boolean fullscreen) {
//		Begin(name, (fullscreen ? app.width : 100), (fullscreen ? app.height : 100));
		int dockspaceFlags = 0;
		dockspaceFlags |= ImGuiDockNodeFlags.None;
		dockspaceFlags |= ImGuiDockNodeFlags.PassthruCentralNode;
		ImGui.dockSpace((int) wind.GetWindowID(), (fullscreen ? app.width : 100), (fullscreen ? app.height : 100), dockspaceFlags);
		//System.err.println("Creating Dockspace '"+name+"' ("+(fullscreen ? app.width : 100)+"x"+(fullscreen ? app.height : 100)+")");
	}
	
	public static void End() {
		ImGui.end();
	}
	
	public static void Render() {
		ImGui.render();
		imGuiGL3.render(ImGui.getDrawData());
	}
	
	public static void NewFrame() {
		ImGui.newFrame();
	}
	
	public static void EndFrame() {
		ImGui.endFrame();
	}
	
	public static void ShowDemoWindow() {
		ImGui.showDemoWindow();
	}

	public static void Text(String string) {
		ImGui.text(string);
	}
	
	public static void TextColored(String string, Color c) {
		ImGui.textColored(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f,string);
	}
	
	public static void Destroy() {
		ImGui.destroyContext();
	}

	public static void BeginMenu(String name, boolean enabled) {	
		ImGui.beginMenu(name, true);
	}
	
	public static void BeginMenuBar() {
		ImGui.beginMenuBar();
	}
	
	public static void Image(Texture2D texture) {
		ImGui.image(texture.GetID(), texture.GetWidth(), texture.GetHeight());
	}
	
	public static void Image(Texture2D texture, float width, float height) {
		ImGui.image(texture.GetID(), width, height);
	}
	 
	public static void SliderFloat(String label, float[] value, float minVal, float maxVal) {
		ImGui.sliderFloat(label, value, minVal, maxVal);
	}
	
	public static void SliderInt(String label, int[] value, int minVal, int maxVal) {
		ImGui.sliderInt(label, value, minVal, maxVal);
	}
	
	public static boolean Button(String label) {
		return ImGui.button(label);
	}
	
	public static void Checkbox(String label, Bool bool) {
		ImGui.checkbox(label, bool.GetReference());
	}
	
	public static void SameLine() {
		ImGui.sameLine();
	}
	
	public static void HistogramFloat(String label,float[] data) {
		ImGui.plotHistogram(label, data, data.length);
	}
	
	public static void HistogramFloat(String label,float[] data,int valOffset, String overlay, float min, float max) {
		ImGui.plotHistogram(label, data, data.length, valOffset, overlay, min, max);
	}
	
	public static void ViewPort(String name, Texture2D texture,float scale) {
		ImGuiViewport.ViewPort(name, texture, scale);
	}
	
	public static void HistogramFloat(String label,float[] data,int valOffset, String overlay, float min, float max, float width, float height) {
		ImGui.plotHistogram(label, data, data.length, valOffset, overlay, min, max, width, height);
	}
	
}
 