from PIL import Image, ImageFilter

# Открываем исходное изображение
input_image_path = 'input_image.jpg'
image = Image.open(input_image_path)

# Применяем фильтры
filters = {
    "BLUR": ImageFilter.BLUR,
    "CONTOUR": ImageFilter.CONTOUR,
    "DETAIL": ImageFilter.DETAIL,
    "EDGE_ENHANCE": ImageFilter.EDGE_ENHANCE,
    "SHARPEN": ImageFilter.SHARPEN
}

# Сохраняем результаты с разными фильтрами
for filter_name, filter_func in filters.items():
    filtered_image = image.filter(filter_func)
    filtered_image.save(f'output_{filter_name}.jpg')

print("Фильтры применены и изображения сохранены.")