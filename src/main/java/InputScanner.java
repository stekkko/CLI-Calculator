import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class InputScanner {
    private BufferedReader br;
    private StringTokenizer st;

    InputScanner() throws IOException {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public InputScanner(String fileTitle) throws IOException {
        this.br = new BufferedReader(new FileReader(fileTitle));
    }

    public String nextLine() throws IOException {
        String s = br.readLine();
        return s == null ? "" : s;
    }

    String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            String s = br.readLine();
            if (s == null || s.equals("")) {
                return "";
            }
            st = new StringTokenizer(s);
        }
        return st.nextToken();
    }

    Integer nextInt() throws IOException {
        return Integer.parseInt(this.next());
    }

    Long nextLong() throws IOException {
        return Long.parseLong(this.next());
    }

    public Double nextDouble() throws IOException {
        return Double.parseDouble(this.next());
    }

    void close() throws IOException {
        this.br.close();
    }
}

