package com.gpl.parser;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import java.util.*;

public class SemanticAnalyzer extends GPLBaseVisitor<Void> {
    private final List<String> errors = new ArrayList<>();
    private final ParseTreeProperty<Type> types = new ParseTreeProperty<>();
    private final Stack<Map<String, Type>> scopes = new Stack<>();
    private final Map<String, FunctionSignature> functions = new HashMap<>();
    private final Stack<LambdaContext> lambdaContexts = new Stack<>();

    public enum Type {
        GRAPH, NODE, ARC, LIST, ARRAY, INT, STRING, BOOLEAN, CHAR, VOID, NIL, ERROR, FUNCTION
    }

    // Контекст для лямбда-функций
    private static class LambdaContext {
        public List<Type> parameterTypes;
        public Type returnType;

        public LambdaContext(List<Type> parameterTypes, Type returnType) {
            this.parameterTypes = parameterTypes;
            this.returnType = returnType;
        }
    }

    public static class FunctionSignature {
        public Type returnType;
        public List<Type> parameterTypes;
        public List<Boolean> referenceParameters;
        public boolean variableArgs;
        public boolean isLambda;

        // Конструктор для обычных функций
        public FunctionSignature(Type returnType, List<Type> parameterTypes,
                                 List<Boolean> referenceParameters) {
            this.returnType = returnType;
            this.parameterTypes = parameterTypes;
            this.referenceParameters = referenceParameters;
            this.variableArgs = false;
            this.isLambda = false;
        }

        // Конструктор для функций с переменным числом аргументов
        public FunctionSignature(Type returnType, List<Type> parameterTypes,
                                 List<Boolean> referenceParameters, boolean variableArgs) {
            this.returnType = returnType;
            this.parameterTypes = parameterTypes;
            this.referenceParameters = referenceParameters;
            this.variableArgs = variableArgs;
            this.isLambda = false;
        }

        // Конструктор для лямбда-функций
        public FunctionSignature(Type returnType, List<Type> parameterTypes, boolean isLambda) {
            this.returnType = returnType;
            this.parameterTypes = parameterTypes;
            this.referenceParameters = Collections.nCopies(parameterTypes.size(), false);
            this.variableArgs = false;
            this.isLambda = isLambda;
        }
    }

    public SemanticAnalyzer() {
        registerBuiltInFunctions();
    }

    private void registerBuiltInFunctions() {
        // graph() или graph(string) — оба варианта
        functions.put("graph", new FunctionSignature(Type.GRAPH,
                Collections.emptyList(), Collections.emptyList(), true));

        // node(string)
        functions.put("node", new FunctionSignature(Type.NODE,
                List.of(Type.STRING), List.of(false)));

        // arc — принимает 1 или 3 аргумента
        functions.put("arc", new FunctionSignature(Type.ARC,
                Collections.emptyList(), Collections.emptyList(), true));

        // list()
        functions.put("list", new FunctionSignature(Type.LIST,
                Collections.emptyList(), Collections.emptyList()));

        // Функции для работы с графами
        functions.put("find_node", new FunctionSignature(Type.NODE,
                List.of(Type.GRAPH, Type.STRING), List.of(false, false)));
        functions.put("bfs", new FunctionSignature(Type.LIST,
                List.of(Type.GRAPH, Type.NODE, Type.FUNCTION), List.of(false, false, false)));
        functions.put("dfs", new FunctionSignature(Type.LIST,
                List.of(Type.GRAPH, Type.NODE, Type.FUNCTION), List.of(false, false, false)));
        functions.put("shortest_path", new FunctionSignature(Type.LIST,
                List.of(Type.GRAPH, Type.NODE, Type.NODE), List.of(false, false, false)));

        // write — принимает любые типы, валидация вручную
        functions.put("write", new FunctionSignature(Type.VOID,
                Collections.emptyList(), Collections.emptyList(), true));
        functions.put("read", new FunctionSignature(Type.GRAPH,
                Collections.emptyList(), Collections.emptyList()));

        // Вспомогательные функции
        functions.put("is_empty", new FunctionSignature(Type.BOOLEAN,
                List.of(Type.GRAPH), List.of(false)));
        functions.put("get_first_node", new FunctionSignature(Type.NODE,
                List.of(Type.GRAPH), List.of(false)));
        functions.put("get_next_node", new FunctionSignature(Type.NODE,
                List.of(Type.GRAPH, Type.NODE), List.of(false, false)));
        functions.put("get_label", new FunctionSignature(Type.STRING,
                List.of(Type.NODE), List.of(false)));
        functions.put("get_nodes", new FunctionSignature(Type.LIST,
                List.of(Type.GRAPH), List.of(false)));
        functions.put("get_neighbors", new FunctionSignature(Type.LIST,
                List.of(Type.GRAPH, Type.NODE), List.of(false, false)));
        functions.put("add", new FunctionSignature(Type.LIST,
                List.of(Type.LIST, Type.NODE), List.of(false, false)));

        // Строковые функции (если нужно)
        functions.put("find", new FunctionSignature(Type.INT,
                List.of(Type.STRING, Type.STRING), List.of(false, false)));
        functions.put("replace", new FunctionSignature(Type.STRING,
                List.of(Type.STRING, Type.STRING, Type.STRING), List.of(false, false, false)));
        functions.put("slice", new FunctionSignature(Type.STRING,
                List.of(Type.STRING, Type.INT, Type.INT), List.of(false, false, false)));
        functions.put("split", new FunctionSignature(Type.ARRAY,
                List.of(Type.STRING, Type.STRING), List.of(false, false)));
    }

    public List<String> getErrors() {
        return errors;
    }

    private void error(int line, String message) {
        errors.add("Семантическая ошибка в строке " + line + ": " + message);
    }

    private Type getType(String typeName) {
        if (typeName.endsWith("?")) {
            typeName = typeName.substring(0, typeName.length() - 1);
        }
        return switch (typeName) {
            case "graph" -> Type.GRAPH;
            case "node" -> Type.NODE;
            case "arc" -> Type.ARC;
            case "list" -> Type.LIST;
            case "array" -> Type.ARRAY;
            case "int" -> Type.INT;
            case "string" -> Type.STRING;
            case "boolean" -> Type.BOOLEAN;
            case "char" -> Type.CHAR;
            case "function" -> Type.FUNCTION;
            case "void" -> Type.VOID;
            case "nil" -> Type.NIL;
            default -> Type.ERROR;
        };
    }

    @Override
    public Void visitProgram(GPLParser.ProgramContext ctx) {
        scopes.push(new HashMap<>());
        visitChildren(ctx);
        scopes.pop();
        return null;
    }

    @Override
    public Void visitBlock(GPLParser.BlockContext ctx) {
        scopes.push(new HashMap<>());
        visitChildren(ctx);
        scopes.pop();
        return null;
    }

    @Override
    public Void visitVariableDeclaration(GPLParser.VariableDeclarationContext ctx) {
        String varName = ctx.ID().getText();
        Type varType = getType(ctx.type().getText());

        if (varType == Type.ERROR) {
            error(ctx.getStart().getLine(), "Неизвестный тип: " + ctx.type().getText());
            return null;
        }

        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(varName)) {
                error(ctx.getStart().getLine(), "Переменная '" + varName + "' уже объявлена");
                return null;
            }
        }

        scopes.peek().put(varName, varType);
        visit(ctx.expression());
        Type exprType = types.get(ctx.expression());

        if (exprType != Type.ERROR && exprType != varType &&
                !(exprType == Type.NIL && varType != Type.INT && varType != Type.BOOLEAN)) {
            // Разрешаем инициализацию NIL всем типам кроме INT и BOOLEAN
            error(ctx.getStart().getLine(),
                    "Несовместимые типы: невозможно присвоить " + exprType + " переменной типа " + varType);
        }

        return null;
    }

    @Override
    public Void visitAssignment(GPLParser.AssignmentContext ctx) {
        String varName = ctx.ID().getText();

        Type varType = null;
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(varName)) {
                varType = scopes.get(i).get(varName);
                break;
            }
        }

        if (varType == null) {
            error(ctx.getStart().getLine(), "Неизвестная переменная: '" + varName + "'");
            types.put(ctx, Type.ERROR);
            return null;
        }

        visit(ctx.expression());
        Type exprType = types.get(ctx.expression());

        if (exprType != Type.ERROR && exprType != varType &&
                !(exprType == Type.NIL && varType != Type.INT && varType != Type.BOOLEAN)) {
            // Разрешаем присваивание NIL всем типам кроме INT и BOOLEAN
            error(ctx.getStart().getLine(),
                    "Несовместимые типы: невозможно присвоить " + exprType + " переменной типа " + varType);
        }

        types.put(ctx, varType);
        return null;
    }

    @Override
    public Void visitFunctionCall(GPLParser.FunctionCallContext ctx) {
        String funcName;
        boolean isLambdaCall = false;

        // Определяем имя функции и проверяем, является ли она лямбдой
        if (ctx.functionName().ID() != null) {
            funcName = ctx.functionName().ID().getText();

            // Проверяем, является ли это лямбда-функцией (хранится в переменной с типом FUNCTION)
            Type varType = findVariableType(funcName);
            if (varType == Type.FUNCTION) {
                isLambdaCall = true;
            }
        } else {
            funcName = ctx.functionName().getText();
        }

        FunctionSignature signature = null;

        // Для лямбда-вызовов получаем сигнатуру из functions
        // Для обычных функций проверяем существование
        if (isLambdaCall) {
            signature = functions.get(funcName);
            if (signature == null) {
                error(ctx.getStart().getLine(), "Лямбда-функция '" + funcName + "' не найдена");
                types.put(ctx, Type.ERROR);
                return null;
            }
        } else {
            if (!functions.containsKey(funcName)) {
                error(ctx.getStart().getLine(), "Неизвестная функция: '" + funcName + "'");
                types.put(ctx, Type.ERROR);
                return null;
            }
            signature = functions.get(funcName);
        }

        // Теперь signature гарантированно не null
        List<GPLParser.ExpressionContext> args = ctx.expression();

        // Посещаем все аргументы — устанавливаем их типы
        for (GPLParser.ExpressionContext arg : args) {
            visit(arg);
        }

        // Устанавливаем возвращаемый тип
        types.put(ctx, signature.returnType);

        // Остальная часть метода без изменений...
        // ===========================================================
        // Пост-валидация для специальных функций
        // ===========================================================

        switch (funcName) {
            case "graph" -> {
                if (args.size() > 1) {
                    error(ctx.getStart().getLine(),
                            "Неверное количество аргументов для функции 'graph'. Ожидается: 0 или 1, получено: " + args.size());
                }
                if (args.size() == 1) {
                    Type argType = types.get(args.get(0));
                    if (argType != Type.STRING) {
                        error(ctx.getStart().getLine(),
                                "Неверный тип аргумента для функции 'graph'. Ожидается: STRING, получено: " + argType);
                    }
                }
            }

            case "arc" -> {
                if (args.size() == 1) {
                    Type argType = types.get(args.get(0));
                    if (argType != Type.INT) {
                        error(ctx.getStart().getLine(),
                                "Неверный тип аргумента для функции 'arc'. Ожидается: INT, получено: " + argType);
                    }
                } else if (args.size() == 3) {
                    Type fromType = types.get(args.get(0));
                    Type toType = types.get(args.get(1));
                    Type weightType = types.get(args.get(2));

                    if (fromType != Type.NODE && fromType != Type.NIL) {
                        error(ctx.getStart().getLine(),
                                "Неверный тип аргумента 1 для функции 'arc'. Ожидается: NODE, получено: " + fromType);
                    }
                    if (toType != Type.NODE && toType != Type.NIL) {
                        error(ctx.getStart().getLine(),
                                "Неверный тип аргумента 2 для функции 'arc'. Ожидается: NODE, получено: " + toType);
                    }
                    if (weightType != Type.INT && weightType != Type.ARC) {
                        error(ctx.getStart().getLine(),
                                "Неверный тип аргумента 3 для функции 'arc'. Ожидается: INT или ARC, получено: " + weightType);
                    }
                } else {
                    error(ctx.getStart().getLine(),
                            "Неверное количество аргументов для функции 'arc'. Ожидается: 1 или 3, получено: " + args.size());
                }
            }

            case "find_node" ->
                // find_node может вернуть NIL если узел не найден
                    types.put(ctx, Type.NODE); // Основной тип - NODE, но может быть NIL

            case "write" -> {
                // write принимает любые типы
                for (GPLParser.ExpressionContext arg : args) {
                    Type argType = types.get(arg);
                    if (argType == Type.ERROR) continue;
                }
            }

            default -> {
                // Для лямбда-функций и обычных функций проверяем аргументы
                if (!signature.variableArgs && args.size() != signature.parameterTypes.size()) {
                    error(ctx.getStart().getLine(),
                            "Неверное количество аргументов для " +
                                    (isLambdaCall ? "лямбда-функции" : "функции") +
                                    " '" + funcName + "'. Ожидается: " +
                                    signature.parameterTypes.size() + ", получено: " + args.size());
                    return null;
                }

                for (int i = 0; i < args.size(); i++) {
                    Type argType = types.get(args.get(i));
                    Type expectedType = i < signature.parameterTypes.size() ?
                            signature.parameterTypes.get(i) : signature.parameterTypes.get(signature.parameterTypes.size() - 1);

                    if (argType != Type.ERROR && argType != expectedType &&
                            !(argType == Type.NIL && expectedType != Type.INT && expectedType != Type.BOOLEAN)) {
                        error(ctx.getStart().getLine(),
                                "Неверный тип аргумента " + (i + 1) + " для " +
                                        (isLambdaCall ? "лямбда-функции" : "функции") +
                                        " '" + funcName + "'. Ожидается: " + expectedType + ", получено: " + argType);
                    }
                }
            }
        }

        return null;
    }

    private Type findVariableType(String varName) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(varName)) {
                return scopes.get(i).get(varName);
            }
        }
        return null;
    }

    // Метод для проверки, является ли вызов лямбда-функцией
    private boolean isLambdaCall(String funcName) {
        // Проверяем, есть ли переменная с таким именем и имеет ли она тип FUNCTION
        Type varType = findVariableType(funcName);
        if (varType == Type.FUNCTION) {
            return true;
        }

        // Проверяем, является ли это лямбда-функцией в таблице функций
        return functions.containsKey(funcName) && functions.get(funcName).isLambda;
    }
    @Override
    public Void visitFuncCallExpr(GPLParser.FuncCallExprContext ctx) {
        visit(ctx.functionCall());
        types.put(ctx, types.get(ctx.functionCall()));
        return null;
    }

    @Override
    public Void visitLiteralExpr(GPLParser.LiteralExprContext ctx) {
        visit(ctx.literal());
        types.put(ctx, types.get(ctx.literal()));
        return null;
    }

    @Override
    public Void visitIdExpr(GPLParser.IdExprContext ctx) {
        String varName = ctx.ID().getText();
        Type varType = null;

        for (int i = scopes.size() - 1; i >= 0; i--) {
            if (scopes.get(i).containsKey(varName)) {
                varType = scopes.get(i).get(varName);
                break;
            }
        }

        if (varType == null) {
            error(ctx.getStart().getLine(), "Неизвестная переменная: '" + varName + "'");
            types.put(ctx, Type.ERROR);
        } else {
            types.put(ctx, varType);
        }
        return null;
    }

    @Override
    public Void visitParenExpr(GPLParser.ParenExprContext ctx) {
        visit(ctx.expression());
        types.put(ctx, types.get(ctx.expression()));
        return null;
    }

    @Override
    public Void visitMemberAccessExpr(GPLParser.MemberAccessExprContext ctx) {
        visit(ctx.expression());
        types.put(ctx, Type.ERROR);
        error(ctx.getStart().getLine(), "Доступ к членам не поддерживается");
        return null;
    }

    @Override
    public Void visitIndexAccessExpr(GPLParser.IndexAccessExprContext ctx) {
        visit(ctx.expression(0));
        visit(ctx.expression(1));
        types.put(ctx, Type.ERROR);
        error(ctx.getStart().getLine(), "Индексный доступ не поддерживается");
        return null;
    }

    @Override
    public Void visitUnaryMinusExpr(GPLParser.UnaryMinusExprContext ctx) {
        visit(ctx.expression());
        Type exprType = types.get(ctx.expression());

        if (exprType != Type.INT) {
            error(ctx.getStart().getLine(), "Унарный минус применим только к типу int");
            types.put(ctx, Type.ERROR);
        } else {
            types.put(ctx, Type.INT);
        }
        return null;
    }

    @Override
    public Void visitNotExpr(GPLParser.NotExprContext ctx) {
        visit(ctx.expression());
        Type exprType = types.get(ctx.expression());

        if (exprType != Type.BOOLEAN) {
            error(ctx.getStart().getLine(), "Логическое отрицание применимо только к типу boolean");
            types.put(ctx, Type.ERROR);
        } else {
            types.put(ctx, Type.BOOLEAN);
        }
        return null;
    }

    @Override
    public Void visitMultExpr(GPLParser.MultExprContext ctx) {
        return visitBinaryExpression(ctx.expression(0), ctx.expression(1), ctx.op.getText());
    }

    @Override
    public Void visitAddSubExpr(GPLParser.AddSubExprContext ctx) {
        return visitBinaryExpression(ctx.expression(0), ctx.expression(1), ctx.op.getText());
    }

    @Override
    public Void visitComparisonExpr(GPLParser.ComparisonExprContext ctx) {
        return visitBinaryExpression(ctx.expression(0), ctx.expression(1), ctx.op.getText());
    }

    @Override
    public Void visitLogicalExpr(GPLParser.LogicalExprContext ctx) {
        return visitBinaryExpression(ctx.expression(0), ctx.expression(1), ctx.op.getText());
    }

    private Void visitBinaryExpression(GPLParser.ExpressionContext left, GPLParser.ExpressionContext right, String op) {
        visit(left);
        visit(right);

        Type leftType = types.get(left);
        Type rightType = types.get(right);

        // Специальные правила для операций с графами
        switch (op) {
            case "+" -> {
                if (leftType == Type.GRAPH) {
                    if (rightType == Type.NODE || rightType == Type.ARC || rightType == Type.GRAPH || rightType == Type.NIL) {
                        // Разрешаем добавление NIL к графу (просто игнорируем)
                        types.put(left.getParent(), Type.GRAPH);
                    } else {
                        error(left.getStart().getLine(),
                                "Несовместимые типы для операции '+': GRAPH и " + rightType);
                        types.put(left.getParent(), Type.ERROR);
                    }
                } else if (leftType == Type.INT && rightType == Type.INT) {
                    types.put(left.getParent(), Type.INT);
                } else if (leftType == Type.STRING && rightType == Type.STRING) {
                    types.put(left.getParent(), Type.STRING);
                } else {
                    error(left.getStart().getLine(),
                            "Несовместимые типы для операции '+': " + leftType + " и " + rightType);
                    types.put(left.getParent(), Type.ERROR);
                }
            }
            case "-" -> {
                if (leftType == Type.GRAPH) {
                    if (rightType == Type.NODE || rightType == Type.ARC || rightType == Type.GRAPH) {
                        types.put(left.getParent(), Type.GRAPH);
                    } else {
                        error(left.getStart().getLine(),
                                "Несовместимые типы для операции '-': GRAPH и " + rightType);
                        types.put(left.getParent(), Type.ERROR);
                    }
                } else if (leftType == Type.INT && rightType == Type.INT) {
                    types.put(left.getParent(), Type.INT);
                } else {
                    error(left.getStart().getLine(),
                            "Несовместимые типы для операции '-': " + leftType + " и " + rightType);
                    types.put(left.getParent(), Type.ERROR);
                }
            }
            case "*" -> {
                if (leftType == Type.GRAPH && rightType == Type.GRAPH) {
                    // Пересечение графов
                    types.put(left.getParent(), Type.GRAPH);
                } else if (leftType == Type.INT && rightType == Type.INT) {
                    types.put(left.getParent(), Type.INT);
                } else {
                    error(left.getStart().getLine(),
                            "Несовместимые типы для операции '*': " + leftType + " и " + rightType);
                    types.put(left.getParent(), Type.ERROR);
                }
            }
            case "/" -> {
                if (leftType == Type.GRAPH && rightType == Type.GRAPH) {
                    // Разность графов
                    types.put(left.getParent(), Type.GRAPH);
                } else if (leftType == Type.INT && rightType == Type.INT) {
                    types.put(left.getParent(), Type.INT);
                } else {
                    error(left.getStart().getLine(),
                            "Несовместимые типы для операции '/': " + leftType + " и " + rightType);
                    types.put(left.getParent(), Type.ERROR);
                }
            }
            case "==", "!=" -> {
                // Сравнение разрешено для любых типов, включая NIL
                if (leftType != rightType && leftType != Type.NIL && rightType != Type.NIL) {
                    error(left.getStart().getLine(),
                            "Несовместимые типы для операции '" + op + "': " + leftType + " и " + rightType);
                    types.put(left.getParent(), Type.ERROR);
                } else {
                    types.put(left.getParent(), Type.BOOLEAN);
                }
            }
            case "and", "or", "&&", "||" -> {
                // Логические операции только для boolean
                if (leftType != Type.BOOLEAN || rightType != Type.BOOLEAN) {
                    error(left.getStart().getLine(),
                            "Логические операции поддерживаются только для типа boolean");
                    types.put(left.getParent(), Type.ERROR);
                } else {
                    types.put(left.getParent(), Type.BOOLEAN);
                }
            }
            case "<", ">", "<=", ">=" -> {
                // Операции сравнения только для чисел
                if (leftType != Type.INT || rightType != Type.INT) {
                    error(left.getStart().getLine(),
                            "Операции сравнения поддерживаются только для типа int");
                    types.put(left.getParent(), Type.ERROR);
                } else {
                    types.put(left.getParent(), Type.BOOLEAN);
                }
            }
            default -> {
                // Для других операций проверяем INT
                if (leftType != Type.INT || rightType != Type.INT) {
                    error(left.getStart().getLine(),
                            "Операция '" + op + "' поддерживается только для типа int");
                    types.put(left.getParent(), Type.ERROR);
                } else {
                    types.put(left.getParent(), Type.INT);
                }
            }
        }

        return null;
    }

    @Override
    public Void visitLiteral(GPLParser.LiteralContext ctx) {
        if (ctx.INT() != null) {
            types.put(ctx, Type.INT);
        } else if (ctx.STRING() != null) {
            types.put(ctx, Type.STRING);
        } else if (ctx.CHAR() != null) {
            types.put(ctx, Type.CHAR);
        } else if (ctx.NIL() != null) {
            types.put(ctx, Type.NIL);
        } else if (ctx.TRUE() != null || ctx.FALSE() != null) {
            types.put(ctx, Type.BOOLEAN);
        }
        return null;
    }

    @Override
    public Void visitForStatement(GPLParser.ForStatementContext ctx) {
        // Создаем новую область видимости для цикла
        scopes.push(new HashMap<>());

        // Добавляем переменную цикла в область видимости
        String varName = ctx.ID().getText();
        Type varType = getType(ctx.type().getText());
        scopes.peek().put(varName, varType);

        // Проверяем выражение после IN
        visit(ctx.expression());
        Type exprType = types.get(ctx.expression());

        // Выражение должно быть списком
        if (exprType != Type.LIST) {
            error(ctx.getStart().getLine(),
                    "Выражение в цикле for должно быть списком, получено: " + exprType);
        }

        // Обрабатываем тело цикла
        visit(ctx.statement());

        // Удаляем область видимости цикла
        scopes.pop();
        return null;
    }

    @Override
    public Void visitSwitchStatement(GPLParser.SwitchStatementContext ctx) {
        // Проверяем тип выражения switch
        visit(ctx.expression());
        Type switchType = types.get(ctx.expression());

        if (switchType != Type.INT && switchType != Type.STRING && switchType != Type.BOOLEAN) {
            error(ctx.getStart().getLine(), "Тип выражения в switch должен быть int, string или boolean, а не " + switchType);
        }

        // Множество для отслеживания дубликатов case
        Set<String> caseValues = new HashSet<>();

        // Обрабатываем все case-блоки
        List<GPLParser.SwitchCaseContext> caseBlocks = ctx.switchCase();
        for (GPLParser.SwitchCaseContext caseCtx : caseBlocks) {
            GPLParser.LiteralContext caseLiteral = caseCtx.literal();
            visit(caseLiteral); // Устанавливаем тип литерала
            Type caseType = types.get(caseLiteral);

            // Проверяем совместимость типа case с типом switch
            if (caseType != switchType) {
                error(caseLiteral.getStart().getLine(),
                        "Тип case (" + caseType + ") не совместим с типом switch (" + switchType + ")");
            }

            // Проверяем уникальность значения case
            String caseValue = caseLiteral.getText();
            if (!caseValues.add(caseValue)) {
                error(caseLiteral.getStart().getLine(), "Дублирующееся значение case: " + caseValue);
            }

            // Посещаем все операторы внутри case
            for (GPLParser.StatementContext stmt : caseCtx.statement()) {
                visit(stmt);
            }
        }

        // Обрабатываем default-блок, если он есть
        if (ctx.DEFAULT() != null) {
            for (GPLParser.StatementContext stmt : ctx.statement()) {
                visit(stmt);
            }
        }

        return null;
    }

    @Override
    public Void visitFunctionDefinition(GPLParser.FunctionDefinitionContext ctx) {
        String funcName = ctx.ID().getText();

        // Определяем возвращаемый тип. Если не указан, по умолчанию — VOID.
        Type returnType = Type.VOID;
        if (ctx.type() != null) {
            returnType = getType(ctx.type().getText());
        }

        // Собираем информацию о параметрах
        List<GPLParser.ParameterContext> params = ctx.parameter();
        List<Type> paramTypes = new ArrayList<>();
        List<Boolean> refParams = new ArrayList<>();

        for (GPLParser.ParameterContext param : params) {
            Type paramType = getType(param.type().getText());
            paramTypes.add(paramType);
            // Проверяем наличие модификатора ref
            refParams.add(param.REF() != null);
        }

        // Регистрируем функцию с её полной сигнатурой
        functions.put(funcName, new FunctionSignature(returnType, paramTypes, refParams));

        // Создаем новую область видимости для параметров и локальных переменных функции
        scopes.push(new HashMap<>());

        // Добавляем параметры в область видимости
        for (int i = 0; i < params.size(); i++) {
            GPLParser.ParameterContext param = params.get(i);
            String paramName = param.ID().getText();
            Type paramType = paramTypes.get(i);
            scopes.peek().put(paramName, paramType);
        }

        // Обрабатываем тело функции
        visit(ctx.block());

        // Закрываем область видимости
        scopes.pop();
        return null;
    }


    @Override
    public Void visitReturnStatement(GPLParser.ReturnStatementContext ctx) {
        // Сначала проверяем, находимся ли мы внутри лямбда-функции
        if (!lambdaContexts.isEmpty()) {
            // Мы внутри лямбда-выражения
            LambdaContext lambdaCtx = lambdaContexts.peek();

            if (ctx.expression() != null) {
                visit(ctx.expression());
                Type actualReturnType = types.get(ctx.expression());

                // Обновляем возвращаемый тип лямбды
                lambdaCtx.returnType = actualReturnType;

                // Проверяем совместимость типов, если тип уже был установлен ранее
                if (lambdaCtx.returnType != actualReturnType &&
                        !(actualReturnType == Type.NIL && lambdaCtx.returnType != Type.INT && lambdaCtx.returnType != Type.BOOLEAN)) {
                    error(ctx.getStart().getLine(),
                            "Тип возвращаемого значения в лямбде (" + actualReturnType +
                                    ") не совпадает с ожидаемым типом (" + lambdaCtx.returnType + ")");
                }
            } else {
                // Пустой return в лямбде - устанавливаем VOID
                lambdaCtx.returnType = Type.VOID;
            }
        } else {
            // Старая логика для обычных функций
            // Находим ближайшее определение функции в дереве
            ParseTree parent = ctx.getParent();
            while (parent != null && !(parent instanceof GPLParser.FunctionDefinitionContext)) {
                parent = parent.getParent();
            }

            if (parent == null) {
                error(ctx.getStart().getLine(), "Оператор return вне тела функции");
                return null;
            }

            GPLParser.FunctionDefinitionContext funcCtx = (GPLParser.FunctionDefinitionContext) parent;
            String funcName = funcCtx.ID().getText();

            // Получаем ожидаемый тип возврата из сигнатуры функции
            Type expectedReturnType = functions.containsKey(funcName) ?
                    functions.get(funcName).returnType : Type.VOID;

            if (ctx.expression() != null) {
                visit(ctx.expression());
                Type actualReturnType = types.get(ctx.expression());

                if (actualReturnType != expectedReturnType &&
                        !(actualReturnType == Type.NIL && expectedReturnType != Type.INT && expectedReturnType != Type.BOOLEAN)) {
                    error(ctx.getStart().getLine(),
                            "Тип возвращаемого значения (" + actualReturnType +
                                    ") не совпадает с типом функции (" + expectedReturnType + ")");
                }
            } else {
                // Пустой return разрешен только для void-функций
                if (expectedReturnType != Type.VOID) {
                    error(ctx.getStart().getLine(),
                            "Функция '" + funcName + "' должна вернуть значение типа " + expectedReturnType);
                }
            }
        }

        return null;
    }
    @Override
    public Void visitLambdaExpr(GPLParser.LambdaExprContext ctx) {
        visit(ctx.lambdaExpression());
        types.put(ctx, types.get(ctx.lambdaExpression()));
        return null;
    }

    @Override
    public Void visitLambdaExpression(GPLParser.LambdaExpressionContext ctx) {
        // Собираем информацию о параметрах лямбды
        List<Type> paramTypes = new ArrayList<>();
        List<GPLParser.ParameterContext> params = new ArrayList<>();

        // Обрабатываем параметры лямбды
        if (ctx.LPAREN() != null) {
            // Случай с несколькими параметрами в скобках
            params = ctx.parameter(); // Это уже список
        } else {
            // Случай с одним параметром без скобок
            // ctx.parameter() возвращает список, но с одним элементом
            params = ctx.parameter();
        }

        for (GPLParser.ParameterContext param : params) {
            Type paramType = getType(param.type().getText());
            paramTypes.add(paramType);
        }

        // Создаем новую область видимости для лямбды
        scopes.push(new HashMap<>());
        lambdaContexts.push(new LambdaContext(paramTypes, Type.VOID));

        // Добавляем параметры в область видимости лямбды
        for (int i = 0; i < params.size(); i++) {
            GPLParser.ParameterContext param = params.get(i);
            String paramName = param.ID().getText();
            Type paramType = paramTypes.get(i);
            scopes.peek().put(paramName, paramType);
        }

        // Обрабатываем тело лямбды
        Type returnType = Type.VOID;
        if (ctx.lambdaBody().expression() != null) {
            // Лямбда с выражением - тип возврата = тип выражения
            visit(ctx.lambdaBody().expression());
            returnType = types.get(ctx.lambdaBody().expression());
        } else {
            // Лямбда с блоком - ищем return statement
            visit(ctx.lambdaBody().block());
            // Для простоты считаем, что тип возврата определяется первым return
            returnType = lambdaContexts.peek().returnType;
        }

        // Обновляем контекст лямбды с правильным возвращаемым типом
        LambdaContext currentLambdaCtx = lambdaContexts.pop();
        lambdaContexts.push(new LambdaContext(paramTypes, returnType));

        // Устанавливаем тип лямбда-выражения
        types.put(ctx, Type.FUNCTION);

        // Выходим из области видимости лямбды
        scopes.pop();

        return null;
    }

    @Override
    public Void visitLambdaAssignment(GPLParser.LambdaAssignmentContext ctx) {
        String lambdaName = ctx.ID().getText();

        // Проверяем, что переменная не объявлена в текущей области видимости
        if (scopes.peek().containsKey(lambdaName)) {
            error(ctx.getStart().getLine(), "Переменная '" + lambdaName + "' уже объявлена");
            return null;
        }

        // Обрабатываем лямбда-выражение
        visit(ctx.lambdaExpression());
        Type lambdaType = types.get(ctx.lambdaExpression());

        if (lambdaType != Type.ERROR && !lambdaContexts.isEmpty()) {
            scopes.peek().put(lambdaName, Type.FUNCTION);
            // Сохраняем сигнатуру лямбда-функции
            LambdaContext lambdaCtx = lambdaContexts.peek();

            // Отладочная информация
            System.out.println("Регистрируем лямбда-функцию: " + lambdaName +
                    " с параметрами: " + lambdaCtx.parameterTypes +
                    " и возвращаемым типом: " + lambdaCtx.returnType);

            functions.put(lambdaName, new FunctionSignature(
                    lambdaCtx.returnType, lambdaCtx.parameterTypes, true));

            // Удаляем контекст лямбды из стека после сохранения
            lambdaContexts.pop();
        } else {
            error(ctx.getStart().getLine(), "Не удалось обработать лямбда-выражение для '" + lambdaName + "'");
        }

        return null;
    }

    private List<Boolean> createBooleanList(int size, boolean value) {
        List<Boolean> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(value);
        }
        return list;
    }

}