from typing import List
from langchain.schema import Document
from langchain_community.vectorstores import Chroma
from embedding.embedder import embeddings


def create_vector_store(docs: List[Document], persist_dir: str = "chroma_db"):
    # Проверка, что документы не пустые
    if not docs:
        raise ValueError("No documents to process")

    # Проверка, что текст не пустой
    valid_docs = [doc for doc in docs if doc.page_content.strip()]
    if not valid_docs:
        raise ValueError("All documents have empty content")

    return Chroma.from_documents(
        documents=valid_docs,
        embedding=embeddings,
        persist_directory=persist_dir
    )


def load_vector_store(persist_dir: str = "chroma_db"):
    return Chroma(
        persist_directory=persist_dir,
        embedding_function=embeddings
    )
