import java.awt.*; //recursos gráficos, como desenhos, cores e Rectangle.
import javax.swing.JFrame; //cria a janela do jogo.
import javax.swing.JPanel;//cria a área do jogo.
import javax.swing.Timer; //controla o tempo e atualizações do jogo.
import java.awt.event.ActionEvent; //representa um evento/ação.
import java.awt.event.ActionListener; //define o que acontece quando ocorre uma ação.
import java.awt.event.KeyAdapter; //facilita o controle pelo teclado.
import java.awt.event.KeyEvent; //identifica qual tecla foi pressionada
import java.util.HashMap; //armazenam dados em chave e valor.
import java.util.Map; //armazenam dados em chave e valor.
import java.util.List;
import java.util.ArrayList;

public class Janela extends JPanel implements ActionListener{

    private PersonagemJogador personagem;
    private boolean cima, baixo, esquerda, direita;
    private Map<Integer, javax.swing.Timer> liberacoesPendentes = new HashMap<>();
    private final List<Obstaculo> obstaculos = new ArrayList<>();
    private final BolinhaVitoria bolinha = new BolinhaVitoria(1400, 200, 35);
    private boolean venceu = false;
 
    public Janela(){
        personagem = new PersonagemJogador(50,100);

        obstaculos.add(new Obstaculo(0, 300, 1280, 30)); //chao
        obstaculos.add(new Obstaculo(-1, 0, 1, 300)); //parede
        //obstaculos.add(new Obstaculo(0, -1, 1920,15)); //teto
        obstaculos.add(new Obstaculo(200, 270, 300, 30));
        obstaculos.add(new Obstaculo(550, 210, 50, 100));
        obstaculos.add(new Obstaculo(670, 260, 50,50));
        obstaculos.add(new Obstaculo(670, 0, 50,180));
        obstaculos.add(new Obstaculo(850, 250, 50,50));
        obstaculos.add(new Obstaculo(970, 230, 100,70));
        obstaculos.add(new Obstaculo(1120, 170, 50, 130));
        obstaculos.add(new Obstaculo(1230, 120, 50,180));
        obstaculos.add(new Obstaculo(1400, 300, 50,30));



        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                trataTecla(e.getKeyCode(), true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                trataTecla(e.getKeyCode(), false);
            }
        });

        Timer loop = new Timer(16, this);
        loop.start();
    }

    private void trataTecla(int codigo, boolean pressionada){
        if(codigo == KeyEvent.VK_W) cima = pressionada;
        if(codigo == KeyEvent.VK_S) baixo = pressionada;
        if(codigo == KeyEvent.VK_A) esquerda = pressionada;
        if(codigo == KeyEvent.VK_D) direita = pressionada;
        if (codigo == KeyEvent.VK_SPACE && pressionada) {
            personagem.pular();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        int dx = 0;
        if (esquerda) dx -= 1;
        if (direita) dx += 1;

        personagem.animar(dx != 0);

        if (dx != 0) {
            Rectangle proximaPosicao = personagem.getBounds();
            proximaPosicao.translate(dx * 4, 0);

            if (obstaculoQueColide(proximaPosicao) == null) {
                personagem.mover(dx, 0);
            }
        }

        int proximoY = personagem.calcularProximaQuedaY();
        Rectangle proximaPosicaoY = new Rectangle(personagem.getBounds().x, proximoY,
                personagem.getBounds().width, personagem.getBounds().height);

        Obstaculo chao = obstaculoQueColide(proximaPosicaoY);
        if (chao != null) {
            int novoY = chao.getBounds().y - personagem.getBounds().height;
            personagem.pousar(novoY);
        } else {
            personagem.aplicarQueda(proximoY);
        }

        if (bolinha.isAtiva() && personagem.getBounds().intersects(bolinha.getBounds())) {
            bolinha.coletar();
            venceu = true;
        }
        repaint();
    }
    private Obstaculo obstaculoQueColide(Rectangle area) {
        for (Obstaculo o : obstaculos) {
            if (area.intersects(o.getBounds())) {
                return o;
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        personagem.desenhar(g);
        for(Obstaculo o : obstaculos)
            o.desenhar(g);
        bolinha.desenhar(g);

        if (venceu) {
            g.setColor(Color.BLACK);
            g.drawString("Você venceu!", 170, 150);
        }
    }

    public static void main(String[] args) {
        JFrame janela = new JFrame("Teste");
        Janela painel = new Janela();
        painel.setPreferredSize(new java.awt.Dimension(1920, 1080));

        janela.add(painel);
        janela.pack();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
    }
}