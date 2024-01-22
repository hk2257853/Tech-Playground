const taskForm = document.getElementById("task-form");
const todoList = document.getElementById("todo-list");

// Fetch tasks from the server and display them
async function fetchTasks() {
    const response = await fetch("http://127.0.0.1:8000/tasks/");
    const tasks = await response.json();

    todoList.innerHTML = ""; // Clear the existing list

    tasks.forEach(task => {
        const li = document.createElement("li");
        li.textContent = `${task.title}: ${task.description}`;
        todoList.appendChild(li);
    });
}

// Add a new task
taskForm.addEventListener("submit", async event => {
    event.preventDefault();

    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;

    const response = await fetch("http://127.0.0.1:8000/tasks/", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            title: title,
            description: description
        })
    });

    await fetchTasks(); // Fetch tasks again to update the list
});

// Fetch tasks when the page loads
fetchTasks();
