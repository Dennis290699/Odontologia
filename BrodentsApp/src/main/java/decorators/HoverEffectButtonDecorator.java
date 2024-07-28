package decorators;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class HoverEffectButtonDecorator extends ButtonDecorator {

    private Color hoverColor;
    private Color textHoverColor;
    private Color originalColor;
    private Color originalTextColor;

    public HoverEffectButtonDecorator(JButton decoratedButton, Color hoverColor, Color textHoverColor) {
        super(decoratedButton);
        this.hoverColor = hoverColor;
        this.textHoverColor = textHoverColor;
        this.originalColor = decoratedButton.getBackground();
        this.originalTextColor = decoratedButton.getForeground();
        applyDecoration();
    }

    @Override
    public void applyDecoration() {
        decoratedButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                decoratedButton.setBackground(hoverColor);
                decoratedButton.setForeground(textHoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                decoratedButton.setBackground(originalColor);
                decoratedButton.setForeground(originalTextColor);
            }
        });
    }
}
