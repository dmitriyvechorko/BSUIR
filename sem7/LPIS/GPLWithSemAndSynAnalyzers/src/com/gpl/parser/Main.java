package com.gpl.parser;

public class Main {
    public static void main(String[] args) {
        GPLSyntaxAnalyzer analyzer = new GPLSyntaxAnalyzer();

        String example1 = """
                    program {
                             // 1. Объявление и инициализация переменных ВСЕХ типов
                             var int? count = 42;
                             var string? message = "Hello, Graph!";
                             var boolean? flag = true;
                             var list? empty_list = list();
                             var graph? g1 = graph("Graph1");
                             var node? start_node = node("Start");
                             var arc? connection = arc(10);
                
                             // 2. Создание сложного графа с помощью встроенных операций
                             var graph? main_graph = graph(); // Создаем пустой граф
                             main_graph = main_graph + start_node; // Добавляем узел
                             var node? end_node = node("End");
                             main_graph = main_graph + end_node; // Добавляем еще один узел
                             var arc? direct_arc = arc(start_node, end_node, 5); // Создаем дугу между узлами
                             main_graph = main_graph + direct_arc; // Добавляем дугу в граф
                
                             // 3. Сложное выражение: создание второго графа и операция пересечения
                             var graph? g2 = (graph() + node("Start") + node("Middle") + arc(node("Start"), node("Middle"), 3));
                             var graph? common_nodes = main_graph * g2; // Пересечение графов (должен содержать только "Start")
                
                             // 4. Использование встроенных функций поиска и вывода
                             write("Начальный узел: ", get_label(start_node));
                             write("Путь в ширину: ", bfs(main_graph, start_node));
                             write("Путь в глубину: ", dfs(main_graph, start_node));
                             write("Кратчайший путь: ", shortest_path(main_graph, start_node, end_node));
                
                             // 5. Чтение графа извне (встроенный ввод)
                             var graph? user_graph = read();
                             write("Прочитанный граф: ", user_graph);
                
                             // 6. Пример сложного выражения со скобками и логикой
                             if ((count > 0) and (flag == true)) then {
                                 write("Счетчик положительный и флаг установлен!");
                             }
                         }
                """;

        String example2 = """
                               // Глобальная переменная
                               var int? global_counter = 0;
                
                               // Пользовательская функция с параметрами и возвратом значения
                               func int? calculate_distance(node? from, node? to) {
                                   // Локальная переменная
                                   var int? base_distance = 1;
                                   if (from == nil or to == nil) then {
                                       return -1; // Ошибка
                                   } else {
                                       // Упрощенный расчет
                                       return base_distance * 10;
                                   }
                               }
                
                               // Функция, использующая switch-case
                               func string? get_node_status(node? n) {
                                   var int? id = 0; // Упрощенно, предположим, что узел имеет числовой ID
                                   // Демонстрация switch-case
                                   switch (id) {
                                       case 0:
                                           return "Активен";
                                       case 1:
                                           return "Неактивен";
                                       case 2:
                                           return "Ожидает";
                                       default:
                                           return "Неизвестен";
                                   }
                               }
                
                               program {
                                   var graph? g = graph() + node("A") + node("B") + node("C");
                                   var list? nodes = get_nodes(g);
                
                                   // Цикл for с итерацией по списку узлов
                                   for (node? current_node in nodes) {
                                       var string? status = get_node_status(current_node);
                                       write("Узел ", get_label(current_node), " имеет статус: ", status);
                
                                       // Пример continue
                                       if (status == "Неактивен") then {
                                           continue; // Пропускаем неактивные узлы
                                       }
                
                                       // Пример break
                                       if (get_label(current_node) == "C") then {
                                           write("Достигнут узел C, выходим из цикла.");
                                           break;
                                       }
                                   }
                
                                   // Цикл while
                                   var int? i = 0;
                                   while (i < 3) {
                                       write("While: Итерация ", i);
                                       i = i + 1;
                                   }
                
                                   // Цикл until (выполняется, пока условие ЛОЖНО)
                                   var boolean? condition = false;
                                   until (condition == true) {
                                       write("Until: Условие пока ложно");
                                       condition = true; // Устанавливаем в true, чтобы выйти
                                   }
                
                                   // Условный оператор if-then-else
                                   if (global_counter > 0) then {
                                       write("Глобальный счетчик положителен.");
                                   } else {
                                       write("Глобальный счетчик нулевой или отрицательный.");
                                   }
                
                                   // Вызов пользовательской функции
                                   var node? node_a = find_node(g, "A");
                                   var node? node_b = find_node(g, "B");
                                   var int? distance = calculate_distance(node_a, node_b);
                                   write("Расстояние между A и B: ", distance);
                               }
                """;

        String example3 = """
                    program {
                                  // Глобальная переменная
                                  var graph? social_network = read();
                
                                  // Главная функция программы
                                  func traverse() {
                                      // Локальная переменная этой функции
                                      var list? visited = list();
                
                                      // Внутренняя (вложенная) функция
                                      func dfs_traverse(node? n) {
                                          // Проверка на посещение (упрощенная)
                                          if (get_label(n) == "visited") then {
                                              return;
                                          }
                
                                          // Добавляем узел в посещенные
                                          visited = add(visited, n);
                                          write("Visiting: ", n);
                
                                          // Рекурсивный вызов для всех соседей
                                          for (node? neighbor in get_neighbors(social_network, n)) {
                                              dfs_traverse(neighbor);
                                          }
                                      }
                
                                      // Вызов вложенной функции
                                      dfs_traverse(get_first_node(social_network));
                
                                      write("DFS traversal completed.");
                                      write(visited);
                                  }
                
                                  // Альтернатива: использование встроенной функции dfs
                                  func compare_traversals() {
                                      var list? dfs_result = dfs(social_network, get_first_node(social_network));
                                      write("Built-in DFS result: ", dfs_result);
                                  }
                
                                  // Вызов функций
                                  traverse();
                                  compare_traversals();
                    }
                """;
        String customExample = """
                program {
                     var graph? master_graph = graph("Master");
                
                     // Функция верхнего уровня
                     func void process_graph(graph? input_graph) {
                         // Вложенная функция, объявленная внутри другой функции
                         func node? create_and_add_node(string? label) {
                             var node? new_node = node(label);
                             input_graph = input_graph + new_node; // Передача по ссылке: изменяем оригинальный граф
                             return new_node;
                         }
                
                         // Создаем узлы с помощью вложенной функции
                         var node? n1 = create_and_add_node("Alpha");
                         var node? n2 = create_and_add_node("Beta");
                         var node? n3 = create_and_add_node("Gamma");
                
                         // Создаем дуги
                         input_graph = input_graph + arc(n1, n2, 15);
                         input_graph = input_graph + arc(n2, n3, 20);
                         input_graph = input_graph + arc(n3, n1, 25);
                
                         // Используем цикл for для обработки всех узлов
                         var list? all_nodes = get_nodes(input_graph);
                         for (node? n in all_nodes) {
                             var string? lbl = get_label(n);
                             var list? neighbors = get_neighbors(input_graph, n);
                             write("Узел '", lbl, "' имеет ", neighbors.size(), " соседей."); // Упрощенная запись .size()
                
                             // Сложное условие с логическими операторами
                             if ((lbl == "Alpha") or (neighbors.size() > 1)) then {
                                 write(" -> Особый узел или узел с множеством связей.");
                             }
                         }
                
                         // Демонстрация операций с графами: создаем подграф
                         var graph? sub_graph = graph() + n1 + n2 + arc(n1, n2, 15);
                         var graph? difference = input_graph - sub_graph; // Удаляем подграф
                         write("Размер графа после вычитания: ", get_nodes(difference).size());
                
                         // Рекурсивный вызов для демонстрации (условный)
                         if (get_nodes(input_graph).size() < 10) then {
                             // process_graph(input_graph); // Раскомментировать для рекурсии (осторожно!)
                         }
                     }
                
                     // Вызов функции верхнего уровня
                     process_graph(master_graph);
                
                     // Проверка, что master_graph был изменен (демонстрация передачи по ссылке)
                     write("Итоговый размер master_graph: ", get_nodes(master_graph).size());
                
                     // Пример использования nil и проверки
                     var node? non_existent = find_node(master_graph, "NonExistent");
                     if (non_existent == nil) then {
                         write("Узел 'NonExistent' не найден.");
                     }
                
                     // Завершающий вывод
                     write("Обработка графа завершена успешно.");
                 }
                """;

                String customExample1 ="""
            program {
                                                 // Лямбда с блоком кода
                                                 var function? complex_op = (graph? g, node? n) -> {
                                                     var string? label = get_label(n);
                                                     write("Processing: " + label);
                                                     return label;
                                                 };
                        
                                                 // Использование лямбда-функций с графами
                                                 var graph? g = graph();
                                                 var node? start = node("A");
                        
                                                 // Фильтрация узлов
                                                 var function? filter_hidden = (node? n) -> get_label(n) != "hidden";
                        
                                                 // Обход графа с лямбдой
                                                 bfs(g, start, (node? current) -> {
                                                     write("Visiting: " + get_label(current));
                                                     return true; // продолжать обход
                                                 });
                        
                                                 write("Graph processing completed successfully");
                                             }
        """;

//      System.out.println("Исходный код:");
//      System.out.println(example1);

//      System.out.println("\nРезультат компиляции в LLVM IR:");
//      String llvmIr = analyzer.compile(example1);
//      System.out.println(llvmIr);

//      System.out.println("Исходный код:");
//      System.out.println(example2);

//      System.out.println("\nРезультат компиляции в LLVM IR:");
//      System.out.println(analyzer.compile(example2));

//      System.out.println("Исходный код:");
//      System.out.println(example3);

//      System.out.println("\nРезультат компиляции в LLVM IR:");
//      System.out.println(analyzer.compile(example3));

System.out.println("Исходный код:");
System.out.println(customExample1);

System.out.println("\nРезультат компиляции в LLVM IR:");
System.out.println(analyzer.compile(customExample1));
}
}