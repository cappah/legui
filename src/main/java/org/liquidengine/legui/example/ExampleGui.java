package org.liquidengine.legui.example;

import org.joml.Vector4f;
import org.liquidengine.legui.component.*;
import org.liquidengine.legui.component.optional.Orientation;


/**
 * Created by Shcherbin Alexander on 9/19/2016.
 */
public class ExampleGui extends ComponentContainer {
    private Image image;

    public ExampleGui(int width, int height) {
        super(0, 0, width, height);

        Panel p1 = new Panel(1 * 20, 10, 10, 10);
        this.addComponent(p1);

        Panel p2 = new Panel(2 * 20, 10, 10, 10);
        this.addComponent(p2);

        Panel p3 = new Panel(3 * 20, 10, 10, 10);
        this.addComponent(p3);

        Panel p4 = new Panel(4 * 20, 10, 10, 10);
        this.addComponent(p4);

        Panel p5 = new Panel(5 * 20, 10, 10, 10);
        this.addComponent(p5);

        Panel p6 = new Panel(6 * 20, 10, 10, 10);
        this.addComponent(p6);

        Panel p7 = new Panel(7 * 20, 10, 10, 10);
        this.addComponent(p7);

        Panel p8 = new Panel(8 * 20, 10, 10, 10);
        this.addComponent(p8);

        Panel p9 = new Panel(9 * 20, 10, 10, 10);
        this.addComponent(p9);

        Panel p10 = new Panel(10 * 20, 10, 10, 10);
        this.addComponent(p10);

        Panel p11 = new Panel(11 * 20, 10, 10, 10);
        this.addComponent(p11);

        Label label = new Label(1 * 20, 30, 100, 20, "Hello Label");
        this.addComponent(label);

        image = Image.createImage("org/liquidengine/legui/example/1.jpg");
        image.setPosition(20, 60);
        image.setSize(100, 100);
        this.addComponent(image);

        Button button = new Button(20, 170, 50, 20);
        button.setBackgroundColor(new Vector4f(1));
        this.addComponent(button);

        CheckBox checkBox1 = new CheckBox(20, 200, 50, 20);
        this.addComponent(checkBox1);

        CheckBox checkBox2 = new CheckBox(20, 230, 50, 20);
        checkBox2.setBackgroundColor(new Vector4f(1));
        checkBox2.setChecked(true);
        this.addComponent(checkBox2);

        ProgressBar progressBar = new ProgressBar(250, 10, 100, 10);
        progressBar.setValue(50);
        this.addComponent(progressBar);

        RadioButton radioButton1 = new RadioButton(250, 30, 100, 20);
        this.addComponent(radioButton1);
        radioButton1.setSelected(true);
        RadioButton radioButton2 = new RadioButton(250, 60, 100, 20);
        this.addComponent(radioButton2);
        radioButton2.setSelected(false);

        Slider slider1 = new Slider(250, 90, 100, 20, 30);
        this.addComponent(slider1);

        Slider slider2 = new Slider(220, 90, 20, 100, 50);
        slider2.setOrientation(Orientation.VERTICAL);
        this.addComponent(slider2);

        TextInput textInput = new TextInput(250,130,100,30);
        this.addComponent(textInput);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.removeComponent(this.image);
        this.image = image;
        if (image != null) {
            this.addComponent(image);
        }
    }

}
