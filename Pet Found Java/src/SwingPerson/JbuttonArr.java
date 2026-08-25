package SwingPerson;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class JbuttonArr extends JButton {

    private Color backgroundColor = new Color(255, 148, 44); // Cor de fundo
    private Color borderColor = new Color(255, 148, 44); // Cor da borda
    private Color hoverBorderColor = new Color(0, 0, 0); // Cor da borda ao passar o mouse
    private int borderThickness = 2; // Espessura da borda

    public JbuttonArr() {
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(5, 0, 5, 0));
        setBackground(backgroundColor);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                borderColor = hoverBorderColor;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                borderColor = new Color(255, 148, 44);
                repaint();
            }
        });
    }

    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arcWidth = 20; // Largura do arredondamento
        int arcHeight = 20; // Altura do arredondamento

        // Pinte o fundo do botão
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, width - 1, height - 1, arcWidth, arcHeight);

        // Defina a cor e espessura da borda
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderThickness)); // Define a espessura da borda
        g2.drawRoundRect(borderThickness / 2, borderThickness / 2, width - 1 - borderThickness, height - 1 - borderThickness, arcWidth, arcHeight);

        super.paintComponent(g);
    }
}
