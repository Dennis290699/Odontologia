package decorators;

import java.awt.Color;

import javax.swing.JButton;

public class ColorButtonDecorator extends ButtonDecorator {

    private Color backgroundColor;
    private Color textColor;

    public ColorButtonDecorator(JButton decoratedButton, Color backgroundColor, Color textColor) {
        super(decoratedButton);
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        applyDecoration();
    }

    @Override
    public void applyDecoration() {
        decoratedButton.setBackground(backgroundColor);
        decoratedButton.setForeground(textColor);
    }
}
