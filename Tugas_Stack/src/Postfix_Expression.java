import java.util.Scanner;
import java.util.Stack;

public class Postfix_Expression {
    static int prec(char c) {
        if (c == '^')
            return 3;
        else if (c == '/' || c == '*')
            return 2;
        else if (c == '+' || c == '-')
            return 1;
        else
            return -1;
    }

    static boolean isRigthAssociative(char c) {
        return c =='^';
    }
    
    public static String infixToPostFix(String s) {
        Stack<Character> st = new Stack<>();
        StringBuilder res = new StringBuilder();
        
        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);
            
            if ((c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c >= '0' && c <= '9'))
                res.append(c);
                
            else if (c == '(')
                st.push ('(');

            else if (c == ')') {
                while (!st.isEmpty() && st.peek() != '(') {
                    res.append(st.pop());
                }
                st.pop();
            }

            else {
                while (!st.isEmpty() && st.peek() != '(' &&
                        (prec(st.peek()) > prec(c) ||
                        (prec(st.peek()) == prec(c) && !isRigthAssociative(c)))) {
                            res.append(st.pop());
                        }
                        st.push(c);
            }
        }
        while (!st.isEmpty()) {
        res.append(st.pop());
        }

        return res.toString();
    }

    static int pembulatanKeBawah(int a, int b) {
        if (a * b < 0 && a % b != 0)
            return (a / b) - 1;
        return a / b;
    }
    public static void main(String[] args) {

        try (Scanner input = new Scanner(System.in)) {
            System.out.print("Masukkan ekspresi infix: ");
            String infix = input.nextLine();

            // konversi infix ke postfix
            String postfix = Postfix_Expression.infixToPostFix(infix);

            System.out.println("\nPostfix Expression: " + postfix);

            // ubah postfix menjadi token array
            String[] tokens = postfix.split("");

            System.out.println("\nProses Evaluasi Stack:");

            Stack<Integer> st = new Stack<>();

            for (String token : tokens) {

                if (Character.isDigit(token.charAt(0))) {

                    st.push(Integer.parseInt(token));
                    System.out.println("Push " + token + " -> Stack: " + st);

                } else {

                    int b = st.pop();
                    int a = st.pop();

                    int hasil = 0;

                    if (token.equals("+")) hasil = a + b;
                    else if (token.equals("-")) hasil = a - b;
                    else if (token.equals("*")) hasil = a * b;
                    else if (token.equals("/")) hasil = Postfix_Expression.pembulatanKeBawah(a,b);
                    else if (token.equals("^")) hasil = (int)Math.pow(a,b);

                    st.push(hasil);

                    System.out.println(
                        "Pop " + a + " dan " + b +
                        " → " + a + " " + token + " " + b +
                        " = " + hasil +
                        " -> Push -> Stack: " + st
                    );
                }
            }

            int result = st.pop();

            System.out.println("\nHasil akhir = " + result);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}