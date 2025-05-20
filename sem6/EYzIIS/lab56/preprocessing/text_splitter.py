from langchain.text_splitter import RecursiveCharacterTextSplitter
from typing import List
from langchain.schema import Document

def split_documents(docs: List[Document]) -> List[Document]:
    splitter = RecursiveCharacterTextSplitter(
        chunk_size=1000,
        chunk_overlap=200,
        separators=["\n\n", "\n", " ", ""]
    )
    return splitter.split_documents(docs)