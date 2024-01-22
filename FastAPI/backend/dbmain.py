# can try: proper folder structure. Not required for now

from fastapi import FastAPI, Depends
from pydantic import BaseModel
from fastapi.middleware.cors import CORSMiddleware
from sqlalchemy import create_engine, Column, Integer, String
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from sqlalchemy.orm import Session

app = FastAPI()

# Configure CORS
origins = ["*"]
app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# SQLAlchemy setup
DATABASE_URL = "sqlite:///./test.db"
engine = create_engine(DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

Base = declarative_base()

class DBTask(Base):
    __tablename__ = "tasks"
    id = Column(Integer, primary_key=True, index=True)
    title = Column(String, index=True)
    description = Column(String)

Base.metadata.create_all(bind=engine)

class Task(BaseModel):
    title: str
    description: str

# Dependency to get a database session
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@app.post("/tasks/")
def create_task(task: Task, db: Session = Depends(get_db)):
    db_task = DBTask(**task.dict())
    db.add(db_task)
    db.commit()
    db.refresh(db_task)
    return {"message": "Task created successfully"}

@app.get("/tasks/")
def get_tasks(db: Session = Depends(get_db)):
    tasks = db.query(DBTask).all()
    return tasks

@app.delete("/tasks/{task_id}")
def delete_task(task_id: int, db: Session = Depends(get_db)):
    task = db.query(DBTask).filter(DBTask.id == task_id).first()
    if task:
        db.delete(task)
        db.commit()
        return {"message": f"Task '{task.title}' deleted"}
    else:
        return {"error": "Task not found"}

