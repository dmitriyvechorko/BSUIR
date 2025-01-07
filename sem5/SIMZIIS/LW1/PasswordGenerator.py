from random import random

class PasswordGenerator:

    def __init__(self, lw_variant: str = 'абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ'):
        self.charset = list(lw_variant)

    def generate(self, length: int):
        return ''.join(random.choice(self.charset) for _ in range(length))