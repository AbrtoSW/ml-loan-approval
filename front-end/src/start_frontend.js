const { exec, spawn } = require('child_process');

// Function to find and kill process on port 3000
function killPort(port, callback) {
    const command = process.platform === 'win32'
        ? `netstat -aon | findstr :${port}`
        : `lsof -i :${port} -t`;

    exec(command, (err, stdout) => {
        if (err || !stdout) {
            console.log(`No process found on port ${port}`);
            return callback();
        }

        // Extract PID
        let pid;
        if (process.platform === 'win32') {
            const lines = stdout.split('\n');
            const match = lines[0].match(/\s(\d+)\s*$/);
            pid = match ? parseInt(match[1]) : null;
        } else {
            pid = parseInt(stdout.trim());
        }

        if (pid) {
            console.log(`Killing process ${pid} on port ${port}`);
            process.kill(pid, 'SIGKILL');
            // Wait briefly to ensure the port is released
            setTimeout(callback, 1000);
        } else {
            console.log(`Could not find PID on port ${port}`);
            callback();
        }
    });
}

// Function to start npm
function startNpm() {
    console.log('Starting npm...');
    const npmStart = spawn(process.platform === 'win32' ? 'npm.cmd' : 'npm', ['start'], {
        stdio: 'inherit', // Inherit stdio to see npm start output in terminal
        shell: true
    });

    npmStart.on('error', (err) => {
        console.error('Failed to start npm:', err);
    });

    npmStart.on('close', (code) => {
        console.log(`npm start exited with code ${code}`);
    });
}

// Main function to kill port and start npm
function run() {
    console.log('Attempting to kill port 3000...');
    killPort(3000, () => {
        console.log('Port 3000 cleared, starting application...');
        startNpm();
    });
}

// Execute the main function
run();