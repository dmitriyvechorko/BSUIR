// GPL.g4
// Grammar for GPL (Graph Programming Language)

grammar GPL;

///////////////////////////////////////////////////////////////////////////////
// Главные (parser) правила                                                  //
///////////////////////////////////////////////////////////////////////////////

/*
program
  - Корневое правило. Ожидает ключевое слово 'program' и один блок.
  - EOF обязателен, чтобы убедиться, что после блока не осталось лишних токенов.
*/
program
    : (globalDeclaration | functionDefinition)* PROGRAM block EOF
    ;

globalDeclaration
    : variableDeclaration
    ;

/*
block
  - Блочный оператор в форме фигурных скобок { ... }.
  - Блок содержит ноль или более statement.
*/
block
    : LBRACE statement* RBRACE
    ;

/*
statement
  - Список возможных операторов.
  - Включает объявления переменных, присваивания, вызовы функций (с ';'),
    управляющие конструкции, определения функций, return/break/continue,
    а также вложенные блоки.
*/
statement
    : variableDeclaration
    | assignment
    | functionCall SEMICOLON
    | ifStatement
    | switchStatement
    | whileStatement
    | untilStatement
    | forStatement
    | functionDefinition
    | returnStatement
    | breakStatement
    | continueStatement
    | block
    ;

///////////////////////////////////////////////////////////////////////////////
// Объявления и присваивания
///////////////////////////////////////////////////////////////////////////////

/*
variableDeclaration
  - Синтаксис: var <type> ? <ID> = <expression> ;
  - Знак QUESTION ('?') присутствует в требованиях: указывает, что при
    объявлении тип указывается с '?'. (Если это часть требований, сохраняем.)
  - Инициализирующее выражение является обязательным согласно грамматике.
  Пример:
    var int? x = 5;
    var graph? g = graph();
*/
variableDeclaration
    : VAR type ID ASSIGN expression SEMICOLON
    ;

/*
assignment
  - Простое присваивание: <ID> = <expression> ;
  - Семантика должна проверять существование переменной и совместимость типов.
*/
assignment
    : ID ASSIGN expression SEMICOLON
    ;

///////////////////////////////////////////////////////////////////////////////
// Вызовы функций и имена функций
///////////////////////////////////////////////////////////////////////////////

/*
functionCall
  - Вызов функции: имя-функции (список выражений, разделённых запятыми)
  - Параметры опциональны: (expression ( , expression )* )?
  Пример:
    node("A")
    arc(find_node(g, "B"), find_node(g, "C"), connection)
*/
functionCall
    : functionName LPAREN (expression (COMMA expression)*)? RPAREN
    ;

/*
functionName
  - Имя функции может быть:
    * пользовательский идентификатор (ID)
    * или одно из ключевых встроенных имён: graph, node, arc, list, int, string
  - Замечание: включение INT_TYPE/STRING_TYPE в functionName означает,
    что используются конструкторы/фабрики типов (graph(), node(...), ...).
*/
functionName
    : ID
    | GRAPH
    | NODE
    | ARC
    | LIST
    | INT_TYPE
    | STRING_TYPE
    | BOOLEAN_TYPE
    ;

///////////////////////////////////////////////////////////////////////////////
// Управляющие конструкции
///////////////////////////////////////////////////////////////////////////////

/*
ifStatement
  - Условный оператор в форме: if (expr) then statement (else statement)?
  - ELSE необязателен. statement может быть блоком { } или одиночным оператором.
  Пример:
    if (x > 0) then { ... } else { ... }
*/
ifStatement
    : IF LPAREN expression RPAREN THEN statement (ELSE statement)?
    ;

/*
whileStatement
  - Цикл while: while (cond) statement
  - statement может быть блоком или однострочным оператором.
*/
whileStatement
    : WHILE LPAREN expression RPAREN statement
    ;

/*
untilStatement
  - Цикл until: выполняет тело, пока условие не станет истинным.
  - Синтаксис: until (cond) statement
  - Семантически отличается от while — зависит от реализации.
*/
untilStatement
    : UNTIL LPAREN expression RPAREN statement
    ;

/*
switchStatement
  - Многовариантный условный оператор.
  - Синтаксис: switch (expression) { case literal: statement* ... (default: statement*)? }
  - Тело switch — это блок, содержащий один или более case-блоков и опциональный default-блок.
  - Каждый case-блок начинается с ключевого слова 'case', за которым следует литерал и двоеточие.
  - После двоеточия может следовать ноль или более операторов.
  - Блок 'default' необязателен и должен быть последним.
*/
switchStatement
    : SWITCH LPAREN expression RPAREN LBRACE switchCase+ (DEFAULT COLON statement*)? RBRACE
    ;

/*
switchCase
  - Один блок case внутри switch.
*/
switchCase
    : CASE literal COLON statement*
    ;

/*
forStatement
  - Цикл for: for (type? id in expression) statement
  - В грамматике: FOR LPAREN type QUESTION ID IN expression RPAREN statement
    — здесь предполагается итерация по коллекции (например, по списку узлов графа).
  - Пример: for (node? n in get_nodes(g)) { write(get_label(n)); }
*/
forStatement
    : FOR LPAREN type ID IN expression RPAREN statement
    ;

///////////////////////////////////////////////////////////////////////////////
// Подпрограммы (функции)
///////////////////////////////////////////////////////////////////////////////

/*
functionDefinition
  - Определение функции: func <return_type>? ID (parameter, ...) block
  - Возвращаемый тип (return_type) теперь опционален. Если не указан, по умолчанию — void.
*/
functionDefinition
    : FUNC type? ID LPAREN (parameter (COMMA parameter)*)? RPAREN block // <-- Изменено: добавлено 'type?'
    ;

/*
parameter
  - Синтаксис параметра: <type> ? <ID>
  - QUESTION используется здесь чтобы соответствовать требованию объявления
    переменных с '?'. Если в требованиях параметр должен содержать '?',
    оставляем; иначе можно убрать QUESTION.
  - Передача параметров по ссылке (в требований) — семантика на уровне
    SemanticAnalyzer/Compiler.
*/
parameter
    : type ID
    ;

///////////////////////////////////////////////////////////////////////////////
// Операторы управления потоком внутри подпрограмм
///////////////////////////////////////////////////////////////////////////////

/*
returnStatement
  - Возврат значения: return <expression>? ;
  - expression опционален (пустой return для void-функций).
*/
returnStatement
    : RETURN expression? SEMICOLON
    ;

/*
breakStatement / continueStatement
  - Останавливают цикл или пропускают итерацию соответственно.
*/
breakStatement
    : BREAK SEMICOLON
    ;

continueStatement
    : CONTINUE SEMICOLON
    ;

///////////////////////////////////////////////////////////////////////////////
// Типы (parser rule)
///////////////////////////////////////////////////////////////////////////////

/*
type
  - Перечисление поддерживаемых встроенных типов.
  - При необходимости можно расширить типами пользовательских структур.
*/
type
    : baseType QUESTION?   // nullable тип: int?
    ;

baseType
    : GRAPH
    | NODE
    | ARC
    | LIST
    | INT_TYPE
    | STRING_TYPE
    | BOOLEAN_TYPE
    ;


///////////////////////////////////////////////////////////////////////////////
// Выражения
// (labeled alternatives используются для удобства при написании Visitor)
///////////////////////////////////////////////////////////////////////////////

/*
expression
  - Labeled alternatives (в конце каждой альтернативы — #Label) дают ANTLR
    генерировать подклассы ExpressionContext (например, AddSubExprContext),
    что удобно в visitor-реализации и предотвращает необходимость
    проверять наличие child-методов в общем ExpressionContext.
  - Порядок альтернатив не задаёт приоритет — приоритет задаётся рекурсивной
    структурой (левая рекурсия). В данной грамматике приоритеты (от высоких
    к низким):
      - functionCall, literal, ID, parenthesis, unary (SUB, NOT)
      - multExpr (*, /, %)
      - addSubExpr (+, -)
      - comparisonExpr (==, !=, <, >, <=, >=)
      - logicalExpr (&&, ||)
  - Это стандартный порядок: сначала умножение, затем сложение, затем сравнение, затем логика.
*/

expression
    : functionCall                  #funcCallExpr
    | literal                       #literalExpr
    | ID                            #idExpr
    | LPAREN expression RPAREN      #parenExpr
    | expression DOT ID             #memberAccessExpr
    | expression LBRACK expression RBRACK #indexAccessExpr
    | SUB expression                #unaryMinusExpr
    | NOT expression                #notExpr
    | expression op=(MUL|DIV|MOD) expression     #multExpr
    | expression op=(ADD|SUB) expression         #addSubExpr
    | expression op=(LT|GT|LE|GE|EQ|NE) expression #comparisonExpr
    | expression op=(AND|OR|AND_WORD|OR_WORD) expression       #logicalExpr
    ;

///////////////////////////////////////////////////////////////////////////////
// Литералы
///////////////////////////////////////////////////////////////////////////////

/*
literal
  - INT: целочисленные константы
  - STRING: строковые литералы в двойных кавычках
  - NIL: nil (null-подобное значение)
  - TRUE/FALSE: булевы константы
*/
literal
    : INT
    | STRING
    | NIL
    | TRUE
    | FALSE
    ;

///////////////////////////////////////////////////////////////////////////////
// Лексерные правила (tokens)
// Пояснения внутри комментариев к каждому блоку
///////////////////////////////////////////////////////////////////////////////

/*
Ключевые слова:
  - Объявлены отдельными токенами (PROGRAM, VAR, FUNC и т.д.), чтобы они не
    распознавались как ID. Порядок объявления в файле лексера важен: если бы ID
    стоял раньше, он бы "перехватывал" ключевые слова. В ANTLR конкретные токены
    выбираются в соответствии с правилами и порядком/приоритетом.
  - Ключевые слова чувствительны к регистру в данной грамматике (написаны в нижнем регистре).
*/
PROGRAM: 'program';
VAR: 'var';
FUNC: 'func';
IF: 'if';
SWITCH: 'switch';
CASE: 'case';
DEFAULT: 'default';
THEN: 'then';
ELSE: 'else';
WHILE: 'while';
UNTIL: 'until';
FOR: 'for';
IN: 'in';
RETURN: 'return';
BREAK: 'break';
CONTINUE: 'continue';
NIL: 'nil';
TRUE: 'true';
FALSE: 'false';
GRAPH: 'graph';
NODE: 'node';
ARC: 'arc';
LIST: 'list';
INT_TYPE: 'int';
STRING_TYPE: 'string';
BOOLEAN_TYPE: 'boolean';


///////////////////////////////////////////////////////////////////////////////
// Символы (операторы и разделители)
//
// Здесь мы явно обозначаем отдельные символы как токены. Это упрощает парсинг,
// потому что парсер видит понятные имена токенов (например, ADD вместо '+').
///////////////////////////////////////////////////////////////////////////////

QUESTION: '?';
MUL: '*';
DIV: '/';
MOD: '%';
ADD: '+';
SUB: '-';
LT: '<';
GT: '>';
LE: '<=';
GE: '>=';
EQ: '==';
NE: '!=';
AND: '&&';
OR: '||';
AND_WORD: 'and';
OR_WORD: 'or';
ASSIGN: '=';
COMMA: ',';
COLON: ':';
SEMICOLON: ';';
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
LBRACK: '[';
RBRACK: ']';
DOT: '.';
NOT: '!';

///////////////////////////////////////////////////////////////////////////////////////////////////
// Именованные идентификаторы и литералы                                                         //
//                                                                                               //
// ID: идентификаторы (переменные, имена функций) — буква или подчеркивание, потом буквы/цифры/_ //
// INT: последовательность цифр (десятичная)                                                     //
// STRING: строковый литерал (поддерживает escape-последовательности)                            //
//                                                                                               //
///////////////////////////////////////////////////////////////////////////////////////////////////

/*
ID:
  - Паттерн [a-zA-Z_][a-zA-Z_0-9]* позволяет имена, начинающиеся с буквы
    или подчеркивания, далее буквы, цифры или подчёркивания.
  - Обратите внимание: ключевые слова (PROGRAM, VAR, ...) определены отдельно,
    поэтому ключевые слова не будут попадать в ID.
*/
ID: [a-zA-Z_][a-zA-Z_0-9]*;

/*
INT:
  - Простая целая константа: 0..9+
  - В дальнейшем можно расширить до поддержки отрицательных чисел (унарный минус в выражениях).
*/
INT: [0-9]+;

/*
STRING:
  - Строки в двойных кавычках; поддерживаются escape-последовательности:
    \"  \\  \n  \t  и т.д. (мы разрешаем любой символ, кроме " и \ либо escape).
  - Использована конструкция: '"' (~["\\] | '\\' .)* '"'
    - ~["\\] — любой символ, кроме кавычки или обратного слэша
    - '\\' . — escape + любой символ
*/

STRING: '"' (~["\\] | '\\' .)* '"';

///////////////////////////////////////////////////////////////////////////////
// Пропуск пробелов и комментариев
//
// - WS: пробелы/табуляция/переводы строки пропускаются (-> skip)
// - COMMENT: однострочные комментарии // ...
// - BLOCK_COMMENT: многострочные /* ... */ (lazy, non-greedy)
///////////////////////////////////////////////////////////////////////////////

WS: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n]* -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;
