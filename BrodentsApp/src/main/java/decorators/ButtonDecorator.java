package decorators;

import javax.swing.JButton;

public abstract class ButtonDecorator extends JButton {
    protected JButton decoratedButton;

    public ButtonDecorator(JButton decoratedButton) {
        this.decoratedButton = decoratedButton;
    }

    public abstract void applyDecoration();
}
