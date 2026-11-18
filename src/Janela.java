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
    private PersonagemInimigo personagemInimigo;
    private boolean esquerda, direita;
    private boolean menu = false;
    private Map<Integer, javax.swing.Timer> liberacoesPendentes = new HashMap<>();
    private final List<Obstaculo> obstaculos = new ArrayList<>();
    private final BolinhaVitoria bolinha = new BolinhaVitoria(1400, 200, 35);
    private boolean venceu = false;
    private boolean perdeu = false;
    private int cameraX = 0;

 
    public Janela(){
        setBackground(Color.DARK_GRAY);
        personagem = new PersonagemJogador(50,100);

        personagemInimigo = new PersonagemInimigo(1920, 70);

        obstaculos.add(new Obstaculo(0, 300, 2200, 30)); // chão
        obstaculos.add(new Obstaculo(-1, 0, 1, 300)); // parede esquerda
        // obstaculos.add(new Obstaculo(0, -1, 2200, 15)); // teto

        obstaculos.add(new Obstaculo(200, 270, 300, 30));
        obstaculos.add(new Obstaculo(550, 210, 50, 100));
        obstaculos.add(new Obstaculo(670, 260, 50, 50));
        obstaculos.add(new Obstaculo(670, 0, 50, 180));
        obstaculos.add(new Obstaculo(850, 250, 50, 50));
        obstaculos.add(new Obstaculo(970, 230, 100, 70));
        obstaculos.add(new Obstaculo(1120, 170, 50, 130));
        obstaculos.add(new Obstaculo(1230, 120, 50, 180));
        obstaculos.add(new Obstaculo(1400, 300, 150, 30));

        // Novos obstáculos
        obstaculos.add(new Obstaculo(1650, 260, 100, 40));
        obstaculos.add(new Obstaculo(1800, 210, 50, 90));
        obstaculos.add(new Obstaculo(1950, 250, 100, 50));
        obstaculos.add(new Obstaculo(2100, 180, 50, 120));
        obstaculos.add(new Obstaculo(2250, 230, 150, 70));
        obstaculos.add(new Obstaculo(2450, 150, 50, 150));
        obstaculos.add(new Obstaculo(2600, 270, 150, 30));
        obstaculos.add(new Obstaculo(2800, 200, 100, 100));
        obstaculos.add(new Obstaculo(3000, 250, 100, 50));

        // Parede final
        obstaculos.add(new Obstaculo(3150, 0, 1, 300));

        // Chão inferior
        obstaculos.add(new Obstaculo(0, 1030, 3200, 50));
        



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

        Timer loop = new Timer(10, this); //TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT
        loop.start();
    }

    private void trataTecla(int codigo, boolean pressionada){
        if(codigo == KeyEvent.VK_A) esquerda = pressionada;
        if(codigo == KeyEvent.VK_D) direita = pressionada;
        if (codigo == KeyEvent.VK_SPACE && pressionada) {
            personagem.pular();
        } 
        if(codigo == KeyEvent.VK_ESCAPE) menu = pressionada;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        int dx = 0;
        if (esquerda) dx -= 1;
        if (direita) dx += 1;

        if (menu)

        personagem.animar(dx != 0);
        personagemInimigo.mover(0, 0);

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

            if (personagem.estaCaindo()) {

                // Está descendo: pousou em cima do obstáculo
                int novoY = chao.getBounds().y - personagem.getBounds().height;
                personagem.pousar(novoY);

            } else if (personagem.estaSubindo()) {

                int novoY = chao.getBounds().y + chao.getBounds().height;
                personagem.baterCabeca(novoY);
            }

        } else {
            personagem.aplicarQueda(proximoY);
        }

        if (bolinha.isAtiva() && personagem.getBounds().intersects(bolinha.getBounds())) {
            bolinha.coletar();
            venceu = true;
        }


        if(personagem.getBounds().intersects(personagemInimigo.getBounds())){
            personagem.mover(-48, 70);
            personagem.setVida(personagem.getVida() -1);
        }

        // if(personagem.getVida() == 0){
        //     personagem.mover(500, 69);

        // }

                // Dentro do actionPerformed, antes do repaint();
        int limiteTelaX = 600; // Ponto X onde a câmera começa a seguir o jogador

        if (personagem.getBounds().x > limiteTelaX) {
            cameraX = personagem.getBounds().x - limiteTelaX;
        } else {
            cameraX = 0; // Não deixa a câmera voltar antes do início da fase
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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // 1. Move a câmera para acompanhar o personagem
        g2d.translate(-cameraX, 0);

        // Desenha os elementos do cenário
        personagem.desenhar(g2d);
        personagemInimigo.desenhar(g2d);
        for (Obstaculo o : obstaculos) {
            o.desenhar(g2d);
        }
        bolinha.desenhar(g2d);

        // 2. Reseta a câmera para desenhar os textos fixos na tela
        g2d.translate(cameraX, 0);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("VIDA: " + personagem.getVida(), 1300, 30);

        if (venceu) {
            g.setColor(Color.YELLOW);
            g.drawString("Você venceu!", 170, 150);
        }

        if (perdeu) {
            g.setColor(Color.CYAN);
            g.drawString("Voce Perdeu!", 170, 150);
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