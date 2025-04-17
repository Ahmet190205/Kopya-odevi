// Token yönetimi için yardımcı fonksiyonlar
const setToken = (token) => localStorage.setItem('jwtToken', token);
const getToken = () => localStorage.getItem('jwtToken');
const removeToken = () => localStorage.removeItem('jwtToken');

async function login() {
    try {
        console.log('Login attempt...');
        const response = await fetch("http://localhost:8080/api/auth/login", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({
                username: document.getElementById('username').value,
                password: document.getElementById('password').value
            })
        });

        console.log('Response status:', response.status);
        
        if (!response.ok) {
            throw new Error('Login failed');
        }

        const data = await response.json();
        console.log('Login successful');
        
        // JWT token'ı kaydet
        setToken(data.token);
        
        // UI güncelleme
        document.getElementById('loginForm').style.display = 'none';
        document.getElementById('uploadForm').style.display = 'block';
        document.getElementById('logoutBtn').style.display = 'block';
        document.getElementById('welcomeMessage').textContent = 
            `Hoş geldin, ${document.getElementById('username').value}!`;

    } catch (error) {
        console.error('Login error:', error);
        document.getElementById('status').textContent = 'Login başarısız: ' + error.message;
    }
}

async function logout() {
    removeToken();
    document.getElementById('loginForm').style.display = 'block';
    document.getElementById('uploadForm').style.display = 'none';
    document.getElementById('logoutBtn').style.display = 'none';
    document.getElementById('welcomeMessage').textContent = '';
    document.getElementById('status').textContent = '';
}

async function uploadPdf() {
    try {
        const token = getToken();
        if (!token) {
            throw new Error('Token bulunamadı, lütfen tekrar giriş yapın');
        }

        const fileInput = document.getElementById('pdfFile');
        const file = fileInput.files[0];
        if (!file) {
            throw new Error('Lütfen bir PDF dosyası seçin');
        }

        const formData = new FormData();
        formData.append('file', file);
        formData.append('username', document.getElementById('username').value);
        formData.append('timestamp', Date.now().toString());

        const response = await fetch("http://localhost:8080/api/pdf/upload", {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            body: formData
        });

        console.log('Upload response status:', response.status);
        
        if (!response.ok) {
            if (response.status === 401 || response.status === 403) {
                // Token geçersiz veya süresi dolmuş
                removeToken();
                document.getElementById('loginForm').style.display = 'block';
                document.getElementById('uploadForm').style.display = 'none';
                throw new Error('Oturum süresi doldu, lütfen tekrar giriş yapın');
            }
            throw new Error('Upload failed: ' + response.status);
        }

        const result = await response.text();
        console.log('Upload result:', result);
        document.getElementById('status').textContent = 'PDF başarıyla yüklendi!';
        fileInput.value = ''; // Input'u temizle

    } catch (error) {
        console.error('Upload error:', error);
        document.getElementById('status').textContent = 'Yükleme başarısız: ' + error.message;
    }
}

// Sayfa yüklendiğinde token kontrolü
document.addEventListener('DOMContentLoaded', () => {
    const token = getToken();
    if (token) {
        document.getElementById('loginForm').style.display = 'none';
        document.getElementById('uploadForm').style.display = 'block';
        document.getElementById('logoutBtn').style.display = 'block';
    } else {
        document.getElementById('loginForm').style.display = 'block';
        document.getElementById('uploadForm').style.display = 'none';
        document.getElementById('logoutBtn').style.display = 'none';
    }
});
