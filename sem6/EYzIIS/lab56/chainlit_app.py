import chainlit as cl
from rag_core.generator import generate_answer
from rag_core.retriever import retrieve_relevant_chunks
from vector_db.chroma_db import load_vector_store
import re

def is_gibberish(text: str, threshold: float = 0.7) -> bool:
    """Определяет, является ли текст случайным набором символов"""
    # Проверяем соотношение букв/небукв (для русского и английского)
    non_word_chars = re.sub(r'[а-яА-ЯёЁa-zA-Z\s]', '', text)
    ratio = len(non_word_chars) / len(text) if text else 0
    return ratio > threshold

@cl.on_chat_start
async def init():
    # Загрузка векторной БД при старте
    db = load_vector_store()
    cl.user_session.set("db", db)
    await cl.Message("Готов к вопросам о кинематографе!").send()


@cl.on_message
async def main(message: cl.Message):
    db = cl.user_session.get("db")


    if is_gibberish(message.content):
        return await cl.Message(
            content="Кажется, вы ввели случайный набор символов. Попробуйте задать другой вопрос!"
        ).send()

    # 1. Поиск релевантных фрагментов
    chunks = retrieve_relevant_chunks(message.content, db)
    context = "\n\n".join([chunk.page_content for chunk in chunks])

    # 2. Генерация ответа
    answer = generate_answer(message.content, context)

    await cl.Message(content=answer).send()
