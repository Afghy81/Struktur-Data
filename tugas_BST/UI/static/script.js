/* ========================
   AVL Tree Web UI - JavaScript
   ======================== */

// Base API URL
const API_URL = '/api';

// ========================
// Tab Navigation
// ========================

function showTab(tabName) {
    // Hide all tabs
    const tabs = document.querySelectorAll('.tab-content');
    tabs.forEach(tab => tab.classList.remove('active'));

    // Remove active class from all buttons
    const buttons = document.querySelectorAll('.nav-btn');
    buttons.forEach(btn => btn.classList.remove('active'));

    // Show selected tab
    const selectedTab = document.getElementById(tabName);
    if (selectedTab) {
        selectedTab.classList.add('active');
    }

    // Add active class to clicked button
    event.target.classList.add('active');

    // Special handling for tabs
    if (tabName === 'dashboard') {
        refreshDashboard();
    } else if (tabName === 'tree') {
        visualizeTree();
    }
}

// ========================
// Feedback Functions
// ========================

function showFeedback(message, type = 'info', duration = 5000) {
    const feedback = document.getElementById('input-feedback') || createFeedback();
    feedback.textContent = message;
    feedback.className = `feedback ${type} show`;
    feedback.style.display = 'block';

    if (duration > 0) {
        setTimeout(() => {
            feedback.classList.remove('show');
        }, duration);
    }
}

function createFeedback() {
    const feedback = document.createElement('div');
    feedback.id = 'input-feedback';
    feedback.className = 'feedback';
    document.querySelector('.main-content') || document.body.appendChild(feedback);
    return feedback;
}

function showError(message) {
    showFeedback('❌ ' + message, 'error');
    console.error(message);
}

function showSuccess(message) {
    showFeedback('✅ ' + message, 'success');
}

function showInfo(message) {
    showFeedback('ℹ️ ' + message, 'info');
}

// ========================
// Dashboard Functions
// ========================

async function refreshDashboard() {
    try {
        // Get stats
        const response = await fetch(`${API_URL}/tree/stats`);
        const data = await response.json();

        if (data.success) {
            const stats = data.data;
            document.getElementById('stat-total').textContent = stats.total_nodes;
            document.getElementById('stat-height').textContent = stats.tree_height;
            document.getElementById('stat-status').textContent = 
                stats.is_empty ? '📭 Kosong' : '✅ Berisi Data';

            // Get ASCII tree
            const asciiResponse = await fetch(`${API_URL}/tree/ascii`);
            const asciiData = await asciiResponse.json();
            if (asciiData.success) {
                document.getElementById('ascii-output').textContent = 
                    asciiData.data || '(Pohon kosong)';
            }
        }
    } catch (error) {
        showError('Gagal memuat dashboard: ' + error.message);
    }
}

// ========================
// Input Functions
// ========================

async function insertSingleData() {
    const idInput = document.getElementById('input-id');
    const namaInput = document.getElementById('input-nama');

    const id = parseInt(idInput.value);
    const nama = namaInput.value.trim();

    if (isNaN(id)) {
        showError('ID harus berupa angka');
        return;
    }

    if (!nama) {
        showError('Nama tidak boleh kosong');
        return;
    }

    try {
        const response = await fetch(`${API_URL}/insert`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ id: id, nama: nama })
        });

        const data = await response.json();

        if (data.success) {
            showSuccess(`Data berhasil ditambahkan: ID=${id}, Nama=${nama}`);
            idInput.value = '';
            namaInput.value = '';
            namaInput.focus();
            refreshDashboard();
        } else {
            showError(data.message || 'Gagal menambah data');
        }
    } catch (error) {
        showError('Error: ' + error.message);
    }
}

async function insertBulkData() {
    const bulkInput = document.getElementById('bulk-input').value.trim();

    if (!bulkInput) {
        showError('Masukkan data terlebih dahulu');
        return;
    }

    const lines = bulkInput.split('\n');
    const records = [];

    for (let line of lines) {
        line = line.trim();
        if (!line) continue;

        // Parse different separators
        let parts = [];
        if (line.includes('\t')) {
            parts = line.split('\t');
        } else if (line.includes(',')) {
            parts = line.split(',');
        } else if (line.includes(';')) {
            parts = line.split(';');
        } else {
            parts = line.split(/\s+/);
        }

        if (parts.length >= 2) {
            try {
                const id = parseInt(parts[0].trim());
                const nama = parts[1].trim();
                if (!isNaN(id) && nama) {
                    records.push({ id: id, nama: nama });
                }
            } catch (e) {
                // Skip invalid records
            }
        }
    }

    if (records.length === 0) {
        showError('Tidak ada data valid untuk ditambahkan');
        return;
    }

    try {
        const response = await fetch(`${API_URL}/insert-bulk`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ records: records })
        });

        const data = await response.json();

        if (data.success) {
            showSuccess(`${data.added} data berhasil ditambahkan`);
            if (data.failed > 0) {
                showInfo(`${data.failed} data gagal ditambahkan`);
            }
            document.getElementById('bulk-input').value = '';
            refreshDashboard();
        } else {
            showError(data.message || 'Gagal menambah data');
        }
    } catch (error) {
        showError('Error: ' + error.message);
    }
}

// ========================
// Search Functions
// ========================

async function searchById() {
    const searchId = document.getElementById('search-id').value.trim();

    if (!searchId) {
        showError('Masukkan ID terlebih dahulu');
        return;
    }

    try {
        const response = await fetch(`${API_URL}/search/id/${searchId}`);
        const data = await response.json();
        const resultsDiv = document.getElementById('search-results');

        if (data.success) {
            const result = data.data;
            resultsDiv.innerHTML = `
                <div class="result-item">
                    <div><strong>ID:</strong> ${result.id}</div>
                    <div><strong>Nama:</strong> ${result.nama}</div>
                    <div><strong>Posisi Inorder:</strong> ${result.inorder_position}</div>
                    <div><strong>Tinggi Node:</strong> ${result.height}</div>
                    <div><strong>Balance Factor:</strong> ${result.balance_factor}</div>
                    <button class="btn btn-danger" onclick="deleteData(${result.id})">
                        🗑️ Hapus Data
                    </button>
                </div>
            `;
            showSuccess('Data ditemukan');
        } else {
            resultsDiv.innerHTML = '<div class="result-item">❌ Data tidak ditemukan</div>';
            showError(data.message || 'Data tidak ditemukan');
        }
    } catch (error) {
        showError('Error: ' + error.message);
    }
}

async function searchByName() {
    const searchName = document.getElementById('search-name').value.trim();

    if (!searchName) {
        showError('Masukkan nama terlebih dahulu');
        return;
    }

    try {
        const response = await fetch(`${API_URL}/search/name/${encodeURIComponent(searchName)}`);
        const data = await response.json();
        const resultsDiv = document.getElementById('search-results');

        if (data.success) {
            let html = `<p><strong>Ditemukan ${data.count} data:</strong></p>`;
            data.data.forEach(result => {
                html += `
                    <div class="result-item">
                        <div><strong>No:</strong> ${result.number}</div>
                        <div><strong>ID:</strong> ${result.id}</div>
                        <div><strong>Nama:</strong> ${result.nama}</div>
                        <div><strong>Posisi Inorder:</strong> ${result.inorder_position}</div>
                        <button class="btn btn-danger" onclick="deleteData(${result.id})">
                            🗑️ Hapus
                        </button>
                    </div>
                `;
            });
            resultsDiv.innerHTML = html;
            showSuccess(`Ditemukan ${data.count} data`);
        } else {
            resultsDiv.innerHTML = '<div class="result-item">❌ Data tidak ditemukan</div>';
            showError(data.message || 'Data tidak ditemukan');
        }
    } catch (error) {
        showError('Error: ' + error.message);
    }
}

// ========================
// Delete Functions
// ========================

async function deleteData(nodeId) {
    if (!confirm('Yakin ingin menghapus data ini?')) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/delete/${nodeId}`, {
            method: 'DELETE'
        });

        const data = await response.json();

        if (data.success) {
            showSuccess('Data berhasil dihapus');
            // Refresh current search or view
            if (document.getElementById('search-id').value) {
                searchById();
            } else if (document.getElementById('search-name').value) {
                searchByName();
            }
            refreshDashboard();
        } else {
            showError(data.message || 'Gagal menghapus data');
        }
    } catch (error) {
        showError('Error: ' + error.message);
    }
}

// ========================
// Traversal Functions
// ========================

async function getTraversal(type) {
    try {
        const response = await fetch(`${API_URL}/traversal/${type}`);
        const data = await response.json();
        const resultsDiv = document.getElementById('traversal-results');

        if (data.success) {
            let html = `<h3>${getTraversalTitle(type)}</h3>`;
            html += `<p><strong>Total: ${data.count} data</strong></p>`;
            
            if (data.count === 0) {
                html += '<p>Pohon kosong</p>';
            } else {
                html += '<div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(200px, 1fr)); gap: 10px;">';
                data.data.forEach(item => {
                    html += `
                        <div class="result-item">
                            <div><span class="result-number">${item.position}</span></div>
                            <div><strong>ID:</strong> ${item.id}</div>
                            <div><strong>Nama:</strong> ${item.nama}</div>
                        </div>
                    `;
                });
                html += '</div>';
            }
            resultsDiv.innerHTML = html;
            showSuccess(`Traversal ${type} berhasil`);
        } else {
            showError(data.message || 'Gagal fetch traversal');
        }
    } catch (error) {
        showError('Error: ' + error.message);
    }
}

function getTraversalTitle(type) {
    const titles = {
        'inorder': '📋 Inorder Traversal (Kiri-Root-Kanan)',
        'preorder': '📋 Preorder Traversal (Root-Kiri-Kanan)',
        'postorder': '📋 Postorder Traversal (Kiri-Kanan-Root)'
    };
    return titles[type] || 'Traversal';
}

// ========================
// Tree Visualization
// ========================

// Global zoom and pan state variables
let currentZoom = 1;
const MIN_ZOOM = 0.5;
const MAX_ZOOM = 3;
let svgGroup = null;
let svgElement = null;
let translateX = 0, translateY = 0;  // Current pan position
let treeMargin = { top: 60, right: 60, bottom: 60, left: 60 };

// Pan tracking state
let panState = {
    isPanning: false,
    startX: 0,
    startY: 0,
    lastTranslateX: 0,
    lastTranslateY: 0
};

function zoomIn() {
    if (currentZoom < MAX_ZOOM) {
        currentZoom += 0.1;
        currentZoom = Math.round(currentZoom * 10) / 10;
        applyZoom();
        console.log(`Zoom in: ${Math.round(currentZoom * 100)}%`);
    }
}

function zoomOut() {
    if (currentZoom > MIN_ZOOM) {
        currentZoom -= 0.1;
        currentZoom = Math.round(currentZoom * 10) / 10;
        applyZoom();
        console.log(`Zoom out: ${Math.round(currentZoom * 100)}%`);
    }
}

function resetZoom() {
    // FIX: Reset zoom and pan - provide user feedback
    if (!svgGroup) {
        showError('No tree rendered yet. Click "Visualisasi Tree" first.');
        return;
    }
    
    currentZoom = 1;
    translateX = 0;
    translateY = 0;
    applyZoom();
    showSuccess('✅ View reset to default (100% zoom, centered)');
}

function applyZoom() {
    const zoomPercentage = Math.round(currentZoom * 100);
    const zoomDisplay = document.getElementById('zoom-percentage');
    if (zoomDisplay) {
        zoomDisplay.textContent = zoomPercentage + '%';
    }
    
    if (svgGroup) {
        svgGroup.attr('transform', 
            `translate(${treeMargin.left + translateX},${treeMargin.top + translateY}) scale(${currentZoom})`
        );
    }
    
    // DEBUG: Log state untuk ensure pan tracking bekerja
    console.log(`✨ Zoom/Pan: ${zoomPercentage}% | Pan: (${Math.round(translateX)}, ${Math.round(translateY)})`);
}


async function visualizeTree() {
    try {
        showInfo('⏳ Memproses data tree...');
        const response = await fetch(`${API_URL}/tree/data`);
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const data = await response.json();

        if (data.success && data.data) {
            renderTreeVisualization(data.data);
        } else {
            showError(data.message || 'Gagal load tree data');
        }
    } catch (error) {
        console.error('Visualization error:', error);
        showError('Error loading tree: ' + error.message);
    }
}

function calculateTreeMetrics(node, metrics = { width: 0, height: 0 }) {
    // Calculate tree dimensions for adaptive layout
    metrics.height = Math.max(metrics.height, node.height || 0);
    if (node.children) {
        for (let child of node.children) {
            calculateTreeMetrics(child, metrics);
        }
    }
    return metrics;
}

function renderTreeVisualization(treeData) {
    const container = document.getElementById('tree-visualization');
    
    // Validate container exists
    if (!container) {
        console.error('Tree visualization container not found');
        showError('Container tidak ditemukan');
        return;
    }

    // Clear previous content - BUT preserve zoom controls container  
    container.innerHTML = '';

    // Validate tree data - check if tree has nodes
    if (!treeData || (!treeData.value && treeData.size === 0)) {
        const emptyMsg = document.createElement('div');
        emptyMsg.className = 'tree-empty-message';
        emptyMsg.innerHTML = '📭 Pohon kosong<br><small>Tambahkan data terlebih dahulu</small>';
        container.appendChild(emptyMsg);
        return;
    }

    console.log('Rendering tree data:', treeData);

    // Reset zoom and pan
    currentZoom = 1;
    translateX = 0;
    translateY = 0;

    // Calculate tree metrics for adaptive layout
    const metrics = calculateTreeMetrics(treeData);
    const treeSize = treeData.size || 10;
    const treeHeight = metrics.height || 3;
    
    console.log(`Tree metrics: size=${treeSize}, height=${treeHeight}`);

    // Calculate dimensions with adaptive scaling for large trees
    let width = Math.max(container.clientWidth - 40, 500);
    let height = Math.max(container.clientHeight - 40, 400);
    
    // Increase canvas size for large trees - MORE AGGRESSIVE SCALING
    if (treeSize > 15) {
        width = Math.max(width, 1500);
        height = Math.max(height, 800);
    }
    if (treeSize > 30) {
        width = Math.max(width, 3000);
        height = Math.max(height, 1100);
    }
    if (treeSize > 50) {
        width = Math.max(width, 5000);
        height = Math.max(height, 1400);
    }
    if (treeSize > 100) {
        width = Math.max(width, 10000);
        height = Math.max(height, 1700);
    }
    
    treeMargin = { top: 80, right: 80, bottom: 80, left: 80 };
    
    // IMPROVED: Adaptive spacing dengan formula yang lebih baik
    // Untuk tree dengan banyak node, spacing harus lebih besar
    let verticalSpacing = Math.max(120, Math.min(200, 800 / Math.max(treeHeight, 1)));
    // let horizontalSpacing = Math.max(200, Math.min(400, 1000 / Math.max(treeSize / treeHeight, 1)));

    const innerWidth = width - treeMargin.left - treeMargin.right;
    const innerHeight = Math.max(height - treeMargin.top - treeMargin.bottom, treeHeight * verticalSpacing + 200);

    if (innerWidth <= 0 || innerHeight <= 0) {
        showError('Ukuran container tidak valid');
        return;
    }

    // Create SVG container with proper styling
    const svg = d3.select(container)
        .append('svg')
        .attr('width', width)
        .attr('height', Math.max(height, innerHeight + treeMargin.top + treeMargin.bottom))
        .attr('class', 'tree-svg')
        .style('border', '1px solid #bdc3c7')
        .style('border-radius', '5px')
        .style('background', 'white')
        .style('display', 'block')
        .style('margin', '0 auto')
        .style('cursor', 'grab')
        .style('touch-action', 'none')
        .style('user-select', 'none')
        .style('pointer-events', 'auto');

    svgElement = svg;

    // Add background gradient
    const defs = svg.append('defs');
    const bgGradient = defs.append('linearGradient')
        .attr('id', 'bgGradient')
        .attr('x1', '0%')
        .attr('y1', '0%')
        .attr('x2', '100%')
        .attr('y2', '100%');
    bgGradient.append('stop')
        .attr('offset', '0%')
        .attr('stop-color', '#ffffff')
        .attr('stop-opacity', 1);
    bgGradient.append('stop')
        .attr('offset', '100%')
        .attr('stop-color', '#f5f7fa')
        .attr('stop-opacity', 1);

    svg.append('rect')
        .attr('width', '100%')
        .attr('height', '100%')
        .attr('fill', 'url(#bgGradient)')
        .attr('pointer-events', 'auto')
        .style('pointer-events', 'none');

    // Create main group for zoom/pan
    svgGroup = svg.append('g')
        .attr('class', 'tree-group')
        .attr('transform', `translate(${treeMargin.left},${treeMargin.top})`)
        .style('pointer-events', 'auto');

    try {
        // Adaptive tree layout based on tree size
        // IMPROVED: Use calculated spacing for better horizontal distribution
        const tree = d3.tree().size([innerWidth, innerHeight]);
        const root = d3.hierarchy(treeData);
        tree(root);
        
        // Adaptive node radius based on tree size - SMALLER for many nodes
        const nodeRadius = treeSize > 50 ? 25 : treeSize > 30 ? 32 : treeSize > 15 ? 40 : 45;
        
        console.log(`Layout params: nodeRadius=${nodeRadius}, innerSize=[${innerWidth}, ${innerHeight}], treeSize=${treeSize}`);

        // Draw links FIRST (so they appear behind nodes)
        const links = svgGroup.selectAll('.link')
            .data(root.links())
            .enter()
            .append('line')
            .attr('class', 'link')
            .attr('x1', d => d.source.x)
            .attr('y1', d => d.source.y)
            .attr('x2', d => d.target.x)
            .attr('y2', d => d.target.y)
            .style('stroke', '#34495e')
            .style('stroke-width', '2.5px')
            .style('opacity', '0.8');

        // Create node groups
        const nodeGroups = svgGroup.selectAll('.node-group')
            .data(root.descendants())
            .enter()
            .append('g')
            .attr('class', 'node-group')
            .attr('transform', d => `translate(${d.x},${d.y})`)
            .style('cursor', 'pointer')
            .style('opacity', '0');

        // Add animation entrance
        nodeGroups.transition()
            .duration(300)
            .style('opacity', '1');

        // Draw node circles with adaptive radius
        nodeGroups.append('circle')
            .attr('class', 'node')
            .attr('cx', 0)
            .attr('cy', 0)
            .attr('r', nodeRadius)
            .style('fill', '#27ae60')
            .style('stroke', '#16a085')
            .style('stroke-width', '3px')
            .style('filter', 'drop-shadow(0 2px 4px rgba(0, 0, 0, 0.2))')
            .style('pointer-events', 'auto')
            .on('mouseenter', function() {
                d3.select(this)
                    .transition()
                    .duration(200)
                    .style('fill', '#2ecc71')
                    .style('stroke', '#1abc9c')
                    .style('stroke-width', '4px')
                    .style('filter', 'drop-shadow(0 4px 8px rgba(0, 0, 0, 0.3))');
            })
            .on('mouseleave', function() {
                d3.select(this)
                    .transition()
                    .duration(200)
                    .style('fill', '#27ae60')
                    .style('stroke', '#16a085')
                    .style('stroke-width', '3px')
                    .style('filter', 'drop-shadow(0 2px 4px rgba(0, 0, 0, 0.2))');
            });

        // Add ID text inside circle (adaptive font size)
        const idFontSize = nodeRadius > 30 ? '11px' : '12px';
        nodeGroups.append('text')
            .attr('class', 'node-id')
            .attr('x', 0)
            .attr('y', -5)
            .text(d => {
                if (d.data.id) {
                    return `ID:${d.data.id}`;
                }
                return d.data.id || '';
            })
            .style('font', `bold ${idFontSize} "Segoe UI", Arial, sans-serif`)
            .style('pointer-events', 'none')
            .style('fill', 'white')
            .style('text-anchor', 'middle')
            .style('dominant-baseline', 'central')
            .style('letter-spacing', '0.5px');

        // Add name text inside circle (below ID) - adaptive
        // FIX: Use d.data.label (nama only) not d.data.name (combined ID+nama)
        const nameFontSize = nodeRadius > 30 ? '8px' : '10px';
        nodeGroups.append('text')
            .attr('class', 'node-name')
            .attr('x', 0)
            .attr('y', 6)
            .text(d => {
                const name = d.data.label || '';  // FIX: Use label for nama only
                if (name.length > 12) {
                    return name.substring(0, 12) + '...';
                }
                return name;
            })
            .style('font', `${nameFontSize} "Segoe UI", Arial, sans-serif`)
            .style('pointer-events', 'none')
            .style('fill', 'white')
            .style('text-anchor', 'middle')
            .style('dominant-baseline', 'central')
            .style('font-weight', '500');

        // === NATIVE EVENT HANDLERS FOR PAN & ZOOM ===
        // FIXED: Proper pan handling dengan tracking position secara real-time
        let isPanning = false;
        let panStartX = 0, panStartY = 0;
        let lastTranslateX = 0, lastTranslateY = 0;
        
        const svgNode = svg.node();
        
        // Mouse wheel zoom - NATIVE event listener
        svgNode.addEventListener('wheel', function(event) {
            event.preventDefault();
            const zoomDelta = event.deltaY > 0 ? -0.1 : 0.1;
            const newZoom = currentZoom + zoomDelta;
            
            if (newZoom >= MIN_ZOOM && newZoom <= MAX_ZOOM) {
                currentZoom = Math.round(newZoom * 10) / 10;
                applyZoom();
            }
        }, { passive: false });

        // Mouse down - start panning (capture position sebelum drag dimulai)
        svgNode.addEventListener('mousedown', function(event) {
            // Both left-click (0) and right-click (2) enable panning
            if (event.button === 0 || event.button === 2) {
                isPanning = true;
                panStartX = event.clientX;
                panStartY = event.clientY;
                lastTranslateX = translateX;
                lastTranslateY = translateY;
                svgNode.classList.add('panning');
                svgNode.style.cursor = 'grabbing';
                if (event.button === 2) event.preventDefault();
            }
        });

        // Mouse move - pan DYNAMICALLY (update translateX/Y based on current cursor position)
        document.addEventListener('mousemove', function(event) {
            if (isPanning) {
                // Calculate difference from START of drag, not from previous position
                const dx = event.clientX - panStartX;
                const dy = event.clientY - panStartY;
                
                // Update translate based on last known position + current delta
                // This prevents "jumping back" effect
                translateX = lastTranslateX + dx;
                translateY = lastTranslateY + dy;
                
                applyZoom();
            }
        });

        // Mouse up - stop panning
        document.addEventListener('mouseup', function(event) {
            isPanning = false;
            svgNode.classList.remove('panning');
            svgNode.style.cursor = 'grab';
        });

        // Mouse leave svg - stop panning (keep last position)
        svgNode.addEventListener('mouseleave', function(event) {
            if (isPanning) {
                lastTranslateX = translateX;
                lastTranslateY = translateY;
            }
            isPanning = false;
            svgNode.classList.remove('panning');
            svgNode.style.cursor = 'grab';
        });

        // Context menu - prevent default
        svgNode.addEventListener('contextmenu', function(event) {
            event.preventDefault();
        });

    } catch (error) {
        console.error('Error rendering tree:', error);
        showError('Error rendering tree: ' + error.message);
        return;
    }

    // === CREATE ZOOM CONTROLS DYNAMICALLY ===
    // Remove old controls if they exist
    const oldControls = container.querySelector('.zoom-controls');
    const oldZoomLevel = container.querySelector('.zoom-level');
    if (oldControls) oldControls.remove();
    if (oldZoomLevel) oldZoomLevel.remove();

    // Create zoom controls container
    const zoomControls = document.createElement('div');
    zoomControls.className = 'zoom-controls';
    zoomControls.innerHTML = `
        <button class="zoom-btn" onclick="zoomIn()" title="Perbesar (Zoom In)">➕</button>
        <button class="zoom-btn" onclick="zoomOut()" title="Perkecil (Zoom Out)">➖</button>
        <button class="zoom-btn" onclick="resetZoom()" title="Reset Zoom ke Default">⟲</button>
    `;
    container.appendChild(zoomControls);

    // Create zoom level display
    const zoomLevel = document.createElement('div');
    zoomLevel.className = 'zoom-level';
    zoomLevel.innerHTML = `<strong>Zoom: <span id="zoom-percentage">100</span></strong>`;
    container.appendChild(zoomLevel);

    // Apply initial zoom (which is 1)
    applyZoom();

    showSuccess(`✅ Visualisasi tree berhasil! 🎨<br>
        <strong style="color:#27ae60;">🎮 KONTROL CANVAS</strong><br>
        • <strong>Zoom:</strong> ➕/➖ tombol OR scroll mouse wheel<br>
        • <strong>Move/Pan:</strong> Klik + drag (Left/Right-click) - SMOOTH & DYNAMIC! ✨<br>
        • <strong>Reset:</strong> ⟲ tombol untuk kembali ke view default<br>
        <em>Cursor: ✋ grab → 👐 grabbing saat drag aktif</em><br>
        <span style="color: #e74c3c; font-size: 0.9em;">📐 Layout otomatis optimal untuk berbagai jumlah data!</span>`);
    console.log('✅ Tree visualization COMPLETED with enhanced pan dynamics & improved node spacing');
}

// ========================
// Additional Functions
// ========================

async function makeBackup() {
    if (!confirm('Buat backup data sekarang?')) {
        return;
    }

    try {
        const response = await fetch(`${API_URL}/backup`, {
            method: 'POST'
        });

        const data = await response.json();

        if (data.success) {
            showSuccess(`Backup berhasil: ${data.file}`);
        } else {
            showError(data.message || 'Gagal membuat backup');
        }
    } catch (error) {
        showError('Error: ' + error.message);
    }
}

function confirmResetTree() {
    if (confirm('⚠️ PERINGATAN: Ini akan menghapus SEMUA data.\n\nYakin ingin melanjutkan?')) {
        if (confirm('Konfirmasi sekali lagi: Hapus semua data?')) {
            resetTree();
        }
    }
}

async function resetTree() {
    try {
        const response = await fetch(`${API_URL}/tree/clear`, {
            method: 'POST'
        });

        const data = await response.json();

        if (data.success) {
            showSuccess('✅ Semua data telah direset');
            refreshDashboard();
        } else {
            showError(data.message || 'Gagal reset pohon');
        }
    } catch (error) {
        showError('Error: ' + error.message);
    }
}

// ========================
// Initialize
// ========================

document.addEventListener('DOMContentLoaded', function() {
    console.log('✅ AVL Tree Web UI loaded');
    refreshDashboard();
});

// Keyboard shortcuts
document.addEventListener('keydown', function(event) {
    // Ctrl+Enter to submit form
    if (event.ctrlKey && event.key === 'Enter') {
        const activeInput = document.activeElement;
        if (activeInput.id === 'bulk-input') {
            event.preventDefault();
            insertBulkData();
        }
    }
});
