(function() {
    // Инициализация элементов
    const canvas = document.getElementById('canvas4');
    const loadBtn = document.getElementById('loadBtn');
    const fileInput = document.getElementById('fileInput');

    // Настройка canvas
    canvas.width = 800;
    canvas.height = 600;
    const ctx = canvas.getContext('2d');

    // Матричные операции (остаются без изменений)
    const mat4 = {
        identity: function() {
            return [
                [1, 0, 0, 0],
                [0, 1, 0, 0],
                [0, 0, 1, 0],
                [0, 0, 0, 1]
            ];
        },
        multiply: function(a, b) {
            const result = [[0,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,0]];
            for (let i = 0; i < 4; i++) {
                for (let j = 0; j < 4; j++) {
                    for (let k = 0; k < 4; k++) {
                        result[i][j] += a[i][k] * b[k][j];
                    }
                }
            }
            return result;
        },
        multiplyVec: function(m, v) {
            const result = [0, 0, 0, 0];
            for (let i = 0; i < 4; i++) {
                for (let j = 0; j < 4; j++) {
                    result[i] += m[i][j] * v[j];
                }
            }
            return result;
        },
        translate: function(tx, ty, tz) {
            return [
                [1, 0, 0, tx],
                [0, 1, 0, ty],
                [0, 0, 1, tz],
                [0, 0, 0, 1]
            ];
        },
        scale: function(sx, sy, sz) {
            return [
                [sx, 0, 0, 0],
                [0, sy, 0, 0],
                [0, 0, sz, 0],
                [0, 0, 0, 1]
            ];
        },
        rotateX: function(angle) {
            const rad = angle * Math.PI / 180;
            const c = Math.cos(rad);
            const s = Math.sin(rad);
            return [
                [1, 0, 0, 0],
                [0, c, -s, 0],
                [0, s, c, 0],
                [0, 0, 0, 1]
            ];
        },
        rotateY: function(angle) {
            const rad = angle * Math.PI / 180;
            const c = Math.cos(rad);
            const s = Math.sin(rad);
            return [
                [c, 0, s, 0],
                [0, 1, 0, 0],
                [-s, 0, c, 0],
                [0, 0, 0, 1]
            ];
        },
        rotateZ: function(angle) {
            const rad = angle * Math.PI / 180;
            const c = Math.cos(rad);
            const s = Math.sin(rad);
            return [
                [c, -s, 0, 0],
                [s, c, 0, 0],
                [0, 0, 1, 0],
                [0, 0, 0, 1]
            ];
        },
        reflectX: function() {
            return [
                [-1, 0, 0, 0],
                [0, 1, 0, 0],
                [0, 0, 1, 0],
                [0, 0, 0, 1]
            ];
        },
        reflectY: function() {
            return [
                [1, 0, 0, 0],
                [0, -1, 0, 0],
                [0, 0, 1, 0],
                [0, 0, 0, 1]
            ];
        },
        reflectZ: function() {
            return [
                [1, 0, 0, 0],
                [0, 1, 0, 0],
                [0, 0, -1, 0],
                [0, 0, 0, 1]
            ];
        }
    };

    // Состояние приложения
    const state = {
        vertices: [],
        originalVertices: [],
        edges: [],
        transformationMatrix: mat4.identity(),
        perspectiveEnabled: false,
        perspectiveDistance: 500,
        debugMode: false
    };

    // Основные функции
    function parseObjectFile(content) {
        state.vertices = [];
        state.edges = [];
        state.originalVertices = [];

        const lines = content.split('\n');
        for (const line of lines) {
            const parts = line.trim().split(/\s+/);
            if (parts.length === 0) continue;

            if (parts[0] === 'v' && parts.length === 4) {
                const x = parseFloat(parts[1]);
                const y = parseFloat(parts[2]);
                const z = parseFloat(parts[3]);
                state.vertices.push([x, y, z, 1]);
                state.originalVertices.push([x, y, z, 1]);
            } else if (parts[0] === 'e' && parts.length === 3) {
                const i = parseInt(parts[1]);
                const j = parseInt(parts[2]);
                if (i >= 0 && j >= 0 && i < state.vertices.length && j < state.vertices.length) {
                    state.edges.push([i, j]);
                }
            }
        }

        if (state.edges.length === 0 && state.vertices.length > 1) {
            for (let i = 0; i < state.vertices.length - 1; i++) {
                state.edges.push([i, i + 1]);
            }
        }
    }

    function resetTransformations() {
        state.transformationMatrix = mat4.identity();
        state.perspectiveEnabled = false;
        state.perspectiveDistance = 500;
        applyTransformations();
        drawObject();
    }

    function applyTransformations() {
        state.vertices = state.originalVertices.map(v => [...v]);
        for (let i = 0; i < state.vertices.length; i++) {
            state.vertices[i] = mat4.multiplyVec(state.transformationMatrix, state.vertices[i]);
        }
    }

    function drawObject() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.fillStyle = 'black';
        ctx.font = '14px Arial';
        ctx.fillText('3D Object Viewer - ' + (state.perspectiveEnabled ? 'Perspective' : 'Orthographic') + ' Projection', 10, 20);

        if (state.vertices.length === 0) return;

        const projectedVertices = state.vertices.map(v => {
            if (state.perspectiveEnabled) {
                const f = state.perspectiveDistance / (state.perspectiveDistance + v[2]);
                return [
                    canvas.width / 2 + v[0] * f,
                    canvas.height / 2 - v[1] * f,
                    v[2]
                ];
            } else {
                return [
                    canvas.width / 2 + v[0],
                    canvas.height / 2 - v[1],
                    v[2]
                ];
            }
        });

        // Рисуем ребра
        ctx.strokeStyle = 'blue';
        ctx.lineWidth = 2;
        for (const [i, j] of state.edges) {
            if (i < projectedVertices.length && j < projectedVertices.length) {
                ctx.beginPath();
                ctx.moveTo(projectedVertices[i][0], projectedVertices[i][1]);
                ctx.lineTo(projectedVertices[j][0], projectedVertices[j][1]);
                ctx.stroke();
            }
        }

        // Рисуем вершины
        ctx.fillStyle = 'red';
        for (const v of projectedVertices) {
            ctx.beginPath();
            ctx.arc(v[0], v[1], 3, 0, Math.PI * 2);
            ctx.fill();
        }
    }

    // Обработчики событий
    function handleKeyPress(e) {
        const step = 10;
        const angle = 5;
        const scaleStep = 0.1;

        let newTransform = mat4.identity();
        let needUpdate = true;

        switch (e.key.toLowerCase()) {
            // Перемещение
            case 'a': newTransform = mat4.translate(-step, 0, 0); break;
            case 'd': newTransform = mat4.translate(step, 0, 0); break;
            case 'w': newTransform = mat4.translate(0, step, 0); break;
            case 's': newTransform = mat4.translate(0, -step, 0); break;
            case 'q': newTransform = mat4.translate(0, 0, -step); break;
            case 'e': newTransform = mat4.translate(0, 0, step); break;

            // Вращение
            case '1': newTransform = mat4.rotateX(-angle); break;
            case '2': newTransform = mat4.rotateX(angle); break;
            case '3': newTransform = mat4.rotateY(-angle); break;
            case '4': newTransform = mat4.rotateY(angle); break;
            case '5': newTransform = mat4.rotateZ(-angle); break;
            case '6': newTransform = mat4.rotateZ(angle); break;

            // Масштабирование
            case '-': newTransform = mat4.scale(1-scaleStep, 1-scaleStep, 1-scaleStep); break;
            case '=': newTransform = mat4.scale(1+scaleStep, 1+scaleStep, 1+scaleStep); break;

            // Отражение
            case 'f': newTransform = mat4.reflectX(); break;
            case 'g': newTransform = mat4.reflectY(); break;
            case 'h': newTransform = mat4.reflectZ(); break;

            // Перспектива
            case 'p':
                state.perspectiveEnabled = !state.perspectiveEnabled;
                drawObject();
                return;
            case '[':
                state.perspectiveDistance = Math.max(50, state.perspectiveDistance - 50);
                drawObject();
                return;
            case ']':
                state.perspectiveDistance += 50;
                drawObject();
                return;

            // Сброс
            case 'r':
                resetTransformations();
                return;

            default:
                needUpdate = false;
        }

        if (needUpdate) {
            state.transformationMatrix = mat4.multiply(state.transformationMatrix, newTransform);
            applyTransformations();
            drawObject();
        }
    }

    function handleFileLoad() {
        const file = fileInput.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                parseObjectFile(e.target.result);
                resetTransformations();
            };
            reader.readAsText(file);
        }
    }

    // Публичный интерфейс
    window.Editor4 = {
        init: function() {
            // Настройка обработчиков событий
            loadBtn.addEventListener('click', handleFileLoad);
            document.addEventListener('keydown', handleKeyPress);

            // Начальная отрисовка
            drawObject();
        }
    };
    Editor4.init();
})();

