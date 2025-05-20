import zipfile
import os
from data_loader.file_loader import load_files_from_dir
from typing import List
from langchain.schema import Document

def extract_and_load_zip(zip_path: str, extract_to: str = "temp_data") -> List[Document]:
    with zipfile.ZipFile(zip_path, "r") as zip_ref:
        zip_ref.extractall(extract_to)
    return load_files_from_dir(extract_to)