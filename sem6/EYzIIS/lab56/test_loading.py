from data_loader.file_loader import load_files_from_dir

docs = load_files_from_dir("data/")
for doc in docs:
    print(f"\nSource: {doc.metadata['source']}")
    print(f"Content (first 100 chars): {doc.page_content[:100]}...")