public class ContaInvestimento extends Conta {
    
    public ContaInvestimento(String numeroDaConta, String nome, String cpf) {
        super(numeroDaConta, nome, cpf);
    }

    @Override
    public void sacar(double valor) {
        if (getSaldo() >= valor) {
            depositar(-valor);
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    //Método para calcular o rendimento mensal

    @Override
    public double getRendimentoMensal() {
        return getSaldo() * (Math.random() * (0.015 - (-0.006)) - 0.006);
    }

    //Método para fazer o cálculo de imposto

    @Override
    public double getImposto() {
        double rendimento = getRendimentoMensal();
        if (rendimento > 0) {
            return rendimento * 0.225;
        } else {
            return 0;
        }
    }
}