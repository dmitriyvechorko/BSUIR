import cv2
import numpy as np

# Загрузка изображений
left = cv2.imread('1.png')
right = cv2.imread('2.png')

# Изменение порядка каналов с BGR на RGB
left = cv2.cvtColor(left, cv2.COLOR_BGR2RGB)
right = cv2.cvtColor(right, cv2.COLOR_BGR2RGB)

matrices = {
    'true': np.array([[0.299, 0.587, 0.114,
                       0, 0, 0,
                       0, 0, 0],

                      [0, 0, 0,
                       0, 0, 0,
                       0.299, 0.587, 0.114]]),

    'gray': np.array([[0.299, 0.587, 0.114,
                       0, 0, 0,
                       0, 0, 0],

                      [0, 0, 0,
                       0.299, 0.587, 0.114,
                       0.299, 0.587, 0.114]]),

    'color': np.array([[1, 0, 0,
                        0, 0, 0,
                        0, 0, 0],

                       [0, 0, 0,
                        0, 1, 0,
                        0, 0, 1]]),

    'half_color': np.array([[0.299, 0.587, 0.114,
                            0, 0, 0,
                            0, 0, 0],

                           [0, 0, 0,
                            0, 1, 0,
                            0, 0, 1]]),

    'optimized': np.array([[0, 0.7, 0.3,
                            0, 0, 0,
                            0, 0, 0],

                           [0, 0, 0,
                            0, 1, 0,
                            0, 0, 1]]),
}


def make_anaglyph(right, left, color):
    height, width, _ = left.shape
    leftMap = left.copy()
    rightMap = right.copy()
    m = matrices[color]

    for y in range(0, height):
        for x in range(0, width):
            r1, g1, b1 = leftMap[y, x]
            r2, g2, b2 = rightMap[y, x]
            leftMap[y, x] = (
                int(r1 * m[0][0] + g1 * m[0][1] + b1 * m[0][2] + r2 * m[1][0] + g2 * m[1][1] + b2 * m[1][2]),  # r
                int(r1 * m[0][3] + g1 * m[0][4] + b1 * m[0][5] + r2 * m[1][3] + g2 * m[1][4] + b2 * m[1][5]),  # g
                int(r1 * m[0][6] + g1 * m[0][7] + b1 * m[0][8] + r2 * m[1][6] + g2 * m[1][7] + b2 * m[1][8])  # b
            )
    return leftMap

# Применение выбранного метода анаглифа
anaglyphed_true = make_anaglyph(left, right, 'true')
anaglyphed_gray = make_anaglyph(left, right, 'gray')
anaglyphed_color = make_anaglyph(left, right, 'color')
anaglyphed_half_color = make_anaglyph(left, right, 'half_color')
anaglyphed_optimized = make_anaglyph(left, right, 'optimized')

cv2.imwrite('true_anaglyphs.png', anaglyphed_true)
cv2.imwrite('gray_anaglyphs.png', anaglyphed_gray)
cv2.imwrite('color_anaglyphs.png', anaglyphed_color)
cv2.imwrite('half_color_anaglyphs.png', anaglyphed_half_color)
cv2.imwrite('optimized_anaglyphs.png', anaglyphed_optimized)
