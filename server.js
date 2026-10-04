const http = require('http');
const fs = require('fs');
const path = require('path');

const PORT = 3000;
const ROOT_DIR = __dirname;

const MIME_TYPES = {
    '.html': 'text/html; charset=utf-8',
    '.css': 'text/css; charset=utf-8',
    '.js': 'application/javascript; charset=utf-8',
    '.json': 'application/json; charset=utf-8',
    '.png': 'image/png',
    '.jpg': 'image/jpeg',
    '.jpeg': 'image/jpeg',
    '.gif': 'image/gif',
    '.svg': 'image/svg+xml',
    '.mp3': 'audio/mpeg',
    '.mp4': 'video/mp4',
    '.apk': 'application/vnd.android.package-archive',
    '.ico': 'image/x-icon'
};

const server = http.createServer((req, res) => {
    let reqUrl = decodeURI(req.url.split('?')[0]);
    if (reqUrl === '/' || reqUrl === '') {
        reqUrl = '/index.html';
    }

    // Direct APK download route
    if (reqUrl === '/download-apk' || reqUrl === '/app-debug.apk') {
        const apkPath = path.join(ROOT_DIR, '.build-outputs', 'app-debug.apk');
        if (fs.existsSync(apkPath)) {
            res.writeHead(200, {
                'Content-Type': 'application/vnd.android.package-archive',
                'Content-Disposition': 'attachment; filename="BuiltByD-PrecisionPhysique.apk"',
                'Content-Length': fs.statSync(apkPath).size
            });
            fs.createReadStream(apkPath).pipe(res);
            return;
        }
    }

    // Try finding the file in root or assets or public
    let filePath = path.join(ROOT_DIR, reqUrl);
    
    // Check if path exists
    if (!fs.existsSync(filePath) || fs.statSync(filePath).isDirectory()) {
        const assetPath = path.join(ROOT_DIR, 'assets', reqUrl);
        if (fs.existsSync(assetPath) && !fs.statSync(assetPath).isDirectory()) {
            filePath = assetPath;
        } else {
            // Fallback to index.html for SPA routes
            filePath = path.join(ROOT_DIR, 'index.html');
        }
    }

    if (!fs.existsSync(filePath)) {
        res.writeHead(404, { 'Content-Type': 'text/plain' });
        res.end('404 Not Found');
        return;
    }

    const ext = path.extname(filePath).toLowerCase();
    const contentType = MIME_TYPES[ext] || 'application/octet-stream';

    const stat = fs.statSync(filePath);
    res.writeHead(200, {
        'Content-Type': contentType,
        'Content-Length': stat.size,
        'Cache-Control': 'no-cache'
    });

    fs.createReadStream(filePath).pipe(res);
});

server.listen(PORT, '0.0.0.0', () => {
    console.log(`Built by D web companion running at http://0.0.0.0:${PORT}`);
});
