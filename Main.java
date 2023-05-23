import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

// 运算符抽象类
abstract class Operator {
    abstract double calculate(double a, double b);
}

// 加法运算符
class AddOperator extends Operator {
    @Override
    double calculate(double a, double b) {
        return a + b;
    }
}

// 减法运算符
class SubOperator extends Operator {
    @Override
    double calculate(double a, double b) {
        return a - b;
    }
}

// 乘法运算符
class MulOperator extends Operator {
    @Override
    double calculate(double a, double b) {
        return a * b;
    }
}

// 除法运算符
class DivOperator extends Operator {
    @Override
    double calculate(double a, double b) {
        return a / b;
    }
}

// 命令抽象类
abstract class Command {
    double num1, num2, res;
    Operator operator;

    abstract void execute();
    abstract void undo();
    abstract void redo();
}

// 加法命令类
class AddCommand extends Command {
    AddCommand(double n1, double n2) {
        num1 = n1;
        num2 = n2;
        operator = new AddOperator();
    }

    @Override
    void execute() {
        res = operator.calculate(num1, num2);
        System.out.printf("%.2f + %.2f = %.2f\n", num1, num2, res);
    }

    @Override
    void undo() {
        System.out.printf("Undo %.2f + %.2f = %.2f\n", num1, num2, res);
    }

    @Override
    void redo() {
        System.out.printf("Redo %.2f + %.2f = %.2f\n", num1, num2, res);
    }
}

// 减法命令类
class SubCommand extends Command {
    SubCommand(double n1, double n2) {
        num1 = n1;
        num2 = n2;
        operator = new SubOperator();
    }

    @Override
    void execute() {
        res = operator.calculate(num1, num2);
        System.out.printf("%.2f - %.2f = %.2f\n", num1, num2, res);
    }

    @Override
    void undo() {
        System.out.printf("Undo %.2f - %.2f = %.2f\n", num1, num2, res);
    }

    @Override
    void redo() {
        System.out.printf("Redo %.2f - %.2f = %.2f\n", num1, num2, res);
    }
}

// 乘法命令类
class MulCommand extends Command {
    MulCommand(double n1, double n2) {
        num1 = n1;
        num2 = n2;
        operator = new MulOperator();
    }

    @Override
    void execute() {
        res = operator.calculate(num1, num2);
        System.out.printf("%.2f * %.2f = %.2f\n", num1, num2, res);
    }

    @Override
    void undo() {
        System.out.printf("Undo %.2f * %.2f = %.2f\n", num1, num2, res);
    }

    @Override
    void redo() {
        System.out.printf("Redo %.2f * %.2f = %.2f\n", num1, num2, res);
    }
}

// 除法命令类
class DivCommand extends Command {
    DivCommand(double n1, double n2) {
        num1 = n1;
        num2 = n2;
        operator = new DivOperator();
    }

    @Override
    void execute() {
        res = operator.calculate(num1, num2);
        System.out.printf("%.2f / %.2f = %.2f\n", num1, num2, res);
    }

    @Override
    void undo() {
        System.out.printf("Undo %.2f / %.2f = %.2f\n", num1, num2, res);
    }

    @Override
    void redo() {
        System.out.printf("Redo %.2f / %.2f = %.2f\n", num1, num2, res);
    }
}

// 计算器类
class Calculator {
    Deque<Command> undoStack = new ArrayDeque<>();
    Deque<Command> redoStack = new ArrayDeque<>();


    // 执行加法操作
    void add(double num1, double num2) {
        Command cmd = new AddCommand(num1, num2);
        cmd.execute();
        undoStack.offerLast(cmd);
        redoStack.clear();
    }

    // 执行减法操作
    void sub(double num1, double num2) {
        Command cmd = new SubCommand(num1, num2);
        cmd.execute();
        undoStack.offerLast(cmd);
        redoStack.clear();
    }

    // 执行乘法操作
    void mul(double num1, double num2) {
        Command cmd = new MulCommand(num1, num2);
        cmd.execute();
        undoStack.offerLast(cmd);
        redoStack.clear();
    }

    // 执行除法操作
    void div(double num1, double num2) {
        Command cmd = new DivCommand(num1, num2);
        cmd.execute();
        undoStack.offerLast(cmd);
        redoStack.clear();
    }

    // 撤销操作
    void undo() {
        if (!undoStack.isEmpty()) {
            // 移除栈顶元素
            Command cmd = undoStack.pollLast();
            cmd.undo();
            // 打印当前栈顶元素
            if(!undoStack.isEmpty()){
                undoStack.peekLast().execute();
            }
            // 推到重做堆
            redoStack.offerLast(cmd);
        } else {
            System.out.println("Nothing to undo.");
        }
    }

    // 重做操作
    void redo() {
        if (!redoStack.isEmpty()) {
            Command cmd = redoStack.pollLast();
            cmd.redo();
            undoStack.offerLast(cmd);
        } else {
            System.out.println("Nothing to redo.");
        }
    }
}

// 测试
public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
//         3.00 + 4.00 = 7.00
        calculator.add(3, 4);

//         7.00 * 2.00 = 14.00
        calculator.mul(7, 2);

//        14.00 / 3.00 = 4.67
        calculator.div(14, 3);

//        Undo 14.00 / 3.00 = 4.67
//        7.00 * 2.00 = 14.00
        calculator.undo();

//        Undo 7.00 * 2.00 = 14.00
//        3.00 + 4.00 = 7.00
        calculator.undo();

//        Undo 3.00 + 4.00 = 7.00
        calculator.undo();

//        Redo 3.00 + 4.00 = 7.00
        calculator.redo();

//        5.00 + 2.00 = 7.00
        calculator.add(5, 2);

//        Nothing to redo.
        calculator.redo();

//        Nothing to redo.
        calculator.redo();
    }
}
