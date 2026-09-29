public class Fisica {

    private double velocidadeY = 0;
    private final double gravidade = 0.5;
    private final double chaoY;

    public Fisica(double chaoY){
        this.chaoY = chaoY;
    }

    public double atualizar(double posY){
        velocidadeY += gravidade;
        posY += velocidadeY;

        if(posY >= chaoY){
            posY = chaoY;
            velocidadeY = 0;
        }
        return posY;
    }
}
