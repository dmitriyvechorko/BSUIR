// Generated from D:/GPLWithSemAndSynAnaluzer/GPL.g4 by ANTLR 4.13.2
package com.gpl.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class GPLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PROGRAM=1, FUNCTION_TYPE=2, VAR=3, FUNC=4, IF=5, SWITCH=6, CASE=7, DEFAULT=8, 
		THEN=9, ELSE=10, WHILE=11, UNTIL=12, FOR=13, IN=14, RETURN=15, BREAK=16, 
		CONTINUE=17, NIL=18, TRUE=19, FALSE=20, GRAPH=21, NODE=22, ARC=23, LIST=24, 
		INT_TYPE=25, STRING_TYPE=26, BOOLEAN_TYPE=27, REF=28, ARRAY=29, CHAR_TYPE=30, 
		FIND=31, REPLACE=32, SLICE=33, SPLIT=34, QUESTION=35, MUL=36, DIV=37, 
		MOD=38, ADD=39, SUB=40, LT=41, GT=42, LE=43, GE=44, EQ=45, NE=46, AND=47, 
		OR=48, AND_WORD=49, OR_WORD=50, ASSIGN=51, COMMA=52, COLON=53, SEMICOLON=54, 
		LPAREN=55, RPAREN=56, LBRACE=57, RBRACE=58, LBRACK=59, RBRACK=60, DOT=61, 
		NOT=62, ARROW=63, ID=64, INT=65, CHAR=66, STRING=67, WS=68, COMMENT=69, 
		BLOCK_COMMENT=70;
	public static final int
		RULE_program = 0, RULE_globalDeclaration = 1, RULE_block = 2, RULE_statement = 3, 
		RULE_variableDeclaration = 4, RULE_assignment = 5, RULE_lambdaAssignment = 6, 
		RULE_lambdaExpression = 7, RULE_lambdaParameters = 8, RULE_lambdaBody = 9, 
		RULE_parameter = 10, RULE_functionCall = 11, RULE_functionName = 12, RULE_ifStatement = 13, 
		RULE_whileStatement = 14, RULE_untilStatement = 15, RULE_switchStatement = 16, 
		RULE_switchCase = 17, RULE_forStatement = 18, RULE_functionDefinition = 19, 
		RULE_returnStatement = 20, RULE_breakStatement = 21, RULE_continueStatement = 22, 
		RULE_type = 23, RULE_baseType = 24, RULE_expression = 25, RULE_literal = 26;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "globalDeclaration", "block", "statement", "variableDeclaration", 
			"assignment", "lambdaAssignment", "lambdaExpression", "lambdaParameters", 
			"lambdaBody", "parameter", "functionCall", "functionName", "ifStatement", 
			"whileStatement", "untilStatement", "switchStatement", "switchCase", 
			"forStatement", "functionDefinition", "returnStatement", "breakStatement", 
			"continueStatement", "type", "baseType", "expression", "literal"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'program'", "'function'", "'var'", "'func'", "'if'", "'switch'", 
			"'case'", "'default'", "'then'", "'else'", "'while'", "'until'", "'for'", 
			"'in'", "'return'", "'break'", "'continue'", "'nil'", "'true'", "'false'", 
			"'graph'", "'node'", "'arc'", "'list'", "'int'", "'string'", "'boolean'", 
			"'ref'", "'array'", "'char'", "'find'", "'replace'", "'slice'", "'split'", 
			"'?'", "'*'", "'/'", "'%'", "'+'", "'-'", "'<'", "'>'", "'<='", "'>='", 
			"'=='", "'!='", "'&&'", "'||'", "'and'", "'or'", "'='", "','", "':'", 
			"';'", "'('", "')'", "'{'", "'}'", "'['", "']'", "'.'", "'!'", "'->'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PROGRAM", "FUNCTION_TYPE", "VAR", "FUNC", "IF", "SWITCH", "CASE", 
			"DEFAULT", "THEN", "ELSE", "WHILE", "UNTIL", "FOR", "IN", "RETURN", "BREAK", 
			"CONTINUE", "NIL", "TRUE", "FALSE", "GRAPH", "NODE", "ARC", "LIST", "INT_TYPE", 
			"STRING_TYPE", "BOOLEAN_TYPE", "REF", "ARRAY", "CHAR_TYPE", "FIND", "REPLACE", 
			"SLICE", "SPLIT", "QUESTION", "MUL", "DIV", "MOD", "ADD", "SUB", "LT", 
			"GT", "LE", "GE", "EQ", "NE", "AND", "OR", "AND_WORD", "OR_WORD", "ASSIGN", 
			"COMMA", "COLON", "SEMICOLON", "LPAREN", "RPAREN", "LBRACE", "RBRACE", 
			"LBRACK", "RBRACK", "DOT", "NOT", "ARROW", "ID", "INT", "CHAR", "STRING", 
			"WS", "COMMENT", "BLOCK_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "GPL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GPLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode PROGRAM() { return getToken(GPLParser.PROGRAM, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TerminalNode EOF() { return getToken(GPLParser.EOF, 0); }
		public List<GlobalDeclarationContext> globalDeclaration() {
			return getRuleContexts(GlobalDeclarationContext.class);
		}
		public GlobalDeclarationContext globalDeclaration(int i) {
			return getRuleContext(GlobalDeclarationContext.class,i);
		}
		public List<FunctionDefinitionContext> functionDefinition() {
			return getRuleContexts(FunctionDefinitionContext.class);
		}
		public FunctionDefinitionContext functionDefinition(int i) {
			return getRuleContext(FunctionDefinitionContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VAR || _la==FUNC) {
				{
				setState(56);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case VAR:
					{
					setState(54);
					globalDeclaration();
					}
					break;
				case FUNC:
					{
					setState(55);
					functionDefinition();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(61);
			match(PROGRAM);
			setState(62);
			block();
			setState(63);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class GlobalDeclarationContext extends ParserRuleContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public GlobalDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globalDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterGlobalDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitGlobalDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitGlobalDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalDeclarationContext globalDeclaration() throws RecognitionException {
		GlobalDeclarationContext _localctx = new GlobalDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_globalDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(65);
			variableDeclaration();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LBRACE() { return getToken(GPLParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(GPLParser.RBRACE, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			match(LBRACE);
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 3)) & ~0x3f) == 0 && ((1L << (_la - 3)) & 2323857407756498703L) != 0)) {
				{
				{
				setState(68);
				statement();
				}
				}
				setState(73);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(74);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GPLParser.SEMICOLON, 0); }
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public SwitchStatementContext switchStatement() {
			return getRuleContext(SwitchStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public UntilStatementContext untilStatement() {
			return getRuleContext(UntilStatementContext.class,0);
		}
		public ForStatementContext forStatement() {
			return getRuleContext(ForStatementContext.class,0);
		}
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public BreakStatementContext breakStatement() {
			return getRuleContext(BreakStatementContext.class,0);
		}
		public ContinueStatementContext continueStatement() {
			return getRuleContext(ContinueStatementContext.class,0);
		}
		public LambdaAssignmentContext lambdaAssignment() {
			return getRuleContext(LambdaAssignmentContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_statement);
		try {
			setState(92);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(76);
				variableDeclaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(77);
				assignment();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(78);
				functionCall();
				setState(79);
				match(SEMICOLON);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(81);
				ifStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(82);
				switchStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(83);
				whileStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(84);
				untilStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(85);
				forStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(86);
				functionDefinition();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(87);
				returnStatement();
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(88);
				breakStatement();
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(89);
				continueStatement();
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(90);
				lambdaAssignment();
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(91);
				block();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableDeclarationContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(GPLParser.VAR, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode QUESTION() { return getToken(GPLParser.QUESTION, 0); }
		public TerminalNode ID() { return getToken(GPLParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(GPLParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GPLParser.SEMICOLON, 0); }
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterVariableDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitVariableDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_variableDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			match(VAR);
			setState(95);
			type();
			setState(96);
			match(QUESTION);
			setState(97);
			match(ID);
			setState(98);
			match(ASSIGN);
			setState(99);
			expression(0);
			setState(100);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignmentContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GPLParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(GPLParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GPLParser.SEMICOLON, 0); }
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			match(ID);
			setState(103);
			match(ASSIGN);
			setState(104);
			expression(0);
			setState(105);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LambdaAssignmentContext extends ParserRuleContext {
		public TerminalNode VAR() { return getToken(GPLParser.VAR, 0); }
		public TerminalNode FUNCTION_TYPE() { return getToken(GPLParser.FUNCTION_TYPE, 0); }
		public TerminalNode QUESTION() { return getToken(GPLParser.QUESTION, 0); }
		public TerminalNode ID() { return getToken(GPLParser.ID, 0); }
		public TerminalNode ASSIGN() { return getToken(GPLParser.ASSIGN, 0); }
		public LambdaExpressionContext lambdaExpression() {
			return getRuleContext(LambdaExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(GPLParser.SEMICOLON, 0); }
		public LambdaAssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdaAssignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterLambdaAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitLambdaAssignment(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitLambdaAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LambdaAssignmentContext lambdaAssignment() throws RecognitionException {
		LambdaAssignmentContext _localctx = new LambdaAssignmentContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_lambdaAssignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(VAR);
			setState(108);
			match(FUNCTION_TYPE);
			setState(109);
			match(QUESTION);
			setState(110);
			match(ID);
			setState(111);
			match(ASSIGN);
			setState(112);
			lambdaExpression();
			setState(113);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LambdaExpressionContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(GPLParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(GPLParser.RPAREN, 0); }
		public TerminalNode ARROW() { return getToken(GPLParser.ARROW, 0); }
		public LambdaBodyContext lambdaBody() {
			return getRuleContext(LambdaBodyContext.class,0);
		}
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GPLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GPLParser.COMMA, i);
		}
		public LambdaExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdaExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterLambdaExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitLambdaExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitLambdaExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LambdaExpressionContext lambdaExpression() throws RecognitionException {
		LambdaExpressionContext _localctx = new LambdaExpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_lambdaExpression);
		int _la;
		try {
			setState(133);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(115);
				match(LPAREN);
				setState(124);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2145386500L) != 0)) {
					{
					setState(116);
					parameter();
					setState(121);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(117);
						match(COMMA);
						setState(118);
						parameter();
						}
						}
						setState(123);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(126);
				match(RPAREN);
				setState(127);
				match(ARROW);
				setState(128);
				lambdaBody();
				}
				break;
			case FUNCTION_TYPE:
			case GRAPH:
			case NODE:
			case ARC:
			case LIST:
			case INT_TYPE:
			case STRING_TYPE:
			case BOOLEAN_TYPE:
			case REF:
			case ARRAY:
			case CHAR_TYPE:
				enterOuterAlt(_localctx, 2);
				{
				setState(129);
				parameter();
				setState(130);
				match(ARROW);
				setState(131);
				lambdaBody();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LambdaParametersContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(GPLParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(GPLParser.RPAREN, 0); }
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GPLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GPLParser.COMMA, i);
		}
		public LambdaParametersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdaParameters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterLambdaParameters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitLambdaParameters(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitLambdaParameters(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LambdaParametersContext lambdaParameters() throws RecognitionException {
		LambdaParametersContext _localctx = new LambdaParametersContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_lambdaParameters);
		int _la;
		try {
			setState(148);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LPAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(135);
				match(LPAREN);
				setState(144);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2145386500L) != 0)) {
					{
					setState(136);
					parameter();
					setState(141);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(137);
						match(COMMA);
						setState(138);
						parameter();
						}
						}
						setState(143);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(146);
				match(RPAREN);
				}
				break;
			case FUNCTION_TYPE:
			case GRAPH:
			case NODE:
			case ARC:
			case LIST:
			case INT_TYPE:
			case STRING_TYPE:
			case BOOLEAN_TYPE:
			case REF:
			case ARRAY:
			case CHAR_TYPE:
				enterOuterAlt(_localctx, 2);
				{
				setState(147);
				parameter();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LambdaBodyContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public LambdaBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdaBody; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterLambdaBody(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitLambdaBody(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitLambdaBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LambdaBodyContext lambdaBody() throws RecognitionException {
		LambdaBodyContext _localctx = new LambdaBodyContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_lambdaBody);
		try {
			setState(152);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FUNCTION_TYPE:
			case NIL:
			case TRUE:
			case FALSE:
			case GRAPH:
			case NODE:
			case ARC:
			case LIST:
			case INT_TYPE:
			case STRING_TYPE:
			case BOOLEAN_TYPE:
			case REF:
			case ARRAY:
			case CHAR_TYPE:
			case SUB:
			case LPAREN:
			case NOT:
			case ID:
			case INT:
			case CHAR:
			case STRING:
				enterOuterAlt(_localctx, 1);
				{
				setState(150);
				expression(0);
				}
				break;
			case LBRACE:
				enterOuterAlt(_localctx, 2);
				{
				setState(151);
				block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParameterContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode QUESTION() { return getToken(GPLParser.QUESTION, 0); }
		public TerminalNode ID() { return getToken(GPLParser.ID, 0); }
		public TerminalNode REF() { return getToken(GPLParser.REF, 0); }
		public ParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterParameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitParameter(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParameterContext parameter() throws RecognitionException {
		ParameterContext _localctx = new ParameterContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_parameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==REF) {
				{
				setState(154);
				match(REF);
				}
			}

			setState(157);
			type();
			setState(158);
			match(QUESTION);
			setState(159);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionCallContext extends ParserRuleContext {
		public FunctionNameContext functionName() {
			return getRuleContext(FunctionNameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(GPLParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(GPLParser.RPAREN, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GPLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GPLParser.COMMA, i);
		}
		public FunctionCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterFunctionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitFunctionCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionCallContext functionCall() throws RecognitionException {
		FunctionCallContext _localctx = new FunctionCallContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_functionCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			functionName();
			setState(162);
			match(LPAREN);
			setState(171);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4647715917105201156L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 15L) != 0)) {
				{
				setState(163);
				expression(0);
				setState(168);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(164);
					match(COMMA);
					setState(165);
					expression(0);
					}
					}
					setState(170);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(173);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionNameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GPLParser.ID, 0); }
		public TerminalNode GRAPH() { return getToken(GPLParser.GRAPH, 0); }
		public TerminalNode NODE() { return getToken(GPLParser.NODE, 0); }
		public TerminalNode ARC() { return getToken(GPLParser.ARC, 0); }
		public TerminalNode LIST() { return getToken(GPLParser.LIST, 0); }
		public TerminalNode INT_TYPE() { return getToken(GPLParser.INT_TYPE, 0); }
		public TerminalNode STRING_TYPE() { return getToken(GPLParser.STRING_TYPE, 0); }
		public TerminalNode BOOLEAN_TYPE() { return getToken(GPLParser.BOOLEAN_TYPE, 0); }
		public FunctionNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterFunctionName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitFunctionName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitFunctionName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionNameContext functionName() throws RecognitionException {
		FunctionNameContext _localctx = new FunctionNameContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_functionName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			_la = _input.LA(1);
			if ( !(((((_la - 21)) & ~0x3f) == 0 && ((1L << (_la - 21)) & 8796093022335L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(GPLParser.IF, 0); }
		public TerminalNode LPAREN() { return getToken(GPLParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GPLParser.RPAREN, 0); }
		public TerminalNode THEN() { return getToken(GPLParser.THEN, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(GPLParser.ELSE, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
			match(IF);
			setState(178);
			match(LPAREN);
			setState(179);
			expression(0);
			setState(180);
			match(RPAREN);
			setState(181);
			match(THEN);
			setState(182);
			statement();
			setState(185);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(183);
				match(ELSE);
				setState(184);
				statement();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhileStatementContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(GPLParser.WHILE, 0); }
		public TerminalNode LPAREN() { return getToken(GPLParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GPLParser.RPAREN, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterWhileStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitWhileStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			match(WHILE);
			setState(188);
			match(LPAREN);
			setState(189);
			expression(0);
			setState(190);
			match(RPAREN);
			setState(191);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UntilStatementContext extends ParserRuleContext {
		public TerminalNode UNTIL() { return getToken(GPLParser.UNTIL, 0); }
		public TerminalNode LPAREN() { return getToken(GPLParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GPLParser.RPAREN, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public UntilStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_untilStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterUntilStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitUntilStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitUntilStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UntilStatementContext untilStatement() throws RecognitionException {
		UntilStatementContext _localctx = new UntilStatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_untilStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			match(UNTIL);
			setState(194);
			match(LPAREN);
			setState(195);
			expression(0);
			setState(196);
			match(RPAREN);
			setState(197);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SwitchStatementContext extends ParserRuleContext {
		public TerminalNode SWITCH() { return getToken(GPLParser.SWITCH, 0); }
		public TerminalNode LPAREN() { return getToken(GPLParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GPLParser.RPAREN, 0); }
		public TerminalNode LBRACE() { return getToken(GPLParser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(GPLParser.RBRACE, 0); }
		public List<SwitchCaseContext> switchCase() {
			return getRuleContexts(SwitchCaseContext.class);
		}
		public SwitchCaseContext switchCase(int i) {
			return getRuleContext(SwitchCaseContext.class,i);
		}
		public TerminalNode DEFAULT() { return getToken(GPLParser.DEFAULT, 0); }
		public TerminalNode COLON() { return getToken(GPLParser.COLON, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public SwitchStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterSwitchStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitSwitchStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitSwitchStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchStatementContext switchStatement() throws RecognitionException {
		SwitchStatementContext _localctx = new SwitchStatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_switchStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
			match(SWITCH);
			setState(200);
			match(LPAREN);
			setState(201);
			expression(0);
			setState(202);
			match(RPAREN);
			setState(203);
			match(LBRACE);
			setState(205); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(204);
				switchCase();
				}
				}
				setState(207); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==CASE );
			setState(217);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEFAULT) {
				{
				setState(209);
				match(DEFAULT);
				setState(210);
				match(COLON);
				setState(214);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (((((_la - 3)) & ~0x3f) == 0 && ((1L << (_la - 3)) & 2323857407756498703L) != 0)) {
					{
					{
					setState(211);
					statement();
					}
					}
					setState(216);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(219);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SwitchCaseContext extends ParserRuleContext {
		public TerminalNode CASE() { return getToken(GPLParser.CASE, 0); }
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode COLON() { return getToken(GPLParser.COLON, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public SwitchCaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchCase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterSwitchCase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitSwitchCase(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitSwitchCase(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchCaseContext switchCase() throws RecognitionException {
		SwitchCaseContext _localctx = new SwitchCaseContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_switchCase);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			match(CASE);
			setState(222);
			literal();
			setState(223);
			match(COLON);
			setState(227);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 3)) & ~0x3f) == 0 && ((1L << (_la - 3)) & 2323857407756498703L) != 0)) {
				{
				{
				setState(224);
				statement();
				}
				}
				setState(229);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForStatementContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(GPLParser.FOR, 0); }
		public TerminalNode LPAREN() { return getToken(GPLParser.LPAREN, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ID() { return getToken(GPLParser.ID, 0); }
		public TerminalNode IN() { return getToken(GPLParser.IN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GPLParser.RPAREN, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public ForStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterForStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitForStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitForStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStatementContext forStatement() throws RecognitionException {
		ForStatementContext _localctx = new ForStatementContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_forStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(230);
			match(FOR);
			setState(231);
			match(LPAREN);
			setState(232);
			type();
			setState(233);
			match(ID);
			setState(234);
			match(IN);
			setState(235);
			expression(0);
			setState(236);
			match(RPAREN);
			setState(237);
			statement();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionDefinitionContext extends ParserRuleContext {
		public TerminalNode FUNC() { return getToken(GPLParser.FUNC, 0); }
		public TerminalNode ID() { return getToken(GPLParser.ID, 0); }
		public TerminalNode LPAREN() { return getToken(GPLParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(GPLParser.RPAREN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<ParameterContext> parameter() {
			return getRuleContexts(ParameterContext.class);
		}
		public ParameterContext parameter(int i) {
			return getRuleContext(ParameterContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GPLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GPLParser.COMMA, i);
		}
		public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterFunctionDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitFunctionDefinition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitFunctionDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDefinitionContext functionDefinition() throws RecognitionException {
		FunctionDefinitionContext _localctx = new FunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_functionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			match(FUNC);
			setState(241);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1876951044L) != 0)) {
				{
				setState(240);
				type();
				}
			}

			setState(243);
			match(ID);
			setState(244);
			match(LPAREN);
			setState(253);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2145386500L) != 0)) {
				{
				setState(245);
				parameter();
				setState(250);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(246);
					match(COMMA);
					setState(247);
					parameter();
					}
					}
					setState(252);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(255);
			match(RPAREN);
			setState(256);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(GPLParser.RETURN, 0); }
		public TerminalNode SEMICOLON() { return getToken(GPLParser.SEMICOLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitReturnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(258);
			match(RETURN);
			setState(260);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4647715917105201156L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 15L) != 0)) {
				{
				setState(259);
				expression(0);
				}
			}

			setState(262);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BreakStatementContext extends ParserRuleContext {
		public TerminalNode BREAK() { return getToken(GPLParser.BREAK, 0); }
		public TerminalNode SEMICOLON() { return getToken(GPLParser.SEMICOLON, 0); }
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterBreakStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitBreakStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(264);
			match(BREAK);
			setState(265);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ContinueStatementContext extends ParserRuleContext {
		public TerminalNode CONTINUE() { return getToken(GPLParser.CONTINUE, 0); }
		public TerminalNode SEMICOLON() { return getToken(GPLParser.SEMICOLON, 0); }
		public ContinueStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterContinueStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitContinueStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitContinueStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			match(CONTINUE);
			setState(268);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TerminalNode GRAPH() { return getToken(GPLParser.GRAPH, 0); }
		public TerminalNode NODE() { return getToken(GPLParser.NODE, 0); }
		public TerminalNode ARC() { return getToken(GPLParser.ARC, 0); }
		public TerminalNode LIST() { return getToken(GPLParser.LIST, 0); }
		public TerminalNode ARRAY() { return getToken(GPLParser.ARRAY, 0); }
		public TerminalNode INT_TYPE() { return getToken(GPLParser.INT_TYPE, 0); }
		public TerminalNode STRING_TYPE() { return getToken(GPLParser.STRING_TYPE, 0); }
		public TerminalNode BOOLEAN_TYPE() { return getToken(GPLParser.BOOLEAN_TYPE, 0); }
		public TerminalNode CHAR_TYPE() { return getToken(GPLParser.CHAR_TYPE, 0); }
		public TerminalNode FUNCTION_TYPE() { return getToken(GPLParser.FUNCTION_TYPE, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1876951044L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BaseTypeContext extends ParserRuleContext {
		public TerminalNode GRAPH() { return getToken(GPLParser.GRAPH, 0); }
		public TerminalNode NODE() { return getToken(GPLParser.NODE, 0); }
		public TerminalNode ARC() { return getToken(GPLParser.ARC, 0); }
		public TerminalNode LIST() { return getToken(GPLParser.LIST, 0); }
		public TerminalNode INT_TYPE() { return getToken(GPLParser.INT_TYPE, 0); }
		public TerminalNode STRING_TYPE() { return getToken(GPLParser.STRING_TYPE, 0); }
		public TerminalNode BOOLEAN_TYPE() { return getToken(GPLParser.BOOLEAN_TYPE, 0); }
		public BaseTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_baseType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterBaseType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitBaseType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitBaseType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BaseTypeContext baseType() throws RecognitionException {
		BaseTypeContext _localctx = new BaseTypeContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_baseType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(272);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 266338304L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AddSubExprContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ADD() { return getToken(GPLParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(GPLParser.SUB, 0); }
		public AddSubExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterAddSubExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitAddSubExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitAddSubExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LogicalExprContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode AND() { return getToken(GPLParser.AND, 0); }
		public TerminalNode OR() { return getToken(GPLParser.OR, 0); }
		public TerminalNode AND_WORD() { return getToken(GPLParser.AND_WORD, 0); }
		public TerminalNode OR_WORD() { return getToken(GPLParser.OR_WORD, 0); }
		public LogicalExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterLogicalExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitLogicalExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitLogicalExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonExprContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode LT() { return getToken(GPLParser.LT, 0); }
		public TerminalNode GT() { return getToken(GPLParser.GT, 0); }
		public TerminalNode LE() { return getToken(GPLParser.LE, 0); }
		public TerminalNode GE() { return getToken(GPLParser.GE, 0); }
		public TerminalNode EQ() { return getToken(GPLParser.EQ, 0); }
		public TerminalNode NE() { return getToken(GPLParser.NE, 0); }
		public ComparisonExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterComparisonExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitComparisonExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitComparisonExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FuncCallExprContext extends ExpressionContext {
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public FuncCallExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterFuncCallExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitFuncCallExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitFuncCallExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MultExprContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode MUL() { return getToken(GPLParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(GPLParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(GPLParser.MOD, 0); }
		public MultExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterMultExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitMultExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitMultExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenExprContext extends ExpressionContext {
		public TerminalNode LPAREN() { return getToken(GPLParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(GPLParser.RPAREN, 0); }
		public ParenExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterParenExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitParenExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitParenExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotExprContext extends ExpressionContext {
		public TerminalNode NOT() { return getToken(GPLParser.NOT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NotExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterNotExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitNotExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitNotExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnaryMinusExprContext extends ExpressionContext {
		public TerminalNode SUB() { return getToken(GPLParser.SUB, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public UnaryMinusExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterUnaryMinusExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitUnaryMinusExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitUnaryMinusExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LambdaExprContext extends ExpressionContext {
		public LambdaExpressionContext lambdaExpression() {
			return getRuleContext(LambdaExpressionContext.class,0);
		}
		public LambdaExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterLambdaExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitLambdaExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitLambdaExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IndexAccessExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode LBRACK() { return getToken(GPLParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(GPLParser.RBRACK, 0); }
		public IndexAccessExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterIndexAccessExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitIndexAccessExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitIndexAccessExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LiteralExprContext extends ExpressionContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LiteralExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterLiteralExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitLiteralExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitLiteralExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MemberAccessExprContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode DOT() { return getToken(GPLParser.DOT, 0); }
		public TerminalNode ID() { return getToken(GPLParser.ID, 0); }
		public MemberAccessExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterMemberAccessExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitMemberAccessExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitMemberAccessExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IdExprContext extends ExpressionContext {
		public TerminalNode ID() { return getToken(GPLParser.ID, 0); }
		public IdExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterIdExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitIdExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitIdExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 50;
		enterRecursionRule(_localctx, 50, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(287);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				{
				_localctx = new FuncCallExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(275);
				functionCall();
				}
				break;
			case 2:
				{
				_localctx = new LiteralExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(276);
				literal();
				}
				break;
			case 3:
				{
				_localctx = new IdExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(277);
				match(ID);
				}
				break;
			case 4:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(278);
				match(LPAREN);
				setState(279);
				expression(0);
				setState(280);
				match(RPAREN);
				}
				break;
			case 5:
				{
				_localctx = new UnaryMinusExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(282);
				match(SUB);
				setState(283);
				expression(7);
				}
				break;
			case 6:
				{
				_localctx = new NotExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(284);
				match(NOT);
				setState(285);
				expression(6);
				}
				break;
			case 7:
				{
				_localctx = new LambdaExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(286);
				lambdaExpression();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(311);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(309);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
					case 1:
						{
						_localctx = new MultExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(289);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(290);
						((MultExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 481036337152L) != 0)) ) {
							((MultExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(291);
						expression(5);
						}
						break;
					case 2:
						{
						_localctx = new AddSubExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(292);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(293);
						((AddSubExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
							((AddSubExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(294);
						expression(4);
						}
						break;
					case 3:
						{
						_localctx = new ComparisonExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(295);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(296);
						((ComparisonExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 138538465099776L) != 0)) ) {
							((ComparisonExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(297);
						expression(3);
						}
						break;
					case 4:
						{
						_localctx = new LogicalExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(298);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(299);
						((LogicalExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 2111062325329920L) != 0)) ) {
							((LogicalExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(300);
						expression(2);
						}
						break;
					case 5:
						{
						_localctx = new MemberAccessExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(301);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(302);
						match(DOT);
						setState(303);
						match(ID);
						}
						break;
					case 6:
						{
						_localctx = new IndexAccessExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(304);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(305);
						match(LBRACK);
						setState(306);
						expression(0);
						setState(307);
						match(RBRACK);
						}
						break;
					}
					} 
				}
				setState(313);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(GPLParser.INT, 0); }
		public TerminalNode STRING() { return getToken(GPLParser.STRING, 0); }
		public TerminalNode CHAR() { return getToken(GPLParser.CHAR, 0); }
		public TerminalNode NIL() { return getToken(GPLParser.NIL, 0); }
		public TerminalNode TRUE() { return getToken(GPLParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(GPLParser.FALSE, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GPLListener ) ((GPLListener)listener).exitLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GPLVisitor ) return ((GPLVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(314);
			_la = _input.LA(1);
			if ( !(((((_la - 18)) & ~0x3f) == 0 && ((1L << (_la - 18)) & 985162418487303L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 25:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		case 2:
			return precpred(_ctx, 2);
		case 3:
			return precpred(_ctx, 1);
		case 4:
			return precpred(_ctx, 9);
		case 5:
			return precpred(_ctx, 8);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001F\u013d\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0001\u0000\u0001\u0000"+
		"\u0005\u00009\b\u0000\n\u0000\f\u0000<\t\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002"+
		"\u0005\u0002F\b\u0002\n\u0002\f\u0002I\t\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003]\b\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0005\u0007x\b\u0007\n\u0007\f\u0007{\t\u0007\u0003\u0007"+
		"}\b\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0003\u0007\u0086\b\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0005\b\u008c\b\b\n\b\f\b\u008f\t\b\u0003\b\u0091\b\b\u0001"+
		"\b\u0001\b\u0003\b\u0095\b\b\u0001\t\u0001\t\u0003\t\u0099\b\t\u0001\n"+
		"\u0003\n\u009c\b\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b\u00a7\b\u000b\n\u000b"+
		"\f\u000b\u00aa\t\u000b\u0003\u000b\u00ac\b\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0003\r\u00ba\b\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0010\u0001\u0010\u0004\u0010\u00ce\b\u0010\u000b\u0010\f"+
		"\u0010\u00cf\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010\u00d5\b\u0010"+
		"\n\u0010\f\u0010\u00d8\t\u0010\u0003\u0010\u00da\b\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0005\u0011\u00e2"+
		"\b\u0011\n\u0011\f\u0011\u00e5\t\u0011\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0013\u0001\u0013\u0003\u0013\u00f2\b\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u00f9\b\u0013\n\u0013"+
		"\f\u0013\u00fc\t\u0013\u0003\u0013\u00fe\b\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0014\u0001\u0014\u0003\u0014\u0105\b\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0003\u0019\u0120\b\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0005\u0019\u0136\b\u0019"+
		"\n\u0019\f\u0019\u0139\t\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0000"+
		"\u00012\u001b\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016"+
		"\u0018\u001a\u001c\u001e \"$&(*,.024\u0000\b\u0002\u0000\u0015\u001b@"+
		"@\u0003\u0000\u0002\u0002\u0015\u001b\u001d\u001e\u0001\u0000\u0015\u001b"+
		"\u0001\u0000$&\u0001\u0000\'(\u0001\u0000).\u0001\u0000/2\u0002\u0000"+
		"\u0012\u0014AC\u0150\u0000:\u0001\u0000\u0000\u0000\u0002A\u0001\u0000"+
		"\u0000\u0000\u0004C\u0001\u0000\u0000\u0000\u0006\\\u0001\u0000\u0000"+
		"\u0000\b^\u0001\u0000\u0000\u0000\nf\u0001\u0000\u0000\u0000\fk\u0001"+
		"\u0000\u0000\u0000\u000e\u0085\u0001\u0000\u0000\u0000\u0010\u0094\u0001"+
		"\u0000\u0000\u0000\u0012\u0098\u0001\u0000\u0000\u0000\u0014\u009b\u0001"+
		"\u0000\u0000\u0000\u0016\u00a1\u0001\u0000\u0000\u0000\u0018\u00af\u0001"+
		"\u0000\u0000\u0000\u001a\u00b1\u0001\u0000\u0000\u0000\u001c\u00bb\u0001"+
		"\u0000\u0000\u0000\u001e\u00c1\u0001\u0000\u0000\u0000 \u00c7\u0001\u0000"+
		"\u0000\u0000\"\u00dd\u0001\u0000\u0000\u0000$\u00e6\u0001\u0000\u0000"+
		"\u0000&\u00ef\u0001\u0000\u0000\u0000(\u0102\u0001\u0000\u0000\u0000*"+
		"\u0108\u0001\u0000\u0000\u0000,\u010b\u0001\u0000\u0000\u0000.\u010e\u0001"+
		"\u0000\u0000\u00000\u0110\u0001\u0000\u0000\u00002\u011f\u0001\u0000\u0000"+
		"\u00004\u013a\u0001\u0000\u0000\u000069\u0003\u0002\u0001\u000079\u0003"+
		"&\u0013\u000086\u0001\u0000\u0000\u000087\u0001\u0000\u0000\u00009<\u0001"+
		"\u0000\u0000\u0000:8\u0001\u0000\u0000\u0000:;\u0001\u0000\u0000\u0000"+
		";=\u0001\u0000\u0000\u0000<:\u0001\u0000\u0000\u0000=>\u0005\u0001\u0000"+
		"\u0000>?\u0003\u0004\u0002\u0000?@\u0005\u0000\u0000\u0001@\u0001\u0001"+
		"\u0000\u0000\u0000AB\u0003\b\u0004\u0000B\u0003\u0001\u0000\u0000\u0000"+
		"CG\u00059\u0000\u0000DF\u0003\u0006\u0003\u0000ED\u0001\u0000\u0000\u0000"+
		"FI\u0001\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000GH\u0001\u0000\u0000"+
		"\u0000HJ\u0001\u0000\u0000\u0000IG\u0001\u0000\u0000\u0000JK\u0005:\u0000"+
		"\u0000K\u0005\u0001\u0000\u0000\u0000L]\u0003\b\u0004\u0000M]\u0003\n"+
		"\u0005\u0000NO\u0003\u0016\u000b\u0000OP\u00056\u0000\u0000P]\u0001\u0000"+
		"\u0000\u0000Q]\u0003\u001a\r\u0000R]\u0003 \u0010\u0000S]\u0003\u001c"+
		"\u000e\u0000T]\u0003\u001e\u000f\u0000U]\u0003$\u0012\u0000V]\u0003&\u0013"+
		"\u0000W]\u0003(\u0014\u0000X]\u0003*\u0015\u0000Y]\u0003,\u0016\u0000"+
		"Z]\u0003\f\u0006\u0000[]\u0003\u0004\u0002\u0000\\L\u0001\u0000\u0000"+
		"\u0000\\M\u0001\u0000\u0000\u0000\\N\u0001\u0000\u0000\u0000\\Q\u0001"+
		"\u0000\u0000\u0000\\R\u0001\u0000\u0000\u0000\\S\u0001\u0000\u0000\u0000"+
		"\\T\u0001\u0000\u0000\u0000\\U\u0001\u0000\u0000\u0000\\V\u0001\u0000"+
		"\u0000\u0000\\W\u0001\u0000\u0000\u0000\\X\u0001\u0000\u0000\u0000\\Y"+
		"\u0001\u0000\u0000\u0000\\Z\u0001\u0000\u0000\u0000\\[\u0001\u0000\u0000"+
		"\u0000]\u0007\u0001\u0000\u0000\u0000^_\u0005\u0003\u0000\u0000_`\u0003"+
		".\u0017\u0000`a\u0005#\u0000\u0000ab\u0005@\u0000\u0000bc\u00053\u0000"+
		"\u0000cd\u00032\u0019\u0000de\u00056\u0000\u0000e\t\u0001\u0000\u0000"+
		"\u0000fg\u0005@\u0000\u0000gh\u00053\u0000\u0000hi\u00032\u0019\u0000"+
		"ij\u00056\u0000\u0000j\u000b\u0001\u0000\u0000\u0000kl\u0005\u0003\u0000"+
		"\u0000lm\u0005\u0002\u0000\u0000mn\u0005#\u0000\u0000no\u0005@\u0000\u0000"+
		"op\u00053\u0000\u0000pq\u0003\u000e\u0007\u0000qr\u00056\u0000\u0000r"+
		"\r\u0001\u0000\u0000\u0000s|\u00057\u0000\u0000ty\u0003\u0014\n\u0000"+
		"uv\u00054\u0000\u0000vx\u0003\u0014\n\u0000wu\u0001\u0000\u0000\u0000"+
		"x{\u0001\u0000\u0000\u0000yw\u0001\u0000\u0000\u0000yz\u0001\u0000\u0000"+
		"\u0000z}\u0001\u0000\u0000\u0000{y\u0001\u0000\u0000\u0000|t\u0001\u0000"+
		"\u0000\u0000|}\u0001\u0000\u0000\u0000}~\u0001\u0000\u0000\u0000~\u007f"+
		"\u00058\u0000\u0000\u007f\u0080\u0005?\u0000\u0000\u0080\u0086\u0003\u0012"+
		"\t\u0000\u0081\u0082\u0003\u0014\n\u0000\u0082\u0083\u0005?\u0000\u0000"+
		"\u0083\u0084\u0003\u0012\t\u0000\u0084\u0086\u0001\u0000\u0000\u0000\u0085"+
		"s\u0001\u0000\u0000\u0000\u0085\u0081\u0001\u0000\u0000\u0000\u0086\u000f"+
		"\u0001\u0000\u0000\u0000\u0087\u0090\u00057\u0000\u0000\u0088\u008d\u0003"+
		"\u0014\n\u0000\u0089\u008a\u00054\u0000\u0000\u008a\u008c\u0003\u0014"+
		"\n\u0000\u008b\u0089\u0001\u0000\u0000\u0000\u008c\u008f\u0001\u0000\u0000"+
		"\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008d\u008e\u0001\u0000\u0000"+
		"\u0000\u008e\u0091\u0001\u0000\u0000\u0000\u008f\u008d\u0001\u0000\u0000"+
		"\u0000\u0090\u0088\u0001\u0000\u0000\u0000\u0090\u0091\u0001\u0000\u0000"+
		"\u0000\u0091\u0092\u0001\u0000\u0000\u0000\u0092\u0095\u00058\u0000\u0000"+
		"\u0093\u0095\u0003\u0014\n\u0000\u0094\u0087\u0001\u0000\u0000\u0000\u0094"+
		"\u0093\u0001\u0000\u0000\u0000\u0095\u0011\u0001\u0000\u0000\u0000\u0096"+
		"\u0099\u00032\u0019\u0000\u0097\u0099\u0003\u0004\u0002\u0000\u0098\u0096"+
		"\u0001\u0000\u0000\u0000\u0098\u0097\u0001\u0000\u0000\u0000\u0099\u0013"+
		"\u0001\u0000\u0000\u0000\u009a\u009c\u0005\u001c\u0000\u0000\u009b\u009a"+
		"\u0001\u0000\u0000\u0000\u009b\u009c\u0001\u0000\u0000\u0000\u009c\u009d"+
		"\u0001\u0000\u0000\u0000\u009d\u009e\u0003.\u0017\u0000\u009e\u009f\u0005"+
		"#\u0000\u0000\u009f\u00a0\u0005@\u0000\u0000\u00a0\u0015\u0001\u0000\u0000"+
		"\u0000\u00a1\u00a2\u0003\u0018\f\u0000\u00a2\u00ab\u00057\u0000\u0000"+
		"\u00a3\u00a8\u00032\u0019\u0000\u00a4\u00a5\u00054\u0000\u0000\u00a5\u00a7"+
		"\u00032\u0019\u0000\u00a6\u00a4\u0001\u0000\u0000\u0000\u00a7\u00aa\u0001"+
		"\u0000\u0000\u0000\u00a8\u00a6\u0001\u0000\u0000\u0000\u00a8\u00a9\u0001"+
		"\u0000\u0000\u0000\u00a9\u00ac\u0001\u0000\u0000\u0000\u00aa\u00a8\u0001"+
		"\u0000\u0000\u0000\u00ab\u00a3\u0001\u0000\u0000\u0000\u00ab\u00ac\u0001"+
		"\u0000\u0000\u0000\u00ac\u00ad\u0001\u0000\u0000\u0000\u00ad\u00ae\u0005"+
		"8\u0000\u0000\u00ae\u0017\u0001\u0000\u0000\u0000\u00af\u00b0\u0007\u0000"+
		"\u0000\u0000\u00b0\u0019\u0001\u0000\u0000\u0000\u00b1\u00b2\u0005\u0005"+
		"\u0000\u0000\u00b2\u00b3\u00057\u0000\u0000\u00b3\u00b4\u00032\u0019\u0000"+
		"\u00b4\u00b5\u00058\u0000\u0000\u00b5\u00b6\u0005\t\u0000\u0000\u00b6"+
		"\u00b9\u0003\u0006\u0003\u0000\u00b7\u00b8\u0005\n\u0000\u0000\u00b8\u00ba"+
		"\u0003\u0006\u0003\u0000\u00b9\u00b7\u0001\u0000\u0000\u0000\u00b9\u00ba"+
		"\u0001\u0000\u0000\u0000\u00ba\u001b\u0001\u0000\u0000\u0000\u00bb\u00bc"+
		"\u0005\u000b\u0000\u0000\u00bc\u00bd\u00057\u0000\u0000\u00bd\u00be\u0003"+
		"2\u0019\u0000\u00be\u00bf\u00058\u0000\u0000\u00bf\u00c0\u0003\u0006\u0003"+
		"\u0000\u00c0\u001d\u0001\u0000\u0000\u0000\u00c1\u00c2\u0005\f\u0000\u0000"+
		"\u00c2\u00c3\u00057\u0000\u0000\u00c3\u00c4\u00032\u0019\u0000\u00c4\u00c5"+
		"\u00058\u0000\u0000\u00c5\u00c6\u0003\u0006\u0003\u0000\u00c6\u001f\u0001"+
		"\u0000\u0000\u0000\u00c7\u00c8\u0005\u0006\u0000\u0000\u00c8\u00c9\u0005"+
		"7\u0000\u0000\u00c9\u00ca\u00032\u0019\u0000\u00ca\u00cb\u00058\u0000"+
		"\u0000\u00cb\u00cd\u00059\u0000\u0000\u00cc\u00ce\u0003\"\u0011\u0000"+
		"\u00cd\u00cc\u0001\u0000\u0000\u0000\u00ce\u00cf\u0001\u0000\u0000\u0000"+
		"\u00cf\u00cd\u0001\u0000\u0000\u0000\u00cf\u00d0\u0001\u0000\u0000\u0000"+
		"\u00d0\u00d9\u0001\u0000\u0000\u0000\u00d1\u00d2\u0005\b\u0000\u0000\u00d2"+
		"\u00d6\u00055\u0000\u0000\u00d3\u00d5\u0003\u0006\u0003\u0000\u00d4\u00d3"+
		"\u0001\u0000\u0000\u0000\u00d5\u00d8\u0001\u0000\u0000\u0000\u00d6\u00d4"+
		"\u0001\u0000\u0000\u0000\u00d6\u00d7\u0001\u0000\u0000\u0000\u00d7\u00da"+
		"\u0001\u0000\u0000\u0000\u00d8\u00d6\u0001\u0000\u0000\u0000\u00d9\u00d1"+
		"\u0001\u0000\u0000\u0000\u00d9\u00da\u0001\u0000\u0000\u0000\u00da\u00db"+
		"\u0001\u0000\u0000\u0000\u00db\u00dc\u0005:\u0000\u0000\u00dc!\u0001\u0000"+
		"\u0000\u0000\u00dd\u00de\u0005\u0007\u0000\u0000\u00de\u00df\u00034\u001a"+
		"\u0000\u00df\u00e3\u00055\u0000\u0000\u00e0\u00e2\u0003\u0006\u0003\u0000"+
		"\u00e1\u00e0\u0001\u0000\u0000\u0000\u00e2\u00e5\u0001\u0000\u0000\u0000"+
		"\u00e3\u00e1\u0001\u0000\u0000\u0000\u00e3\u00e4\u0001\u0000\u0000\u0000"+
		"\u00e4#\u0001\u0000\u0000\u0000\u00e5\u00e3\u0001\u0000\u0000\u0000\u00e6"+
		"\u00e7\u0005\r\u0000\u0000\u00e7\u00e8\u00057\u0000\u0000\u00e8\u00e9"+
		"\u0003.\u0017\u0000\u00e9\u00ea\u0005@\u0000\u0000\u00ea\u00eb\u0005\u000e"+
		"\u0000\u0000\u00eb\u00ec\u00032\u0019\u0000\u00ec\u00ed\u00058\u0000\u0000"+
		"\u00ed\u00ee\u0003\u0006\u0003\u0000\u00ee%\u0001\u0000\u0000\u0000\u00ef"+
		"\u00f1\u0005\u0004\u0000\u0000\u00f0\u00f2\u0003.\u0017\u0000\u00f1\u00f0"+
		"\u0001\u0000\u0000\u0000\u00f1\u00f2\u0001\u0000\u0000\u0000\u00f2\u00f3"+
		"\u0001\u0000\u0000\u0000\u00f3\u00f4\u0005@\u0000\u0000\u00f4\u00fd\u0005"+
		"7\u0000\u0000\u00f5\u00fa\u0003\u0014\n\u0000\u00f6\u00f7\u00054\u0000"+
		"\u0000\u00f7\u00f9\u0003\u0014\n\u0000\u00f8\u00f6\u0001\u0000\u0000\u0000"+
		"\u00f9\u00fc\u0001\u0000\u0000\u0000\u00fa\u00f8\u0001\u0000\u0000\u0000"+
		"\u00fa\u00fb\u0001\u0000\u0000\u0000\u00fb\u00fe\u0001\u0000\u0000\u0000"+
		"\u00fc\u00fa\u0001\u0000\u0000\u0000\u00fd\u00f5\u0001\u0000\u0000\u0000"+
		"\u00fd\u00fe\u0001\u0000\u0000\u0000\u00fe\u00ff\u0001\u0000\u0000\u0000"+
		"\u00ff\u0100\u00058\u0000\u0000\u0100\u0101\u0003\u0004\u0002\u0000\u0101"+
		"\'\u0001\u0000\u0000\u0000\u0102\u0104\u0005\u000f\u0000\u0000\u0103\u0105"+
		"\u00032\u0019\u0000\u0104\u0103\u0001\u0000\u0000\u0000\u0104\u0105\u0001"+
		"\u0000\u0000\u0000\u0105\u0106\u0001\u0000\u0000\u0000\u0106\u0107\u0005"+
		"6\u0000\u0000\u0107)\u0001\u0000\u0000\u0000\u0108\u0109\u0005\u0010\u0000"+
		"\u0000\u0109\u010a\u00056\u0000\u0000\u010a+\u0001\u0000\u0000\u0000\u010b"+
		"\u010c\u0005\u0011\u0000\u0000\u010c\u010d\u00056\u0000\u0000\u010d-\u0001"+
		"\u0000\u0000\u0000\u010e\u010f\u0007\u0001\u0000\u0000\u010f/\u0001\u0000"+
		"\u0000\u0000\u0110\u0111\u0007\u0002\u0000\u0000\u01111\u0001\u0000\u0000"+
		"\u0000\u0112\u0113\u0006\u0019\uffff\uffff\u0000\u0113\u0120\u0003\u0016"+
		"\u000b\u0000\u0114\u0120\u00034\u001a\u0000\u0115\u0120\u0005@\u0000\u0000"+
		"\u0116\u0117\u00057\u0000\u0000\u0117\u0118\u00032\u0019\u0000\u0118\u0119"+
		"\u00058\u0000\u0000\u0119\u0120\u0001\u0000\u0000\u0000\u011a\u011b\u0005"+
		"(\u0000\u0000\u011b\u0120\u00032\u0019\u0007\u011c\u011d\u0005>\u0000"+
		"\u0000\u011d\u0120\u00032\u0019\u0006\u011e\u0120\u0003\u000e\u0007\u0000"+
		"\u011f\u0112\u0001\u0000\u0000\u0000\u011f\u0114\u0001\u0000\u0000\u0000"+
		"\u011f\u0115\u0001\u0000\u0000\u0000\u011f\u0116\u0001\u0000\u0000\u0000"+
		"\u011f\u011a\u0001\u0000\u0000\u0000\u011f\u011c\u0001\u0000\u0000\u0000"+
		"\u011f\u011e\u0001\u0000\u0000\u0000\u0120\u0137\u0001\u0000\u0000\u0000"+
		"\u0121\u0122\n\u0004\u0000\u0000\u0122\u0123\u0007\u0003\u0000\u0000\u0123"+
		"\u0136\u00032\u0019\u0005\u0124\u0125\n\u0003\u0000\u0000\u0125\u0126"+
		"\u0007\u0004\u0000\u0000\u0126\u0136\u00032\u0019\u0004\u0127\u0128\n"+
		"\u0002\u0000\u0000\u0128\u0129\u0007\u0005\u0000\u0000\u0129\u0136\u0003"+
		"2\u0019\u0003\u012a\u012b\n\u0001\u0000\u0000\u012b\u012c\u0007\u0006"+
		"\u0000\u0000\u012c\u0136\u00032\u0019\u0002\u012d\u012e\n\t\u0000\u0000"+
		"\u012e\u012f\u0005=\u0000\u0000\u012f\u0136\u0005@\u0000\u0000\u0130\u0131"+
		"\n\b\u0000\u0000\u0131\u0132\u0005;\u0000\u0000\u0132\u0133\u00032\u0019"+
		"\u0000\u0133\u0134\u0005<\u0000\u0000\u0134\u0136\u0001\u0000\u0000\u0000"+
		"\u0135\u0121\u0001\u0000\u0000\u0000\u0135\u0124\u0001\u0000\u0000\u0000"+
		"\u0135\u0127\u0001\u0000\u0000\u0000\u0135\u012a\u0001\u0000\u0000\u0000"+
		"\u0135\u012d\u0001\u0000\u0000\u0000\u0135\u0130\u0001\u0000\u0000\u0000"+
		"\u0136\u0139\u0001\u0000\u0000\u0000\u0137\u0135\u0001\u0000\u0000\u0000"+
		"\u0137\u0138\u0001\u0000\u0000\u0000\u01383\u0001\u0000\u0000\u0000\u0139"+
		"\u0137\u0001\u0000\u0000\u0000\u013a\u013b\u0007\u0007\u0000\u0000\u013b"+
		"5\u0001\u0000\u0000\u0000\u001a8:G\\y|\u0085\u008d\u0090\u0094\u0098\u009b"+
		"\u00a8\u00ab\u00b9\u00cf\u00d6\u00d9\u00e3\u00f1\u00fa\u00fd\u0104\u011f"+
		"\u0135\u0137";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}