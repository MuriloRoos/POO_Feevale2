import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Obstaculo {

    private int x;
    private int y;
    private int largura;
    private int altura;

    public Obstaculo(int x, int y, int largura, int altura) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
    }

    public void desenhar(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(x, y, largura, altura);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura);
    }
}