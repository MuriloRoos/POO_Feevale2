import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class Janela extends JPanel implements ActionListener{

    private PersonagemJogador personagem;
    private boolean cima, baixo, esquerda, direita;
    private Map<Integer, javax.swing.Timer> liberacoesPendentes = new HashMap<>();
    private final Obstaculo obstaculo = new Obstaculo(0, 300, 2000, 30);
    private final Obstaculo obstaculoParede = new Obstaculo(-1, 0, 1, 300);

    public Janela(){
        personagem = new PersonagemJogador(50,100, 300 - 48);

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int codigo = e.getKeyCode();
                javax.swing.Timer pendente = liberacoesPendentes.remove(codigo);
                if (pendente != null) {
                    pendente.stop(); // cancela o "soltar" que tinha sido agendado: foi so o auto-repeat do SO
                }
                trataTecla(codigo, true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int codigo = e.getKeyCode();
                javax.swing.Timer adiar = new javax.swing.Timer(40, ev -> {
                    trataTecla(codigo, false);
                    liberacoesPendentes.remove(codigo);
                });
                adiar.setRepeats(false);
                adiar.start();
                liberacoesPendentes.put(codigo, adiar);
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

            if (!proximaPosicao.intersects(obstaculo.getBounds())) {
                personagem.mover(dx, 0);
            }
        }

        personagem.aplicarGravidade();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        personagem.desenhar(g);
        obstaculo.desenhar(g);
        obstaculoParede.desenhar(g);
    }

    public static void main(String[] args) {
        JFrame janela = new JFrame("Teste");
        Janela painel = new Janela();
        painel.setPreferredSize(new java.awt.Dimension(1920, 1500));

        janela.add(painel);
        janela.pack();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
    }
}