import java.util.ArrayList;

public class PatternMatchingHash {
    static long instrucoes = 0;
    static long iteracoes = 0;

    public static void main(String[] args) {
        String input = "ABCDCBDCBDACBDABDCBADF";
        String pattern = "ADF";

        long tempoInicial = System.nanoTime();
        search(pattern, input); // Usando Rabin-Karp
        long tempoFinal = System.nanoTime();
        double tempoTotal = (tempoFinal - tempoInicial) / 1_000_000.0; // Tempo em milissegundos

        String[][] resultadosTabela = {
            {"Pattern Matching", pattern, input, String.valueOf(iteracoes), String.valueOf(instrucoes), String.valueOf(tempoTotal)}
        };
        printTable(resultadosTabela); 
    }

    /*
     * Algoritmo de Rabin-Karp para encontrar todas as ocorrências de um padrão em uma string.
     */
    public static ArrayList<Integer> search(String pat, String txt) {
        int d = 256; // Número de caracteres no alfabeto (ASCII)
        int q = 101; // Um número primo para a operação de módulo
        int M = pat.length(); // Comprimento do padrão
        int N = txt.length(); // Comprimento do texto
        int p = 0; // Valor hash do padrão
        int t = 0; // Valor hash da janela atual do texto
        int h = 1; // Multiplicador de dígitos de ordem superior
        ArrayList<Integer> ans = new ArrayList<>();

        // Pré-calcula h = pow(d, M-1) % q
        for (int i = 0; i < M - 1; i++) {
            h = (h * d) % q;
            instrucoes++;  
        }

        // Calcula os valores hash iniciais para o padrão e a primeira janela do texto
        for (int i = 0; i < M; i++) {
            p = (d * p + pat.charAt(i)) % q;
            t = (d * t + txt.charAt(i)) % q;
            instrucoes++;  
        }

        // Desliza o padrão sobre o texto
        for (int i = 0; i <= N - M; i++) {
            iteracoes++;  

            // Se os valores de hash coincidem, verifica os caracteres um a um
            if (p == t) {
                boolean match = true;
                for (int j = 0; j < M; j++) {
                    instrucoes++;  // Contabiliza instrução
                    if (txt.charAt(i + j) != pat.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    ans.add(i + 1); // Índices baseados em 1
                }
            }

            // Calcula o valor hash para a próxima janela
            if (i < N - M) {
                t = (d * (t - txt.charAt(i) * h) + txt.charAt(i + M)) % q;
                // Garante que o valor do hash seja não-negativo
                if (t < 0)
                    t += q;
                instrucoes++; 
            }
        }
        return ans;
    }

    // Função para imprimir a tabela de resultados
    public static void printTable(String[][] rows) {
        String[] headers = {"Algoritmo", "Padrão", "Texto", "Iterações", "Instruções", "Tempo (ms)"};
        int[] widths = {15, 10, 25, 12, 13, 12};

        // Imprime os cabeçalhos da tabela
        for (int i = 0; i < headers.length; i++) {
            System.out.printf("%-" + widths[i] + "s", headers[i]);
            if (i < headers.length - 1) System.out.print(" | ");
        }
        System.out.println();

        // Imprime as divisões entre as colunas
        for (int width : widths) {
            System.out.print("-".repeat(width));
            System.out.print("-+-");
        }
        System.out.println();

        // Imprime os dados das linhas da tabela
        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                System.out.printf("%-" + widths[i] + "s", row[i]);
                if (i < row.length - 1) System.out.print(" | ");
            }
            System.out.println();
        }
    }
}
