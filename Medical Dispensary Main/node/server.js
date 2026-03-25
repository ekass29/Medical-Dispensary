console.log("Starting server...");

const express = require('express');
const mysql = require('mysql2');
const bodyParser = require('body-parser');
const cors = require('cors');
const path = require('path');

// Initialize Express app
const app = express();
app.use(bodyParser.json()); // To parse JSON request bodies
app.use(cors()); // Enable CORS for all routes

// Serve static files (for assets like HTML, CSS, JS, etc.)
app.use('/assets', express.static(path.join(__dirname, 'assets')));

// Create MySQL database connection
const db = mysql.createConnection({
    host: 'localhost',
    user: 'root', // Your MySQL Workbench username
    password: 'root', // Your MySQL Workbench password
    database: 'health_management',
    port: 3307 // If you're using port 3307
});

// Connect to the database
db.connect(err => {
    if (err) {
        console.error('Database connection failed:', err.stack);
        return;
    }
    console.log('Connected to MySQL database.');
});

// Login route
app.post('/login', (req, res) => {
    const { username, password } = req.body;

    // Correct SQL query definition
    const query = `SELECT * FROM users WHERE username = ? AND password = SHA2(?, 256)`;

    db.query(query, [username, password], (err, result) => {
        if (err) {
            console.error('Database error:', err);
            res.status(500).json({ success: false, message: 'Server error.' });
            return;
        }

        if (result.length > 0) {
            res.json({ success: true, message: 'Login successful!' });
        } else {
            res.json({ success: false, message: 'Invalid username or password.' });
        }
    });
});


// Route to serve the login.html file
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'login.html')); // Path to your login.html file
});

// Route to serve the index.html file
app.get('/index.html', (req, res) => {
    res.sendFile(path.join(__dirname, 'index.html')); // Serve the index page
});

// Route to serve the doctors.html page
app.get('/doctors.html', (req, res) => {
    res.sendFile(path.join(__dirname, 'doctors.html')); // Serve the doctors page
});
app.get('/reminder.html', (req, res) => {
    res.sendFile(path.join(__dirname, 'reminder.html')); // Serve the doctors page
});
app.get('/login.html', (req, res) => {
    res.sendFile(path.join(__dirname, 'login.html')); // Serve the doctors page
});
app.get('/profile.html', (req, res) => {
    res.sendFile(path.join(__dirname, 'profile.html')); // Serve the doctors page
});
app.get('/register.html', (req, res) => {
    res.sendFile(path.join(__dirname, 'register.html')); // Serve the doctors page
});

// Fetch available specialties for the dropdown
app.get('/get-specialties', (req, res) => {
    const query = `SELECT DISTINCT specialty FROM doctors`;
    db.query(query, (err, result) => {
        if (err) {
            console.error('Error fetching specialties:', err);
            res.status(500).json({ success: false, message: 'Server error.' });
            return;
        }

        const specialties = result.map(row => row.specialty);
        res.json({ specialties });
    });
});

// Fetch available locations for the dropdown
app.get('/get-locations', (req, res) => {
    const query = `SELECT DISTINCT city FROM doctors`;
    db.query(query, (err, result) => {
        if (err) {
            console.error('Error fetching locations:', err);
            res.status(500).json({ success: false, message: 'Server error.' });
            return;
        }

        const locations = result.map(row => row.city);
        res.json({ locations });
    });
});

// Search doctors based on specialty and location
app.get('/search-doctors', (req, res) => {
    const { specialty, location } = req.query;

    const query = `SELECT * FROM doctors WHERE specialty LIKE ? AND city LIKE ?`;
    db.query(query, [`%${specialty}%`, `%${location}%`], (err, result) => {
        if (err) {
            console.error('Database error:', err);
            res.status(500).json({ success: false, message: 'Server error.' });
            return;
        }

        if (result.length > 0) {
            res.json({ success: true, doctors: result });
        } else {
            res.json({ success: false, message: 'No doctors found.' });
        }
    });
});
// Route to handle setting reminders
app.post('/set-reminder', (req, res) => {
    const { task, date, time, notes } = req.body;

    // SQL query to insert the reminder into the database
    const query = `INSERT INTO reminders (task, reminder_date, reminder_time, notes) VALUES (?, ?, ?, ?)`;
    db.query(query, [task, date, time, notes], (err, result) => {
        if (err) {
            console.error('Error inserting reminder:', err);
            res.status(500).json({ success: false, message: 'Error saving reminder' });
            return;
        }
        res.json({ success: true, message: 'Reminder set successfully!' });
    });
});

// Route to fetch all reminders
app.get('/get-reminders', (req, res) => {
    const query = 'SELECT * FROM reminders ORDER BY created_at DESC'; // Fetch latest reminders first
    db.query(query, (err, result) => {
        if (err) {
            console.error('Error fetching reminders:', err);
            res.status(500).json({ success: false, message: 'Error fetching reminders' });
            return;
        }
        res.json({ success: true, reminders: result });
    });
});
app.get('/get-user-profile', (req, res) => {
    const username = req.query.username;

    const query = `SELECT full_name, email, phone_number FROM users WHERE username = ?`;
    db.query(query, [username], (err, result) => {
        if (err) {
            console.error('Error fetching user profile:', err);
            res.status(500).json({ success: false, message: 'Server error.' });
            return;
        }

        if (result.length > 0) {
            res.json({ success: true, user: result[0] });
        } else {
            res.json({ success: false, message: 'User not found.' });
        }
    });
});
// Register route
app.post('/register', (req, res) => {
    const { fullName, username, email, phoneNumber, password } = req.body;

    const query = `INSERT INTO users (full_name, username, email, phone_number, password)
                   VALUES (?, ?, ?, ?, SHA2(?, 256))`;

    db.query(query, [fullName, username, email, phoneNumber, password], (err, result) => {
        if (err) {
            console.error('Database error:', err);
            res.status(500).json({ success: false, message: 'Registration failed.' });
            return;
        }

        res.json({ success: true, message: 'Registration successful! Please login.' });
    });
});



// Start the server
const PORT = process.env.PORT || 3000; // Default to 3000, or use an environment variable
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
