import java.util.Scanner;

public class Main {
    private static DirecaoXuBank direcao = new DirecaoXuBank();
    private static Cliente clienteAtual = null;

    //Menu inicial do sistema
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Cliente");
            System.out.println("2. Diretores");
            System.out.println("3. Passar Meses");
            System.out.println("Escolha uma opção:");
            int opcao = scanner.nextInt();
            if (opcao == 1) {
                menuCliente(scanner);
            } else if (opcao == 2) {
                menuDiretores(scanner);
            } else if (opcao == 3) {
            	passarMeses(scanner);
            }
        }
    }

    //Menu de Opções para o Cliente

    private static void menuCliente(Scanner scanner) {
        System.out.println("1. Criar nova conta");
        System.out.println("2. Sacar");
        System.out.println("3. Depositar");
        System.out.println("4. Verificar situação das contas");
        System.out.println("Escolha uma opção:");
        int opcao = scanner.nextInt();
    
        if (opcao == 1) {
            // Código para criar nova conta
            Conta novaConta = criarConta(scanner);
            if (novaConta != null) {
                clienteAtual.adicionarConta(novaConta);
            } else {
                System.out.println("Não foi possível criar a conta. Por favor, tente novamente.");
            }
        } else if (opcao == 2 || opcao == 3) {
            if (clienteAtual.getContas().isEmpty()) {
                System.out.println("Você não possui contas. Por favor, crie uma conta antes de sacar ou depositar.");
                return;
            }
    
            System.out.println("Escolha a conta para " + (opcao == 2 ? "sacar" : "depositar") + ":");
    
            for (int i = 0; i < clienteAtual.getContas().size(); i++) {
                Conta conta = clienteAtual.getContas().get(i);
                System.out.println((i + 1) + ". " + conta.getNumeroDaConta() + " - Tipo: " + getTipoConta(conta) + " - Saldo: " + conta.getSaldo());
            }
    
            int escolhaConta = scanner.nextInt();
    
            if (escolhaConta < 1 || escolhaConta > clienteAtual.getContas().size()) {
                System.out.println("Escolha de conta inválida. Tente novamente.");
                return;
            }
    
            Conta contaSelecionada = clienteAtual.getContas().get(escolhaConta - 1);
    
            System.out.println("Digite o valor:");
            double valor = scanner.nextDouble();
            if (opcao == 2) { // Sacar
                contaSelecionada.sacar(valor);
            } else if (opcao == 3) { // Depositar
                contaSelecionada.depositar(valor);
            }
        } else if (opcao == 4) {
            // Código para verificar situação das contas
            clienteAtual.verContas();
        }
    }
    
    
    private static String getTipoConta(Conta conta) {
        if (conta instanceof ContaCorrente) {
            return "Corrente";
        } else if (conta instanceof ContaPoupanca) {
            return "Poupança";
        } else if (conta instanceof ContaRendaFixa) {
            return "Renda Fixa";
        } else if (conta instanceof ContaInvestimento) {
            return "Investimento";
        } else {
            return "Desconhecido";
        }
    }        

    //Caso o cliente tenha escolhido a opção de criar conta

    private static Conta criarConta(Scanner scanner) {
        System.out.println("Digite o tipo de conta que deseja criar (corrente, poupanca, rendafixa, investimento):");
        String tipo = scanner.next();
        System.out.println("Digite o nome do cliente:");
        String nome = scanner.next();
        System.out.println("Digite o CPF do cliente:");
        String cpf = scanner.next();
        System.out.println("Digite a senha do cliente:");
        String senha = scanner.next();
        System.out.println("Digite o número da conta:");
        String numeroDaConta = scanner.next();
        clienteAtual = criarCliente(nome, cpf, senha);
        direcao.adicionarCliente(clienteAtual);
        if (tipo.equals("corrente")) {
            System.out.println("Digite o limite para a conta corrente:");
            double limite = scanner.nextDouble();
            return new ContaCorrente(numeroDaConta, nome, cpf, limite);
        } else if (tipo.equals("poupanca")) {
            return new ContaPoupanca(numeroDaConta, nome, cpf);
        } else if (tipo.equals("rendafixa")) {
            return new ContaRendaFixa(numeroDaConta, nome, cpf);
        } else if (tipo.equals("investimento")) {
            return new ContaInvestimento(numeroDaConta, nome, cpf);
        } else {
            System.out.println("Tipo de conta inválido. Por favor, tente novamente.");
            return null;
        }
    }
    
    private static Cliente criarCliente(String nome, String cpf, String senha) {
        return new Cliente(nome, cpf, senha);
    }

    //Menu de opções para os diretores

    private static void menuDiretores(Scanner scanner) {
        System.out.println("1. Valor total em custódia para cada tipo de conta");
        System.out.println("2. Saldo médio das contas");
        System.out.println("3. Cliente com maior saldo");
        System.out.println("4. Cliente com menor saldo");
        System.out.println("Escolha uma opção:");
        int opcao = scanner.nextInt();
        if (opcao == 1) {
            // Código para obter o valor total em custódia para cada tipo de conta
            System.out.println("Valor total em custódia: " + direcao.getValorEmCustodia());
        } else if (opcao == 2) {
            // Código para obter o saldo médio das contas
            System.out.println("Saldo médio das contas: " + direcao.getSaldoMedio());
        } else if (opcao == 3) {
            // Código para obter o cliente com maior saldo
            Cliente clienteComMaiorSaldo = direcao.getClienteComMaiorSaldo();
            System.out.println("Cliente com maior saldo: " + clienteComMaiorSaldo.getNome());
        } else if (opcao == 4) {
            // Código para obter o cliente com menor saldo
            Cliente clienteComMenorSaldo = direcao.getClienteComMenorSaldo();
            System.out.println("Cliente com menor saldo: " + clienteComMenorSaldo.getNome());
        }
    }

    //Opção de passar os meses
    
    private static void passarMeses(Scanner scanner) {
        System.out.println("Digite o número de meses que deseja passar:");
        int meses = scanner.nextInt();
        for (int i = 0; i < meses; i++) {
            for (Cliente cliente : direcao.getClientes()) {
                for (Conta conta : cliente.getContas()) {
                    double rendimento = conta.getRendimentoMensal();
                    double imposto = conta.getImposto();
                    conta.depositar(rendimento - imposto);
                }
            }
        }
    }
}
