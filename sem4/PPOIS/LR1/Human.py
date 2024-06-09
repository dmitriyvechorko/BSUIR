class Human:
    def __init__(self, name="Dmitry", age=19):
        self._name = name
        self._age = age

    def get_name(self) -> str:
        return self._name

    def get_age(self) -> int:
        return self._age
