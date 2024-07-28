package decorators;

import java.awt.Font;

import javax.swing.JButton;

public class FontButtonDecorator extends ButtonDecorator {

    private Font font;

    public FontButtonDecorator(JButton decoratedButton, Font font) {
        super(decoratedButton);
        this.font = font;
        applyDecoration();
    }

    @Override
    public void applyDecoration() {
        decoratedButton.setFont(font);
    }
}
