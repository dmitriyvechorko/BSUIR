from sqlalchemy.orm import Session
from models.log_model import LogEntry
from datetime import datetime
from typing import List

def log_query(
    session: Session,
    query: str,
    sources: List[str],
    answer: str,
    model: str
):
    log = LogEntry(
        session_id="user_123",
        query_text=query,
        sources=sources,
        answer=answer,
        model_used=model,
        timestamp=datetime.utcnow()
    )
    session.add(log)
    session.commit()