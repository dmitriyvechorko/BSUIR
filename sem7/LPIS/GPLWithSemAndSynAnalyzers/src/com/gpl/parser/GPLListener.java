// Generated from D:/GPLWithSemAndSynAnaluzer/GPL.g4 by ANTLR 4.13.2
package com.gpl.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GPLParser}.
 */
public interface GPLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GPLParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(GPLParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(GPLParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#globalDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGlobalDeclaration(GPLParser.GlobalDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#globalDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGlobalDeclaration(GPLParser.GlobalDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(GPLParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(GPLParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(GPLParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(GPLParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(GPLParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(GPLParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(GPLParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(GPLParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#lambdaAssignment}.
	 * @param ctx the parse tree
	 */
	void enterLambdaAssignment(GPLParser.LambdaAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#lambdaAssignment}.
	 * @param ctx the parse tree
	 */
	void exitLambdaAssignment(GPLParser.LambdaAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExpression(GPLParser.LambdaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExpression(GPLParser.LambdaExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#lambdaParameters}.
	 * @param ctx the parse tree
	 */
	void enterLambdaParameters(GPLParser.LambdaParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#lambdaParameters}.
	 * @param ctx the parse tree
	 */
	void exitLambdaParameters(GPLParser.LambdaParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#lambdaBody}.
	 * @param ctx the parse tree
	 */
	void enterLambdaBody(GPLParser.LambdaBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#lambdaBody}.
	 * @param ctx the parse tree
	 */
	void exitLambdaBody(GPLParser.LambdaBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#parameter}.
	 * @param ctx the parse tree
	 */
	void enterParameter(GPLParser.ParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#parameter}.
	 * @param ctx the parse tree
	 */
	void exitParameter(GPLParser.ParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(GPLParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(GPLParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#functionName}.
	 * @param ctx the parse tree
	 */
	void enterFunctionName(GPLParser.FunctionNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#functionName}.
	 * @param ctx the parse tree
	 */
	void exitFunctionName(GPLParser.FunctionNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(GPLParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(GPLParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(GPLParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(GPLParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#untilStatement}.
	 * @param ctx the parse tree
	 */
	void enterUntilStatement(GPLParser.UntilStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#untilStatement}.
	 * @param ctx the parse tree
	 */
	void exitUntilStatement(GPLParser.UntilStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#switchStatement}.
	 * @param ctx the parse tree
	 */
	void enterSwitchStatement(GPLParser.SwitchStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#switchStatement}.
	 * @param ctx the parse tree
	 */
	void exitSwitchStatement(GPLParser.SwitchStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#switchCase}.
	 * @param ctx the parse tree
	 */
	void enterSwitchCase(GPLParser.SwitchCaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#switchCase}.
	 * @param ctx the parse tree
	 */
	void exitSwitchCase(GPLParser.SwitchCaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(GPLParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(GPLParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(GPLParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(GPLParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(GPLParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(GPLParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(GPLParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(GPLParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(GPLParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(GPLParser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(GPLParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(GPLParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#baseType}.
	 * @param ctx the parse tree
	 */
	void enterBaseType(GPLParser.BaseTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#baseType}.
	 * @param ctx the parse tree
	 */
	void exitBaseType(GPLParser.BaseTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addSubExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddSubExpr(GPLParser.AddSubExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addSubExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddSubExpr(GPLParser.AddSubExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalExpr(GPLParser.LogicalExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalExpr(GPLParser.LogicalExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code comparisonExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterComparisonExpr(GPLParser.ComparisonExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code comparisonExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitComparisonExpr(GPLParser.ComparisonExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcCallExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFuncCallExpr(GPLParser.FuncCallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcCallExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFuncCallExpr(GPLParser.FuncCallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMultExpr(GPLParser.MultExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMultExpr(GPLParser.MultExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(GPLParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(GPLParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNotExpr(GPLParser.NotExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNotExpr(GPLParser.NotExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryMinusExpr(GPLParser.UnaryMinusExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryMinusExpr(GPLParser.UnaryMinusExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lambdaExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExpr(GPLParser.LambdaExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lambdaExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExpr(GPLParser.LambdaExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code indexAccessExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIndexAccessExpr(GPLParser.IndexAccessExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code indexAccessExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIndexAccessExpr(GPLParser.IndexAccessExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code literalExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLiteralExpr(GPLParser.LiteralExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code literalExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLiteralExpr(GPLParser.LiteralExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberAccessExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccessExpr(GPLParser.MemberAccessExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberAccessExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccessExpr(GPLParser.MemberAccessExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdExpr(GPLParser.IdExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdExpr(GPLParser.IdExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GPLParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(GPLParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link GPLParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(GPLParser.LiteralContext ctx);
}