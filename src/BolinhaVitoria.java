import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class BolinhaVitoria {

    private int x;
    private int y;
    private int tamanho;
    private boolean ativa = true;

    public BolinhaVitoria(int x, int y, int tamanho) {
        this.x = x;
        this.y = y;
        this.tamanho = tamanho;
    }

    public void desenhar(Graphics g) {
        if (!ativa) {
            return;
        }
        g.setColor(Color.GREEN);
        g.fillOval(x, y, tamanho, tamanho);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, tamanho, tamanho);
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void coletar() {
        ativa = false;
    }
}