import cv2

# Загрузка изображения
image = cv2.imread('3.jpg')

# Преобразование в оттенки серого
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

# Применение гауссова размытия для уменьшения шума
blur = cv2.GaussianBlur(gray, (5, 5), 0)

# Применение детектора Кэнни для поиска границ
edges = cv2.Canny(blur, threshold1=100, threshold2=200)

# Сохранение результата в файл
cv2.imwrite('res.jpg', edges)


