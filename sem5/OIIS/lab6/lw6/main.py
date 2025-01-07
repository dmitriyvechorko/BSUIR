from mtcnn import MTCNN
import cv2
import matplotlib.pyplot as plt

# Инициализация детектора
detector = MTCNN()

# Загрузка изображения с лицом
image = cv2.cvtColor(cv2.imread('../3.png'), cv2.COLOR_BGR2RGB)

# Загрузка изображения для замены лица
replacement_face = cv2.cvtColor(cv2.imread('../cat.png'), cv2.COLOR_BGR2RGB)

# Распознавание лиц
faces = detector.detect_faces(image)

# Отметка лиц на изображении и замена
for face in faces:
    x, y, width, height = face['box']

    # Изменение размера изображения замены под размер распознанного лица
    replacement_face_resized = cv2.resize(replacement_face, (width, height))

    # Замена лица в исходном изображении
    image[y:y + height, x:x + width] = replacement_face_resized


# Отображение результата
plt.imshow(image)
plt.axis('off')
plt.show()