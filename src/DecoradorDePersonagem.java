import java.awt.Graphics;
import java.awt.Rectangle;
public abstract class DecoradorDePersonagem extends Personagem{
    protected Personagem personagem;

    public DecoradorDePersonagem(Personagem personagem){
        this.personagem = personagem;
    }

    @Override
    public void mover(int dx, int dy){
        personagem.mover(dx, dy);
    }
    @Override
    public Rectangle getBounds(){
        return personagem.getBounds();
    }
}
