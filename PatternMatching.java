public class PatternMatching {
    static long instrucoes = 0;
    static long iteracoes = 0;

    public static void main(String[] args) {
        String input = "ABCDCBDCBDACBDABDCBADF";
        String pattern = "ADF";

        long tempoInicial = System.nanoTime();
        encontrarPrimeiraOcorrencia(input, pattern);
        long tempoFinal = System.nanoTime();
        double tempoTotal = (tempoFinal - tempoInicial) / 1_000_000.0; 

        String[][] resultados = {
            {"Pattern Matching", pattern, input, String.valueOf(iteracoes), String.valueOf(instrucoes), String.valueOf(tempoTotal)}
        };
        printTable(resultados); 
    }

    /*
     * Encontra a posição da primeira ocorrência de um padrão em uma string.
     * 
     * @param input A string onde o padrão será buscado.
     * @param pattern O padrão a ser buscado na string.
     * @return A posição da primeira ocorrência do padrão na string, ou -1 se não encontrado.
     */
    public static int encontrarPrimeiraOcorrencia(String input, String pattern) {
        int n = input.length();
        int m = pattern.length();
        for (int i = 0; i <= n - m; i++) {
            int j = 0;
            while (j < m && input.charAt(i + j) == pattern.charAt(j)) {
                j++;
                iteracoes++;  // Contando iteração dentro do while
                instrucoes++; // Instrução de comparação
            }
            if (j == m) {
                instrucoes++;  // Instrução de comparação
                return i;
            }
            iteracoes++;  // Contando iteração dentro do for
            instrucoes++; // Instrução de comparação
        }
        return -1;
    }

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
