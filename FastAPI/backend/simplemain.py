# simple with no database

from fastapi import FastAPI
from pydantic import BaseModel
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()

origins = ["*"]

app.add_middleware( CORSMiddleware, allow_origins=origins, allow_credentials=True, allow_methods=["*"], allow_headers=["*"])

tasks = []

class Task(BaseModel): # the post request body should be in this format else it will throw an error
    title: str
    description: str

@app.get("/tasks/")
def get_tasks():
    return tasks

@app.post("/tasks/")
def create_task(task: Task):
    tasks.append(task)
    return {"message": "Task created successfully"}

@app.delete("/tasks/{task_id}")
def delete_task(task_id: int):
    if 0 <= task_id < len(tasks):
        deleted_task = tasks.pop(task_id)
        return {"message": f"Task '{deleted_task.title}' deleted"}
    else:
        return {"error": "Task not found"}

