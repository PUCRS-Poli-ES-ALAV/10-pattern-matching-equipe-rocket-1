import java.util.ArrayList;
import java.util.Random;

public class PatternMatchingHashHorner {
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
        search(pattern, inputPequeno);
        long tempoFinal = System.nanoTime();
        double tempoTotal = (tempoFinal - tempoInicial) / 1_000_000.0;
        resultados[0] = new String[]{
            "Pattern Matching sem rolling", pattern, inputPequeno,
            String.valueOf(iteracoes), String.valueOf(instrucoes), String.valueOf(tempoTotal)
        };

        // Teste com entrada grande
        resetContagem();
        tempoInicial = System.nanoTime();
        search(pattern, inputGrande);
        tempoFinal = System.nanoTime();
        tempoTotal = (tempoFinal - tempoInicial) / 1_000_000.0;
        resultados[1] = new String[]{
            "Pattern Matching sem rolling", pattern, "String aleatoria de 500.000 caracteres + padrão",
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

    public static ArrayList<Integer> search(String pat, String txt) {
        int d = 256; // ASCII
        int q = 101; // Número primo para módulo
        int M = pat.length();
        int N = txt.length();
        int p = 0; // hash do padrão
        ArrayList<Integer> ans = new ArrayList<>();

        // Calcula o hash do padrão
        for (int i = 0; i < M; i++) {
            p = (d * p + pat.charAt(i)) % q;
            instrucoes++;
        }

        // Percorre cada janela no texto e calcula o hash do zero (sem rolling hash)
        for (int i = 0; i <= N - M; i++) {
            iteracoes++;

            int t = 0; // hash da janela atual
            for (int j = 0; j < M; j++) {
                t = (d * t + txt.charAt(i + j)) % q;
                instrucoes++;
            }

            // Compara os hashes
            if (p == t) {
                boolean match = true;
                for (int j = 0; j < M; j++) {
                    instrucoes++;
                    if (txt.charAt(i + j) != pat.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    ans.add(i + 1); // índice baseado em 1
                }
            }
        }

        return ans;
    }

    public static void printTable(String[][] rows) {
        String[] headers = {"Algoritmo", "Padrão", "Texto", "Iterações", "Instruções", "Tempo (ms)"};
        int[] widths = {30, 8, 50, 12, 13, 12};

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