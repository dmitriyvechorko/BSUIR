// Generated from D:/GPLWithSemAndSynAnaluzer/GPL.g4 by ANTLR 4.13.2
package com.gpl.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GPLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GPLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GPLParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(GPLParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#globalDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalDeclaration(GPLParser.GlobalDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(GPLParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(GPLParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(GPLParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(GPLParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#lambdaAssignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaAssignment(GPLParser.LambdaAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#lambdaExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaExpression(GPLParser.LambdaExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#lambdaParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaParameters(GPLParser.LambdaParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#lambdaBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaBody(GPLParser.LambdaBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#parameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameter(GPLParser.ParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(GPLParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#functionName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionName(GPLParser.FunctionNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(GPLParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(GPLParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#untilStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUntilStatement(GPLParser.UntilStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#switchStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchStatement(GPLParser.SwitchStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#switchCase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchCase(GPLParser.SwitchCaseContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(GPLParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#functionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinition(GPLParser.FunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(GPLParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(GPLParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(GPLParser.ContinueStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(GPLParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#baseType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseType(GPLParser.BaseTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addSubExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSubExpr(GPLParser.AddSubExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicalExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalExpr(GPLParser.LogicalExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code comparisonExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonExpr(GPLParser.ComparisonExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcCallExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCallExpr(GPLParser.FuncCallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultExpr(GPLParser.MultExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(GPLParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotExpr(GPLParser.NotExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryMinusExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryMinusExpr(GPLParser.UnaryMinusExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code lambdaExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaExpr(GPLParser.LambdaExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code indexAccessExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIndexAccessExpr(GPLParser.IndexAccessExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code literalExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteralExpr(GPLParser.LiteralExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code memberAccessExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberAccessExpr(GPLParser.MemberAccessExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link GPLParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpr(GPLParser.IdExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link GPLParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(GPLParser.LiteralContext ctx);
}