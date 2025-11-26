document.addEventListener('DOMContentLoaded', () => {
    // Navbar scroll effect
    window.addEventListener('scroll', () => {
        const navbar = document.getElementById('navbar');
        if (window.scrollY > 50) {
            navbar.classList.add('scrolled');
        } else {
            navbar.classList.remove('scrolled');
        }
    });

    // Smooth scroll for navigation links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();
            const target = document.querySelector(this.getAttribute('href'));
            if (target) {
                target.scrollIntoView({
                    behavior: 'smooth',
                    block: 'start'
                });
            }
        });
    });

    // Booking button click event handler
    document.querySelectorAll('.book-btn, .btn-primary').forEach(btn => {
        btn.addEventListener('click', function(e) {
            const card = e.target.closest('.car-card');
            const carName = card ? card.querySelector('h3').textContent : 'Luxury Vehicle';
            alert(`Thank you for your booking!\n\nModel: ${carName}\n\nOur dedicated account manager will contact you within 30 minutes to provide one-on-one VIP service.\n\nCustomer Hotline: 400-888-8888`);
        });
    });

    // Scroll animation (Intersection Observer for element fade-in)
    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };

    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.style.opacity = '1';
                entry.target.style.transform = 'translateY(0)';
            }
        });
    }, observerOptions);

    // Apply initial hidden styles and observe elements for fade-in effect
    document.querySelectorAll('.feature-card, .car-card, .stat-item').forEach(el => {
        el.style.opacity = '0';
        el.style.transform = 'translateY(30px)';
        el.style.transition = 'opacity 0.6s ease, transform 0.6s ease';
        observer.observe(el);
    });
});