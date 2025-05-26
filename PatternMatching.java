public class PatternMatching {
    public static void main(String[] args) {
        String input = "ABCDCBDCBDACBDABDCBADF";
        String pattern = "ADF";

        int pos = encontrarPrimeiraOcorrencia(input, pattern);

        System.out.println("String de entrada (s1): " + input);
        System.out.println("Padrão a ser buscado (s2): " + pattern);
        System.out.println("Posição da primeira ocorrência: " + pos);
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
            }
            if (j == m) {
                return i;
            }
        }
        return -1;
    }
}
