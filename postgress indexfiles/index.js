// this one has todos and apis table (to learn how to use foreign key and join)
// todos has todoid, task, completed, api_id (foreign key)
// apis has apiid, name, description

import pg from 'pg';
const { Pool } = pg;
import express from 'express';


const app = express();
const PORT = 3000;

// Set up PostgreSQL connection
const pool = new Pool({
  user: 'postgres',
  host: 'localhost',
  database: 'hellopg',
  password: '1234',
  port: 5432,
});

app.use(express.json());

// Create Todo Table
const createTodoTableQuery = `
  CREATE TABLE IF NOT EXISTS todos (
    id SERIAL PRIMARY KEY,
    task TEXT,
    completed BOOLEAN,
    api_id INT REFERENCES apis(id)
  );
`;
pool.query(createTodoTableQuery);
// api_id is the foreign key

// Create APIs Table
const createApisTableQuery = `
  CREATE TABLE IF NOT EXISTS apis (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT
  );
`;
pool.query(createApisTableQuery);

// CRUD Operations

// Create a new todo
app.post('/todos', async (req, res) => {
  try {
    const { task, api_id } = req.body;
    const result = await pool.query('INSERT INTO todos (task, completed, api_id) VALUES ($1, $2, $3) RETURNING *', [task, false, api_id]);
    res.json(result.rows[0]);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

// Get all todos with API information using JOIN
app.get('/todos', async (req, res) => {
  try {
    const result = await pool.query('SELECT todos.*, apis.name as api_name FROM todos LEFT JOIN apis ON todos.api_id = apis.id');
    res.json(result.rows);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

// Update a todo
app.put('/todos/:id', async (req, res) => {
  try {
    const { id } = req.params;
    const { task, completed, api_id } = req.body;
    const result = await pool.query('UPDATE todos SET task = $1, completed = $2, api_id = $3 WHERE id = $4 RETURNING *', [task, completed, api_id, id]);
    res.json(result.rows[0]);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

// Delete a todo
app.delete('/todos/:id', async (req, res) => {
  try {
    const { id } = req.params;
    await pool.query('DELETE FROM todos WHERE id = $1', [id]);
    res.json({ message: 'Todo deleted successfully' });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

// apis table post
app.post('/apis', async (req, res) => {
  try {
    const { name, description } = req.body;
    const result = await pool.query('INSERT INTO apis (name, description) VALUES ($1, $2) RETURNING *', [name, description]);
    res.json(result.rows[0]);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

// apis table get
app.get('/apis', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM apis');
    res.json(result.rows);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
