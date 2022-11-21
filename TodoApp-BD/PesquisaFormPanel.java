import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class PesquisaFormPanel extends JPanel {
    private AppFrame frame;

    private Pesquisa pesquisa;

    private GridBagLayout layout;
    private GridBagConstraints constraints;

    private JTextField txtId;
    private JFormattedTextField txtData;
    private JTextField txtFonte;
    private JButton btnSalvar;
    private JButton btnCancelar;

    public PesquisaFormPanel(AppFrame frame) {
        this.frame = frame;

        layout = new GridBagLayout();
        constraints = new GridBagConstraints();

        setLayout(layout);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                if (pesquisa == null) {
                    txtId.setText("");
                    txtData.setText("");
                    txtFonte.setText("");
                } else {
                    txtId.setText(Integer.toString(pesquisa.getIdPesquisa()));
                    txtData.setText(pesquisa.getDataPesquisa());
                    txtFonte.setText(pesquisa.getFontePesquisa());
                }
            }
        });

        criarForm();
        formatCampo();
        
    }

    public void setFontePesquisa(Pesquisa pesquisa) {
        this.pesquisa = pesquisa;
    }

    private void criarForm() {
        JLabel label;

        label = new JLabel("ID");
        adicionarComponente(label, 0, 0);
        txtId = new JTextField(5);
        txtId.setEditable(false);
        adicionarComponente(txtId, 1, 0);

        label = new JLabel("Data");
        adicionarComponente(label, 2, 0);
        txtData = new JFormattedTextField();
        adicionarComponente(txtData, 3, 0);

        label = new JLabel("Fonte");
        adicionarComponente(label, 4, 0);
        txtFonte = new JTextField(30);
        adicionarComponente(txtFonte, 5, 0);

        criarBotoes();
    }

    private void criarBotoes() {
        JPanel btnPanel = new JPanel();
        FlowLayout flowLayout = (FlowLayout) btnPanel.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);

        criarBtnSalvar(btnPanel);
        criarBtnCancelar(btnPanel);

        adicionarComponente(btnPanel, 6, 0, 2, 1);
    }

    private void criarBtnSalvar(JPanel btnPanel) {
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PesquisaFormPanel.this.pesquisa == null) {
                    Pesquisa novaPesquisa = new Pesquisa();
                    novaPesquisa.setDataPesquisa(txtData.getText());
                    novaPesquisa.setFontePesquisa(txtFonte.getText());

                    PesquisaStorage.inserir(novaPesquisa);
                    JOptionPane.showMessageDialog(PesquisaFormPanel.this, 
                                                  "Pesquisa incluida com sucesso", 
                                                  "Todo App", 
                                                  JOptionPane.INFORMATION_MESSAGE);
                } else {
                    pesquisa.setDataPesquisa(txtData.getText());
                    pesquisa.setFontePesquisa(txtFonte.getText());
                    PesquisaStorage.atualizar(PesquisaFormPanel.this.pesquisa);
                    JOptionPane.showMessageDialog(PesquisaFormPanel.this, 
                                                  "Pesquisa atualizada com sucesso", 
                                                  "Todo App", 
                                                  JOptionPane.INFORMATION_MESSAGE);
                }

                    
                frame.mostrarPesquisaListPanel();
            }
        });
        btnPanel.add(btnSalvar);
    }

    private void criarBtnCancelar(JPanel btnPanel) {
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.mostrarPesquisaListPanel();
            }
        });
        btnPanel.add(btnCancelar);
    }

    private void adicionarComponente(JComponent componente, 
                                    int linha, int coluna) {
        adicionarComponente(componente, linha, coluna, 1, 1);
    }                                    

    private void adicionarComponente(JComponent componente, 
                                    int linha, int coluna, 
                                    int largura, int altura) {
        constraints.gridx = coluna;
        constraints.gridy = linha;
        constraints.gridwidth = largura;
        constraints.gridheight = altura;

        constraints.fill = GridBagConstraints.HORIZONTAL;

        layout.setConstraints(componente, constraints);
        add(componente);
    }

    private void formatCampo () {
        try {
            MaskFormatter mask = new MaskFormatter("##-##-####");
            mask.install(txtData);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

} // fim da classe TarefaFormPanel