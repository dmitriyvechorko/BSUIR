from langchain_huggingface import HuggingFaceEmbeddings
from sentence_transformers import SentenceTransformer

# Инициализация для LangChain
embeddings = HuggingFaceEmbeddings(
    model_name="sentence-transformers/all-MiniLM-L6-v2",
    model_kwargs={"device": "cpu"},  # или "cuda" для GPU
    encode_kwargs={"normalize_embeddings": False}
)