import java.util.Random;

public class PatternMatching {
    static long instrucoes = 0;
    static long iteracoes = 0;

    public static void main(String[] args) {
        String inputPequeno = "ABCDCBDCBDACBDABDCBADF";
        String pattern = "ADF";
        String inputGrande = gerarStringAleatoria(500000) + pattern; // Garante que o padrão existe

        String[][] resultados = new String[2][6];

        // Teste com entrada pequena
        resetContagem();
        long tempoInicial = System.nanoTime();
        encontrarPrimeiraOcorrencia(inputPequeno, pattern);
        long tempoFinal = System.nanoTime();
        double tempoTotal = (tempoFinal - tempoInicial) / 1_000_000.0;
        resultados[0] = new String[]{
            "Pattern Matching naive", pattern, inputPequeno,
            String.valueOf(iteracoes), String.valueOf(instrucoes), String.valueOf(tempoTotal)
        };

        // Teste com entrada grande
        resetContagem();
        tempoInicial = System.nanoTime();
        encontrarPrimeiraOcorrencia(inputGrande, pattern);
        tempoFinal = System.nanoTime();
        tempoTotal = (tempoFinal - tempoInicial) / 1_000_000.0;
        resultados[1] = new String[]{
            "Pattern Matching naive", pattern, "String aleatoria de 500.000 caracteres + padrão",
            String.valueOf(iteracoes), String.valueOf(instrucoes), String.valueOf(tempoTotal)
        };
        System.out.println();
        printTable(resultados);
        System.out.println();
    }

    /*
     * Encontra a posição da primeira ocorrência de um padrão em uma string.
     */
    public static int encontrarPrimeiraOcorrencia(String input, String pattern) {
        int n = input.length();
        int m = pattern.length();
        instrucoes += 2;

        for (int i = 0; i <= n - m; i++) {
            iteracoes++;
            instrucoes++;

            int j = 0;
            instrucoes++;

            while (j < m && input.charAt(i + j) == pattern.charAt(j)) {
                instrucoes += 3;
                j++;
                iteracoes++;
            }

            instrucoes++;
            if (j == m) {
                return i;
            }
        }
        return -1;
    }

    // Função para gerar uma string aleatória com letras maiúsculas
    public static String gerarStringAleatoria(int tamanho) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(tamanho);
        for (int i = 0; i < tamanho; i++) {
            sb.append((char) ('A' + random.nextInt(26)));
        }
        return sb.toString();
    }

    // Função para resetar contagem de instruções e iterações
    public static void resetContagem() {
        instrucoes = 0;
        iteracoes = 0;
    }

    // Função para imprimir a tabela de resultados
    public static void printTable(String[][] rows) {
        String[] headers = {"Algoritmo", "Padrão", "Texto", "Iterações", "Instruções", "Tempo (ms)"};
        int[] widths = {25, 8, 50, 12, 13, 12};

        // Imprime cabeçalhos
        for (int i = 0; i < headers.length; i++) {
            System.out.printf("%-" + widths[i] + "s", headers[i]);
            if (i < headers.length - 1) System.out.print(" | ");
        }
        System.out.println();

        // Linha de divisão
        for (int width : widths) {
            System.out.print("-".repeat(width));
            System.out.print("-+-");
        }
        System.out.println();

        // Dados
        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                System.out.printf("%-" + widths[i] + "s", row[i]);
                if (i < row.length - 1) System.out.print(" | ");
            }
            System.out.println();
        }
    }
}
