import java.util.Random;

public class PatternMatchingKMP {
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
        KMPSearch(inputPequeno, pattern);
        long tempoFinal = System.nanoTime();
        double tempoTotal = (tempoFinal - tempoInicial) / 1_000_000.0;
        resultados[0] = new String[]{
            "Pattern Matching KMP", pattern, inputPequeno,
            String.valueOf(iteracoes), String.valueOf(instrucoes), String.valueOf(tempoTotal)
        };

        // Teste com entrada grande
        resetContagem();
        tempoInicial = System.nanoTime();
        KMPSearch(inputGrande, pattern);
        tempoFinal = System.nanoTime();
        tempoTotal = (tempoFinal - tempoInicial) / 1_000_000.0;
        resultados[1] = new String[]{
            "Pattern Matching KMP", pattern, "String aleatoria de 500.000 caracteres + padrão",
            String.valueOf(iteracoes), String.valueOf(instrucoes), String.valueOf(tempoTotal)
        };
        System.out.println();
        printTable(resultados);
        System.out.println();
    }

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

    static void KMPSearch(String txt, String pat) {
        int M = pat.length();
        int N = txt.length();

        int lps[] = new int[M];
        int j = 0;

        computeLPSArray(pat, M, lps);

        int i = 0;
        while (i < N) {
            iteracoes++;

            instrucoes++;
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
                instrucoes += 2;
            }

            instrucoes++;
            if (j == M) {
                System.out.println("Padrão encontrado no indice: " + (i - j));
                j = lps[j - 1];
                instrucoes++;
            }

            instrucoes++;
            if (i < N && j < M && pat.charAt(j) != txt.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                    instrucoes++;
                } else {
                    i = i + 1;
                    instrucoes++;
                }
            }
        }
    }

    static void computeLPSArray(String pat, int M, int lps[]) {
        int len = 0;
        int i = 1;
        lps[0] = 0;
        instrucoes++;

        while (i < M) {
            iteracoes++;
            instrucoes++;
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
                instrucoes += 3;
            } else {
                instrucoes++;
                if (len != 0) {
                    len = lps[len - 1];
                    instrucoes++;
                } else {
                    lps[i] = 0;
                    i++;
                    instrucoes += 2;
                }
            }
        }
    }

    public static void printTable(String[][] rows) {
        String[] headers = {"Algoritmo", "Padrão", "Texto", "Iterações", "Instruções", "Tempo (ms)"};
        int[] widths = {20, 8, 50, 12, 13, 12};

        for (int i = 0; i < headers.length; i++) {
            System.out.printf("%-" + widths[i] + "s", headers[i]);
            if (i < headers.length - 1) System.out.print(" | ");
        }
        System.out.println();

        for (int width : widths) {
            System.out.print("-".repeat(width));
            System.out.print("-+-");
        }
        System.out.println();

        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                System.out.printf("%-" + widths[i] + "s", row[i]);
                if (i < row.length - 1) System.out.print(" | ");
            }
            System.out.println();
        }
    }
}