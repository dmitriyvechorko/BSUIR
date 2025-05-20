import easyocr
from PIL import Image

reader = easyocr.Reader(["ru", "en"])

def extract_text_from_image(image_path: str) -> str:
    result = reader.readtext(image_path, detail=0)
    return " ".join(result)