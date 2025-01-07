import itertools
import time

from matplotlib import pyplot as plt


class BruteForceTimePlotter:
    def __init__(self, password_generator):
        self.password_generator = password_generator

    def estimate_brute_force_time(self, password_length: int, samples: int):
        charset = self.password_generator.charset
        total_time = 0

        passwords = [''.join(i) for i in itertools.product(charset, repeat=password_length)]
        for _ in range(samples):
            generated_password = self.password_generator.generate(length=password_length)
            start_time = time.time()
            for password in passwords:
                if password == generated_password:
                    break
            end_time = time.time()
            total_time += end_time - start_time

        return total_time / samples


    def plot_average_time(self, max_password_length: int, samples: int):
        lengths = list(range(1, max_password_length + 1))
        average_times = []

        for length in lengths:
            brute_force_time = self.estimate_brute_force_time(length, samples)
            average_times.append(brute_force_time)

        plt.plot(lengths, average_times)
        plt.xlabel('Password Length')
        plt.ylabel('Estimated Brute Force Time (seconds)')
        plt.title('Estimated Brute Force Time for Passwords of Different Lengths')
        plt.show()
