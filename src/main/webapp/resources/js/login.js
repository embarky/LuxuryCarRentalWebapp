// ========================
// Global Variables
// ========================
let currentRole = 'customer';

// ========================
// Modal Control Functions
// ========================
function openLoginModal() {
    const modal = document.getElementById('loginModal');
    modal.classList.add('active');
    document.body.style.overflow = 'hidden';
}

function closeLoginModal() {
    const modal = document.getElementById('loginModal');
    modal.classList.remove('active');
    document.body.style.overflow = '';
}

// Close modal when clicking outside
document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('loginModal');

    modal.addEventListener('click', function(e) {
        if (e.target === modal) {
            closeLoginModal();
        }
    });

    // Close modal with ESC key
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape') {
            closeLoginModal();
        }
    });

    // Auto-open modal on page load (for demo - remove in production)
    openLoginModal();
});

// ========================
// Role Switching
// ========================
function switchRole(role) {
    currentRole = role;

    // Update active tab
    const tabs = document.querySelectorAll('.role-tab');
    tabs.forEach(tab => {
        if (tab.dataset.role === role) {
            tab.classList.add('active');
        } else {
            tab.classList.remove('active');
        }
    });

    // Update modal content
    const modalTitle = document.getElementById('modalTitle');
    const modalSubtitle = document.getElementById('modalSubtitle');

    if (role === 'customer') {
        modalTitle.textContent = 'Welcome Back';
        modalSubtitle.textContent = 'Login to your account';
    } else if (role === 'admin') {
        modalTitle.textContent = 'Admin Portal';
        modalSubtitle.textContent = 'Login to the management dashboard';
    }

    // Clear form inputs
    document.getElementById('emailInput').value = '';
    document.getElementById('passwordInput').value = '';
    document.getElementById('rememberMe').checked = false;
}

// ========================
// Password Toggle
// ========================
function togglePassword() {
    const passwordInput = document.getElementById('passwordInput');
    const eyeIcon = document.getElementById('eyeIcon');

    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        eyeIcon.innerHTML = `
            <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/>
            <line x1="1" y1="1" x2="23" y2="23"/>
        `;
    } else {
        passwordInput.type = 'password';
        eyeIcon.innerHTML = `
            <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/>
            <circle cx="12" cy="12" r="3"/>
        `;
    }
}

// ========================
// Login Handler
// ========================
function handleLogin() {
    const email = document.getElementById('emailInput').value.trim();
    const password = document.getElementById('passwordInput').value;
    const remember = document.getElementById('rememberMe').checked;

    // Basic validation
    if (!email || !password) {
        alert('Please fill in all fields!');
        return;
    }

    // Email validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert('Please enter a valid email address!');
        return;
    }

    // Log login attempt
    console.log('Login Attempt:', {
        role: currentRole,
        email: email,
        remember: remember,
        timestamp: new Date().toISOString()
    });

    // Simulate successful login
    if (currentRole === 'customer') {
        alert(`Customer Login Successful!\n\nEmail: ${email}\n\nRedirecting to dashboard...`);
        // window.location.href = 'customer-dashboard.html';
    } else if (currentRole === 'admin') {
        alert(`Admin Login Successful!\n\nEmail: ${email}\n\nRedirecting to admin panel...`);
        // window.location.href = 'admin.html';
    }
}

// ========================
// Enter Key Support
// ========================
document.addEventListener('DOMContentLoaded', function() {
    const emailInput = document.getElementById('emailInput');
    const passwordInput = document.getElementById('passwordInput');

    [emailInput, passwordInput].forEach(input => {
        input.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                handleLogin();
            }
        });
    });
});