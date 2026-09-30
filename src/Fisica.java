public class Fisica {

    private double velocidadeY = 0;
    private final double gravidade = 1.5 ;
    private boolean noChao = false;

    public double calcularQueda(double posY) {
        velocidadeY += gravidade;
        return posY + velocidadeY;
    }

    public void pousar() {
        velocidadeY = 0;
        noChao = true;
    }

    public void cair() {
        noChao = false;
    }

    public void pular(double forca) {
        if (noChao) {
            velocidadeY = -forca;
            noChao = false;
        }
    }
}