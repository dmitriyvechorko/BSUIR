import dash
from dash import Dash, html, Input, Output, dcc
import plotly.graph_objs as go
import numpy as np
import rasterio
from rasterio.transform import from_origin

# Загрузка данных высот из GeoTIFF
def load_elevation_data(file_path):
    dataset = rasterio.open(file_path)
    return dataset

# Вычисление высоты в данной точке
def calculate_elevation_at_point(dataset, lon, lat):
    # Преобразуем географические координаты (долгота, широта) в пиксели
    row, col = dataset.index(lon, lat)
    elevation = dataset.read(1)[row, col]
    if elevation == dataset.nodata:
        return None
    return elevation


# Создание приложения Dash
def create_dash_app(dataset):
    app = Dash(__name__)

    # Начальные значения для координат
    initial_lon = 37.62
    initial_lat = 55.75
    initial_zoom = 3

    app.layout = html.Div([
        html.H1("Интерактивная карта высот", style={"textAlign": "center"}),

        # Поля для ввода координат
        html.Div([
            html.Label("Введите широту:"),
            dcc.Input(id='lat-input', type='number', value=initial_lat, debounce=True),
        ], style={"marginBottom": "10px"}),

        html.Div([
            html.Label("Введите долготу:"),
            dcc.Input(id='lon-input', type='number', value=initial_lon, debounce=True),
        ], style={"marginBottom": "10px"}),

        # Кнопка для обновления карты
        html.Div([
            html.Button("Показать точку на карте", id='submit-button', n_clicks=0)
        ], style={"marginBottom": "20px"}),

        dcc.Graph(
            id='map',
            config={
                'scrollZoom': False,  # Отключаем масштабирование карты с помощью колесика мыши
                'displayModeBar': True,
                'editable': True,
                'doubleClick': 'reset',  # Убираем возможность вращения карты при двойном клике
            },
            figure={
                'data': [
                    go.Scattermapbox(
                        lat=[initial_lat],  # Центр карты
                        lon=[initial_lon],
                        mode='markers',
                        marker={'size': 14, 'symbol': 'circle', 'color': 'red'},
                    ),
                ],
                'layout': go.Layout(
                    mapbox={
                        'style': "open-street-map",  # Стиль карты
                        'center': {'lat': initial_lat, 'lon': initial_lon},
                        'zoom': initial_zoom,
                        'pitch': 0,  # Устанавливаем угол наклона на 0 (вид сверху)
                        'bearing': 0,  # Убираем поворот карты
                    },
                    margin={'r': 0, 't': 0, 'l': 0, 'b': 0},
                ),
            },
        ),
        html.Div(id="output", style={"padding": "10px", "backgroundColor": "#f4f4f4", "border": "1px solid #ccc"}),
        html.Div(id="coords", style={"padding": "10px", "backgroundColor": "#f4f4f4", "border": "1px solid #ccc"})
    ])

    # Callback для обновления карты и вывода высоты по введенным координатам
    @app.callback(
        [Output("output", "children"),
         Output("coords", "children"),
         Output("map", "figure")],
        [Input("submit-button", "n_clicks")],
        [Input("lat-input", "value"),
         Input("lon-input", "value")]
    )
    def on_coordinates_change(n_clicks, lat, lon):
        if n_clicks > 0:
            try:
                lat = float(lat)
                lon = float(lon)

                # Проверка корректности координат
                if not (-90 <= lat <= 90):
                    elevation_info = "Некорректная широта. Введите значение от -90 до 90."
                    coords_info = "Координаты не определены."
                    figure = dash.no_update
                    return elevation_info, coords_info, figure

                if not (-180 <= lon <= 180):
                    elevation_info = "Некорректная долгота. Введите значение от -180 до 180."
                    coords_info = "Координаты не определены."
                    figure = dash.no_update
                    return elevation_info, coords_info, figure

                # Вычисление высоты в этой точке
                elevation = calculate_elevation_at_point(dataset, lon, lat)
                if elevation is not None:
                    coords_info = f"Координаты: Широта: {lat}, Долгота: {lon}"
                    elevation_info = f"Высота в этой точке: {elevation:.2f} м"
                else:
                    elevation_info = "Некорректные координаты. Нет данных для этой точки"
                    coords_info = "Координаты не определены."

                # Обновляем карту с новыми координатами
                figure = {
                    'data': [
                        go.Scattermapbox(
                            lat=[lat],  # Обновление центра карты
                            lon=[lon],
                            mode='markers',
                            marker={'size': 14, 'symbol': 'circle', 'color': 'red'},
                        ),
                    ],
                    'layout': go.Layout(
                        mapbox={
                            'style': "open-street-map",
                            'center': {'lat': lat, 'lon': lon},  # Обновление центра карты
                            'zoom': 6,  # Можно добавить увеличение для точности
                            'pitch': 0,
                            'bearing': 0,
                        },
                        margin={'r': 0, 't': 0, 'l': 0, 'b': 0},
                    ),
                }

                return elevation_info, coords_info, figure
            except ValueError:
                elevation_info = "Пожалуйста, введите корректные значения для координат."
                coords_info = "Координаты не определены."
                return elevation_info, coords_info, dash.no_update
        else:
            return dash.no_update, dash.no_update, dash.no_update

    return app

if __name__ == '__main__':

    # Загрузка данных высот
    dataset = load_elevation_data("ETOPO1_Bed_g_geotiff.tif") #ищите файл по названию в Интернете

    # Создание и запуск приложения
    app = create_dash_app(dataset)
    app.run_server(debug=True)
