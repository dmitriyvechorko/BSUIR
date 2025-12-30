package com.gpl.parser;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.ArrayList;
import java.util.List;

public class GPLSyntaxAnalyzer {
    private final List<String> errors = new ArrayList<>();
    private ParseTree tree;

    public void analyze(String input) {
        errors.clear();

        // Синтаксический анализ
        CharStream stream = CharStreams.fromString(input);
        GPLParser parser = getGplParser(stream);

        try {
            tree = parser.program();

            // Если нет синтаксических ошибок, выполняем семантический анализ
            if (errors.isEmpty()) {
                SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
                semanticAnalyzer.visit(tree);
                errors.addAll(semanticAnalyzer.getErrors());
            }

            printAnalysisResults();
        } catch (Exception e) {
            errors.add("Критическая ошибка: " + e.getMessage());
        }
    }

    private GPLParser getGplParser(CharStream stream) {
        GPLLexer lexer = new GPLLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GPLParser parser = new GPLParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine,
                                    String msg, RecognitionException e) {
                errors.add("Синтаксическая ошибка в строке " + line + ":" + charPositionInLine + " - " + msg);
            }
        });
        return parser;
    }

    public String compile(String input) {
        analyze(input);

        if (!errors.isEmpty()) {
            return "Компиляция невозможна из-за ошибок\n";
        }

        GPLCompiler compiler = new GPLCompiler();
        return compiler.compile(tree);
    }

    private void printAnalysisResults() {
        System.out.println("=" .repeat(50));
        if (errors.isEmpty()) {
            System.out.println("Синтаксический анализ завершен успешно");
            System.out.println("Дерево разбора:");
            System.out.println(tree.toStringTree(new GPLParser(null)));
        } else {
            System.out.println("Обнаружены ошибки (" + errors.size() + "):");
            for (String error : errors) {
                System.out.println("  • " + error);
            }
        }
        System.out.println("=" .repeat(50));
    }

}
