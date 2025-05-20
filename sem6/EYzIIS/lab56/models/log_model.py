from sqlalchemy import Column, Integer, String, DateTime, JSON, ForeignKey
from sqlalchemy.ext.declarative import declarative_base
from datetime import datetime
# В models/log_model.py
from sqlalchemy import Index

Base = declarative_base()

class DBSession(Base):
    __tablename__ = 'sessions'
    id = Column(String, primary_key=True)  # session_id из Chainlit
    start_time = Column(DateTime, default=datetime.utcnow)
    user_agent = Column(String)
    ip_address = Column(String)

class QueryLog(Base):
    __tablename__ = 'queries'
    id = Column(Integer, primary_key=True, autoincrement=True)
    session_id = Column(String, ForeignKey('sessions.id'))
    query_text = Column(String)
    response_time = Column(Integer)  # В миллисекундах
    timestamp = Column(DateTime, default=datetime.utcnow)
    sources = Column(JSON)  # Список источников из RAG
    model_used = Column(String)  # Например, "llama3"

class DocumentLog(Base):
    __tablename__ = 'processed_documents'
    id = Column(Integer, primary_key=True, autoincrement=True)
    file_name = Column(String)
    file_type = Column(String)
    chunk_count = Column(Integer)
    processing_time = Column(Integer)
    session_id = Column(String, ForeignKey('sessions.id'))
