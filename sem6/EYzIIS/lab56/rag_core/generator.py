import ollama

def generate_answer(query: str, context: str, model: str = "llama3") -> str:
    prompt = f"Ответь на вопрос: {query}\nКонтекст: {context}"
    response = ollama.generate(model=model, prompt=prompt)
    return response["response"]