import re

def is_gibberish(text: str,
                 min_word_length: int = 3,
                 max_non_word_ratio: float = 0.6) -> bool:
    """
    Определяет, является ли текст бессмысленным набором символов
    Параметры:
        min_word_length: минимальная длина осмысленного слова
        max_non_word_ratio: максимально допустимая доля "не-слов"
    """
    if not text.strip():
        return True

    # Разделяем на "слова" (последовательности букв)
    words = re.findall(r'[а-яА-ЯёЁa-zA-Z]{2,}', text)
    non_word_parts = re.sub(r'[а-яА-ЯёЁa-zA-Z\s]', '', text)

    # Расчет показателей
    word_ratio = len(words) / (len(text.split()) or 1)
    non_word_ratio = len(non_word_parts) / len(text)

    # Критерии бреда:
    # 1. Слишком много несловесных символов
    # 2. Нет слов нужной длины
    has_proper_words = any(len(w) >= min_word_length for w in words)

    return non_word_ratio > max_non_word_ratio or not has_proper_words