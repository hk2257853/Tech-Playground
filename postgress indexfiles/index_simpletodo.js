// Purpose: Simple Todo App using PostgreSQL (CRUD Operations)

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
const createTableQuery = `
  CREATE TABLE IF NOT EXISTS todos (
    id SERIAL PRIMARY KEY,
    task TEXT,
    completed BOOLEAN
  );
`;
// SERIAL - auto-incrementing integer
pool.query(createTableQuery);

// CRUD Operations
// Create a new todo
app.post('/todos', async (req, res) => {
  try {
    const { task } = req.body;
    const result = await pool.query('INSERT INTO todos (task, completed) VALUES ($1, $2) RETURNING *', [task, false]); // RETURNING * - database returns the entire row that was just inserted
    res.json(result.rows[0]);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Internal Server Error' });
  }
});

// Get all todos
app.get('/todos', async (req, res) => {
  try {
    const result = await pool.query('SELECT * FROM todos');
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
    const { task, completed } = req.body;
    const result = await pool.query('UPDATE todos SET task = $1, completed = $2 WHERE id = $3 RETURNING *', [task, completed, id]);
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

app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}`);
});
