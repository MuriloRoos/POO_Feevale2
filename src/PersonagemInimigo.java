import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class PersonagemInimigo extends Personagem {

    public static final int LARGURA = 48;
    public static final int ALTURA = 48;
    public final int velocidade = 6;
    public int posicaoFinal = 0;

    public PersonagemInimigo(int xInicial, int yInicial) {
        this.x = xInicial;
        this.y = yInicial;
        //carregarSprites();
    }

    @Override
    public void desenhar(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, ALTURA, LARGURA);
    }

    public void mover(int dx, int dy) {
        if(x != posicaoFinal) {
            this.x += -dx * velocidade;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y, LARGURA, ALTURA);
    }
}
