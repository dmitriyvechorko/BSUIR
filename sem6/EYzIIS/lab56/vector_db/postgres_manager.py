# from sqlalchemy import create_engine
# from sqlalchemy.orm import sessionmaker
# from sqlalchemy.ext.declarative import declarative_base
# import os
#
# DB_URL = "postgresql://postgres:root@localhost:5432/postgres"
#
# engine = create_engine(
#     DB_URL,
#     pool_size=20,
#     max_overflow=10,
#     pool_pre_ping=True
# )
#
# SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
# Base = declarative_base()
#
# def init_db():
#     Base.metadata.create_all(bind=engine)