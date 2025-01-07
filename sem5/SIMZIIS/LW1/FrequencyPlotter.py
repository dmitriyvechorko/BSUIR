import matplotlib.pyplot as plt

class FrequencyPlotter:
    def __init__(self, password_generator):
        self.password_generator = password_generator

    def plot_frequency(self, password_length: int, num_samples: int):
        frequencies = {symbol: 0 for symbol in self.password_generator.charset}
        for _ in range(num_samples):
            password = self.password_generator.generate(password_length)
            for symbol in password:
                frequencies[symbol] += 1

        symbols = list(frequencies.keys())
        counts = list(frequencies.values())

        plt.bar(symbols, counts)
        plt.xlabel('Symbol')
        plt.ylabel('Frequency')
        plt.title('Frequency of Symbols in Generated Passwords')
        plt.show()