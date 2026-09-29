import java.io.File;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class PersonagemJogador extends Personagem{

    private static final int LARGURA = 48;
    private static final int ALTURA = 48;
    private final int velocidade = 4;
    private final Fisica fisica;
    private BufferedImage spriteParado;
    private BufferedImage spriteAndando1;
    private BufferedImage spriteAndando2;
    private BufferedImage spriteAtual;

    private int contadorAnimacao = 0;
    private boolean primeiraPerna = true;
    private static final int INTERVALO_ANIMACAO = 13; // quanto menor, mais rapido troca

    public PersonagemJogador(int xInicial, int yInicial, double chaoY){
        this.x = xInicial;
        this.y = yInicial;
        this.fisica = new Fisica(chaoY);
        carregarSprites();
    }

    private void carregarSprites() {
        try {
            spriteParado = ImageIO.read(new File("sprites/megamanParado.png"));
            spriteAndando1 = ImageIO.read(new File("sprites/megamanAndando.png"));
            spriteAndando2 = ImageIO.read(new File("sprites/megamanAndando22.png"));
            spriteAtual = spriteParado;
        } catch (IOException e) {
            System.out.println("Nao consegui carregar os sprites: " + e.getMessage());
        }
    }

    public void animar(boolean andando) {
        if (!andando) {
            spriteAtual = spriteParado;
            contadorAnimacao = 0;
            return;
        }

        contadorAnimacao++;
        if (contadorAnimacao >= INTERVALO_ANIMACAO) {
            contadorAnimacao = 0;
            primeiraPerna = !primeiraPerna;
            spriteAtual = primeiraPerna ? spriteAndando1 : spriteAndando2;
        }
    }

    @Override
    public void desenhar(Graphics g){
        g.drawImage(spriteAtual, x,  y, LARGURA, ALTURA, null);

    }

    @Override
    public void mover(int dx, int dy){
        this.x += dx * velocidade;
    }

    public void aplicarGravidade(){
        this.y = (int) fisica.atualizar(this.y);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, LARGURA, ALTURA);
    }
}
