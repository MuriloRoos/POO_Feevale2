import java.io.File;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class PersonagemJogador extends Personagem{

    private static final int LARGURA = 48;
    private static final int ALTURA = 48;
    private final int velocidade = 3;
    private final Fisica fisica;
    private BufferedImage spriteParado;
    private BufferedImage spriteAndando1d;
    private BufferedImage spriteAndando2d;
    private BufferedImage spriteAndando3d;
    private BufferedImage spriteAtual;

    private int contadorAnimacao = 0;
    private int primeiraPerna = 0;
    private static final int INTERVALO_ANIMACAO = 7; // quanto menor, mais rapido troca

    public PersonagemJogador(int xInicial, int yInicial, double chaoY){
        this.x = xInicial;
        this.y = yInicial;
        this.fisica = new Fisica(chaoY);
        carregarSprites();
    }

    private void carregarSprites() {
        try {
            spriteParado = ImageIO.read(new File("sprites/megamanParado.png"));
            spriteAndando1d = ImageIO.read(new File("sprites/megamanAndando1D.png"));
            spriteAndando2d = ImageIO.read(new File("sprites/megamanAndando2D.png"));
            spriteAndando3d = ImageIO.read(new File("sprites/megamanAndando3D.png"));
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
            switch (primeiraPerna++) {
                case 0:
                    spriteAtual = spriteAndando3d;
                    break;
                case 1:
                    spriteAtual = spriteAndando2d;
                    break;
                case 2:
                    spriteAtual = spriteAndando1d;
                    break;
                case 3:
                    spriteAtual = spriteAndando2d;
                    primeiraPerna = 0;
                default:
                    break;
            }
        }
    }
    
    public void pular(){
        fisica.pular(8.5);
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
