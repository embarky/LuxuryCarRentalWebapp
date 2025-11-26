// Set active menu item based on current page
function setActiveMenu() {
    const currentPage = window.location.pathname.split('/').pop() || 'dashboard.xhtml';
    const menuItems = document.querySelectorAll('.menu-item');

    menuItems.forEach(item => {
        const href = item.getAttribute('href');
        if (href === currentPage) {
            item.classList.add('active');
        } else {
            item.classList.remove('active');
        }
    });
}

// Logout function
function logout() {
    if (confirm('Are you sure you want to logout?')) {
        alert('Logging out...');
        // Redirect to login page
        window.location.href = 'login.html';
    }
}

// Initialize on page load
window.addEventListener('load', setActiveMenu);

// Handle menu clicks
document.querySelectorAll('.menu-item').forEach(item => {
    item.addEventListener('click', function(e) {
        // Remove active class from all items
        document.querySelectorAll('.menu-item').forEach(i => i.classList.remove('active'));
        // Add active class to clicked item
        this.classList.add('active');
    });
});