#include <iostream>
#include <fstream>
#include <vector>
#include <string>

using namespace std;

// Пара сокращений
typedef std::vector<size_t> row_t;
typedef std::vector<row_t> matrix_t;

string tests(int numb) {
    string path;
    switch (numb) {
    case 1: { path = "C:/GITHUB/RPIIS/sem1/PIOIVIS/SW/Tests/1.txt"; }
          break;
    case 2: { path = "C:/GITHUB/RPIIS/sem1/PIOIVIS/SW/Tests/2.txt"; }
          break;
    case 3: { path = "C:/GITHUB/RPIIS/sem1/PIOIVIS/SW/Tests/3.txt"; }
          break;
    case 4: { path = "C:/GITHUB/RPIIS/sem1/PIOIVIS/SW/Tests/4.txt"; }
          break;
    case 5: { path = "C:/GITHUB/RPIIS/sem1/PIOIVIS/SW/Tests/5.txt"; }
          break;
    default: cout << "Wrong path.";
    }
    return path;
}
string tests1(int numb1) {
    string path1;
    switch (numb1) {
    case 1: { path1 = "C:/GITHUB/RPIIS/sem1/PIOIVIS/SW/Tests/1_1.txt"; }
          break;
    case 2: { path1 = "C:/GITHUB/RPIIS/sem1/PIOIVIS/SW/Tests/2_1.txt"; }
          break;
    case 3: { path1 = "C:/GITHUB/RPIIS/sem1/PIOIVIS/SW/Tests/3_1.txt"; }
          break;
    case 4: { path1 = "C:/GITHUB/RPIIS/sem1/PIOIVIS/SW/Tests/4_1.txt"; }
          break;
    case 5: { path1 = "C:/GITHUB/RPIIS/sem1/PIOIVIS/SW/Tests/5_1.txt"; }
          break;
    default: cout << "Wrong path.";
    }
    return path1;
}
void graph_into_matrix(vector<vector<int> >& graph, string& path, int& size) {
    ifstream fin(path);
    string s;
    getline(fin, s);
    size = stoi(s);
    cout << size << endl;
    for (int i = 0; i < size; i++) {
        graph.push_back(vector<int>());
        getline(fin, s);
        string tmp;
        for (char j : s) {
            if (j != ' ') {
                tmp += j;
            }
            else {
                graph[i].push_back(stoi(tmp));
                tmp = "";
            }
        }
        graph[i].push_back(stoi(tmp));
    }
    for (int i = 0; i < graph.size(); i++) {
        for (int j = 0; j < graph[i].size(); j++) {
            cout << graph[i][j] << " ";
        }
        cout << endl;
    }
}
bool visited[10000];
void dfs(int v, int& size, vector<vector<int> >& graph)
{
    if (visited[v])
        return;
    visited[v] = true;
    for (int i = 0; i < size; i++)
        if (graph[v][i])
            dfs(i, size, graph);
}
int IsConnected(int& size, vector<vector<int> >& graph)
{
    dfs(0, size, graph);
    for (int i = 0; i < size; i++)
        if (!visited[i])
        {
            cout << "Graph is not connected.\n";
            cout << "It is not Cactus.\n";
            exit(0);
            return 0;
        }
    cout << "Graph is connected.\n";
    return 1;
}
void DFS(matrix_t& graph1, size_t edges, size_t start, row_t& solution)
{
    // Выход если рёбер не осталось
    if (solution.size() > edges)
        return;

    // Добавить текущую вершину в путь
    solution.push_back(start);

    // Поиск непройденных рёбер
    for (size_t i = 0; i < graph1.size(); i++)
    {
        if (graph1[start][i] > 0)
        {
            // Уменьшить количество рёбер
            graph1[start][i]--;
            graph1[i][start]--;

            // Поиск пути
            DFS(graph1, edges, i, solution);

            // Восстановить количество рёбер
            graph1[start][i]++;
            graph1[i][start]++;
        }
    }

    // Если эйлеров цикл не найден - убрать текущую вершину из пути
    if (solution.size() <= edges || solution.back() != solution.front())
        solution.pop_back();
}
int Work_with_Cycles(string& path1) {

    ifstream fin(path1);
    string vertices;
    getline(fin, vertices);

    size_t edges = 0, start = 0;

    
    matrix_t matrix(vertices.size(), row_t(vertices.size(), 0));


    string str;
    while (getline(fin, str))
    {

        
        size_t x, y;
        x = vertices.find(str[0]);
        y = vertices.find(str[1]);


        if (x != string::npos && y != string::npos)
        {
            matrix[x][y]++;
            matrix[y][x]++;
            edges++;
            start = y;
        }
    }

    if (edges == 0)
    {
        cout << "The graph doesn't contain any edges!\n";
        cout << "Graph is not cactus.\n";
        cout << "Press Enter to exit.\n";
        system("pause");
        exit(0);
        return 0;
    }
 
    row_t result;
    DFS(matrix, edges, start, result);

    if (result.empty()) {
        cout << "The graph doesn't contain necessary cycles.\n";
        return 0;
    }
    else {
        return 1;
    }
    fin.close();

}
int main()
{
    ifstream fin;
    int size = 0, numb = 0;
    vector <vector<int> > graph;
    vector <vector<int> > graph1;
    string s;
    cout << "Choose the file (from 1 to 5): ";
    cin >> numb;
    cout << "\n";
    
    string path = tests(numb);
    string path1 = tests1(numb);
    graph_into_matrix(graph, path, size);
    if (IsConnected(size, graph)) {
        if (Work_with_Cycles(path1)){
            cout << "Graph is cactus.\n";
        }
        else{
            cout << "Graph is not cactus.\n";
        }
        exit(0);
    }  
    fin.close();
    return 0;
}