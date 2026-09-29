import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Personagem {

    protected int x;
    protected int y;
    protected final int largura = 48;
    protected final int altura = 48;
    protected final int velocidade = 4;

    public abstract void desenhar(Graphics g);

    public abstract void mover(int dx, int dy);

    public abstract Rectangle getBounds();
}