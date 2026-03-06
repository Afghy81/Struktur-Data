class PostfixExpression:

    @staticmethod
    def prec(c):
        if c == '^':
            return 3
        elif c in ('*', '/'):
            return 2
        elif c in ('+', '-'):
            return 1
        return -1

    @staticmethod
    def is_right_associative(c):
        return c == '^'

    @staticmethod
    def infix_to_postfix(expr):

        stack = []
        output = []
        number = ""

        for c in expr:

            # membentuk multi digit number
            if c.isdigit():
                number += c
                continue
            else:
                if number:
                    output.append(number)
                    number = ""

            if c == ' ':
                continue

            elif c == '(':
                stack.append(c)

            elif c == ')':
                while stack and stack[-1] != '(':
                    output.append(stack.pop())
                stack.pop()

            else:
                while (stack and stack[-1] != '(' and
                       (PostfixExpression.prec(stack[-1]) > PostfixExpression.prec(c) or
                        (PostfixExpression.prec(stack[-1]) == PostfixExpression.prec(c) and
                         not PostfixExpression.is_right_associative(c)))):

                    output.append(stack.pop())

                stack.append(c)

        if number:
            output.append(number)

        while stack:
            output.append(stack.pop())

        return output


    @staticmethod
    def floor_div(a, b):
        if a * b < 0 and a % b != 0:
            return (a // b) - 1
        return a // b


    @staticmethod
    def evaluate_postfix(tokens):

        stack = []

        print("\nProses Evaluasi Stack\n")

        print(f"{'Token':<8}{'Stack':<20}{'Aksi'}")
        print("-"*40)

        for token in tokens:

            if token.lstrip('-').isdigit():

                stack.append(int(token))
                print(f"{token:<8}{str(stack):<20}Push")

            else:

                operand2 = stack.pop()
                operand1 = stack.pop()

                if token == '+':
                    result = operand1 + operand2
                elif token == '-':
                    result = operand1 - operand2
                elif token == '*':
                    result = operand1 * operand2
                elif token == '/':
                    result = PostfixExpression.floor_div(operand1, operand2)
                elif token == '^':
                    result = operand1 ** operand2

                stack.append(result)

                print(f"{token:<8}{str(stack):<20}Pop {operand1},{operand2} → {operand1} {token} {operand2} = {result}")

        return stack.pop()


# ========================
# MAIN PROGRAM
# ========================

expr = input("Masukkan ekspresi infix: ")

postfix = PostfixExpression.infix_to_postfix(expr)

print("\nPostfix Expression:")
print(" ".join(postfix))

result = PostfixExpression.evaluate_postfix(postfix)

print("\nHasil akhir =", result)