from pathlib import Path
from typing import List, Union, Optional
from langchain_core.documents import Document
from langchain_community.document_loaders import (
    PyPDFLoader,
    Docx2txtLoader,
    TextLoader,
    UnstructuredFileLoader
)
import tempfile
import chainlit as cl


def load_single_file(file_path: Union[str, Path]) -> Optional[List[Document]]:
    """Загружает один файл любого поддерживаемого формата"""
    try:
        path = Path(file_path) if isinstance(file_path, str) else file_path

        if path.suffix.lower() == '.pdf':
            loader = PyPDFLoader(str(path))
        elif path.suffix.lower() in ('.docx', '.doc'):
            loader = Docx2txtLoader(str(path))
        elif path.suffix.lower() == '.txt':
            loader = TextLoader(str(path))
        else:
            loader = UnstructuredFileLoader(str(path))

        return loader.load()
    except Exception as e:
        print(f"Ошибка загрузки файла {file_path}: {e}")
        return None


def save_uploaded_file(uploaded_file: cl.File) -> Path:
    """Сохраняет загруженный файл во временную директорию"""
    temp_dir = Path(tempfile.mkdtemp())
    file_path = temp_dir / uploaded_file.name

    with open(file_path, 'wb') as f:
        f.write(uploaded_file.content)

    return file_path


def load_files_from_dir(dir_path: str) -> List[Document]:
    """Загружает все файлы из указанной директории"""
    documents = []
    for file_path in Path(dir_path).glob("*.*"):
        doc = load_single_file(file_path)
        if doc:
            documents.extend(doc)
    return documents
