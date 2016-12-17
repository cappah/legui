package org.liquidengine.legui.example;

import org.joml.Vector4f;
import org.liquidengine.legui.component.Component;
import org.liquidengine.legui.component.Frame;
import org.liquidengine.legui.component.Label;
import org.liquidengine.legui.component.optional.align.HorizontalAlign;
import org.liquidengine.legui.component.optional.align.VerticalAlign;
import org.liquidengine.legui.util.ColorConstants;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

/**
 * Created by Alexander on 14.10.2016.
 */
public class LiquidEngineGuiExample extends Demo {


    private static Label leguiLabel;
    private static Vector4f second = ColorConstants.red();
    private static Vector4f first = ColorConstants.darkBlue();
    private static GLFWWindowSizeCallbackI windowSizeCallbackI;


    public static void main(String[] args) {
        int width = 400;
        int height = 150;
        Frame frame = new Frame(width, height);
        Component component = createGui(width, height);
        frame.addComponent(component);
        LiquidEngineGuiExample example = new LiquidEngineGuiExample(width, height, "Liquid Engine GUI", frame, true);
        example.start();
        windowSizeCallbackI = (window, wid, hei) -> {
            leguiLabel.setSize(wid, hei);
            leguiLabel.getTextState().setFontSize(hei);
        };
        example.callbackKeeper.getChainWindowSizeCallback().add(windowSizeCallbackI);
    }

    private static Component createGui(int width, int height) {
        leguiLabel = new Label(0, 0, width, height);
        leguiLabel.setBackgroundColor(ColorConstants.transparent());
        leguiLabel.getTextState().setTextColor(ColorConstants.darkGray());
        leguiLabel.getTextState().setText("LE GUI");
        leguiLabel.getTextState().setFontSize(height);
        leguiLabel.getTextState().setHorizontalAlign(HorizontalAlign.CENTER);
        leguiLabel.getTextState().setVerticalAlign(VerticalAlign.MIDDLE);
        return leguiLabel;
    }

    @Override
    public void update() {
        leguiLabel.getTextState().setTextColor(new Vector4f(second).sub(first).mul((float) ((Math.sin(GLFW.glfwGetTime()) + 1) / 2f)).add(first));

    }

    public LiquidEngineGuiExample(int width, int height, String title, Frame frame, boolean resizable) {
        super(width, height, title, frame, resizable);
    }
}
