from vector_db.chroma_db import create_vector_store
from typing import List
from langchain.schema import Document

def retrieve_relevant_chunks(query: str, db, top_k: int = 3) -> List[Document]:
    return db.similarity_search(query, k=top_k)