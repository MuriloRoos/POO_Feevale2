import java.awt.*;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

public class PersonagemInimigo extends Personagem {

    public static final int LARGURA = 48;
    public static final int ALTURA = 48;
    public final int velocidade = 3;
    public int posicaoFinal = 0;
    private long tempoProximoMovimento;
    private boolean esperando = false;

    private final Random random = new Random();

    public PersonagemInimigo(int xInicial, int yInicial) {
        this.x = xInicial;
        this.y = yInicial;
        //carregarSprites();
    }

    @Override
    public void desenhar(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, ALTURA, LARGURA);
    }

    @Override
    public void mover(int dx, int dy) {

        if (esperando) {

            if (System.currentTimeMillis() >= tempoProximoMovimento) {
                esperando = false;
            }

            return;
        }

        x -= velocidade;

        if (x <= -48) {

            x = 1920;

            esperando = true;

            int espera = random.nextInt(2000) + 1000;

            tempoProximoMovimento =
                    System.currentTimeMillis() + espera;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y, LARGURA, ALTURA);
    }
}
