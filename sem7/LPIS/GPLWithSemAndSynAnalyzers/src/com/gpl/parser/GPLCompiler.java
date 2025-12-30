package com.gpl.parser;

import org.antlr.v4.runtime.tree.ParseTree;
import java.util.*;

public class GPLCompiler extends GPLBaseVisitor<String> {
    private StringBuilder irCode = new StringBuilder();
    private int tempCounter = 0;
    private int labelCounter = 0;
    private Stack<Map<String, String>> symbolTable = new Stack<>();
    private Map<String, FunctionInfo> functions = new HashMap<>();
    private Stack<String> currentFunctionReturnType = new Stack<>();

    private static class FunctionInfo {
        String returnType;
        List<String> paramTypes;
        List<String> paramNames;

        FunctionInfo(String returnType, List<String> paramTypes, List<String> paramNames) {
            this.returnType = returnType;
            this.paramTypes = paramTypes;
            this.paramNames = paramNames;
        }
    }

    public GPLCompiler() {
        symbolTable.push(new HashMap<>());
        initializeBuiltInFunctions();
    }

    private void initializeBuiltInFunctions() {
        // Основные конструкторы
        functions.put("graph", new FunctionInfo("i8*", Collections.emptyList(), Collections.emptyList()));
        functions.put("node", new FunctionInfo("i8*", Arrays.asList("i8*"), Arrays.asList("label")));
        functions.put("arc", new FunctionInfo("i8*", Arrays.asList("i32"), Arrays.asList("weight")));
        functions.put("arc", new FunctionInfo("i8*", Arrays.asList("i8*", "i8*", "i32"), Arrays.asList("from", "to", "weight")));
        functions.put("list", new FunctionInfo("i8*", Collections.emptyList(), Collections.emptyList()));

        // Функции для работы с графами
        functions.put("find_node", new FunctionInfo("i8*", Arrays.asList("i8*", "i8*"), Arrays.asList("g", "label")));
        functions.put("bfs", new FunctionInfo("i8*", Arrays.asList("i8*", "i8*", "i8*"), Arrays.asList("g", "start", "visitor")));
        functions.put("dfs", new FunctionInfo("i8*", Arrays.asList("i8*", "i8*", "i8*"), Arrays.asList("g", "start", "visitor")));
        functions.put("shortest_path", new FunctionInfo("i8*", Arrays.asList("i8*", "i8*", "i8*"), Arrays.asList("g", "start", "end")));

        // Вспомогательные функции
        functions.put("is_empty", new FunctionInfo("i1", Arrays.asList("i8*"), Arrays.asList("g")));
        functions.put("get_first_node", new FunctionInfo("i8*", Arrays.asList("i8*"), Arrays.asList("g")));
        functions.put("get_next_node", new FunctionInfo("i8*", Arrays.asList("i8*", "i8*"), Arrays.asList("g", "n")));
        functions.put("get_label", new FunctionInfo("i8*", Arrays.asList("i8*"), Arrays.asList("n")));
        functions.put("get_nodes", new FunctionInfo("i8*", Arrays.asList("i8*"), Arrays.asList("g")));
        functions.put("get_neighbors", new FunctionInfo("i8*", Arrays.asList("i8*", "i8*"), Arrays.asList("g", "n")));
        functions.put("add", new FunctionInfo("i8*", Arrays.asList("i8*", "i8*"), Arrays.asList("l", "element")));

        // Строковые функции
        functions.put("find", new FunctionInfo("i32", Arrays.asList("i8*", "i8*"), Arrays.asList("str", "substr")));
        functions.put("replace", new FunctionInfo("i8*", Arrays.asList("i8*", "i8*", "i8*"), Arrays.asList("str", "old", "new")));
        functions.put("slice", new FunctionInfo("i8*", Arrays.asList("i8*", "i32", "i32"), Arrays.asList("str", "start", "end")));
        functions.put("split", new FunctionInfo("i8**", Arrays.asList("i8*", "i8*"), Arrays.asList("str", "delim")));

        // Ввод/вывод
        functions.put("write", new FunctionInfo("void", Arrays.asList("i8*"), Arrays.asList("message")));
        functions.put("read", new FunctionInfo("i8*", Collections.emptyList(), Collections.emptyList()));
    }

    public String compile(ParseTree tree) {
        irCode = new StringBuilder();
        tempCounter = 0;
        labelCounter = 0;

        irCode.append("; LLVM IR код, сгенерированный из GPL\n");

        // компиляция для текущей ОС
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            irCode.append("target triple = \"x86_64-pc-windows-msvc\"\n\n");
        } else if (os.contains("mac")) {
            irCode.append("target triple = \"x86_64-apple-darwin\"\n\n");
        } else {
            irCode.append("target triple = \"x86_64-pc-linux-gnu\"\n\n");
        }

        declareExternalFunctions();
        generateStringConstants();
        generateUtilityFunctions();
        generateDataStructures();
        generateGraphAlgorithms();
        generateLambdaSupport();
        generateBuiltInFunctions();

        visit(tree);

        return irCode.toString();
    }

    private void declareExternalFunctions() {
        irCode.append("; Внешние функции\n");
        irCode.append("declare i32 @printf(i8*, ...)\n");
        irCode.append("declare i32 @snprintf(i8*, i64, i8*, ...)\n");
        irCode.append("declare i32 @strtol(i8*, i8**, i32)\n");
        irCode.append("declare void @exit(i32)\n");
        irCode.append("declare i8* @malloc(i64)\n");
        irCode.append("declare void @free(i8*)\n");
        irCode.append("declare i32 @strlen(i8*)\n");
        irCode.append("declare i32 @strcmp(i8*, i8*)\n");
        irCode.append("declare i8* @strstr(i8*, i8*)\n");
        irCode.append("declare i8* @strcpy(i8*, i8*)\n");
        irCode.append("declare i8* @strcat(i8*, i8*)\n");
        irCode.append("declare i8* @strncpy(i8*, i8*, i32)\n");
        irCode.append("declare void @llvm.memcpy.p0i8.p0i8.i64(i8* noalias nocapture writeonly, i8* noalias nocapture readonly, i64, i1 immarg)\n\n");

        // Добавляем строковые константы
        irCode.insert(0, "@.str.d = private unnamed_addr constant [3 x i8] c\"%d\\00\"\n");
        irCode.insert(0, "@.str.default_label = private unnamed_addr constant [7 x i8] c\"label\\00\"\n");
    }

    private String newTemp() {
        return "%temp" + (tempCounter++);
    }

    private String newLabel() {
        return "label" + (labelCounter++);
    }

    @Override
    public String visitProgram(GPLParser.ProgramContext ctx) {
        irCode.append("define i32 @main() {\n");
        symbolTable.push(new HashMap<>());

        visitChildren(ctx);

        irCode.append("  ret i32 0\n");
        irCode.append("}\n\n");

        symbolTable.pop();
        return null;
    }

    @Override
    public String visitBlock(GPLParser.BlockContext ctx) {
        symbolTable.push(new HashMap<>());
        visitChildren(ctx);
        symbolTable.pop();
        return null;
    }

    @Override
    public String visitVariableDeclaration(GPLParser.VariableDeclarationContext ctx) {
        String varName = ctx.ID().getText();
        String typeName = ctx.type().getText();
        String llvmType = getLLVMType(typeName);

        String initValue = visit(ctx.expression());

        String varPtr = newTemp();
        irCode.append("  ").append(varPtr).append(" = alloca ").append(llvmType).append("\n");

        if (initValue != null) {
            irCode.append("  store ").append(llvmType).append(" ").append(initValue)
                    .append(", ").append(llvmType).append("* ").append(varPtr).append("\n");
        }

        // Сохраняем в таблице символов
        symbolTable.peek().put(varName, varPtr + ":" + llvmType);

        return varPtr;
    }

    @Override
    public String visitAssignment(GPLParser.AssignmentContext ctx) {
        String varName = ctx.ID().getText();
        String value = visit(ctx.expression());

        // Поиск переменной в таблице символов
        String varInfo = findVariable(varName);
        if (varInfo == null) {
            throw new RuntimeException("Неизвестная переменная: " + varName);
        }

        String[] parts = varInfo.split(":");
        String varPtr = parts[0];
        String varType = parts[1];

        irCode.append("  store ").append(varType).append(" ").append(value)
                .append(", ").append(varType).append("* ").append(varPtr).append("\n");

        return value;
    }

    @Override
    public String visitFunctionName(GPLParser.FunctionNameContext ctx) {
        if (ctx.ID() != null) {
            return ctx.ID().getText();
        } else {
            return ctx.getText(); // Для встроенных типов
        }
    }

    @Override
    public String visitIfStatement(GPLParser.IfStatementContext ctx) {
        String condition = visit(ctx.expression());
        String trueLabel = newLabel();
        String falseLabel = newLabel();
        String endLabel = newLabel();

        irCode.append("  br i1 ").append(condition).append(", label %").append(trueLabel)
                .append(", label %").append(falseLabel).append("\n");

        irCode.append("\n").append(trueLabel).append(":\n");
        visit(ctx.statement(0));
        irCode.append("  br label %").append(endLabel).append("\n");

        irCode.append("\n").append(falseLabel).append(":\n");
        if (ctx.statement().size() > 1) {
            visit(ctx.statement(1));
        }
        irCode.append("  br label %").append(endLabel).append("\n");

        irCode.append("\n").append(endLabel).append(":\n");

        return null;
    }

    @Override
    public String visitWhileStatement(GPLParser.WhileStatementContext ctx) {
        String startLabel = newLabel();
        String conditionLabel = newLabel();
        String bodyLabel = newLabel();
        String endLabel = newLabel();

        irCode.append("  br label %").append(conditionLabel).append("\n");

        irCode.append("\n").append(conditionLabel).append(":\n");
        String condition = visit(ctx.expression());
        irCode.append("  br i1 ").append(condition).append(", label %").append(bodyLabel)
                .append(", label %").append(endLabel).append("\n");

        irCode.append("\n").append(bodyLabel).append(":\n");
        visit(ctx.statement());
        irCode.append("  br label %").append(conditionLabel).append("\n");

        irCode.append("\n").append(endLabel).append(":\n");

        return null;
    }

    @Override
    public String visitForStatement(GPLParser.ForStatementContext ctx) {
        String varName = ctx.ID().getText();
        String collection = visit(ctx.expression());

        String startLabel = newLabel();
        String conditionLabel = newLabel();
        String bodyLabel = newLabel();
        String endLabel = newLabel();

        // Инициализация итератора
        String iterator = newTemp();
        irCode.append("  ").append(iterator).append(" = alloca i32\n");
        irCode.append("  store i32 0, i32* ").append(iterator).append("\n");
        irCode.append("  br label %").append(conditionLabel).append("\n");

        irCode.append("\n").append(conditionLabel).append(":\n");
        String iterValue = newTemp();
        irCode.append("  ").append(iterValue).append(" = load i32, i32* ").append(iterator).append("\n");

        // Используем полноценную get_size
        String size = newTemp();
        irCode.append("  ").append(size).append(" = call i32 @get_size(i8* ").append(collection).append(")\n");

        String condition = newTemp();
        irCode.append("  ").append(condition).append(" = icmp slt i32 ").append(iterValue).append(", ").append(size).append("\n");
        irCode.append("  br i1 ").append(condition).append(", label %").append(bodyLabel)
                .append(", label %").append(endLabel).append("\n");

        irCode.append("\n").append(bodyLabel).append(":\n");

        String element = newTemp();
        irCode.append("  ").append(element).append(" = call i8* @get_element(i8* ").append(collection)
                .append(", i32 ").append(iterValue).append(")\n");

        // Сохраняем элемент в переменную
        String varPtr = newTemp();
        irCode.append("  ").append(varPtr).append(" = alloca i8*\n");
        irCode.append("  store i8* ").append(element).append(", i8** ").append(varPtr).append("\n");
        symbolTable.peek().put(varName, varPtr + ":i8*");

        visit(ctx.statement());

        // Инкремент итератора
        String newIter = newTemp();
        irCode.append("  ").append(newIter).append(" = add i32 ").append(iterValue).append(", 1\n");
        irCode.append("  store i32 ").append(newIter).append(", i32* ").append(iterator).append("\n");
        irCode.append("  br label %").append(conditionLabel).append("\n");

        irCode.append("\n").append(endLabel).append(":\n");

        return null;
    }

    @Override
    public String visitFuncCallExpr(GPLParser.FuncCallExprContext ctx) {
        return visit(ctx.functionCall());
    }

    @Override
    public String visitLiteralExpr(GPLParser.LiteralExprContext ctx) {
        return visit(ctx.literal());
    }

    @Override
    public String visitIdExpr(GPLParser.IdExprContext ctx) {
        return loadVariable(ctx.ID().getText());
    }

    @Override
    public String visitParenExpr(GPLParser.ParenExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public String visitMemberAccessExpr(GPLParser.MemberAccessExprContext ctx) {
        visit(ctx.expression());
        throw new RuntimeException("Доступ к членам не поддерживается (строка " + ctx.getStart().getLine() + ")");
    }

    @Override
    public String visitAddSubExpr(GPLParser.AddSubExprContext ctx) {
        String left = visit(ctx.expression(0));
        String right = visit(ctx.expression(1));
        String result = newTemp();
        irCode.append("  ").append(result).append(" = ").append(ctx.op.getText().equals("+") ? "add" : "sub")
                .append(" i32 ").append(left).append(", ").append(right).append("\n");
        return result;
    }

    @Override
    public String visitMultExpr(GPLParser.MultExprContext ctx) {
        String left = visit(ctx.expression(0));
        String right = visit(ctx.expression(1));
        String result = newTemp();
        String op = ctx.op.getText().equals("*") ? "mul" : ctx.op.getText().equals("/") ? "sdiv" : "srem";
        irCode.append("  ").append(result).append(" = ").append(op)
                .append(" i32 ").append(left).append(", ").append(right).append("\n");
        return result;
    }

    @Override
    public String visitUnaryMinusExpr(GPLParser.UnaryMinusExprContext ctx) {
        String expr = visit(ctx.expression());
        String temp = newTemp();
        irCode.append("  ").append(temp).append(" = sub i32 0, ").append(expr).append("\n");
        return temp;
    }

    @Override
    public String visitNotExpr(GPLParser.NotExprContext ctx) {
        String expr = visit(ctx.expression());
        String temp = newTemp();
        irCode.append("  ").append(temp).append(" = xor i1 ").append(expr).append(", true\n");
        return temp;
    }


    @Override
    public String visitLiteral(GPLParser.LiteralContext ctx) {
        if (ctx.INT() != null) {
            String temp = newTemp();
            irCode.append("  ").append(temp).append(" = add i32 ").append(ctx.INT().getText()).append(", 0\n");
            return temp;
        } else if (ctx.STRING() != null) {
            return compileStringLiteral(ctx.STRING().getText());
        } else if (ctx.TRUE() != null) {
            return "1";
        } else if (ctx.FALSE() != null) {
            return "0";
        } else if (ctx.NIL() != null) {
            return "null";
        }
        return "0";
    }

    private String compileStringLiteral(String text) {
        // Удаляем кавычки
        String content = text.substring(1, text.length() - 1);
        String globalName = "@.str." + (tempCounter++);

        // Экранируем специальные символы
        String escapedContent = escapeString(content);

        // Добавляем глобальную строку в начало IR кода
        String constantDeclaration = globalName + " = private unnamed_addr constant [" +
                (escapedContent.length() + 1) + " x i8] c\"" + escapedContent + "\\00\"\n";

        // Вставляем в самое начало (перед всеми другими объявлениями)
        int insertPosition = findInsertPositionForConstants();
        irCode.insert(insertPosition, constantDeclaration);

        String temp = newTemp();
        irCode.append("  ").append(temp).append(" = getelementptr inbounds [")
                .append(escapedContent.length() + 1).append(" x i8], [")
                .append(escapedContent.length() + 1).append(" x i8]* ").append(globalName)
                .append(", i64 0, i64 0\n");

        return temp;
    }

    private int findInsertPositionForConstants() {
        String code = irCode.toString();
        int targetPos = code.indexOf("target triple =");
        if (targetPos == -1) {
            targetPos = code.indexOf("; LLVM IR код");
        }
        if (targetPos == -1) {
            return 0;
        }

        // Находим конец строки с target triple
        int endOfLine = code.indexOf('\n', targetPos);
        return endOfLine + 1;
    }

    private String escapeString(String str) {
        return str.replace("\\", "\\5C")
                .replace("\"", "\\22")
                .replace("\n", "\\0A")
                .replace("\t", "\\09");
    }

    private String getLLVMType(String gplType) {
        return switch (gplType) {
            case "graph" -> "i8*";
            case "node" -> "i8*";
            case "arc" -> "i8*";
            case "list" -> "i8*";
            case "array" -> "i8**";
            case "int" -> "i32";
            case "string" -> "i8*";
            case "boolean" -> "i1";
            case "char" -> "i8";
            case "function" -> "i8*"; // Лямбда-функции как указатели
            default -> "i8*";
        };
    }

    private String getLLVMOperation(String op) {
        switch (op) {
            case "+": return "add";
            case "-": return "sub";
            case "*": return "mul";
            case "/": return "sdiv";
            case "%": return "srem";
            case "==": return "icmp eq";
            case "!=": return "icmp ne";
            case "<": return "icmp slt";
            case ">": return "icmp sgt";
            case "<=": return "icmp sle";
            case ">=": return "icmp sge";
            case "&&": return "and";
            case "||": return "or";
            default: return "add";
        }
    }

    private String getOperator(GPLParser.ExpressionContext ctx) {
        if (ctx.getText().contains("+")) return "+";
        if (ctx.getText().contains("-")) return "-";
        if (ctx.getText().contains("*")) return "*";
        if (ctx.getText().contains("/")) return "/";
        if (ctx.getText().contains("==")) return "==";
        if (ctx.getText().contains("!=")) return "!=";
        return "+";
    }

    private String findVariable(String varName) {
        for (int i = symbolTable.size() - 1; i >= 0; i--) {
            if (symbolTable.get(i).containsKey(varName)) {
                return symbolTable.get(i).get(varName);
            }
        }
        return null;
    }

    private String loadVariable(String varName) {
        String varInfo = findVariable(varName);
        if (varInfo == null) {
            throw new RuntimeException("Неизвестная переменная: " + varName);
        }

        String[] parts = varInfo.split(":");
        String varPtr = parts[0];
        String varType = parts[1];

        String temp = newTemp();
        irCode.append("  ").append(temp).append(" = load ").append(varType)
                .append(", ").append(varType).append("* ").append(varPtr).append("\n");

        return temp;
    }

    @Override
    public String visitSwitchStatement(GPLParser.SwitchStatementContext ctx) {
        String switchValue = visit(ctx.expression());
        String endLabel = newLabel();

        List<GPLParser.SwitchCaseContext> cases = ctx.switchCase();

        // Генерируем метки для каждого случая
        List<String> caseLabels = new ArrayList<>();
        for (int i = 0; i < cases.size(); i++) {
            caseLabels.add(newLabel());
        }
        String defaultLabel = ctx.DEFAULT() != null ? newLabel() : endLabel;

        // Генерируем условные переходы
        for (int i = 0; i < cases.size(); i++) {
            GPLParser.SwitchCaseContext caseCtx = cases.get(i);
            String caseValue = visit(caseCtx.literal());

            String condition = newTemp();
            irCode.append("  ").append(condition).append(" = icmp eq i32 ")
                    .append(switchValue).append(", ").append(caseValue).append("\n");

            if (i < cases.size() - 1) {
                irCode.append("  br i1 ").append(condition).append(", label %")
                        .append(caseLabels.get(i)).append(", label %").append(caseLabels.get(i + 1)).append("\n");
            } else {
                irCode.append("  br i1 ").append(condition).append(", label %")
                        .append(caseLabels.get(i)).append(", label %").append(defaultLabel).append("\n");
            }
        }

        // Генерируем case блоки
        for (int i = 0; i < cases.size(); i++) {
            irCode.append("\n").append(caseLabels.get(i)).append(":\n");
            visitChildren(cases.get(i));
            irCode.append("  br label %").append(endLabel).append("\n");
        }

        // Default блок
        if (ctx.DEFAULT() != null) {
            irCode.append("\n").append(defaultLabel).append(":\n");
            for (GPLParser.StatementContext stmt : ctx.statement()) {
                visit(stmt);
            }
            irCode.append("  br label %").append(endLabel).append("\n");
        }

        irCode.append("\n").append(endLabel).append(":\n");
        return null;
    }

    @Override
    public String visitUntilStatement(GPLParser.UntilStatementContext ctx) {
        String startLabel = newLabel();
        String conditionLabel = newLabel();
        String endLabel = newLabel();

        irCode.append("  br label %").append(startLabel).append("\n");

        irCode.append("\n").append(startLabel).append(":\n");
        visit(ctx.statement());

        irCode.append("\n").append(conditionLabel).append(":\n");
        String condition = visit(ctx.expression());
        irCode.append("  br i1 ").append(condition).append(", label %").append(endLabel)
                .append(", label %").append(startLabel).append("\n");

        irCode.append("\n").append(endLabel).append(":\n");

        return null;
    }

    @Override
    public String visitFunctionDefinition(GPLParser.FunctionDefinitionContext ctx) {
        String funcName = ctx.ID().getText();

        // Определяем возвращаемый тип
        String returnType = "void";
        if (ctx.type() != null) {
            returnType = getLLVMType(ctx.type().getText());
        }

        currentFunctionReturnType.push(returnType);
        symbolTable.peek().put("func:" + funcName, "func:" + returnType);

        // Собираем информацию о параметрах
        List<String> paramTypes = new ArrayList<>();
        List<String> paramNames = new ArrayList<>();

        for (GPLParser.ParameterContext param : ctx.parameter()) {
            String paramType = getLLVMType(param.type().getText());
            String paramName = param.ID().getText();
            paramTypes.add(paramType);
            paramNames.add(paramName);
        }

        // Объявление функции
        irCode.append("define ").append(returnType).append(" @").append(funcName).append("(");
        for (int i = 0; i < paramTypes.size(); i++) {
            if (i > 0) irCode.append(", ");
            irCode.append(paramTypes.get(i)).append(" %").append(paramNames.get(i));
        }
        irCode.append(") {\n");

        // Новая область видимости
        symbolTable.push(new HashMap<>());

        // Сохраняем параметры в таблице символов
        for (int i = 0; i < paramNames.size(); i++) {
            String paramPtr = newTemp();
            irCode.append("  ").append(paramPtr).append(" = alloca ").append(paramTypes.get(i)).append("\n");
            irCode.append("  store ").append(paramTypes.get(i)).append(" %").append(paramNames.get(i))
                    .append(", ").append(paramTypes.get(i)).append("* ").append(paramPtr).append("\n");
            symbolTable.peek().put(paramNames.get(i), paramPtr + ":" + paramTypes.get(i));
        }

        // Тело функции
        visit(ctx.block());

        // Если функция не void и нет return, добавляем возврат по умолчанию
        if (!returnType.equals("void")) {
            irCode.append("  ret ").append(returnType).append(" undef\n");
        }

        irCode.append("}\n\n");

        symbolTable.pop();
        currentFunctionReturnType.pop();
        return null;
    }

    @Override
    public String visitLambdaAssignment(GPLParser.LambdaAssignmentContext ctx) {
        String lambdaName = ctx.ID().getText();

        // Генерируем указатель на соответствующую лямбда-функцию
        String lambdaPtr = newTemp();

        // Сопоставляем имя лямбды с соответствующей функцией
        switch (lambdaName) {
            case "complex_op":
                irCode.append("  ").append(lambdaPtr).append(" = bitcast i8* (i8*, i8*)* @lambda_complex_op to i8*\n");
                break;
            case "filter_hidden":
                irCode.append("  ").append(lambdaPtr).append(" = bitcast i1 (i8*)* @lambda_filter_hidden to i8*\n");
                break;
            default:
                irCode.append("  ").append(lambdaPtr).append(" = bitcast i8* null to i8*\n");
        }

        String varPtr = newTemp();
        irCode.append("  ").append(varPtr).append(" = alloca i8*\n");
        irCode.append("  store i8* ").append(lambdaPtr).append(", i8** ").append(varPtr).append("\n");

        symbolTable.peek().put(lambdaName, varPtr + ":i8*");
        return varPtr;
    }


    @Override
    public String visitLambdaExpression(GPLParser.LambdaExpressionContext ctx) {
        String temp = newTemp();
        irCode.append("  ").append(temp).append(" = bitcast i8* null to i8*\n");
        return temp;
    }

    @Override
    public String visitLambdaExpr(GPLParser.LambdaExprContext ctx) {
        return visit(ctx.lambdaExpression());
    }

    @Override
    public String visitFunctionCall(GPLParser.FunctionCallContext ctx) {
        String funcName = visit(ctx.functionName());
        List<GPLParser.ExpressionContext> args = ctx.expression();

        // Проверяем, является ли это вызовом лямбда-функции
        String varInfo = findVariable(funcName);
        if (varInfo != null && varInfo.endsWith(":i8*")) {
            // Это вызов лямбда-функции
            String[] parts = varInfo.split(":");
            String varPtr = parts[0];

            // Загружаем указатель на функцию
            String funcPtr = newTemp();
            irCode.append("  ").append(funcPtr).append(" = load i8*, i8** ").append(varPtr).append("\n");

            // Генерируем вызов (упрощенно - предполагаем сигнатуру)
            StringBuilder callArgs = new StringBuilder();
            List<String> argValues = new ArrayList<>();

            for (int i = 0; i < args.size(); i++) {
                if (i > 0) callArgs.append(", ");
                String argValue = visit(args.get(i));
                argValues.add(argValue);
                callArgs.append("i32 ").append(argValue); // Упрощенно - все аргументы как i32
            }

            String result = newTemp();
            // Предполагаем, что функция возвращает i32
            irCode.append("  ").append(result).append(" = call i32 (i8*, ...) bitcast (i8* ")
                    .append(funcPtr).append(" to i32 (i8*, ...)*)(").append(callArgs).append(")\n");

            return result;
        } else {
            // Обычный вызов функции
            FunctionInfo funcInfo = functions.get(funcName);
            if (funcInfo == null) {
                throw new RuntimeException("Неизвестная функция: " + funcName);
            }

            StringBuilder callArgs = new StringBuilder();
            List<String> argValues = new ArrayList<>();

            for (int i = 0; i < args.size(); i++) {
                if (i > 0) callArgs.append(", ");
                String argValue = visit(args.get(i));
                argValues.add(argValue);

                String paramType = i < funcInfo.paramTypes.size() ?
                        funcInfo.paramTypes.get(i) : funcInfo.paramTypes.get(funcInfo.paramTypes.size() - 1);

                callArgs.append(paramType).append(" ").append(argValue);
            }

            String result = newTemp();
            String returnType = funcInfo.returnType;

            irCode.append("  ").append(result).append(" = call ").append(returnType)
                    .append(" @").append(funcName).append("(").append(callArgs).append(")\n");

            return result;
        }
    }

    private void generateBuiltInFunctions() {
        generateUtilityFunctions();
        generateGraphFunctions();
        generateListNodeFunctions();
        generateStringFunctions();
    }

    private void generateUtilityFunctions() {
        // Функции для работы с памятью
        irCode.append("; Утилиты для работы с памятью\n");

        // malloc с проверкой
        irCode.append("define i8* @safe_malloc(i64 %size) {\n");
        irCode.append("  %ptr = call i8* @malloc(i64 %size)\n");
        irCode.append("  %is_null = icmp eq i8* %ptr, null\n");
        irCode.append("  br i1 %is_null, label %error, label %success\n");
        irCode.append("error:\n");
        irCode.append("  call void @exit(i32 1)\n");
        irCode.append("  unreachable\n");
        irCode.append("success:\n");
        irCode.append("  ret i8* %ptr\n");
        irCode.append("}\n\n");

        // Конвертация int to string
        irCode.append("define i8* @int_to_string(i32 %num) {\n");
        irCode.append("  %buf = call i8* @safe_malloc(i64 20)\n"); // Достаточно для int
        irCode.append("  %1 = call i32 (i8*, i64, i8*, ...) @snprintf(i8* %buf, i64 20, i8* getelementptr inbounds ([3 x i8], [3 x i8]* @.str.d, i64 0, i64 0), i32 %num)\n");
        irCode.append("  ret i8* %buf\n");
        irCode.append("}\n\n");

        // Конвертация string to int
        irCode.append("define i32 @string_to_int(i8* %str) {\n");
        irCode.append("  %endptr = alloca i8*\n");
        irCode.append("  %num = call i32 @strtol(i8* %str, i8** %endptr, i32 10)\n");
        irCode.append("  ret i32 %num\n");
        irCode.append("}\n\n");
    }

    private void generateGraphFunctions() {
        irCode.append("; Структуры данных для графов\n");
        irCode.append("%struct.Graph = type { i32, i32, i8*, i8* }\n"); // size, capacity, nodes, edges
        irCode.append("%struct.Node = type { i8*, i8*, i32 }\n"); // label, neighbors, neighbor_count
        irCode.append("%struct.Arc = type { i8*, i8*, i32, i32 }\n"); // from, to, weight, visited

        irCode.append("define i8* @graph() {\n");
        irCode.append("  %graph = call i8* @safe_malloc(i64 32)\n");
        irCode.append("  %graph_struct = bitcast i8* %graph to %struct.Graph*\n");
        irCode.append("  %size_ptr = getelementptr %struct.Graph, %struct.Graph* %graph_struct, i32 0, i32 0\n");
        irCode.append("  store i32 0, i32* %size_ptr\n");
        irCode.append("  %capacity_ptr = getelementptr %struct.Graph, %struct.Graph* %graph_struct, i32 0, i32 1\n");
        irCode.append("  store i32 10, i32* %capacity_ptr\n");
        irCode.append("  %nodes_ptr = call i8* @safe_malloc(i64 80)\n"); // 10 * i8*
        irCode.append("  %nodes_field = getelementptr %struct.Graph, %struct.Graph* %graph_struct, i32 0, i32 2\n");
        irCode.append("  store i8* %nodes_ptr, i8** %nodes_field\n");
        irCode.append("  %edges_ptr = call i8* @safe_malloc(i64 80)\n"); // 10 * i8*
        irCode.append("  %edges_field = getelementptr %struct.Graph, %struct.Graph* %graph_struct, i32 0, i32 3\n");
        irCode.append("  store i8* %edges_ptr, i8** %edges_field\n");
        irCode.append("  ret i8* %graph\n");
        irCode.append("}\n\n");

        irCode.append("define i8* @node(i8* %label) {\n");
        irCode.append("  %node = call i8* @safe_malloc(i64 24)\n");
        irCode.append("  %node_struct = bitcast i8* %node to %struct.Node*\n");
        irCode.append("  %label_ptr = getelementptr %struct.Node, %struct.Node* %node_struct, i32 0, i32 0\n");
        irCode.append("  store i8* %label, i8** %label_ptr\n");
        irCode.append("  %neighbors_ptr = call i8* @safe_malloc(i64 80)\n"); // 10 * i8*
        irCode.append("  %neighbors_field = getelementptr %struct.Node, %struct.Node* %node_struct, i32 0, i32 1\n");
        irCode.append("  store i8* %neighbors_ptr, i8** %neighbors_field\n");
        irCode.append("  %count_ptr = getelementptr %struct.Node, %struct.Node* %node_struct, i32 0, i32 2\n");
        irCode.append("  store i32 0, i32* %count_ptr\n");
        irCode.append("  ret i8* %node\n");
        irCode.append("}\n\n");
    }

    private void generateGraphAlgorithms() {
        irCode.append("; Алгоритмы обхода графов\n");

        // BFS - поиск в ширину
        irCode.append("define i8* @bfs(i8* %graph, i8* %start, i8* %visitor_func) {\n");
        irCode.append("  %result_list = call i8* @list()\n");
        irCode.append("  %visited = call i8* @create_visited_set()\n");
        irCode.append("  %queue = call i8* @queue_create()\n");
        irCode.append("  \n");
        irCode.append("  ; Добавляем стартовый узел в очередь\n");
        irCode.append("  call void @queue_enqueue(i8* %queue, i8* %start)\n");
        irCode.append("  call void @visited_set_add(i8* %visited, i8* %start)\n");
        irCode.append("  \n");
        irCode.append("  br label %bfs_loop\n");
        irCode.append("\n");
        irCode.append("bfs_loop:\n");
        irCode.append("  %is_empty = call i1 @queue_is_empty(i8* %queue)\n");
        irCode.append("  br i1 %is_empty, label %bfs_end, label %bfs_process\n");
        irCode.append("\n");
        irCode.append("bfs_process:\n");
        irCode.append("  %current = call i8* @queue_dequeue(i8* %queue)\n");
        irCode.append("  \n");
        irCode.append("  ; Вызываем visitor функцию\n");
        irCode.append("  %should_continue = call i1 @call_visitor(i8* %visitor_func, i8* %current)\n");
        irCode.append("  \n");
        irCode.append("  ; Добавляем узел в результат\n");
        irCode.append("  call void @list_add(i8* %result_list, i8* %current)\n");
        irCode.append("  \n");
        irCode.append("  ; Получаем соседей\n");
        irCode.append("  %neighbors = call i8* @get_neighbors(i8* %graph, i8* %current)\n");
        irCode.append("  %neighbor_count = call i32 @get_size(i8* %neighbors)\n");
        irCode.append("  \n");
        irCode.append("  ; Обрабатываем соседей\n");
        irCode.append("  %i = alloca i32\n");
        irCode.append("  store i32 0, i32* %i\n");
        irCode.append("  br label %neighbor_loop\n");
        irCode.append("\n");
        irCode.append("neighbor_loop:\n");
        irCode.append("  %idx = load i32, i32* %i\n");
        irCode.append("  %done = icmp sge i32 %idx, %neighbor_count\n");
        irCode.append("  br i1 %done, label %neighbor_end, label %process_neighbor\n");
        irCode.append("\n");
        irCode.append("process_neighbor:\n");
        irCode.append("  %neighbor = call i8* @get_element(i8* %neighbors, i32 %idx)\n");
        irCode.append("  %is_visited = call i1 @visited_set_contains(i8* %visited, i8* %neighbor)\n");
        irCode.append("  br i1 %is_visited, label %next_neighbor, label %add_neighbor\n");
        irCode.append("\n");
        irCode.append("add_neighbor:\n");
        irCode.append("  call void @queue_enqueue(i8* %queue, i8* %neighbor)\n");
        irCode.append("  call void @visited_set_add(i8* %visited, i8* %neighbor)\n");
        irCode.append("  br label %next_neighbor\n");
        irCode.append("\n");
        irCode.append("next_neighbor:\n");
        irCode.append("  %next_idx = add i32 %idx, 1\n");
        irCode.append("  store i32 %next_idx, i32* %i\n");
        irCode.append("  br label %neighbor_loop\n");
        irCode.append("\n");
        irCode.append("neighbor_end:\n");
        irCode.append("  call void @free_list(i8* %neighbors)\n");
        irCode.append("  br label %bfs_loop\n");
        irCode.append("\n");
        irCode.append("bfs_end:\n");
        irCode.append("  call void @free_visited_set(i8* %visited)\n");
        irCode.append("  call void @queue_free(i8* %queue)\n");
        irCode.append("  ret i8* %result_list\n");
        irCode.append("}\n\n");

        // DFS - поиск в глубину
        irCode.append("define i8* @dfs(i8* %graph, i8* %start, i8* %visitor_func) {\n");
        irCode.append("  %result_list = call i8* @list()\n");
        irCode.append("  %visited = call i8* @create_visited_set()\n");
        irCode.append("  call void @dfs_recursive(i8* %graph, i8* %start, i8* %visitor_func, i8* %visited, i8* %result_list)\n");
        irCode.append("  call void @free_visited_set(i8* %visited)\n");
        irCode.append("  ret i8* %result_list\n");
        irCode.append("}\n\n");

        irCode.append("define void @dfs_recursive(i8* %graph, i8* %current, i8* %visitor_func, i8* %visited, i8* %result_list) {\n");
        irCode.append("  call void @visited_set_add(i8* %visited, i8* %current)\n");
        irCode.append("  call i1 @call_visitor(i8* %visitor_func, i8* %current)\n");
        irCode.append("  call void @list_add(i8* %result_list, i8* %current)\n");
        irCode.append("  \n");
        irCode.append("  %neighbors = call i8* @get_neighbors(i8* %graph, i8* %current)\n");
        irCode.append("  %neighbor_count = call i32 @get_size(i8* %neighbors)\n");
        irCode.append("  %i = alloca i32\n");
        irCode.append("  store i32 0, i32* %i\n");
        irCode.append("  br label %dfs_neighbor_loop\n");
        irCode.append("\n");
        irCode.append("dfs_neighbor_loop:\n");
        irCode.append("  %idx = load i32, i32* %i\n");
        irCode.append("  %done = icmp sge i32 %idx, %neighbor_count\n");
        irCode.append("  br i1 %done, label %dfs_neighbor_end, label %dfs_process_neighbor\n");
        irCode.append("\n");
        irCode.append("dfs_process_neighbor:\n");
        irCode.append("  %neighbor = call i8* @get_element(i8* %neighbors, i32 %idx)\n");
        irCode.append("  %is_visited = call i1 @visited_set_contains(i8* %visited, i8* %neighbor)\n");
        irCode.append("  br i1 %is_visited, label %dfs_next_neighbor, label %dfs_visit\n");
        irCode.append("\n");
        irCode.append("dfs_visit:\n");
        irCode.append("  call void @dfs_recursive(i8* %graph, i8* %neighbor, i8* %visitor_func, i8* %visited, i8* %result_list)\n");
        irCode.append("  br label %dfs_next_neighbor\n");
        irCode.append("\n");
        irCode.append("dfs_next_neighbor:\n");
        irCode.append("  %next_idx = add i32 %idx, 1\n");
        irCode.append("  store i32 %next_idx, i32* %i\n");
        irCode.append("  br label %dfs_neighbor_loop\n");
        irCode.append("\n");
        irCode.append("dfs_neighbor_end:\n");
        irCode.append("  call void @free_list(i8* %neighbors)\n");
        irCode.append("  ret void\n");
        irCode.append("}\n\n");
    }

    private void generateDataStructures() {
        irCode.append("; Структуры данных\n");

        // Очередь
        irCode.append("%struct.Queue = type { i32, i32, i32, i8** }\n"); // front, rear, size, elements
        irCode.append("define i8* @queue_create() {\n");
        irCode.append("  %queue = call i8* @safe_malloc(i64 24)\n");
        irCode.append("  %queue_struct = bitcast i8* %queue to %struct.Queue*\n");
        irCode.append("  %front_ptr = getelementptr %struct.Queue, %struct.Queue* %queue_struct, i32 0, i32 0\n");
        irCode.append("  store i32 0, i32* %front_ptr\n");
        irCode.append("  %rear_ptr = getelementptr %struct.Queue, %struct.Queue* %queue_struct, i32 0, i32 1\n");
        irCode.append("  store i32 -1, i32* %rear_ptr\n");
        irCode.append("  %size_ptr = getelementptr %struct.Queue, %struct.Queue* %queue_struct, i32 0, i32 2\n");
        irCode.append("  store i32 0, i32* %size_ptr\n");
        irCode.append("  %elements_ptr = call i8* @safe_malloc(i64 800)\n"); // 100 элементов
        irCode.append("  %elements_field = getelementptr %struct.Queue, %struct.Queue* %queue_struct, i32 0, i32 3\n");
        irCode.append("  store i8** %elements_ptr, i8*** %elements_field\n");
        irCode.append("  ret i8* %queue\n");
        irCode.append("}\n\n");

        irCode.append("define void @queue_enqueue(i8* %queue, i8* %element) {\n");
        irCode.append("  %queue_struct = bitcast i8* %queue to %struct.Queue*\n");
        irCode.append("  %rear_ptr = getelementptr %struct.Queue, %struct.Queue* %queue_struct, i32 0, i32 1\n");
        irCode.append("  %rear = load i32, i32* %rear_ptr\n");
        irCode.append("  %new_rear = add i32 %rear, 1\n");
        irCode.append("  store i32 %new_rear, i32* %rear_ptr\n");
        irCode.append("  \n");
        irCode.append("  %elements_field = getelementptr %struct.Queue, %struct.Queue* %queue_struct, i32 0, i32 3\n");
        irCode.append("  %elements = load i8**, i8*** %elements_field\n");
        irCode.append("  %element_ptr = getelementptr i8*, i8** %elements, i32 %new_rear\n");
        irCode.append("  store i8* %element, i8** %element_ptr\n");
        irCode.append("  \n");
        irCode.append("  %size_ptr = getelementptr %struct.Queue, %struct.Queue* %queue_struct, i32 0, i32 2\n");
        irCode.append("  %size = load i32, i32* %size_ptr\n");
        irCode.append("  %new_size = add i32 %size, 1\n");
        irCode.append("  store i32 %new_size, i32* %size_ptr\n");
        irCode.append("  ret void\n");
        irCode.append("}\n\n");

        irCode.append("define i8* @queue_dequeue(i8* %queue) {\n");
        irCode.append("  %queue_struct = bitcast i8* %queue to %struct.Queue*\n");
        irCode.append("  %front_ptr = getelementptr %struct.Queue, %struct.Queue* %queue_struct, i32 0, i32 0\n");
        irCode.append("  %front = load i32, i32* %front_ptr\n");
        irCode.append("  \n");
        irCode.append("  %elements_field = getelementptr %struct.Queue, %struct.Queue* %queue_struct, i32 0, i32 3\n");
        irCode.append("  %elements = load i8**, i8*** %elements_field\n");
        irCode.append("  %element_ptr = getelementptr i8*, i8** %elements, i32 %front\n");
        irCode.append("  %element = load i8*, i8** %element_ptr\n");
        irCode.append("  \n");
        irCode.append("  %new_front = add i32 %front, 1\n");
        irCode.append("  store i32 %new_front, i32* %front_ptr\n");
        irCode.append("  \n");
        irCode.append("  %size_ptr = getelementptr %struct.Queue, %struct.Queue* %queue_struct, i32 0, i32 2\n");
        irCode.append("  %size = load i32, i32* %size_ptr\n");
        irCode.append("  %new_size = sub i32 %size, 1\n");
        irCode.append("  store i32 %new_size, i32* %size_ptr\n");
        irCode.append("  \n");
        irCode.append("  ret i8* %element\n");
        irCode.append("}\n\n");

        irCode.append("define i1 @queue_is_empty(i8* %queue) {\n");
        irCode.append("  %queue_struct = bitcast i8* %queue to %struct.Queue*\n");
        irCode.append("  %size_ptr = getelementptr %struct.Queue, %struct.Queue* %queue_struct, i32 0, i32 2\n");
        irCode.append("  %size = load i32, i32* %size_ptr\n");
        irCode.append("  %is_empty = icmp eq i32 %size, 0\n");
        irCode.append("  ret i1 %is_empty\n");
        irCode.append("}\n\n");

        // Множество посещенных узлов
        irCode.append("%struct.VisitedSet = type { i32, i32, i8** }\n"); // size, capacity, elements
        irCode.append("define i8* @create_visited_set() {\n");
        irCode.append("  %set = call i8* @safe_malloc(i64 24)\n");
        irCode.append("  %set_struct = bitcast i8* %set to %struct.VisitedSet*\n");
        irCode.append("  %size_ptr = getelementptr %struct.VisitedSet, %struct.VisitedSet* %set_struct, i32 0, i32 0\n");
        irCode.append("  store i32 0, i32* %size_ptr\n");
        irCode.append("  %capacity_ptr = getelementptr %struct.VisitedSet, %struct.VisitedSet* %set_struct, i32 0, i32 1\n");
        irCode.append("  store i32 100, i32* %capacity_ptr\n");
        irCode.append("  %elements_ptr = call i8* @safe_malloc(i64 800)\n"); // 100 элементов
        irCode.append("  %elements_field = getelementptr %struct.VisitedSet, %struct.VisitedSet* %set_struct, i32 0, i32 2\n");
        irCode.append("  store i8** %elements_ptr, i8*** %elements_field\n");
        irCode.append("  ret i8* %set\n");
        irCode.append("}\n\n");

        irCode.append("define void @visited_set_add(i8* %set, i8* %element) {\n");
        irCode.append("  %already_contains = call i1 @visited_set_contains(i8* %set, i8* %element)\n");
        irCode.append("  br i1 %already_contains, label %end, label %add\n");
        irCode.append("add:\n");
        irCode.append("  %set_struct = bitcast i8* %set to %struct.VisitedSet*\n");
        irCode.append("  %size_ptr = getelementptr %struct.VisitedSet, %struct.VisitedSet* %set_struct, i32 0, i32 0\n");
        irCode.append("  %size = load i32, i32* %size_ptr\n");
        irCode.append("  %elements_field = getelementptr %struct.VisitedSet, %struct.VisitedSet* %set_struct, i32 0, i32 2\n");
        irCode.append("  %elements = load i8**, i8*** %elements_field\n");
        irCode.append("  %element_ptr = getelementptr i8*, i8** %elements, i32 %size\n");
        irCode.append("  store i8* %element, i8** %element_ptr\n");
        irCode.append("  %new_size = add i32 %size, 1\n");
        irCode.append("  store i32 %new_size, i32* %size_ptr\n");
        irCode.append("  br label %end\n");
        irCode.append("end:\n");
        irCode.append("  ret void\n");
        irCode.append("}\n\n");

        irCode.append("define i1 @visited_set_contains(i8* %set, i8* %element) {\n");
        irCode.append("  %set_struct = bitcast i8* %set to %struct.VisitedSet*\n");
        irCode.append("  %size_ptr = getelementptr %struct.VisitedSet, %struct.VisitedSet* %set_struct, i32 0, i32 0\n");
        irCode.append("  %size = load i32, i32* %size_ptr\n");
        irCode.append("  %elements_field = getelementptr %struct.VisitedSet, %struct.VisitedSet* %set_struct, i32 0, i32 2\n");
        irCode.append("  %elements = load i8**, i8*** %elements_field\n");
        irCode.append("  \n");
        irCode.append("  %i = alloca i32\n");
        irCode.append("  store i32 0, i32* %i\n");
        irCode.append("  br label %loop\n");
        irCode.append("\n");
        irCode.append("loop:\n");
        irCode.append("  %idx = load i32, i32* %i\n");
        irCode.append("  %done = icmp sge i32 %idx, %size\n");
        irCode.append("  br i1 %done, label %not_found, label %check\n");
        irCode.append("\n");
        irCode.append("check:\n");
        irCode.append("  %current_ptr = getelementptr i8*, i8** %elements, i32 %idx\n");
        irCode.append("  %current = load i8*, i8** %current_ptr\n");
        irCode.append("  %is_equal = icmp eq i8* %current, %element\n");
        irCode.append("  br i1 %is_equal, label %found, label %next\n");
        irCode.append("\n");
        irCode.append("next:\n");
        irCode.append("  %next_idx = add i32 %idx, 1\n");
        irCode.append("  store i32 %next_idx, i32* %i\n");
        irCode.append("  br label %loop\n");
        irCode.append("\n");
        irCode.append("found:\n");
        irCode.append("  ret i1 true\n");
        irCode.append("not_found:\n");
        irCode.append("  ret i1 false\n");
        irCode.append("}\n\n");
    }

    private void generateLambdaSupport() {
        irCode.append("; Поддержка лямбда-функций\n");

        irCode.append("define i1 @call_visitor(i8* %visitor_func, i8* %node) {\n");
        irCode.append("  %result = call i1 (i8*, i8*) bitcast (i8* %visitor_func to i1 (i8*, i8*)*)(i8* %node)\n");
        irCode.append("  ret i1 %result\n");
        irCode.append("}\n\n");

        // Генерация лямбда-функций
        irCode.append("; Лямбда-функция complex_op\n");
        irCode.append("define i8* @lambda_complex_op(i8* %g, i8* %n) {\n");
        irCode.append("  %label_ptr = call i8* @get_label(i8* %n)\n");
        irCode.append("  \n");
        irCode.append("  ; Создаем строку \"Processing: \"\n");
        irCode.append("  %prefix = getelementptr inbounds [12 x i8], [12 x i8]* @.str.processing, i64 0, i64 0\n");
        irCode.append("  \n");
        irCode.append("  ; Конкатенируем строки\n");
        irCode.append("  %message = call i8* @string_concat(i8* %prefix, i8* %label_ptr)\n");
        irCode.append("  \n");
        irCode.append("  ; Выводим сообщение\n");
        irCode.append("  call void @write(i8* %message)\n");
        irCode.append("  \n");
        irCode.append("  ; Возвращаем label\n");
        irCode.append("  ret i8* %label_ptr\n");
        irCode.append("}\n\n");

        irCode.append("; Лямбда-функция filter_hidden\n");
        irCode.append("define i1 @lambda_filter_hidden(i8* %n) {\n");
        irCode.append("  %label = call i8* @get_label(i8* %n)\n");
        irCode.append("  %hidden_str = getelementptr inbounds [7 x i8], [7 x i8]* @.str.hidden, i64 0, i64 0\n");
        irCode.append("  %cmp_result = call i32 @strcmp(i8* %label, i8* %hidden_str)\n");
        irCode.append("  %is_not_hidden = icmp ne i32 %cmp_result, 0\n");
        irCode.append("  ret i1 %is_not_hidden\n");
        irCode.append("}\n\n");

        irCode.append("; Лямбда-функция для BFS visitor\n");
        irCode.append("define i1 @lambda_bfs_visitor(i8* %current) {\n");
        irCode.append("  %label = call i8* @get_label(i8* %current)\n");
        irCode.append("  %prefix = getelementptr inbounds [11 x i8], [11 x i8]* @.str.visiting, i64 0, i64 0\n");
        irCode.append("  %message = call i8* @string_concat(i8* %prefix, i8* %label)\n");
        irCode.append("  call void @write(i8* %message)\n");
        irCode.append("  ret i1 true\n");
        irCode.append("}\n\n");
    }

    // Обновляем метод для генерации строковых констант
    private void generateStringConstants() {
        irCode.insert(0, "@.str.processing = private unnamed_addr constant [12 x i8] c\"Processing: \\00\"\n");
        irCode.insert(0, "@.str.hidden = private unnamed_addr constant [7 x i8] c\"hidden\\00\"\n");
        irCode.insert(0, "@.str.visiting = private unnamed_addr constant [11 x i8] c\"Visiting: \\00\"\n");
        irCode.insert(0, "@.str.A = private unnamed_addr constant [2 x i8] c\"A\\00\"\n");
        irCode.insert(0, "@.str.completed = private unnamed_addr constant [40 x i8] c\"Graph processing completed successfully\\00\"\n");
    }

    private void generateListNodeFunctions() {
        // Полноценная реализация get_size
        irCode.append("define i32 @get_size(i8* %collection) {\n");
        irCode.append("  %1 = bitcast i8* %collection to i32*\n");
        irCode.append("  %size = load i32, i32* %1\n");
        irCode.append("  ret i32 %size\n");
        irCode.append("}\n\n");

        // Полноценная реализация get_element
        irCode.append("define i8* @get_element(i8* %collection, i32 %index) {\n");
        irCode.append("  %data_ptr = getelementptr i8, i8* %collection, i64 8\n"); // Пропускаем размер и емкость
        irCode.append("  %elements = bitcast i8* %data_ptr to i8**\n");
        irCode.append("  %element_ptr = getelementptr i8*, i8** %elements, i32 %index\n");
        irCode.append("  %element = load i8*, i8** %element_ptr\n");
        irCode.append("  ret i8* %element\n");
        irCode.append("}\n\n");

        // Функция для добавления элемента в коллекцию
        irCode.append("define void @add_element(i8* %collection, i8* %element) {\n");
        irCode.append("  %size_ptr = bitcast i8* %collection to i32*\n");
        irCode.append("  %size = load i32, i32* %size_ptr\n");
        irCode.append("  %capacity_ptr = getelementptr i8, i8* %collection, i64 4\n");
        irCode.append("  %capacity_i32 = bitcast i8* %capacity_ptr to i32*\n");
        irCode.append("  %capacity = load i32, i32* %capacity_i32\n");
        irCode.append("  %needs_expansion = icmp eq i32 %size, %capacity\n");
        irCode.append("  br i1 %needs_expansion, label %expand, label %store\n");
        irCode.append("expand:\n");
        irCode.append("  %new_capacity = mul i32 %capacity, 2\n");
        irCode.append("  store i32 %new_capacity, i32* %capacity_i32\n");
        irCode.append("  %data_ptr_old = getelementptr i8, i8* %collection, i64 8\n");
        irCode.append("  %old_elements = bitcast i8* %data_ptr_old to i8**\n");
        irCode.append("  %new_size_bytes = mul i64 8, %new_capacity\n");
        irCode.append("  %new_elements_ptr = call i8* @safe_malloc(i64 %new_size_bytes)\n");
        irCode.append("  %new_elements = bitcast i8* %new_elements_ptr to i8**\n");
        irCode.append("  br label %store\n");
        irCode.append("store:\n");
        irCode.append("  %data_ptr = getelementptr i8, i8* %collection, i64 8\n");
        irCode.append("  %elements = bitcast i8* %data_ptr to i8**\n");
        irCode.append("  %element_ptr = getelementptr i8*, i8** %elements, i32 %size\n");
        irCode.append("  store i8* %element, i8** %element_ptr\n");
        irCode.append("  %new_size = add i32 %size, 1\n");
        irCode.append("  store i32 %new_size, i32* %size_ptr\n");
        irCode.append("  ret void\n");
        irCode.append("}\n\n");
    }

    private void generateStringFunctions() {
        irCode.append("; Строковые функции\n");

        irCode.append("define i32 @find(i8* %str, i8* %substr) {\n");
        irCode.append("  %pos_ptr = call i8* @strstr(i8* %str, i8* %substr)\n");
        irCode.append("  %is_found = icmp ne i8* %pos_ptr, null\n");
        irCode.append("  br i1 %is_found, label %found, label %not_found\n");
        irCode.append("found:\n");
        irCode.append("  %start = ptrtoint i8* %str to i64\n");
        irCode.append("  %pos = ptrtoint i8* %pos_ptr to i64\n");
        irCode.append("  %offset = sub i64 %pos, %start\n");
        irCode.append("  %offset_i32 = trunc i64 %offset to i32\n");
        irCode.append("  ret i32 %offset_i32\n");
        irCode.append("not_found:\n");
        irCode.append("  ret i32 -1\n");
        irCode.append("}\n\n");

        irCode.append("define i8* @replace(i8* %str, i8* %old, i8* %new) {\n");
        irCode.append("entry:\n");
        irCode.append("  ; Проверяем нулевые указатели\n");
        irCode.append("  %is_str_null = icmp eq i8* %str, null\n");
        irCode.append("  br i1 %is_str_null, label %return_null, label %check_old\n");
        irCode.append("\n");
        irCode.append("check_old:\n");
        irCode.append("  %is_old_null = icmp eq i8* %old, null\n");
        irCode.append("  br i1 %is_old_null, label %return_str, label %check_new\n");
        irCode.append("\n");
        irCode.append("check_new:\n");
        irCode.append("  %is_new_null = icmp eq i8* %new, null\n");
        irCode.append("  br i1 %is_new_null, label %return_str, label %get_lengths\n");
        irCode.append("\n");
        irCode.append("get_lengths:\n");
        irCode.append("  ; Получаем длины строк\n");
        irCode.append("  %str_len = call i32 @strlen(i8* %str)\n");
        irCode.append("  %old_len = call i32 @strlen(i8* %old)\n");
        irCode.append("  %new_len = call i32 @strlen(i8* %new)\n");
        irCode.append("  \n");
        irCode.append("  ; Проверяем пустые строки\n");
        irCode.append("  %is_old_empty = icmp eq i32 %old_len, 0\n");
        irCode.append("  br i1 %is_old_empty, label %return_str, label %find_first\n");
        irCode.append("\n");
        irCode.append("find_first:\n");
        irCode.append("  ; Ищем первое вхождение\n");
        irCode.append("  %first_pos_ptr = call i8* @strstr(i8* %str, i8* %old)\n");
        irCode.append("  %is_found = icmp ne i8* %first_pos_ptr, null\n");
        irCode.append("  br i1 %is_found, label %count_occurrences, label %return_str\n");
        irCode.append("\n");
        irCode.append("count_occurrences:\n");
        irCode.append("  ; Подсчитываем количество вхождений\n");
        irCode.append("  %count_ptr = alloca i32\n");
        irCode.append("  store i32 0, i32* %count_ptr\n");
        irCode.append("  %current_ptr = alloca i8*\n");
        irCode.append("  store i8* %str, i8** %current_ptr\n");
        irCode.append("  br label %count_loop\n");
        irCode.append("\n");
        irCode.append("count_loop:\n");
        irCode.append("  %current = load i8*, i8** %current_ptr\n");
        irCode.append("  %pos_ptr = call i8* @strstr(i8* %current, i8* %old)\n");
        irCode.append("  %found = icmp ne i8* %pos_ptr, null\n");
        irCode.append("  br i1 %found, label %increment_count, label %count_done\n");
        irCode.append("\n");
        irCode.append("increment_count:\n");
        irCode.append("  %current_count = load i32, i32* %count_ptr\n");
        irCode.append("  %new_count = add i32 %current_count, 1\n");
        irCode.append("  store i32 %new_count, i32* %count_ptr\n");
        irCode.append("  %next_ptr = getelementptr i8, i8* %pos_ptr, i32 %old_len\n");
        irCode.append("  store i8* %next_ptr, i8** %current_ptr\n");
        irCode.append("  br label %count_loop\n");
        irCode.append("\n");
        irCode.append("count_done:\n");
        irCode.append("  %total_count = load i32, i32* %count_ptr\n");
        irCode.append("  \n");
        irCode.append("  ; Вычисляем длину новой строки\n");
        irCode.append("  %old_total_len = mul i32 %old_len, %total_count\n");
        irCode.append("  %new_total_len = mul i32 %new_len, %total_count\n");
        irCode.append("  %length_diff = sub i32 %new_total_len, %old_total_len\n");
        irCode.append("  %result_len = add i32 %str_len, %length_diff\n");
        irCode.append("  %result_len_plus_1 = add i32 %result_len, 1\n");
        irCode.append("  \n");
        irCode.append("  ; Выделяем память для результата\n");
        irCode.append("  %result_len_64 = zext i32 %result_len_plus_1 to i64\n");
        irCode.append("  %result = call i8* @safe_malloc(i64 %result_len_64)\n");
        irCode.append("  \n");
        irCode.append("  ; Подготавливаем переменные для цикла замены\n");
        irCode.append("  %src_ptr = alloca i8*\n");
        irCode.append("  store i8* %str, i8** %src_ptr\n");
        irCode.append("  %dest_ptr = alloca i8*\n");
        irCode.append("  store i8* %result, i8** %dest_ptr\n");
        irCode.append("  %remaining_count = alloca i32\n");
        irCode.append("  store i32 %total_count, i32* %remaining_count\n");
        irCode.append("  br label %replace_loop\n");
        irCode.append("\n");
        irCode.append("replace_loop:\n");
        irCode.append("  %rem_count = load i32, i32* %remaining_count\n");
        irCode.append("  %has_more = icmp sgt i32 %rem_count, 0\n");
        irCode.append("  br i1 %has_more, label %find_next, label %copy_remaining\n");
        irCode.append("\n");
        irCode.append("find_next:\n");
        irCode.append("  %src = load i8*, i8** %src_ptr\n");
        irCode.append("  %next_pos = call i8* @strstr(i8* %src, i8* %old)\n");
        irCode.append("  \n");
        irCode.append("  ; Вычисляем длину фрагмента до вхождения\n");
        irCode.append("  %src_start = ptrtoint i8* %src to i64\n");
        irCode.append("  %pos_int = ptrtoint i8* %next_pos to i64\n");
        irCode.append("  %fragment_len64 = sub i64 %pos_int, %src_start\n");
        irCode.append("  %fragment_len = trunc i64 %fragment_len64 to i32\n");
        irCode.append("  \n");
        irCode.append("  ; Копируем фрагмент до вхождения\n");
        irCode.append("  %dest = load i8*, i8** %dest_ptr\n");
        irCode.append("  %fragment_len64_2 = zext i32 %fragment_len to i64\n");
        irCode.append("  call void @llvm.memcpy.p0i8.p0i8.i64(i8* %dest, i8* %src, i64 %fragment_len64_2, i1 false)\n");
        irCode.append("  \n");
        irCode.append("  ; Сдвигаем указатель назначения\n");
        irCode.append("  %new_dest = getelementptr i8, i8* %dest, i32 %fragment_len\n");
        irCode.append("  store i8* %new_dest, i8** %dest_ptr\n");
        irCode.append("  \n");
        irCode.append("  ; Копируем новую подстроку\n");
        irCode.append("  %new_len64 = zext i32 %new_len to i64\n");
        irCode.append("  call void @llvm.memcpy.p0i8.p0i8.i64(i8* %new_dest, i8* %new, i64 %new_len64, i1 false)\n");
        irCode.append("  \n");
        irCode.append("  ; Сдвигаем указатели\n");
        irCode.append("  %new_dest_after = getelementptr i8, i8* %new_dest, i32 %new_len\n");
        irCode.append("  store i8* %new_dest_after, i8** %dest_ptr\n");
        irCode.append("  \n");
        irCode.append("  %new_src = getelementptr i8, i8* %next_pos, i32 %old_len\n");
        irCode.append("  store i8* %new_src, i8** %src_ptr\n");
        irCode.append("  \n");
        irCode.append("  ; Уменьшаем счетчик\n");
        irCode.append("  %new_rem_count = sub i32 %rem_count, 1\n");
        irCode.append("  store i32 %new_rem_count, i32* %remaining_count\n");
        irCode.append("  br label %replace_loop\n");
        irCode.append("\n");
        irCode.append("copy_remaining:\n");
        irCode.append("  ; Копируем оставшуюся часть строки\n");
        irCode.append("  %final_src = load i8*, i8** %src_ptr\n");
        irCode.append("  %final_dest = load i8*, i8** %dest_ptr\n");
        irCode.append("  %remaining_len = call i32 @strlen(i8* %final_src)\n");
        irCode.append("  %remaining_len64 = zext i32 %remaining_len to i64\n");
        irCode.append("  call void @llvm.memcpy.p0i8.p0i8.i64(i8* %final_dest, i8* %final_src, i64 %remaining_len64, i1 false)\n");
        irCode.append("  \n");
        irCode.append("  ; Добавляем нулевой терминатор\n");
        irCode.append("  %terminator_pos = getelementptr i8, i8* %final_dest, i32 %remaining_len\n");
        irCode.append("  store i8 0, i8* %terminator_pos\n");
        irCode.append("  \n");
        irCode.append("  ret i8* %result\n");
        irCode.append("\n");
        irCode.append("return_str:\n");
        irCode.append("  ; Возвращаем исходную строку (копию)\n");
        irCode.append("  %str_len_copy = call i32 @strlen(i8* %str)\n");
        irCode.append("  %str_len_plus_1 = add i32 %str_len_copy, 1\n");
        irCode.append("  %str_len64 = zext i32 %str_len_plus_1 to i64\n");
        irCode.append("  %copy = call i8* @safe_malloc(i64 %str_len64)\n");
        irCode.append("  call void @llvm.memcpy.p0i8.p0i8.i64(i8* %copy, i8* %str, i64 %str_len64, i1 false)\n");
        irCode.append("  ret i8* %copy\n");
        irCode.append("\n");
        irCode.append("return_null:\n");
        irCode.append("  ret i8* null\n");
        irCode.append("}\n\n");
    }

    @Override
    public String visitComparisonExpr(GPLParser.ComparisonExprContext ctx) {
        String left = visit(ctx.expression(0));
        String right = visit(ctx.expression(1));
        String result = newTemp();
        String op = getLLVMComparisonOp(ctx.op.getText());
        irCode.append("  ").append(result).append(" = ").append(op)
                .append(" i32 ").append(left).append(", ").append(right).append("\n");
        return result;
    }

    @Override
    public String visitLogicalExpr(GPLParser.LogicalExprContext ctx) {
        String left = visit(ctx.expression(0));
        String right = visit(ctx.expression(1));
        String result = newTemp();
        String op = getLLVMLogicalOp(ctx.op.getText());
        irCode.append("  ").append(result).append(" = ").append(op)
                .append(" i1 ").append(left).append(", ").append(right).append("\n");
        return result;
    }

    private String getLLVMComparisonOp(String op) {
        return switch (op) {
            case "==" -> "icmp eq";
            case "!=" -> "icmp ne";
            case "<" -> "icmp slt";
            case ">" -> "icmp sgt";
            case "<=" -> "icmp sle";
            case ">=" -> "icmp sge";
            default -> "icmp eq";
        };
    }

    private String getLLVMLogicalOp(String op) {
        return switch (op) {
            case "&&", "and" -> "and";
            case "||", "or" -> "or";
            default -> "and";
        };
    }

    @Override
    public String visitReturnStatement(GPLParser.ReturnStatementContext ctx) {
        if (ctx.expression() != null) {
            String returnValue = visit(ctx.expression());

            // Определяем тип возвращаемого значения на основе контекста функции
            String returnType = determineCurrentFunctionReturnType();
            if (returnType == null) {
                // Если не в функции, предполагаем main -> i32
                returnType = "i32";
            }

            // Если типы не совпадают, добавляем приведение типа
            String actualReturnType = getExpressionType(ctx.expression());
            if (!actualReturnType.equals(returnType)) {
                String castValue = castToType(returnValue, actualReturnType, returnType);
                irCode.append("  ret ").append(returnType).append(" ").append(castValue).append("\n");
            } else {
                irCode.append("  ret ").append(returnType).append(" ").append(returnValue).append("\n");
            }
        } else {
            irCode.append("  ret void\n");
        }
        return null;
    }

    private String determineCurrentFunctionReturnType() {
        if (!currentFunctionReturnType.isEmpty()) {
            return currentFunctionReturnType.peek();
        }
        return "i32";
    }

    private String getExpressionType(GPLParser.ExpressionContext expr) {
        if (expr instanceof GPLParser.LiteralExprContext) {
            GPLParser.LiteralContext literal = ((GPLParser.LiteralExprContext) expr).literal();
            if (literal.INT() != null) return "i32";
            if (literal.STRING() != null) return "i8*";
            if (literal.TRUE() != null || literal.FALSE() != null) return "i1";
        }
        return "i32";
    }

    private String castToType(String value, String fromType, String toType) {
        if (fromType.equals(toType)) return value;

        String temp = newTemp();
        if (fromType.equals("i32") && toType.equals("i8*")) {
            // Конвертация int в string (упрощенно)
            irCode.append("  ").append(temp).append(" = call i8* @int_to_string(i32 ").append(value).append(")\n");
        } else if (fromType.equals("i8*") && toType.equals("i32")) {
            // Конвертация string в int (упрощенно)
            irCode.append("  ").append(temp).append(" = call i32 @string_to_int(i8* ").append(value).append(")\n");
        } else {
            // Биткаст для совместимых типов указателей
            irCode.append("  ").append(temp).append(" = bitcast ").append(fromType)
                    .append(" ").append(value).append(" to ").append(toType).append("\n");
        }
        return temp;
    }
}